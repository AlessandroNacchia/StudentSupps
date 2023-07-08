package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.CarrelloDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="MyInit", urlPatterns="/MyInit", loadOnStartup=0)
public class InitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        //Cancella i carrelli non collegati ad account non aggiornati da più di un giorno (86400000)
        CarrelloDAO.doDeleteUnlinkedCarts(0);

        //PATH immagini prodotti non assoluta ma tramite environment variable in modo da poterlo usare su pc diversi anche avendo path diversi.
        //Andrà modificata e spostata la cartella delle immagini prodotti quando finito il sito.
        String prodImageFolder= System.getenv("STUDENT_SUPPS") + getServletContext().getInitParameter("uploadImageProduct.location");
        getServletContext().setAttribute("prodImageFolder", prodImageFolder);

        String delProdImageFolder= System.getenv("STUDENT_SUPPS") + getServletContext().getInitParameter("delUploadImageProduct.location");
        getServletContext().setAttribute("delProdImageFolder", delProdImageFolder);

        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}