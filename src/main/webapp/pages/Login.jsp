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
    <main class="login">
        <h1 class="login-title">Accedi oppure registra un nuovo account</h1>
        <div class="login-wrapper">
            <section class="login-section">
                <h2 class="login-subtitle">Accedi</h2>
                <form action="Login" method="post">
                    <div class="form-field">
                        <label class="form-field-label" for="username">Username o Email</label>
                        <input class="form-field-input" id="username" name="username" type="text">
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="password">Password</label>
                        <div class="form-field-icon">
                            <input class="form-field-input" id="password" name="password" type="password" minlength="8" autocomplete="off">
                            <span class="password-eye" onclick="revealPassword()"><i class="fa fa-eye" id="passEye" title="Mostra Password"></i></span>
                        </div>
                    </div>
                    <button class="form-submitButton" onclick="return (confermaParametri())" type="submit">Accedi</button>
                </form>
            </section>
            <section class="login-section">
                <h2 class="login-subtitle">Registrati</h2>
                <form action="Signup">

                </form>
            </section>
        </div>
    </main>

<script>
    function revealPassword() {
        let passField= document.getElementById("password");
        let passEye= document.getElementById("passEye")
        if (passField.type === "password") {
            passField.type= "text";
            passEye.className= "fa fa-eye-slash";
        }
        else {
            passField.type= "password";
            passEye.className= "fa fa-eye";
        }
    }
</script>
</body>
</html>
