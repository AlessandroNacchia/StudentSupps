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
            <tr>
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
                        <form action="<%=request.getContextPath()%>/DeleteUser" method="post" style="margin:0">
                            <input type="hidden" name="id" value="<%=u.getId()%>">
                            <button class="buttonPrimary buttonHover" onclick="return confirm(
                                    'Vuoi eliminare l\x27utente con dati:\n id=\x27<%=u.getId()%>\x27\n username=\x27<%=u.getNome()%>\x27?\n' +
                                    'Quest\x27azione non è reversibile!'
                                    )" type="submit">Cancella</button>
                        </form>
                    <%}%>
                </td>
            </tr>
            <%}%>
        </table>
    </div>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
