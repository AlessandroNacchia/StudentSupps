package com.tsw.studentsupps.Controller.Account;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Account/EditProfile")
public class EditProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session= request.getSession();
        Utente u= (Utente) session.getAttribute("Utente");
        if(u == null) {
            response.sendRedirect(request.getContextPath()+"/Login");
            return;
        }
        if(Checks.userCheck(request,response)) return;

        RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/Account/EditProfile.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
