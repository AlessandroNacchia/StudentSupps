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
import java.util.UUID;

@WebServlet("/UpdateUser")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.userCheck(request, response)) return;

        String userToUpdateId= request.getParameter("id");
        if(!UUID.fromString(userToUpdateId).toString().equals(userToUpdateId)) {
            request.setAttribute("errorMessage", "UUID non valido");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        Utente userToUpdate= UtenteDAO.doRetrieveById(userToUpdateId);
        if(userToUpdate == null) {
            request.setAttribute("errorMessage", "Utente da aggiornare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        Utente callingUser= (Utente) request.getSession().getAttribute("Utente");
        if(!callingUser.isAdmin() && !userToUpdate.getId().equals(callingUser.getId()) ) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }

        userToUpdate.setNome(request.getParameter("name"));
        userToUpdate.setCognome(request.getParameter("lastname"));
        userToUpdate.setNumeroTel(request.getParameter("phone"));
        userToUpdate.setUsername(request.getParameter("username"));
        userToUpdate.setEmail(request.getParameter("email"));
        if(request.getParameter("isAdmin")==null)
            userToUpdate.setAdmin(false);
        else if(request.getParameter("isAdmin").equals("true"))
            userToUpdate.setAdmin(true);
        if(userToUpdate.getId().equals(callingUser.getId()))
            userToUpdate.setPasswordHash(request.getParameter("password"));

        UtenteDAO.doUpdate(userToUpdate);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
