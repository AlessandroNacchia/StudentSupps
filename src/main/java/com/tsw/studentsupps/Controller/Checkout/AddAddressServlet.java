package com.tsw.studentsupps.Controller.Checkout;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Indirizzo;
import com.tsw.studentsupps.Model.IndirizzoDAO;
import com.tsw.studentsupps.Model.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Cart/Checkout/AddAddress")
public class AddAddressServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Checks.userCheck(request, response)) return;

        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Checkout/AddAddress.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Checks.userCheck(request, response)) return;

        Indirizzo ind= new Indirizzo();
        ind.setNazione(request.getParameter("country"));
        ind.setProvincia(request.getParameter("province"));
        ind.setCitta(request.getParameter("city"));
        ind.setCAP(request.getParameter("cap"));
        ind.setVia(request.getParameter("street"));
        ind.setNumeroTel(request.getParameter("phone"));

        IndirizzoDAO.doSave(ind, ((Utente) request.getSession().getAttribute("Utente")).getId());

        response.sendRedirect(request.getContextPath() + "/Cart/Checkout");
    }
}
