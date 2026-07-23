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

        String des = request.getParameter("destinazione");
        String dataParam = request.getParameter("data");
        String par = request.getParameter("durata");
        Date data = null;

        try{
            if (des != null || dataParam != null || par != null) {
                des = (des != null && !des.trim().isEmpty()) ? StringEscapeUtils.escapeHtml4(des.trim()) : null;
                if (dataParam != null && !dataParam.trim().isEmpty()) {
                    data = Date.valueOf(LocalDate.parse(dataParam));
                }
                request.setAttribute("crociere", crocieraDAO.doRetrieveByParams(des, null, data));
            } else if("prezzi_bassi".equalsIgnoreCase(request.getParameter("ricercaAction"))){
                request.setAttribute("crociere", crocieraDAO.doRetrieveByPrezziBassi());
            } else {
                request.setAttribute("crociere", crocieraDAO.doRetrieveAllAttivi());
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/risultati.jsp");
        dispatcher.forward(request, response);
    }
}
