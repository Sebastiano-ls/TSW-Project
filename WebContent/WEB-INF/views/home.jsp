<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.CrocieraBean, org.apache.commons.text.StringEscapeUtils" %>
<%
    List<CrocieraBean> crociere = (List<CrocieraBean>) request.getAttribute("crociere");
%>
<!DOCTYPE html>
    <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Home — S&S Cruises</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
        </head>
        <body>
            <jsp:include page="header.jsp" />

            <h1>Il mare è solo <span>l'inizio</span></h1>
            <p>Scopri le nostre crociere verso le destinazioni più belle del mondo.</p>
    
            <section>
                <form class="ricerca" action="${pageContext.request.contextPath}/catalogo?ricercaAction='ricerca'" method="post">
                    <fieldset>
                        <legend>Prenota la tua crociera</legend>
                        <select name="des">
                            <option value="" selected>Destinazione</option>
                            <option value="Mediterraneo">Mediterraneo</option>
                            <option value="Caraibi">Caraibi</option>
                            <option value="Fiordi Norvegesi">Fiordi Norvegesi</option>
                            <option value="Grecia">Isole Greche</option>
                        </select>

                        <div class="divisore-ricerca"/>

                        <select name="par">
                            <option value="" selected>Porto di partenza</option>
                            <option value="Venezia Porto">Venezia</option>
                            <option value="Dubrovnik Porto">Dubrovnik</option>
                            <option value="Atene Porto">Atene</option>
                            <option value="Miami Porto">Miami</option>
                            <option value="Nassau Porto">Nassau</option>
                            <option value="Cozumel Porto" selected>Cozumel</option>
                            <option value="Bergen Porto">Bergen</option>
                            <option value="Oslo Porto">Oslo</option>
                            <option value="Stavanger Porto">Stavanger</option>
                            <option value="Mykonos Porto">Mykonos</option>
                            <option value="Santorini Porto">Santorini</option>
                            <option value="Rodi Porto">Rodi</option>
                        </select>

                        <div class="divisore-ricerca"/>

                        <label for="data">Data Partenza</label>
                        <input type="date" name="data" id="data" value="">

                        <div class="divisore-ricerca"/>

                        <label for="adults">Quanti adulti?</label>
                        <input type="number" name="adults" id="adults" value="1" min="1" max="4">

                        <div class="divisore-ricerca"/>

                        <label for="childs">Quanti minorenni?</label>
                        <input type="number" name="childs" id="childs" value="0" min="0" max="3">

                        <input type="submit" value="Cerca" class="ricerca-btn">
                    </fieldset>
                </form>
            </section>


            <section>
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
                        if (count >= 3) 
                            break;
                        double prezzoFinale = c.getPrezzo() * (1 - c.getSconto() / 100.0);
                        count++;
                    %>
                    <div class="offerta">
                        <div class="offerta-img catalogo-img">
                            <img src="${pageContext.request.contextPath}/images/crociera?id=<%= c.getId() %>" alt="<%= StringEscapeUtils.escapeHtml4(c.getNome()) %>" class="img">
                        </div>
                        <div class="offerta-body">
                            <h3><%= StringEscapeUtils.escapeHtml4(c.getNome()) %></h3>
                            <p><%= StringEscapeUtils.escapeHtml4(c.getDes() != null ? c.getDes() : "") %></p>
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
                    <% } %>
                    </div>
                <% } %>
                </div>
            </section>

             <!-- Chiamate predefinite -->
            <section class="sezione-bianca">
                <h2>Vedi anche: <span>Prezzi più Bassi</span></h2>
                <div class="container">
                    <a href="${pageContext.request.contextPath}/ricerca?ricercaAction='prezzi_bassi'"> <img src="" alt="PREZZI PIU BASSI"> </a>
                </div>

                <h2>Parla con noi</h2>
                <div class="container">
                    <a href="${pageContext.request.contextPath}/supporto"> <img src="" alt="ASSISTENZA"> </a>
                </div>
            </section>

            <jsp:include page="footer.jsp" />
        </body>
    </html>