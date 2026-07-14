package model;

import java.time.LocalDate;

public class Crociera {
    private String des;
    private String par;
    private String comp;
    private LocalDate data;
    private int adu;
    private int childs;

    public Crociera(String destinazione, String partenza, String compagnia, LocalDate d, int adults, int c){
        des = destinazione;
        par = partenza;
        comp = compagnia;
        data = d;
        adu = adults;
        childs = c;
    }

    public String getDes() {
        return des;
    }

    public String getPar() {
        return par;
    }

    public String getComp() {
        return comp;
    }

    public LocalDate getData() {
        return data;
    }

    public int getAdu() {
        return adu;
    }

    public int getChilds() {
        return childs;
    }

    public void setDes(String des) {
        this.des = des;
    }
    
    public void setPar(String par) {
        this.par = par;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setAdu(int adu) {
        this.adu = adu;
    }

    public void setChilds(int childs) {
        this.childs = childs;
    }

    @Override
    public String toString() {
        return "Crociera [des=" + des + ", par=" + par + ", comp=" + comp + ", data=" + data + ", adu=" + adu
                + ", childs=" + childs + "]";
    }
}
