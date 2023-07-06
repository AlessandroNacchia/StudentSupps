package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Recensione;
import com.tsw.studentsupps.Model.RecensioneDAO;
import com.tsw.studentsupps.Model.OrdineDAO;
import com.tsw.studentsupps.Model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Shop/Prodotto/DeleteReview")
public class DeleteReviewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + '/');
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.userCheck(request, response)) return;
        if(Checks.UUIDCheck(request, response, request.getParameter("id"))) return;

        Recensione recToDelete= RecensioneDAO.doRetrieveById(request.getParameter("id"));
        if(recToDelete == null) {
            return;
        }

        RecensioneDAO.doDelete(recToDelete);
    }
}
