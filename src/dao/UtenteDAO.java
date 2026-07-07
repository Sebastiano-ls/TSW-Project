package dao;

import java.sql.SQLException;
import model.UtenteBean;

public interface UtenteDAO {
    public void doSave(UtenteBean utente) throws SQLException; //registrazione
    public UtenteBean doRetrieveByEmailAndPassword(String email, String password) throws SQLException; //login
}
