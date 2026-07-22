package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import dao.OrdineDAO;
import dao.OrdineDAOImpl;
import dao.TappaDAO;
import dao.TappaDAOImpl;

import model.ItemCarrello;
import model.CrocieraBean;
import model.DettaglioOrdineBean;
import model.OrdineBean;
import model.TappaBean;
import model.UtenteBean;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/common/checkout")
public class CheckoutServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    private OrdineDAO ordineDAO;
    private TappaDAO tappaDAO;

    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }

        ordineDAO = new OrdineDAOImpl(ds);
        tappaDAO = new TappaDAOImpl(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<ItemCarrello> carrello = (List<ItemCarrello>) session.getAttribute("carrello");
        if (carrello == null || carrello.isEmpty()) {
            request.setAttribute("error", "il carrello è vuoto");
            request.getRequestDispatcher("/WEB-INF/views/carrello.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/common/checkout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<ItemCarrello> carrello = (List<ItemCarrello>) session.getAttribute("carrello");
        if (carrello == null || carrello.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        try {
            double totale = 0;
            for (ItemCarrello item : carrello) {
                totale += item.getTotale();
            }

            OrdineBean ordine = new OrdineBean();
            ordine.setDataPagamento(Date.valueOf(LocalDate.now()));
            ordine.setTotOrdine(Math.round(totale * 100.0) / 100.0);
            ordine.setIdUtente(utente.getIdUtente());

            List<DettaglioOrdineBean> dettagli = new ArrayList<>();
            for (ItemCarrello item : carrello) {
                DettaglioOrdineBean dettaglio = new DettaglioOrdineBean();
                dettaglio.setNumBigliettoAdulto(item.getNumBiglAdu());
                dettaglio.setNumBigliettoBambino(item.getNumBiglChilds());
                dettaglio.setPrezzoArchiviato(Math.round(item.getPrezzoApplicato() * 100.0) / 100.0);
                dettaglio.setIdCrociera(item.getCrociera().getId());

                CrocieraBean crociera = item.getCrociera();
                dettaglio.setNomeCrocieraArchiviato(crociera.getNome());
                dettaglio.setDescrizioneArchiviato(crociera.getDes());
                dettaglio.setDataInizioArchiviato(crociera.getDataInizio());
                dettaglio.setDataFineArchiviato(crociera.getDataFine());

                List<TappaBean> tappe = tappaDAO.doRetrieveByCrociera(crociera.getId());

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tappe.size(); i++) {
                    if (i > 0) 
                        sb.append(" &rarr ");
                    sb.append(tappe.get(i).getLocalita());
                }

                dettaglio.setTappeArchiviato(sb.toString());
                dettagli.add(dettaglio);
            }

            ordineDAO.doSaveConDettagli(ordine, dettagli);

            session.removeAttribute("carrello");
            session.setAttribute("success", "ordine confermato, grazie per aver scelto s&s crociere");
            response.sendRedirect(request.getContextPath() + "/common/ordini/dettaglio?id=" + ordine.getIdOrdine());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
