package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.TappaDAO;
import dao.TappaDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TappaBean;

@WebServlet("/admin/tappe")
public class AdminTappeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TappaDAO tappaDAO;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        tappaDAO = new TappaDAOImpl(ds);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                request.getRequestDispatcher("/WEB-INF/views/admin/tappaForm.jsp")
                        .forward(request, response);
            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("tappa", tappaDAO.doRetrieveByKey(id));
                request.getRequestDispatcher("/WEB-INF/views/admin/tappaForm.jsp")
                        .forward(request, response);
            } else {
                request.setAttribute("tappe", tappaDAO.doRetrieveAll());
                request.getRequestDispatcher("/WEB-INF/views/admin/tappe.jsp")
                        .forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                TappaBean t = new TappaBean();
                t.setLocalita(request.getParameter("localita"));
                t.setPorto(request.getParameter("porto"));
                t.setAttivo("on".equals(request.getParameter("attivo")));
                tappaDAO.doSave(t);
            } else if ("edit".equals(action)) {
                TappaBean t = new TappaBean();
                t.setId(Integer.parseInt(request.getParameter("id")));
                t.setLocalita(request.getParameter("localita"));
                t.setPorto(request.getParameter("porto"));
                t.setAttivo("on".equals(request.getParameter("attivo")));
                tappaDAO.doUpdate(t);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                tappaDAO.doDelete(id);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/admin/tappe");
    }
}
