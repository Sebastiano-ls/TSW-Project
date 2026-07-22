package dao;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import model.CrocieraBean;

public interface CrocieraDAO {
    public void doSave(CrocieraBean crociera) throws SQLException;
    public CrocieraBean doRetrieveByKey(int id) throws SQLException;
    public void doUpdate(CrocieraBean crociera) throws SQLException;
    public void doDelete(int id) throws SQLException;
    public List<CrocieraBean> doRetrieveByParams(String destinazione, String partenza, Date dataIn) throws SQLException;
    public List<CrocieraBean> doRetrieveByPrezziBassi() throws SQLException;
    public List<CrocieraBean> doRetrieveAllAttivi() throws SQLException;
    public List<CrocieraBean> doRetrieveAll() throws SQLException;
    public byte[] doRetrieveImage(int id) throws SQLException;
}
