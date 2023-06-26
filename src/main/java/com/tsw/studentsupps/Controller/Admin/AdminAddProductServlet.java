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

@WebServlet("/Admin/AddProduct")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 100  // 100 MB
)
public class AdminAddProductServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user= (Utente) request.getSession().getAttribute("Utente");
        if(user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }
        if(!user.equals(UtenteDAO.doRetrieveById(user.getId()))) {
            request.setAttribute("errorMessage", "Dati Utente Session/DB non coincidenti");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Admin/AddProduct.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("Utente");
        if(user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }
        if(!user.equals(UtenteDAO.doRetrieveById(user.getId()))) {
            request.setAttribute("errorMessage", "Dati Utente Session/DB non coincidenti");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        if(ProdottoDAO.doExistsByName(request.getParameter("name"))) {
            request.setAttribute("addProductStatus", "nameTaken");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Admin/AddProduct.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Prodotto p= new Prodotto();
        String name= request.getParameter("name");
        p.setNome(name);
        p.setDescrizione(request.getParameter("description"));
        p.setPrezzo((double) Math.round(Double.parseDouble(request.getParameter("price")) * 100) / 100);
        p.setIVA(Short.parseShort(request.getParameter("iva")));
        p.setQuantita(Integer.parseInt(request.getParameter("quantity")));

        Part imagePart= request.getPart("image");
        File uploads= new File((String) getServletContext().getAttribute("prodImageFolder"));
        String imageName= imagePart.getSubmittedFileName();
        File imageFile= new File(uploads, name + imageName.substring(imageName.lastIndexOf(".")));

        try (InputStream input= imagePart.getInputStream()) {
            if(!Files.exists(imageFile.toPath()))
                Files.copy(input, imageFile.toPath());
        }

        ProdottoDAO.doSave(p);

        String[] categorie= request.getParameterValues("categories");
        if(categorie != null){
            for(String catId: categorie) {
                ProdottocategoriaDAO.doSave(p.getId(), catId);
            }
        }

        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }
}
