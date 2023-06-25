<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.Prodotto" %>
<%@ page import="com.tsw.studentsupps.Model.ProdottocategoriaDAO" %>
<%@ page import="com.tsw.studentsupps.Model.CategoriaDAO" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Visualizza Utenti | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <%
        List<Prodotto> prodList= (List<Prodotto>) request.getAttribute("prodList");
    %>

    <div class="tableContainer">
        <table class="defaultTable">
            <tr>
                <th>Immagine</th>
                <th>Nome</th>
                <th>Descrizione</th>
                <th>Prezzo</th>
                <th>IVA</th>
                <th>Quantità</th>
                <th>Categorie</th>
                <th>Azione</th>
            </tr>
            <% for(Prodotto p: prodList){ %>
            <tr>
                <td>
                    <div style="position: relative;padding-top: 100%;">
                        <figure style="position: absolute;top: 0;">
                            <picture>
                                <img src="<%=request.getContextPath() + "/images/products/" + p.getNome() + ".png"%>" alt="<%=p.getNome()%>" title="<%=p.getNome()%>">
                            </picture>
                        </figure>
                    </div>
                </td>
                <td><%=p.getNome()%></td>
                <td style="max-width: 0; word-break: break-word">
                    <%=p.getDescrizione()%>
                </td>
                <td><%=p.getPrezzo()%>&nbsp;€</td>
                <td><%=p.getIVA()%>%</td>
                <td><%=p.getQuantita()%></td>
                <td style="max-width: 0; word-break: break-word">
                    <%for(String catId: ProdottocategoriaDAO.doRetrieveCategorie(p.getId())) {%>
                        <%=CategoriaDAO.doRetrieveById(catId).getNome()%><br>
                    <%}%>
                </td>
                <td>
                    <button class="buttonPrimary buttonHover" onclick=location.href="EditProduct?id=<%=p.getId()%>">Modifica</button>
                    <form action="<%=request.getContextPath()%>/Admin/DeleteProduct" method="post" style="margin:0">
                        <input type="hidden" name="id" value="<%=p.getId()%>">
                        <button class="buttonPrimary buttonHover" onclick="return confirm(
                                'Vuoi eliminare il prodotto con dati:\n id=\x27<%=p.getId()%>\x27\n name=\x27<%=p.getNome()%>\x27?\n' +
                                'Quest\x27azione non è reversibile!'
                                )" type="submit">Cancella</button>
                    </form>
                </td>
            </tr>
            <%}%>
        </table>
    </div>

    <button class="buttonPrimary buttonHover" style="width: auto; margin: 20px auto 0;" onclick=location.href="AddProduct">Aggiungi Prodotto</button>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
