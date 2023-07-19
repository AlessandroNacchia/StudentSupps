package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.*;
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
            return;
        }

        Utente callingUser= (Utente) request.getSession().getAttribute("Utente");
        if(!callingUser.isAdmin() && !userToDelete.getId().equals(callingUser.getId()) ) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }

        for(Metodopagamento mpToDelete: MetodopagamentoDAO.doRetrieveByUserId(userToDelete.getId())) {
            if(OrdineDAO.doExistsByMp(mpToDelete.getId()))
                MetodopagamentoDAO.doRemoveUserId(mpToDelete);
            else
                MetodopagamentoDAO.doDelete(mpToDelete);
        }
        for(Indirizzo indToDelete: IndirizzoDAO.doRetrieveByUserId(userToDelete.getId())) {
            if(OrdineDAO.doExistsByInd(indToDelete.getId()))
                IndirizzoDAO.doRemoveUserId(indToDelete);
            else
                IndirizzoDAO.doDelete(indToDelete);
        }

        OrdineDAO.doRemoveAllUserId(userToDelete.getId());
        RecensioneDAO.doRemoveAllUsername(userToDelete.getUsername());
        UtenteDAO.doDelete(userToDelete);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
