<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Conferma Ordine | StudentSupps</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/checkout.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <%
        List<String> productsList= (List<String>) request.getAttribute("prodList");
        Carrello cart= (Carrello) session.getAttribute("Cart");
        Utente user= (Utente) session.getAttribute("Utente");

        if(productsList.size()==0) {
    %>

    <%} else {%>
        <main class="checkoutMain">
            <h1 class="checkout-title">
                <i class="fa fa-check" aria-hidden="true"></i>
                Conferma acquisto
            </h1>
            <section class="checkout-section">
                <h2 class="checkout-subtitle">Dati ordine</h2>
                <form action="<%=request.getContextPath()%>/Cart/Checkout" method="post" style="margin:0">
                    <div class="form-field form-botBorder">
                        <div class="form-field-multipleRadio" onclick="openRadios('Indirizzi')">
                            <h3 style="display: inline;">Indirizzi</h3>
                            <i class="fa fa-caret-down" style="float: right;"></i>
                        </div>
                        <ul class="form-field-nav-radio" style="height:0px" id="navRadioIndirizzi">
                            <%List<Indirizzo> indList= IndirizzoDAO.doRetrieveByUserId(user.getId());
                            if(indList.size() == 0) {%>
                                <div class="form-field-navEmpty">
                                    <h4 style="margin-bottom: 5px;">Non hai nessun indirizzo salvato!</h4>
                                    <label>
                                        <input class="hiddenInput" oninvalid="this.setCustomValidity('Devi aggiungere un indirizzo!')" required>
                                    </label>
                                </div>
                            <%} else {
                                for(Indirizzo ind: indList) {%>
                                    <li class="form-field-radio">
                                        <input class="form-field-input" style="height: auto; width: auto;" id="address<%=ind.getId()%>"
                                               name="address" type="radio" value="<%=ind.getId()%>" required>
                                        <label class="form-field-label" style="display: inline; margin-left: 5px;" for="address<%=ind.getId()%>">
                                            <%=ind.getVia()%>, <%=ind.getCitta()%>, <%=ind.getProvincia()%>, <%=ind.getCAP()%>, <%=ind.getNazione()%>,
                                            Numero di telefono: <%=ind.getNumeroTel()%>
                                        </label>
                                    </li>
                                <%}
                            }%>
                            <a href="Cart/Checkout/AddAddress" class="buttonPrimary buttonSecondary buttonHover">Aggiungi indirizzo</a>
                        </ul>
                    </div>
                    <div class="form-field form-botBorder">
                        <div class="form-field-multipleRadio" onclick="openRadios('MetodiPagamento')">
                            <h3 style="display: inline;">Metodi di pagamento</h3>
                            <i class="fa fa-caret-down" style="float: right;"></i>
                        </div>
                        <ul class="form-field-nav-radio" style="height:0px" id="navRadioMetodiPagamento">
                            <%List<Metodopagamento> mpList= MetodopagamentoDAO.doRetrieveByUserId(user.getId());
                            if(mpList.size() == 0) {%>
                                <div class="form-field-navEmpty">
                                    <h4 style="margin-bottom: 5px;">Non hai nessun metodo di pagamento salvato!</h4>
                                    <label>
                                        <input class="hiddenInput" oninvalid="this.setCustomValidity('Devi aggiungere un metodo di pagamento!')" required>
                                    </label>
                                </div>
                            <%} else {
                                for(Metodopagamento mp: mpList) {%>
                                    <li class="form-field-radio">
                                        <input class="form-field-input" style="height: auto; width: auto;" id="payMethod<%=mp.getId()%>"
                                               name="payMethod" type="radio" value="<%=mp.getId()%>" required>
                                        <label class="form-field-label" style="display: inline; margin-left: 5px;" for="payMethod<%=mp.getId()%>">
                                            <%=mp.getProvider()%>, termina con <%=mp.getLastDigits()%>
                                            <%LocalDate date= mp.getDataScadenza().toLocalDate();%>
                                            Data di scadenza: <%=date.getMonthValue()%>/<%=date.getYear()%>
                                        </label>
                                    </li>
                                <%}
                            }%>
                            <a href="Cart/Checkout/AddPayMethod" class="buttonPrimary buttonSecondary buttonHover">Aggiungi metodo di pagamento</a>
                        </ul>
                    </div>
                    <button class="buttonPrimary buttonHover" type="submit">Completa l'acquisto</button>
                </form>
            </section>
            <section class="checkout-section">
                <h2 class="checkout-subtitle">Riassunto ordine</h2>
                <section>
                    <%for (String prodId: productsList) {
                        Prodotto p= ProdottoDAO.doRetrieveById(prodId);
                        int quantita= ProdottocarrelloDAO.doRetrieveQuantita(cart.getId(), prodId);%>

                        <div class="checkout-summary-product">
                            <div class="checkout-summary-productImage">
                                <picture>
                                    <img src="<%=request.getContextPath() + "/ProductImages/" + p.getNome() + ".png"%>" alt="<%=p.getNome()%>" title="<%=p.getNome()%>">
                                </picture>
                            </div>
                            <div class="checkout-summary-productInfo">
                                <div class="checkout-summary-productInfo-name"><%=p.getNome()%></div>
                                <div class="checkout-summary-productInfo-price">
                                    <span><%=(BigDecimal.valueOf(p.getPrezzo()).multiply(BigDecimal.valueOf(quantita))).doubleValue()%>&nbsp;€</span>
                                    <span style="float: right;">(Prezzo singolo: <%=p.getPrezzo()%>&nbsp;€)</span>
                                </div>
                                <div class="checkout-summary-productInfo-quantity">
                                    <span>Quantità: <%=quantita%></span>
                                </div>
                            </div>
                        </div>
                    <%}%>
                </section>
                <div class="cart-summary">
                    <header class="cart-summary-head">
                        <span>Riassunto</span>
                        <span><%=productsList.size()%> Prodotti</span>
                    </header>
                    <div class="cart-summary-item">
                        <span>Totale parziale</span>
                        <span><%=cart.getTotale()%>&nbsp;€</span>
                    </div>
                    <div class="cart-summary-item">
                        <span>
                            Spedizione
                            <span class="cart-summary-itemDescription">(3-5 Giorni Lavorativi)</span>
                        </span>
                        <span>Gratis</span>
                    </div>
                    <div class="cart-summary-item cart-summary-total">
                        <span>Totale</span>
                        <span><%=cart.getTotale()%>&nbsp;€</span>
                    </div>
                </div>
            </section>
        </main>
    <%}%>

    <script>
        function openRadios(x){
            let navlink=document.getElementById("navRadio"+x);
            if (navlink.style.height==="0px")
                navlink.style.height="auto";
            else
                navlink.style.height="0px";
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
