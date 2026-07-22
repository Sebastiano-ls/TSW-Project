<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrati — S&S Crociere</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <script src="${pageContext.request.contextPath}/scripts/validazione-utente.js" defer></script>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="form-page">
        <div class="form">
            <h2>Registrati</h2>

<% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                <%= StringEscapeUtils.escapeHtml4((String) request.getAttribute("error")) %>
            </div>
<% } %>

            <form action="${pageContext.request.contextPath}/register" method="post" onsubmit="return validateRegisterForm()">
                <div class="riga-form">
                    <div class="form-group">
                        <label for="nome">Nome</label>
                        <input type="text" name="nome" id="nome" placeholder="Il tuo nome" required>
                    </div>

                    <div class="form-group">
                        <label for="cognome">Cognome</label>
                        <input type="text" name="cognome" id="cognome" placeholder="Il tuo cognome" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" placeholder="la tua email" required>
                </div>

                <div class="riga-form">
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" name="password" id="password" placeholder="•••••••••••••••" required>
                    </div>

                    <div class="form-group">
                        <label for="genere">Genere</label>
                        <select name="genere" id="genere">
                            <option value="">Seleziona</option>
                            <option value="M">Maschio</option>
                            <option value="F">Femmina</option>
                            <option value="A">Altro</option>
                        </select>
                    </div>
                </div>

                <div class="riga-form">
                    <div class="form-group">
                        <label for="dataNascita">Data di Nascita</label>
                        <input type="date" name="dataNascita" id="dataNascita">
                    </div>

                    <div class="form-group">
                        <label for="numTelefono">Telefono</label>
                        <input type="tel" name="numTelefono" id="numTelefono" placeholder="+39 3XX XXX XXXX">
                    </div>
                </div>

                <div class="form-azione">
                    <button type="submit" class="btn btn-primary">Registrati</button>
                </div>

                <div class="form-footer">
                    Hai già un account? <a href="${pageContext.request.contextPath}/login">Accedi</a>
                </div>
            </form>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>