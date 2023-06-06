<%@ page import="com.tsw.studentsupps.Model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Site Head Nav Bar</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="CSS/siteStyle.css">

</head>
<body>
<header class="mainHeader">
    <div class="wrapperHeader">
        <a onclick="openNav()" class="menuIcon"><i class="fa fa-bars"></i></a>
        <div class="logo">
            <a href="./" class="site-logo-link"></a>
        </div>
        <nav class="navBar" id="myNavBar">
            <div class="mobileHeader">
                <a href="./" class="homeBtn" id="hmBtn"><i class="fa fa-home"></i></a>
                <a onclick="subCatClose()" class="goBack" id="goBck"><i class="fa fa-angle-left"></i></a>
                <a style="flex-grow: 1"></a>
                <a href="javascript:void(0)" class="closeBtn" onclick="closeNav()"><i class="fa fa-times"></i></a>
            </div>
            <div class="navListContainer">
                <ul class="navCategories" id="navCats">
                    <li>
                        <div class="navBarPrimaryCategory">
                            <a href="Shop" title="Shop All">Shop All</a>
                        </div>
                    </li>
                    <li>
                        <div class="navBarPrimaryCategory" onclick="subCatOpen(1)">
                            <a href="Shop?filter=AllBoosters" title="Boosters">Boosters</a>
                            <i class="fa fa-caret-down" style="margin-left: 5px"></i>
                        </div>
                        <ul class="dropdown-content" id="drpCnt1">
                            <li>
                                <div class="navBarSecondaryCategory">
                                    <a href="Shop?filter=AllBoosters">All Boosters</a>
                                </div>
                                <div class="navBarSecondaryCategory">
                                    <a href="Shop?filter=BoosterBundles">Booster Bundles</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <div class="navBarPrimaryCategory" onclick="subCatOpen(2)">
                            <a href="Shop?filter=AllShakers" title="Shakers">Shakers</a>
                            <i class="fa fa-caret-down" style="margin-left: 5px"></i>
                        </div>
                        <ul class="dropdown-content" id="drpCnt2">
                            <li>
                                <div class="navBarSecondaryCategory">
                                    <a href="Shop?filter=AllShakers">All Shakers</a>
                                </div>
                                <div class="navBarSecondaryCategory">
                                    <a href="Shop?filter=ShakerBundles">Shaker Bundles</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <div class="navBarPrimaryCategory" onclick="subCatOpen(3)">
                            <a href="Shop?filter=AllMerch" title="Merce">Merce</a>
                            <i class="fa fa-caret-down" style="margin-left: 5px"></i>
                        </div>
                        <ul class="dropdown-content" id="drpCnt3">
                            <li>
                                <div class="navBarSecondaryCategory">
                                    <a href="Shop?filter=AllMerch">Merce Varia</a>
                                </div>
                                <div class="navBarSecondaryCategory">
                                    <a href="Shop?filter=MerchMaglie">Maglie</a>
                                </div>
                                <div class="navBarSecondaryCategory">
                                    <a href="Shop?filter=MerchPantaloni">Pantaloni</a>
                                </div>
                                <div class="navBarSecondaryCategory">
                                    <a href="Shop?filter=MerchAltro">Altro</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
        <ul class="actions">
            <li>
                <div class="account">
                    <a href="Account"><i class="fa fa-user-circle"></i></a>
                </div>
            </li>
            <%if(session.getAttribute("Utente")!=null)
            {%>
                <li>
                    <div class="sign-out">
                        <a href="Signout"><i class="fa fa-sign-out"></i></a>
                    </div>
                </li>
                <li>
                    <div class="userInfo">
                        <%Utente u= (Utente) session.getAttribute("Utente");%>
                        <a>Sei loggato come <%=u.getNome()%></a>
                    </div>
                </li>
            <%}%>
            <li>
                <div class="cart">
                    <a href="Cart"><i class="fa fa-shopping-cart"></i></a>
                </div>
            </li>
            <%if(session.getAttribute("Utente")!=null)
            {%>
                <li>
                    <div class="orders">
                        <a href="Orders"><i class="fa fa-truck"></i></a>
                    </div>
                </li>
            <%}%>
            <li>
                <div class="search-container">
                    <form action="Search">
                        <input type="text" placeholder="Cerca..." name="search" class="searchField">
                        <button type="submit" value="Cerca" class="searchBtn"><i class="fa fa-search"></i></button>
                    </form>
                </div>
            </li>
        </ul>
    </div>
</header>



<script>
    function openNav() {
        let el= document.getElementById("myNavBar");
        if (el.className === "navBar")
            el.className = "navBar navBar--open";
    }
    function closeNav() {
        let el= document.getElementById("myNavBar");
        if (el.className === "navBar navBar--open")
            el.className = "navBar";
    }

    function subCatOpen(drpCntNumber) {
        let nav= document.getElementById("navCats");
        let drpCnt= document.getElementById("drpCnt"+drpCntNumber);
        if (nav.className === "navCategories")
            nav.className = "navCategories navCategories--sub";
        if (drpCnt.className === "dropdown-content")
            drpCnt.className = "dropdown-content dropdown-content--active";

        let hmBtn= document.getElementById("hmBtn");
        if (hmBtn.className === "homeBtn")
            hmBtn.className = "homeBtn homeBtn--remove";
        let goBck= document.getElementById("goBck");
        if (goBck.className === "goBack")
            goBck.className = "goBack goBack--appear";
    }
    function subCatClose() {
        let nav= document.getElementById("navCats");
        let drpCntArr= document.getElementsByClassName("dropdown-content");
        if (nav.className === "navCategories navCategories--sub")
            nav.className = "navCategories";
        for (let drpCnt of drpCntArr) {
            if (drpCnt.className === "dropdown-content dropdown-content--active")
                drpCnt.className = "dropdown-content";
        }

        let hmBtn= document.getElementById("hmBtn");
        if (hmBtn.className === "homeBtn homeBtn--remove")
            hmBtn.className = "homeBtn";
        let goBck= document.getElementById("goBck");
        if (goBck.className === "goBack goBack--appear")
            goBck.className = "goBack";
    }
</script>
</body>
</html>
