<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.ProdottocarrelloDAO" %>
<%@ page import="com.tsw.studentsupps.Model.Carrello" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Carrello | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="CSS/siteStyle.css">
    <link rel="stylesheet" href="CSS/cart.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <%
        List<String> productsList= (List) request.getAttribute("prodList");
        Carrello cart= (Carrello) session.getAttribute("Cart");
    %>
    <main class="cart">
        <header class="cart-header">
            <h1 class="cart-header-title">Il tuo Carrello</h1>
            <section class="cart-header-right">
                <div class="cart-header-actions">
                    <div class="cart-header-actionShop">
                        <a href="Shop" class="buttonPrimary buttonSecondary buttonHover">Torna al negozio</a>
                    </div>
                    <div class="cart-header-actionCheckout">
                        <a href="Cart/Shop" class="buttonPrimary buttonHover">Procedi all'acquisto</a>
                    </div>
                </div>
            </section>
        </header>
        <section class="cart-main">
            <aside class="cart-products">

            </aside>
            <section class="cart-side">
                <div class="cart-sideSummary">
                    <header class="cart-sideSummary-head">
                        <span>Riassunto</span>
                        <span><%=productsList.size()%> Prodotti</span>
                    </header>
                    <div class="cart-sideSummary-item">
                        <span>Totale parziale</span>
                        <span><%=cart.getTotale()%>&nbsp;€</span>
                    </div>
                    <div class="cart-sideSummary-item">
                        <span>
                            Spedizione
                            <span class="cart-sideSummary-itemDescription">(3-5 Giorni Lavorativi)</span>
                        </span>
                        <span>Gratis</span>
                    </div>
                    <div class="cart-sideSummary-item cart-sideSummary-total">
                        <span>Totale</span>
                        <span><%=cart.getTotale()%>&nbsp;€</span>
                    </div>
                    <div class="cart-sideSummary-checkout">
                        <a href="Cart/Shop" class="buttonPrimary buttonHover">Procedi all'acquisto</a>
                    </div>
                </div>
            </section>
        </section>
    </main>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
