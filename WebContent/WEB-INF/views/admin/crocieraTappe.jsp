<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.TappaBean, model.CrocieraBean, model.UtenteBean"%>

<%
    CrocieraBean crociera = (CrocieraBean) request.getAttribute("crociera");
    List<TappaBean> tappeAssociate = (List<TappaBean>) request.getAttribute("tappeAssociate");
    List<TappaBean> tappeDisponibili = (List<TappaBean>) request.getAttribute("tappeDisponibili");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gestisci Tappe Crociera — Admin</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
        <script src="${pageContext.request.contextPath}/scripts/modal.js" defer></script>
    </head>
    <body>
        <%@ include file="../header.jsp" %>

        <div class="admin-page profile-page">
            <div class="container">
                <div class="profile-header">
                    <h1>Tappe Crociera</h1>
                    <p>Gestisci le tappe per: <strong><%= crociera != null ? crociera.getNomeCrociera() : "?" %></strong></p>
                    <a href="${pageContext.request.contextPath}/admin/crociere" class="btn btn-outline btn-sm">&larr; Torna alle crociere</a>
                </div>

                <div class="profilo">
                    <h2>Tappe Associate</h2>
                    <% if (tappeAssociate != null && !tappeAssociate.isEmpty()) { %>
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th class="admin-th">Località</th>
                                <th class="admin-th">Porto</th>
                                <th class="admin-th">Azioni</th>
                            </tr>
                        </thead>

                        <tbody>
                            <% for (TappaBean t : tappeAssociate) { %>
                            <tr>
                                <td class="admin-td"><%= t.getLocalita() %></td>
                                <td class="admin-td"><%= t.getPorto() %></td>
                                <td class="admin-td">
                                    <form action="${pageContext.request.contextPath}/admin/crociera-tappe" method="post" class="form-inline">
                                        <input type="hidden" name="actionAdmin" value="remove">
                                        <input type="hidden" name="idCrociera" value="<%= crociera.getIdCrociera() %>">
                                        <input type="hidden" name="idTappa" value="<%= t.getId() %>">
                                        <button type="button" class="btn-admin-delete" onclick="showModal(this, 'Rimuovere questa tappa?')">Rimuovi</button>
                                    </form>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    <% } else { %>
                            <p class="stato-vuoto">Nessuna tappa associata a questa crociera.</p>
                    <% } %>
                </div>

                <div class="profilo">
                    <h2>Aggiungi Tappa</h2>
                    <form action="${pageContext.request.contextPath}/admin/crociera-tappe" method="post" class="riga-form-azioni">
                        <input type="hidden" name="actionAdmin" value="add">
                        <input type="hidden" name="idCrociera" value="<%= crociera.getIdCrociera() %>">
                        <div class="form-group form-group-min">
                            <label for="idTappa">Tappa</label>
                            <select name="idTappa" id="idTappa">
                            <% if (tappeDisponibili != null) {
                                for (TappaBean t : tappeDisponibili) {
                                    boolean giaAssociata = false;
                                    if (tappeAssociate != null) {
                                        for (TappaBean a : tappeAssociate) {
                                            if (a.getId() == t.getId()) { giaAssociata = true; break; }
                                        }
                                    }
                                    
                                    if (!giaAssociata) {
                                    %>
                                    <option value="<%= t.getId() %>"> <%= t.getLocalita() %> (<%= t.getPorto() %>)</option>
                                <%  }
                                }
                            } %>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="dataSosta">Data sosta</label>
                            <input type="date" name="dataSosta" id="dataSosta" required>
                        </div>
                        
                        <button type="submit" class="btn btn-primary btn-sm mt-20">Aggiungi</button>
                    </form>
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