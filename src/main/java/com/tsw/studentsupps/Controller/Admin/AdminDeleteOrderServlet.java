package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Ordine;
import com.tsw.studentsupps.Model.OrdineDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Admin/DeleteOrder")
public class AdminDeleteOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        String orderToDeleteId= request.getParameter("id");
        if(Checks.UUIDCheck(request, response, orderToDeleteId)) return;

        Ordine orderToDelete= OrdineDAO.doRetrieveById(orderToDeleteId);
        if(orderToDelete == null) {
            return;
        }

        OrdineDAO.doDelete(orderToDelete);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
