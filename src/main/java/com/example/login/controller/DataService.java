package com.example.login.controller;

import com.example.login.model.Famille;
import com.example.login.model.Fournisseur;
import com.example.login.model.Medicament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    // Méthode pour récupérer les fournisseurs depuis la base de données
    public List<Fournisseur> getFournisseurs() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String query = "SELECT * FROM fournisseur"; // Remplacez "fournisseurs" par le nom de votre table
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                // Récupérez les données du résultat de la requête et créez un objet Medicament pour chaque fournisseur
                int idFournisseur = resultSet.getInt("id_fournisseur");
                String nomFournisseur = resultSet.getString("nom_fournisseur");
                Fournisseur fournisseur = new Fournisseur(idFournisseur, nomFournisseur);
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur appropriée selon votre application
        }
        return fournisseurs;
    }

    // Méthode pour récupérer les familles depuis la base de données
    public List<Famille> getFamilles() {
        List<Famille> familles = new ArrayList<>();
        String query = "SELECT * FROM famille"; // Remplacez "familles" par le nom de votre table
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                // Récupérez les données du résultat de la requête et créez un objet Medicament pour chaque famille
                int idFamille = resultSet.getInt("id_famille");
                String nomFamille = resultSet.getString("nom_famille");
                Famille famille = new Famille(idFamille, nomFamille);
                familles.add(famille);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur appropriée selon votre application
        }
        return familles;
    }

    // Méthode pour établir la connexion à la base de données
    private Connection getConnection() throws SQLException {
        // Remplacez les paramètres par vos informations de connexion à la base de données
        String url = "jdbc:postgresql://localhost:5432/pharmagest";
        String user = "postgres";
        String password = "5751";
        return DriverManager.getConnection(url, user, password);
    }
}
