<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Aggiungi Prodotto | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/Admin/formContainer.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <main class="formContainer">
        <h1 class="formContainer-title">Aggiungi i parametri del prodotto</h1>
        <div class="formContainer-wrapper">
            <section class="formContainer-section">
                <form action="<%=request.getContextPath()%>/Admin/AddProduct" method="post" enctype="multipart/form-data">
                    <div class="form-field">
                        <label class="form-field-label" for="imageAdd">Immagine</label>
                        <input class="form-field-inputFile" id="imageAdd" name="image" type="file" accept=".jpg, .jpeg, .png, .webp" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="nameAdd">Nome</label>
                        <input class="form-field-input" id="nameAdd" name="name" type="text" autocomplete="off" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="descrAdd">Descrizione</label>
                        <input class="form-field-input" id="descrAdd" name="description" type="text" autocomplete="off" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="priceAdd">Prezzo</label>
                        <input class="form-field-input" id="priceAdd" name="price" type="number" min="0" autocomplete="off" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="ivaAdd">IVA</label>
                        <input class="form-field-input" id="ivaAdd" name="iva" type="number" min="0" max="100" autocomplete="off" required>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="quantityAdd">Quantità</label>
                        <input class="form-field-input" id="quantityAdd" name="quantity" type="number" min="0" autocomplete="off" required>
                    </div>

                    <button class="form-submitButton" onclick="return (confermaParametri())" type="submit">Aggiorna</button>
                </form>
            </section>
        </div>
    </main>

    <script>
        function confermaParametri() {
            let name= document.getElementById('nameAdd').value;
            let descr= document.getElementById('descrAdd').value;

            const nameRGX= /^.{2,30}$/;
            const descrRGX= /^.{2,250}$/;

            if(!nameRGX.test(name)){
                alert("Nome non valido!");
                return false;
            }
            if(!descrRGX.test(descr)){
                alert("Descrizione non valida!");
                return false;
            }

            return true;
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>
