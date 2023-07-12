<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.*" %>
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
                <th>Sconto</th>
                <th>Azione</th>
            </tr>
            <% for(Prodotto p: prodList){ %>
            <tr id="prodId<%=p.getId()%>">
                <td>
                    <div style="position: relative;padding-top: 100%;overflow: hidden;">
                        <figure style="position: absolute;top: 0;">
                            <picture>
                                <img src="<%=request.getContextPath() + "/ProductImages/" + p.getNome() + ".png"%>"
                                     class="imgProdErr" alt="<%=p.getNome()%>" title="<%=p.getNome()%>">
                            </picture>
                        </figure>
                    </div>
                </td>
                <td><%=p.getNome()%></td>
                <td style="word-break: break-word">
                    <div style="max-height: 200px;min-width: 200px;overflow-y: auto;">
                        <%=p.getDescrizione()%>
                    </div>
                </td>
                <td><%=p.getPrezzo()%>&nbsp;€</td>
                <td><%=p.getIVA()%>%</td>
                <td><%=p.getQuantita()%></td>
                <td style="max-width: 0; word-break: break-word">
                    <%for(String catId: ProdottocategoriaDAO.doRetrieveCategorie(p.getId())) {
                        Categoria cat= CategoriaDAO.doRetrieveById(catId);
                        if(cat!=null) {%>
                            <%=cat.getNome()%><br>
                        <%}
                    }%>
                </td>
                <td>
                    <%String discountId= ProdottoDAO.doRetrieveDiscountId(p.getId());
                    if(discountId==null) {%>
                        Non applicato
                    <%} else {
                        Sconto s= ScontoDAO.doRetrieveById(discountId);
                        if(s!=null) {%>
                            <%=s.getNome()%>
                        <%}
                    }%>
                </td>
                <td>
                    <button class="buttonPrimary buttonHover" onclick=location.href="EditProduct?id=<%=p.getId()%>">Modifica</button>
                    <button class="buttonPrimary buttonHover" onclick="return deleteProduct('<%=p.getId()%>', '<%=p.getNome()%>')">Cancella</button>
                </td>
            </tr>
            <%}%>
        </table>
    </div>

    <button class="buttonPrimary buttonHover" style="width: auto; margin: 20px auto 0;" onclick=location.href="AddProduct">Aggiungi Prodotto</button>

    <script>
        function deleteProduct(id, name) {
            if(!confirm('Vuoi eliminare il prodotto con dati:\n id=\x27'+id+'\x27\n name=\x27'+name+'\x27?\n' +
                'Quest\x27azione non è reversibile!'))
                return false;

            let xhttp= new XMLHttpRequest();
            xhttp.onreadystatechange= function() {
                if (this.readyState === 4 && this.status === 200) {
                    document.getElementById('prodId'+id).remove();
                }
            };

            xhttp.open("POST", "<%=request.getContextPath()%>/Admin/DeleteProduct", true);
            xhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
            xhttp.send("id="+id);
        }

        let imgProds= document.querySelectorAll('.imgProdErr');
        imgProds.forEach(img=>{
            img.addEventListener('error', ()=>{
                img.src="<%=request.getContextPath()%>/images/img_notfound.png";
                img.alt="Immagine non trovata";
                img.title="Immagine non trovata";
            })
        })
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
