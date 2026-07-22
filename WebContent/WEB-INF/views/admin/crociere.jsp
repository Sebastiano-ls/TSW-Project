<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.CrocieraBean, model.UtenteBean, org.apache.commons.text.StringEscapeUtils" %>

<%
    List<CrocieraBean> crociere = (List<CrocieraBean>) request.getAttribute("crociere");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gestisci Crociere — Admin</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
        <script src="${pageContext.request.contextPath}/scripts/modal.js" defer></script>
    </head>
    <body>
        <%@ include file="../header.jsp" %>

        <div class="admin-page profile-page">
            <div class="container">
                <div class="profile-header">
                    <h1>Gestisci Crociere</h1>
                    <p>Elenco delle crociere nel catalogo.</p>
                    <a href="${pageContext.request.contextPath}/admin/crociere?actionAdmin=create" class="btn-admin-create">+ Nuova Crociera</a>
                </div>

                <div class="profilo wrapper-tabella">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th class="admin-th">ID</th>
                                <th class="admin-th">Nome</th>
                                <th class="admin-th">Inizio</th>
                                <th class="admin-th">Fine</th>
                                <th class="admin-th">Prezzo</th>
                                <th class="admin-th">Sconto</th>
                                <th class="admin-th">Immagine</th>
                                <th class="admin-th">Attivo</th>
                                <th class="admin-th">Azioni</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (crociere != null) { 
                                int rowNum = 0; 
                                for (CrocieraBean c : crociere) { %>
                                <tr class='<%= rowNum++ % 2 == 0 ? "admin-tr" : "admin-tr-alt" %>'>
                                    <td class="admin-td"><%= c.getId() %></td>
                                    <td class="admin-td"><%= StringEscapeUtils.escapeHtml4(c.getNome()) %></td>
                                    <td class="admin-td"><%= c.getDataInizio() %></td>
                                    <td class="admin-td"><%= c.getDataFine() %></td>
                                    <td class="admin-td">€ <%= String.format("%.2f", c.getPrezzo()) %></td>
                                    <td class="admin-td"><%= String.format("%.0f", c.getSconto()) %>%</td>
                                    <td class="admin-td"><img src="${pageContext.request.contextPath}/images/crociera?id=<%= c.getId() %>" alt="" onerror="this.style.display='none'"></td>
                                    <td class="admin-td"><%= c.isAttivo() ? "Attivo" : "Non Attivo" %></td>
                                    <td class="admin-td">
                                        <a href="${pageContext.request.contextPath}/admin/crociere?actionAdmin=edit&id=<%= c.getIdCrociera() %>" class="btn-admin-edit">Modifica</a>
                                        <form action="${pageContext.request.contextPath}/admin/crociere" method="post" class="form-inline">
                                            <input type="hidden" name="actionAdmin" value="delete">
                                            <input type="hidden" name="id" value="<%= c.getIdCrociera() %>">
                                            <button type="button" class="btn-admin-delete" onclick="showModal(this, 'Disattivare questa crociera?')">Disattiva</button>
                                        </form>
                                    </td>
                                </tr>
                            <% } } %>

                            <% if (crociere == null || crociere.isEmpty()) { %>
                                <tr><td colspan="9" class="admin-td stato-vuoto">Nessuna crociera nel catalogo.</td></tr>
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