package com.tsw.studentsupps.Controller;

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

@WebServlet("/DeleteUser")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.userCheck(request, response)) return;
        if(Checks.UUIDCheck(request, response, request.getParameter("id"))) return;

        Utente userToDelete= UtenteDAO.doRetrieveById(request.getParameter("id"));
        if(userToDelete == null) {
            request.setAttribute("errorMessage", "Utente da cancellare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        Utente callingUser= (Utente) request.getSession().getAttribute("Utente");
        if(!callingUser.isAdmin() && !userToDelete.getId().equals(callingUser.getId()) ) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }

        UtenteDAO.doDelete(userToDelete);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
