package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.AttraversaBean;
import model.CrocieraBean;
import model.TappaBean;

public class AttraversaDAOImpl implements AttraversaDAO {
    private static final String TABLE_NAME = "attraversa";
    private DataSource ds;

    public AttraversaDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    public synchronized void doSave(AttraversaBean bean) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (ID_crociera, ID_tappa, data_sosta) VALUES (?, ?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bean.getIdCrociera());
            ps.setInt(2, bean.getIdTappa());
            ps.setDate(3, bean.getDataSosta());

            ps.executeUpdate();
        }
    }

    public synchronized void doUpdate(AttraversaBean bean) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET data_sosta = ? WHERE ID_crociera = ? AND ID_tappa = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, bean.getDataSosta());
            ps.setInt(2, bean.getIdCrociera());
            ps.setInt(3, bean.getIdTappa());

            ps.executeUpdate();
        }
    }

    public synchronized void doDelete(int idCrociera, int idTappa) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ID_crociera = ? AND ID_tappa = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCrociera);
            ps.setInt(2, idTappa);

            ps.executeUpdate();
        }
    }

    public synchronized List<AttraversaBean> doRetrieveAll() throws SQLException {
        String sql = "SELECT ID_crociera, ID_tappa, data_sosta FROM " + TABLE_NAME;
        List<AttraversaBean> risultati = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AttraversaBean bean = new AttraversaBean();
                bean.setIdCrociera(rs.getInt("ID_crociera"));
                bean.setIdTappa(rs.getInt("ID_tappa"));
                bean.setDataSosta(rs.getDate("data_sosta"));

                risultati.add(bean);
            }
        }
        return risultati;
    }

    public synchronized List<TappaBean> doRetrieveByCrociera(int idCrociera) throws SQLException {
        List<TappaBean> tappe = new ArrayList<>();
        String sql = "SELECT t.ID_tappa, t.nome_tappa, t.nome_porto, t.attivo " +
                     "FROM " + TABLE_NAME + " a " +
                     "JOIN tappa t ON a.ID_tappa = t.ID_tappa " +
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
        String sql = "SELECT t.ID_tappa, t.nome_tappa, t.nome_porto, t.attivo " +
                     "FROM " + TABLE_NAME + " a " +
                     "JOIN tappa t ON a.ID_tappa = t.ID_tappa " +
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

    public synchronized List<CrocieraBean> doRetrieveByTappa(int idTappa) throws SQLException {
        List<CrocieraBean> crociere = new ArrayList<>();
        String sql = "SELECT c.ID_crociera, c.nome_crociera, c.descrizione, c.data_inizio, c.data_fine, " +
                     "c.prezzo, c.sconto, c.immagine_crociera, c.immagine_tipo, c.attivo " +
                     "FROM " + TABLE_NAME + " a " +
                     "JOIN crociera c ON a.ID_crociera = c.ID_crociera " +
                     "WHERE a.ID_tappa = ? AND c.attivo = TRUE " +
                     "ORDER BY c.data_inizio DESC";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTappa);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    crociere.add(mapCrociera(rs));
                }
            }
        }
        return crociere;
    }

    public synchronized List<CrocieraBean> doRetrieveByTappaAll(int idTappa) throws SQLException {
        List<CrocieraBean> crociere = new ArrayList<>();
        String sql = "SELECT c.ID_crociera, c.nome_crociera, c.descrizione, c.data_inizio, c.data_fine, " +
                     "c.prezzo, c.sconto, c.immagine_crociera, c.immagine_tipo, c.attivo " +
                     "FROM " + TABLE_NAME + " a " +
                     "JOIN crociera c ON a.ID_crociera = c.ID_crociera " +
                     "WHERE a.ID_tappa = ? " +
                     "ORDER BY c.data_inizio DESC";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTappa);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    crociere.add(mapCrociera(rs));
                }
            }
        }
        return crociere;
    }

    private TappaBean mapTappa(ResultSet rs) throws SQLException {
        TappaBean tappa = new TappaBean();
        tappa.setId(rs.getInt("ID_tappa"));
        tappa.setLocalita(rs.getString("nome_tappa"));
        tappa.setPorto(rs.getString("nome_porto"));
        tappa.setAttivo(rs.getBoolean("attivo"));
        return tappa;
    }

    private CrocieraBean mapCrociera(ResultSet rs) throws SQLException {
        CrocieraBean crociera = new CrocieraBean();
        crociera.setId(rs.getInt("ID_crociera"));
        crociera.setNome(rs.getString("nome_crociera"));
        crociera.setDes(rs.getString("descrizione"));
        crociera.setDataInizio(rs.getDate("data_inizio"));
        crociera.setDataFine(rs.getDate("data_fine"));
        crociera.setPrezzo(rs.getDouble("prezzo"));
        crociera.setSconto(rs.getDouble("sconto"));
        crociera.setImmagineCrociera(rs.getBytes("immagine_crociera"));
        crociera.setMimeType(rs.getString("immagine_tipo"));
        crociera.setAttivo(rs.getBoolean("attivo"));
        return crociera;
    }
}