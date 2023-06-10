package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/Cart")
public class CartServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String prodToAdd= request.getParameter("prodToAdd");
        if(prodToAdd != null) {
            HttpSession session= request.getSession();
            Carrello cart= (Carrello) session.getAttribute("Cart");
            int quantitaAttuale= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), prodToAdd);
            if(quantitaAttuale == -1) {
                ProdottocarrelloDAO.doSave(cart.getId(), prodToAdd, 1);
            } else {
                ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), prodToAdd, quantitaAttuale+1);
            }

            //Ricalcolo da 0 il totale all'inserimento di un prodotto
            List<String> prodList= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
            cart.setTotale(0);
            for(String idProd: prodList) {
                int quantita= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), idProd);
                double price= ProdottoDAO.doRetrieveById(idProd).getPrezzo();
                cart.setTotale(cart.getTotale() + (price * quantita));
            }

            CarrelloDAO.doUpdate(cart);
        }

        response.sendRedirect(request.getParameter("callerPage"));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
