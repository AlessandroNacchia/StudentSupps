<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Shop | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="CSS/siteStyle.css">
    <link rel="stylesheet" href="CSS/shop.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <main class="products">
        <header class="productsHeader">
            <h1 class="productsHeader-title">Prodotti</h1>
            <div class="productsHeader-descr">
                <div class="productsHeader-left" id="prodNumber"></div>
                <div class="productsHeader-right"></div>
            </div>
        </header>
        <section class="productsSlots" id="prodSlots"></section>
    </main>

    <script>
        $(document).ready(function() {
            const urlParams= new URLSearchParams(window.location.search);
            const contextPath= "<%=request.getContextPath()%>";
            $.ajax({
                type: 'POST',
                url: contextPath + '/LoadShopProducts',
                dataType: "json",
                async: true,
                data: {
                    filter: urlParams.get("filter")
                },
                success: function(data) {
                    console.log(data);
                    let prodSlots = ('#prodSlots');
                    let prodNumber = ('#prodNumber');
                    $(prodNumber).append(data.length+' Prodotti')

                    for (let i= 0; i<data.length; i++) {
                        let newArticle= document.createElement("article");
                        newArticle.className= "productBox";

                        newArticle.innerHTML=`
                        <a class="productBox-image" href="Shop/Prodotto?prodName=`+data[i].nome+`">
                            <figure class="imageWrapper">
                                <picture>
                                    <img src="`+contextPath+`/ProductImages/`+data[i].nome+`.png"
                                            class="imgProdErr" alt="`+data[i].nome+`" title="`+data[i].nome+`">
                                </picture>
                            </figure>
                        </a>
                        <div class="productBox-wrapper">
                            <h3><a class="productBox-title" href="Shop/Prodotto?prodName=`+data[i].nome+`">`+data[i].nome+`</a></h3>
                            <div class="productBox-price">
                                <span>`+data[i].prezzo+`&nbsp;€</span>
                            </div>
                            <form action="Cart" method="post">
                                <input type="hidden" name="prodToAdd" value="`+data[i].id+`">
                                <input type="hidden" name="callerPage" value="Shop">
                                <button class="buttonPrimary buttonHover" type="submit">Aggiungi al Carrello</button>
                            </form>
                        </div>`

                        if(data[i].prezzo !== data[i].prezzoScontato) {
                            newArticle.getElementsByClassName("productBox-price")[0].innerHTML=`
                            <span><s>`+data[i].prezzo+`&nbsp;€</s></span>
                            <span style="color: #620000;margin-left: 10px;">`+data[i].prezzoScontato+`&nbsp;€</span>`
                        }

                        $(prodSlots).append(newArticle);
                    }

                    let imgProds= document.querySelectorAll('.imgProdErr');
                    imgProds.forEach(img=>{
                        img.addEventListener('error', ()=>{
                            img.src="<%=request.getContextPath()%>/images/img_notfound.png";
                            img.alt="Immagine non trovata";
                            img.title="Immagine non trovata";
                        })
                    })
                }
            });
        });
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
