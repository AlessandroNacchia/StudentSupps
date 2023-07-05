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

        String country= request.getParameter("country");
        String province= request.getParameter("province");
        String city= request.getParameter("city");
        String cap= request.getParameter("cap");
        String street= request.getParameter("street");
        String phone= request.getParameter("phone");

        String nameRGX= "^[a-zA-Z0-9\\-\\s]{2,60}$";
        String capRGX= "^[A-Z0-9\\-\\s]{2,10}$";
        String streetRGX= "^.{2,100}$";
        String phoneRGX= "^([+]?[(]?[0-9]{1,3}[)]?[-\\s])?([(]?[0-9]{3}[)]?[-\\s]?)?([0-9][-\\s]?){3,10}[0-9]$";

        if(!country.matches(nameRGX)) {
            request.setAttribute("addAddressStatus", "countryWrongPattern");
            doGet(request, response);
            return;
        }
        if(!province.matches(nameRGX)) {
            request.setAttribute("addAddressStatus", "provinceWrongPattern");
            doGet(request, response);
            return;
        }
        if(!city.matches(nameRGX)) {
            request.setAttribute("addAddressStatus", "cityWrongPattern");
            doGet(request, response);
            return;
        }
        if(!cap.matches(capRGX)) {
            request.setAttribute("addAddressStatus", "capWrongPattern");
            doGet(request, response);
            return;
        }
        if(!street.matches(streetRGX)) {
            request.setAttribute("addAddressStatus", "streetWrongPattern");
            doGet(request, response);
            return;
        }
        if(!phone.matches(phoneRGX)) {
            request.setAttribute("addAddressStatus", "phoneWrongPattern");
            doGet(request, response);
            return;
        }

        Indirizzo ind= new Indirizzo();
        ind.setNazione(country);
        ind.setProvincia(province);
        ind.setCitta(city);
        ind.setCAP(cap);
        ind.setVia(street);
        ind.setNumeroTel(phone);

        IndirizzoDAO.doSave(ind, ((Utente) request.getSession().getAttribute("Utente")).getId());

        response.sendRedirect(request.getContextPath() + "/Cart/Checkout");
    }
}
