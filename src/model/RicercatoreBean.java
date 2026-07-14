package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RicercatoreBean implements Serializable{
    public RicercatoreBean(){
    }

    public List<CrocieraBean> getCruises(String destinazione, String partenza, String compagnia, LocalDate d, int adults, int c){
        List<CrocieraBean> risultati = new ArrayList<CrocieraBean>();

        risultati.add(new CrocieraBean(destinazione, partenza, compagnia, d, adults, c));
        risultati.add(new CrocieraBean(destinazione, partenza, compagnia, d, adults, c));

        return risultati;
    }
}