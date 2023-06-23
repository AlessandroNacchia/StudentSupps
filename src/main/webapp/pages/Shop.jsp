<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.Prodotto" %>
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

    <%List<Prodotto> productsList= (List<Prodotto>) request.getAttribute("prodotti");%>
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
            <%for (Prodotto p: productsList)
                if(p.getQuantita()>0) {%>
                    <article class="productBox">
                        <a class="productBox-image" href="Shop/Prodotto?prodName=<%=p.getNome()%>">
                            <figure class="imageWrapper">
                                <picture>
                                    <img src="<%="images/products/" + p.getNome() + ".png"%>" alt="<%=p.getNome()%>" title="<%=p.getNome()%>">
                                </picture>
                            </figure>
                        </a>
                        <div class="productBox-wrapper">
                            <h3><a class="productBox-title" href="Shop/Prodotto?prodName=<%=p.getNome()%>"><%=p.getNome()%></a></h3>
                            <div class="productBox-price">
                                <span><%=p.getPrezzo()%>&nbsp;€</span>
                            </div>
                            <form action="Cart" method="post">
                                <input type="hidden" name="prodToAdd" value="<%=p.getId()%>">
                                <input type="hidden" name="callerPage" value="Shop">
                                <button class="buttonPrimary buttonHover" type="submit">Aggiungi al Carrello</button>
                            </form>
                        </div>
                    </article>
                <%}%>
        </section>
    </main>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
