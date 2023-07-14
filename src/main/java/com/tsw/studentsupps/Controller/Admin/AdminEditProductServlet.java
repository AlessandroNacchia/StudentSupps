package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebServlet("/Admin/EditProduct")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 100  // 100 MB
)
public class AdminEditProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request,response)) return;

        String id= request.getParameter("id");
        if(id == null || !UUID.fromString(id).toString().equals(id)) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }

        Prodotto p= ProdottoDAO.doRetrieveById(id);
        if(p == null) {
            request.setAttribute("errorMessage", "Prodotto da modificare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        request.setAttribute("prodToEdit", p);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/Admin/EditProduct.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        String prodToUpdateId= request.getParameter("id");
        if(Checks.UUIDCheck(request, response, prodToUpdateId)) return;

        Prodotto prodToUpdate= ProdottoDAO.doRetrieveById(prodToUpdateId);
        if(prodToUpdate == null) {
            request.setAttribute("errorMessage", "Prodotto da aggiornare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        String oldName= prodToUpdate.getNome();
        String name= request.getParameter("name");
        String description= request.getParameter("description");
        String price= request.getParameter("price");
        String iva= request.getParameter("iva");
        String quantity= request.getParameter("quantity");

        String nameRGX= "^[\\w\\-+.(){} ]{2,50}$";
        String descrRGX= "^[\\s\\S]{2,1000}$";
        String priceRGX= "^[+]?([0-9]{0,7}[.])?[0-9]{0,7}$";
        String ivaRGX= "^[+]?[0-9]{1,2}$|^[+]?100$";
        String quantityRGX= "^[+]?[0-9]{0,7}$";

        if(!name.matches(nameRGX)) {
            request.setAttribute("editProdStatus", "nameWrongPattern");
            doGet(request, response);
            return;
        }
        if(!description.matches(descrRGX)) {
            request.setAttribute("editProdStatus", "descriptionWrongPattern");
            doGet(request, response);
            return;
        }
        if(!price.matches(priceRGX)) {
            request.setAttribute("editProdStatus", "priceWrongPattern");
            doGet(request, response);
            return;
        }
        if(!iva.matches(ivaRGX)) {
            request.setAttribute("editProdStatus", "ivaWrongPattern");
            doGet(request, response);
            return;
        }
        if(!quantity.matches(quantityRGX)) {
            request.setAttribute("editProdStatus", "quantityWrongPattern");
            doGet(request, response);
            return;
        }

        if(!oldName.equals(name) && ProdottoDAO.doExistsByName(name)) {
            request.setAttribute("prodToEdit", prodToUpdate);
            request.setAttribute("editProdStatus", "nameTaken");
            doGet(request, response);
            return;
        }

        prodToUpdate.setNome(name);
        prodToUpdate.setDescrizione(description);
        prodToUpdate.setPrezzo((double) Math.round(Double.parseDouble(price) * 100) / 100);
        prodToUpdate.setIVA(Short.parseShort(iva));
        prodToUpdate.setQuantita(Integer.parseInt(quantity));

        Part imagePart= request.getPart("image");
        if(imagePart.getSubmittedFileName().equals("") && imagePart.getSize() == 0) {
            String imageToRename= oldName + ".png";
            File image= new File((String) getServletContext().getAttribute("prodImageFolder"), imageToRename);
            if(Files.exists(image.toPath())) {
                if(!image.renameTo(new File((String) getServletContext().getAttribute("prodImageFolder"), name + ".png"))) {
                    request.setAttribute("errorMessage", "Rename Image Error");
                    RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(request,response);
                    return;
                }
            }
        } else {
            String imageToDelete= oldName + ".png";
            File imageToMove= new File((String) getServletContext().getAttribute("prodImageFolder"), imageToDelete);

            String delProdImageFolder= (String) getServletContext().getAttribute("delProdImageFolder");
            File movedImage= new File(delProdImageFolder, imageToDelete);
            if(Files.exists(imageToMove.toPath())) {
                for(int i= 0; i<1000; i++) {
                    if(!Files.exists(movedImage.toPath())) {
                        Files.move(imageToMove.toPath(), movedImage.toPath());
                        break;
                    }
                    else {
                        movedImage= new File(delProdImageFolder, oldName + i + imageToDelete.substring(imageToDelete.lastIndexOf(".")));
                    }
                }
            }

            File uploads= new File((String) getServletContext().getAttribute("prodImageFolder"));
            String imageName= imagePart.getSubmittedFileName();
            File imageFile= new File(uploads, name + imageName.substring(imageName.lastIndexOf(".")));

            try (InputStream input= imagePart.getInputStream()) {
                if(!Files.exists(imageFile.toPath()))
                    Files.copy(input, imageFile.toPath());
            }
        }

        ProdottoDAO.doUpdate(prodToUpdate);

        String[] categorie= request.getParameterValues("categories");
        if(categorie == null)
            categorie= new String[]{""};
        List<String> catList= Arrays.asList(categorie);
        for(Categoria cat: CategoriaDAO.doRetrieveAll()) {
            if(catList.contains(cat.getId()) && !ProdottocategoriaDAO.doExists(prodToUpdate.getId(), cat.getId())) {
                if(Checks.UUIDCheck(request, response, cat.getId())) return;
                if(CategoriaDAO.doRetrieveById(cat.getId()) != null)
                    ProdottocategoriaDAO.doSave(prodToUpdate.getId(), cat.getId());
            }
            else if(!catList.contains(cat.getId()) && ProdottocategoriaDAO.doExists(prodToUpdate.getId(), cat.getId())) {
                ProdottocategoriaDAO.doDelete(prodToUpdate.getId(), cat.getId());
            }
        }

        String discountId= request.getParameter("discount");
        if(discountId.equals("Nessuno"))
            ProdottoDAO.doRemoveDiscount(prodToUpdate.getId());
        else {
            if(Checks.UUIDCheck(request, response, discountId)) return;
            if(ScontoDAO.doRetrieveById(discountId) != null)
                ProdottoDAO.doUpdateDiscount(prodToUpdate.getId(), discountId);
        }

        request.setAttribute("returnPage", "/Admin/Products");
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }
}
