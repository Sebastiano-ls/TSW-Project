<%@page import="java.util.List, model.CrocieraBean"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<CrocieraBean> crociere = (List<CrocieraBean>) request.getAttribute("risultati");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Risultati Ricerca</title>
    </head>

    <body>
        <jsp include url="${pageContext.request.contextPath}/header">

    <section>
        <h2>Catalogo Crociere</h2>
        <p class="sottotitolo">Scegli la tua prossima avventura tra le nostre destinazioni.</p>
        <div class="container">
        <% if (crociere == null || crociere.isEmpty()) { %>
            <p>Nessuna crociera disponibile al momento</p>
        <% } else { %>
            <div class="griglia-offerte">
            <% for (CrocieraBean c : crociere) {
                double prezzoFinale = c.getPrezzo() * (1 - c.getSconto() / 100.0);
                %>
                <div class="card-offerta">
                    <div class="card-offerta-img card-catalogo-img">
                        <img src="${pageContext.request.contextPath}/images/crociera?id=<%= c.getIdCrociera() %>" alt="<%= c.getNomeCrociera() %>" class="img-fit">
                    </div>
                    <div class="card-offerta-body">
                        <h3><%= SecurityUtils.escapeHtml(c.getNomeCrociera()) %></h3>
                        <p><%= SecurityUtils.escapeHtml(c.getDescrizione() != null ? c.getDescrizione() : "") %></p>
                        <p class="card-offerta-date"><%= c.getDataInizio() %> — <%= c.getDataFine() %></p>
                        <% if (c.getSconto() > 0) { %>
                            <p class="card-offerta-prezzo">
                            <span class="prezzo-originale"><%= String.format("%.2f", c.getPrezzo()) %> €</span>
                            <%= String.format("%.2f", prezzoFinale) %> € <small>a persona</small>
                            </p>
                        <% } else { %>
                            <p class="card-offerta-prezzo"><%= String.format("%.2f", c.getPrezzo()) %> € <small>a persona</small></p>
                        <% } %>
                        <div class="card-offerta-footer">
                            <a href="${pageContext.request.contextPath}/catalogo/dettaglio?id=<%= c.getIdCrociera() %>" class="btn btn-outline btn-sm">Dettagli</a>
                        </div>
                    </div>
                </div>
            <% } %>
            </div>
        <% } %>
        </div>
        </section>
        
        <jsp include url="${pageContext.request.contextPath}/footer">
    </body>
</html>