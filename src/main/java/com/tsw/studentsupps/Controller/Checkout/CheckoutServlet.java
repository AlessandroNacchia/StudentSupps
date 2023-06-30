package com.tsw.studentsupps.Controller.Checkout;

import com.tsw.studentsupps.Controller.utils.Checks;
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

@WebServlet("/Cart/Checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.userCheck(request, response)) return;

        HttpSession session= request.getSession();
        Carrello cart;
        if(session.getAttribute("Cart")==null) {
            cart= new Carrello();
            CarrelloDAO.doSave(cart);
            session.setAttribute("Cart", cart);
        } else
            cart= (Carrello) session.getAttribute("Cart");

        //Ricalcolo da zero il totale per sicurezza
        calcoloTotale(session, cart, ProdottocarrelloDAO.doRetrieveProdotti(cart.getId()));

        request.setAttribute("prodList", ProdottocarrelloDAO.doRetrieveProdotti(cart.getId()));

        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Checkout/Checkout.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    private static void calcoloTotale(HttpSession session, Carrello cart, List<String> prodList) {
        cart.setTotale(0);
        for(String idProd: prodList) {
            int quantita= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), idProd);
            Prodotto prod= ProdottoDAO.doRetrieveById(idProd);
            if(prod==null) {
                ProdottocarrelloDAO.doDelete(cart.getId(), idProd);
                continue;
            }
            if(prod.getQuantita() < quantita || quantita > 99) {
                if(prod.getQuantita() <= 0) {
                    ProdottocarrelloDAO.doDelete(cart.getId(), idProd);
                    continue;
                }
                else {
                    ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), idProd, Math.min(prod.getQuantita(), 99));
                }
            }

            BigDecimal price= BigDecimal.valueOf(prod.getPrezzo());
            cart.setTotale(
                    (BigDecimal.valueOf(cart.getTotale()).add(
                            price.multiply(BigDecimal.valueOf(quantita))
                    )).doubleValue());
        }

        session.setAttribute("Cart", cart);
        CarrelloDAO.doUpdate(cart);
    }
}
