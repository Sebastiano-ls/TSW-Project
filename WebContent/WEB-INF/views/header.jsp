<%@ page import="model.UtenteBean" %>
<%
    UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("utente");
%>
<nav class="navbar">
  <div class="container">
    <a href="${pageContext.request.contextPath}/home" class="navbar-brand"><img src="images/S&S_Cruises_Logo.jpeg" alt="Logo not found" width="150" height="100"></a>
    <ul class="navbar-nav">
      <li><a href="${pageContext.request.contextPath}/home" class="nav-link">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/home#ricerca" class="nav-link">Prenota ora</a></li>
      <li><a href="${pageContext.request.contextPath}/ricerca?action=recenti" class="nav-link">Catalogo</a></li>
      <li><a href="${pageContext.request.contextPath}/carrello" class="nav-link">Carrello</a></li>
<% if (utenteLoggato == null) { %>
      <li class="dropdown">
        <button class="dropdown-toggle">Accedi</button>
        <div class="dropdown-menu">
          <div class="dropdown-menu-header">Accesso</div>
          <form class="dropdown-login-form" action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
              <label for="login-email">Email</label>
              <input type="email" name="email" id="login-email" placeholder="la tua email" required>
            </div>
            <div class="form-group">
              <label for="login-password">Password</label>
              <input type="password" name="password" id="login-password" placeholder="••••••••" required>
            </div>
            <button type="submit" class="btn btn-primary btn-sm">Accedi</button>
            <div class="dropdown-register-link">
              Non hai un account? <a href="${pageContext.request.contextPath}/register">Registrati</a>
            </div>
          </form>
        </div>
      </li>
<% } else { %>
      <li><a href="${pageContext.request.contextPath}/storicoOrdini" class="nav-link">I miei Ordini</a></li>
      <li class="dropdown">
        <button class="dropdown-toggle">Ciao, <%= utenteLoggato.getNome() %></button>
        <div class="dropdown-menu">
          <div class="dropdown-menu-header">Il mio account</div>
          <a href="${pageContext.request.contextPath}/profile">Il mio Profilo</a>
          <a href="${pageContext.request.contextPath}/ordini">I miei Ordini</a>
<% if ("admin".equals(utenteLoggato.getRuolo())) { %>
          <div class="dropdown-divider"></div>
          <a href="${pageContext.request.contextPath}/admin/dashboard">Pannello Admin</a>
<% } %>
          <div class="dropdown-divider"></div>
          <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
      </li>
<% } %>
    </ul>
  </div>
</nav>