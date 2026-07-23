<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.OrdineBean, org.apache.commons.text.StringEscapeUtils" %>
<%
    List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("ordini");
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
  <title>I miei Ordini — S&S Crociere</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
  <%@ include file="../header.jsp" %>

  <section class="section">
    <h2>I miei Ordini</h2>
    <p class="sottotitolo">Storico dei tuoi acquisti.</p>
    <div class="container">
    <% if (success != null) { %>
      <div class="alert alert-success"><%= StringEscapeUtils.escapeHtml4(success) %></div>
    <% } %>

    <% if (ordini == null || ordini.isEmpty()) { %>
      <div class="carrello-vuoto">
        <p>nessun ordine effettuato</p>
        <a href="${pageContext.request.contextPath}/catalogo" class="btn">Sfoglia il catalogo</a>
      </div>
      <% } else { %>
      <div class="ordini-tabella">
        <div class="ordini-intestazione">
          <span>ordine</span>
          <span>data</span>
          <span>totale</span>
          <span>dettaglio</span>
        </div>
      
      <% for (OrdineBean o : ordini) { %>
        <div class="ordini-riga">
          <span>#<%= o.getIdOrdine() %></span>
          <span><%= o.getDataPagamento() %></span>
          <span><%= String.format("%.2f", o.getTotOrdine()) %> €</span>
          <span><a href="${pageContext.request.contextPath}/common/ordini/dettaglio?id=<%= o.getIdOrdine() %>" class="btn btn-outline btn-sm">Dettaglio</a></span>
        </div>
      <% } %>
    </div>
  <% } %>
  
    </div>
  </section>

  <%@ include file="../footer.jsp" %>
</body>
</html>
