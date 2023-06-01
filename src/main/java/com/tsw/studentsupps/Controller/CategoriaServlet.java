package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Categoria;
import com.tsw.studentsupps.Model.CategoriaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Categoria cat= null;
        try {
            cat= CategoriaDAO.doRetrieveById(request.getParameter("selectedCat"));
        } catch (NumberFormatException ex) {
            request.setAttribute("errorMessage", ex.toString());
            RequestDispatcher dispatcher= request.getRequestDispatcher("WEB-INF/results/error.jsp");
            dispatcher.forward(request, response);
        }

        //List<Prodotto> prodList= ProdottoDAO.doRetrieveByCategoria(cat != null ? cat.getId() : "null");
        request.setAttribute("selectedCat", cat);
        //request.setAttribute("prodList", prodList);
        RequestDispatcher dispatcher= request.getRequestDispatcher("pages/categoria.jsp");
        dispatcher.forward(request, response);
    }
}
