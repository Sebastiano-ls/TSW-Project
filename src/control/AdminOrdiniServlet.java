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
import model.OrdineBean;

@WebServlet("/admin/ordini")
public class AdminOrdiniServlet extends HttpServlet {
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
        String daData = request.getParameter("daData");
        String aData = request.getParameter("aData");
        String email = request.getParameter("email");

        try {
            List<OrdineBean> lista;
            if ((daData != null && !daData.isEmpty())
                    || (aData != null && !aData.isEmpty())
                    || (email != null && !email.isEmpty())) {
                lista = ordineDAO.doRetrieveByCond(daData, aData, email);
            } else {
                lista = ordineDAO.doRetrieveAll();
            }
            request.setAttribute("ordini", lista);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/ordini.jsp")
                .forward(request, response);
    }
}
