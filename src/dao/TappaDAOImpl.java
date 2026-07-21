package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.TappaBean;

public class TappaDAOImpl implements TappaDAO {
    private static final String TABLE_NAME = "tappa";
    private DataSource ds;

    public TappaDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    public synchronized void doSave(TappaBean tappa) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (nome_tappa, nome_porto, attivo) VALUES (?, ?, ?)";
        
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tappa.getLocalita());
            ps.setString(2, tappa.getPorto());
            ps.setBoolean(3, tappa.isAttivo());

            ps.executeUpdate();
        }
    }

    public synchronized TappaBean doRetrieveByKey(int id) throws SQLException {
        String sql = "SELECT ID_tappa, nome_tappa, nome_porto, attivo FROM " + TABLE_NAME + " WHERE ID_tappa = ?";
        TappaBean tappa = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tappa = mapTappa(rs);
                }
            }
        }
        return tappa;
    }

    public synchronized List<TappaBean> doRetrieveByCrociera(int idCrociera) throws SQLException {
        List<TappaBean> tappe = new ArrayList<>();
        String sql = "SELECT t.ID_tappa, t.nome_tappa, t.nome_porto, t.attivo, a.data_sosta " +
                     "FROM " + TABLE_NAME + " t " +
                     "JOIN attraversa a ON t.ID_tappa = a.ID_tappa " +
                     "WHERE a.ID_crociera = ? AND t.attivo = TRUE " +
                     "ORDER BY a.data_sosta";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCrociera);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tappe.add(mapTappa(rs));
                }
            }
        }
        return tappe;
    }

    public synchronized List<TappaBean> doRetrieveByCrocieraAll(int idCrociera) throws SQLException {
        List<TappaBean> tappe = new ArrayList<>();
        String sql = "SELECT t.ID_tappa, t.nome_tappa, t.nome_porto, t.attivo, a.data_sosta " +
                     "FROM " + TABLE_NAME + " t " +
                     "JOIN attraversa a ON t.ID_tappa = a.ID_tappa " +
                     "WHERE a.ID_crociera = ? " +
                     "ORDER BY a.data_sosta";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCrociera);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tappe.add(mapTappa(rs));
                }
            }
        }
        return tappe;
    }

    public synchronized void doUpdate(TappaBean tappa) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET nome_tappa = ?, nome_porto = ?, attivo = ? WHERE ID_tappa = ?";

        TappaBean tappaConfermata = doRetrieveByKey(tappa.getId());
        if (tappaConfermata == null) {
            throw new SQLException("Impossibile aggiornare: Tappa con ID " + tappa.getId() + " non trovata.");
        }

        if (tappa.getLocalita() != null) {
            tappaConfermata.setLocalita(tappa.getLocalita());
        }

        if (tappa.getPorto() != null) {
            tappaConfermata.setPorto(tappa.getPorto());
        }

        if (tappa.isAttivo() != tappaConfermata.isAttivo()) {
            tappaConfermata.setAttivo(tappa.isAttivo());
        }

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tappaConfermata.getLocalita());
            ps.setString(2, tappaConfermata.getPorto());
            ps.setBoolean(3, tappaConfermata.isAttivo());
            ps.setInt(4, tappaConfermata.getId());

            ps.executeUpdate();
        }
    }

    public synchronized void doDelete(int id) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET attivo = FALSE WHERE ID_tappa = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }

    public synchronized List<TappaBean> doRetrieveAllAttivi() throws SQLException {
        List<TappaBean> risultati = new ArrayList<>();
        String sql = "SELECT ID_tappa, nome_tappa, nome_porto, attivo FROM " + TABLE_NAME + " WHERE attivo = TRUE";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                risultati.add(mapTappa(rs));
            }
        }
        return risultati;
    }

    public synchronized List<TappaBean> doRetrieveAll() throws SQLException {
        List<TappaBean> risultati = new ArrayList<>();
        String sql = "SELECT ID_tappa, nome_tappa, nome_porto, attivo FROM " + TABLE_NAME;

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                risultati.add(mapTappa(rs));
            }
        }
        return risultati;
    }

    private TappaBean mapTappa(ResultSet rs) throws SQLException {
        TappaBean tappa = new TappaBean();
        tappa.setId(rs.getInt("ID_tappa"));
        tappa.setLocalita(rs.getString("nome_tappa"));
        tappa.setPorto(rs.getString("nome_porto"));
        tappa.setAttivo(rs.getBoolean("attivo"));
        return tappa;
    }
}