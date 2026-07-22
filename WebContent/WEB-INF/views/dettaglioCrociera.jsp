<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.CrocieraBean, model.TappaBean, org.apache.commons.text.StringEscapeUtils" %>

<%
    CrocieraBean crociera = (CrocieraBean) request.getAttribute("crociera");
    List<TappaBean> tappe = (List<TappaBean>) request.getAttribute("tappe");

    if (crociera == null) {
        response.sendRedirect(request.getContextPath() + "/catalogo");
        return;
    }

    double prezzoFinale = crociera.getPrezzo() * (1 - crociera.getSconto() / 100.0);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= StringEscapeUtils.escapeHtml4(crociera.getNomeCrociera()) %> — S&amp;S Crociere</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
        <script src="${pageContext.request.contextPath}/scripts/validazione-utente.js" defer></script>
    </head>
    <body>
        <%@ include file="header.jsp" %>

        <section class="section">
            <div class="container">
                <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-outline btn-sm mb-20">&larr; Torna al catalogo</a>

                <div class="dettaglio-header">
                    <div class="dettaglio-img">
                        <img src="${pageContext.request.contextPath}/images/crociera?id=<%= crociera.getIdCrociera() %>" alt="<%= crociera.getNomeCrociera() %>" class="img">
                    </div>

                    <div class="dettaglio-info">
                        <h1><%= StringEscapeUtils.escapeHtml4(crociera.getNomeCrociera()) %></h1>
                        <p class="dettaglio-descrizione"><%= StringEscapeUtils.escapeHtml4(crociera.getDescrizione() != null ? crociera.getDescrizione() : "") %></p>
                        <div class="dettaglio-viaggio">
                            <div class="dettaglio-viaggio-item">
                                <span class="dettaglio-viaggio-label">partenza</span>
                                <span class="dettaglio-viaggio-value"><%= crociera.getDataInizio() %></span>
                            </div>
                            <div class="dettaglio-viaggio-item">
                                <span class="dettaglio-viaggio-label">ritorno</span>
                                <span class="dettaglio-viaggio-value"><%= crociera.getDataFine() %></span>
                            </div>
                        </div>

                        <% if (crociera.getSconto() > 0) { %>
                            <p class="dettaglio-prezzo">
                            <span class="prezzo-originale"><%= String.format("%.2f", crociera.getPrezzo()) %> €</span>
                            <%= String.format("%.2f", prezzoFinale) %> € <small>a persona</small>
                            <span class="dettaglio-sconto">-<%= String.format("%.0f", crociera.getSconto()) %>%</span>
                            </p>
                        <% } else { %>
                            <p class="dettaglio-prezzo"><%= String.format("%.2f", crociera.getPrezzo()) %> € <small>a persona</small></p>
                        <% } %>

                        <form action="${pageContext.request.contextPath}/carrello" method="post" class="dettaglio-form" onsubmit="return validateAddToCartForm()">
                            <input type="hidden" name="action" value="addC">
                            <input type="hidden" name="idCrociera" value="<%= crociera.getIdCrociera() %>">
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="adulti">adulti</label>
                                    <input type="number" name="adulti" id="adulti" value="1" min="1" max="4" required>
                                </div>
                                <div class="form-group">
                                    <label for="bambini">minorenni</label>
                                    <input type="number" name="bambini" id="bambini" value="0" min="0" max="3">
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary">Aggiungi al carrello</button>
                        </form>
                    </div>
                </div>

                <% if (tappe != null && !tappe.isEmpty()) { %>
                <div class="dettaglio-tappe">
                    <h3>Itinerario</h3>
                    <div class="tappe">
                        <% for (TappaBean t : tappe) { %>
                        <div class="tappa-item">
                            <div class="tappa-marker"></div>
                            <div class="contenuto-tappa">
                                <span class="tappa-nome"><%= StringEscapeUtils.escapeHtml4(t.getLocalita()) %></span>
                                <span class="tappa-porto"><%= StringEscapeUtils.escapeHtml4(t.getPorto()) %></span>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>
                <% } %>
            </div>
        </section>

        <%@ include file="footer.jsp" %>
    </body>
</html>
