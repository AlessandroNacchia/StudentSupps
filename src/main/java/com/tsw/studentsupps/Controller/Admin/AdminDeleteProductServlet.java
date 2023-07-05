package com.tsw.studentsupps.Controller.Admin;

import com.tsw.studentsupps.Controller.utils.Checks;
import com.tsw.studentsupps.Model.Prodotto;
import com.tsw.studentsupps.Model.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/Admin/DeleteProduct")
public class AdminDeleteProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Checks.adminCheck(request, response)) return;

        String prodToDeleteId= request.getParameter("id");
        if(Checks.UUIDCheck(request, response, prodToDeleteId)) return;

        Prodotto prodToDelete= ProdottoDAO.doRetrieveById(prodToDeleteId);
        if(prodToDelete == null) {
            request.setAttribute("errorMessage", "Prodotto da cancellare non esistente");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        String prodName= prodToDelete.getNome();
        String imageToDelete= prodName + ".png";
        File imageToMove= new File((String) getServletContext().getAttribute("prodImageFolder"), imageToDelete);

        String delProdImageFolder= (String) getServletContext().getAttribute("delProdImageFolder");
        File movedImage= new File(delProdImageFolder, imageToDelete);
        for(int i= 0; i<1000; i++) {
            if(!Files.exists(movedImage.toPath())) {
                Files.move(imageToMove.toPath(), movedImage.toPath());
                break;
            }
            else {
                movedImage= new File(delProdImageFolder, prodName + i + imageToDelete.substring(imageToDelete.lastIndexOf(".")));
            }
        }

        ProdottoDAO.doDelete(prodToDelete);

        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
