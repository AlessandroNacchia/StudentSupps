package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Utente;
import com.tsw.studentsupps.Model.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/Admin/EditUser")
public class AdminEditUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request,response)) return;

        String id= request.getParameter("id");
        if(id == null || !UUID.fromString(id).toString().equals(id)) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }

        Utente u= UtenteDAO.doRetrieveById(id);
        if(u == null) {
            request.setAttribute("errorMessage", "Utente da modificare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        if(((Utente) request.getSession().getAttribute("Utente")).getId().equals(u.getId())) {
            request.setAttribute("errorMessage", "Non puoi modificare i parametri del tuo profilo");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        request.setAttribute("userToEdit", u);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/Admin/EditUser.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
