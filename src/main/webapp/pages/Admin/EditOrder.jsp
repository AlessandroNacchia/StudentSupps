<%@ page import="com.tsw.studentsupps.Model.Ordine" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Aggiungi Prodotto | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/formContainer.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <%Ordine o= (Ordine) request.getAttribute("orderToEdit");%>
    <main class="formContainer">
        <h1 class="formContainer-title">Modifica i parametri del prodotto</h1>
        <div class="formContainer-wrapper">
            <section class="formContainer-section">
                <c:if test="${requestScope.editOrderStatus == 'statusNotValid'}">
                    <p style="color: red">Stato ordine non valido!</p>
                </c:if>
                <c:if test="${requestScope.editOrderStatus == 'deliveryDateNotValid'}">
                    <p style="color: red">Data di consegna non valida!</p>
                </c:if>
                <form action="<%=request.getContextPath()%>/Admin/EditOrder" method="post">
                    <div class="form-field">
                        <label class="form-field-label" for="orderToEditId">Id</label>
                        <input class="form-field-input" id="orderToEditId" name="id" type="text" value="<%=o.getId()%>" readonly>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="statusUpdate">Stato ordine</label>
                        <select class="form-field-input" id="statusUpdate" name="status" required>
                            <option value="processing" <%if(o.getStato().equals("processing")) {%> selected <%}%>>In Elaborazione</option>
                            <option value="shipped" <%if(o.getStato().equals("shipped")) {%> selected <%}%>>Spedito</option>
                            <option value="delivered" <%if(o.getStato().equals("delivered")) {%> selected <%}%>>Consegnato</option>
                        </select>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="deliveryDateUpdate">Data di consegna</label>
                        <input class="form-field-input" id="deliveryDateUpdate" name="deliveryDate" type="datetime-local"
                               value="<%=o.getDataConsegna().toString().replace(" ", "T")%>"
                               pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" step="1" autocomplete="off"
                               required <%if(o.getStato().equals("delivered")) {%> readonly <%}%>>
                    </div>

                    <div class="form-buttons">
                        <a href="<%=request.getContextPath()%>/Admin/Orders" class="buttonPrimary buttonSecondary buttonHover">Annulla</a>
                        <button class="form-submitButton" onclick="return (confermaParametri())" type="submit">Aggiorna</button>
                    </div>
                </form>
            </section>
        </div>
    </main>

    <script>
        function confermaParametri() {
            let status= document.getElementById('statusUpdate').value;
            let deliveryDateString= document.getElementById('deliveryDateUpdate').value;

            if((deliveryDateString.match(/:/g) || []).length === 1)
                deliveryDateString+=":00";

            const datetimeRGX= /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})$/;

            if(status!=="processing" && status!=="shipped" && status!=="delivered"){
                alert("Stato ordine non valido!");
                return false;
            }
            if(!datetimeRGX.test(deliveryDateString)){
                alert("Data di consegna non valida!");
                return false;
            }

            return true;
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
