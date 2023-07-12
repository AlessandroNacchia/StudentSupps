package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Admin/ViewOrder")
public class AdminViewOrderServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        String IdOrdine= request.getParameter("id");
        if(Checks.UUIDCheck(request, response,IdOrdine)) return;
        if(!OrdineDAO.doExistsById(IdOrdine)) {
            request.setAttribute("viewOrderStatus", "orderNotValid");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Admin/Orders");
            dispatcher.forward(request, response);
            return;
        }
        Ordine Order= OrdineDAO.doRetrieveById(IdOrdine);
        if (Order==null)
            throw new RuntimeException();
        Indirizzo Ind= IndirizzoDAO.doRetrieveById(OrdineDAO.doRetrieveIndById(IdOrdine));
        Metodopagamento Mp= MetodopagamentoDAO.doRetrieveById(OrdineDAO.doRetrieveMpById(IdOrdine));
        List<Prodottoordine> prodList= ProdottoordineDAO.doRetrieveProdotti(IdOrdine);
        request.setAttribute("ordine", Order);
        request.setAttribute("indirizzo", Ind);
        request.setAttribute("metodopagamento", Mp);
        request.setAttribute("listaprodotti", prodList);
        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Admin/ViewOrderDetails.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
