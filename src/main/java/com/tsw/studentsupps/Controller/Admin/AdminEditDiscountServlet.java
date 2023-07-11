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
import java.util.UUID;

@WebServlet("/Admin/EditDiscount")
public class AdminEditDiscountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request,response)) return;

        String id= request.getParameter("id");
        if(id == null || !UUID.fromString(id).toString().equals(id)) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }

        Sconto s= ScontoDAO.doRetrieveById(id);
        if(s == null) {
            request.setAttribute("errorMessage", "Sconto da modificare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        request.setAttribute("discountToEdit", s);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/Admin/EditDiscount.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        String discountToUpdateId= request.getParameter("id");
        if(!UUID.fromString(discountToUpdateId).toString().equals(discountToUpdateId)) {
            request.setAttribute("errorMessage", "UUID non valido");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        Sconto discountToUpdate= ScontoDAO.doRetrieveById(discountToUpdateId);
        if(discountToUpdate == null) {
            request.setAttribute("errorMessage", "Sconto da aggiornare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        String oldName= discountToUpdate.getNome();
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

        if(!oldName.equals(name) && ScontoDAO.doExistsByName(request.getParameter("name"))) {
            request.setAttribute("addDiscountStatus", "nameTaken");
            doGet(request, response);
            return;
        }

        discountToUpdate.setNome(name);
        discountToUpdate.setPercentuale(Short.parseShort(percentage));
        if(status==null)
            discountToUpdate.setStato(false);
        else if(status.equals("true"))
            discountToUpdate.setStato(true);
        discountToUpdate.setDataInizio(Timestamp.valueOf(startDateString.replace("T", " ")));
        discountToUpdate.setDataFine(Timestamp.valueOf(endDateString.replace("T", " ")));

        ScontoDAO.doUpdate(discountToUpdate);

        request.setAttribute("returnPage", "/Admin/Discounts");
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }
}
