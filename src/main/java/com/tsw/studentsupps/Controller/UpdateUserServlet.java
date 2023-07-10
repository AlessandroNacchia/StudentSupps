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

@WebServlet("/UpdateUser")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.userCheck(request, response)) return;
        if(Checks.UUIDCheck(request, response, request.getParameter("id"))) return;

        Utente userToUpdate= UtenteDAO.doRetrieveById(request.getParameter("id"));
        if(userToUpdate == null) {
            request.setAttribute("errorMessage", "Utente da aggiornare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        Utente callingUser= (Utente) request.getSession().getAttribute("Utente");

        String updateStatus_returnPage;
        boolean isUsersEqual= false;
        if(userToUpdate.getId().equals(callingUser.getId())) {
            isUsersEqual= true;
            updateStatus_returnPage= "/Account/EditProfile";
        }
        else
            updateStatus_returnPage= "/Admin/EditUser";

        if(!callingUser.isAdmin() && !isUsersEqual) {
            response.sendRedirect(request.getContextPath()+'/');
            return;
        }

        String oldUsername= userToUpdate.getUsername();
        String oldEmail= userToUpdate.getEmail();
        String name= request.getParameter("name");
        String lastname= request.getParameter("lastname");
        String phone= request.getParameter("phone");
        String username= request.getParameter("username");
        String email= request.getParameter("email");
        String password= request.getParameter("password");

        String nameRGX="^[a-zA-Z.\\s]{2,30}$";
        String phoneRGX="^([+]?[(]?[0-9]{1,3}[)]?[-\\s])?([(]?[0-9]{3}[)]?[-\\s]?)?([0-9][-\\s]?){3,10}[0-9]$";
        String usernameRGX= "^[A-Za-z][A-Za-z0-9_]{4,29}$";
        String emailRGX= "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
        String passwordRGX= "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\\[{}\\]:;',?*~$^\\-+=<>]).{8,30}$";

        if(!name.matches(nameRGX) || !lastname.matches(nameRGX)) {
            request.setAttribute("updateStatus", "nameWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher(updateStatus_returnPage);
            dispatcher.forward(request, response);
            return;
        }
        if(!phone.equals("") && !phone.matches(phoneRGX)) {
            request.setAttribute("updateStatus", "phoneWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher(updateStatus_returnPage);
            dispatcher.forward(request, response);
            return;
        }
        if(!username.matches(usernameRGX)) {
            request.setAttribute("updateStatus", "usernameWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher(updateStatus_returnPage);
            dispatcher.forward(request, response);
            return;
        }
        if(!email.matches(emailRGX)) {
            request.setAttribute("updateStatus", "emailWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher(updateStatus_returnPage);
            dispatcher.forward(request, response);
            return;
        }
        if(isUsersEqual && !password.equals(request.getParameter("password2"))) {
            request.setAttribute("updateStatus", "passwordsNotEqual");
            RequestDispatcher dispatcher= request.getRequestDispatcher(updateStatus_returnPage);
            dispatcher.forward(request, response);
            return;
        }
        if(isUsersEqual && !password.equals("") && !password.matches(passwordRGX)) {
            request.setAttribute("updateStatus", "passwordWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher(updateStatus_returnPage);
            dispatcher.forward(request, response);
            return;
        }

        if(!oldUsername.equals(username) && UtenteDAO.doExistsByUsername(username)) {
            request.setAttribute("updateStatus", "usernameTaken");
            RequestDispatcher dispatcher= request.getRequestDispatcher(updateStatus_returnPage);
            dispatcher.forward(request, response);
            return;
        }
        if(!oldEmail.equals(email) && UtenteDAO.doExistsByUsername(email)) {
            request.setAttribute("updateStatus", "emailTaken");
            RequestDispatcher dispatcher= request.getRequestDispatcher(updateStatus_returnPage);
            dispatcher.forward(request, response);
            return;
        }

        userToUpdate.setNome(name);
        userToUpdate.setCognome(lastname);
        userToUpdate.setNumeroTel(phone);
        userToUpdate.setUsername(username);
        userToUpdate.setEmail(email);

        String isAdmin= request.getParameter("isAdmin");
        if(!isUsersEqual && isAdmin==null)
            userToUpdate.setAdmin(false);
        else if(isAdmin!=null && isAdmin.equals("true"))
            userToUpdate.setAdmin(true);
        if(isUsersEqual)
            userToUpdate.setPasswordHash(password);

        UtenteDAO.doUpdate(userToUpdate);

        if(!isUsersEqual)
            request.setAttribute("returnPage", "/Admin/Users");
        else {
            userToUpdate.setPasswordAlreadyHashed("");
            request.getSession().setAttribute("Utente", userToUpdate);
        }
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
