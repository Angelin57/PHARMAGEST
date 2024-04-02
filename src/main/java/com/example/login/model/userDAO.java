package com.example.login.model;
import com.example.login.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class userDAO {
    public static List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.\"utilisateur\" ORDER BY id_utilisateur ASC";

        try {
            Connection conn = new connection().getConnection();
            ResultSet rsUsers = conn.createStatement().executeQuery(selectStmt);
            List<User> userList = getUserList(rsUsers);
            conn.close();
            return userList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }
    private static List<User> getUserList(ResultSet rs) throws SQLException {
        List<User> userList = new ArrayList<>();

        while (rs.next()) {
            User user = new User();
            user.setIdUser(rs.getInt("id_utilisateur"));
            user.setIdentifiant(rs.getString("identifiant"));
            user.setMdp(rs.getString("mdp"));
            user.setProfil(rs.getString("profil"));
            userList.add(user);
        }

        return userList;
    }
    public static void insertUser(String identifiant, String mdp, String profil) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO public.\"utilisateur\" (identifiant, mdp, profil) VALUES (?, ?, ?)";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, identifiant);
            statement.setString(2, mdp);
            statement.setString(3, profil);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Un nouvel utilisateur a été inséré avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'insertion de l'utilisateur : " + e);
            throw e;
        }
    }
    // Méthode pour récupérer la fonction sélectionnée dans la base de donnée
    public static String getSelectedFonction(String identifiant) throws SQLException, ClassNotFoundException {
        String sql = "SELECT profil FROM public.\"utilisateur\" WHERE identifiant = ?";
        String fonction = null;

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, identifiant);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                fonction = rs.getString("profil");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la récupération de la fonction : " + e);
            throw e;
        }

        return fonction;
    }

    public static List<User> searchUser(int userId, String identifiant, String profil) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM public.\"utilisateur\" WHERE id_utilisateur = ? OR identifiant = ? OR profil = ?";
        List<User> userList = new ArrayList<>();

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setString(2, identifiant);
            statement.setString(3, profil);

            ResultSet rs = statement.executeQuery();
            userList = getUserList(rs);
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la recherche de l'utilisateur : " + e);
            throw e;
        }

        return userList;
    }
    public static void updateUser(int id, String identifiant, String mdp, String profil) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE public.\"utilisateur\" SET identifiant = ?, mdp = ?, profil = ? WHERE id_utilisateur = ?";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, identifiant);
            statement.setString(2, mdp);
            statement.setString(3, profil);
            statement.setInt(4, id);

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





