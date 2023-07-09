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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/Cart/Checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session= request.getSession();
        if(session.getAttribute("Utente") == null) {
            response.sendRedirect(request.getContextPath()+"/Login");
            return;
        }
        if(Checks.userCheck(request, response)) return;

        Carrello cart;
        if(session.getAttribute("Cart")==null) {
            response.sendRedirect(request.getContextPath()+"/Cart");
            return;
        } else
            cart= (Carrello) session.getAttribute("Cart");

        List<String> prodIdListCart= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
        if(prodIdListCart.isEmpty() || cart.getTotale() == 0) {
            response.sendRedirect(request.getContextPath()+"/Cart");
            return;
        }

        //Ricalcolo da zero il totale per sicurezza
        calcoloTotale(session, cart, prodIdListCart);

        request.setAttribute("prodList", prodIdListCart);
        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Checkout/Checkout.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session= request.getSession();

        Utente u= (Utente) session.getAttribute("Utente");
        if(u == null) {
            response.sendRedirect(request.getContextPath()+"/Login");
            return;
        }
        if(Checks.userCheck(request, response)) return;

        String address= request.getParameter("address");
        String payMethod= request.getParameter("payMethod");

        if(Checks.UUIDCheck(request, response, address)) return;
        if(Checks.UUIDCheck(request, response, payMethod)) return;

        Carrello cart;
        if(session.getAttribute("Cart")==null) {
            response.sendRedirect(request.getContextPath()+"/Cart");
            return;
        } else
            cart= (Carrello) session.getAttribute("Cart");

        List<String> prodIdListCart= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
        double totCart= cart.getTotale();
        if(prodIdListCart.isEmpty() || totCart == 0) {
            response.sendRedirect(request.getContextPath()+"/Cart");
            return;
        }

        if(!IndirizzoDAO.doExistsById_UserId(address, u.getId())) {
            request.setAttribute("prodList", prodIdListCart);
            request.setAttribute("checkoutStatus", "addressNotValid");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Checkout/Checkout.jsp");
            dispatcher.forward(request, response);
            return;
        }
        if(!MetodopagamentoDAO.doExistsById_UserId(payMethod, u.getId())) {
            request.setAttribute("prodList", prodIdListCart);
            request.setAttribute("checkoutStatus", "payMethodNotValid");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Checkout/Checkout.jsp");
            dispatcher.forward(request, response);
            return;
        }

        //Ricalcolo da zero il totale per sicurezza
        calcoloTotale(session, cart, prodIdListCart);
        if(totCart != cart.getTotale()) {
            request.setAttribute("prodList", prodIdListCart);
            request.setAttribute("checkoutStatus", "totalPriceChanged");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Checkout/Checkout.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Ordine ordine= new Ordine();
        ordine.setTotale(cart.getTotale());
        ordine.setDataAcquisto(Timestamp.valueOf(LocalDateTime.now()));
        ordine.setDataConsegna(Timestamp.valueOf(LocalDateTime.now().plusDays(5)));
        ordine.setStato("processing");

        OrdineDAO.doSave(ordine, u.getId(), address, payMethod);

        prodIdListCart= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
        for(String prodId: prodIdListCart) {
            int quantita= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), prodId);
            Prodotto prod= ProdottoDAO.doRetrieveById(prodId);
            if(prod==null)
                throw new RuntimeException();

            ProdottoDAO.doUpdateQuantita(prodId, prod.getQuantita() - quantita);

            Prodottoordine prodOrd= new Prodottoordine();
            prodOrd.setId(prodId);
            prodOrd.setNome_prodotto(prod.getNome());
            prodOrd.setQuantita((short) quantita);
            prodOrd.setPrezzo_acquisto(prod.getPrezzo());
            prodOrd.setIVA_acquisto(prod.getIVA());

            ProdottoordineDAO.doSave(ordine.getId(), prodOrd);

            ProdottocarrelloDAO.doDelete(cart.getId(), prodId);
        }

        response.sendRedirect(request.getContextPath()+"/Orders");
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
