package dao;

import java.sql.SQLException;
import java.util.List;

import model.TappaBean;

public interface TappaDAO {
    public void doSave(TappaBean tappa) throws SQLException;
    public TappaBean doRetrieveByKey(int id) throws SQLException;
    public List<TappaBean> doRetrieveByCrociera(int idCrociera) throws SQLException;
    public List<TappaBean> doRetrieveByCrocieraAll(int idCrociera) throws SQLException;
    public void doUpdate(TappaBean tappa) throws SQLException;
    public void doDelete(int id) throws SQLException;
    public List<TappaBean> doRetrieveAllAttivi() throws SQLException;
    public List<TappaBean> doRetrieveAll() throws SQLException;
}
