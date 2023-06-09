package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Cart")
public class CartServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String prodToAdd= request.getParameter("prodToAdd");
        if(prodToAdd != null) {
            HttpSession session= request.getSession();
            Carrello cart= (Carrello) session.getAttribute("Cart");
            int quantitaAttuale= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), prodToAdd);
            if(quantitaAttuale == -1) {
                ProdottocarrelloDAO.doSave(cart.getId(), prodToAdd);
            } else {
                ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), prodToAdd, quantitaAttuale+1);
            }
        }

        response.sendRedirect(request.getParameter("callerPage"));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
