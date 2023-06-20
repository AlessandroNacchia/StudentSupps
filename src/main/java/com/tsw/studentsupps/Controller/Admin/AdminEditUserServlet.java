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
import java.util.UUID;

@WebServlet("/Admin/EditUser")
public class AdminEditUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user= (Utente) request.getSession().getAttribute("Utente");
        String id= request.getParameter("id");
        if(user == null || !user.isAdmin() || id == null || !UUID.fromString(id).toString().equals(id)) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        Utente u= UtenteDAO.doRetrieveById(request.getParameter("id"));
        request.setAttribute("userToEdit", u);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/Admin/EditUser.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
