package com.example.login.model;

import com.example.login.connection;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenteDAO {
    public static Pair<List<Vente>, List<Medicament>> getAllVente() throws SQLException, ClassNotFoundException {
        String selectVenteStmt = "SELECT * FROM public.vente WHERE statut = 'non envoyé' ORDER BY id_vente DESC";
        String selectMedStmt = "SELECT * FROM public.medicament ORDER BY id_medicament ASC";

        try {
            Connection conn = new connection().getConnection();
            ResultSet rsVente = conn.createStatement().executeQuery(selectVenteStmt);
            ResultSet rsMed = conn.createStatement().executeQuery(selectMedStmt);

            Pair<List<Vente>, List<Medicament>> listsPair = getVenteMedList(rsVente, rsMed); // Passer les deux ResultSets à la méthode
            conn.close();
            return listsPair;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }


    public static Pair<List<Vente>, List<Medicament>> getVenteMedList(ResultSet rsVente, ResultSet rsMed) throws SQLException {
        List<Vente> venteList = new ArrayList<>();
        List<Medicament> medList = new ArrayList<>();

        while (rsVente.next()) {
            // Données de vente
            Vente vente = new Vente();
            vente.setIdVente(rsVente.getInt("id_vente"));
            vente.setNomMedicament(rsVente.getString("nom_medicament"));
            vente.setPrixMedicament(rsVente.getInt("prix_unit"));
            vente.setQttAchete(rsVente.getInt("quantite_achetee"));
            vente.setPrixTotal(rsVente.getInt("prix_total"));
            vente.setStatut(rsVente.getString("statut"));

            venteList.add(vente);
        }


        while (rsMed.next()) {
            // Données de médicament
            Medicament medicament = new Medicament();
            medicament.setId_medicament(rsMed.getInt("id_medicament"));
            medicament.setNom_medicament(rsMed.getString("nom_medicament"));
            medicament.setFamille_medicament(rsMed.getString("famille_medicament"));
            medicament.setPrix_medicament(rsMed.getInt("prix_medicament"));


            medList.add(medicament);
        }

        return new Pair<>(venteList, medList);
    }

    public static List<Medicament> searchMedicament(String nom) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM public.\"medicament\" WHERE nom_medicament LIKE ? OR famille_medicament LIKE ?";
        List<Medicament> medicamentList = new ArrayList<>();

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Ajout de '%' autour du paramètre de recherche
            String nomSearch = "%" + nom + "%";

            statement.setString(1, nomSearch);
            statement.setString(2, nomSearch); // Pour rechercher également par famille de médicament

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Medicament medicament = new Medicament();
                medicament.setId_medicament(rs.getInt("id_medicament"));
                medicament.setNom_medicament(rs.getString("nom_medicament"));
                medicament.setFamille_medicament(rs.getString("famille_medicament"));
                medicament.setPrix_medicament(rs.getInt("prix_medicament"));
                // Ajoutez d'autres attributs si nécessaire
                medicamentList.add(medicament);
            }

        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la recherche du médicament : " + e);
            throw e;
        }
        return medicamentList;
    }

    public static void insertVente(int id, String nom, int qtt_achete, String client, String medecin, int prixUnit) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO public.\"vente\" (id_medicament, nom_medicament, quantite_achetee, client, medecin, prix_unit, prix_total) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Calcul du prix total
        int prixTotal = prixUnit * qtt_achete;

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, nom);
            statement.setInt(3, qtt_achete);
            statement.setString(4, client);
            statement.setString(5, medecin);
            statement.setInt(6, prixUnit);
            statement.setInt(7, prixTotal);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Une nouvelle vente a été insérée avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'insertion de la vente : " + e);
            throw e;
        }
    }

    public static void cancelvente(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM public.\"vente\" WHERE id_vente = ?";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("vente supprimé avec succès !");
            } else {
                throw new SQLException("La suppression du vente a échoué, aucun enregistrement supprimé.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la suppression du fournisseur : " + e.getMessage());
            throw e;
        }
    }

    public static void envoiCaisse(String nom, int prixUnit, int quantite, Famille famille, Fournisseur fournisseur) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO public.\"medicament\" (nom_medicament, fournisseur_medicament, famille_medicament, prix_medicament, quantite_medicament) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, nom);
            statement.setString(2, fournisseur.getNom()); // Utilise l'identifiant du fournisseur
            statement.setString(3, famille.getNom()); // Utilise l'identifiant de la famille
            statement.setInt(4, prixUnit);
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


    public static void insertFactureEtVentes(Facture facture) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement insertFactureStmt = null;
        PreparedStatement insertAssociationStmt = null;

        try {
            // Obtenir la connexion à la base de données
            conn = new connection().getConnection();
            conn.setAutoCommit(false);

            int montantTotal = 0; // Initialisation du montant total
            boolean ordonnance = false; // Initialisation de l'ordonnance à false

            // Parcourir les ventes pour déterminer si l'ordonnance est requise
            for (Vente vente : facture.getVentes()) {
                boolean clientExiste = vente.getNomClient() != null && !vente.getNomClient().isEmpty();
                boolean medecinExiste = vente.getNomMedecin() != null && !vente.getNomMedecin().isEmpty();

                // Si une vente a un client et un médecin, l'ordonnance est requise
                if (clientExiste && medecinExiste) {
                    ordonnance = true;
                    break; // Sortir de la boucle dès qu'une vente nécessite une ordonnance
                }

                // Calculer le montant total de la facture
                montantTotal += vente.getPrixTotal();
            }

            // Mettre à jour le montant total dans la facture
            facture.setMontantTotal(montantTotal);
            facture.setOrdonnance(ordonnance); // Mettre à jour l'ordonnance dans la facture

            // Insérer la facture avec la bonne valeur pour "ordonnance"
            String insertFactureSQL = "INSERT INTO facture (date, heure, montant_total, statut, ordonnance) VALUES (?, ?, ?, ?, ?)";
            insertFactureStmt = conn.prepareStatement(insertFactureSQL, Statement.RETURN_GENERATED_KEYS);
            insertFactureStmt.setDate(1, Date.valueOf(facture.getDateCreation()));
            insertFactureStmt.setTime(2, Time.valueOf(facture.getHeureCreation()));
            insertFactureStmt.setInt(3, facture.getMontantTotal());
            insertFactureStmt.setString(4, facture.getStatut());
            insertFactureStmt.setBoolean(5, facture.getOrdonnance());
            insertFactureStmt.executeUpdate();

            // Récupérer l'ID de la nouvelle facture
            ResultSet generatedKeys = insertFactureStmt.getGeneratedKeys();
            int nouvelleFactureId = generatedKeys.next() ? generatedKeys.getInt(1) : -1;

            // Insérer les associations entre facture et ventes
            String insertAssociationSQL = "INSERT INTO facture_vente (facture_id, vente_id) VALUES (?, ?)";
            insertAssociationStmt = conn.prepareStatement(insertAssociationSQL);

            for (Vente vente : facture.getVentes()) {
                insertAssociationStmt.setInt(1, nouvelleFactureId); // ID de la facture créée
                insertAssociationStmt.setInt(2, vente.getIdVente()); // ID de la vente
                insertAssociationStmt.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Revenir en cas d'erreur
            }
            throw e; // Relancer l'exception
        } finally {
            // Fermer les ressources
            if (insertFactureStmt != null) insertFactureStmt.close();
            if (insertAssociationStmt != null) insertAssociationStmt.close();
            if (conn != null) conn.close();
        }
    }


    public static void updateVente(Vente vente) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Obtenir la connexion à la base de données
            conn = new connection().getConnection();

            // Requête SQL pour mettre à jour la vente
            String updateSQL = "UPDATE vente SET statut = ? WHERE statut = 'non envoyé'" ;
            stmt = conn.prepareStatement(updateSQL);

            // Définir les valeurs pour le statut et l'identifiant de la facture
            stmt.setString(1, "envoyé");  // Mettre le statut à "envoyé"

            stmt.executeUpdate();  // Exécuter la mise à jour

        } finally {
            // Fermer les ressources pour éviter les fuites de mémoire
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
