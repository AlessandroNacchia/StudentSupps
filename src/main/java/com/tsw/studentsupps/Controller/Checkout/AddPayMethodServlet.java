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

        String provider= request.getParameter("provider");
        String cardNumber= request.getParameter("cardNumber");
        String expiryDate= request.getParameter("expiryDate");

        String providerRGX= "^[a-zA-Z.\\s]{2,30}$";
        String cardNumberRGX= "^\\b(\\d{4}\\s?\\d{4}\\s?\\d{4}\\s?\\d{4}$)\\b$";
        String expiryDateRGX= "^(0[1-9]|1[0-2])/([0-9]{2})$";

        if(!provider.matches(providerRGX)) {
            request.setAttribute("addPayMethodStatus", "providerWrongPattern");
            doGet(request, response);
            return;
        }
        if(!cardNumber.matches(cardNumberRGX)) {
            request.setAttribute("addPayMethodStatus", "cardNumberWrongPattern");
            doGet(request, response);
            return;
        }
        if(!expiryDate.matches(expiryDateRGX)) {
            request.setAttribute("addPayMethodStatus", "expiryDateWrongPattern");
            doGet(request, response);
            return;
        }

        Metodopagamento mp= new Metodopagamento();
        mp.setProvider(provider);
        mp.setNumeroHash(cardNumber);

        int month= Integer.parseInt(expiryDate.substring(0,2));
        int year= Integer.parseInt(expiryDate.substring(3,5));

        LocalDate date= LocalDate.of(2000+year, month, 1);
        date= date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));

        mp.setDataScadenza(Date.valueOf(date));

        MetodopagamentoDAO.doSave(mp, ((Utente) request.getSession().getAttribute("Utente")).getId());

        response.sendRedirect(request.getContextPath() + "/Cart/Checkout");
    }
}
