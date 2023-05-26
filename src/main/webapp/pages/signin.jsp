<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Registrazione</title>
    <style>
        #fieldsetSignin {
            margin: 10% auto 10% auto;
            width: 30%;
            background-color: #d7d7d7;
        }
        #fieldsetSignin input  {
            width: 75%;
        }
        #fieldsetSignin button {
            background-color: #00b000;
            width: 30%;
            margin-top: 10%;
            margin-left: 60%;
            margin-right: 10%;
        }
    </style>
</head>
<body>
    <fieldset id="fieldsetSignin">
        <form action="signin" method="post">
            <label for="username"><b>Username:</b></label><br>
            <input type="text" id="username" name="username" required><br>
            <label for="password"><b>Password:</b></label><br>
            <input type="password" id="password" name="password" required><br>
            <label for="email"><b>Email:</b></label><br>
            <input type="email" id="email" name="email" required><br>
            <label for="nome"><b>Nome:</b></label><br>
            <input type="text" id="nome" name="nome" required><br>

            <button onclick="return (confermaParametri())" type="submit">Invia</button>
        </form>
    </fieldset>

    <script>
        function confermaParametri() {
            let username= document.getElementById('username').value;
            let password= document.getElementById('password').value;
            let email= document.getElementById('email').value;
            let nome=document.getElementById('nome').value;

            const usernameRGX= /^[A-Za-z][A-Za-z0-9_]{7,29}$/;
            const passwordRGX=/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\[{}\]:;',?/*~$^+=<>]).{8,30}$/;
            const emailRGX= /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            const nomeRGX=/^[a-zA-Z' ']*$/;

            if(!usernameRGX.test(username)) {
                alert("L'username non rispecchia il formato corretto. Riprovare!");
                return false;
            }
            if(!passwordRGX.test(password)) {
                alert("La password non rispecchia il formato corretto. Riprovare!");
                return false;
            }
            if((!emailRGX.test(email))){
                alert("Formato email non valido!");
                return false;
            }
            if(!(nomeRGX.test(nome))){
                alert("Nome non valido!");
                return false;
            }

            return true;
        }
    </script>
</body>
</html>
