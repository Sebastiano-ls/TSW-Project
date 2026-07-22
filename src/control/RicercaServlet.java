package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import dao.CrocieraDAO;
import dao.CrocieraDAOImpl;
import model.CrocieraBean;

@WebServlet("/catalogo")
public class RicercaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CrocieraDAO crocieraDAO;

    public void init() throws ServletException{
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        
        crocieraDAO = new CrocieraDAOImpl(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("ricercaAction");
        if("ricerca".equalsIgnoreCase(action)){
            //PRENDO I PARAMETRI DEL FORM DALLA RICHIESTA
            String des = request.getParameter("des");
            String par = request.getParameter("par");
            Date data = Date.valueOf(LocalDate.parse(request.getParameter("data")));
            
            des = (!des.trim().isEmpty()) ? StringEscapeUtils.escapeHtml4(des.trim()) : null;
            par = (!par.trim().isEmpty()) ? StringEscapeUtils.escapeHtml4(par.trim()) : null;
            data = (!request.getParameter("data").equals(null) && !request.getParameter("data").equals("")) ? Date.valueOf(LocalDate.parse(request.getParameter("data"))) : null;
            
            //CHIAMO IL DAO
            try{
                List<CrocieraBean> risultati = crocieraDAO.doRetrieveByParams(des, par, data);
                request.setAttribute("crociere", risultati);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }else if("prezzi_bassi".equalsIgnoreCase(action)){
            try{
                List<CrocieraBean> risultati = crocieraDAO.doRetrieveByPrezziBassi();
                request.setAttribute("crociere", risultati);
            } catch (SQLException e){
                throw new ServletException(e);
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/risultati.jsp");
        dispatcher.forward(request, response);
    }
}
