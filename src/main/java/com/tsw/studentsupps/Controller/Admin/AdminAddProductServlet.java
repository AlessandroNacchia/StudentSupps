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

@WebServlet("/Admin/AddProduct")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 100  // 100 MB
)
public class AdminAddProductServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Admin/AddProduct.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        String name= request.getParameter("name");
        String description= request.getParameter("description");
        String price= request.getParameter("price");
        String iva= request.getParameter("iva");
        String quantity= request.getParameter("quantity");

        String nameRGX= "^[\\w\\-. ]{2,50}$";
        String descrRGX= "^.{2,1000}$";
        String priceRGX= "^[+]?([0-9]{0,7}[.])?[0-9]{0,7}$";
        String ivaRGX= "^[+]?[0-9]{1,2}$|^[+]?100$";
        String quantityRGX= "^[+]?[0-9]{0,7}$";

        if(!name.matches(nameRGX)) {
            request.setAttribute("addProdStatus", "nameWrongPattern");
            doGet(request, response);
            return;
        }
        if(!description.matches(descrRGX)) {
            request.setAttribute("addProdStatus", "descriptionWrongPattern");
            doGet(request, response);
            return;
        }
        if(!price.matches(priceRGX)) {
            request.setAttribute("addProdStatus", "priceWrongPattern");
            doGet(request, response);
            return;
        }
        if(!iva.matches(ivaRGX)) {
            request.setAttribute("addProdStatus", "ivaWrongPattern");
            doGet(request, response);
            return;
        }
        if(!quantity.matches(quantityRGX)) {
            request.setAttribute("addProdStatus", "quantityWrongPattern");
            doGet(request, response);
            return;
        }

        if(ProdottoDAO.doExistsByName(request.getParameter("name"))) {
            request.setAttribute("addProdStatus", "nameTaken");
            doGet(request, response);
            return;
        }

        Prodotto p= new Prodotto();
        p.setNome(name);
        p.setDescrizione(description);
        p.setPrezzo((double) Math.round(Double.parseDouble(price) * 100) / 100);
        p.setIVA(Short.parseShort(iva));
        p.setQuantita(Integer.parseInt(quantity));

        Part imagePart= request.getPart("image");
        File uploads= new File((String) getServletContext().getAttribute("prodImageFolder"));
        String imageName= imagePart.getSubmittedFileName();
        if(imageName.equals("") && imagePart.getSize() == 0) {
            request.setAttribute("addProdStatus", "imageMissing");
            doGet(request, response);
            return;
        }
        File imageFile= new File(uploads, name + imageName.substring(imageName.lastIndexOf(".")));

        try (InputStream input= imagePart.getInputStream()) {
            if(!Files.exists(imageFile.toPath()))
                Files.copy(input, imageFile.toPath());
        }

        ProdottoDAO.doSave(p);

        String[] categorie= request.getParameterValues("categories");
        if(categorie != null){
            for(String catId: categorie) {
                if(Checks.UUIDCheck(request, response, catId)) return;
                if(CategoriaDAO.doRetrieveById(catId) != null)
                    ProdottocategoriaDAO.doSave(p.getId(), catId);
            }
        }

        String discountId= request.getParameter("discount");
        if(Checks.UUIDCheck(request, response, discountId)) return;
        if(ScontoDAO.doRetrieveById(discountId) != null)
            ProdottoDAO.doUpdateDiscount(p.getId(), discountId);

        request.setAttribute("returnPage", "/Admin/Products");
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }
}
