<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean, org.apache.commons.text.StringEscapeUtils" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Il mio Profilo — S&S Crociere</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <script src="${pageContext.request.contextPath}/scripts/validazione-utente.js" defer></script>
</head>
<body>
    <%@ include file="../header.jsp" %>

    <div class="profile-page">
        <div class="container">
            <div class="header-profilo">
                <h1>Il mio Profilo</h1>
                <p>Gestisci le tue informazioni personali.</p>
            </div>

            <div class="profilo-griglia">
                
                <div class="profilo">
                    <h2>Dati Personali</h2>
                    <form action="${pageContext.request.contextPath}/profile" method="post" onsubmit="return validateProfileInfo()">
                        <input type="hidden" name="action" value="updateInfo">

                        <div class="riga-profilo">
                            <label for="nome" class="label-profilo">Nome</label>
                            <input type="text" name="nome" id="nome" value="<%= StringEscapeUtils.escapeHtml4(utente.getNome() != null ? utente.getNome() : "") %>" class="input-profilo">
                        </div>

                        <div class="riga-profilo">
                            <label for="cognome" class="label-profilo">Cognome</label>
                            <input type="text" name="cognome" id="cognome" value="<%= StringEscapeUtils.escapeHtml4(utente.getCognome() != null ? utente.getCognome() : "") %>" class="input-profilo">
                        </div>

                        <div class="riga-profilo">
                            <label for="email" class="label-profilo">Email</label>
                            <input type="email" name="email" id="email" value="<%= StringEscapeUtils.escapeHtml4(utente.getEmail() != null ? utente.getEmail() : "") %>" class="input-profilo">
                        </div>

                        <div class="riga-profilo">
                            <label for="genere" class="label-profilo">Genere</label>
                            <select name="genere" id="genere" class="input-profilo">
                                <option value="" <%= utente.getGenere() == null || utente.getGenere().isEmpty() ? "selected" : "" %>>Seleziona</option>
                                <option value="M" <%= "M".equals(utente.getGenere()) ? "selected" : "" %>>Maschio</option>
                                <option value="F" <%= "F".equals(utente.getGenere()) ? "selected" : "" %>>Femmina</option>
                                <option value="A" <%= "A".equals(utente.getGenere()) ? "selected" : "" %>>Altro</option>
                            </select>
                        </div>

                        <div class="riga-profilo">
                            <label for="dataNascita" class="label-profilo">Data Nascita</label>
                            <input type="date" name="dataNascita" id="dataNascita" value="<%= utente.getDataNascita() != null ? utente.getDataNascita().toString() : "" %>" class="input-profilo">
                        </div>

                        <div class="riga-profilo">
                            <label for="numTelefono" class="label-profilo">Telefono</label>
                            <input type="tel" name="numTelefono" id="numTelefono" value="<%= StringEscapeUtils.escapeHtml4(utente.getNumTelefono() != null ? utente.getNumTelefono() : "") %>" class="input-profilo">
                        </div>

                        <div class="azioni-profilo">
                            <button type="submit" class="btn btn-primary btn-sm">Salva modifiche</button>
                        </div>
                    </form>
                </div>

                
                <div class="profilo">
                    <h2>Cambia Password</h2>
                    <form action="${pageContext.request.contextPath}/profile" method="post" onsubmit="return validatePasswordForm()">
                        <input type="hidden" name="action" value="updatePassword">

                        <div class="riga-profilo">
                            <label for="oldPassword" class="label-profilo">Password attuale</label>
                            <input type="password" name="oldPassword" id="oldPassword" placeholder="••••••••" class="input-profilo" required>
                        </div>

                        <div class="riga-profilo">
                            <label for="newPassword" class="label-profilo">Nuova password</label>
                            <input type="password" name="newPassword" id="newPassword" placeholder="••••••••" class="input-profilo" required>
                        </div>

                        <div class="riga-profilo">
                            <label for="confirmPassword" class="label-profilo">Conferma nuova password</label>
                            <input type="password" id="confirmPassword" placeholder="••••••••" class="input-profilo" required>
                        </div>

                        <div class="azioni-profilo">
                            <button type="submit" class="btn btn-primary btn-sm">Aggiorna Password</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../footer.jsp" %>
</body>
</html>