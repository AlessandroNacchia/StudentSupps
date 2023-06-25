package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Model.Utente;
import com.tsw.studentsupps.Model.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Admin/Users")
public class AdminUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        List<Utente> userList= UtenteDAO.doRetrieveAll();
        request.setAttribute("userList", userList);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/Admin/ViewUsers.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
