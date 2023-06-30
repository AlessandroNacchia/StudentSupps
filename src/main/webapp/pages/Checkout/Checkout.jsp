<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page import="java.time.LocalDate" %>
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
                <form action="<%=request.getContextPath()%>/Cart/Checkout" method="post" style="margin:0">
                    <div class="form-field form-botBorder">
                        <div class="form-field-multipleRadio" onclick="openRadios('Indirizzi')">
                            Indirizzi
                            <i class="fa fa-caret-down" ></i>
                        </div>
                        <ul class="form-field-nav-radio" style="height:0px" id="navRadioIndirizzi">
                            <%List<Indirizzo> indList= IndirizzoDAO.doRetrieveByUserId(user.getId());
                            if(indList.size() == 0) {%>
                                <div class="form-field-navEmpty">
                                    <h3>Non hai nessun indirizzo salvato!</h3>
                                    <input type="hidden" oninvalid="this.setCustomValidity('Devi aggiungere un indirizzo!')" required>
                                    <a href="Cart/Checkout/AddAddress" class="buttonPrimary buttonHover">Aggiungi indirizzo</a>
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
                        </ul>
                    </div>
                    <div class="form-field form-botBorder">
                        <div class="form-field-multipleRadio" onclick="openRadios('MetodiPagamento')">
                            Metodi di pagamento
                            <i class="fa fa-caret-down" ></i>
                        </div>
                        <ul class="form-field-nav-radio" style="height:0px" id="navRadioMetodiPagamento">
                            <%List<Metodopagamento> mpList= MetodopagamentoDAO.doRetrieveByUserId(user.getId());
                            if(mpList.size() == 0) {%>
                                <div class="form-field-navEmpty">
                                    <h3>Non hai nessun metodo di pagamento salvato!</h3>
                                    <input type="hidden" oninvalid="this.setCustomValidity('Devi aggiungere un indirizzo!')" required>
                                    <a href="Cart/Checkout/AddPayMethod" class="buttonPrimary buttonHover">Aggiungi metodo di pagamento</a>
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
                        </ul>
                    </div>
                </form>
            </section>
            <section class="checkout-section">

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
