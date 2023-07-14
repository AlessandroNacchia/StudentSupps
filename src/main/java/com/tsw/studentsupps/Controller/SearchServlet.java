package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Prodotto;
import com.tsw.studentsupps.Model.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Search")
public class SearchServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Shop.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search= request.getParameter("search");
        JSONParser parser= new JSONParser();
        JSONArray jsonArray;

        response.setContentType("application/json");
        List<Prodotto> prodList= new ArrayList<>();
        if(search!= null && !search.equals(""))
            prodList= ProdottoDAO.doRetrieveBySearchName(search, 5);

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

    public String JSONprodList(List<Prodotto> prodList) {
        StringBuilder JSONprodList= new StringBuilder();
        JSONprodList.append("[");
        for(Prodotto p: prodList) {
            JSONprodList.append("{\"nome\": \"").append(p.getNome()).append("\"}, ");
        }
        JSONprodList.deleteCharAt(JSONprodList.length()-1);
        JSONprodList.append("]");

        return JSONprodList.toString();
    }
}
