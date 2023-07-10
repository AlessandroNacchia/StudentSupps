<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Aggiungi indirizzo | Student Supps</title>
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
                <c:if test="${requestScope.addAddressStatus == 'countryWrongPattern'}">
                    <p style="color: red">Pattern Nazione errato!</p>
                </c:if>
                <c:if test="${requestScope.addAddressStatus == 'provinceWrongPattern'}">
                    <p style="color: red">Pattern Provincia errato!</p>
                </c:if>
                <c:if test="${requestScope.addAddressStatus == 'cityWrongPattern'}">
                    <p style="color: red">Pattern Città errato!</p>
                </c:if>
                <c:if test="${requestScope.addAddressStatus == 'capWrongPattern'}">
                    <p style="color: red">Pattern CAP errato!</p>
                </c:if>
                <c:if test="${requestScope.addAddressStatus == 'streetWrongPattern'}">
                    <p style="color: red">Pattern Via/Piazza errato!</p>
                </c:if>
                <c:if test="${requestScope.addAddressStatus == 'phoneWrongPattern'}">
                    <p style="color: red">Pattern Numero di telefono errato!</p>
                </c:if>
                <form action="<%=request.getContextPath()%>/Cart/Checkout/AddAddress" method="post">
                    <div class="form-field">
                        <label class="form-field-label" for="countryAdd">Nazione</label>
                        <!-- Country names and Country Name -->
                        <select class="form-field-input" id="countryAdd" name="country" required>
                            <option value="Afghanistan">Afghanistan</option>
                            <option value="Aland Islands">Isole Aland</option>
                            <option value="Albania">Albania</option>
                            <option value="Algeria">Algeria</option>
                            <option value="American Samoa">Samoa americane</option>
                            <option value="Andorra">Andorra</option>
                            <option value="Angola">Angola</option>
                            <option value="Anguilla">Anguilla</option>
                            <option value="Antarctica">Antartide</option>
                            <option value="Antigua and Barbuda">Antigua e Barbuda</option>
                            <option value="Argentina">Argentina</option>
                            <option value="Armenia">Armenia</option>
                            <option value="Aruba">Aruba</option>
                            <option value="Australia">Australia</option>
                            <option value="Austria">Austria</option>
                            <option value="Azerbaijan">Azerbaigian</option>
                            <option value="Bahamas">Bahamas</option>
                            <option value="Bahrain">Bahrein</option>
                            <option value="Bangladesh">Bangladesh</option>
                            <option value="Barbados">Barbados</option>
                            <option value="Belarus">Bielorussia</option>
                            <option value="Belgium">Belgio</option>
                            <option value="Belize">Belize</option>
                            <option value="Benin">Benin</option>
                            <option value="Bermuda">Bermuda</option>
                            <option value="Bhutan">Bhutan</option>
                            <option value="Bolivia">Bolivia</option>
                            <option value="Bonaire, Sint Eustatius and Saba">Bonaire, Sint Eustatius e Saba</option>
                            <option value="Bosnia and Herzegovina">Bosnia Erzegovina</option>
                            <option value="Botswana">Botswana</option>
                            <option value="Bouvet Island">Isola Bouvet</option>
                            <option value="Brazil">Brasile</option>
                            <option value="British Indian Ocean Territory">Territorio britannico dell'Oceano Indiano</option>
                            <option value="Brunei Darussalam">Brunei Darussalam</option>
                            <option value="Bulgaria">Bulgaria</option>
                            <option value="Burkina Faso">Burkina Faso</option>
                            <option value="Burundi">Burundi</option>
                            <option value="Cambodia">Cambogia</option>
                            <option value="Cameroon">Camerun</option>
                            <option value="Canada">Canada</option>
                            <option value="Cape Verde">capo Verde</option>
                            <option value="Cayman Islands">Isole Cayman</option>
                            <option value="Central African Republic">Repubblica Centrafricana</option>
                            <option value="Chad">Chad</option>
                            <option value="Chile">Chile</option>
                            <option value="China">Cina</option>
                            <option value="Christmas Island">Isola di Natale</option>
                            <option value="Cocos (Keeling) Islands">Isole Cocos (Keeling)</option>
                            <option value="Colombia">Colombia</option>
                            <option value="Comoros">Comore</option>
                            <option value="Congo">Congo</option>
                            <option value="Congo, Democratic Republic of the Congo">Congo, Repubblica Democratica del Congo</option>
                            <option value="Cook Islands">Isole Cook</option>
                            <option value="Costa Rica">Costa Rica</option>
                            <option value="Cote D'Ivoire">Costa d'Avorio</option>
                            <option value="Croatia">Croazia</option>
                            <option value="Cuba">Cuba</option>
                            <option value="Curacao">Curacao</option>
                            <option value="Cyprus">Cipro</option>
                            <option value="Czech Republic">Repubblica Ceca</option>
                            <option value="Denmark">Danimarca</option>
                            <option value="Djibouti">Gibuti</option>
                            <option value="Dominica">Dominica</option>
                            <option value="Dominican Republic">Repubblica Dominicana</option>
                            <option value="Ecuador">Ecuador</option>
                            <option value="Egypt">Egitto</option>
                            <option value="El Salvador">El Salvador</option>
                            <option value="Equatorial Guinea">Guinea Equatoriale</option>
                            <option value="Eritrea">Eritrea</option>
                            <option value="Estonia">Estonia</option>
                            <option value="Ethiopia">Etiopia</option>
                            <option value="Falkland Islands (Malvinas)">Isole Falkland (Malvinas)</option>
                            <option value="Faroe Islands">Isole Faroe</option>
                            <option value="Fiji">Figi</option>
                            <option value="Finland">Finlandia</option>
                            <option value="France">Francia</option>
                            <option value="French Guiana">Guiana francese</option>
                            <option value="French Polynesia">Polinesia francese</option>
                            <option value="French Southern Territories">Territori della Francia del sud</option>
                            <option value="Gabon">Gabon</option>
                            <option value="Gambia">Gambia</option>
                            <option value="Georgia">Georgia</option>
                            <option value="Germany">Germania</option>
                            <option value="Ghana">Ghana</option>
                            <option value="Gibraltar">Gibilterra</option>
                            <option value="Greece">Grecia</option>
                            <option value="Greenland">Groenlandia</option>
                            <option value="Grenada">Grenada</option>
                            <option value="Guadeloupe">Guadalupa</option>
                            <option value="Guam">Guam</option>
                            <option value="Guatemala">Guatemala</option>
                            <option value="Guernsey">Guernsey</option>
                            <option value="Guinea">Guinea</option>
                            <option value="Guinea-Bissau">Guinea-Bissau</option>
                            <option value="Guyana">Guyana</option>
                            <option value="Haiti">Haiti</option>
                            <option value="Heard Island and Mcdonald Islands">Heard Island e McDonald Islands</option>
                            <option value="Holy See (Vatican City State)">Santa Sede (Stato della Città del Vaticano)</option>
                            <option value="Honduras">Honduras</option>
                            <option value="Hong Kong">Hong Kong</option>
                            <option value="Hungary">Ungheria</option>
                            <option value="Iceland">Islanda</option>
                            <option value="India">India</option>
                            <option value="Indonesia">Indonesia</option>
                            <option value="Iran, Islamic Republic of Iran">Iran (Repubblica Islamica dell'Iran)</option>
                            <option value="Iraq">Iraq</option>
                            <option value="Ireland">Irlanda</option>
                            <option value="Isle of Man">Isola di Man</option>
                            <option value="Israel">Israele</option>
                            <option value="Italy" selected>Italia</option>
                            <option value="Jamaica">Giamaica</option>
                            <option value="Japan">Giappone</option>
                            <option value="Jersey">Jersey</option>
                            <option value="Jordan">Giordania</option>
                            <option value="Kazakhstan">Kazakistan</option>
                            <option value="Kenya">Kenya</option>
                            <option value="Kiribati">Kiribati</option>
                            <option value="Korea, Democratic People's Republic of Korea">Corea, Repubblica Popolare Democratica di Corea</option>
                            <option value="Korea, Republic of Korea">Corea, Repubblica di Corea</option>
                            <option value="Kosovo">Kosovo</option>
                            <option value="Kuwait">Kuwait</option>
                            <option value="Kyrgyzstan">Kirghizistan</option>
                            <option value="Lao People's Democratic Republic">Repubblica Democratica Popolare del Laos</option>
                            <option value="Latvia">Lettonia</option>
                            <option value="Lebanon">Libano</option>
                            <option value="Lesotho">Lesotho</option>
                            <option value="Liberia">Liberia</option>
                            <option value="Libyan Arab Jamahiriya">Giamahiria araba libica</option>
                            <option value="Liechtenstein">Liechtenstein</option>
                            <option value="Lithuania">Lituania</option>
                            <option value="Luxembourg">Lussemburgo</option>
                            <option value="Macao">Macao</option>
                            <option value="North Macedonia">Macedonia del Nord</option>
                            <option value="Madagascar">Madagascar</option>
                            <option value="Malawi">Malawi</option>
                            <option value="Malaysia">Malaysia</option>
                            <option value="Maldives">Maldive</option>
                            <option value="Mali">Mali</option>
                            <option value="Malta">Malta</option>
                            <option value="Marshall Islands">Isole Marshall</option>
                            <option value="Martinique">Martinica</option>
                            <option value="Mauritania">Mauritania</option>
                            <option value="Mauritius">Maurizio</option>
                            <option value="Mayotte">Mayotte</option>
                            <option value="Mexico">Messico</option>
                            <option value="Micronesia">Micronesia</option>
                            <option value="Moldova">Moldova</option>
                            <option value="Monaco">Monaco</option>
                            <option value="Mongolia">Mongolia</option>
                            <option value="Montenegro">Montenegro</option>
                            <option value="Montserrat">Montserrat</option>
                            <option value="Morocco">Marocco</option>
                            <option value="Mozambique">Mozambico</option>
                            <option value="Myanmar">Myanmar</option>
                            <option value="Namibia">Namibia</option>
                            <option value="Nauru">Nauru</option>
                            <option value="Nepal">Nepal</option>
                            <option value="Netherlands">Olanda</option>
                            <option value="Netherlands Antilles">Antille Olandesi</option>
                            <option value="New Caledonia">Nuova Caledonia</option>
                            <option value="New Zealand">Nuova Zelanda</option>
                            <option value="Nicaragua">Nicaragua</option>
                            <option value="Niger">Niger</option>
                            <option value="Nigeria">Nigeria</option>
                            <option value="Niue">Niue</option>
                            <option value="Norfolk Island">Isola Norfolk</option>
                            <option value="Northern Mariana Islands">Isole Marianne settentrionali</option>
                            <option value="Norway">Norvegia</option>
                            <option value="Oman">Oman</option>
                            <option value="Pakistan">Pakistan</option>
                            <option value="Palau">Palau</option>
                            <option value="Palestinian Territory, Occupied">Territori palestinesi occupati</option>
                            <option value="Panama">Panama</option>
                            <option value="Papua New Guinea">Papua Nuova Guinea</option>
                            <option value="Paraguay">Paraguay</option>
                            <option value="Peru">Perù</option>
                            <option value="Philippines">Filippine</option>
                            <option value="Pitcairn">Pitcairn</option>
                            <option value="Poland">Polonia</option>
                            <option value="Portugal">Portogallo</option>
                            <option value="Puerto Rico">Porto Rico</option>
                            <option value="Qatar">Qatar</option>
                            <option value="Reunion">Riunione</option>
                            <option value="Romania">Romania</option>
                            <option value="Russian Federation">Federazione Russa</option>
                            <option value="Rwanda">Ruanda</option>
                            <option value="Saint Barthelemy">Saint Barthelemy</option>
                            <option value="Saint Helena">Sant'Elena</option>
                            <option value="Saint Kitts and Nevis">Saint Kitts e Nevis</option>
                            <option value="Saint Lucia">Santa Lucia</option>
                            <option value="Saint Martin">Saint Martin</option>
                            <option value="Saint Pierre and Miquelon">Saint Pierre e Miquelon</option>
                            <option value="Saint Vincent and the Grenadines">Saint Vincent e Grenadine</option>
                            <option value="Samoa">Samoa</option>
                            <option value="San Marino">San Marino</option>
                            <option value="Sao Tome and Principe">Sao Tome e Principe</option>
                            <option value="Saudi Arabia">Arabia Saudita</option>
                            <option value="Senegal">Senegal</option>
                            <option value="Serbia">Serbia</option>
                            <option value="Serbia and Montenegro">Serbia e Montenegro</option>
                            <option value="Seychelles">Seychelles</option>
                            <option value="Sierra Leone">Sierra Leone</option>
                            <option value="Singapore">Singapore</option>
                            <option value="Sint Maarten">St Martin</option>
                            <option value="Slovakia">Slovacchia</option>
                            <option value="Slovenia">Slovenia</option>
                            <option value="Solomon Islands">Isole Salomone</option>
                            <option value="Somalia">Somalia</option>
                            <option value="South Africa">Sud Africa</option>
                            <option value="South Georgia and the South Sandwich Islands">Georgia del Sud e isole Sandwich meridionali</option>
                            <option value="South Sudan">Sudan del Sud</option>
                            <option value="Spain">Spagna</option>
                            <option value="Sri Lanka">Sri Lanka</option>
                            <option value="Sudan">Sudan</option>
                            <option value="Suriname">Suriname</option>
                            <option value="Svalbard and Jan Mayen">Svalbard e Jan Mayen</option>
                            <option value="Swaziland">Swaziland</option>
                            <option value="Sweden">Svezia</option>
                            <option value="Switzerland">Svizzera</option>
                            <option value="Syrian Arab Republic">Repubblica Araba Siriana</option>
                            <option value="Taiwan">Taiwan</option>
                            <option value="Tajikistan">Tagikistan</option>
                            <option value="Tanzania">Tanzania</option>
                            <option value="Thailand">Tailandia</option>
                            <option value="Timor-Leste">Timor-Leste</option>
                            <option value="Togo">Andare</option>
                            <option value="Tokelau">Tokelau</option>
                            <option value="Tonga">Tonga</option>
                            <option value="Trinidad and Tobago">Trinidad e Tobago</option>
                            <option value="Tunisia">Tunisia</option>
                            <option value="Turkey">tacchino</option>
                            <option value="Turkmenistan">Turkmenistan</option>
                            <option value="Turks and Caicos Islands">Isole Turks e Caicos</option>
                            <option value="Tuvalu">Tuvalu</option>
                            <option value="Uganda">Uganda</option>
                            <option value="Ukraine">Ucraina</option>
                            <option value="United Arab Emirates">Emirati Arabi Uniti</option>
                            <option value="United Kingdom">Regno Unito</option>
                            <option value="United States">Stati Uniti</option>
                            <option value="United States Minor Outlying Islands">Isole minori esterne degli Stati Uniti</option>
                            <option value="Uruguay">Uruguay</option>
                            <option value="Uzbekistan">Uzbekistan</option>
                            <option value="Vanuatu">Vanuatu</option>
                            <option value="Venezuela">Venezuela</option>
                            <option value="Viet Nam">Viet Nam</option>
                            <option value="Virgin Islands, British">Isole Vergini britanniche</option>
                            <option value="Virgin Islands, U.s.">Isole Vergini, Stati Uniti</option>
                            <option value="Wallis and Futuna">Wallis e Futuna</option>
                            <option value="Western Sahara">Sahara occidentale</option>
                            <option value="Yemen">Yemen</option>
                            <option value="Zambia">Zambia</option>
                            <option value="Zimbabwe">Zimbabwe</option>
                        </select>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="provinceAdd">Provincia</label>
                        <input class="form-field-input" id="provinceAdd" name="province" type="text" maxlength="60" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 60 caratteri.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="cityAdd">Città</label>
                        <input class="form-field-input" id="cityAdd" name="city" type="text" maxlength="60" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 60 caratteri.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="capAdd">CAP</label>
                        <input class="form-field-input" id="capAdd" name="cap" type="text" maxlength="12" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 10 caratteri. Accetta solo numeri e lettere maiuscole. Inserire 00000 in caso di CAP mancante.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="streetAdd">Via/Piazza</label>
                        <input class="form-field-input" id="streetAdd" name="street" type="text" maxlength="100" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 100 caratteri.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="phoneAdd">Numero di Telefono</label>
                        <input class="form-field-input" id="phoneAdd" name="phone" type="text" required>
                    </div>

                    <div class="form-buttons">
                        <a href="<%=request.getContextPath()%>/Cart/Checkout" class="buttonPrimary buttonSecondary buttonHover">Annulla</a>
                        <button class="form-submitButton" onclick="return (confermaParametri())" type="submit">Aggiungi</button>
                    </div>
                </form>
            </section>
        </div>
    </main>

    <script>
        function confermaParametri() {
            let country= document.getElementById('countryAdd').value;
            let province= document.getElementById('provinceAdd').value;
            let city= document.getElementById('cityAdd').value;
            let cap= document.getElementById('capAdd').value;
            let street= document.getElementById('streetAdd').value;
            let phone= document.getElementById('phoneAdd').value;

            const nameRGX= /^[a-zA-Z0-9\-\s]{2,60}$/;
            const capRGX= /^[A-Z0-9\-\s]{2,10}$/;
            const streetRGX= /^.{2,100}$/;
            const phoneRGX= /^([+]?[(]?[0-9]{1,3}[)]?[-\s])?([(]?[0-9]{3}[)]?[-\s]?)?([0-9][-\s]?){3,10}[0-9]$/;

            if(!nameRGX.test(country)){
                alert("Paese non valido!");
                return false;
            }
            if(!nameRGX.test(province)){
                alert("Provincia non valida!");
                return false;
            }
            if(!nameRGX.test(city)){
                alert("Città non valida!");
                return false;
            }
            if(!capRGX.test(cap)){
                alert("CAP non valido!");
                return false;
            }
            if(!streetRGX.test(street)){
                alert("Via/Piazza non valida!");
                return false;
            }
            if(!phoneRGX.test(phone)){
                alert("Numero di telefono non valido!");
                return false;
            }

            return true;
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>