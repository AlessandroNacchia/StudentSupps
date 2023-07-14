<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.*" %>
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

    <%Prodotto p= (Prodotto) request.getAttribute("prodToEdit");%>
    <main class="formContainer">
        <h1 class="formContainer-title">Modifica i parametri del prodotto</h1>
        <div class="formContainer-wrapper">
            <section class="formContainer-section">
                <c:if test="${requestScope.editProdStatus == 'nameWrongPattern'}">
                    <p style="color: red">Pattern Nome errato!</p>
                </c:if>
                <c:if test="${requestScope.editProdStatus == 'descriptionWrongPattern'}">
                    <p style="color: red">Pattern Descrizione errato!</p>
                </c:if>
                <c:if test="${requestScope.editProdStatus == 'priceWrongPattern'}">
                    <p style="color: red">Pattern Prezzo errato!</p>
                </c:if>
                <c:if test="${requestScope.editProdStatus == 'ivaWrongPattern'}">
                    <p style="color: red">Pattern IVA errato!</p>
                </c:if>
                <c:if test="${requestScope.editProdStatus == 'quantityWrongPattern'}">
                    <p style="color: red">Pattern Quantità errato!</p>
                </c:if>
                <c:if test="${requestScope.editProdStatus == 'nameTaken'}">
                    <p style="color: red">Nome Prodotto già usato!</p>
                </c:if>
                <form action="<%=request.getContextPath()%>/Admin/EditProduct" method="post" enctype="multipart/form-data">
                    <div class="form-field">
                        <label class="form-field-label" for="prodToEditId">Id</label>
                        <input class="form-field-input" id="prodToEditId" name="id" type="text" value="<%=p.getId()%>" readonly>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="imageUpdate">Immagine</label>
                        <input class="form-field-inputFile" id="imageUpdate" name="image" type="file" accept=".png">
                        <div class="form-field-comment">
                            Accetta .png. Non selezionandola verrà solo rinominata l'immagine già presente.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="nameUpdate">Nome</label>
                        <input class="form-field-input" id="nameUpdate" name="name" type="text" maxlength="50" autocomplete="off" value="<%=p.getNome()%>" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 50 caratteri. Accetta lettere, numeri, spazi, punti, trattini medi, + e parentesi tonde e graffe.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="descrUpdate">Descrizione</label>
                        <input class="form-field-input" id="descrUpdate" name="description" type="text" maxlength="1000" value="<%=p.getDescrizione()%>" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 1000 caratteri.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="priceUpdate">Prezzo</label>
                        <input class="form-field-input" id="priceUpdate" name="price" type="number" min="0" max="9999999" step="0.01" value="<%=p.getPrezzo()%>" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 0. Massimo 9999999.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="ivaUpdate">IVA</label>
                        <input class="form-field-input" id="ivaUpdate" name="iva" type="number" min="0" max="100" value="<%=p.getIVA()%>" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 0. Massimo 100.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="quantityUpdate">Quantità</label>
                        <input class="form-field-input" id="quantityUpdate" name="quantity" type="number" min="0" max="9999999" value="<%=p.getQuantita()%>" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 0. Massimo 9999999.
                        </div>
                    </div>
                    <div class="form-field">
                        <div class="form-field-multipleCheckbox" onclick="openCheckboxs('Categorie')">
                            Categorie
                            <i class="fa fa-caret-down" ></i>
                        </div>
                        <ul class="form-field-nav-checkbox" style="height:0" id="navCheckboxCategorie">
                            <%List<Categoria> catList= CategoriaDAO.doRetrieveAll();
                            for(Categoria cat: catList) {%>
                                <li class="form-field-checkbox">
                                    <input class="form-field-input" style="height: auto; width: auto;" id="category<%=cat.getNome()%>"
                                           name="categories" type="checkbox" value="<%=cat.getId()%>"
                                            <%if(ProdottocategoriaDAO.doExists(p.getId(), cat.getId())) {%> checked <%}%>>
                                    <label class="form-field-label" style="display: inline; margin-left: 5px;" for="category<%=cat.getNome()%>"><%=cat.getNome()%></label>
                                </li>
                            <%}%>
                        </ul>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="discountUpdate">Sconto</label>
                        <select class="form-field-input" id="discountUpdate" name="discount" required>
                            <%String discountId= ProdottoDAO.doRetrieveDiscountId(p.getId());%>
                            <option value="Nessuno" <%if(discountId==null) {%> selected <%}%>>Nessuno</option>
                            <%List<Sconto> discountList= ScontoDAO.doRetrieveAll();
                                for(Sconto s: discountList) {%>
                            <option value="<%=s.getId()%>" <%if(discountId!=null && discountId.equals(s.getId())) {%> selected <%}%>>
                                <%=s.getNome()%>
                            </option>
                            <%}%>
                        </select>
                    </div>

                    <div class="form-buttons">
                        <a href="<%=request.getContextPath()%>/Admin/Products" class="buttonPrimary buttonSecondary buttonHover">Annulla</a>
                        <button class="form-submitButton" onclick="return (confermaParametri())" type="submit">Aggiorna</button>
                    </div>
                </form>
            </section>
        </div>
    </main>

    <script>
        function confermaParametri() {
            let name= document.getElementById('nameUpdate').value;
            let descr= document.getElementById('descrUpdate').value;
            let price= document.getElementById('priceUpdate').value;
            let iva= document.getElementById('ivaUpdate').value;
            let quantity= document.getElementById('quantityUpdate').value;

            const nameRGX= /^[\w\-+.(){} ]{2,50}$/;
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
