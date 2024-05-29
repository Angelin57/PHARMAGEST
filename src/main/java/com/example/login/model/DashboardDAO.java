package com.example.login.model;

import com.example.login.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {

    public static List<Vente> getVenteJournalier() throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT SUM(prix_total) AS prix_total\n" +
                "FROM public.\"vente\"\n" +
                "WHERE heure_creation >= NOW() - INTERVAL '24 hours';\n";
        try {
            Connection conn = new connection().getConnection();
            ResultSet rsMedicament = conn.createStatement().executeQuery(selectStmt);
            List<Vente> MedicamentList = getVentetList1(rsMedicament);
            conn.close();
            return MedicamentList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }
    private static List<Vente> getVentetList1(ResultSet rs) throws SQLException {
        List<Vente> medicamentList = new ArrayList<>();

        while (rs.next()) {
            Vente vente = new Vente();
            vente.setPrixTotal(rs.getInt("prix_total"));
            medicamentList.add(vente);
        }

        return medicamentList;
    }

    public static List<Vente> getVenteHebdomadaire() throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT SUM(prix_total) AS prix_total\n" +
                "FROM public.\"vente\"\n" +
                "WHERE heure_creation >= NOW() - INTERVAL '7 days';\n";
        try {
            Connection conn = new connection().getConnection();
            ResultSet rsMedicament = conn.createStatement().executeQuery(selectStmt);
            List<Vente> MedicamentList = getVentetList2(rsMedicament);
            conn.close();
            return MedicamentList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }
    private static List<Vente> getVentetList2(ResultSet rs) throws SQLException {
        List<Vente> medicamentList = new ArrayList<>();

        while (rs.next()) {
            Vente vente = new Vente();
            vente.setPrixTotal(rs.getInt("prix_total"));
            medicamentList.add(vente);
        }

        return medicamentList;
    }


    public static List<Vente> getVenteMensuel() throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT SUM(prix_total) AS prix_total\n" +
                "FROM public.\"vente\"\n" +
                "WHERE heure_creation >= NOW() - INTERVAL '30 days';\n";
        try {
            Connection conn = new connection().getConnection();
            ResultSet rsMedicament = conn.createStatement().executeQuery(selectStmt);
            List<Vente> MedicamentList = getVentetList3(rsMedicament);
            conn.close();
            return MedicamentList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }
    private static List<Vente> getVentetList3(ResultSet rs) throws SQLException {
        List<Vente> medicamentList = new ArrayList<>();

        while (rs.next()) {
            Vente vente = new Vente();
            vente.setPrixTotal(rs.getInt("prix_total"));
            medicamentList.add(vente);
        }

        return medicamentList;
    }

    public static List<Medicament> getMed() throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT COUNT(*) quantite_medicament FROM public.\"medicament\"\n " +
                "WHERE quantite_medicament <= 30 ";
        try {
            Connection conn = new connection().getConnection();
            ResultSet rsMedicament = conn.createStatement().executeQuery(selectStmt);
            List<Medicament> MedicamentList = getMed(rsMedicament);
            conn.close();
            return MedicamentList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }

    private static List<Medicament> getMed(ResultSet rs) throws SQLException {
        List<Medicament> medicamentList = new ArrayList<>();

        while (rs.next()) {
            Medicament medicament = new Medicament();
            medicament.setQuantite_medicament(rs.getInt("quantite_medicament"));
            medicamentList.add(medicament);
        }

        return medicamentList;
    }


}
