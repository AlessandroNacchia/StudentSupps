package com.tsw.studentsupps.Controller.Account;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Ordine;
import com.tsw.studentsupps.Model.OrdineDAO;
import com.tsw.studentsupps.Model.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/Account/Orders")
public class OrdersServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session= request.getSession();
        Utente u= (Utente)   session.getAttribute("Utente");
        if(u == null) {
            response.sendRedirect(request.getContextPath()+"/Login");
            return;
        }
        if(Checks.userCheck(request, response)) return;

        List<Ordine> OrderList= OrdineDAO.doRetrieveByUserId(u.getId());
        request.setAttribute("ordini", OrderList);
        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Account/Orders.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
