import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.List;

import model.RicercatoreBean;
import model.CrocieraBean;

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
		String des = (!request.getParameter("des").trim().isEmpty()) ? request.getParameter("des").trim() : null;
        String par = (!request.getParameter("par").trim().isEmpty()) ? request.getParameter("par").trim() : null;
        String comp = (!request.getParameter("comp").trim().isEmpty()) ? request.getParameter("comp").trim() : null;
        LocalDate data = (!request.getParameter("data").equals(null) && !request.getParameter("data").equals("")) ? LocalDate.parse(request.getParameter("data")) : null;
        int adu = Integer.parseInt(request.getParameter("adults"));
        int childs = Integer.parseInt(request.getParameter("childs"));

        //CHIAMO IL JAVABEAN
        RicercatoreBean ricercatore = new RicercatoreBean();
        List<CrocieraBean> risultati = ricercatore.getCruises(des, par, comp, data, adu, childs);


        //AGGIUNGO I RIUSLTATI NELLA RICHIESTA E LA INOLTRO ALLA JSP
        request.setAttribute("risultati", risultati);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/risultati.jsp");
        dispatcher.forward(request, response);
	}
}
