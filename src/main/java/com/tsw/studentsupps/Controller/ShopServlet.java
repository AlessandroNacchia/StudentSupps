package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Prodotto;
import com.tsw.studentsupps.Model.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/Shop")
public class ShopServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String filter= request.getParameter("filter");
        if(filter == null) {
            List<Prodotto> prodottiCategoria= ProdottoDAO.doRetrieveAll();
            request.setAttribute("prodotti", prodottiCategoria);
            RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Shop.jsp");
            dispatcher.forward(request, response);
        }
        List<Prodotto> prodottiCategoria= ProdottoDAO.doRetrieveByCategoria(filter);
        request.setAttribute("prodotti", prodottiCategoria);
        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Shop.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
