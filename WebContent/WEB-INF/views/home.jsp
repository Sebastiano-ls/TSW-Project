<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.CrocieraBean, org.apache.commons.text.StringEscapeUtils" %>
<%
    List<CrocieraBean> crociere = (List<CrocieraBean>) request.getAttribute("crociere");
%>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Home — S&S Crociere</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
  <%@ include file="header.jsp" %>

  <section class="hero">
    <h1>Il mare è solo <span>l'inizio</span></h1>
    <p>Scopri le nostre crociere verso le destinazioni più belle del mondo.</p>
    <form class="hero-search" action="${pageContext.request.contextPath}/catalogo" method="get">
        <select name="destinazione">
          <option value="">Destinazione</option>
          <option value="Mediterraneo">Mediterraneo</option>
          <option value="Caraibi">Caraibi</option>
          <option value="Fiordi Norvegesi">Fiordi Norvegesi</option>
          <option value="Baltico">Capitali Baltiche</option>
          <option value="Grecia">Isole Greche</option>
        </select>
        <div class="divisore-ricerca"></div>
        <input type="date" name="data" placeholder="Data partenza">
        <div class="divisore-ricerca"></div>
      <select name="durata">
        <option value="">Durata</option>
        <option value="7">1 settimana</option>
        <option value="10">10 giorni</option>
        <option value="14">2 settimane</option>
      </select>
      <button type="submit">Cerca</button>
    </form>
  </section>

  <section class="section">
    <h2>Offerte Speciali</h2>
    <p class="sottotitolo">Le migliori destinazioni ai prezzi più vantaggiosi.</p>
    <div class="container">
    <% if (crociere == null || crociere.isEmpty()) { %>
      <p class="vuoto">nessuna offerta speciale disponibile al momento</p>
    <% } else { %>
      <div class="griglia-offerte">
      <% 
         int count = 0;
         for (CrocieraBean c : crociere) { 
            if (count >= 3) break; // Mostra solo le prime 3 offerte
            double prezzoFinale = c.getPrezzo() * (1 - c.getSconto() / 100.0);
            count++;
      %>
        <div class="offerta">
          <div class="offerta-img catalogo-img">
            <img src="${pageContext.request.contextPath}/images/crociera?id=<%= c.getId() %>" alt="<%= StringEscapeUtils.escapeHtml4(c.getNome()) %>" class="img" onerror="this.style.display='none';this.parentElement.innerHTML='<span class=img-fallback>🚢</span>'">
            <span class="img-fallback hidden">🚢</span>
          </div>
          <div class="offerta-body">
            <h3><%= StringEscapeUtils.escapeHtml4(c.getNome()) %></h3>
            <p><%= StringEscapeUtils.escapeHtml4(c.getDes() != null && c.getDes().length() > 50 ? c.getDes().substring(0, 50) + "..." : (c.getDes() != null ? c.getDes() : "")) %></p>
            <% if (c.getSconto() > 0) { %>
              <p class="offerta-prezzo">
                <span class="prezzo-originale"><%= String.format("%.2f", c.getPrezzo()) %> €</span>
                <%= String.format("%.2f", prezzoFinale) %> € <small>a persona</small>
              </p>
            <% } else { %>
              <p class="offerta-prezzo"><%= String.format("%.2f", c.getPrezzo()) %> € <small>a persona</small></p>
            <% } %>
            <div class="offerta-footer">
              <a href="${pageContext.request.contextPath}/catalogo/dettaglio?id=<%= c.getId() %>" class="btn btn-outline btn-sm">Dettagli</a>
            </div>
          </div>
        </div>
      <% } %>
      </div>
    <% } %>
    </div>
  </section>

  <section class="section sezione-bianca">
    <h2>Destinazioni</h2>
    <p class="sottotitolo">Scegli la tua prossima meta.</p>
    <div class="container">
      <div class="griglia-dest">
        <div class="card-dest"><span class="card-dest-icon">🌊</span><h4>Mediterraneo</h4><span>Italia, Grecia, Spagna</span></div>
        <div class="card-dest"><span class="card-dest-icon">🏝️</span><h4>Caraibi</h4><span>Bahamas, Messico</span></div>
	<div class="card-dest"><span class="card-dest-icon">🏔️</span><h4>Fiordi</h4><span>Norvegia, Islanda</span></div>
        <div class="card-dest"><span class="card-dest-icon">🏰</span><h4>Baltico</h4><span>Stoccolma, Helsinki</span></div>
        <div class="card-dest"><span class="card-dest-icon">⛪</span><h4>Grecia</h4><span>Santorini, Mykonos</span></div>
        <div class="card-dest"><span class="card-dest-icon">🗺️</span><h4>S. America</h4><span>Brasile, Argentina</span></div>
        <div class="card-dest"><span class="card-dest-icon">🌴</span><h4>Oceano Indiano</h4><span>Maldive, Seychelles</span></div>
        <div class="card-dest"><span class="card-dest-icon">🗿</span><h4>Medio Oriente</h4><span>Dubai, Abu Dhabi</span></div>
      </div>
    </div>
  </section>

  <%@ include file="footer.jsp" %>
</body>
</html>