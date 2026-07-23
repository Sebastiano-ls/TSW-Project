<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logout — S&S Crociere</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
    <%@ include file="../header.jsp" %>

    <div class="form-page">
        <div class="form">
            <h2>Logout effettuato</h2>
            <p>Hai effettuato il logout con successo.</p>

            <div class="form-azioni">
                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Accedi di nuovo</a>
            </div>

            <div class="form-footer">
                <a href="${pageContext.request.contextPath}/home">Torna alla Home</a>
            </div>
        </div>
    </div>

    <%@ include file="../footer.jsp" %>
</body>
</html>