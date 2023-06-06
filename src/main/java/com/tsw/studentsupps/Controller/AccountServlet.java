package com.tsw.studentsupps.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Account")
public class AccountServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session= request.getSession();
        if(session.getAttribute("Utente")==null) {
            RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Login.jsp");
            dispatcher.forward(request, response);
        }
        else {
            RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Account.jsp");
            dispatcher.forward(request, response);
        }
    }
}
