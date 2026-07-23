package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javax.sql.DataSource;

import dao.CrocieraDAO;
import dao.CrocieraDAOImpl;

@WebServlet("/images/crociera")
public class ImageServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private CrocieraDAO crocieraDAO;

    public void init() throws ServletException{
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }

        crocieraDAO = new CrocieraDAOImpl(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            servePlaceholder(response);
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            byte[] image = crocieraDAO.doRetrieveImage(id);
            if (image == null) {
                servePlaceholder(response);
                return;
            }

            response.setContentType("image/jpeg");
            response.setContentLength(image.length);
            response.getOutputStream().write(image);
        } catch (NumberFormatException e) {
            servePlaceholder(response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }

    private void servePlaceholder(HttpServletResponse response) throws IOException {
        String relativePath = "/images/placeholder.jpg";
        InputStream is = getServletContext().getResourceAsStream(relativePath);

        if (is == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("image/jpeg");
        is.transferTo(response.getOutputStream());
        is.close();
    }
}
