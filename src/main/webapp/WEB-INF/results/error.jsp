<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Error | StudentSupps</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <div style="margin: 50px 50px auto; min-height: 140px; text-align: center">
        <h1 style="margin-bottom: 20px">ERROR</h1>
        <h2>"Error message: ${errorMessage}"</h2>
        <button class="buttonPrimary buttonHover" style="margin-top: 20px; " onclick="location.href='<%=request.getContextPath()%>'">Torna alla Home</button>
    </div>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
