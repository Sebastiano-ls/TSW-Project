<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>

<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard — S&S Crociere</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    </head>
    <body>
    <%@ include file="../header.jsp" %>

    <div class="admin-page profilo-page">
        <div class="container">
            <div class="profilo-header">
                <h1>Pannello Admin</h1>
                <p>Benvenuto, <%= utente.getNome() %>. Gestisci crociere, ordini e utenti.</p>
            </div>

            <div class="profilo-griglia">
                <a href="${pageContext.request.contextPath}/admin/crociere" class="profilo dashboard">
                    <h2>Crociere</h2>
                    <p class="dashboard-stat"><%= request.getAttribute("totCrociere") %></p>
                    <p class="dashboard-label">crociere nel catalogo</p>
                </a>

                <a href="${pageContext.request.contextPath}/admin/ordini" class="profilo dashboard">
                    <h2>Ordini</h2>
                    <p class="dashboard-stat"><%= request.getAttribute("totOrdini") %></p>
                    <p class="dashboard-label">ordini ricevuti</p>
                </a>

                <a href="${pageContext.request.contextPath}/admin/utenti" class="profilo dashboard">
                <h2>Utenti</h2>
                    <p class="dashboard-stat"><%= request.getAttribute("totUtenti") %></p>
                    <p class="dashboard-label">utenti registrati</p>
                </a>

                <a href="${pageContext.request.contextPath}/admin/tappe" class="profilo dashboard">
                    <h2>Tappe</h2>
                    <p class="dashboard-stat"><%= request.getAttribute("totTappe") %></p>
                    <p class="dashboard-label">tappe nel sistema</p>
                </a>
            </div>
        </div>
    </div>

    <%@ include file="../footer.jsp" %>
    </body>
</html>
