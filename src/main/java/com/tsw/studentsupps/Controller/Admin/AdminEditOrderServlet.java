package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Ordine;
import com.tsw.studentsupps.Model.OrdineDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet("/Admin/EditOrder")
public class AdminEditOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request,response)) return;

        String id= request.getParameter("id");
        if(id == null || !UUID.fromString(id).toString().equals(id)) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }

        Ordine o= OrdineDAO.doRetrieveById(id);
        if(o == null) {
            request.setAttribute("errorMessage", "Ordine da modificare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        request.setAttribute("orderToEdit", o);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/Admin/EditOrder.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        String orderToUpdateId= request.getParameter("id");
        if(Checks.UUIDCheck(request, response, orderToUpdateId)) return;

        Ordine orderToUpdate= OrdineDAO.doRetrieveById(orderToUpdateId);
        if(orderToUpdate == null) {
            request.setAttribute("errorMessage", "Ordine da aggiornare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        String status= request.getParameter("status");
        String deliveryDateString= request.getParameter("deliveryDate");


        if(deliveryDateString.chars().filter(ch -> ch==':').count() == 1)
            deliveryDateString+= ":00";

        String datetimeRGX= "^(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})$";

        if(!status.equals("processing") && !status.equals("shipped") && !status.equals("delivered")) {
            request.setAttribute("editOrderStatus", "statusNotValid");
            doGet(request, response);
            return;
        }
        if(!deliveryDateString.matches(datetimeRGX)) {
            request.setAttribute("editOrderStatus", "deliveryDateNotValid");
            doGet(request, response);
            return;
        }

        orderToUpdate.setStato(status);
        orderToUpdate.setDataConsegna(Timestamp.valueOf(deliveryDateString.replace("T", " ")));

        OrdineDAO.doUpdate(orderToUpdate);

        request.setAttribute("returnPage", "/Admin/Orders");
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }
}
