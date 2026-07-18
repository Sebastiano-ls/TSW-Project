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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("datasource non disponibile");
        }
        utenteDAO = new UtenteDAOImpl(ds);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String genere = request.getParameter("genere");
        String dataNascitaStr = request.getParameter("dataNascita");
        String numTelefono = request.getParameter("numTelefono");

        if (nome == null || nome.trim().isEmpty() ||
            cognome == null || cognome.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "tutti i campi obbligatori devono essere compilati");
            doGet(request, response);
            return;
        }

        UtenteBean utente = new UtenteBean();
        utente.setNome(nome.trim());
        utente.setCognome(cognome.trim());
        utente.setEmail(email.trim());
        utente.setPassword(SecurityUtils.toDigest(password));
        utente.setGenere(genere);
        utente.setNumTelefono(numTelefono);

        if (dataNascitaStr != null && !dataNascitaStr.trim().isEmpty()) {
            utente.setDataNascita(Date.valueOf(dataNascitaStr));
        }

        try {
            utenteDAO.doSave(utente);
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (SQLException e) {
            request.setAttribute("error", "Email già registrata.");
            doGet(request, response);
        }
    }
}
