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
        List<Utente> prodList= (List<Utente>) request.getAttribute("prodList");
        Utente user= (Utente) session.getAttribute("Utente");
    %>

    <div class="tableContainer">
        <table class="usersTable">
            <tr>
                <th>Username</th>
                <th>Email</th>
                <th>Numero Telefono</th>
                <th>Admin</th>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Azione</th>
            </tr>
            <% for(Utente u: prodList){ %>
            <tr>
                <td><%=u.getUsername()%></td>
                <td><%=u.getEmail()%></td>
                <td><%=u.getNumeroTel()%></td>
                <td><%=u.isAdmin()%></td>
                <td><%=u.getNome()%></td>
                <td><%=u.getCognome()%></td>
                <td>
                    <button class="buttonPrimary buttonHover" onclick=location.href="EditUser?id=<%=u.getId()%>">Modifica</button>
                    <button class="buttonPrimary buttonHover" onclick=location.href="EditUser?id=<%=u.getId()%>">Cancella</button>
                </td>
            </tr>
            <%}%>
        </table>
    </div>


    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
