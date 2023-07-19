package com.tsw.studentsupps.Controller.Checkout;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Indirizzo;
import com.tsw.studentsupps.Model.IndirizzoDAO;
import com.tsw.studentsupps.Model.OrdineDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Cart/Checkout/DeleteAddress")
public class DeleteAddressServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + '/');
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.userCheck(request, response)) return;
        if(Checks.UUIDCheck(request, response, request.getParameter("id"))) return;

        Indirizzo indToDelete= IndirizzoDAO.doRetrieveById(request.getParameter("id"));
        if(indToDelete == null) {
            return;
        }

        if(OrdineDAO.doExistsByInd(indToDelete.getId()))
            IndirizzoDAO.doRemoveUserId(indToDelete);
        else
            IndirizzoDAO.doDelete(indToDelete);
    }
}
