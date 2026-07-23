package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.CrocieraBean;

public class CrocieraDAOImpl implements CrocieraDAO{
    private static final String TABLE_NAME = "crociera";
    private static final String ALL_COLS = "ID_crociera, nome_crociera, descrizione, data_inizio, data_fine, prezzo, sconto, immagine_crociera, immagine_tipo, attivo";
    private static final String ALL_COLS_ALIAS = "c.ID_crociera, c.nome_crociera, c.descrizione, c.data_inizio, c.data_fine, c.prezzo, c.sconto, c.immagine_crociera, c.immagine_tipo, c.attivo";
    private DataSource ds;

    public CrocieraDAOImpl(DataSource ds){
        this.ds=ds;
    }

    public synchronized void doSave(CrocieraBean crociera) throws SQLException{
        String sql = "INSERT INTO " + TABLE_NAME + " (nome_crociera, descrizione, data_inizio, data_fine, prezzo, sconto, immagine_crociera, immagine_tipo, attivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, crociera.getNome());
            ps.setString(2, crociera.getDes());
            ps.setDate(3, crociera.getDataInizio());
            ps.setDate(4, crociera.getDataFine());
            ps.setDouble(5, crociera.getPrezzo());
            ps.setDouble(6, crociera.getSconto());
            ps.setBytes(7, crociera.getImmagineCrociera());
            ps.setString(8, crociera.getMimeType());
            ps.setBoolean(9, crociera.isAttivo());
            ps.executeUpdate();
        }
    }

    public synchronized CrocieraBean doRetrieveByKey(int id) throws SQLException{
        CrocieraBean crociera = null;
        String sql = "SELECT " + ALL_COLS + " FROM " + TABLE_NAME + " WHERE ID_crociera = ?";

        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    crociera = mapCrociera(rs);
                }
            }
        }
        return crociera;
    }

    public synchronized void doUpdate(CrocieraBean crociera) throws SQLException{
        boolean hasImage = crociera.getImmagineCrociera() != null;
        String sql;

        //COSTRUISCO LA CROCIERA IN BASE AI SOLI ELEMENTI MODIFICATI DALL'ADMIN
        CrocieraBean crocieraConfermata;
        if((crocieraConfermata = doRetrieveByKey(crociera.getId())) == null )
            throw new SQLException("Impossibile aggiornare: Crociera con ID " + crociera.getId() + " non trovata.");

        if(crociera.getNome() != null){
            crocieraConfermata.setNome(crociera.getNome());
        }

        if(crociera.getDes() != null){
            crocieraConfermata.setDes(crociera.getDes());
        }

        if(crociera.getDataInizio() != null){
            crocieraConfermata.setDataInizio(crociera.getDataInizio());
        }

        if(crociera.getDataFine() != null){
            crocieraConfermata.setDataFine(crociera.getDataFine());
        }

        if(crociera.getPrezzo() != -1){
            crocieraConfermata.setPrezzo(crociera.getPrezzo());
        }

        if(crociera.getSconto() != -1){
            crocieraConfermata.setSconto(crociera.getSconto());
        }

        if(crociera.isAttivo()!=crocieraConfermata.isAttivo())
        crocieraConfermata.setAttivo(crociera.isAttivo());

        if (hasImage) {
            crocieraConfermata.setImmagineCrociera(crociera.getImmagineCrociera());
            crocieraConfermata.setMimeType(crociera.getMimeType());
        }

        if (hasImage) {
            sql = "UPDATE " + TABLE_NAME
                    + " SET nome_crociera = ?, descrizione = ?, data_inizio = ?, data_fine = ?, prezzo = ?, sconto = ?, attivo = ?, immagine_crociera = ?, immagine_tipo = ? WHERE ID_crociera = ?";
        } else {
            sql = "UPDATE " + TABLE_NAME
                    + " SET nome_crociera = ?, descrizione = ?, data_inizio = ?, data_fine = ?, prezzo = ?, sconto = ?, attivo = ? WHERE ID_crociera = ?";
        }

        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setString(1, crocieraConfermata.getNome());
                ps.setString(2, crocieraConfermata.getDes());
                ps.setDate(3, crocieraConfermata.getDataInizio());
                ps.setDate(4, crocieraConfermata.getDataFine());
                ps.setDouble(5, crocieraConfermata.getPrezzo());
                ps.setDouble(6, crocieraConfermata.getSconto());
                ps.setBoolean(7, crocieraConfermata.isAttivo());
                if (hasImage) {
                    ps.setBytes(8, crocieraConfermata.getImmagineCrociera());
                    ps.setString(9, crocieraConfermata.getMimeType());
                    ps.setInt(10, crocieraConfermata.getId());
                } else {
                    ps.setInt(8, crocieraConfermata.getId());
                }
            ps.executeUpdate();
        }
    }

    public synchronized void doDelete(int id) throws SQLException{
        String sql = "UPDATE " + TABLE_NAME + " SET attivo = FALSE WHERE ID_crociera = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public synchronized List<CrocieraBean> doRetrieveByParams(String destinazione, String partenza, Date dataIn) throws SQLException{
        List<CrocieraBean> risultati = new ArrayList<>();
        String sql="SELECT DISTINCT " + ALL_COLS_ALIAS + " FROM " + TABLE_NAME + " c " +  "LEFT JOIN attraversa a ON c.ID_crociera = a.ID_crociera " + "LEFT JOIN tappa t ON a.ID_tappa = t.ID_tappa " + "WHERE c.attivo = TRUE";

        //VERIFICO QUALI PARAMETRI USARE PER LA RICERCA
        List<Object> parametri = new ArrayList<>();

        if(destinazione!=null){
            parametri.add("%" + destinazione + "%");
            parametri.add("%" + destinazione + "%");
            parametri.add("%" + destinazione + "%");
            sql = sql + " AND (c.nome_crociera LIKE ? OR c.descrizione LIKE ? OR t.nome_tappa LIKE ?)";
        }

        if(partenza!=null){
            parametri.add("%" + partenza + "%");
            sql = sql + " AND t.nome_porto LIKE ?";
        }

        if(dataIn!=null){
            parametri.add(dataIn);
            sql = sql + " AND c.data_inizio >= ?";
        }

        sql = sql + " ORDER BY c.data_inizio DESC";

        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                //ASSEGNAZIONE DINAMICA PARAMETRI
                for (int i = 0; i < parametri.size(); i++) {
                    Object param = parametri.get(i);
                    if (param instanceof String) {
                        ps.setString(i + 1, (String) param);
                    } else if (param instanceof java.sql.Date) {
                        ps.setDate(i + 1, (java.sql.Date) param);
                    }
                }
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    risultati.add(mapCrociera(rs));
                }
            }
        }
        return risultati;
    }

    public synchronized List<CrocieraBean> doRetrieveByPrezziBassi() throws SQLException{
        List<CrocieraBean> risultati = new ArrayList<>();
        String sql = "SELECT " + ALL_COLS + " FROM " + TABLE_NAME + " WHERE attivo = TRUE ORDER BY (prezzo - (prezzo * sconto / 100.0)) ASC";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                risultati.add(mapCrociera(rs));
            }
        }
        return risultati;
    }

    public synchronized List<CrocieraBean> doRetrieveAllAttivi() throws SQLException{
        List<CrocieraBean> risultati = new ArrayList<>();
        String sql = "SELECT " + ALL_COLS + " FROM " + TABLE_NAME + " WHERE attivo = TRUE ORDER BY data_inizio DESC";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                risultati.add(mapCrociera(rs));
            }
        }
        return risultati;
    }

    public synchronized List<CrocieraBean> doRetrieveAll() throws SQLException{
        List<CrocieraBean> risultati = new ArrayList<>();
        String sql = "SELECT " + ALL_COLS + " FROM " + TABLE_NAME + " ORDER BY data_inizio DESC";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                risultati.add(mapCrociera(rs));
            }
        }
        return risultati;
    }

    public synchronized byte[] doRetrieveImage(int id) throws SQLException{
        String sql = "SELECT immagine_crociera FROM " + TABLE_NAME + " WHERE ID_crociera = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("immagine_crociera");
                }
            }
        }
        return null;
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