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
        if(!request.getParameter("passwordS").equals(request.getParameter("passwordS2"))) {
            request.setAttribute("signupStatus", "passwordsNotEqual");
            RequestDispatcher dispatcher= request.getRequestDispatcher("pages/Login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Utente u= new Utente();
        u.setNome(request.getParameter("nameS"));
        u.setCognome(request.getParameter("lastnameS"));
        u.setNumeroTel(request.getParameter("phoneS"));
        u.setUsername(request.getParameter("usernameS"));
        u.setEmail(request.getParameter("emailS"));
        u.setPasswordHash(request.getParameter("passwordS"));

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
