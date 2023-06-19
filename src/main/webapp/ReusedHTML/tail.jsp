<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Site Tail</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<%String sitePath= request.getContextPath();%>
<footer class="site-footer">
    <div class="main-footer">
        <div class="main-footer-wrapper">
            <div class="main-footer-logo">
                <a href="<%=sitePath%>" class="site-logo-link"></a>
            </div>
            <div class="main-footer-menu">
                <div class="main-footer-menu-container">
                    <div class="main-footer-menu-head" onclick="openTail('Info')">
                        Informazioni su StudentSupps
                        <i class="fa fa-caret-down" ></i>
                    </div>
                    <ul class="main-footer-nav-link" style="height:0px" id="navLinkInfo">
                        <li class="main-footer-link">
                            <a href="" title="chi siamo">Chi siamo</a>
                        </li>
                        <li class="main-footer-link">
                            <a href="" title="Cosa è StudentSupps?">Cosa è StudentSupps?</a>
                        </li>
                        <li class="main-footer-link">
                            <a href="" title="Newsletter">Newsletter</a>
                        </li>
                    </ul>
                </div>
                <div class="main-footer-menu-container">
                    <div class="main-footer-menu-head" onclick="openTail('Help')">
                        Servizio Clienti
                        <i class="fa fa-caret-down" ></i>
                    </div>
                    <ul class="main-footer-nav-link" style="height:0px" id="navLinkHelp">
                        <li class="main-footer-link">
                            <a href="" title="Assistenza">Assistenza</a>
                        </li>
                        <li class="main-footer-link">
                            <a href="" title="Pagamenti e spedizioni">Pagamenti e spedizioni</a>
                        </li>
                        <li class="main-footer-link">
                            <a href="" title="F.A.Q">F.A.Q</a>
                        </li>
                    </ul>
                </div>
                <div class="main-footer-menu-container">
                    <div class="main-footer-menu-head" style="cursor: default;">Social media</div>
                    <ul class="main-footer-nav-link">
                        <li class="main-footer-Slink">
                            <a class="fa fa-stack" href="" title="twitter" >
                                <i class="fa fa-square fa fa-stack-2x"  style="color:#620000" ></i>
                                <i class="fa fa-twitter fa fa-stack-1x" style="color:#fff" ></i>
                            </a>
                        </li>
                        <li class="main-footer-Slink">
                            <a class="fa fa-stack" href="" title="instagram" >
                                <i class="fa fa-square fa fa-stack-2x"  style="color:#620000" ></i>
                                <i class="fa fa-instagram fa fa-stack-1x" style="color:#fff" ></i>
                            </a>
                        </li>
                        <li class="main-footer-Slink">
                            <a class="fa fa-stack" href="" title="facebook" >
                                <i class="fa fa-square fa fa-stack-2x"  style="color:#620000" ></i>
                                <i class="fa fa-facebook fa fa-stack-1x" style="color:#fff" ></i>
                            </a>
                        </li>
                    </ul>
                </div>

            </div>
        </div>
    </div>
</footer>
<script>
    function openTail(x){
        let navlink=document.getElementById("navLink"+x);
        if (navlink.style.height==="0px")
            navlink.style.height="auto";
        else
            navlink.style.height="0px";
    }
</script>
</body>
</html>
