package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

import dao.CrocieraDAO;
import dao.CrocieraDAOImpl;
import model.ItemCarrello;
import model.CrocieraBean;

import java.util.ArrayList;
import java.util.List;

@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private CrocieraDAO crocieraDAO;

    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }

        crocieraDAO = new CrocieraDAOImpl(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/carrello.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        List<ItemCarrello> carrello = (List<ItemCarrello>) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new ArrayList<>();
            session.setAttribute("carrello", carrello);
        }

        try {
            if ("addC".equals(action)) {
                int idCrociera;
                try {
                    idCrociera = Integer.parseInt(request.getParameter("idCrociera"));
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "id crociera non valido");
                    return;
                }
                
                int adulti = parseIntParam(request.getParameter("adulti"), 1);
                int bambini = parseIntParam(request.getParameter("bambini"), 0);

                boolean trovato = false;
                for (ItemCarrello item : carrello) {
                    if (item.getCrociera().getId() == idCrociera) {
                        item.setNumBiglAdu(item.getNumBiglAdu() + adulti);
                        item.setNumBiglChilds(item.getNumBiglChilds() + bambini);
                        trovato = true;
                        break;
                    }
                }

                if (!trovato) {
                    CrocieraBean crociera = crocieraDAO.doRetrieveByKey(idCrociera);
                    if (crociera == null || !crociera.isAttivo()) {
                        response.sendRedirect(request.getContextPath() + "/catalogo");
                        return;
                    }

                    ItemCarrello item = new ItemCarrello();
                    item.setCrociera(crociera);
                    item.setNumBiglAdu(adulti);
                    item.setNumBiglChilds(bambini);
                    carrello.add(item);
                }

                String xhr = request.getHeader("X-Requested-With");
                if ("XMLHttpRequest".equals(xhr)) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"ok\":true,\"totale\":" + carrello.size() + "}");
                    return;
                }

                response.sendRedirect(request.getContextPath() + "/carrello");
            } else if ("deleteC".equals(action)) {
                int index;
                try {
                    index = Integer.parseInt(request.getParameter("index"));
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "indice non valido");
                    return;
                }
                if (index >= 0 && index < carrello.size()) {
                    carrello.remove(index);
                }
                response.sendRedirect(request.getContextPath() + "/carrello");

            } else if ("updateQuantita".equals(action)) {
                int index;

                try {
                    index = Integer.parseInt(request.getParameter("index"));
                } catch (NumberFormatException e) {
                    if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"ok\":false,\"errore\":\"indice non valido\"}");

                        return;
                    }

                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "indice non valido");
                    return;
                }

                double subtotaleItem = 0.0;
                boolean rimosso = false;

                if (index >= 0 && index < carrello.size()) {
                    ItemCarrello item = carrello.get(index);

                    int adulti = parseIntParam(request.getParameter("adulti"), 0);
                    int bambini = parseIntParam(request.getParameter("bambini"), 0);

                    if (adulti < 0) 
                        adulti = 0;

                    if (bambini < 0) 
                        bambini = 0;
                    
                    item.setNumBiglAdu(adulti);
                    item.setNumBiglChilds(bambini);

                    if (item.getNumBiglAdu() == 0 && item.getNumBiglChilds() == 0) {
                        carrello.remove(index);
                        rimosso = true;
                    }else{
                        subtotaleItem = item.getTotale();
                    }
                }

                String xhr = request.getHeader("X-Requested-With");
                if ("XMLHttpRequest".equals(xhr)) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    
                    JSONObject json = new JSONObject();
                    json.put("ok", true);
                    json.put("totale", calcolaTotaleCarrello(carrello));
                    json.put("subtotale", String.format("%.2f", subtotaleItem));
                    json.put("rimosso", rimosso);

                    response.getWriter().print(json.toString());
                    return;
                }
                response.sendRedirect(request.getContextPath() + "/carrello");

            } else if ("svuota".equals(action)) {
                session.removeAttribute("carrello");
                session.setAttribute("carrello", new ArrayList<ItemCarrello>());
                response.sendRedirect(request.getContextPath() + "/carrello");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private int parseIntParam(String value, int defaultVal) {
        if (value == null || value.trim().isEmpty()) 
            return defaultVal;

        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    private String calcolaTotaleCarrello(List<ItemCarrello> carrello) {
        double totale = 0;

        for (ItemCarrello item : carrello) {
            totale += item.getTotale();
        }

        return String.format("%.2f", totale);
    }
}
