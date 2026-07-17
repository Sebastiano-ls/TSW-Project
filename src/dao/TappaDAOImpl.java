package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.CrocieraBean;
import model.TappaBean;

public class TappaDAOImpl implements TappaDAO{
    private static final String TABLE_NAME = "tappa";
    private static final String TABLE_CROCIERA_NAME = "crociera";
    private DataSource ds;

    public TappaDAOImpl(DataSource ds){
        this.ds=ds;
    }

    public synchronized void doSave(TappaBean tappa) throws SQLException{
        String sql = "INSERT INTO " + TABLE_NAME + "(nome_tappa, nome_porto, attivo) VALUES (?, ?, ?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tappa.getLocalita());
            ps.setString(2, tappa.getPorto());
            ps.setBoolean(3, tappa.isAttivo());
        
            ps.executeUpdate();
        }
    }

    public synchronized TappaBean doRetrieveByKey(int id) throws SQLException{
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID_tappa = ?";
        TappaBean tappa = new TappaBean();

        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tappa.setId(rs.getInt(1));
                    tappa.setLocalita(rs.getString(2));
                    tappa.setPorto(rs.getString(3));
                    tappa.setAttivo(rs.getBoolean(4));
                }
            }
        }
        return tappa;
    }

    //tappe attive al momento della crociera
    public synchronized List<TappaBean> doRetrieveByCrociera(CrocieraBean crociera) throws SQLException{
        ArrayList<TappaBean> tappe = new ArrayList<>();
        String sql = "SELECT DISTINCT t.* FROM " + TABLE_NAME + " t " +  "LEFT JOIN attraversa a ON t.ID_tappa = a.ID_tappa " + "LEFT JOIN " + TABLE_CROCIERA_NAME + " c ON a.ID_crociera = c.ID_crociera " + "WHERE t.attivo = TRUE";

        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                TappaBean tappa = new TappaBean();

                tappa.setId(rs.getInt(1));
                tappa.setLocalita(rs.getString(2));
                tappa.setPorto(rs.getString(3));
                tappa.setAttivo(rs.getBoolean(4));

                tappe.add(tappa);
            }
        }

        return tappe;
    }

    public synchronized List<TappaBean> doRetrieveByCrocieraAll(CrocieraBean crociera) throws SQLException{
        ArrayList<TappaBean> tappe = new ArrayList<>();
        String sql = "SELECT DISTINCT t.* FROM " + TABLE_NAME + " t " +  "LEFT JOIN attraversa a ON t.ID_tappa = a.ID_tappa " + "LEFT JOIN " + TABLE_CROCIERA_NAME + " c ON a.ID_crociera = c.ID_crociera ";

        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                TappaBean tappa = new TappaBean();

                tappa.setId(rs.getInt(1));
                tappa.setLocalita(rs.getString(2));
                tappa.setPorto(rs.getString(3));
                tappa.setAttivo(rs.getBoolean(4));

                tappe.add(tappa);
            }
        }

        return tappe;
    }


    public synchronized void doUpdate(TappaBean tappa) throws SQLException{
        String sql = "UPDATE " + TABLE_NAME + " SET (ID_tappa, nome_tappa, nome_porto, attivo) VALUES (?, ?, ?, ?)";

        TappaBean tappaConfermata = doRetrieveByKey(tappa.getId());

        if(tappa.getLocalita() != null){
            tappaConfermata.setLocalita(tappa.getLocalita());
        }

        if(tappa.getPorto() != null){
            tappaConfermata.setPorto(tappa.getPorto());
        }

        if(tappa.isAttivo()!=tappaConfermata.isAttivo()){
            tappaConfermata.setAttivo(tappa.isAttivo());
        }

         try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setInt(1, tappaConfermata.getId());
            ps.setString(2, tappaConfermata.getLocalita());
            ps.setString(3, tappaConfermata.getPorto());
            ps.setBoolean(4, tappaConfermata.isAttivo());
                
            ps.executeUpdate();
        }
    }
    
    public synchronized void doDelete(int id) throws SQLException{
        String sql = "UPDATE " + TABLE_NAME + " SET attivo = FALSE WHERE ID_crociera = ?";
        
        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                
            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }

    public synchronized List<TappaBean> doRetrieveAllAttivi() throws SQLException{
        ArrayList<TappaBean> risultati = new ArrayList<>();
        String sql = "SELECT *" + " FROM " + TABLE_NAME + " WHERE attivo = TRUE";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TappaBean tappa = new TappaBean();

                tappa.setId(rs.getInt(1));
                tappa.setLocalita(rs.getString(2));
                tappa.setPorto(rs.getString(3));
                tappa.setAttivo(rs.getBoolean(4));

                risultati.add(tappa);
            }
        }

        return risultati;
    }

    public synchronized List<TappaBean> doRetrieveAll() throws SQLException{
        ArrayList<TappaBean> risultati = new ArrayList<>();
        String sql = "SELECT *" + " FROM " + TABLE_NAME;

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TappaBean tappa = new TappaBean();

                tappa.setId(rs.getInt(1));
                tappa.setLocalita(rs.getString(2));
                tappa.setPorto(rs.getString(3));
                tappa.setAttivo(rs.getBoolean(4));

                risultati.add(tappa);
            }
        }
        return risultati;
    }
}
