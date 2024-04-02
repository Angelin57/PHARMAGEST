package com.example.login.model;

import com.example.login.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamilleDAO {
    public static List<Famille> getAllFamilles() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.\"famille\" ORDER BY id_famille ASC";

        try {
            Connection conn = new connection().getConnection();
            ResultSet rsFamilles = conn.createStatement().executeQuery(selectStmt);
            List<Famille> familleList = getFamilleList(rsFamilles);
            conn.close();
            return familleList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }
    private static List<Famille> getFamilleList(ResultSet rs) throws SQLException {
        List<Famille> familleList = new ArrayList<>();

        while (rs.next()) {
            Famille famille = new Famille();
            famille.setIdFamille(rs.getInt("id_famille"));
            famille.setNom(rs.getString("nom_famille"));
            familleList.add(famille);
        }

        return familleList;
    }
    public static void insertFamille(String nom) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO public.\"famille\" (nom_famille) VALUES (?)";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, nom);


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Un nouvel utilisateur a été inséré avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'insertion de l'utilisateur : " + e);
            throw e;
        }
    }


    public static List<Famille> searchFamille(int familleId, String nom) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM public.\"famille\" WHERE id_famille = ? OR nom_famille = ?";
        List<Famille> familleList = new ArrayList<>();

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, familleId);
            statement.setString(2, nom);

            ResultSet rs = statement.executeQuery();
            familleList = getFamilleList(rs);
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la recherche de l'utilisateur : " + e);
            throw e;
        }

        return familleList;
    }
    public static void updateFamille(int id, String nom) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE public.\"famille\" SET nom_famille = ? WHERE id_famille = ?";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, nom);
            statement.setInt(2, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Utilisateur mis à jour avec succès !");
            } else {
                throw new SQLException("La mise à jour de l'utilisateur a échoué, aucun enregistrement mis à jour.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la mise à jour de l'utilisateur : " + e.getMessage());
            throw e;
        }
    }
    public static void deleteFamille(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM public.\"famille\" WHERE id_famille = ?";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Utilisateur supprimé avec succès !");
            } else {
                throw new SQLException("La suppression de l'utilisateur a échoué, aucun enregistrement supprimé.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la suppression de l'utilisateur : " + e.getMessage());
            throw e;
        }
    }
}
