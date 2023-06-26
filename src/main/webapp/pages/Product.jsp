<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prodotto | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <!--<link rel="stylesheet" href="CSS/product.css">-->
</head>
<body>
<jsp:include page="/ReusedHTML/head.jsp"/>
<%Prodotto p= (Prodotto) request.getAttribute("prodotto");%>
<main class="product-page">
    <figure class="product-page-image">
        <div class="product-image-wrapper">
            <picture>
                <img src="src="<%=request.getContextPath() + "/ProductImages/" + p.getNome() + ".png"%>" alt="<%=p.getNome()%>" title="<%=p.getNome()%>">
            </picture>
        </div>

    </figure>
   <header product-page-section>
       <div class="product-details">
           <div class="product-details-name"><%=p.getNome()%></div>
           <div class="product-details-rating"></div>
       </div>
       <section class="product-cart-controls">
           <div class="product-price">
               <span class="product-price-value"><%=p.getPrezzo()%></span>
           </div>
           <div class="product-addtocart-section">
               <strong class="product-quantity-selector-label">Quantità</strong>
               <div class="product-quantity-selector">
                   <!--<form action="<%=request.getContextPath()%>/Cart" method="post" id="quantitaForm" style="margin-bottom: 0;">
                       <input type="hidden" name="prodToUpdate" value="<%=p.getId()%>">
                       <input type="hidden" name="callerPage" value="Shop/Prodotto?<%=p.getId()%>">
                       <button class="cart-products-colQuantity-btn" type="submit" name="updateType" value="remove">
                           <i class="fa fa-minus"></i>
                       </button>
                       <input class="cart-products-colQuantity-input" type="number" name="updateQuantity" value="1"
                              onkeydown="submitOnEnter(this.form)" onblur="submit()" autocomplete="off" disabled>
                       <button class="cart-products-colQuantity-btn" type="submit" name="updateType" value="add">
                           <i class="fa fa-plus"></i>
                       </button>
                   </form>-->
               </div>
               <div class="product-addtocart">
                   <form action="<%=request.getContextPath()%>/Cart" method="post">
                       <input type="hidden" name="prodToAdd" value="<%=p.getId()%>">
                       <input type="hidden" name="callerPage" value="Cart">
                       <button class="buttonPrimary buttonHover" type="submit">Aggiungi al Carrello</button>
                   </form>
               </div>
           </div>
       </section>
       <ul class="product-details-general-info">
          <li>
              spedizione gratuita dal lunedi al venerdi
          </li>
       </ul>
   </header>
    <section class="product-page-info">
        <header class="product-tab-header">
            <h2>Info sul prodotto</h2>
        </header>
        <section class="product-tab-content">
            <div class="product-info-header">StudentSupps <%=p.getNome()%></div>
            <div class="product-info-description">
                <p><%=p.getDescrizione()%></p>
            </div>
            <div class="prduct-review-header"></div>
            <div class="product-review-content"
        </section>
    </section>
</main>
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
