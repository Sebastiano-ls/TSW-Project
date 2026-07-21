package model;

import java.io.Serializable;
import java.sql.Date;

public class AttraversaBean implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int id_crociera;
    private int id_tappa;
    private Date dataSosta;

    public AttraversaBean(){}

    public int getIdCrociera() {
        return id_crociera;
    }
    public void setIdCrociera(int id_crociera) {
        this.id_crociera = id_crociera;
    }
    public int getIdTappa() {
        return id_tappa;
    }
    public void setIdTappa(int id_tappa) {
        this.id_tappa = id_tappa;
    }
    public Date getDataSosta() {
        return dataSosta;
    }
    public void setDataSosta(Date dataSosta) {
        this.dataSosta = dataSosta;
    }
}
