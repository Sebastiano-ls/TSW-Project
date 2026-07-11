import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

public class RicercaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //PRENDO I PARAMETRI DEL FORM DALLA RICHIESTA
		String des = request.getParameter("des");
        String par = request.getParameter("par");
        String comp = request.getParameter("comp");
        LocalDate data = LocalDate.parse(request.getParameter("data"));
        int adu = Integer.parseInt(request.getParameter("adults"));
        int childs = Integer.parseInt(request.getParameter("childs"));

        //IMPOSTO L'HEADING CONTENTTYPE DI TIPO HTML
        response.setContentType ("text/html");

        //SCRIVO LA PAGINA RISULTANTE TEMPORANEA
		PrintWriter writer = response.getWriter();
		writer.println("<!DOCTYPE html>");
		writer.println("<html><body>");
		writer.println("<h1>LA TUA SELEZIONE</h1>");
		writer.println("<br>Destinazione: " + des);
        writer.println("<br>Porto di partenza: " + par);
        writer.println("<br>Compagnia: " + comp);
        writer.println("<br>Data di partenza: " + data);
        writer.println("<br>Numero di adulti: " + adu);
        writer.println("<br>Numero di bambini: " + childs);
		writer.println("</html></body>");
	}
}
