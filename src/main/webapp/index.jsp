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
        <div class="info-wrapper">
            <section class="info">
                <header class="header-info">
                    <h2 class="header-text">Perché StudentSupps</h2>
                </header>
                <a href="" class="link-to-FAQ">
                    <article class="info-section" style="background-color:#1f1f1f">
                        <div class="background-wrapper-info">
                           <div class="banner-content">
                               <div class="info-icon">
                                   <i class="fa fa-book" style="color:#620000"></i>
                               </div>
                               <div class="text-info">
                                   Formula speciale per studenti con caffeina e vitamina B12
                               </div>
                           </div>
                        </div>
                    </article>
                    <article class="info-section" style="background-color:#282828">
                        <div class="background-wrapper-info">
                            <div class="banner-content">
                                <div class="info-icon">
                                    <i class="fa fa-money" style="color:#620000"></i>
                                </div>
                                <div class="text-info">
                                    Rapporto qualità/prezzo: una porzione da 500ml costa meno di 1 Euro
                                </div>
                            </div>
                        </div>
                    </article>
                    <article class="info-section" style="background-color:#2f2f2f">
                        <div class="background-wrapper-info">
                            <div class="banner-content">
                                <div class="info-icon">
                                    <i class="fa fa-cubes" style="color:#620000"></i>
                                </div>
                                <div class="text-info">
                                    una porzione da 500ml contiene solo circa 4 grammi di zucchero
                                </div>
                            </div>
                        </div>
                    </article>
                    <article class="info-section" style="background-color:#1f1f1f">
                        <div class="background-wrapper-info">
                            <div class="banner-content">
                                <div class="info-icon">
                                    <i class="fa fa-flag-o" style="color:#620000"></i>
                                </div>
                                <div class="text-info">
                                    Ingriedenti di alta qualità rigorosamente made in Italy
                                </div>
                            </div>
                        </div>
                    </article>
                </a>
            </section>
        </div>
    </section>

    <jsp:include page="ReusedHTML/tail.jsp"/>
</body>
</html>
