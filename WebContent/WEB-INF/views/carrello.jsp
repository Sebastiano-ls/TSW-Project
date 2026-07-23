<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.ItemCarrello, org.apache.commons.text.StringEscapeUtils" %>

<%
    List<ItemCarrello> carrello = (List<ItemCarrello>) session.getAttribute("carrello");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Carrello — S&S Crociere</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
        <script src="${pageContext.request.contextPath}/scripts/carrello-ajax.js" defer></script>
        <script src="${pageContext.request.contextPath}/scripts/modal.js" defer></script>
    </head>
    <body>
        <%@ include file="header.jsp" %>

        <section class="section">
            <h2>Il tuo Carrello</h2>
            <p class="sottotitolo">Rivedi la tua selezione prima di procedere.</p>
            <div class="container">
                <% if (error != null) { %>
                <div class="alert alert-error"><%= StringEscapeUtils.escapeHtml4(error) %></div>
                <% } %>

                <% if (carrello == null || carrello.isEmpty()) { %>
                <div class="carrello-vuoto">
                    <p>Il carrello è vuoto</p>
                    <a href="${pageContext.request.contextPath}/catalogo" class="btn">Sfoglia il catalogo</a>
                </div>
                <% } else {
                    double totaleCarrello = 0;
                    for (ItemCarrello item : carrello) {
                        totaleCarrello += item.getTotale();
                    }
                %>

                <form action="${pageContext.request.contextPath}/carrello" method="post" id="carrello-form">
                    <input type="hidden" name="action" value="svuota">
                </form>
                <div class="carrello-azioni-alto">
                    <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-outline btn-sm">&larr; Continua lo shopping</a>
                    <button type="button" form="carrello-form" class="btn btn-danger btn-sm" onclick="showModal(this, 'svuotare il carrello?')">Svuota carrello</button>
                </div>

                <div class="carrello-tabella">
                    <div class="carrello-intestazione">
                        <span>prodotto</span>
                        <span>prezzo</span>
                        <span>adulti</span>
                        <span>bambini</span>
                        <span>subtotale</span>
                        <span></span>
                    </div>

                    <% for (int i = 0; i < carrello.size(); i++) {
                        ItemCarrello item = carrello.get(i);
                    %>

                    <div class="carrello-riga" riga-index="<%= i %>">
                        <span>
                            <strong><%= StringEscapeUtils.escapeHtml4(item.getCrociera().getNome()) %></strong>
                            <span class="carrello-date"><%= item.getCrociera().getDataInizio() %> — <%= item.getCrociera().getDataFine() %></span>
                        </span>

                        <span><%= String.format("%.2f", item.getPrezzoApplicato()) %> €</span>

                        <span>
                            <p id="alert-error">
                            <form action="${pageContext.request.contextPath}/carrello" method="post" class="carrello-quantita-form">
                                <input type="hidden" name="action" value="updateQuantita">
                                <input type="hidden" name="index" value="<%= i %>">
                                <input type="number" name="adulti" value="<%= item.getNumBiglAdu() %>" min="0" max="20" class="carrello-quantita-input" ajax-quantita>
                            </form>
                        </span>

                        <p id="alert-error">
                        <span>
                            <form action="${pageContext.request.contextPath}/carrello" method="post" class="carrello-quantita-form">
                                <input type="hidden" name="action" value="updateQuantita">
                                <input type="hidden" name="index" value="<%= i %>">
                                <input type="number" name="bambini" value="<%= item.getNumBiglChilds() %>" min="0" max="10" class="carrello-quantita-input" ajax-quantita>
                            </form>
                        </span>

                        <span><%= String.format("%.2f", item.getTotale()) %> €</span>
                        
                        <span>
                            <form action="${pageContext.request.contextPath}/carrello" method="post">
                                <input type="hidden" name="action" value="deleteC">
                                <input type="hidden" name="index" value="<%= i %>">
                                <button type="submit" class="btn btn-danger btn-sm">Rimuovi</button>
                            </form>
                        </span>
                    </div>
                <% } %>
                </div>

                <div class="carrello-totale">
                    <span>Totale ordine:</span>
                    <span class="carrello-totale-valore"><%= String.format("%.2f", totaleCarrello) %> €</span>
                </div>

                <div class="carrello-checkout">
                    <a href="${pageContext.request.contextPath}/common/checkout" class="btn">Procedi al checkout</a>
                </div>
                <% } %>
            </div>
        </section>

        <%@ include file="footer.jsp" %>

        //oggetti di appoggio per la funziona JS per la rimozione di oggetti
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