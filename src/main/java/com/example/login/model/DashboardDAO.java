package com.example.login.model;

import com.example.login.connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {

    public static List<Facture> getVenteJournalier() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT SUM(montant_total) AS montant_total " +
                "FROM public.\"facture\" " +
                "WHERE (date + heure) >= NOW() - INTERVAL '24 hours';";
        try (Connection conn = new connection().getConnection();
             ResultSet rsFacture = conn.createStatement().executeQuery(selectStmt)) {
            return getFactureList(rsFacture);
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }

    public static List<Facture> getVenteHebdomadaire() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT SUM(montant_total) AS montant_total " +
                "FROM public.\"facture\" " +
                "WHERE (date + heure) >= NOW() - INTERVAL '7 days';";
        try (Connection conn = new connection().getConnection();
             ResultSet rsFacture = conn.createStatement().executeQuery(selectStmt)) {
            return getFactureList(rsFacture);
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }

    public static List<Facture> getVenteMensuel() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT SUM(montant_total) AS montant_total " +
                "FROM public.\"facture\" " +
                "WHERE (date + heure) >= NOW() - INTERVAL '30 days';";
        try (Connection conn = new connection().getConnection();
             ResultSet rsFacture = conn.createStatement().executeQuery(selectStmt)) {
            return getFactureList(rsFacture);
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }

    private static List<Facture> getFactureList(ResultSet rs) throws SQLException {
        List<Facture> factureList = new ArrayList<>();
        while (rs.next()) {
            Facture facture = new Facture();
            facture.setMontantTotal(rs.getInt("montant_total"));
            factureList.add(facture);
        }
        return factureList;
    }

    public static List<Medicament> getMed() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT quantite_medicament FROM public.\"medicament\" " +
                "WHERE quantite_medicament <= 30";
        try (Connection conn = new connection().getConnection();
             ResultSet rsMedicament = conn.createStatement().executeQuery(selectStmt)) {
            return getMedList(rsMedicament);
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }

    private static List<Medicament> getMedList(ResultSet rs) throws SQLException {
        List<Medicament> medicamentList = new ArrayList<>();
        while (rs.next()) {
            Medicament medicament = new Medicament();
            medicament.setQuantite_medicament(rs.getInt("quantite_medicament"));
            medicamentList.add(medicament);
        }
        return medicamentList;
    }
}
