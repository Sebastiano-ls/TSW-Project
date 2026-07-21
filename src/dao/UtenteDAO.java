package dao;

import java.sql.SQLException;
import java.util.List;
import model.UtenteBean;

public interface UtenteDAO {
    public void doSave(UtenteBean utente) throws SQLException;
    public UtenteBean doRetrieveByEmailAndPassword(String email, String password) throws SQLException;
    public UtenteBean doRetrieveByKey(int idUtente) throws SQLException;
    public void doUpdate(UtenteBean utente) throws SQLException;
    public void doDelete(int idUtente) throws SQLException;
    public void doUpdatePassword(int idUtente, String newPassword) throws SQLException;
    public List<UtenteBean> doRetrieveAll() throws SQLException;
}
