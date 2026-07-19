package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.OrdineDAO;
import dao.OrdineDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrdineBean;
import model.UtenteBean;

@WebServlet("/common/ordini")
public class OrdiniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        ordineDAO = new OrdineDAOImpl(ds);
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

        try {
            List<OrdineBean> ordini = ordineDAO.doRetrieveByIdUtente(utente.getIdUtente());
            System.out.println("[DEBUG] ordini trovati per utente " + utente.getIdUtente() + ": " + ordini.size());
            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("/WEB-INF/views/common/ordini.jsp")
                    .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
