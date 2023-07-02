package com.tsw.studentsupps.Controller.Checkout;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Metodopagamento;
import com.tsw.studentsupps.Model.MetodopagamentoDAO;
import com.tsw.studentsupps.Model.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/Cart/Checkout/AddPayMethod")
public class AddPayMethodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Checks.userCheck(request, response)) return;

        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Checkout/AddPayMethod.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Checks.userCheck(request, response)) return;

        Metodopagamento mp= new Metodopagamento();
        mp.setProvider(request.getParameter("provider"));
        mp.setNumeroHash(request.getParameter("cardNumber"));

        String expiryDate= request.getParameter("expiryDate");
        int month= Integer.parseInt(expiryDate.substring(0,2));
        int year= Integer.parseInt(expiryDate.substring(3,5));

        LocalDate date= LocalDate.of(2000+year, month, 1);
        date= date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));

        mp.setDataScadenza(Date.valueOf(date));

        MetodopagamentoDAO.doSave(mp, ((Utente) request.getSession().getAttribute("Utente")).getId());

        response.sendRedirect(request.getContextPath() + "/Cart/Checkout");
    }
}
