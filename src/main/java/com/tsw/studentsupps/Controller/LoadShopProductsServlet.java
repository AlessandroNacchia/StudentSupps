package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Prodotto;
import com.tsw.studentsupps.Model.ProdottoDAO;
import com.tsw.studentsupps.Model.Sconto;
import com.tsw.studentsupps.Model.ScontoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/LoadShopProducts")
public class LoadShopProductsServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String filter= request.getParameter("filter");
        String search= request.getParameter("search");
        JSONParser parser= new JSONParser();
        JSONArray jsonArray;

        response.setContentType("application/json");
        List<Prodotto> prodList;
        if(filter.equals("") && search.equals(""))
            prodList= ProdottoDAO.doRetrieveAll(true);
        else if(!filter.equals("") && !search.equals(""))
            prodList= ProdottoDAO.doRetrieveBySearchName_Cat(search, filter, true);
        else if(!filter.equals(""))
            prodList= ProdottoDAO.doRetrieveByCategoria(filter, true);
        else
            prodList= ProdottoDAO.doRetrieveBySearchName(search);

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
            String discountId= ProdottoDAO.doRetrieveDiscountId(p.getId());
            double prezzo= p.getPrezzo();
            if(discountId!=null) {
                Sconto sc= ScontoDAO.doRetrieveById(discountId);
                if(sc!=null && sc.isStato() &&
                        Timestamp.valueOf(LocalDateTime.now()).compareTo(sc.getDataInizio()) >= 0 &&
                        Timestamp.valueOf(LocalDateTime.now()).compareTo(sc.getDataFine()) < 0) {
                    prezzo= prezzo * ((double)(100-sc.getPercentuale()) / 100);
                    prezzo= (double) Math.round(prezzo * 100) / 100;
                }
            }

            JSONprodList.append("{\"id\": \"").append(p.getId()).append("\", ");
            JSONprodList.append("\"nome\": \"").append(p.getNome()).append("\", ");
            JSONprodList.append("\"descrizione\": \"").append(p.getDescrizione()).append("\", ");
            JSONprodList.append("\"prezzo\": \"").append(p.getPrezzo()).append("\", ");
            JSONprodList.append("\"prezzoScontato\": \"").append(prezzo).append("\", ");
            JSONprodList.append("\"IVA\": \"").append(p.getIVA()).append("\", ");
            JSONprodList.append("\"quantita\": \"").append(p.getQuantita()).append("\"},");
        }
        JSONprodList.deleteCharAt(JSONprodList.length()-1);
        JSONprodList.append("]");

        return JSONprodList.toString();
    }
}
