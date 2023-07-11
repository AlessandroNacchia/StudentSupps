<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.Sconto" %>
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
        List<Sconto> discountList= (List<Sconto>) request.getAttribute("discountList");
    %>

    <div class="tableContainer">
        <table class="defaultTable">
            <tr>
                <th>Nome</th>
                <th>Percentuale</th>
                <th>Stato</th>
                <th>Data Inizio</th>
                <th>Data Fine</th>
                <th>Azione</th>
            </tr>
            <% for(Sconto s: discountList){ %>
            <tr id="discountId<%=s.getId()%>">
                <td><%=s.getNome()%></td>
                <td><%=s.getPercentuale()%>%</td>
                <td>
                    <%if(s.isStato()) {%>
                        Attivato
                    <%} else {%>
                        Disattivato
                    <%}%>
                </td>
                <td><%=s.getDataInizio()%></td>
                <td><%=s.getDataFine()%></td>
                <td>
                    <button class="buttonPrimary buttonHover" onclick=location.href="EditDiscount?id=<%=s.getId()%>">Modifica</button>
                    <button class="buttonPrimary buttonHover" onclick="return deleteDiscount('<%=s.getId()%>', '<%=s.getNome()%>')">Cancella</button>
                    <button id="rmvDiscountProds<%=s.getId()%>" class="buttonPrimary buttonHover" onclick="return rmvDiscountProds('<%=s.getId()%>')">Rimuovi sconto dai prodotti</button>
                </td>
            </tr>
            <%}%>
        </table>
    </div>

    <button class="buttonPrimary buttonHover" style="width: auto; margin: 20px auto 0;" onclick=location.href="AddDiscount">Aggiungi Sconto</button>

    <script>
        function deleteDiscount(id, name) {
            if(!confirm('Vuoi eliminare lo sconto con dati:\n id=\x27'+id+'\x27\n name=\x27'+name+'\x27?\n' +
                'Quest\x27azione non è reversibile!'))
                return false;

            let xhttp= new XMLHttpRequest();
            xhttp.onreadystatechange= function() {
                if (this.readyState === 4 && this.status === 200) {
                    document.getElementById('discountId'+id).remove();
                }
            };

            xhttp.open("POST", "<%=request.getContextPath()%>/Admin/DeleteDiscount", true);
            xhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
            xhttp.send("id="+id);
        }

        function rmvDiscountProds(id) {
            if(!confirm('Vuoi rimuovere questo sconto dai prodotti?\n' +
                'Quest\x27azione non è reversibile!'))
                return false;

            let xhttp= new XMLHttpRequest();
            xhttp.onreadystatechange= function() {
                if (this.readyState === 4 && this.status === 200) {
                    document.getElementById('rmvDiscountProds'+id).remove();
                }
            };

            xhttp.open("POST", "<%=request.getContextPath()%>/Admin/RmvDiscountProds", true);
            xhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
            xhttp.send("id="+id);
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
