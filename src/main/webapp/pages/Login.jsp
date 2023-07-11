<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Login | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="CSS/siteStyle.css">
    <link rel="stylesheet" href="CSS/login.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <main class="login">
        <h1 class="login-title">Accedi oppure registra un nuovo account</h1>
        <nav class="login-tabs">
            <a class="login-link login-link--active" id="linkTab-Login" onclick="changeTab('Login')">Accedi</a>
            <a class="login-link" id="linkTab-Signup" onclick="changeTab('Signup')">Registrati</a>
        </nav>
        <div class="login-wrapper">
            <section class="login-section login-section--active" id="section-Login">
                <h2 class="login-subtitle">Accedi</h2>
                <c:if test="${requestScope.loginStatus == 'usernameWrongPattern'}">
                    <p style="color: red">Pattern Username/Email errato!</p>
                </c:if>
                <c:if test="${requestScope.loginStatus == 'passwordWrongPattern'}">
                    <p style="color: red">Pattern password errato!</p>
                </c:if>
                <c:if test="${requestScope.loginStatus == 'failedLogin'}">
                    <p style="color: red">Username/Email o Password errati!</p>
                </c:if>
                <form action="Login" method="post">
                    <div class="form-field">
                        <label class="form-field-label" for="usernameLogin">Username o Email</label>
                        <input class="form-field-input" id="usernameLogin" name="username" type="text" minlength="5" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="passwordLogin">Password</label>
                        <div class="form-field-icon">
                            <input class="form-field-input" id="passwordLogin" name="password" type="password" minlength="8" autocomplete="off" required>
                            <span class="password-eye" onclick="revealPassword('Login')"><i class="fa fa-eye" id="passEyeLogin" title="Mostra Password"></i></span>
                        </div>
                    </div>
                    <button class="form-submitButton" onclick="return (confermaParametri('Login'))" type="submit">Accedi</button>
                </form>
            </section>
            <section class="login-section" id="section-Signup">
                <h2 class="login-subtitle">Registrati</h2>
                <c:if test="${requestScope.signupStatus == 'nameWrongPattern'}">
                    <p style="color: red">Pattern Nome/Cognome errato!</p>
                </c:if>
                <c:if test="${requestScope.signupStatus == 'phoneWrongPattern'}">
                    <p style="color: red">Pattern Numero di telefono errato!</p>
                </c:if>
                <c:if test="${requestScope.signupStatus == 'usernameWrongPattern'}">
                    <p style="color: red">Pattern Username errato!</p>
                </c:if>
                <c:if test="${requestScope.signupStatus == 'emailWrongPattern'}">
                    <p style="color: red">Pattern Email errato!</p>
                </c:if>
                <c:if test="${requestScope.signupStatus == 'passwordWrongPattern'}">
                    <p style="color: red">Pattern Password errato!</p>
                </c:if>

                <c:if test="${requestScope.signupStatus == 'usernameTaken'}">
                    <p style="color: red">Username già usato!</p>
                </c:if>
                <c:if test="${requestScope.signupStatus == 'emailTaken'}">
                    <p style="color: red">Email già usata!</p>
                </c:if>
                <c:if test="${requestScope.signupStatus == 'passwordsNotEqual'}">
                    <p style="color: red">Le passwords non corrispondono!</p>
                </c:if>
                <form action="Signup" method="post">
                    <div class="form-field">
                        <label class="form-field-label" for="nameSignup">Nome</label>
                        <input class="form-field-input" id="nameSignup" name="nameS" type="text" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="lastnameSignup">Cognome</label>
                        <input class="form-field-input" id="lastnameSignup" name="lastnameS" type="text" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="phoneSignup">Numero di Telefono (Opzionale)</label>
                        <input class="form-field-input" id="phoneSignup" name="phoneS" type="text">
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="usernameSignup">Username</label>
                        <input class="form-field-input" id="usernameSignup" name="usernameS" type="text" minlength="5" autocomplete="new-username" required>
                        <div class="form-field-comment">
                            Minimo 5 caratteri, Massimo 30 caratteri. Accetta lettere, numeri e trattino basso.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="emailSignup">Email</label>
                        <input class="form-field-input" id="emailSignup" name="emailS" type="email" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="passwordSignup1">Password</label>
                        <div class="form-field-icon">
                            <input class="form-field-input" id="passwordSignup1" name="passwordS" type="password" minlength="8" autocomplete="new-password" required>
                            <span class="password-eye" onclick="revealPassword('Signup1')"><i class="fa fa-eye" id="passEyeSignup1" title="Mostra Password"></i></span>
                        </div>
                        <div class="form-field-comment">
                            Minimo 8 caratteri, Massimo 30 caratteri. Deve contenere lettere maiuscole, minuscole, numeri e caratteri speciali tranne slash.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="passwordSignup2">Conferma Password</label>
                        <div class="form-field-icon">
                            <input class="form-field-input" id="passwordSignup2" name="passwordS2" type="password" minlength="8" autocomplete="new-password" required>
                            <span class="password-eye" onclick="revealPassword('Signup2')"><i class="fa fa-eye" id="passEyeSignup2" title="Mostra Password"></i></span>
                        </div>
                        <div class="form-field-comment">
                            Ripeti Password inserita nel campo precedente.
                        </div>
                    </div>
                    <button class="form-submitButton" onclick="return (confermaParametri('Signup'))" type="submit">Registrati</button>
                </form>
            </section>
        </div>
    </main>

    <jsp:include page="/ReusedHTML/tail.jsp"/>

    <script>
        function revealPassword(x) {
            let passField= document.getElementById("password" + x);
            let passEye= document.getElementById("passEye" + x);
            if (passField.type === "password") {
                passField.type= "text";
                passEye.className= "fa fa-eye-slash";
            }
            else {
                passField.type= "password";
                passEye.className= "fa fa-eye";
            }
        }

        function confermaParametri(x) {
            if(x === 'Login') {
                let username= document.getElementById('usernameLogin').value;
                let password= document.getElementById('passwordLogin').value;

                const usernameRGX= /^[A-Za-z][A-Za-z0-9_]{4,29}$/;
                const emailRGX= /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
                const passwordRGX=/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\[{}\]:;',?*~$^\-+=<>]).{8,30}$/;

                if(!usernameRGX.test(username) && !emailRGX.test(username)) {
                    alert("L'username o email non rispecchia il formato corretto. Riprovare!");
                    return false;
                }
                if(!passwordRGX.test(password)) {
                    alert("La password non rispecchia il formato corretto. Riprovare!");
                    return false;
                }

                return true;
            } else if (x === 'Signup') {
                if(document.getElementById('passwordSignup1').value !== document.getElementById('passwordSignup2').value) {
                    alert("Le password inserite non sono uguali. Riprovare!");
                    document.getElementById('passwordSignup1').value= '';
                    document.getElementById('passwordSignup2').value= '';
                    return false;
                }

                let name= document.getElementById('nameSignup').value;
                let lastname= document.getElementById('lastnameSignup').value;
                let phone= document.getElementById('phoneSignup').value;
                let username= document.getElementById('usernameSignup').value;
                let email= document.getElementById('emailSignup').value.toLowerCase();
                let password= document.getElementById('passwordSignup1').value;

                const nameRGX= /^[a-zA-Z.\s]{2,30}$/;
                const phoneRGX= /^([+]?[(]?[0-9]{1,3}[)]?[-\s])?([(]?[0-9]{3}[)]?[-\s]?)?([0-9][-\s]?){3,10}[0-9]$/;
                const usernameRGX= /^[A-Za-z][A-Za-z0-9_]{4,29}$/;
                const emailRGX= /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
                const passwordRGX=/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\[{}\]:;',?*~$^\-+=<>]).{8,30}$/;

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
                if(!passwordRGX.test(password)) {
                    alert("La password non rispecchia il formato corretto. Riprovare!");
                    return false;
                }

                return true;
            } else
                return false;
        }

        function changeTab(x) {
            if(x === 'Login') {
                let loginLink= document.getElementById("linkTab-Login");
                if (loginLink.className === 'login-link') {
                    loginLink.className= 'login-link login-link--active';
                    document.getElementById("section-Login").className= 'login-section login-section--active';

                    document.getElementById("linkTab-Signup").className= 'login-link';
                    document.getElementById("section-Signup").className= 'login-section';
                }
            } else if(x === 'Signup') {
                let loginLink= document.getElementById("linkTab-Signup");
                if (loginLink.className === 'login-link') {
                    loginLink.className= 'login-link login-link--active';
                    document.getElementById("section-Signup").className= 'login-section login-section--active';

                    document.getElementById("linkTab-Login").className= 'login-link';
                    document.getElementById("section-Login").className= 'login-section';
                }
            } else
                return false;
        }
    </script>
</body>
</html>
