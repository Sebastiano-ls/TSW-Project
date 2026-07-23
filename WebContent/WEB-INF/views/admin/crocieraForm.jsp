<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.CrocieraBean" %>

<%
    CrocieraBean crociera = (CrocieraBean) request.getAttribute("crociera");
    boolean isEdit = crociera != null;
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= isEdit ? "Modifica" : "Nuova" %> Crociera — Admin</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    </head>
    <body>
        <%@ include file="../header.jsp" %>

        <div class="form-page">
            <div class="form form-grande">
                <h2><%= isEdit ? "Modifica Crociera" : "Nuova Crociera" %></h2>
                <form action="${pageContext.request.contextPath}/admin/crociere" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="actionAdmin" value="<%= isEdit ? "edit" : "create" %>">
                    <% if (isEdit) { %>
                        <input type="hidden" name="id" value="<%= crociera.getIdCrociera() %>">
                    <% } %>

                    <div class="form-group">
                        <label for="nomeCrociera">Nome Crociera</label>
                        <input type="text" name="nomeCrociera" id="nomeCrociera" required value="<%= isEdit ? crociera.getNomeCrociera() : "" %>">
                    </div>

                    <div class="form-group">
                        <label for="descrizione">Descrizione</label>
                        <textarea name="descrizione" id="descrizione" rows="4" class="form-textarea"><%= isEdit && crociera.getDescrizione() != null ? crociera.getDescrizione() : "" %></textarea>
                    </div>

                    <div class="riga-form">
                        <div class="form-group">
                            <label for="dataInizio">Data Inizio</label>
                            <input type="date" name="dataInizio" id="dataInizio" required value="<%= isEdit ? crociera.getDataInizio() : "" %>">
                        </div>
                    
                        <div class="form-group">
                            <label for="dataFine">Data Fine</label>
                            <input type="date" name="dataFine" id="dataFine" required value="<%= isEdit ? crociera.getDataFine() : "" %>">
                        </div>
                    </div>

                    <div class="riga-form">
                        <div class="form-group">
                            <label for="prezzo">Prezzo (€)</label>
                            <input type="number" name="prezzo" id="prezzo" step="0.01" min="0" required value="<%= isEdit ? crociera.getPrezzo() : "" %>">
                        </div>

                        <div class="form-group">
                            <label for="sconto">Sconto (%)</label>
                            <input type="number" name="sconto" id="sconto" step="0.01" min="0" max="100" value="<%= isEdit ? crociera.getSconto() : "0" %>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="immagineCrociera">Immagine</label>
                        <% if (isEdit && crociera.getImmagineCrociera() != null) { %>
                            <img src="${pageContext.request.contextPath}/images/crociera?id=<%= crociera.getIdCrociera() %>" alt="anteprima" class="image-preview">
                            <small class="testo-sfumato text-small">lascia vuoto per mantenere l'immagine attuale</small>
                        <% } %>
                        <input type="file" name="immagineCrociera" id="immagineCrociera" accept="image/*">
                    </div>

                    <div class="form-group">
                        <label>
                            <input type="checkbox" name="attivo" <%= isEdit && crociera.isAttivo() ? "checked" : "" %>> Crociera attiva
                        </label>
                    </div>

                    <div class="form-azione riga-form-azione">
                        <a href="${pageContext.request.contextPath}/admin/crociere" class="btn btn-outline">Annulla</a>
                        <button type="submit" class="btn btn-primary"><%= isEdit ? "Salva Modifiche" : "Crea Crociera" %></button>
                    </div>
                </form>
            </div>
        </div>

        <%@ include file="../footer.jsp" %>
    </body>
</html>