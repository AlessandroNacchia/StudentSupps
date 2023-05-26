package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Utente;
import com.tsw.studentsupps.Model.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente u= UtenteDAO.doRetrieveByUsernamePassword(
                request.getParameter("username"), request.getParameter("password"));

        if(u!= null) {
            request.getSession().setAttribute("utente", u);
            response.sendRedirect(".");
        }
        else {
            request.setAttribute("loginStatus", "failedLogin");
            RequestDispatcher dispatcher= request.getRequestDispatcher(".");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
