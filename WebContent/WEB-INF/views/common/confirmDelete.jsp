<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>
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
  <title>Elimina Account</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
  <%@ include file="../header.jsp" %>

  <div class="pagina-conferma">
    <div class="conferma">
      <h2>Elimina Account</h2>
      <p>Sei sicuro di voler eliminare il tuo account?<br>
      Questa operazione è irreversibile e tutti i tuoi dati verranno cancellati.</p>
      <form action="${pageContext.request.contextPath}/account/delete" method="post">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="<%= utente.getIdUtente() %>">
        <div class="conferma-azione">
          <a href="#" onclick="history.back();return false;" class="btn btn-outline">Annulla</a>
          <button type="submit" class="btn btn-danger">Elimina account</button>
        </div>
      </form>
    </div>

    <%@ include file="../footer.jsp" %>
  </body>
</html>
