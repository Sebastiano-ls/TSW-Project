<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.OrdineBean, model.UtenteBean, org.apache.commons.text.StringEscapeUtils" %>
<%
    List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("ordini");
    String daData = request.getParameter("daData");
    String aData = request.getParameter("aData");
    String email = request.getParameter("email");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestisci Ordini — Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>

    <%@ include file="../header.jsp" %>

    <div class="admin-page profile-page">
        <div class="container">
            
            <div class="profile-header">
                <h1>Gestisci Ordini</h1>
                <p>Elenco degli ordini ricevuti. Usa i filtri per restringere la ricerca.</p>
            </div>

            <div class="profilo">
                <form method="get" action="${pageContext.request.contextPath}/admin/ordini" class="form-azioni-riga">
                    <div class="form-group">
                        <label for="daData">Da data</label>
                        <input type="date" name="daData" id="daData" 
                               value="<%= daData != null ? StringEscapeUtils.escapeHtml4(daData) : "" %>">
                    </div>
                    
                    <div class="form-group">
                        <label for="aData">A data</label>
                        <input type="date" name="aData" id="aData" 
                               value="<%= aData != null ? StringEscapeUtils.escapeHtml4(aData) : "" %>">
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Cliente (email)</label>
                        <input type="text" name="email" id="email" placeholder="email..." 
                               value="<%= email != null ? StringEscapeUtils.escapeHtml4(email) : "" %>">
                    </div>
                    
                    <button type="submit" class="btn btn-primary btn-sm">Filtra</button>
                    <a href="${pageContext.request.contextPath}/admin/ordini" class="btn btn-outline btn-sm">Reset</a>
                </form>
            </div>

            
            <div class="profilo wrapper-tabella">
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th class="admin-th">ID</th>
                            <th class="admin-th">Cliente</th>
                            <th class="admin-th">Email</th>
                            <th class="admin-th">Data</th>
                            <th class="admin-th">Totale</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% 
                        if (ordini != null && !ordini.isEmpty()) { 
                            int rowNum = 0;
                            for (OrdineBean o : ordini) { 
                                String rowClass = (rowNum++ % 2 == 0) ? "admin-tr" : "admin-tr-alt";
                    %>
                        <tr class="<%= rowClass %>">
                            <td class="admin-td">#<%= o.getIdOrdine() %></td>
                            <td class="admin-td">
                                <%= StringEscapeUtils.escapeHtml4(o.getNomeUtente()) %> 
                                <%= StringEscapeUtils.escapeHtml4(o.getCognomeUtente()) %>
                            </td>
                            <td class="admin-td"><%= StringEscapeUtils.escapeHtml4(o.getEmailUtente()) %></td>
                            <td class="admin-td"><%= o.getDataPagamento() %></td>
                            <td class="admin-td">€ <%= String.format("%.2f", o.getTotOrdine()) %></td>
                        </tr>
                    <% 
                            } 
                        } else { 
                    %>
                        <tr>
                            <td colspan="5" class="admin-td stato-vuoto">Nessun ordine trovato.</td>
                        </tr>
                    <% 
                        } 
                    %>
                    </tbody>
                </table>
            </div>

        </div>
    </div>

    <%@ include file="../footer.jsp" %>

</body>
</html>