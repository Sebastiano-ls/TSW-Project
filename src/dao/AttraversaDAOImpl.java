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

public class AttraversaDAOImpl implements AttraversaDAO{
    private static final String TABLE_NAME = "attraversa";
    private DataSource ds;

    public AttraversaDAOImpl(DataSource ds){
        this.ds=ds;
    }

    public void doSave(AttraversaBean bean) throws SQLException{
        String sql = "INSERT INTO " + TABLE_NAME + " (ID_crociera, ID_tappa, data_sosta) VALUES (?, ?, ?)";

        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, bean.getIdCrociera());
            ps.setInt(2, bean.getIdTappa());
            ps.setDate(3, bean.getDataSosta());

            ps.executeUpdate();
         }
    }

    public void doUpdate(AttraversaBean bean) throws SQLException{
        String sql = "UPDATE " + TABLE_NAME + " SET data_sosta = ? WHERE ID_crociera = ? AND ID_tappa = ?";

        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setDate(1, bean.getDataSosta());
            ps.setInt(2, bean.getIdCrociera());
            ps.setInt(3, bean.getIdTappa());

            ps.executeUpdate();
         }
    }

    public void doDelete(int idCrociera, int idTappa) throws SQLException{
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ID_crociera = ? AND ID_tappa = ?";

        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, idCrociera);
            ps.setInt(2, idTappa);

            ps.executeUpdate();
         }
    }

    public List<AttraversaBean> doRetrieveAll() throws SQLException{
        String sql = "SELECT * FROM " + TABLE_NAME;
        ArrayList<AttraversaBean> risultati = new ArrayList<>();

        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                AttraversaBean bean = new AttraversaBean();

                bean.setIdCrociera(rs.getInt("ID_crociera"));
                bean.setIdTappa(rs.getInt("ID_tappa"));
                bean.setDataSosta(rs.getDate("data_sosta"));

                risultati.add(bean);
            }
         }

         return risultati;
    }

    public List<TappaBean> doRetrieveByCrociera(int idCrociera) throws SQLException{
        ArrayList<TappaBean> tappe = new ArrayList<>();
        String sql = "SELECT t.ID_tappa, t.nome_tappa, t.nome_porto, t.attivo FROM " + TABLE_NAME + " a JOIN tappa t ON a.ID_tappa = t.ID_tappa WHERE a.ID_crociera = ? AND t.attivo = true";

        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setInt(1, idCrociera);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TappaBean tappa = new TappaBean();
                    
                    tappa.setId(rs.getInt("ID_tappa"));
                    tappa.setLocalita(rs.getString("nome_tappa"));
                    tappa.setPorto(rs.getString("nome_porto"));
                    tappa.setAttivo(rs.getBoolean("attivo"));
                    
                    tappe.add(tappa);
                }
            }
        }

        return tappe;
    }

    public List<TappaBean> doRetrieveByCrocieraAll(int idCrociera) throws SQLException{
        ArrayList<TappaBean> tappe = new ArrayList<>();
        String sql = "SELECT t.ID_tappa, t.nome_tappa, t.nome_porto, t.attivo FROM " + TABLE_NAME + " a JOIN tappa t ON a.ID_tappa = t.ID_tappa WHERE a.ID_crociera = ?";

        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setInt(1, idCrociera);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TappaBean tappa = new TappaBean();
                    
                    tappa.setId(rs.getInt("ID_tappa"));
                    tappa.setLocalita(rs.getString("nome_tappa"));
                    tappa.setPorto(rs.getString("nome_porto"));
                    tappa.setAttivo(rs.getBoolean("attivo"));
                    
                    tappe.add(tappa);
                }
            }
        }

        return tappe;
    }

    public List<CrocieraBean> doRetrieveByTappa(int idTappa) throws SQLException{
        ArrayList<CrocieraBean> crociere = new ArrayList<>();
        String sql = "SELECT c.ID_crociera, c.nome_crociera, c.descrizione, c.data_inizio, c.data_fine, c.prezzo, c.sconto, c.immagine_crociera, c.immagine_tipo, c.attivo FROM " + TABLE_NAME + " a JOIN crociera c ON a.ID_crociera = c.ID_crociera WHERE a.ID_tappa = ? AND c.attivo = true";

        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setInt(1, idTappa);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CrocieraBean crociera = new CrocieraBean();
                    
                    crociera.setId(rs.getInt(1));
                    crociera.setNome(rs.getString(2));
                    crociera.setDes(rs.getString(3));
                    crociera.setDataInizio(rs.getDate(4));
                    crociera.setDataFine(rs.getDate(5));
                    crociera.setPrezzo(rs.getInt(6));
                    crociera.setSconto(rs.getInt(7));
                    crociera.setImmagineCrociera(rs.getBytes(8));
                    crociera.setMimeType(rs.getString(9));
                    crociera.setAttivo(rs.getBoolean(10));
                    
                    crociere.add(crociera);
                }
            }
        }

        return crociere;
    }

    public List<CrocieraBean> doRetrieveByTappaAll(int idTappa) throws SQLException{
        ArrayList<CrocieraBean> crociere = new ArrayList<>();
        String sql = "SELECT c.ID_crociera, c.nome_crociera, c.descrizione, c.data_inizio, c.data_fine, c.prezzo, c.sconto, c.immagine_crociera, c.immagine_tipo, c.attivo FROM " + TABLE_NAME + " a JOIN crociera c ON a.ID_crociera = c.ID_crociera WHERE a.ID_tappa = ?";

        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setInt(1, idTappa);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CrocieraBean crociera = new CrocieraBean();
                    
                    crociera.setId(rs.getInt(1));
                    crociera.setNome(rs.getString(2));
                    crociera.setDes(rs.getString(3));
                    crociera.setDataInizio(rs.getDate(4));
                    crociera.setDataFine(rs.getDate(5));
                    crociera.setPrezzo(rs.getInt(6));
                    crociera.setSconto(rs.getInt(7));
                    crociera.setImmagineCrociera(rs.getBytes(8));
                    crociera.setMimeType(rs.getString(9));
                    crociera.setAttivo(rs.getBoolean(10));
                    
                    crociere.add(crociera);
                }
            }
        }

        return crociere;
    }
}
