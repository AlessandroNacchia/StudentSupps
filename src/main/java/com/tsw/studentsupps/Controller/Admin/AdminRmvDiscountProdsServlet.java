package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Admin/RmvDiscountProds")
public class AdminRmvDiscountProdsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        String discountToDeleteId= request.getParameter("id");
        if(Checks.UUIDCheck(request, response, discountToDeleteId)) return;

        if(ProdottoDAO.doExistsByDiscount(discountToDeleteId))
            ProdottoDAO.doRemoveDiscountByDiscountId(discountToDeleteId);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
