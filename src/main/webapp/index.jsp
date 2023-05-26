<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Home InitCategorie</title>
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

        #hr1 {
            height: 3px;
            margin: 15px auto auto auto;
            width: 75%;
            background-color: #313131;
        }

        #fieldsetCat {
            margin: 15px auto auto auto;
            width: fit-content;
            background-color: #d7d7d7;
        }
    </style>
</head>
<body>
    <c:choose>
        <c:when test="${sessionScope.utente != null}">
            Sei loggato come ${sessionScope.utente.getUsername()}
        </c:when>
        <c:otherwise>
            <jsp:include page="pages/header.jsp" />
        </c:otherwise>
    </c:choose>

    <br>
    <h1 align="center">Prodotti</h1>

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
    <hr id="hr1">
    <fieldset id="fieldsetCat">
        <form action="categoria">
            <label for="cat"><b>Scegli una categoria:</b></label>
            <select name="selectedCat" id="cat">
                <jsp:useBean id="categorie" scope="application" type="java.util.List"/>
                <c:forEach items="${categorie}" var="cat">
                    <option value="${cat.id}">${cat.nome}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Invia">
        </form>
        Oppure
        <button onclick="location.href='logout'" type="button">Esci</button>
    </fieldset>
</body>
</html>
