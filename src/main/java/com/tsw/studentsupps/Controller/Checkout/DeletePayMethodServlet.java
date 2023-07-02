package com.tsw.studentsupps.Controller.Checkout;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Metodopagamento;
import com.tsw.studentsupps.Model.MetodopagamentoDAO;
import com.tsw.studentsupps.Model.OrdineDAO;
import com.tsw.studentsupps.Model.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Cart/Checkout/DeletePayMethod")
public class DeletePayMethodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.userCheck(request, response)) return;
        if(Checks.UUIDCheck(request, response)) return;

        Metodopagamento mpToDelete= MetodopagamentoDAO.doRetrieveById(request.getParameter("id"));
        if(mpToDelete == null) {
            request.setAttribute("errorMessage", "Metodo di pagamento da cancellare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        String userId= ((Utente) request.getSession().getAttribute("Utente")).getId();
        if(OrdineDAO.doRetrieveByUserId(userId).isEmpty())
            MetodopagamentoDAO.doDelete(mpToDelete);
        else
            MetodopagamentoDAO.doRemoveUserId(mpToDelete);

        response.sendRedirect(request.getContextPath() + "/Cart/Checkout");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
