package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URLDecoder;

@WebServlet("/Shop/Prodotto/Review")
public class ReviewServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath()+'/');
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(102);
        if(Checks.userCheck(req, resp)) {
            resp.setStatus(403);
            return;
        }

        JSONObject recensione;
        try {
            JSONParser parser= new JSONParser();
            String recens= req.getParameter("recensione");
            recens= recens.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            recens= recens.replaceAll("\\+", "%2B");
            recensione= (JSONObject) parser.parse(URLDecoder.decode(recens, "UTF-8"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        resp.setContentType("text/plain");
        if(Checks.UUIDCheck(req, resp, (String) recensione.get("prodId"))) {
            resp.getWriter().write("invalidProdUUID");
            resp.setStatus(400);
            return;
        }
        Prodotto prod= ProdottoDAO.doRetrieveById((String) recensione.get("prodId"));
        if(prod == null) {
            resp.getWriter().write("nullProd");
            resp.setStatus(400);
            return;
        }

        String autore= (String) recensione.get("autore");
        String voto= (String) recensione.get("voto");
        String descrizione= (String) recensione.get("descrizione");

        String descrizioneRGX="^[\\s\\S]{2,1000}$";
        String votoRGX="^[1-5]$";

        if (!autore.equals(((Utente)req.getSession().getAttribute("Utente")).getUsername())){
            resp.getWriter().write("autoreWrongPattern");
            resp.setStatus(400);
            return;
        }
        if(RecensioneDAO.doExistsByUsername_Prod(autore, prod.getId())) {
            resp.getWriter().write("recensioneAlreadyExist");
            resp.setStatus(400);
            return;
        }
        if (!descrizione.matches(descrizioneRGX)){
            resp.getWriter().write("descrizioneWrongPattern");
            resp.setStatus(400);
            return;
        }
        if (!voto.matches(votoRGX)){
            resp.getWriter().write("votoWrongPattern");
            resp.setStatus(400);
            return;
        }

        Recensione rec= new Recensione();
        rec.setAutore(autore);
        rec.setVoto(Short.parseShort(voto));
        rec.setDescrizione(descrizione);

        RecensioneDAO.doSave(rec,prod.getId());
        resp.getWriter().write(rec.getId());
        resp.setStatus(200);
    }
}
