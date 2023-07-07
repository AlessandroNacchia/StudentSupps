<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tsw.studentsupps.Model.*" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List"%>

<html>
<head>
    <title>Prodotto | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/product.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/formContainer.css">
</head>
<body>
<jsp:include page="/ReusedHTML/head.jsp"/>
<%Prodotto p= (Prodotto) request.getAttribute("prodotto");
  Utente   u= (Utente)   session.getAttribute("Utente");
  List<Recensione> ReviewList= (List<Recensione>) request.getAttribute("recensioni");%>
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
                   <span class="product-price-value"><%=p.getPrezzo()%>&nbsp;€</span>
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
                <div class="product-review-header" onclick="openTabContent('add-Review'); openTabContent('Reviews') ;" >
                    <div>
                        Recensioni
                        <i class="fa fa-caret-down" ></i>
                    </div>
                    <%if(!ReviewList.isEmpty()) {
                        int count=0;
                        double media=0;
                        for (Recensione r: ReviewList){
                            media+=r.getVoto();
                            count++;
                        }media=media/count;%>
                    <div class="media-review-rating">
                        <%int i=0;
                            for (;i<Math.round(media);i++){%>
                        <span class="stella-piena">
                            <i class="fa fa-star"></i>
                        </span>
                        <%}
                            for (;i<5;i++){%>
                        <span class="stella-vuota">
                            <i class="fa fa-star-o"></i>
                        </span>
                        <%}%>
                    </div>
                    <%}%>
                </div>
                <div class="product-review-content" style="height:0px" id="tab-content-add-Review">
                    <%if(u!= null){%>
                    <header class="button-add-review" >
                        <button class="buttonPrimary buttonHover" id="button-review" type="submit" onclick="openTabContentB('form-review')">Scrivi la tua recensione</button>
                    </header>
                    <div class="formContainer " id="tab-content-form-review"   style="height:0px; border: 0px solid #737373; margin:0px; padding:0px;">
                        <h1 class="formContainer-title " style="text-align: center;">Scrivi qui i dettagli della tua recensione</h1>
                        <div class="formContainer-wrapper" id="formContainer-wrapper-review" >
                            <form action="<%=request.getContextPath()%>/Shop/Prodotto/Review" method="post">
                                <c:if test="${requestScope.reviewStatus=='autoreWrongPattern'}">
                                    <p style="color:red">Pattern Autore Errato</p>
                                </c:if>
                                <c:if test="${requestScope.reviewStatus=='descrizioneWrongPattern'}">
                                    <p style="color:red">Pattern Descrizione Errato</p>
                                </c:if>
                                <c:if test="${requestScope.reviewStatus=='votoWrongPattern'}">
                                    <p style="color:red">Pattern Voto Errato</p>
                                </c:if>
                                <section class="form-field">
                                    <label class="form-field-label" for="authoradd" style="justify-content: center" >Autore</label>
                                    <input class="form-field-input" id="authoradd" name="author" readonly value="<%=u.getUsername()%>" autocomplete="off" type="text" >
                                </section>
                                <section class="form-field">
                                    <label class="form-field-label" style="justify-content: center" >Voto</label>
                                    <div class="rating" >

                                        <input id="rating-5" type="radio" name="rating" value="5"/><label for="rating-5"><i class=" fa fa-star"></i></label>
                                        <input id="rating-4" type="radio" name="rating" value="4"/><label for="rating-4"><i class=" fa fa-star"></i></label>
                                        <input id="rating-3" type="radio" name="rating" value="3"/><label for="rating-3"><i class=" fa fa-star"></i></label>
                                        <input id="rating-2" type="radio" name="rating" value="2"/><label for="rating-2"><i class=" fa fa-star"></i></label>
                                        <input id="rating-1" type="radio" name="rating" value="1" checked/><label for="rating-1"><i class="fa fa-star"></i></label>
                                    </div>
                                </section>
                                <section class="form-field">
                                    <label class="form-field-label" style="justify-content: center"  for="description">Descrizione</label>
                                    <textarea class="form-field-input" style="height: auto; resize: none;" id="description" name="description" rows="5" maxlength="1000" autocomplete="off" required></textarea>
                                    <div class="form-field-comment">
                                        Minimo 2 caratteri. Massimo 1000 caratteri.
                                    </div>
                                </section>
                                <input type="hidden" name="prodId" value="<%=p.getId()%>">
                                <button class="buttonPrimary buttonHover" onclick="return (confermaParametri())"  type="submit">Pubblica Recensione</button>

                            </form>
                        </div>
                    </div>


                    <%}%>

                </div>
                <div class="product-reviews" style="height:0px" id="tab-content-Reviews">
                    <%if(!ReviewList.isEmpty()) {
                        for (Recensione r: ReviewList){%>

                            <article class="review" id="reviewId<%=r.getId()%>">
                                <header class="review-author">
                                     <h2 class="review-author-text">Recensione di  <%=r.getAutore()%></h2>
                                </header>
                                <div class="review-rating">
                                    <%int i=0;
                                    for (;i<r.getVoto();i++){%>
                                        <span class="stella-piena">
                                            <i class="fa fa-star"></i>
                                        </span>
                                    <%}
                                    for (;i<5;i++){%>
                                        <span class="stella-vuota">
                                            <i class="fa fa-star-o"></i>
                                        </span>
                                    <%}%>
                                </div>
                                <div class="review-text">
                                    <p>
                                        <%=r.getDescrizione()%>
                                    </p>

                                </div>
                                <%if(u!=null && (u.getUsername().equals(r.getAutore()) || u.isAdmin())) {%>
                                <div class="button-delete-review">
                                    <button class="buttonPrimary buttonHover trashButtonReview"  type="button" onclick="return deleteReview('<%=r.getId()%>')">
                                        <i class="fa fa-trash"></i>
                                    </button>
                                </div>
                                <%}%>
                            </article>
                        <%}%>

                    <%}else{%>
                        <p>Non sono presenti delle recensioni per questo prodotto</p>
                    <%}%>
                </div>
            </div>

        </section>
    </section>
</main>



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

    function openTabContentB(x){
        let navlink=document.getElementById("tab-content-"+x);
        let button=document.getElementById("button-review")
        if (navlink.style.height==="0px"){
            navlink.style.height="auto";
            navlink.style.border="1px solid #737373";
            navlink.style.removeProperty("padding");
            navlink.style.removeProperty("margin");
            button.innerText="Annulla";
        }

        else{navlink.style.height="0px";
            navlink.style.border="0px solid #737373";
            navlink.style.margin="0px";
            navlink.style.padding="0px";
            button.innerText="Scrivi la tua recensione";
        }
    }
    <%if(u!=null){%>
    function confermaParametri() {
        let autore=document.getElementById("authoradd").value;
        let voto=document.getElementsByName("rating");
        let descr= document.getElementById('description').value;

        const descrRGX= /^.{2,1000}$/;
        const votoRGX= /^\d$/;

        if (autore!=='<%=u.getUsername()%>'){
            alert("Autore non valido!");
            return false;
        }

        if(!descrRGX.test(descr)){
            alert("Descrizione non valida!");
            return false;
        }

        voto.forEach(v=>{
            if(!votoRGX.test(v.value)){
                alert("Voto non valido!");
                return false;
            }

            if(v.value>5){
                v.value=5;
            }
            if(v.value<1){
                v.value=1;
            }
        })

        return true;
    }
    <%}%>

    function deleteReview(id) {
        if(!confirm('Sei sicuro di voler eliminare questa recensione?'))
            return false;

        let xhttp= new XMLHttpRequest();
        xhttp.onreadystatechange= function() {
            if (this.readyState === 4 && this.status === 200) {
                document.getElementById('reviewId'+id).remove();
            }
        };

        xhttp.open("POST", "<%=request.getContextPath()%>/Shop/Prodotto/DeleteReview", true);
        xhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded")
        xhttp.setRequestHeader("connection", "close")
        xhttp.send("id="+id);
    }
</script>

<jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
