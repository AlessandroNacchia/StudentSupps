<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.LocalDateTime" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
    <table class="order-details-table">
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
        <tr>
            <td><%=ind.getVia()%>, <%=ind.getCitta()%>, <%=ind.getProvincia()%>, <%=ind.getCAP()%>, <%=ind.getNazione()%></td>
            <td><%=mp.getProvider()%>, termina con <%=mp.getLastDigits()%></td>
            <%if (o.getStato().equals("processing") || o.getStato().equals("shipped")){%>
            <%if (Timestamp.valueOf(LocalDateTime.now()).compareTo(o.getDataConsegna()) <= 0){%>
            <td><%=o.getDataConsegna()%></td>
            <%} else if (Timestamp.valueOf(LocalDateTime.now()).compareTo(o.getDataConsegna()) > 0) {%>
            <td>IN RITARDO</td>
            <%}%>
            <%}else if (o.getStato().equals("delivered")){%>
            <td><%=o.getDataConsegna()%></td>
            <%}%>
        </tr>
        </tbody>
    </table>


<table class="orders-info-table">
    <thead>
    <tr>
        <th>Prodotto</th>
        <th>Quantità</th>
        <th>Prezzo d'Acquisto</th>
        <th>Iva d'Acquisto</th>
    </tr>
    </thead>

    <tbody>
    <%for (Prodottoordine po: prodList){%>
    <tr class="order-item">
        <td>
            <div class="orders-label">Prodotto</div>
            <div class="orders-Value"><%=po.getNome_prodotto()%></div>
        </td>
        <td>
            <div class="orders-label">Quantità</div>
            <div class="orders-Value"><%=po.getQuantita()%></div>
        </td>
        <td>
            <div class="orders-label">Prezzo d'Acquisto</div>
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
<jsp:include page="/ReusedHTML/tail.jsp"/>

</body>
</html>
