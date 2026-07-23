<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.OrdineBean, model.DettaglioOrdineBean, org.apache.commons.text.StringEscapeUtils" %>

<%
    OrdineBean ordine = (OrdineBean) request.getAttribute("ordine");
    List<DettaglioOrdineBean> dettagli = (List<DettaglioOrdineBean>) request.getAttribute("dettagli");
    if (ordine == null) {
        response.sendRedirect(request.getContextPath() + "/common/ordini");
        return;
    }
    String success = (String) session.getAttribute("success");
    if (success != null) {
        session.removeAttribute("success");
    }
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dettaglio Ordine #<%= ordine.getIdOrdine() %> — S&S Crociere</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
  <%@ include file="../header.jsp" %>

  <section class="section">
    <div class="container">
      <a href="${pageContext.request.contextPath}/common/ordini" class="btn btn-outline btn-sm">&larr; Torna agli ordini</a>

      <% if (success != null) { %>
        <div class="alert alert-success"><%= StringEscapeUtils.escapeHtml4(success) %></div>
      <% } %>

      <div class="dettaglio-ordine-header">
        <h1>Ordine #<%= ordine.getIdOrdine() %></h1>
        <p class="dettaglio-ordine-data">Data: <%= ordine.getDataPagamento() %></p>
      </div>

      <div class="profilo">
        <h2>Dettagli ordine</h2>
        <div class="ordine-dettagli-info">
          <div class="riga-profilo">
            <span class="label-profilo">cliente</span>
            <span class="profile-value">
              <%= StringEscapeUtils.escapeHtml4(ordine.getNomeUtente()) %> <%= StringEscapeUtils.escapeHtml4(ordine.getCognomeUtente()) %>
            </span>
          </div>
          <div class="riga-profilo">
            <span class="label-profilo">email</span>
            <span class="profile-value"><%= StringEscapeUtils.escapeHtml4(ordine.getEmailUtente()) %></span>
          </div>
          <div class="profile-row">
            <span class="label-profilo">totale</span>
            <span class="profile-value"><%= String.format("%.2f", ordine.getTotOrdine()) %> €</span>
          </div>
        </div>
      </div>

      <div class="profilo">
        <h2>Crociere acquistate</h2>
        <div class="ordini-tabella">
          <div class="ordini-intestazione">
            <span>crociera</span>
            <span>adulti</span>
            <span>bambini</span>
            <span>prezzo unitario</span>
            <span>subtotale</span>
          </div>
          <% if (dettagli != null) {
              for (DettaglioOrdineBean d : dettagli) {
                    double sub = d.getPrezzoArchiviato() * d.getNumBigliettoAdulto() + (d.getPrezzoArchiviato() * 0.5 * d.getNumBigliettoBambino());
          %>
            <div class="ordini-riga">
              <span>
                <a href="${pageContext.request.contextPath}/common/ordini/archivio-crociera?idDettaglio=<%= d.getIdDettaglio() %>" class="link-archivio">
                  <%= StringEscapeUtils.escapeHtml4(d.getNomeCrocieraArchiviato()) %>
                </a>
              </span>
              <span><%= d.getNumBigliettoAdulto() %></span>
              <span><%= d.getNumBigliettoBambino() %></span>
              <span><%= String.format("%.2f", d.getPrezzoArchiviato()) %> €</span>
              <span><%= String.format("%.2f", sub) %> €</span>
            </div>
          <%  }
            } %>
        </div>
      </div>
    </div>
  </section>

  <%@ include file="../footer.jsp" %>
</body>
</html>