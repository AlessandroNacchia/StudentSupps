package com.tsw.studentsupps.Controller.Admin;

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
        Utente user= (Utente) request.getSession().getAttribute("Utente");
        String id= request.getParameter("id");
        if(user == null || !user.isAdmin() || id == null || !UUID.fromString(id).toString().equals(id)) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }
        if(!user.equals(UtenteDAO.doRetrieveById(user.getId()))) {
            request.setAttribute("errorMessage", "Dati Utente Session/DB non coincidenti");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        Prodotto p= ProdottoDAO.doRetrieveById(request.getParameter("id"));
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
        Utente user= (Utente) request.getSession().getAttribute("Utente");
        if(user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }

        String prodToUpdateId= request.getParameter("id");
        if(!UUID.fromString(prodToUpdateId).toString().equals(prodToUpdateId)) {
            request.setAttribute("errorMessage", "UUID non valido");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }
        if(!user.equals(UtenteDAO.doRetrieveById(user.getId()))) {
            request.setAttribute("errorMessage", "Dati Utente Session/DB non coincidenti");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        Prodotto prodToUpdate= ProdottoDAO.doRetrieveById(prodToUpdateId);
        if(prodToUpdate == null) {
            request.setAttribute("errorMessage", "Prodotto da aggiornare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        String oldName= prodToUpdate.getNome();
        String name= request.getParameter("name");

        if(!oldName.equals(name) && ProdottoDAO.doExistsByName(name)) {
            request.setAttribute("prodToEdit", prodToUpdate);
            request.setAttribute("addProductStatus", "nameTaken");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Admin/EditProduct.jsp");
            dispatcher.forward(request, response);
            return;
        }

        prodToUpdate.setNome(name);
        prodToUpdate.setDescrizione(request.getParameter("description"));
        prodToUpdate.setPrezzo((double) Math.round(Double.parseDouble(request.getParameter("price")) * 100) / 100);
        prodToUpdate.setIVA(Short.parseShort(request.getParameter("iva")));
        prodToUpdate.setQuantita(Integer.parseInt(request.getParameter("quantity")));

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
            for(int i= 0; i<1000; i++) {
                if(!Files.exists(movedImage.toPath())) {
                    Files.move(imageToMove.toPath(), movedImage.toPath());
                    break;
                }
                else {
                    movedImage= new File(delProdImageFolder, oldName + i + imageToDelete.substring(imageToDelete.lastIndexOf(".")));
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
                ProdottocategoriaDAO.doSave(prodToUpdate.getId(), cat.getId());
            }
            else if(!catList.contains(cat.getId()) && ProdottocategoriaDAO.doExists(prodToUpdate.getId(), cat.getId())) {
                ProdottocategoriaDAO.doDelete(prodToUpdate.getId(), cat.getId());
            }
        }

        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }
}
