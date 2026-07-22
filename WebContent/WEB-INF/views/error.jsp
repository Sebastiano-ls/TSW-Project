<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Errore — S&amp;S Crociere</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    </head>
    <body>
        <%@ include file="header.jsp" %>

        <section class="section">
            <div class="container error-container">
                <% Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code"); %>
                <% Throwable throwedException = (Throwable) pageContext.getException();%>

                <!-- Se tomcat ha messo l'exception nella request anzichè nell'oggetto exception ce lo prendiamo così -->
                <% if (throwedException == null) {
                    throwedException = (Throwable) request.getAttribute("javax.servlet.error.exception");
                    }
                %>

                <!-- gestione errori interni al Server -->
                <% if (statusCode != null && statusCode == 404) { %>
                    <h1 class="error-code">404</h1>
                    <h2 class="error-heading">Pagina non trovata</h2>
                    <p class="sottotitolo">La pagina che stai cercando non esiste o è stata rimossa.</p>
                <% } else { %>
                    <h1 class="error-code">:(</h1>
                    <h2 class="error-heading">Si è verificato un errore</h2>
                    <p class="sottotitolo">Qualcosa è andato storto. Riprova tra qualche istante.</p>
                <% } %>

                <!-- gestione errori dovuti a Exception -->
                <% if (throwedException != null) { %>
                Errore Interno: <%= throwedException.getMessage() %>
                <% 
                } %>

                <a href="${pageContext.request.contextPath}/home" class="btn btn-primary error-btn">Torna alla Home</a>
            </div>
        </section>

        <%@ include file="footer.jsp" %>
    </body>
</html>