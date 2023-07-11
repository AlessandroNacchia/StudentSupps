package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Sconto;
import com.tsw.studentsupps.Model.ScontoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Admin/Discounts")
public class AdminDiscountsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        List<Sconto> discountList= ScontoDAO.doRetrieveAll();
        request.setAttribute("discountList", discountList);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/Admin/ViewDiscounts.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
