package dao;

import java.sql.SQLException;
import java.util.List;

import model.AttraversaBean;
import model.CrocieraBean;
import model.TappaBean;

public interface AttraversaDAO {
    public void doSave(AttraversaBean bean) throws SQLException;
    public void doUpdate(AttraversaBean bean) throws SQLException;
    public void doDelete(int idCrociera, int idTappa) throws SQLException;
    public List<AttraversaBean> doRetrieveAll() throws SQLException;
    public List<TappaBean> doRetrieveByCrociera(int idCrociera) throws SQLException;
    public List<TappaBean> doRetrieveByCrocieraAll(int idCrociera) throws SQLException;
    public List<CrocieraBean> doRetrieveByTappa(int idTappa) throws SQLException;
    public List<CrocieraBean> doRetrieveByTappaAll(int idTappa) throws SQLException;
}
