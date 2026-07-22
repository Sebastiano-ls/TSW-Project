package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;

import java.util.List;

import dao.CrocieraDAO;
import dao.CrocieraDAOImpl;
import dao.TappaDAO;
import dao.TappaDAOImpl;

import model.CrocieraBean;
import model.TappaBean;

@WebServlet("/catalogo/dettaglio")
public class DettaglioCrocieraServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private CrocieraDAO crocieraDAO;
    private TappaDAO tappaDAO;


    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }

        crocieraDAO = new CrocieraDAOImpl(ds);
        tappaDAO = new TappaDAOImpl(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/catalogo");
            return;
        }

        try {
            int id;
            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/catalogo");
                return;
            }

            CrocieraBean crociera = crocieraDAO.doRetrieveByKey(id);
            if (crociera == null || !crociera.isAttivo()) {
                response.sendRedirect(request.getContextPath() + "/catalogo");
                return;
            }

            List<TappaBean> tappe = tappaDAO.doRetrieveByCrociera(id);
            request.setAttribute("crociera", crociera);
            request.setAttribute("tappe", tappe);
            request.getRequestDispatcher("/WEB-INF/views/dettaglioCrociera.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}