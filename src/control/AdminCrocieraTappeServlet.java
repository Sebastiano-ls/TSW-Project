package control;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;

import dao.AttraversaDAO;
import dao.AttraversaDAOImpl;
import dao.CrocieraDAO;
import dao.CrocieraDAOImpl;
import dao.TappaDAO;
import dao.TappaDAOImpl;
import model.AttraversaBean;

@WebServlet("/admin/crociera-tappe")
public class AdminCrocieraTappeServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    private AttraversaDAO attraversaDAO;
    private TappaDAO tappaDAO;
    private CrocieraDAO crocieraDAO;

    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }

        attraversaDAO = new AttraversaDAOImpl(ds);
        tappaDAO = new TappaDAOImpl(ds);
        crocieraDAO = new CrocieraDAOImpl(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("actionAdmin");
        int idCrociera = Integer.parseInt(request.getParameter("idCrociera"));

        try {
            request.setAttribute("crociera", crocieraDAO.doRetrieveByKey(idCrociera));

            if ("manage".equals(action)) {
                request.setAttribute("tappeAssociate", attraversaDAO.doRetrieveByCrociera(idCrociera));
                request.setAttribute("tappeDisponibili", tappaDAO.doRetrieveAllAttivi());
                request.getRequestDispatcher("/WEB-INF/views/admin/crocieraTappe.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("actionAdmin");
        int idCrociera = Integer.parseInt(request.getParameter("idCrociera"));

        try {
            if ("add".equals(action)) {
                int idTappa = Integer.parseInt(request.getParameter("idTappa"));
                AttraversaBean bean = new AttraversaBean();
                bean.setIdCrociera(idCrociera);
                bean.setIdTappa(idTappa);
                String dataSosta = request.getParameter("dataSosta");

                if (dataSosta != null && !dataSosta.isEmpty()) {
                    bean.setDataSosta(java.sql.Date.valueOf(dataSosta));
                }
                
                attraversaDAO.doSave(bean);
            } else if ("remove".equals(action)) {
                int idTappa = Integer.parseInt(request.getParameter("idTappa"));
                attraversaDAO.doDelete(idCrociera, idTappa);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/admin/crociera-tappe?actionAdmin=manage&idCrociera=" + idCrociera);
    }
}