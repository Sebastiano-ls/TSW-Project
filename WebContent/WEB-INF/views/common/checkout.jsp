<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.ItemCarrello, model.UtenteBean" %>

<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    List<ItemCarrello> carrello = (List<ItemCarrello>) session.getAttribute("carrello");
    if (carrello == null || carrello.isEmpty()) {
        response.sendRedirect(request.getContextPath() + "/carrello");
        return;
    }

    double totaleCarrello = 0;
    for (ItemCarrello item : carrello) {
        totaleCarrello += item.getTotale();
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Checkout — S&S Crociere</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    </head>
    <body>
        <%@ include file="../header.jsp" %>

        <section class="section">
            <h2>Checkout</h2>
            <p class="sottotitolo">Conferma i dettagli del tuo ordine.</p>
            <div class="container">
                <div class="checkout-griglia">
                    <div class="checkout-riepilogo">
                        <h3>Riepilogo ordine</h3>
                        <div class="checkout-items">
                            <% for (ItemCarrello item : carrello) { %>
                                <div class="checkout-item">
                                    <div class="checkout-item-info">
                                        <strong><%= item.getCrociera().getNome() %></strong>
                                        <span><%= item.getCrociera().getDataInizio() %> — <%= item.getCrociera().getDataFine() %></span>
                                    </div>

                                    <div class="checkout-item-quantita">
                                        <%= item.getNumBiglAdu() %> adulti, <%= item.getNumBiglChilds() %> bambini
                                    </div>

                                    <div class="checkout-item-prezzo"><%= String.format("%.2f", item.getTotale()) %> €</div>
                                </div>
                            <% } %>
                        </div>

                        <div class="checkout-totale">
                            <span>Totale</span>
                            <span><%= String.format("%.2f", totaleCarrello) %> €</span>
                        </div>
                    </div>

                    <div class="checkout-form">
                        <h3>Dati intestatario</h3>
                        <div class="checkout-utente-info">
                            <p><strong><%= utente.getNome() %> <%= utente.getCognome() %></strong></p>
                            <p><%= utente.getEmail() %></p>
                        </div>

                        <form action="${pageContext.request.contextPath}/common/checkout" method="post">
                            <input type="hidden" name="action" value="create">
                            <p class="checkout-conferma">Confermando l'ordine accetti i <a href="#">termini e condizioni</a> di S&S Crociere.</p>
                            <button type="submit" class="btn btn-block">Conferma ordine</button>
                        </form>

                        <div class="checkout-back">
                            <a href="${pageContext.request.contextPath}/carrello" class="btn btn-outline btn-sm btn-link-block">&larr; Torna al carrello</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <%@ include file="../footer.jsp" %>
    </body>
</html>