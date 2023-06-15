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
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/Cart")
public class CartServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String prodToAdd= request.getParameter("prodToAdd");
        String prodToRemove= request.getParameter("prodToRemove");
        String prodToUpdate= request.getParameter("prodToUpdate");
        if(prodToAdd != null) {
            HttpSession session= request.getSession();
            Carrello cart= (Carrello) session.getAttribute("Cart");

            int quantitaAttuale= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), prodToAdd);
            if(quantitaAttuale == -1) {
                ProdottocarrelloDAO.doSave(cart.getId(), prodToAdd, 1);
            } else {
                ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), prodToAdd, quantitaAttuale+1);
            }

            List<String> prodList= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
            //Ricalcolo da 0 il totale all'inserimento di un prodotto
            calcoloTotale(session, cart, prodList);
        } else if(prodToRemove != null) {
            HttpSession session= request.getSession();
            Carrello cart= (Carrello) session.getAttribute("Cart");

            ProdottocarrelloDAO.doDelete(cart.getId(), prodToRemove);

            List<String> prodList= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
            //Ricalcolo da 0 il totale alla rimozione di un prodotto
            calcoloTotale(session, cart, prodList);
        } else if(prodToUpdate != null) {
            HttpSession session= request.getSession();
            Carrello cart= (Carrello) session.getAttribute("Cart");

            int quantitaAttuale= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), prodToUpdate);
            if(quantitaAttuale == -1) {
                ProdottocarrelloDAO.doSave(cart.getId(), prodToUpdate, 1);
            } else {
                String updateType= request.getParameter("updateType");
                if(updateType.equals("add")) {
                    if((quantitaAttuale+1) <= 99)
                        ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), prodToUpdate, quantitaAttuale+1);
                }
                else if(updateType.equals("remove")) {
                    if((quantitaAttuale-1) <= 0)
                        ProdottocarrelloDAO.doDelete(cart.getId(), prodToUpdate);
                    else
                        ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), prodToUpdate, quantitaAttuale-1);
                }
                else {
                    int updatedQuantita= Integer.parseInt(request.getParameter("updateQuantity"));
                    if((updatedQuantita) <= 0)
                        ProdottocarrelloDAO.doDelete(cart.getId(), prodToUpdate);
                    else if(updatedQuantita <= 99)
                        ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), prodToUpdate, updatedQuantita);
                }
            }

            List<String> prodList= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
            //Ricalcolo da 0 il totale alla rimozione di un prodotto
            calcoloTotale(session, cart, prodList);
        }

        response.sendRedirect(request.getParameter("callerPage"));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session= request.getSession();
        Carrello cart= (Carrello) session.getAttribute("Cart");

        List<String> prodList= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
        request.setAttribute("prodList", prodList);

        //Ricalcolo da 0 il totale per sicurezza
        calcoloTotale(session, cart, prodList);

        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Cart.jsp");
        dispatcher.forward(request, response);
    }


    private static void calcoloTotale(HttpSession session, Carrello cart, List<String> prodList) {
        cart.setTotale(0);
        for(String idProd: prodList) {
            int quantita= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), idProd);
            BigDecimal price= BigDecimal.valueOf(ProdottoDAO.doRetrieveById(idProd).getPrezzo());
            cart.setTotale(
                    (BigDecimal.valueOf(cart.getTotale()).add(
                            price.multiply(BigDecimal.valueOf(quantita))
                    )).doubleValue());
        }

        session.setAttribute("Cart", cart);
        CarrelloDAO.doUpdate(cart);
    }
}
