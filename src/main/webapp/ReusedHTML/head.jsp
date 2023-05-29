<%@ page import="com.tsw.studentsupps.Model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Site Head Nav Bar</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="CSS/siteStyle.css">
    <style>
        /********* MOBILE CSS ***********/
        @media (max-width: 766px) {
            .topnav .search-container{
                margin-top: 2%;
                float: left;
            }
        }

        /********* SMALL SCREEN CSS ***********/
        @media screen and (max-width: 1200px) {
            .topnav a:not(:first-child), .dropdown .dropbtn {
                display: none;
            }
            .topnav a.menuIcon {
                float: right;
                display: block;
            }
            .topnav .search-container{
                float: left;
            }
            .topnav.responsive {position: relative;}
            .topnav.responsive .menuIcon {
                position: absolute;
                right: 0;
                top: 0;
            }
            .topnav.responsive a {
                float: none;
                display: block;
                text-align: left;
            }
            .topnav.responsive .dropdown {float: none;}
            .topnav.responsive .dropdown-content {position: relative;}
            .topnav.responsive .dropdown .dropbtn {
                display: block;
                width: 100%;
                text-align: left;
            }
        }

    </style>
    <script>
        function myFunction() {
            let el= document.getElementById("SiteTopNav");
            if (el.className === "topnav")
                el.className += " responsive";
            else
                el.className = "topnav";
        }
    </script>
</head>
<body>
<div class="topnav" id="SiteTopNav">
    <a href="./"><img src="images/img_placeholder.png" class="logo" alt="img placeholder"></a>
    <div class="dropdown">
        <button class="dropbtn" onclick="window.location.href='Shop?filter=AllBoosters'">Boosters
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="Shop?filter=AllBoosters">All Boosters</a>
            <a href="Shop?filter=BoosterBundles">Booster Bundles</a>
        </div>
    </div>

    <div class="dropdown">
        <button class="dropbtn" onclick="window.location.href='Shop?filter=AllShakers'">Shakers
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="Shop?filter=AllShakers">All Shakers</a>
            <a href="Shop?filter=ShakerBundles">Shaker Bundles</a>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn" onclick="window.location.href='Shop?filter=AllMerch'">Merce
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="Shop?filter=AllMerch">Merce Varia</a>
            <a href="Shop?filter=MerchMaglie">Maglie</a>
            <a href="Shop?filter=MerchPantaloni">Pantaloni</a>
            <a href="Shop?filter=MerchAltro">Altro</a>
        </div>
    </div>

    <a href="javascript:void(0);" class="menuIcon" onclick="myFunction()">&#9776;</a>
    <div class="dropdown">
        <button class="dropbtn" onclick="window.location.href=''">
            <i class="fa fa-phone"></i>
        </button>
    </div>
    <%
        if(session.getAttribute("Utente")==null)
        {%>
    <div class="dropdown">
        <button class="dropbtn" onclick="window.location.href=''">
            <i class="fa fa-user-circle"></i>
        </button>
    </div>
    <%}
    else
    {%>
    <div class="dropdown">
        <button class="dropbtn" onclick="window.location.href=''">
            <i class="fa fa-sign-out"></i>
        </button>
    </div>
    <div class="dropdown">
        <%Utente u= (Utente) session.getAttribute("Utente");%>
        <a style="text-underline: none; pointer-events: none">Sei loggato come <%=u.getNome()%></a>
    </div>
    <%}%>
    <%if(session.getAttribute("Utente")!=null)
    {%>
    <div class="dropdown">
        <button class="dropbtn" onclick="window.location.href=''">
            <i class="fa fa-shopping-cart"></i>
        </button>
    </div>
    <div class="dropdown">
        <button class="dropbtn" onclick="window.location.href=''">
            <i class="fa fa-truck"></i>
        </button>
    </div>
    <%}%>
    <div class="search-container">
        <form action="RicercaServlet">
            <button type="submit" value="Cerca" class="cerca"><i class="fa fa-search"></i></button>
            <input type="text" placeholder="Cerca..." name="search" class="nomeProdotto">
        </form>
    </div>
</div>
</body>
</html>
