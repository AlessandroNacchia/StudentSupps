<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Aggiungi metodo di pagamento | Student Supps</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/formContainer.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <main class="formContainer">
        <h1 class="formContainer-title">Aggiungi i parametri del prodotto</h1>
        <div class="formContainer-wrapper">
            <section class="formContainer-section">
                <c:if test="${requestScope.addPayMethodStatus == 'providerWrongPattern'}">
                    <p style="color: red">Pattern Provider errato!</p>
                </c:if>
                <c:if test="${requestScope.addPayMethodStatus == 'cardNumberWrongPattern'}">
                    <p style="color: red">Pattern Numero Carta errato!</p>
                </c:if>
                <c:if test="${requestScope.addPayMethodStatus == 'expiryDateWrongPattern'}">
                    <p style="color: red">Pattern Data di scadenza errato!</p>
                </c:if>
                <form action="<%=request.getContextPath()%>/Cart/Checkout/AddPayMethod" method="post">
                    <div class="form-field">
                        <label class="form-field-label" for="providerAdd">Provider</label>
                        <input class="form-field-input" id="providerAdd" name="provider" type="text" maxlength="30" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 30 caratteri.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="cardNumberAdd">Numero Carta</label>
                        <input class="form-field-input" id="cardNumberAdd" name="cardNumber" type="text" maxlength="20" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="expiryDateAdd">Data di scadenza</label>
                        <input class="form-field-input" id="expiryDateAdd" name="expiryDate" type="text" style="width: 100px"
                               onkeyup="expiryDateSlash(this)" maxlength="5" placeholder="MM/YY" required>
                    </div>

                    <div class="form-buttons">
                        <a href="<%=request.getContextPath()%>/Cart/Checkout" class="buttonPrimary buttonSecondary buttonHover" type="submit">Annulla</a>
                        <button class="form-submitButton buttonHover" onclick="return (confermaParametri())" type="submit">Aggiungi</button>
                    </div>
                </form>
            </section>
        </div>
    </main>

    <script>
        function confermaParametri() {
            let provider= document.getElementById('providerAdd').value;
            let cardNumber= document.getElementById('cardNumberAdd').value;
            let expiryDate= document.getElementById('expiryDateAdd').value;

            const providerRGX= /^[a-zA-Z.\s]{2,30}$/;
            const cardNumberRGX= /^\b(\d{4}\s?\d{4}\s?\d{4}\s?\d{4}$)\b$/;
            const expiryDateRGX= /^(0[1-9]|1[0-2])\/([0-9]{2})$/;

            if(!providerRGX.test(provider)) {
                alert("Provider non valido!");
                return false;
            }
            if(!cardNumberRGX.test(cardNumber)) {
                alert("Numero carta non valido!");
                return false;
            }
            if(!expiryDateRGX.test(expiryDate)) {
                alert("Data di scadenza non valida!");
                return false;
            }

            return true;
        }

        function expiryDateSlash(el) {
            if (el.value.length === 2)
                el.value= el.value + '/';
            else
            if ((el.value.length === 3 || el.value.length === 2) && el.value.charAt(2) === '/') {
                el.value= el.value.replace('/', '');
                el.value= el.value.replace(el.value.charAt(1), '');
            }
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
