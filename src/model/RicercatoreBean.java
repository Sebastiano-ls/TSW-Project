package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RicercatoreBean{
    public RicercatoreBean(){
    }

    public List<Crociera> getCruises(String destinazione, String partenza, String compagnia, LocalDate d, int adults, int c){
        List<Crociera> risultati = new ArrayList<Crociera>();

        risultati.add(new Crociera(destinazione, partenza, compagnia, d, adults, c));
        risultati.add(new Crociera(destinazione, partenza, compagnia, d, adults, c));

        return risultati;
    }
}