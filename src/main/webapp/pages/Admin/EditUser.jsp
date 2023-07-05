<%@ page import="com.tsw.studentsupps.Model.Utente" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Modifica Utente | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/formContainer.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <%Utente u= (Utente) request.getAttribute("userToEdit");%>
    <main class="formContainer">
        <h1 class="formContainer-title">Modifica i parametri dell'utente</h1>
        <div class="formContainer-wrapper">
            <section class="formContainer-section">
                <c:if test="${requestScope.updateStatus == 'nameWrongPattern'}">
                    <p style="color: red">Pattern Nome/Cognome errato!</p>
                </c:if>
                <c:if test="${requestScope.updateStatus == 'phoneWrongPattern'}">
                    <p style="color: red">Pattern Numero di telefono errato!</p>
                </c:if>
                <c:if test="${requestScope.updateStatus == 'usernameWrongPattern'}">
                    <p style="color: red">Pattern Username errato!</p>
                </c:if>
                <c:if test="${requestScope.updateStatus == 'emailWrongPattern'}">
                    <p style="color: red">Pattern Email errato!</p>
                </c:if>
                <c:if test="${requestScope.updateStatus == 'passwordWrongPattern'}">
                    <p style="color: red">Pattern Password errato!</p>
                </c:if>

                <c:if test="${requestScope.updateStatus == 'usernameTaken'}">
                    <p style="color: red">Username già usato!</p>
                </c:if>
                <c:if test="${requestScope.updateStatus == 'emailTaken'}">
                    <p style="color: red">Email già usata!</p>
                </c:if>
                <c:if test="${requestScope.updateStatus == 'passwordsNotEqual'}">
                    <p style="color: red">Le passwords non corrispondono!</p>
                </c:if>
                <form action="<%=request.getContextPath()%>/UpdateUser" method="post">
                    <div class="form-field">
                        <label class="form-field-label" for="userToEditId">Id</label>
                        <input class="form-field-input" id="userToEditId" name="id" type="text" value="<%=u.getId()%>" readonly>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="nameUpdate">Nome</label>
                        <input class="form-field-input" id="nameUpdate" name="name" type="text" value="<%=u.getNome()%>" autocomplete="off" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="lastnameUpdate">Cognome</label>
                        <input class="form-field-input" id="lastnameUpdate" name="lastname" type="text" value="<%=u.getCognome()%>" autocomplete="off" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="phoneUpdate">Numero di Telefono (Opzionale)</label>
                        <input class="form-field-input" id="phoneUpdate" name="phone" type="text" value="<%=u.getNumeroTel()%>" autocomplete="off">
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="usernameUpdate">Username</label>
                        <input class="form-field-input" id="usernameUpdate" name="username" type="text" value="<%=u.getUsername()%>" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 5 caratteri, Massimo 30 caratteri. Accetta lettere, numeri e trattino basso.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="emailUpdate">Email</label>
                        <input class="form-field-input" id="emailUpdate" name="email" type="email" value="<%=u.getEmail()%>" autocomplete="off" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="isAdminUpdate">Admin</label>
                        <input class="form-field-input" id="isAdminUpdate" name="isAdmin" type="checkbox" value="true"
                            style="width: 40px; cursor: pointer; margin: 5px 20px;" <%if(u.isAdmin()) {%> checked <%}%>>
                    </div>
                    <button class="form-submitButton" onclick="return (confermaParametri())" type="submit">Aggiorna</button>
                </form>
            </section>
        </div>
    </main>

    <script>
        function confermaParametri() {
            let name= document.getElementById('nameUpdate').value;
            let lastname= document.getElementById('lastnameUpdate').value;
            let phone= document.getElementById('phoneUpdate').value;
            let username= document.getElementById('usernameUpdate').value;
            let email= document.getElementById('emailUpdate').value.toLowerCase();

            const nameRGX= /^[a-zA-Z\s]{2,30}$/;
            const phoneRGX= /^([+]?[(]?[0-9]{1,3}[)]?[-\s])?([(]?[0-9]{3}[)]?[-\s]?)?([0-9][-\s]?){3,10}[0-9]$/;
            const usernameRGX= /^[A-Za-z][A-Za-z0-9_]{4,29}$/;
            const emailRGX= /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;

            if(!nameRGX.test(name) || !nameRGX.test(lastname)){
                alert("Nome e/o Cognome non validi!");
                return false;
            }
            if(phone !== "" && !phoneRGX.test(phone)) {
                alert("Numero di telefono non valido. Riprovare!");
                return false;
            }
            if(!usernameRGX.test(username)) {
                alert("L'username non rispecchia il formato corretto. Riprovare!");
                return false;
            }
            if((!emailRGX.test(email))){
                alert("L'email non rispecchia il formato corretto. Riprovare!");
                return false;
            }

            return true;
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
