package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.DettaglioOrdineDAO;
import dao.DettaglioOrdineDAOImpl;
import dao.OrdineDAO;
import dao.OrdineDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DettaglioOrdineBean;
import model.OrdineBean;
import model.UtenteBean;

@WebServlet("/common/ordini/dettaglio")
public class DettaglioOrdineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO;
    private DettaglioOrdineDAO dettaglioOrdineDAO;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        ordineDAO = new OrdineDAOImpl(ds);
        dettaglioOrdineDAO = new DettaglioOrdineDAOImpl(ds);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/common/ordini");
            return;
        }

        try {
            int idOrdine;
            try {
                idOrdine = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/common/ordini");
                return;
            }
            OrdineBean ordine = ordineDAO.doRetrieveByKey(idOrdine);
            if (ordine == null) {
                response.sendRedirect(request.getContextPath() + "/common/ordini");
                return;
            }

            boolean isAdmin = "admin".equals(utente.getRuolo());
            if (ordine.getIdUtente() != utente.getIdUtente() && !isAdmin) {
                response.sendRedirect(request.getContextPath() + "/common/ordini");
                return;
            }

            List<DettaglioOrdineBean> dettagli = dettaglioOrdineDAO.doRetrieveByIdOrdine(idOrdine);
            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);
            request.getRequestDispatcher("/WEB-INF/views/common/dettaglioOrdine.jsp")
                    .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
