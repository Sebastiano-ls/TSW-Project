package model;

import java.io.Serializable;

public class TappaBean implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String localita;
    private String porto;
    private boolean attivo;

    public TappaBean(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getPorto() {
        return porto;
    }

    public void setPorto(String porto) {
        this.porto = porto;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }
}
