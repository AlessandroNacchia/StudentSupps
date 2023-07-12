<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Ordini | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/orders.css">
</head>
<body>
<jsp:include page="/ReusedHTML/head.jsp"/>
<%List<Ordine> OrderList= (List<Ordine>) request.getAttribute("ordini");%>


<main class="orders-page">
    <header class="orders-header">
        <h1 class="orders-header-text">Cronologia Ordini</h1>
    </header>
    <c:if test="${requestScope.viewOrderStatus == 'orderNotValid'}">
        <p style="color: red">Ordine non valido!</p>
    </c:if>
    <%if(!OrderList.isEmpty()) {%>
    <table class="orders-table">
        <thead>
        <tr>
            <th>Numero Ordine</th>
            <th>Stato</th>
            <th>Data di Acquisto</th>
            <th>Totale</th>
            <th></th>
        </tr>
        </thead>

        <tbody>
        <%for (Ordine o: OrderList){%>
        <tr class="order-item">
            <td>
                <div class="orders-label">Numero Ordine</div>
                <div class="orders-Value"><%=o.getId()%></div>
            </td>
            <td>
                <div class="orders-label">Stato</div>
                <%
                    switch (o.getStato()) {
                        case "processing" : { %>
                        <div class="orders-Value">In Elaborazione</div>
                            <%break;} case "shipped" : { %>
                        <div class="orders-Value">Spedito</div>
                            <%break;} case "delivered" : { %>
                        <div class="orders-Value">Consegnato</div>
                        <%break;}
                    }%>
            </td>
            <td>
                <div class="orders-label">Data di Acquisto</div>
                <div class="orders-Value"><%=o.getDataAcquisto().toLocalDateTime().toLocalDate()%></div>
            </td>
            <td>
                <div class="orders-label">Totale</div>
                <div class="orders-Value"><%=o.getTotale()%></div>
            </td>
            <td>
                <a style="width: 100%" href="<%=request.getContextPath()%>/Account/ViewOrder?id=<%=o.getId()%>" class="view-order-button">
                    <div class="buttonHover buttonPrimary">Visualizza Dettagli</div>
                </a>
            </td>
        </tr>
            <%}%>

        </tbody>
    </table>
    <%}else {%>
    <section class="orders-empty">
        <span class="orders-emptyIcon"><i class="fa fa-truck"></i></span>
        <h2 class="orders-emptyMessage">Non hai ancora effettuato degli ordini</h2>
        <div class="orders-emptyBtn">
            <a  href="<%=request.getContextPath()%>" class="buttonPrimary buttonHover">Continua i tuoi Acquisti</a>
        </div>
    </section>
    <%}%>
</main>

<jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
