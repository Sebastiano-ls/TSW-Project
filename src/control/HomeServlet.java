import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.CrocieraDAO;
import dao.CrocieraDAOImpl;
import model.CrocieraBean;

@WebServlet("/common/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CrocieraDAO crocieraDAO;

    public void init() throws ServletException{
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        crocieraDAO = new CrocieraDAOImpl(ds);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            List<CrocieraBean> lastMinute = crocieraDAO.doRetrieveAllAttivi();
            request.setAttribute("crociereLastMinute", lastMinute);
        }catch(SQLException e){
            throw new ServletException(e);
        }

        request.getRequestDispatcher("/WEB-INF/views/common/home.jsp").forward(request, response);
    }
}
