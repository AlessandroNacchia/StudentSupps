package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Prodotto;
import com.tsw.studentsupps.Model.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Shop")
public class ShopServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String filter= request.getParameter("filter");
        if(filter == null) {
            List<Prodotto> prodList= ProdottoDAO.doRetrieveAll();
            request.setAttribute("prodottiJSON", JSONprodList(prodList));
            RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Shop.jsp");
            dispatcher.forward(request, response);
            return;
        }

        List<Prodotto> prodListByCat= ProdottoDAO.doRetrieveByCategoria(filter);
        request.setAttribute("prodottiJSON", JSONprodList(prodListByCat));
        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Shop.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public String JSONprodList(List<Prodotto> prodList) {
        StringBuilder JSONprodList= new StringBuilder();
        JSONprodList.append("[");
        for(Prodotto p: prodList) {
            JSONprodList.append("{\"id\": \"").append(p.getId()).append("\", ");
            JSONprodList.append("\"nome\": \"").append(p.getNome()).append("\", ");
            JSONprodList.append("\"descrizione\": \"").append(p.getDescrizione()).append("\", ");
            JSONprodList.append("\"prezzo\": \"").append(p.getPrezzo()).append("\", ");
            JSONprodList.append("\"IVA\": \"").append(p.getIVA()).append("\", ");
            JSONprodList.append("\"quantita\": \"").append(p.getQuantita()).append("\"},");
        }
        JSONprodList.deleteCharAt(JSONprodList.length()-1);
        JSONprodList.append("]");

        return JSONprodList.toString();
    }
}
