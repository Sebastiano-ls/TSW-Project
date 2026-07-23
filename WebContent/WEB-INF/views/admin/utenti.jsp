<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.UtenteBean, org.apache.commons.text.StringEscapeUtils" %>
<%
    List<UtenteBean> utenti = (List<UtenteBean>) request.getAttribute("utenti");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gestisci Utenti — Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
  <script src="${pageContext.request.contextPath}/scripts/modal.js" defer></script>
</head>
<body>
  <%@ include file="../header.jsp" %>

  <div class="admin-page profile-page">
    <div class="container">
      <div class="profile-header">
        <h1>Gestisci Utenti</h1>
        <p>Elenco degli utenti registrati.</p>
      </div>

      <div class="profilo wrapper-tabella">
        <table class="admin-table">
          <thead>
            <tr>
              <th class="admin-th">ID</th>
              <th class="admin-th">Nome</th>
              <th class="admin-th">Cognome</th>
              <th class="admin-th">Email</th>
              <th class="admin-th">Ruolo</th>
              <th class="admin-th">Telefono</th>
              <th class="admin-th">Azioni</th>
            </tr>
          </thead>
          <tbody>
            <% if (utenti != null) { 
                int rowNum = 0; 
                for (UtenteBean u : utenti) { 
            %>
              <tr class='<%= rowNum++ % 2 == 0 ? "admin-tr" : "admin-tr-alt" %>'>
                <td class="admin-td"><%= u.getIdUtente() %></td>
                <td class="admin-td"><%= StringEscapeUtils.escapeHtml4(u.getNome() != null ? u.getNome() : "") %></td>
                <td class="admin-td"><%= StringEscapeUtils.escapeHtml4(u.getCognome() != null ? u.getCognome() : "") %></td>
                <td class="admin-td"><%= StringEscapeUtils.escapeHtml4(u.getEmail()) %></td>
                <td class="admin-td"><%= StringEscapeUtils.escapeHtml4(u.getRuolo()) %></td>
                <td class="admin-td"><%= StringEscapeUtils.escapeHtml4(u.getNumTelefono() != null ? u.getNumTelefono() : "-") %></td>
                <td class="admin-td">
                  <% if (!"admin".equals(u.getRuolo())) { %>
                    <form action="${pageContext.request.contextPath}/account/delete" method="post" class="form-inline">
                      <input type="hidden" name="action" value="delete">
                      <input type="hidden" name="id" value="<%= u.getIdUtente() %>">
                      <button type="button" class="btn-admin-delete" onclick="showModal(this, 'Eliminare questo utente?')">Elimina</button>
                    </form>
                  <% } else { %>
                    <span class="testo-sfumato">—</span>
                  <% } %>
                </td>
              </tr>
            <% 
                } 
              } 
            %>
            <% if (utenti == null || utenti.isEmpty()) { %>
              <tr>
                <td colspan="7" class="admin-td stato-vuoto">Nessun utente registrato.</td>
              </tr>
            <% } %>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <%@ include file="../footer.jsp" %>

  <div class="modal-overlay" id="modal">
    <div class="modal-box">
      <p id="modal-msg"></p>
      <div class="modal-azioni">
        <button class="btn btn-outline" onclick="closeModal()">Annulla</button>
        <button class="btn btn-danger" id="modal-confirm">Conferma</button>
      </div>
    </div>
  </div>
</body>
</html>