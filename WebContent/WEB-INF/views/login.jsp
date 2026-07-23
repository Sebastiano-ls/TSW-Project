<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accedi — S&S Crociere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <script src="${pageContext.request.contextPath}/scripts/validazione-utente.js" defer></script>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="form-page">
        <div class="form-card">
            <h2>Accedi</h2>

<% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                <%= StringEscapeUtils.escapeHtml4((String) request.getAttribute("error")) %>
            </div>
<% } %>

            <form action="${pageContext.request.contextPath}/login" method="post" onsubmit="return validateLoginForm()">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" placeholder="La tua email ..." required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" placeholder="••••••••••••••" required>
                </div>

                <div class="form-azione">
                    <button type="submit" class="btn btn-primary">Accedi</button>
                </div>

                <div class="form-footer">
                    Non hai un account? <a href="${pageContext.request.contextPath}/register">Registrati</a>
                </div>
            </form>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>