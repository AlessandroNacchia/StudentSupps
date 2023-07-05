package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Carrello;
import com.tsw.studentsupps.Model.CarrelloDAO;
import com.tsw.studentsupps.Model.Utente;
import com.tsw.studentsupps.Model.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Signup")
public class SignupServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("Account");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name= request.getParameter("nameS");
        String lastname= request.getParameter("lastnameS");
        String phone= request.getParameter("phoneS");
        String username= request.getParameter("usernameS");
        String email= request.getParameter("emailS");
        String password= request.getParameter("passwordS");

        String nameRGX="^[a-zA-Z.\\s]{2,30}$";
        String phoneRGX="^([+]?[(]?[0-9]{1,3}[)]?[-\\s])?([(]?[0-9]{3}[)]?[-\\s]?)?([0-9][-\\s]?){3,10}[0-9]$";
        String usernameRGX= "^[A-Za-z][A-Za-z0-9_]{4,29}$";
        String emailRGX= "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
        String passwordRGX= "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\\[{}\\]:;',?*~$^\\-+=<>]).{8,30}$";

        if(!name.matches(nameRGX) || !lastname.matches(nameRGX)) {
            request.setAttribute("signupStatus", "nameWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
            return;
        }
        if(!phone.equals("") && !phone.matches(phoneRGX)) {
            request.setAttribute("signupStatus", "phoneWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
            return;
        }
        if(!username.matches(usernameRGX)) {
            request.setAttribute("signupStatus", "usernameWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
            return;
        }
        if(!email.matches(emailRGX)) {
            request.setAttribute("signupStatus", "emailWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
            return;
        }
        if(!password.matches(passwordRGX)) {
            request.setAttribute("signupStatus", "passwordWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
            return;
        }

        if(UtenteDAO.doExistsByUsername(username)) {
            request.setAttribute("signupStatus", "usernameTaken");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
            return;
        }
        if(UtenteDAO.doExistsByUsername(email)) {
            request.setAttribute("signupStatus", "emailTaken");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
            return;
        }
        if(!password.equals(request.getParameter("passwordS2"))) {
            request.setAttribute("signupStatus", "passwordsNotEqual");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
            return;
        }

        Utente u= new Utente();
        u.setNome(request.getParameter("nameS"));
        u.setCognome(request.getParameter("lastnameS"));
        u.setNumeroTel(request.getParameter("phoneS"));
        u.setUsername(request.getParameter("usernameS"));
        u.setEmail(request.getParameter("emailS"));
        u.setPasswordHash(request.getParameter("passwordS"));

        UtenteDAO.doSave(u);
        HttpSession session= request.getSession();
        Carrello cart;
        if(session.getAttribute("Cart")==null) {
            cart= new Carrello();
            CarrelloDAO.doSave(cart);
            session.setAttribute("Cart", cart);
        } else
            cart= (Carrello) session.getAttribute("Cart");
        UtenteDAO.doUpdateIdCart(u, cart);

        u.setPasswordHash("");
        request.getSession().setAttribute("Utente", u);
        response.sendRedirect(request.getContextPath()+'/');
    }
}
