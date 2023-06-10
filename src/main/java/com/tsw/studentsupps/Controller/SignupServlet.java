package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Carrello;
import com.tsw.studentsupps.Model.Utente;
import com.tsw.studentsupps.Model.UtenteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Signup")
public class SignupServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("Account");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente u= new Utente();
        u.setNome(request.getParameter("name"));
        u.setCognome(request.getParameter("lastname"));
        u.setNumeroTel(request.getParameter("phone"));
        u.setUsername(request.getParameter("username"));
        u.setEmail(request.getParameter("email"));
        u.setPasswordHash(request.getParameter("password"));

        UtenteDAO.doSave(u);
        UtenteDAO.doUpdateIdCart(u, (Carrello) request.getSession().getAttribute("Cart"));

        u.setPasswordHash("");
        request.getSession().setAttribute("Utente", u);
        response.sendRedirect(".");
    }
}
