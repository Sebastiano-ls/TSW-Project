package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;

import dao.DettaglioOrdineDAO;
import dao.DettaglioOrdineDAOImpl;
import model.DettaglioOrdineBean;
import model.UtenteBean;

@WebServlet("/common/ordini/archivio-crociera")
public class ArchivioCrocieraServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private DettaglioOrdineDAO dettaglioOrdineDAO;

    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        dettaglioOrdineDAO = new DettaglioOrdineDAOImpl(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idParam = request.getParameter("idDettaglio");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/common/ordini");
            return;
        }

        try {
            int idDettaglio;
            try {
                idDettaglio = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/common/ordini");
                return;
            }

            DettaglioOrdineBean dettaglio = dettaglioOrdineDAO.doRetrieveByKey(idDettaglio);
            if (dettaglio == null) {
                response.sendRedirect(request.getContextPath() + "/common/ordini");
                return;
            }

            request.setAttribute("dettaglio", dettaglio);
            request.getRequestDispatcher("/WEB-INF/views/common/dettaglioCrocieraArchiviata.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}
