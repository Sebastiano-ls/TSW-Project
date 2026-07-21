package model;

import java.io.Serializable;
import java.sql.Date;

public class OrdineBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idOrdine;
    private Date dataPagamento;
    private double totOrdine;
    private int idUtente;

    private String nomeUtente;
    private String cognomeUtente;
    private String emailUtente;

    public OrdineBean() {
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getTotOrdine() {
        return totOrdine;
    }

    public void setTotOrdine(double totOrdine) {
        this.totOrdine = totOrdine;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getCognomeUtente() {
        return cognomeUtente;
    }

    public void setCognomeUtente(String cognomeUtente) {
        this.cognomeUtente = cognomeUtente;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }
}
