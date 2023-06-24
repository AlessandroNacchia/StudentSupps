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

import java.io.IOException;
import java.util.UUID;

@WebServlet("/Admin/DeleteProduct")
public class AdminDeleteProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente callingUser= (Utente) request.getSession().getAttribute("Utente");
        String prodToDeleteId= request.getParameter("id");
        if(!UUID.fromString(prodToDeleteId).toString().equals(prodToDeleteId)) {
            request.setAttribute("errorMessage", "UUID non valido");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }
        Prodotto prodToDelete= ProdottoDAO.doRetrieveById(prodToDeleteId);
        if(!callingUser.equals(UtenteDAO.doRetrieveById(callingUser.getId())) || prodToDelete == null) {
            request.setAttribute("errorMessage", "Dati Utente Session/DB non coincidenti");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        if(callingUser.isAdmin() || prodToDelete.getId().equals(callingUser.getId())) {
            ProdottoDAO.doDelete(prodToDelete);
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
            dispatcher.forward(request,response);
            return;
        }

        request.setAttribute("errorMessage", "Delete Servlet Error");
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
