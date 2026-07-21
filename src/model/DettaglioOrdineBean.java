package model;

import java.io.Serializable;
import java.sql.Date;

public class DettaglioOrdineBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idDettaglio;
    private int numBigliettoAdulto;
    private int numBigliettoBambino;
    private double prezzoArchiviato;
    private int idOrdine;
    private int idCrociera;

    private String nomeCrocieraArchiviato;
    private String descrizioneArchiviato;
    private Date dataInizioArchiviato;
    private Date dataFineArchiviato;
    private String tappeArchiviato;

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

    public String getNomeCrocieraArchiviato() {
        return nomeCrocieraArchiviato;
    }

    public void setNomeCrocieraArchiviato(String nomeCrocieraArchiviato) {
        this.nomeCrocieraArchiviato = nomeCrocieraArchiviato;
    }

    public String getDescrizioneArchiviato() {
        return descrizioneArchiviato;
    }

    public void setDescrizioneArchiviato(String descrizioneArchiviato) {
        this.descrizioneArchiviato = descrizioneArchiviato;
    }

    public Date getDataInizioArchiviato() {
        return dataInizioArchiviato;
    }

    public void setDataInizioArchiviato(Date dataInizioArchiviato) {
        this.dataInizioArchiviato = dataInizioArchiviato;
    }

    public Date getDataFineArchiviato() {
        return dataFineArchiviato;
    }

    public void setDataFineArchiviato(Date dataFineArchiviato) {
        this.dataFineArchiviato = dataFineArchiviato;
    }

    public String getTappeArchiviato() {
        return tappeArchiviato;
    }

    public void setTappeArchiviato(String tappeArchiviato) {
        this.tappeArchiviato = tappeArchiviato;
    }
}
