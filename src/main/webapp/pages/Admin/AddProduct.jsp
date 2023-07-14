<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.Categoria" %>
<%@ page import="com.tsw.studentsupps.Model.CategoriaDAO" %>
<%@ page import="com.tsw.studentsupps.Model.Sconto" %>
<%@ page import="com.tsw.studentsupps.Model.ScontoDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Aggiungi Prodotto | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/formContainer.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <main class="formContainer">
        <h1 class="formContainer-title">Aggiungi i parametri del prodotto</h1>
        <div class="formContainer-wrapper">
            <section class="formContainer-section">
                <c:if test="${requestScope.addProdStatus == 'nameWrongPattern'}">
                    <p style="color: red">Pattern Nome errato!</p>
                </c:if>
                <c:if test="${requestScope.addProdStatus == 'descriptionWrongPattern'}">
                    <p style="color: red">Pattern Descrizione errato!</p>
                </c:if>
                <c:if test="${requestScope.addProdStatus == 'priceWrongPattern'}">
                    <p style="color: red">Pattern Prezzo errato!</p>
                </c:if>
                <c:if test="${requestScope.addProdStatus == 'ivaWrongPattern'}">
                    <p style="color: red">Pattern IVA errato!</p>
                </c:if>
                <c:if test="${requestScope.addProdStatus == 'quantityWrongPattern'}">
                    <p style="color: red">Pattern Quantità errato!</p>
                </c:if>
                <c:if test="${requestScope.addProdStatus == 'imageMissing'}">
                    <p style="color: red">Immagine mancante!</p>
                </c:if>
                <c:if test="${requestScope.addProdStatus == 'nameTaken'}">
                    <p style="color: red">Nome Prodotto già usato!</p>
                </c:if>
                <form action="<%=request.getContextPath()%>/Admin/AddProduct" method="post" enctype="multipart/form-data">
                    <div class="form-field">
                        <label class="form-field-label" for="imageAdd">Immagine</label>
                        <input class="form-field-inputFile" id="imageAdd" name="image" type="file" accept=".png" required>
                        <div class="form-field-comment">
                            Accetta .png.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="nameAdd">Nome</label>
                        <input class="form-field-input" id="nameAdd" name="name" type="text" maxlength="50" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 50 caratteri. Accetta lettere, numeri, spazi, punti, trattini medi, + e parentesi.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="descrAdd">Descrizione</label>
                        <textarea class="form-field-input" style="height: auto; resize: none;" id="descrAdd" name="description" rows="5" maxlength="1000" autocomplete="off" required></textarea>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 1000 caratteri.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="priceAdd">Prezzo</label>
                        <input class="form-field-input" id="priceAdd" name="price" type="number" min="0" max="9999999" step="0.01" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 0. Massimo 9999999.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="ivaAdd">IVA</label>
                        <input class="form-field-input" id="ivaAdd" name="iva" type="number" min="0" max="100" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 0. Massimo 100.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="quantityAdd">Quantità</label>
                        <input class="form-field-input" id="quantityAdd" name="quantity" type="number" min="0" max="9999999" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 0. Massimo 9999999.
                        </div>
                    </div>
                    <div class="form-field">
                        <div class="form-field-multipleCheckbox" style="width: max-content" onclick="openCheckboxs('Categorie')">
                            Categorie
                            <i class="fa fa-caret-down" ></i>
                        </div>
                        <ul class="form-field-nav-checkbox" style="height:0" id="navCheckboxCategorie">
                            <%List<Categoria> catList= CategoriaDAO.doRetrieveAll();
                            for(Categoria cat: catList) {%>
                                <li class="form-field-checkbox">
                                    <input class="form-field-input" style="height: auto; width: auto;" id="category<%=cat.getNome()%>"
                                           name="categories" type="checkbox" value="<%=cat.getId()%>">
                                    <label class="form-field-label" style="display: inline; margin-left: 5px;" for="category<%=cat.getNome()%>"><%=cat.getNome()%></label>
                                </li>
                            <%}%>
                        </ul>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="discountAdd">Sconto</label>
                        <select class="form-field-input" id="discountAdd" name="discount" required>
                            <option value="Nessuno" selected>Nessuno</option>
                            <%List<Sconto> discountList= ScontoDAO.doRetrieveAll();
                            for(Sconto s: discountList) {%>
                                <option value="<%=s.getId()%>"><%=s.getNome()%></option>
                            <%}%>
                        </select>
                    </div>

                    <div class="form-buttons">
                        <a href="<%=request.getContextPath()%>/Admin/Products" class="buttonPrimary buttonSecondary buttonHover">Annulla</a>
                        <button class="form-submitButton" onclick="return (confermaParametri())" type="submit">Aggiungi</button>
                    </div>
                </form>
            </section>
        </div>
    </main>

    <script>
        function confermaParametri() {
            let name= document.getElementById('nameAdd').value;
            let descr= document.getElementById('descrAdd').value;
            let price= document.getElementById('priceAdd').value;
            let iva= document.getElementById('ivaAdd').value;
            let quantity= document.getElementById('quantityAdd').value;

            const nameRGX= /^[\w\-+.()\[\]{} ]{2,50}$/;
            const descrRGX= /^[\s\S]{2,1000}$/;
            const priceRGX= /^[+]?([0-9]{0,7}[.])?[0-9]{0,7}$/;
            const ivaRGX= /^[+]?[0-9]{1,2}$|^[+]?100$/;
            const quantityRGX= /^[+]?[0-9]{0,7}$/;

            if(!nameRGX.test(name)){
                alert("Nome non valido!");
                return false;
            }
            if(!descrRGX.test(descr)){
                alert("Descrizione non valida!");
                return false;
            }
            if(!priceRGX.test(price)) {
                alert("Prezzo non valido!");
                return false;
            }
            if(!ivaRGX.test(iva)) {
                alert("IVA non valida!");
                return false;
            }
            if(!quantityRGX.test(quantity)) {
                alert("Quantità non valida!");
                return false;
            }

            return true;
        }

        function openCheckboxs(x){
            let navlink=document.getElementById("navCheckbox"+x);
            if (navlink.style.height==="0px")
                navlink.style.height="auto";
            else
                navlink.style.height="0px";
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
