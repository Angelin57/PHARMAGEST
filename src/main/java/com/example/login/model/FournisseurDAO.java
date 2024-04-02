package com.example.login.model;

import com.example.login.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FournisseurDAO {

    public static List<Fournisseur> getAllFournisseurs() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.\"fournisseur\" ORDER BY id_fournisseur ASC";

        try {
            Connection conn = new connection().getConnection();
            ResultSet rsFournisseurs = conn.createStatement().executeQuery(selectStmt);
            List<Fournisseur> fournisseurList = getFournisseurList(rsFournisseurs);
            conn.close();
            return fournisseurList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }

    private static List<Fournisseur> getFournisseurList(ResultSet rs) throws SQLException {
        List<Fournisseur> fournisseurList = new ArrayList<>();

        while (rs.next()) {
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setIdFournisseur(rs.getInt("id_fournisseur"));
            fournisseur.setNom(rs.getString("nom_fournisseur"));
            fournisseur.setEmail(rs.getString("email_fournisseur"));
            fournisseur.setTelephone(rs.getInt("tel_fournisseur"));
            fournisseurList.add(fournisseur);
        }

        return fournisseurList;
    }

    public static void insertFournisseur(String nom, String email, int telephone) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO public.\"fournisseur\" (nom_fournisseur, email_fournisseur, tel_fournisseur) VALUES (?, ?, ?)";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, nom);
            statement.setString(2, email);
            statement.setInt(3, telephone);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Un nouveau fournisseur a été inséré avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'insertion du fournisseur : " + e);
            throw e;
        }
    }
    public static List<Fournisseur> searchFournisseur(int idFournisseur, String nomFournisseur) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM public.\"fournisseur\" WHERE id_fournisseur = ? OR nom_fournisseur = ?";
        List<Fournisseur> fournisseurList = new ArrayList<>();

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idFournisseur);
            statement.setString(2, nomFournisseur);

            ResultSet rs = statement.executeQuery();
            fournisseurList = getFournisseurList(rs);

        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la recherche du fournisseur : " + e);
            throw e;
        }

        return fournisseurList;
    }

    public static void updateFournisseur(int id, String nom, String email, int telephone) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE public.\"fournisseur\" SET nom_fournisseur = ?, email_fournisseur = ?, tel_fournisseur = ? WHERE id_fournisseur = ?";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nom);
            statement.setString(2, email);
            statement.setInt(3, telephone);
            statement.setInt(4, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Fournisseur mis à jour avec succès !");
            } else {
                throw new SQLException("La mise à jour du fournisseur a échoué, aucun enregistrement mis à jour.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la mise à jour du fournisseur : " + e.getMessage());
            throw e;
        }
    }

    public static void deleteFournisseur(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM public.\"fournisseur\" WHERE id_fournisseur = ?";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Fournisseur supprimé avec succès !");
            } else {
                throw new SQLException("La suppression du fournisseur a échoué, aucun enregistrement supprimé.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la suppression du fournisseur : " + e.getMessage());
            throw e;
        }
    }
    public static void deleteUser(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM public.\"utilisateur\" WHERE id_utilisateur = ?";

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