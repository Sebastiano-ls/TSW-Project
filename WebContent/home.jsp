<!DOCTYPE html>
    <html>
        <head>
            <meta charset="UTF-8">
            <title>S&S Cruises</title>
        </head>
        <body>
            <!-- Intestazione della pagina -->
            <jsp include url="${pageContext.request.contextPath}/header">

            <!-- Foto che andrà al di sotto della prenotazione (al momento è mancante)-->

            <!-- Field della prenotazione -->
             <form id="ricerca" action="/TSW-Project/RicercaServlet" method="post">
                <fieldset>
                    <legend>Prenota la tua crociera</legend>
                    <label for="des">Dove?</label>
                    <input type="text" name="des" id="des" placeholder="destinazione" value="">

                    <label for="par">Da quale porto?</label>
                    <input type="text" name="par" id="par" placeholder="porto di partenza" value="">

                    <label for="comp">Compagnia?</label>
                    <input type="text" name="comp" id="comp" placeholder="compagnia" value="">

                    <label for="data">Quando?</label>
                    <input type="date" name="data" id="data" value="">

                    <label for="adults">Quanti adulti?</label>
                    <input type="number" name="adults" id="adults" value="1" min="1" max="4">

                    <label for="childs">Quanti minorenni?</label>
                    <input type="number" name="childs" id="childs" value="0" min="0" max="3">

                    <input type="submit" value="CERCA">
                </fieldset>
             </form>

             <!-- Chiamate a ricerche predefinite -->
              <a href="${pageContext.request.contextPath}/ricerca?ricerca=prezzi_bassi" id="prezzi_bassi"> <img src="" alt="PREZZI PIU BASSI"> </a>
              <a href="${pageContext.request.contextPath}/supporto" id="consulenza"> <img src="" alt="PARLA CON NOI"> </a>
              <a href="${pageContext.request.contextPath}/ricerca?ricerca=best_val" id="le_migliori"> <img src="" alt="MIGLIORI RECENSIONI"> </a>

            <!-- Footer della pagina -->
            <jsp include url="${pageContext.request.contextPath}/footer">
        </body>
    </html>