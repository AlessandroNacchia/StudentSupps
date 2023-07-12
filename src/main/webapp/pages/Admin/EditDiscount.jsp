<%@ page import="com.tsw.studentsupps.Model.Sconto" %>
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

    <%Sconto s= (Sconto) request.getAttribute("discountToEdit");%>
    <main class="formContainer">
        <h1 class="formContainer-title">Modifica i parametri del prodotto</h1>
        <div class="formContainer-wrapper">
            <section class="formContainer-section">
                <c:if test="${requestScope.editDiscountStatus == 'nameWrongPattern'}">
                    <p style="color: red">Pattern Nome errato!</p>
                </c:if>
                <c:if test="${requestScope.editDiscountStatus == 'percentageWrongPattern'}">
                    <p style="color: red">Pattern Percentuale errato!</p>
                </c:if>
                <c:if test="${requestScope.editDiscountStatus == 'startDateWrongPattern'}">
                    <p style="color: red">Pattern Data d'inizio errato!</p>
                </c:if>
                <c:if test="${requestScope.editDiscountStatus == 'endDateWrongPattern'}">
                    <p style="color: red">Pattern Data di fine errato!</p>
                </c:if>
                <c:if test="${requestScope.editDiscountStatus == 'nameTaken'}">
                    <p style="color: red">Nome Sconto già usato!</p>
                </c:if>
                <form action="<%=request.getContextPath()%>/Admin/EditDiscount" method="post">
                    <div class="form-field">
                        <label class="form-field-label" for="discountToEditId">Id</label>
                        <input class="form-field-input" id="discountToEditId" name="id" type="text" value="<%=s.getId()%>" readonly>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="nameUpdate">Nome</label>
                        <input class="form-field-input" id="nameUpdate" name="name" type="text" maxlength="50" autocomplete="off" value="<%=s.getNome()%>" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 50 caratteri. Accetta lettere, numeri, spazi, punti e trattini medi.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="percentageUpdate">IVA</label>
                        <input class="form-field-input" id="percentageUpdate" name="percentage" type="number" min="0" max="100" value="<%=s.getPercentuale()%>" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 0. Massimo 100.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="statusUpdate">Stato</label>
                        <input class="form-field-input" id="statusUpdate" name="status" type="checkbox" value="true"
                               style="width: 40px; cursor: pointer; margin: 5px 20px;" <%if(s.isStato()) {%> checked <%}%>>
                        <div class="form-field-comment">
                            Spuntare per indicare che lo sconto è attivo tra la data d'inizio e di fine.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="startDateUpdate">Data d'inizio</label>
                        <input class="form-field-input" id="startDateUpdate" name="startDate" type="datetime-local" value="<%=s.getDataInizio().toString().replace(" ", "T")%>"
                               pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" autocomplete="off" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="endDateUpdate">Data di fine</label>
                        <input class="form-field-input" id="endDateUpdate" name="endDate" type="datetime-local" value="<%=s.getDataFine().toString().replace(" ", "T")%>"
                               pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" autocomplete="off" required>
                    </div>

                    <div class="form-buttons">
                        <a href="<%=request.getContextPath()%>/Admin/Discounts" class="buttonPrimary buttonSecondary buttonHover">Annulla</a>
                        <button class="form-submitButton" onclick="return (confermaParametri())" type="submit">Aggiorna</button>
                    </div>
                </form>
            </section>
        </div>
    </main>

    <script>
        function confermaParametri() {
            let name= document.getElementById('nameUpdate').value;
            let percentage= document.getElementById('percentageUpdate').value;
            let startDateString= document.getElementById('startDateUpdate').value;
            let endDateString= document.getElementById('endDateUpdate').value;

            if((startDateString.match(/:/g) || []).length === 1)
                startDateString+=":00";
            if((endDateString.match(/:/g) || []).length === 1)
                endDateString+=":00";

            const nameRGX= /^[\w\-. ]{2,50}$/;
            const percentageRGX= /^[+]?[0-9]{1,2}$|^[+]?100$/;
            const datetimeRGX= /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})$/;

            if(!nameRGX.test(name)){
                alert("Nome non valido!");
                return false;
            }
            if(!percentageRGX.test(percentage)){
                alert("Percentuale non valida!");
                return false;
            }
            if(!datetimeRGX.test(startDateString)){
                alert("Data d'inizio non valida!");
                return false;
            }
            if(!datetimeRGX.test(endDateString)){
                alert("Data di fine non valida!");
                return false;
            }

            return true;
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
