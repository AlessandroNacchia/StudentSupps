<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Aggiornato con Successo | StudentSupps</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/results/success.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <main class="success">
        <h1 class="success-title">Aggiornato con Successo</h1>
        <div class="success-wrapper">
            <section class="success-section">
                <button class="buttonPrimary buttonHover" style="margin-top: 40px;" onclick="location.href='<%=request.getContextPath()%>/'">Torna alla Home</button>
                <%if(request.getAttribute("returnPage") != null) {%>
                    <button class="buttonPrimary buttonSecondary buttonHover" style="margin-top: 40px;" onclick="location.href='<%=request.getContextPath()%><%=request.getAttribute("returnPage")%>'">Torna indietro</button>
                <%}%>
            </section>
        </div>
    </main>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
