<%@ page import="com.tsw.studentsupps.Model.Utente" %>
<%@ page import="java.util.List" %>
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
        List<Utente> userList= (List<Utente>) request.getAttribute("userList");
        Utente user= (Utente) session.getAttribute("Utente");
    %>

    <div class="tableContainer">
        <table class="defaultTable">
            <tr>
                <th>Username</th>
                <th>Email</th>
                <th>Numero Telefono</th>
                <th>Admin</th>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Azione</th>
            </tr>
            <% for(Utente u: userList){ %>
            <tr id="userId<%=u.getId()%>">
                <td><%=u.getUsername()%></td>
                <td><%=u.getEmail()%></td>
                <td><%=u.getNumeroTel()%></td>
                <td><%=u.isAdmin()%></td>
                <td><%=u.getNome()%></td>
                <td><%=u.getCognome()%></td>
                <td>
                    <% if(user.getId().equals(u.getId())) {%>
                        Non puoi interagire con il tuo account.
                    <%} else {%>
                        <button class="buttonPrimary buttonHover" onclick=location.href="EditUser?id=<%=u.getId()%>">Modifica</button>
                        <button class="buttonPrimary buttonHover" onclick="return deleteUser('<%=u.getId()%>', '<%=u.getUsername()%>')">Cancella</button>
                    <%}%>
                </td>
            </tr>
            <%}%>
        </table>
    </div>

    <script>
        function deleteUser(id, username) {
            if(!confirm('Vuoi eliminare l\x27utente con dati:\n id=\x27'+id+'\x27\n username=\x27'+username+'\x27?\n' +
                'Quest\x27azione non è reversibile!'))
                return false;

            let xhttp= new XMLHttpRequest();
            xhttp.onreadystatechange= function() {
                if (this.readyState === 4 && this.status === 200) {
                    document.getElementById('userId'+id).remove();
                }
            };

            xhttp.open("POST", "<%=request.getContextPath()%>/Admin/DeleteUser", true);
            xhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
            xhttp.send("id="+id);
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
