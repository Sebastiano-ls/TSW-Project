package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.UtenteBean;

public class UtenteDAOImpl implements UtenteDAO {

    private static final String TABLE_NAME = "utente";
    private DataSource ds;

    public UtenteDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public synchronized void doSave(UtenteBean utente) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (nome, cognome, email, password, genere, data_nascita, num_telefono, ruolo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            // binding parametri per prevenire SQL injection
            preparedStatement.setString(1, utente.getNome());
            preparedStatement.setString(2, utente.getCognome());
            preparedStatement.setString(3, utente.getEmail());
            preparedStatement.setString(4, utente.getPassword());
            preparedStatement.setString(5, utente.getGenere());
            preparedStatement.setDate(6, utente.getDataNascita());
            preparedStatement.setString(7, utente.getNumTelefono());
            preparedStatement.setString(8, utente.getRuolo() != null ? utente.getRuolo() : "user");

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized UtenteBean doRetrieveByEmailAndPassword(String email, String password) throws SQLException {
        UtenteBean bean = null;
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ? AND password = ?";

        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bean = new UtenteBean();
                    bean.setIdUtente(rs.getInt("ID_utente"));
                    bean.setNome(rs.getString("nome"));
                    bean.setCognome(rs.getString("cognome"));
                    bean.setEmail(rs.getString("email"));
                    bean.setPassword(rs.getString("password"));
                    bean.setGenere(rs.getString("genere"));
                    bean.setDataNascita(rs.getDate("data_nascita"));
                    bean.setNumTelefono(rs.getString("num_telefono"));
                    bean.setRuolo(rs.getString("ruolo"));
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized UtenteBean doRetrieveByKey(int idUtente) throws SQLException {
        UtenteBean bean = null;
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID_utente = ?";

        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, idUtente);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bean = new UtenteBean();
                    bean.setIdUtente(rs.getInt("ID_utente"));
                    bean.setNome(rs.getString("nome"));
                    bean.setCognome(rs.getString("cognome"));
                    bean.setEmail(rs.getString("email"));
                    bean.setPassword(rs.getString("password"));
                    bean.setGenere(rs.getString("genere"));
                    bean.setDataNascita(rs.getDate("data_nascita"));
                    bean.setNumTelefono(rs.getString("num_telefono"));
                    bean.setRuolo(rs.getString("ruolo"));
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized void doUpdate(UtenteBean utente) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_NAME
                + " SET nome = ?, cognome = ?, email = ?, genere = ?, data_nascita = ?, num_telefono = ? WHERE ID_utente = ?";

        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, utente.getNome());
            preparedStatement.setString(2, utente.getCognome());
            preparedStatement.setString(3, utente.getEmail());
            preparedStatement.setString(4, utente.getGenere());
            preparedStatement.setDate(5, utente.getDataNascita());
            preparedStatement.setString(6, utente.getNumTelefono());
            preparedStatement.setInt(7, utente.getIdUtente());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized void doDelete(int idUtente) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE ID_utente = ?";

        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, idUtente);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized List<UtenteBean> doRetrieveAll() throws SQLException {
        List<UtenteBean> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID_utente";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UtenteBean bean = new UtenteBean();
                bean.setIdUtente(rs.getInt("ID_utente"));
                bean.setNome(rs.getString("nome"));
                bean.setCognome(rs.getString("cognome"));
                bean.setEmail(rs.getString("email"));
                bean.setPassword(rs.getString("password"));
                bean.setGenere(rs.getString("genere"));
                bean.setDataNascita(rs.getDate("data_nascita"));
                bean.setNumTelefono(rs.getString("num_telefono"));
                bean.setRuolo(rs.getString("ruolo"));
                lista.add(bean);
            }
        }
        return lista;
    }

    @Override
    public synchronized void doUpdatePassword(int idUtente, String newPassword) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_NAME + " SET password = ? WHERE ID_utente = ?";

        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, idUtente);

            preparedStatement.executeUpdate();
        }
    }
}