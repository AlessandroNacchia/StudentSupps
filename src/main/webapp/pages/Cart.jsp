<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page import="java.math.BigDecimal" %>
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

        if(productsList.size()==0) {
    %>
        <main class="cart">
            <header class="cart-header">
                <h1 class="cart-header-title">Il tuo Carrello</h1>
            </header>
            <section class="cart-main-empty">
                <span class="cart-main-emptyIcon"><i class="fa fa-shopping-cart"></i></span>
                <h2 class="cart-main-emptyMessage">Non ci sono prodotti nel carrello</h2>
                <div class="cart-main-emptyBtn">
                    <a href="Shop" class="buttonPrimary buttonHover">Torna al negozio</a>
                </div>
            </section>
        </main>
    <%  } else {%>
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
                    <header class="cart-products-row">
                        <div class="cart-products-col">Immagine</div>
                        <div class="cart-products-col">Prodotto</div>
                        <div class="cart-products-col">Prezzo Singolo</div>
                        <div class="cart-products-col">Quantità</div>
                        <div class="cart-products-col cart-products-colTotal">Prezzo Totale</div>
                    </header>
                    <%for (String prodId: productsList) {
                        Prodotto p= ProdottoDAO.doRetrieveById(prodId);
                        int quantita= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), prodId);
                    %>
                        <div class="cart-products-row">
                            <div class="cart-products-colImage">
                                <a href="Shop/Prodotto?prodName=<%=p.getNome()%>">
                                    <picture>
                                        <img src="<%="images/products/" + p.getNome() + ".png"%>" alt="<%=p.getNome()%>" title="<%=p.getNome()%>">
                                    </picture>
                                </a>
                            </div>
                            <div class="cart-products-colProduct">
                                <h3><a href="Shop/Prodotto?prodName=<%=p.getNome()%>"><%=p.getNome()%></a></h3>
                                <div class="cart-products-colProduct-descr"><%=p.getDescrizione()%></div>
                                <div class="cart-products-colProduct-remove">
                                    <form action="Cart" method="post" style="margin-bottom: 0;">
                                        <input type="hidden" name="prodToRemove" value="<%=p.getId()%>">
                                        <input type="hidden" name="callerPage" value="Cart">
                                        <button class="buttonPrimary buttonHover" type="submit">Rimuovi</button>
                                    </form>
                                </div>
                            </div>
                            <div class="cart-products-colPrice">
                                <span><%=p.getPrezzo()%>&nbsp;€</span>
                            </div>
                            <div class="cart-products-colQuantity">
                                <form action="Cart" method="post" id="quantitaForm" style="margin-bottom: 0;">
                                    <input type="hidden" name="prodToUpdate" value="<%=p.getId()%>">
                                    <input type="hidden" name="callerPage" value="Cart">
                                    <button class="cart-products-colQuantity-btn" type="submit" name="updateType" value="remove">
                                        <i class="fa fa-minus"></i>
                                    </button>
                                    <input class="cart-products-colQuantity-input" name="updateQuantity" value="<%=quantita%>" pattern=""
                                           onkeydown="submitOnEnter(this.form)" onblur="submit()" autocomplete="off" disabled>
                                    <button class="cart-products-colQuantity-btn" type="submit" name="updateType" value="add">
                                        <i class="fa fa-plus"></i>
                                    </button>
                                </form>
                            </div>
                            <div class="cart-products-colTotal">
                                <span><%=(BigDecimal.valueOf(p.getPrezzo()).multiply(BigDecimal.valueOf(quantita)))%>&nbsp;€</span>
                            </div>
                        </div>
                    <%}%>
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
    <%  }%>

    <jsp:include page="/ReusedHTML/tail.jsp"/>

    <script>
        let colQntInput= document.getElementsByClassName('cart-products-colQuantity-input');
        for(let i=0; i<colQntInput.length; i++)
            colQntInput[i].removeAttribute('disabled');

        function submitOnEnter(formEl) {
            if (event.keyCode === 13) {
                event.preventDefault();
                formEl.submit();
            }
        }
    </script>
</body>
</html>
