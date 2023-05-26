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

@WebServlet("/signin")
public class RegistrazioneServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher= request.getRequestDispatcher("pages/signin.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente u= new Utente();
        u.setUsername(request.getParameter("username"));
        u.setPasswordHash(request.getParameter("password"));
        u.setEmail(request.getParameter("email"));
        u.setNome(request.getParameter("nome"));
        UtenteDAO.doSave(u);
        u.setPasswordHash("");
        request.getSession().setAttribute("utente", u);
        response.sendRedirect(".");
    }
}
