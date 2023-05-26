<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Header</title>
</head>
<body>
    <form action="login" method="POST">
        <label for="username">Username: </label>
        <input type="text" id="username" name="username" required>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <button onclick="confermaParametri()" type="submit">Login</button>
        <button onclick="location.href='signin'" type="button">Registrati</button>
    </form>

    <c:if test="${requestScope.loginStatus == 'failedLogin'}">
        <p style="color: red">Email o Password errati!</p>
    </c:if>

    <script>
        function confermaParametri() {
            let username= document.getElementById('username').value;
            let password= document.getElementById('password').value;

            const usernameRGX= /[A-Za-z][A-Za-z0-9_]{5,20}$/;
            const passwordRGX=/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\[{}\]:;',?/*~$^+=<>]).{8,20}$/;

            if(!usernameRGX.test(username)) {
                alert("L'username non rispecchia il formato corretto. Riprovare!");
                return false;
            }
            if(!passwordRGX.test(password)) {
                alert("La password non rispecchia il formato corretto. Riprovare!");
                return false;
            }

            return true;
        }
    </script>
</body>
</html>
