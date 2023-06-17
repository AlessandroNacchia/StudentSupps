<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>StudentSupps | Creato per Studenti!</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="CSS/siteStyle.css">
    <link rel="stylesheet" href="CSS/homepage.css">

</head>
<body>
    <jsp:include page="ReusedHTML/head.jsp"/>

    <section class="contentSlot">
        <section class="news">
            <div class="banner">
                <div class="background-wrapper">
                    <picture>
                        <source media="(max-width: 767px)" srcset="images\banner-placeholder2.jpg">
                        <img src="images\banner-placeholder.jpg">
                    </picture>
                    <div class="banner-content">
                        <div class="banner-text-content">
                            <div class="text1-banner">Sono Arrivati i Nuovi Gusti!</div>
                            <div class="text2-banner">StudentSupps ha creato dei nuovi gusti fatti apposta per aiutarti nella tua sessione estiva di Esami </div>
                            <button class="buttonPrimary buttonHover buttonews" onclick="location.href='Shop'">
                                Scoprilo ora!
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>

    <jsp:include page="ReusedHTML/tail.jsp"/>
</body>
</html>
