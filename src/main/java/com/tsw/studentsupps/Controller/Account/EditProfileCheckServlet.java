package com.tsw.studentsupps.Controller.Account;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Account/EditProfileCheck")
public class EditProfileCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.userCheck(request, response)) return;
        if(Checks.UUIDCheck(request, response, request.getParameter("id"))) return;

        Utente u= (Utente) request.getSession().getAttribute("Utente");
        if(!u.getId().equals(request.getParameter("id"))) {
            request.setAttribute("errorMessage", "Stai provando a modificare un utente diverso da te!");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        RequestDispatcher dispatcher=request.getRequestDispatcher("/UpdateUser");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
