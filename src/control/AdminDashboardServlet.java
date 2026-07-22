package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;

import dao.CrocieraDAO;
import dao.CrocieraDAOImpl;
import dao.OrdineDAO;
import dao.OrdineDAOImpl;
import dao.TappaDAO;
import dao.TappaDAOImpl;
import dao.UtenteDAO;
import dao.UtenteDAOImpl;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    private CrocieraDAO crocieraDAO;
    private OrdineDAO ordineDAO;
    private UtenteDAO utenteDAO;
    private TappaDAO tappaDAO;

    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }

        crocieraDAO = new CrocieraDAOImpl(ds);
        ordineDAO = new OrdineDAOImpl(ds);
        utenteDAO = new UtenteDAOImpl(ds);
        tappaDAO = new TappaDAOImpl(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("totCrociere", crocieraDAO.doRetrieveAll().size());
            request.setAttribute("totOrdini", ordineDAO.doRetrieveAll().size());
            request.setAttribute("totUtenti", utenteDAO.doRetrieveAll().size());
            request.setAttribute("totTappe", tappaDAO.doRetrieveAll().size());
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}