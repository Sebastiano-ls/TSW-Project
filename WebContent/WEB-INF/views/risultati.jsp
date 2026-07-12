<%@page import="java.util.List"%>
<%@page import="model.Crociera"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Risultati</title>
    </head>

    <body>
        <h1>RISULTATI IN MERITO ALLA TUA SELEZIONE:</h1>
         <%
         List<Crociera> risultati = (List<Crociera>)request.getAttribute("risultati");
         for(Crociera c : risultati){
            out.println("<br>" + c);
         }
         %>
    </body>
</html>