<%@page import="java.util.List, model.CrocieraBean, org.apache.commons.text.StringEscapeUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    List<CrocieraBean> crociere = (List<CrocieraBean>) request.getAttribute("crociere");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Catalogo — S&S Crociere</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    </head>

    <body>
        <%@ include file="header.jsp" %>

        <section class="section">
        <h2>Catalogo Crociere</h2>
        <p class="sottotitolo">Scegli la tua prossima avventura tra le nostre destinazioni.</p>
        <div class="container">
            <% if (crociere == null || crociere.isEmpty()) { %>
                <p class=vuoto>Nessuna crociera disponibile al momento</p>
            <% } else { %>
            <div class="griglia-offerte">
                <% for (CrocieraBean c : crociere) {
                double prezzoFinale = c.getPrezzo() * (1 - c.getSconto() / 100.0);
                %>
                <div class="offerta">
                    <div class="offerta-img catalogo-img">
                        <img src="${pageContext.request.contextPath}/images/crociera?id=<%= StringEscapeUtils.escapeHtml4(c.getIdCrociera()) %>" alt="<%= c.getNomeCrociera() %>" class="img">
                    </div>
                    <div class="offerta-body">
                        <h3><%= StringEscapeUtils.escapeHtml4(c.getNomeCrociera()) %></h3>
                        <p><%= StringEscapeUtils.escapeHtml4(c.getDescrizione() != null ? StringEscapeUtils.escapeHtml4(c.getDescrizione()) : "") %></p>
                        <p class="offerta-date"><%= c.getDataInizio() %> — <%= c.getDataFine() %></p>
                        <% if (c.getSconto() > 0) { %>
                            <p class="offerta-prezzo">
                                <span class="prezzo-originale"><%= String.format("%.2f", c.getPrezzo()) %> €</span>
                                <%= String.format("%.2f", prezzoFinale) %> € <small>a persona</small>
                            </p>
                        <% } else { %>
                            <p class="offerta-prezzo"><%= String.format("%.2f", c.getPrezzo()) %> € <small>a persona</small></p>
                        <% } %>
                        <div class="offerta-footer">
                            <a href="${pageContext.request.contextPath}/catalogo/dettaglio?id=<%= c.getIdCrociera() %>" class="btn btn-outline btn-sm">Dettagli</a>
                        </div>
                    </div>
                </div>
            <% } %>
            </div>
        <% } %>
        </div>
        </section>
            
        <%@ include file="footer.jsp" %>
        </body>
</html>