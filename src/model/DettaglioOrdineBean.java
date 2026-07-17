package model;

import java.io.Serializable;

public class DettaglioOrdineBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idDettaglio;
    private int numBigliettoAdulto;
    private int numBigliettoBambino;
    private double prezzoArchiviato;
    private int idOrdine;
    private int idCrociera;

    private String nomeCrociera;

    public DettaglioOrdineBean() {
    }

    public int getIdDettaglio() {
        return idDettaglio;
    }

    public void setIdDettaglio(int idDettaglio) {
        this.idDettaglio = idDettaglio;
    }

    public int getNumBigliettoAdulto() {
        return numBigliettoAdulto;
    }

    public void setNumBigliettoAdulto(int numBigliettoAdulto) {
        this.numBigliettoAdulto = numBigliettoAdulto;
    }

    public int getNumBigliettoBambino() {
        return numBigliettoBambino;
    }

    public void setNumBigliettoBambino(int numBigliettoBambino) {
        this.numBigliettoBambino = numBigliettoBambino;
    }

    public double getPrezzoArchiviato() {
        return prezzoArchiviato;
    }

    public void setPrezzoArchiviato(double prezzoArchiviato) {
        this.prezzoArchiviato = prezzoArchiviato;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public int getIdCrociera() {
        return idCrociera;
    }

    public void setIdCrociera(int idCrociera) {
        this.idCrociera = idCrociera;
    }

    public String getNomeCrociera() {
        return nomeCrociera;
    }

    public void setNomeCrociera(String nomeCrociera) {
        this.nomeCrociera = nomeCrociera;
    }
}
