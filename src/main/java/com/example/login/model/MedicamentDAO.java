package com.example.login.model;


import com.example.login.connection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {
    public static List<Medicament> getAllMedicaments() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.\"medicament\" ORDER BY id_medicament ASC";

        try {
            Connection conn = new connection().getConnection();
            ResultSet rsMedicament = conn.createStatement().executeQuery(selectStmt);
            List<Medicament> MedicamentList = getMedicamentList(rsMedicament);
            conn.close();
            return MedicamentList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }

    private static List<Medicament> getMedicamentList(ResultSet rs) throws SQLException {
        List<Medicament> medicamentList = new ArrayList<>();

        while (rs.next()) {
            Medicament medicament = new Medicament();
            medicament.setId_medicament(rs.getInt("id_medicament"));
            medicament.setNom_medicament(rs.getString("nom_medicament"));
            medicament.setFournisseur_medicament(rs.getString("fournisseur_medicament"));
            medicament.setFamille_medicament(rs.getString("famille_medicament"));
            medicament.setPrix_medicament(rs.getInt("prix_medicament"));
            medicament.setQuantite_medicament(rs.getInt("quantite_medicament"));
            medicamentList.add(medicament);
        }

        return medicamentList;
    }

    public static void insertMedicament(String nom,String fournisseur,String famille,Integer prix,Integer quantite) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO public.\"medicament\" (nom_medicament, fournisseur_medicament, famille_medicament, prix_medicament, quantite_medicament) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, nom);
            statement.setString(2, fournisseur);
            statement.setString(3, famille);
            statement.setInt(4, prix);
            statement.setInt(5, quantite);


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Un nouvel medicament a été inséré avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'insertion de medicament : " + e);
            throw e;
        }
    }

    public static List<Medicament> searchMedicament(int medicamentId, String nom,Integer prix,Integer quantite) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM public.\"medicament\" WHERE id_medicament = ? OR nom_medicament = ? OR quantite_medicament = ? OR prix_medicament = ?";
        List<Medicament> medicamentList = new ArrayList<>();

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, medicamentId);
            statement.setString(2, nom);
            statement.setInt(3, prix);
            statement.setInt(4, quantite);

            ResultSet rs = statement.executeQuery();
            medicamentList = getMedicamentList(rs);
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la recherche de l'utilisateur : " + e);
            throw e;
        }
        return medicamentList;
    }

    public static void updateMedicament(int id, String nom,Integer prix,Integer quantite) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE public.\"medicament\" SET nom_medicament = ?, prix_medicament = ?, quantite_medicament = ? WHERE id_medicament = ?";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, nom);
            statement.setInt(2, prix);
            statement.setInt(3, quantite);
            statement.setInt(4, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Medicament mis à jour avec succès !");
            } else {
                throw new SQLException("La mise à jour de Medicament a échoué, aucun enregistrement mis à jour.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la mise à jour de Medicament : " + e.getMessage());
            throw e;
        }
    }

    public static void deleteMedicament(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM public.\"medicament\" WHERE id_medicament = ?";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Medicament supprimé avec succès !");
            } else {
                throw new SQLException("La suppression de Medicament a échoué, aucun enregistrement supprimé.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la suppression de Medicament : " + e.getMessage());
            throw e;
        }
    }

  

}
