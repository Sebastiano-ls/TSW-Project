<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.text.StringEscapeUtils, model.DettaglioOrdineBean" %>

<%
    DettaglioOrdineBean d = (DettaglioOrdineBean) request.getAttribute("dettaglio");
    if (d == null) {
        response.sendRedirect(request.getContextPath() + "/common/ordini");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= StringEscapeUtils.escapeHtml4(d.getNomeCrocieraArchiviato()) %> — S&S Crociere</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    </head>
    <body>
        <%@ include file="../header.jsp" %>

        <section class="section">
            <div class="container">
                <a href="${pageContext.request.contextPath}/ordini" class="btn btn-outline btn-sm mb-20">&larr; Torna agli ordini</a>

                <div class="dettaglio-header">
                    <div class="dettaglio-info">
                        <h1><%= StringEscapeUtils.escapeHtml4(d.getNomeCrocieraArchiviato()) %></h1>
                        <p class="dettaglio-descrizione"><%= d.getDescrizioneArchiviato() != null ? StringEscapeUtils.escapeHtml4(d.getDescrizioneArchiviato()) : "" %></p>
                        <div class="dettaglio-viaggio">
                            <div class="dettaglio-viaggio-item">
                                <span class="dettaglio-viaggio-label">partenza</span>
                                <span class="dettaglio-viaggio-value"><%= d.getDataInizioArchiviato() %></span>
                            </div>

                            <div class="dettaglio-viaggio-item">
                                <span class="dettaglio-viaggio-label">ritorno</span>
                                <span class="dettaglio-viaggio-value"><%= d.getDataFineArchiviato() %></span>
                            </div>
                        </div>

                        <p class="dettaglio-prezzo"><%= String.format("%.2f", d.getPrezzoArchiviato()) %> € <small>a persona</small></p>
                    </div>
                </div>

                <% if (d.getTappeArchiviato() != null && !d.getTappeArchiviato().isEmpty()) { %>
                <div class="dettaglio-tappe">
                    <h3>Itinerario</h3>
                    <div class="tappe">
                        <% String[] tappe = d.getTappeArchiviato().split(" &rarr; "); %>
                        <% for (int i = 0; i < tappe.length; i++) { %>
                        <div class="tappa-item">
                            <div class="tappa-marker"></div>
                            <div class="tappa-content">
                                <span class="tappa-nome"><%= StringEscapeUtils.escapeHtml4(tappe[i]) %></span>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>
                <% } %>
            </div>
        </section>

        <%@ include file="../footer.jsp" %>
    </body>
</html>
