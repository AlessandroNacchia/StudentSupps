<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.LocalDateTime" %>

<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Visualizza Ordine | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/orders.css">
</head>
<body>
<jsp:include page="/ReusedHTML/head.jsp"/>
<%Ordine o= (Ordine)   request.getAttribute("ordine");
    Indirizzo ind= (Indirizzo)   request.getAttribute("indirizzo");
    Metodopagamento mp= (Metodopagamento)   request.getAttribute("metodopagamento");
    List<Prodottoordine> prodList= (List<Prodottoordine>) request.getAttribute("listaprodotti");
%>
<main class="orders-page">
    <header class="orders-header">
        <h1 class="orders-header-text">Ordine N <%=o.getId()%></h1>
    </header>
    <table class="orders-table">
        <thead>
        <tr>
            <th>Indirizzo</th>
            <th>Metodo di Pagamento</th>
            <%if (o.getStato().equals("processing") || o.getStato().equals("shipped")){%>
            <th>Data Di Consegna Prevista</th>
            <%}else if (o.getStato().equals("delivered")){%>
            <th>Data Di Consegna</th>
            <%}%>

        </tr>
        </thead>
        <tbody>
        <tr class="order-item">
            <td class="border-order">
                <div class="orders-label">Indirizzo</div>
                <div class="orders-Value"><%=ind.getVia()%>, <%=ind.getCitta()%>, <%=ind.getProvincia()%>, <%=ind.getCAP()%>, <%=ind.getNazione()%></div>
            </td>
            <td class="border-order">
                <div class="orders-label">Metodo di Pagamento</div>
                <div class="orders-Value"><%=mp.getProvider()%>, termina con <%=mp.getLastDigits()%></div>
            </td>
            <%if (o.getStato().equals("processing") || o.getStato().equals("shipped")){%>
            <%if (Timestamp.valueOf(LocalDateTime.now()).compareTo(o.getDataConsegna()) <= 0){%>
            <td>
                <div class="orders-label">Data Di Consegna Prevista</div>
                <div class="orders-Value"><%=o.getDataConsegna().toLocalDateTime().toLocalDate()%></div>
            </td>
            <%} else if (Timestamp.valueOf(LocalDateTime.now()).compareTo(o.getDataConsegna()) > 0) {%>
            <td>
                <div class="orders-label">Data Di Consegna Prevista</div>
                <div class="orders-Value">IN RITARDO</div>
            </td>
            <%}%>
            <%}else if (o.getStato().equals("delivered")){%>
            <td>
                <div class="orders-label">Data Di Consegna</div>
                <div class="orders-Value"><%=o.getDataConsegna().toLocalDateTime().toLocalDate()%></div>
            </td>
            <%}%>
        </tr>
        </tbody>
    </table>


<table class="orders-table">
    <thead>
    <tr>
        <th class="img-prod-order">Immagine</th>
        <th>Prodotto</th>
        <th>Quantità</th>
        <th>Prezzo d'Acquisto (1pz)</th>
        <th>Iva d'Acquisto</th>
    </tr>
    </thead>

    <tbody>
    <%for (Prodottoordine po: prodList){%>
    <tr class="order-item">
        <td class="img-prod-order">
            <div style="position: relative;padding-top: 40%;overflow: hidden;">
                <figure style="position: absolute;top: 0;">
                    <picture>
                        <img style="max-width: 40%" src="<%=request.getContextPath() + "/ProductImages/" + po.getNome_prodotto() + ".png"%>"
                             class="imgProdErr" alt="<%=po.getNome_prodotto()%>" title="<%=po.getNome_prodotto()%>">
                    </picture>
                </figure>
            </div>
        </td>
        <td>
            <div class="orders-label">Prodotto</div>
            <div class="orders-Value"><%=po.getNome_prodotto()%></div>
        </td>
        <td>
            <div class="orders-label">Quantità</div>
            <div class="orders-Value"><%=po.getQuantita()%></div>
        </td>
        <td>
            <div class="orders-label">Prezzo d'Acquisto (1pz)</div>
            <div class="orders-Value"><%=po.getPrezzo_acquisto()%></div>
        </td>
        <td>
            <div class="orders-label">Iva d'Acquisto</div>
            <div class="orders-Value"><%=po.getIVA_acquisto()%></div>
        </td>
    </tr>
    <%}%>
    </tbody>
</table>
</main>

<script>
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
