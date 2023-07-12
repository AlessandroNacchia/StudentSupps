<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Ordini utenti | StudentSupps</title>
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
        <h1 class="orders-header-text">Cronologia Ordini di tutti gli utenti</h1>
    </header>
    <c:if test="${requestScope.viewOrderStatus == 'orderNotValid'}">
        <p style="color: red">Ordine non valido!</p>
    </c:if>
    <%if(!OrderList.isEmpty()) {%>
        <table class="orders-table">
            <thead>
            <tr>
                <th>Utente</th>
                <th>Numero Ordine</th>
                <th>Stato</th>
                <th>Data di Acquisto</th>
                <th>Totale</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <%for (Ordine o: OrderList){%>
                <tr class="order-item" id="orderId<%=o.getId()%>">
                    <td>
                        <div class="orders-label">Numero Ordine</div>
                        <%Utente userOrder= UtenteDAO.doRetrieveById(OrdineDAO.doRetrieveUserById(o.getId()));
                        if(userOrder!=null) {%>
                            <div class="orders-Value"><%=userOrder.getUsername()%></div>
                        <%} else {%>
                            <div class="orders-Value">Utente non trovato</div>
                        <%}%>
                    </td>
                    <td>
                        <div class="orders-label">Numero Ordine</div>
                        <div class="orders-Value"><%=o.getId()%></div>
                    </td>
                    <td>
                        <div class="orders-label">Stato</div>
                        <%switch (o.getStato()) {
                            case "processing": {%>
                                <div class="orders-Value">In Elaborazione</div>
                                <%break;
                            }
                            case "shipped": {%>
                                <div class="orders-Value">Spedito</div>
                                <%break;
                            }
                            case "delivered": {%>
                                <div class="orders-Value">Consegnato</div>
                                <%break;
                            }
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
                    <td style="display: block">
                        <a style="width: 100%;" href="<%=request.getContextPath()%>/Admin/ViewOrder?id=<%=o.getId()%>" class="view-order-button">
                            <div class="buttonHover buttonPrimary" style="border-bottom: 1px solid #737373;">Visualizza Dettagli</div>
                        </a>
                        <a style="width: 100%" href="<%=request.getContextPath()%>/Admin/EditOrder?id=<%=o.getId()%>" class="view-order-button">
                            <div class="buttonHover buttonPrimary" style="border-bottom: 1px solid #737373;">Modifica Ordine</div>
                        </a>
                        <a style="width: 100%;" onclick="return deleteOrder('<%=o.getId()%>')" class="view-order-button">
                            <div class="buttonHover buttonPrimary">Cancella Ordine</div>
                        </a>
                    </td>
                </tr>
            <%}%>
            </tbody>
        </table>
    <%}else {%>
        <section class="orders-empty">
            <span class="orders-emptyIcon"><i class="fa fa-truck"></i></span>
            <h2 class="orders-emptyMessage">Non ci sono ordini sul sito!</h2>
            <div class="orders-emptyBtn">
                <a href="<%=request.getContextPath()%>" class="buttonPrimary buttonHover">Torna alla Home</a>
            </div>
        </section>
    <%}%>
</main>

<script>
    function deleteOrder(id) {
        if(!confirm('Vuoi eliminare lo sconto con dati:\n id=\x27'+id+'\x27?\n' +
            'Quest\x27azione non è reversibile!'))
            return false;

        let xhttp= new XMLHttpRequest();
        xhttp.onreadystatechange= function() {
            if (this.readyState === 4 && this.status === 200) {
                document.getElementById('orderId'+id).remove();
            }
        };

        xhttp.open("POST", "<%=request.getContextPath()%>/Admin/DeleteOrder", true);
        xhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
        xhttp.send("id="+id);
    }
</script>

<jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
