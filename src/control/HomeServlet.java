package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.CrocieraDAO;
import dao.CrocieraDAOImpl;
import model.CrocieraBean;
import model.ItemCarrello;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CrocieraDAO crocieraDAO;

    public void init() throws ServletException{
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        crocieraDAO = new CrocieraDAOImpl(ds);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        
        if (session.getAttribute("userAgent") == null) {
            String userAgent = request.getHeader("User-Agent");
            String browser = "Sconosciuto";

            if (userAgent != null) {
                if (userAgent.contains("Edg")) {
                    browser = "Edge";
                } else if (userAgent.contains("OPR") || userAgent.contains("Opera")) {
                    browser = "Opera";
                } else if (userAgent.contains("Chrome")) {
                    browser = "Chrome";
                } else if (userAgent.contains("Firefox")) {
                    browser = "Firefox";
                } else if (userAgent.contains("Safari")) {
                    browser = "Safari";
                }
            }
            
            session.setAttribute("userAgent", browser);
        }

        try{
            List<CrocieraBean> lastMinute = crocieraDAO.doRetrieveAllAttivi();
            request.setAttribute("crociere", lastMinute);
        }catch(SQLException e){
            throw new ServletException(e);
        }

        if (session.getAttribute("carrello") == null)
            session.setAttribute("carrello", new ArrayList<ItemCarrello>());

        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}
