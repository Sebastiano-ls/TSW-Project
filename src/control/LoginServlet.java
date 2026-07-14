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
import utils.SecurityUtils;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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
        request.getRequestDispatcher("/WEB-INF/views/common/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String passwordParam = request.getParameter("password");
        if (email == null || email.trim().isEmpty() || passwordParam == null || passwordParam.isEmpty()) {
            request.setAttribute("error", "email e password sono obbligatorie");
            doGet(request, response);
            return;
        }
        String password = SecurityUtils.toDigest(passwordParam);

        try {
            UtenteBean utente = utenteDAO.doRetrieveByEmailAndPassword(email, password);
            if (utente != null) {
                request.getSession().setAttribute("utente", utente);
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                request.setAttribute("error", "Email o password errate");
                doGet(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
