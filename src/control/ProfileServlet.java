package control;

import java.io.IOException;
import java.sql.Date;
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

@WebServlet("/common/profile")
public class ProfileServlet extends HttpServlet {
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
        request.getRequestDispatcher("/WEB-INF/views/common/profile.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        try {
            if ("updateInfo".equals(action)) {
                updateInfo(request, utente);
                request.getSession().setAttribute("success", "dati aggiornati con successo");
            } else if ("updatePassword".equals(action)) {
                if (!updatePassword(request, utente)) {
                    request.getSession().setAttribute("error", "password attuale errata");
                } else {
                    request.getSession().setAttribute("success", "password aggiornata con successo");
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        response.sendRedirect(request.getContextPath() + "/common/profile");
    }

    private void updateInfo(HttpServletRequest request, UtenteBean utente) throws SQLException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String genere = request.getParameter("genere");
        String dataNascitaStr = request.getParameter("dataNascita");
        String numTelefono = request.getParameter("numTelefono");

        if (nome != null && !nome.trim().isEmpty()) utente.setNome(nome.trim());
        if (cognome != null && !cognome.trim().isEmpty()) utente.setCognome(cognome.trim());
        if (email != null && !email.trim().isEmpty()) utente.setEmail(email.trim());
        if (genere != null) utente.setGenere(genere);
        if (dataNascitaStr != null && !dataNascitaStr.trim().isEmpty())
            utente.setDataNascita(Date.valueOf(dataNascitaStr));
        if (numTelefono != null) utente.setNumTelefono(numTelefono);

        utenteDAO.doUpdate(utente);
        request.getSession().setAttribute("utente", utente);
    }

    private boolean updatePassword(HttpServletRequest request, UtenteBean utente) throws SQLException {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        if (oldPassword == null || newPassword == null ||
            oldPassword.trim().isEmpty() || newPassword.trim().isEmpty()) {
            return false;
        }

        String oldHash = SecurityUtils.toDigest(oldPassword);
        if (!oldHash.equals(utente.getPassword())) {
            return false;
        }

        String newHash = SecurityUtils.toDigest(newPassword);
        utenteDAO.doUpdatePassword(utente.getIdUtente(), newHash);
        utente.setPassword(newHash);
        request.getSession().setAttribute("utente", utente);
        return true;
    }
}
