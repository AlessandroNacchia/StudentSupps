<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prodotto | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/product.css">
</head>
<body>
<jsp:include page="/ReusedHTML/head.jsp"/>
<%Prodotto p= (Prodotto) request.getAttribute("prodotto");%>
<main class="product-page">
    <figure class="product-page-image">
        <div class="product-image-wrapper">
            <picture>
                <img src="<%=request.getContextPath() + "/images/products/" + p.getNome() + ".png"%>" alt="<%=p.getNome()%>" title="<%=p.getNome()%>">
            </picture>
        </div>

    </figure>
   <header class="product-page-section">
       <div class="product-details">
           <div class="product-details-name"><%=p.getNome()%></div>
           <div class="product-details-rating"></div>
       </div>
       <section class="product-cart-controls">
           <div class="product-price">
               <div class="product-price-primary">
                   <span class="product-price-value"><%=p.getPrezzo()%></span>
               </div>
           </div>
           <div class="product-addtocart-section">
               <strong class="product-quantity-selector-label">Quantità</strong>
               <form action="<%=request.getContextPath()%>/Cart" method="post"  style="margin-bottom: 0;">
                   <div class="product-quantity-selector">
                       <div class="product-selector-quantity">

                        <input type="hidden" name="prodToAdd" value="<%=p.getId()%>">
                        <input type="hidden" name="callerPage" value="Cart">
                        <button class="quantity-selector-button-decrease" type="button">
                            <i class="fa fa-minus"></i>
                        </button>
                        <input class="quantity-selector-input" onblur="quantity_limit()" type="number" name="quantityToAdd" step="1" value="1" size="3">
                        <button class="quantity-selector-button-increase" type="button">
                            <i class="fa fa-plus"></i>
                        </button>
                       </div>
                   </div>
                   <div class="product-addtocart">
                       <button class="buttonPrimary buttonHover" type="submit">Aggiungi al Carrello</button>
                   </div>
               </form>
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
            <div class="product-review-content"></div>
        </section>
    </section>
</main>

<jsp:include page="/ReusedHTML/tail.jsp"/>

<script>

    let plus_b=document.querySelectorAll('.quantity-selector-button-increase');
    let minus_b=document.querySelectorAll('.quantity-selector-button-decrease');
    let quantity=document.querySelectorAll('.quantity-selector-input');

    plus_b.forEach(btn=>{
        btn.addEventListener('click', ()=>{
            btn.previousElementSibling.value = (btn.previousElementSibling.value == 99) ? 99 : ++btn.previousElementSibling.value;
        })
    })

    minus_b.forEach(btn=>{
        btn.addEventListener('click', ()=>{
            btn.nextElementSibling.value = (btn.nextElementSibling.value == 0) ? 0 : btn.nextElementSibling.value - 1;
        })
    })

    function quantity_limit() {
        quantity.forEach(qnt=>{
            if(qnt.value>=99){
                qnt.value=99;
            }
            if(qnt.value<0){
                qnt.value=0;
            }
        })

    }

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
