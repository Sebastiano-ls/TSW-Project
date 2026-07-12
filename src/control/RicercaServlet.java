import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.List;

import model.RicercatoreBean;
import model.Crociera;

@WebServlet("/RicercaServlet")
public class RicercaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RicercaServlet(){
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //PRENDO I PARAMETRI DEL FORM DALLA RICHIESTA
		String des = (request.getParameter("des") != "") ? request.getParameter("des") : null;
        String par = (request.getParameter("par") != "") ? request.getParameter("par") : null;
        String comp = (request.getParameter("comp") != "") ? request.getParameter("comp") : null;
        LocalDate data = (request.getParameter("data") != "") ? LocalDate.parse(request.getParameter("data")) : null;
        int adu = (request.getParameter("adults") != "") ? Integer.parseInt(request.getParameter("adults")) : null;
        int childs = (request.getParameter("childs") != "") ? Integer.parseInt(request.getParameter("childs")) : null;

        //CHIAMO IL JAVABEAN
        RicercatoreBean ricercatore = new RicercatoreBean();
        List<Crociera> risultati = ricercatore.getCruises(des, par, comp, data, adu, childs);

        //IMPOSTO L'HEADING CONTENTTYPE DI TIPO HTML
        response.setContentType ("text/html");

        //CREO LA PAGINA DI RISPOSTA
		PrintWriter writer = response.getWriter();
		writer.println("<!DOCTYPE html>");
		writer.println("<html><body>");
		writer.println("<h1>RISULTATI IN MERITO ALLA TUA SELEZIONE:</h1>");
		writer.println(" Destinazione: " + des);
        writer.println(" Porto di partenza: " + par);
        writer.println(" Compagnia: " + comp);
        writer.println(" Data di partenza: " + data);
        writer.println(" Numero di adulti: " + adu);
        writer.println(" Numero di bambini: " + childs);

        for(Crociera c : risultati){
            writer.println("<br>" + c);
        }

		writer.println("</html></body>");
	}
}
