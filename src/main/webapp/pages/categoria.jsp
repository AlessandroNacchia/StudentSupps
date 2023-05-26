<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="selectedCat" scope="request" type="com.tsw.initcategorie.Model.Categoria"/>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Categoria: ${selectedCat.nome}</title>
    <style>
        #prodotti {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 70%;
            margin-left: auto;
            margin-right: auto;
        }
        #prodotti td, #prodotti th {
            border: 1px solid #ddd;
            padding: 8px;
            width: 33%;
        }
        #prodotti tr:nth-child(even){background-color: #f2f2f2;}
        #prodotti tr:hover {background-color: #ddd;}
        #prodotti th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #00b000;
            color: white;
        }
    </style>
</head>
<body>
    <c:choose>
        <c:when test="${sessionScope.utente != null}">
            Sei loggato come ${sessionScope.utente.getUsername()}
        </c:when>
        <c:otherwise>
            <jsp:include page="header.jsp" />
        </c:otherwise>
    </c:choose>
    <br>
    <h1 align="center">${selectedCat.nome}</h1>
    <h2 align="center">${selectedCat.descrizione}</h2>
    <table id="prodotti">
        <tr>
            <th>Nome</th>
            <th>Descrizione</th>
            <th>Costo</th>
        </tr>
        <jsp:useBean id="prodList" scope="request" type="java.util.List"/>
        <c:forEach items="${prodList}" var="prodotto">
            <tr>
                <td>${prodotto.nome}</td>
                <td>${prodotto.descrizione}</td>
                <td>${prodotto.prezzo}</td>
            </tr>
        </c:forEach>
    </table>
    <button style="margin: 20px auto auto 15%" onclick="location.href='.'" type="button">Torna indietro</button>
</body>
</html>
