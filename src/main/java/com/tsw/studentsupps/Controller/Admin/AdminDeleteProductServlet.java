package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Model.Prodotto;
import com.tsw.studentsupps.Model.ProdottoDAO;
import com.tsw.studentsupps.Model.Utente;
import com.tsw.studentsupps.Model.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@WebServlet("/Admin/DeleteProduct")
public class AdminDeleteProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user= (Utente) request.getSession().getAttribute("Utente");
        if(user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }

        String prodToDeleteId= request.getParameter("id");
        if(!UUID.fromString(prodToDeleteId).toString().equals(prodToDeleteId)) {
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

        Prodotto prodToDelete= ProdottoDAO.doRetrieveById(prodToDeleteId);
        if(prodToDelete == null) {
            request.setAttribute("errorMessage", "Prodotto da cancellare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        String prodName= prodToDelete.getNome();
        String imageToDelete= prodName + ".png";
        File imageToMove= new File((String) getServletContext().getAttribute("prodImageFolder"), imageToDelete);

        String delProdImageFolder= (String) getServletContext().getAttribute("delProdImageFolder");
        File movedImage= new File(delProdImageFolder, imageToDelete);
        for(int i= 0; i<1000; i++) {
            if(!Files.exists(movedImage.toPath())) {
                Files.move(imageToMove.toPath(), movedImage.toPath());
                break;
            }
            else {
                movedImage= new File(delProdImageFolder, prodName + i + imageToDelete.substring(imageToDelete.lastIndexOf(".")));
            }
        }

        ProdottoDAO.doDelete(prodToDelete);

        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
