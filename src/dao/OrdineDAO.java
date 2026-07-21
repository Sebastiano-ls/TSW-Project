package dao;

import java.sql.SQLException;
import java.util.List;
import model.OrdineBean;

public interface OrdineDAO {
    public void doSave(OrdineBean ordine) throws SQLException;
    public void doSaveConDettagli(OrdineBean ordine, List<model.DettaglioOrdineBean> dettagli) throws SQLException;
    public OrdineBean doRetrieveByKey(int id) throws SQLException;
    public List<OrdineBean> doRetrieveAll() throws SQLException;
    public List<OrdineBean> doRetrieveByCond(String daData, String aData, String email) throws SQLException;
    public List<OrdineBean> doRetrieveByIdUtente(int idUtente) throws SQLException;
}
