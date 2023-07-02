<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page contentType="text/html;charset=UTF-8"%>
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
<%Prodotto p= (Prodotto) request.getAttribute("prodotto");
  Utente   u= (Utente)   request.getAttribute("Utente");%>
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
           <form action="<%=request.getContextPath()%>/Cart" method="post"  style="margin-bottom: 0;">
               <div class="product-addtocart-section">
                   <strong class="product-quantity-selector-label">Quantità</strong>
                   <div class="product-quantity-selector">
                       <div class="product-selector-quantity">
                           <input type="hidden" name="prodToAdd" value="<%=p.getId()%>">
                           <input type="hidden" name="callerPage" value="Cart">
                           <button class="quantity-selector-button-decrease" type="button">
                               <i class="fa fa-minus"></i>
                           </button>
                           <label>
                               <input class="quantity-selector-input" onblur="quantity_limit()" type="number" name="quantityToAdd" step="1" value="1" size="3">
                           </label>
                           <button class="quantity-selector-button-increase" type="button">
                               <i class="fa fa-plus"></i>
                           </button>
                       </div>
                   </div>
                   <div class="product-addtocart">
                       <button class="buttonPrimary buttonHover" type="submit">Aggiungi al Carrello</button>
                   </div>
                </div>
           </form>
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
            <div class="product-tab-content-info">
                <div class="product-info-header" onclick="openTabContent('Info')" >
                    StudentSupps <%=p.getNome()%>
                    <i class="fa fa-caret-down" ></i>
                </div>
                <div class="product-info-description" style="height:0px" id="tab-content-Info">
                    <p><%=p.getDescrizione()%></p>
                </div>
            </div>
            <div class="product-tab-content-review">
                <div class="product-review-header" onclick="openTabContent('Review')">
                    Recensioni
                    <i class="fa fa-caret-down" ></i>
                </div>
                <div class="product-review-content" style="height:0px" id="tab-content-Review">
                    <%if(session.getAttribute("Utente")!= null){%>
                    <button class="buttonPrimary buttonHover" type="submit" onclick="openTabContent('form-review')">Scrivi la tua recensione</button>
                    <div id="tab-content-form-review"   style="height:0px">
                        <form action="<%=request.getContextPath()%>/Shop/Prodotto/Review" method="post">
                            <section class="form-field-review">
                                <label class="form-field-label-review" for="authoradd"></label>
                                <input class="form-field-input-review" id="authoradd" name="author" type="text" required>
                            </section>
                            <section class="form-field-review">
                                <label class="form-field-label-review" for="stars"></label>
                                <input class="form-field-input-review" id="stars" name="author" type="text" required>
                            </section>
                            <section class="form-field-review">
                                <label class="form-field-label-review" for="description"></label>
                                <input class="form-field-input-review" id="description" name="author" type="text" required>
                            </section>
                            <button class="buttonPrimary buttonHover" type="submit">Pubblica Recensione</button>

                        </form>
                    </div>

                    <%}%>

                </div>
            </div>

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
            btn.previousElementSibling.firstElementChild.value = (btn.previousElementSibling.firstElementChild.value === '99') ? 99 : ++btn.previousElementSibling.firstElementChild.value;
        })
    })

    minus_b.forEach(btn=>{
        btn.addEventListener('click', ()=>{
            btn.nextElementSibling.firstElementChild.value = (btn.nextElementSibling.firstElementChild.value === '0') ? 0 : btn.nextElementSibling.firstElementChild.value - 1;
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

    function openTabContent(x){
        let navlink=document.getElementById("tab-content-"+x);
        if (navlink.style.height==="0px")
            navlink.style.height="auto";
        else
            navlink.style.height="0px";
    }
</script>
</body>
</html>
