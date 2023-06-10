package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.CarrelloDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name="MyInit", urlPatterns="/MyInit", loadOnStartup=0)
public class InitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        //Cancella i carrelli non collegati ad account non aggiornati da più di un giorno (86400000)
        CarrelloDAO.doDeleteUnlinkedCarts(0);
        super.init();
    }
}