<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.TappaBean, model.UtenteBean"%>
<%
    List<TappaBean> tappe = (List<TappaBean>) request.getAttribute("tappe");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gestisci Tappe — Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
  <script src="${pageContext.request.contextPath}/scripts/modal.js" defer></script>
</head>
<body>
  <%@ include file="../header.jsp" %>

  <div class="admin-page profile-page">
    <div class="container">
      <div class="profile-header">
        <h1>Gestisci Tappe</h1>
        
        <p>Elenco delle tappe nel sistema.</p>
        <a href="${pageContext.request.contextPath}/admin/tappe?action=create" class="btn-admin-create">+ Nuova Tappa</a>
      </div>

      <div class="profile-card wrapper-tabella">
        <table class="admin-table">
          <thead>
            <tr>
              <th class="admin-th">ID</th>
              <th class="admin-th">Localit&agrave;</th>
              <th class="admin-th">Porto</th>
              <th class="admin-th">Attivo</th>
              <th class="admin-th">Azioni</th>
            </tr>
          </thead>
          <tbody>
            <% if (tappe != null) { 
                int rowNum = 0; 
                for (TappaBean t : tappe) { 
            %>
              <tr class='<%= rowNum++ % 2 == 0 ? "admin-tr" : "admin-tr-alt" %>'>
                <td class="admin-td"><%= t.getId() %></td>
                <td class="admin-td"><%= t.getLocalita() %></td>
                <td class="admin-td"><%= t.getPorto() %></td>
                <td class="admin-td"><%= t.isAttivo() ? "S&igrave;" : "No" %></td>
                <td class="admin-td">
                  <a href="${pageContext.request.contextPath}/admin/tappe?action=edit&id=<%= t.getId() %>" class="btn-admin-edit">Modifica</a>
                  <form action="${pageContext.request.contextPath}/admin/tappe" method="post" class="form-inline">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= t.getId() %>">
                    <button type="button" class="btn-admin-delete" onclick="showModal(this, 'Disattivare questa tappa?')">Disattiva</button>
                  </form>
                </td>
              </tr>
            <% 
                } 
              } 
            %>
            <% if (tappe == null || tappe.isEmpty()) { %>
              <tr>
                <td colspan="5" class="admin-td stato-vuoto">Nessuna tappa presente.</td>
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