package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Prodotto;
import com.tsw.studentsupps.Model.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

@WebServlet("/LoadShopProducts")
public class LoadShopProductsServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String filter= request.getParameter("filter");
        JSONParser parser= new JSONParser();
        JSONArray jsonArray;

        response.setContentType("application/json");
        List<Prodotto> prodList;
        if(filter.equals(""))
            prodList= ProdottoDAO.doRetrieveAll(true);
        else
            prodList= ProdottoDAO.doRetrieveByCategoria(filter, true);

        if(prodList.size() == 0) {
            response.getWriter().print("[]");
            return;
        }
        try {
            jsonArray= (JSONArray) parser.parse(JSONprodList(prodList));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        response.getWriter().print(jsonArray);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
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
