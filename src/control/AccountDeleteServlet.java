package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.UtenteDAO;
import dao.UtenteDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteBean;

@WebServlet("/account/delete")
public class AccountDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        utenteDAO = new UtenteDAOImpl(ds);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/common/confirmDelete.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteBean sessionUser = (UtenteBean) request.getSession().getAttribute("utente");
        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            String idParam = request.getParameter("id");
            int targetId;
            try {
                targetId = (idParam != null) ? Integer.parseInt(idParam) : sessionUser.getIdUtente();
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if ("admin".equals(sessionUser.getRuolo()) || targetId == sessionUser.getIdUtente()) {
                utenteDAO.doDelete(targetId);
                if (targetId == sessionUser.getIdUtente()) {
                    request.getSession().invalidate();
                    response.sendRedirect(request.getContextPath() + "/home");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/utenti");
                }
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
