package model;

import java.io.Serializable;

public class ItemCarrello implements Serializable {
    private static final long serialVersionUID = 1L;

    private CrocieraBean crociera;
    private int numBiglAdu;
    private int numBiglChilds;

    public ItemCarrello(){}

    public CrocieraBean getCrociera() {
        return crociera;
    }

    public void setCrociera(CrocieraBean crociera) {
        this.crociera = crociera;
    }

    public int getNumBiglAdu() {
        return numBiglAdu;
    }

    public void setNumBiglAdu(int numBiglAdu) {
        this.numBiglAdu = numBiglAdu;
    }

    public int getNumBiglChilds() {
        return numBiglChilds;
    }

    public void setNumBiglChilds(int numBiglChilds) {
        this.numBiglChilds = numBiglChilds;
    }

    public double getPrezzoApplicato(){
        if (crociera == null) 
            return 0.0;

        double prezzoScontato = crociera.getPrezzo() - (crociera.getPrezzo()/100) * crociera.getSconto();

        return Math.round(prezzoScontato * 100.0) / 100.0;
    }

    public double getTotale(){
        double prezzoSingolo = getPrezzoApplicato();
        double tot = (prezzoSingolo * numBiglAdu) + (prezzoSingolo * 0.5 * numBiglChilds);

        return Math.round(tot * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;

        if (o == null || getClass() != o.getClass()) 
            return false;

        ItemCarrello cart = (ItemCarrello) o;
        
        if (crociera == null || cart.crociera == null) 
            return false;

        return getCrociera().getId() == cart.getCrociera().getId();
    }
}