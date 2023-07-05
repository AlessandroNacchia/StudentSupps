package com.tsw.studentsupps.Controller.utils;

import com.tsw.studentsupps.Model.Utente;
import com.tsw.studentsupps.Model.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public class Checks {
    public static boolean userCheck(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Utente user= (Utente) request.getSession().getAttribute("Utente");
        if(user == null) {
            response.sendRedirect(request.getContextPath()+'/');
            return true;
        }
        if(!user.equals(UtenteDAO.doRetrieveById(user.getId()))) {
            request.setAttribute("errorMessage", "Dati Utente Session/DB non coincidenti");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return true;
        }
        return false;
    }

    public static boolean adminCheck(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Utente user= (Utente) request.getSession().getAttribute("Utente");
        if(user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath()+'/');
            return true;
        }
        if(!user.equals(UtenteDAO.doRetrieveById(user.getId()))) {
            request.setAttribute("errorMessage", "Dati Utente Session/DB non coincidenti");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return true;
        }
        return false;
    }

    public static boolean UUIDCheck(HttpServletRequest request, HttpServletResponse response, String id)
            throws IOException, ServletException {
        try {
            if(id == null || !UUID.fromString(id).toString().equals(id)) {
                request.setAttribute("errorMessage", "UUID non valido");
                RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request,response);
                return true;
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("errorMessage", "UUID non valido");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return true;
        }

        return false;
    }
}
