package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.io.IOException;

import java.util.List;
import javax.sql.DataSource;
import java.sql.Date;

import dao.CrocieraDAO;
import dao.CrocieraDAOImpl;
import model.CrocieraBean;

@MultipartConfig
@WebServlet("/admin/crociere")
public class AdminCrocieraServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private CrocieraDAO crocieraDAO;

    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }

        crocieraDAO = new CrocieraDAOImpl(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("actionAdmin");

        try {
            if ("create".equalsIgnoreCase(action)) {
                request.getRequestDispatcher("/WEB-INF/views/admin/crocieraForm.jsp").forward(request, response);
                return;
            } else if ("edit".equals(action)) {
                String idParam = request.getParameter("id");

                if (idParam != null) {
                    try {
                        CrocieraBean crociera = crocieraDAO.doRetrieveByKey(Integer.parseInt(idParam));
                        request.setAttribute("crociera", crociera);
                        request.getRequestDispatcher("/WEB-INF/views/admin/crocieraForm.jsp").forward(request, response);
                        return;
                    } catch (NumberFormatException e) {
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    }
                }
            }

            List<CrocieraBean> lista = crocieraDAO.doRetrieveAll();
            request.setAttribute("crociere", lista);
            request.getRequestDispatcher("/WEB-INF/views/admin/crociere.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("actionAdmin");

        try {
            if ("create".equals(action)) {
                CrocieraBean crociera = extract(request, response);
                crocieraDAO.doSave(crociera);
            } else if ("edit".equals(action)) {
                CrocieraBean crociera = extract(request, response);
                crociera.setId(Integer.parseInt(request.getParameter("id")));
                crocieraDAO.doUpdate(crociera);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                crocieraDAO.doDelete(id);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/admin/crociere");
    }

    private CrocieraBean extract(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CrocieraBean bean = new CrocieraBean();

        bean.setNome(request.getParameter("nomeCrociera"));
        bean.setDes(request.getParameter("descrizione"));
        
        String dataInizio = request.getParameter("dataInizio");
        if (dataInizio != null && !dataInizio.isEmpty()) {
            bean.setDataInizio(Date.valueOf(dataInizio));
        }

        String dataFine = request.getParameter("dataFine");
        if (dataFine != null && !dataFine.isEmpty()) {
            bean.setDataFine(Date.valueOf(dataFine));
        }

        String prezzoStr = request.getParameter("prezzo");
        if (prezzoStr != null && !prezzoStr.isEmpty()) {
            try { 
                double p = Double.parseDouble(prezzoStr);
                if (p < 0) 
                    p = 0;
                bean.setPrezzo(p);
            } catch (NumberFormatException e) {
                throw new ServletException("Il prezzo inserito non è un numero valido");
            }
        }

        String scontoStr = request.getParameter("sconto");
        if (scontoStr != null && !scontoStr.isEmpty()) {
            try { 
                double s = Double.parseDouble(scontoStr);

                if (s < 0) 
                    s = 0;

                if (s > 100) 
                    s = 100;

                bean.setSconto(s);
            } catch (NumberFormatException e) { 
                throw new ServletException("Lo sconto inserito non è valido");
            }
        }

        bean.setAttivo("on".equals(request.getParameter("attivo")));

        try {
            Part filePart = request.getPart("immagineCrociera");

            if (filePart != null && filePart.getSize() > 0) {
                bean.setImmagineCrociera(readBytes(filePart.getInputStream()));

                String contentType = filePart.getContentType();
                if (contentType == null || contentType.isEmpty()) {
                    contentType = "image/jpeg";
                }

                bean.setMimeType(contentType);
            }
        } catch (ServletException e) {
            throw new ServletException("Problemi con il caricamento dell'immagine");
        }

        return bean;
    }

    private byte[] readBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[8192];
        int n;

        while ((n = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, n);
        }
        
        return buffer.toByteArray();
    }
}
