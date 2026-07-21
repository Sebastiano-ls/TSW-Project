package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.DettaglioOrdineBean;

public class DettaglioOrdineDAOImpl implements DettaglioOrdineDAO {

    private static final String TABLE_NAME = "dettagli_ordine";
    private DataSource ds;

    public DettaglioOrdineDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public synchronized void doSave(DettaglioOrdineBean bean) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (num_biglietto_adulto, num_biglietto_bambino, prezzo_archiviato, nome_crociera_archiviato, descrizione_archiviato, data_inizio_archiviato, data_fine_archiviato, tappe_archiviato, ID_ordine, ID_crociera) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bean.getNumBigliettoAdulto());
            ps.setInt(2, bean.getNumBigliettoBambino());
            ps.setDouble(3, bean.getPrezzoArchiviato());
            ps.setString(4, bean.getNomeCrocieraArchiviato());
            ps.setString(5, bean.getDescrizioneArchiviato());
            ps.setDate(6, bean.getDataInizioArchiviato());
            ps.setDate(7, bean.getDataFineArchiviato());
            ps.setString(8, bean.getTappeArchiviato());
            ps.setInt(9, bean.getIdOrdine());
            ps.setInt(10, bean.getIdCrociera());
            ps.executeUpdate();
        }
    }

    @Override
    public synchronized DettaglioOrdineBean doRetrieveByKey(int idDettaglio) throws SQLException {
        DettaglioOrdineBean bean = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID_dettaglio = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idDettaglio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bean = mapBean(rs);
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized List<DettaglioOrdineBean> doRetrieveByIdOrdine(int idOrdine) throws SQLException {
        List<DettaglioOrdineBean> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID_ordine = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idOrdine);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapBean(rs));
                }
            }
        }
        return lista;
    }

    private DettaglioOrdineBean mapBean(ResultSet rs) throws SQLException {
        DettaglioOrdineBean bean = new DettaglioOrdineBean();
        bean.setIdDettaglio(rs.getInt("ID_dettaglio"));
        bean.setNumBigliettoAdulto(rs.getInt("num_biglietto_adulto"));
        bean.setNumBigliettoBambino(rs.getInt("num_biglietto_bambino"));
        bean.setPrezzoArchiviato(rs.getDouble("prezzo_archiviato"));
        bean.setNomeCrocieraArchiviato(rs.getString("nome_crociera_archiviato"));
        bean.setDescrizioneArchiviato(rs.getString("descrizione_archiviato"));
        bean.setDataInizioArchiviato(rs.getDate("data_inizio_archiviato"));
        bean.setDataFineArchiviato(rs.getDate("data_fine_archiviato"));
        bean.setTappeArchiviato(rs.getString("tappe_archiviato"));
        bean.setIdOrdine(rs.getInt("ID_ordine"));
        bean.setIdCrociera(rs.getInt("ID_crociera"));
        return bean;
    }
}
