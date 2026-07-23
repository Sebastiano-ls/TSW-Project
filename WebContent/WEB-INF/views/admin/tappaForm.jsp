<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.TappaBean, model.UtenteBean"%>
<%
    TappaBean tappa = (TappaBean) request.getAttribute("tappa");
    boolean isEdit = tappa != null;
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><%= isEdit ? "Modifica" : "Nuova" %> Tappa — Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
  <%@ include file="../header.jsp" %>

  <div class="form-page">
    <div class="form">
      <h2><%= isEdit ? "Modifica Tappa" : "Nuova Tappa" %></h2>
      <form action="${pageContext.request.contextPath}/admin/tappe" method="post">
        <input type="hidden" name="action" value="<%= isEdit ? "edit" : "create" %>">
        <% if (isEdit) { %>
          <input type="hidden" name="id" value="<%= tappa.getId() %>">
        <% } %>

        <div class="form-group">
          <label for="localita">Localit&agrave;</label>
          <input type="text" name="localita" id="localita" required
                 value="<%= isEdit ? tappa.getLocalita() : "" %>">
        </div>

        <div class="form-group">
          <label for="porto">Porto</label>
          <input type="text" name="porto" id="porto" required
                 value="<%= isEdit ? tappa.getPorto() : "" %>">
        </div>

        <div class="form-group">
          <label>
            <input type="checkbox" name="attivo" <%= isEdit && tappa.isAttivo() ? "checked" : "" %>>
            Tappa attiva
          </label>
        </div>

        <div class="form-azione">
          <button type="submit" class="btn"><%= isEdit ? "Salva Modifiche" : "Crea Tappa" %></button>
          <a href="${pageContext.request.contextPath}/admin/tappe" class="btn btn-outline">Annulla</a>
        </div>
      </form>
    </div>
  </div>

  <%@ include file="../footer.jsp" %>
</body>
</html>