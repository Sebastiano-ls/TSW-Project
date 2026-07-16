package model;

import java.io.Serializable;
import java.sql.Date;

public class CrocieraBean implements Serializable{
    private int id;
    private String nome;
    private String des;
    private Date dataIn;
    private Date dataFin;
    private int prezzo;
    private int sconto;
    private byte[] immagineCrociera;
    private String immagineTipo;
    private boolean attivo;

    public CrocieraBean(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Date getDataInizio() {
        return dataIn;
    }

    public void setDataInizio(Date dataIn) {
        this.dataIn = dataIn;
    }

    public Date getDataFine() {
        return dataFin;
    }

    public void setDataFine(Date dataFin) {
        this.dataFin = dataFin;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public int getSconto() {
        return sconto;
    }

    public void setSconto(int sconto) {
        this.sconto = sconto;
    }

    public byte[] getImmagineCrociera() {
        return immagineCrociera;
    }

    public void setImmagineCrociera(byte[] immagineCrociera) {
        this.immagineCrociera = immagineCrociera;
    }

    public String getMimeType() {
        return immagineTipo;
    }

    public void setMimeType(String immagineTipo) {
        this.immagineTipo = immagineTipo;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }
}
