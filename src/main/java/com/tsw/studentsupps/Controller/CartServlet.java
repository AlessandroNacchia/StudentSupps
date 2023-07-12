package com.tsw.studentsupps.Controller;

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

@WebServlet("/Cart")
public class CartServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String prodToAdd= request.getParameter("prodToAdd");
        String prodToRemove= request.getParameter("prodToRemove");
        String prodToUpdate= request.getParameter("prodToUpdate");

        HttpSession session= request.getSession();
        Carrello cart;
        if(session.getAttribute("Cart")==null) {
            cart= new Carrello();
            CarrelloDAO.doSave(cart);
            session.setAttribute("Cart", cart);
        } else
            cart= (Carrello) session.getAttribute("Cart");

        if(prodToAdd != null) {
            if(Checks.UUIDCheck(request, response, prodToAdd)) return;
            int quantitaAttuale= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), prodToAdd);
            Prodotto prod= ProdottoDAO.doRetrieveById(prodToAdd);
            if(prod != null) {
                int quantityToAdd= 1;
                if(request.getParameter("quantityToAdd") != null) {
                    try {
                        quantityToAdd= Integer.parseInt(request.getParameter("quantityToAdd"));
                    } catch (NumberFormatException ex) {
                        quantityToAdd= 0;
                    }
                }
                if(prod.getQuantita() <= 0) {
                    request.setAttribute("prodAddStatus", "productNotAvailable");
                    String url=request.getParameter("callerPage");
                    RequestDispatcher dispatcher=request.getRequestDispatcher(url);
                    dispatcher.forward(request,response);
                    return;
                }else if(quantitaAttuale == -1 && prod.getQuantita() >= 1) {
                    ProdottocarrelloDAO.doSave(cart.getId(), prodToAdd, Math.min(prod.getQuantita(), Math.min(quantityToAdd, 99)));
                } else if(prod.getQuantita() >= (quantitaAttuale + 1)) {
                    ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), prodToAdd, Math.min(prod.getQuantita(), Math.min(quantitaAttuale+quantityToAdd, 99)));
                }
            }

            List<String> prodList= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
            //Ricalcolo da zero il totale all'inserimento di un prodotto
            calcoloTotale(session, cart, prodList);
        } else if(prodToRemove != null) {
            if(Checks.UUIDCheck(request, response, prodToRemove)) return;
            ProdottocarrelloDAO.doDelete(cart.getId(), prodToRemove);

            List<String> prodList= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
            //Ricalcolo da zero il totale alla rimozione di un prodotto
            calcoloTotale(session, cart, prodList);
        } else if(prodToUpdate != null) {
            if(Checks.UUIDCheck(request, response, prodToUpdate)) return;
            int quantitaAttuale= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), prodToUpdate);
            if(quantitaAttuale == -1) {
                ProdottocarrelloDAO.doDelete(cart.getId(), prodToUpdate);
            } else {
                String updateType= request.getParameter("updateType");

                Prodotto prod= ProdottoDAO.doRetrieveById(prodToUpdate);
                if(prod != null) {
                    if(updateType!=null && updateType.equals("add")) {
                        if((quantitaAttuale+1) <= 99 && prod.getQuantita() > quantitaAttuale)
                            ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), prodToUpdate, quantitaAttuale+1);
                    }
                    else if(updateType!=null && updateType.equals("remove")) {
                        if((quantitaAttuale-1) <= 0)
                            ProdottocarrelloDAO.doDelete(cart.getId(), prodToUpdate);
                        else
                            ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), prodToUpdate, Math.min(quantitaAttuale-1, 99));
                    }
                    else {
                        int updatedQuantita;
                        try {
                            updatedQuantita= Integer.parseInt(request.getParameter("updateQuantity"));
                        } catch (NumberFormatException ex) {
                            updatedQuantita= quantitaAttuale;
                        }
                        if((updatedQuantita) <= 0)
                            ProdottocarrelloDAO.doDelete(cart.getId(), prodToUpdate);
                        else if(updatedQuantita <= 99)
                            ProdottocarrelloDAO.doUpdateQuantita(cart.getId(), prodToUpdate, Math.min(prod.getQuantita(), updatedQuantita));
                    }
                }
            }

            List<String> prodList= ProdottocarrelloDAO.doRetrieveProdotti(cart.getId());
            //Ricalcolo da zero il totale alla rimozione di un prodotto
            calcoloTotale(session, cart, prodList);
        }

        response.sendRedirect(request.getParameter("callerPage"));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Cart.jsp");
        dispatcher.forward(request, response);
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

            double prezzo=prod.getPrezzo();
            String discountId= ProdottoDAO.doRetrieveDiscountId(idProd);
            if(discountId!=null) {
                Sconto sc= ScontoDAO.doRetrieveById(discountId);
                if(sc!=null && sc.isStato() &&
                        Timestamp.valueOf(LocalDateTime.now()).compareTo(sc.getDataInizio()) >= 0 &&
                        Timestamp.valueOf(LocalDateTime.now()).compareTo(sc.getDataFine()) < 0) {
                    prezzo= prezzo * ((double)(100-sc.getPercentuale()) / 100);
                    prezzo= (double) Math.round(prezzo * 100) / 100;
                }
            }

            BigDecimal price= BigDecimal.valueOf(prezzo);
            cart.setTotale(
                    (BigDecimal.valueOf(cart.getTotale()).add(
                            price.multiply(BigDecimal.valueOf(quantita))
                    )).doubleValue());
        }

        session.setAttribute("Cart", cart);
        CarrelloDAO.doUpdate(cart);
    }
}
