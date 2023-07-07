<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
<%@ page import="org.json.simple.parser.ParseException" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Shop | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="CSS/siteStyle.css">
    <link rel="stylesheet" href="CSS/shop.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <%
        JSONParser parser= new JSONParser();
        JSONArray productsList;
        try {
            String prodottiJSON= (String) request.getAttribute("prodottiJSON");
            productsList= (JSONArray) parser.parse(prodottiJSON);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    %>
    <main class="products">
        <header class="productsHeader">
            <h1 class="productsHeader-title">Prodotti</h1>
            <div class="productsHeader-descr">
                <div class="productsHeader-left">
                    <%=productsList.size()%> Prodotti
                </div>
                <div class="productsHeader-right">

                </div>
            </div>
        </header>
        <section class="productsSlots">
            <%for (Object o: productsList) {
                JSONObject p= (JSONObject) o;
                if(Integer.parseInt(p.get("quantita").toString())>0) {%>
                    <article class="productBox">
                        <a class="productBox-image" href="Shop/Prodotto?prodName=<%=p.get("nome")%>">
                            <figure class="imageWrapper">
                                <picture>
                                    <img src="<%=request.getContextPath() + "/ProductImages/" + p.get("nome") + ".png"%>" alt="<%=p.get("nome")%>" title="<%=p.get("nome")%>">
                                </picture>
                            </figure>
                        </a>
                        <div class="productBox-wrapper">
                            <h3><a class="productBox-title" href="Shop/Prodotto?prodName=<%=p.get("nome")%>"><%=p.get("nome")%></a></h3>
                            <div class="productBox-price">
                                <span><%=p.get("prezzo")%>&nbsp;€</span>
                            </div>
                            <form action="Cart" method="post">
                                <input type="hidden" name="prodToAdd" value="<%=p.get("id")%>">
                                <input type="hidden" name="callerPage" value="Shop">
                                <button class="buttonPrimary buttonHover" type="submit">Aggiungi al Carrello</button>
                            </form>
                        </div>
                    </article>
                <%}
            }%>
        </section>
    </main>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
