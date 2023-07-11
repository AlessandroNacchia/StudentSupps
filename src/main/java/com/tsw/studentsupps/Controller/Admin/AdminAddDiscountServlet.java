package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/Admin/AddDiscount")
public class AdminAddDiscountServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Admin/AddDiscount.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        String name= request.getParameter("name");
        String percentage= request.getParameter("percentage");
        String status= request.getParameter("status");
        String startDateString= request.getParameter("startDate");
        String endDateString= request.getParameter("endDate");

        if(startDateString.chars().filter(ch -> ch==':').count() == 1)
            startDateString+=":00";
        if(endDateString.chars().filter(ch -> ch==':').count() == 1)
            endDateString+=":00";

        String nameRGX= "^[\\w\\-. ]{2,50}$";
        String percentageRGX= "^[+]?[0-9]{1,2}$|^[+]?100$";
        String datetimeRGX= "^(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})$";

        if(!name.matches(nameRGX)) {
            request.setAttribute("addDiscountStatus", "nameWrongPattern");
            doGet(request, response);
            return;
        }
        if(!percentage.matches(percentageRGX)) {
            request.setAttribute("addDiscountStatus", "percentageWrongPattern");
            doGet(request, response);
            return;
        }
        if(!startDateString.matches(datetimeRGX)) {
            request.setAttribute("addDiscountStatus", "startDateWrongPattern");
            doGet(request, response);
            return;
        }
        if(!endDateString.matches(datetimeRGX)) {
            request.setAttribute("addDiscountStatus", "endDateWrongPattern");
            doGet(request, response);
            return;
        }

        if(ScontoDAO.doExistsByName(request.getParameter("name"))) {
            request.setAttribute("addDiscountStatus", "nameTaken");
            doGet(request, response);
            return;
        }

        Sconto s= new Sconto();
        s.setNome(name);
        s.setPercentuale(Short.parseShort(percentage));
        if(status==null)
            s.setStato(false);
        else if(status.equals("true"))
            s.setStato(true);

        s.setDataInizio(Timestamp.valueOf(startDateString.replace("T", " ")));
        s.setDataFine(Timestamp.valueOf(endDateString.replace("T", " ")));

        ScontoDAO.doSave(s);

        request.setAttribute("returnPage", "/Admin/Discounts");
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }
}
