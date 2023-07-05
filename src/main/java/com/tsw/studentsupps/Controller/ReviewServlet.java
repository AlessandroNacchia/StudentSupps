package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Shop/Prodotto/Review")
public class ReviewServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath()+'/');
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        if (Checks.userCheck(req, resp)) return;

        Prodotto prod= ProdottoDAO.doRetrieveById(req.getParameter("prodId"));
        String descrizione=req.getParameter("description");
        String voto=req.getParameter("rating");
        String autore=req.getParameter("author");

        String descrizioneRGX="^.{2,1000}$";
        String votoRGX="^[1-5]$";

        if (!autore.equals(((Utente)req.getSession().getAttribute("Utente")).getUsername())){
            req.setAttribute("reviewStatus","autoreWrongPattern");
            RequestDispatcher dispatcher =req.getRequestDispatcher("/Shop/Prodotto?prodName=" + prod.getNome());
            dispatcher.forward(req,resp);
            return;
        }
        if (!descrizione.matches(descrizioneRGX)){
            req.setAttribute("reviewStatus","descrizioneWrongPattern");
            RequestDispatcher dispatcher =req.getRequestDispatcher("/Shop/Prodotto?prodName=" + prod.getNome());
            dispatcher.forward(req,resp);
            return;
        }
        if (!voto.matches(votoRGX)){
            req.setAttribute("reviewStatus","votoWrongPattern");
            RequestDispatcher dispatcher =req.getRequestDispatcher("/Shop/Prodotto?prodName=" + prod.getNome());
            dispatcher.forward(req,resp);
            return;
        }

        Recensione rec= new Recensione();
        rec.setAutore(autore);
        rec.setVoto(Short.parseShort(voto));
        rec.setDescrizione(descrizione);


        RecensioneDAO.doSave(rec,prod.getId());

        resp.sendRedirect(req.getContextPath()+"/Shop/Prodotto?prodName=" + prod.getNome());
    }
}
