package dao;

import java.sql.SQLException;
import java.util.List;
import model.DettaglioOrdineBean;

public interface DettaglioOrdineDAO {
    public void doSave(DettaglioOrdineBean dettaglio) throws SQLException;
    public DettaglioOrdineBean doRetrieveByKey(int idDettaglio) throws SQLException;
    public List<DettaglioOrdineBean> doRetrieveByIdOrdine(int idOrdine) throws SQLException;
}
