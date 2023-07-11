package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.ProdottoDAO;
import com.tsw.studentsupps.Model.Sconto;
import com.tsw.studentsupps.Model.ScontoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Admin/DeleteDiscount")
public class AdminDeleteDiscountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        String discountToDeleteId= request.getParameter("id");
        if(Checks.UUIDCheck(request, response, discountToDeleteId)) return;

        Sconto discountToDelete= ScontoDAO.doRetrieveById(discountToDeleteId);
        if(discountToDelete == null) {
            return;
        }

        if(ProdottoDAO.doExistsByDiscount(discountToDeleteId))
            ProdottoDAO.doRemoveDiscountByDiscountId(discountToDeleteId);
        ScontoDAO.doDelete(discountToDelete);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
