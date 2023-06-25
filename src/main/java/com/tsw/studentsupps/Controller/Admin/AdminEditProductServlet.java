package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Model.Prodotto;
import com.tsw.studentsupps.Model.ProdottoDAO;
import com.tsw.studentsupps.Model.Utente;
import com.tsw.studentsupps.Model.UtenteDAO;
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
        prodToUpdate.setNome(name);
        prodToUpdate.setNome(request.getParameter("description"));
        prodToUpdate.setPrezzo((double) Math.round(Double.parseDouble(request.getParameter("price")) * 100) / 100);
        prodToUpdate.setIVA(Short.parseShort(request.getParameter("iva")));
        prodToUpdate.setQuantita(Integer.parseInt(request.getParameter("quantity")));

        Part imagePart= request.getPart("image");
        if(imagePart == null) {
            String imageToRename= oldName + ".png";
            File image= new File((String) getServletContext().getAttribute("prodImageFolder"), imageToRename);
            if(!image.renameTo(new File((String) getServletContext().getAttribute("prodImageFolder"), name + ".png"))) {
                request.setAttribute("errorMessage", "Rename Image Error");
                RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request,response);
                return;
            }
        } else {
            String imageToDelete= name + ".png";
            File image= new File((String) getServletContext().getAttribute("prodImageFolder"), imageToDelete);
            if(!image.delete()) {
                request.setAttribute("errorMessage", "Delete Image Error");
                RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request,response);
                return;
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

        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }
}
