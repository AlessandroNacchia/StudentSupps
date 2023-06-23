package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Carrello;
import com.tsw.studentsupps.Model.CarrelloDAO;
import com.tsw.studentsupps.Model.Utente;
import com.tsw.studentsupps.Model.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Signup")
public class SignupServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("Account");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(UtenteDAO.doExistsByUsername(request.getParameter("username"))) {
            request.setAttribute("signupStatus", "usernameTaken");
            RequestDispatcher dispatcher= request.getRequestDispatcher("pages/Login.jsp");
            dispatcher.forward(request, response);
            return;
        }
        if(UtenteDAO.doExistsByUsername(request.getParameter("email"))) {
            request.setAttribute("signupStatus", "emailTaken");
            RequestDispatcher dispatcher= request.getRequestDispatcher("pages/Login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Utente u= new Utente();
        u.setNome(request.getParameter("name"));
        u.setCognome(request.getParameter("lastname"));
        u.setNumeroTel(request.getParameter("phone"));
        u.setUsername(request.getParameter("username"));
        u.setEmail(request.getParameter("email"));
        u.setPasswordHash(request.getParameter("password"));

        UtenteDAO.doSave(u);
        HttpSession session= request.getSession();
        Carrello cart;
        if(session.getAttribute("Cart")==null) {
            cart= new Carrello();
            CarrelloDAO.doSave(cart);
            session.setAttribute("Cart", cart);
        } else
            cart= (Carrello) session.getAttribute("Cart");
        UtenteDAO.doUpdateIdCart(u, cart);

        u.setPasswordHash("");
        request.getSession().setAttribute("Utente", u);
        response.sendRedirect(request.getContextPath()+'/');
    }
}
