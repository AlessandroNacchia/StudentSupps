<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <title>Error</title>
    </head>
    <body>
        <div align="center">
            <table class="title">
                <tr>
                    <th>Error</th>
                </tr>
            </table>
            <p></p>
            <h2>"Errorwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww message: ${errorMessage}"</h2>
            <button onclick="location.href='.'" type="button">Torna alla Home</button>
        </div>
    </body>
</html>
