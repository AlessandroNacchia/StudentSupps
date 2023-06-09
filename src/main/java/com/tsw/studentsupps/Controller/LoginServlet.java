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

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente u= UtenteDAO.doRetrieveByUsernamePassword(
                request.getParameter("username"), request.getParameter("password"));

        if(u!= null) {
            HttpSession session= request.getSession();
            session.setAttribute("Utente", u);

            CarrelloDAO.doDelete((Carrello) session.getAttribute("Cart"));
            Carrello cart= CarrelloDAO.doRetrieveById(UtenteDAO.doRetrieveIdCart(u));
            session.setAttribute("Cart", cart);
            response.sendRedirect(".");
        }
        else {
            request.setAttribute("loginStatus", "failedLogin");
            RequestDispatcher dispatcher= request.getRequestDispatcher("pages/Login.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("Account");
    }
}
