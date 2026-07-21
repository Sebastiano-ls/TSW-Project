package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.OrdineBean;

public class OrdineDAOImpl implements OrdineDAO {

    private static final String TABLE_NAME = "ordine";
    private DataSource ds;

    public OrdineDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public synchronized void doSave(OrdineBean bean) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (data_pagamento, tot_ordine, ID_utente) VALUES (?, ?, ?)";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, bean.getDataPagamento());
            ps.setDouble(2, bean.getTotOrdine());
            ps.setInt(3, bean.getIdUtente());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    bean.setIdOrdine(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public synchronized void doSaveConDettagli(OrdineBean ordine, List<model.DettaglioOrdineBean> dettagli) throws SQLException {
        doSave(ordine);

        String sqlDettaglio = "INSERT INTO dettagli_ordine (num_biglietto_adulto, num_biglietto_bambino, prezzo_archiviato, ID_ordine, ID_crociera) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = ds.getConnection();
             PreparedStatement psD = c.prepareStatement(sqlDettaglio)) {
            for (model.DettaglioOrdineBean d : dettagli) {
                psD.setInt(1, d.getNumBigliettoAdulto());
                psD.setInt(2, d.getNumBigliettoBambino());
                psD.setDouble(3, d.getPrezzoArchiviato());
                psD.setInt(4, ordine.getIdOrdine());
                psD.setInt(5, d.getIdCrociera());
                psD.executeUpdate();
            }
        }
    }

    @Override
    public synchronized List<OrdineBean> doRetrieveByIdUtente(int idUtente) throws SQLException {
        List<OrdineBean> lista = new ArrayList<>();
        String sql = "SELECT o.*, u.nome AS nome_utente, u.cognome AS cognome_utente, u.email AS email_utente "
                + "FROM " + TABLE_NAME + " o JOIN utente u ON o.ID_utente = u.ID_utente WHERE o.ID_utente = ? ORDER BY o.data_pagamento DESC";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapBean(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public synchronized OrdineBean doRetrieveByKey(int id) throws SQLException {
        OrdineBean bean = null;
        String sql = "SELECT o.*, u.nome AS nome_utente, u.cognome AS cognome_utente, u.email AS email_utente "
                + "FROM " + TABLE_NAME + " o JOIN utente u ON o.ID_utente = u.ID_utente WHERE o.ID_ordine = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bean = mapBean(rs);
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized List<OrdineBean> doRetrieveAll() throws SQLException {
        List<OrdineBean> lista = new ArrayList<>();
        String sql = "SELECT o.*, u.nome AS nome_utente, u.cognome AS cognome_utente, u.email AS email_utente "
                + "FROM " + TABLE_NAME + " o JOIN utente u ON o.ID_utente = u.ID_utente ORDER BY o.data_pagamento DESC";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapBean(rs));
            }
        }
        return lista;
    }

    @Override
    public synchronized List<OrdineBean> doRetrieveByCond(String daData, String aData, String email) throws SQLException {
        List<OrdineBean> lista = doRetrieveAll();

        if (lista.isEmpty()) return lista;

        List<OrdineBean> filtrata = new ArrayList<>();
        for (OrdineBean o : lista) {
            boolean match = true;

            if (daData != null && !daData.trim().isEmpty()) {
                try {
                    if (o.getDataPagamento().before(java.sql.Date.valueOf(daData))) match = false;
                } catch (IllegalArgumentException e) {
                    throw new SQLException("Formato data non valido", e);
                }
            }

            if (aData != null && !aData.trim().isEmpty() && match) {
                try {
                    if (o.getDataPagamento().after(java.sql.Date.valueOf(aData))) match = false;
                } catch (IllegalArgumentException e) {
                    throw new SQLException("Formato data non valido", e);
                }
            }

            if (email != null && !email.trim().isEmpty() && match) {
                if (o.getEmailUtente() == null || !o.getEmailUtente().toLowerCase().contains(email.trim().toLowerCase()))
                    match = false;
            }

            if (match) filtrata.add(o);
        }
        return filtrata;
    }

    private OrdineBean mapBean(ResultSet rs) throws SQLException {
        OrdineBean bean = new OrdineBean();
        bean.setIdOrdine(rs.getInt("ID_ordine"));
        bean.setDataPagamento(rs.getDate("data_pagamento"));
        bean.setTotOrdine(rs.getDouble("tot_ordine"));
        bean.setIdUtente(rs.getInt("ID_utente"));
        bean.setNomeUtente(rs.getString("nome_utente"));
        bean.setCognomeUtente(rs.getString("cognome_utente"));
        bean.setEmailUtente(rs.getString("email_utente"));
        return bean;
    }
}
