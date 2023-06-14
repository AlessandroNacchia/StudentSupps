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
        </section>
    </main>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
