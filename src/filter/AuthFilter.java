package filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.UtenteBean;

@WebFilter("/*")
public class AuthFilter extends HttpFilter{
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
       String path = request.getServletPath();

       if(!path.startsWith("/admin/") && !path.startsWith("/common/")){
        chain.doFilter(request, response);
        return;
       }

       HttpSession session = request.getSession(false);
       UtenteBean utente = (session != null) ? (UtenteBean) session.getAttribute("utente") : null;

       boolean autorizzato = false;
       if(utente!=null){
        if(path.startsWith("/admin/")){
            autorizzato = utente.getRuolo().equalsIgnoreCase("admin");
        }else if(path.startsWith("/common/")){
            autorizzato = utente.getRuolo().equalsIgnoreCase("admin") || utente.getRuolo().equalsIgnoreCase("user");
        }
       }

       if(autorizzato){
        chain.doFilter(request, response);
       }else{
        response.sendRedirect(request.getContextPath() + "/login");
       }
    }
}