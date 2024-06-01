package com.example.login.model;

import com.example.login.connection;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApprovisonnementDAO {
    public static Pair<List<Approvisionnement>, List<Medicament>> getAllApprovisionnemet() throws SQLException, ClassNotFoundException {
        String selectApproStmt = "SELECT * FROM public.\"approvisionnement\" ORDER BY id_approvisionnement ASC";
        String selectMedStmt = "SELECT * FROM public.\"medicament\" WHERE quantite_medicament <= 30 ORDER BY id_medicament ASC";

        try {
            Connection conn = new connection().getConnection();
            ResultSet rsAppro = conn.createStatement().executeQuery(selectApproStmt);
            ResultSet rsMed = conn.createStatement().executeQuery(selectMedStmt);

            Pair<List<Approvisionnement>, List<Medicament>> listsPair = getApproMedLists(rsAppro, rsMed); // Passer les deux ResultSets à la méthode
            conn.close();
            return listsPair;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }


    public static Pair<List<Approvisionnement>, List<Medicament>> getApproMedLists(ResultSet rsAppro, ResultSet rsMed) throws SQLException {
        List<Approvisionnement> approList = new ArrayList<>();
        List<Medicament> medList = new ArrayList<>();

        while (rsAppro.next()) {
            // Données d'approvisionnement
            Approvisionnement approvisionnement = new Approvisionnement();
            approvisionnement.setIdApprovisonnement(rsAppro.getInt("id_approvisionnement"));
            approvisionnement.setNom(rsAppro.getString("nom"));
            approvisionnement.setDateRecue(rsAppro.getDate("date_recue"));
            approvisionnement.setPrixFournisseur(rsAppro.getInt("prix_fournisseur"));
            approvisionnement.setQuantiteCommande(rsAppro.getInt("quantite_commande"));
            approvisionnement.setQuantiteRecue(rsAppro.getInt("quantite_recue"));
            approvisionnement.setStatus(rsAppro.getString("status"));
            approvisionnement.setFournisseur(rsAppro.getString("fournisseur"));
            approvisionnement.setId_med(rsAppro.getInt("id_medicament"));
            approList.add(approvisionnement);
        }

        while (rsMed.next()) {
            // Données de médicament
            Medicament medicament = new Medicament();
            medicament.setId_medicament(rsMed.getInt("id_medicament"));
            medicament.setNom_medicament(rsMed.getString("nom_medicament"));
            medicament.setQuantite_medicament(rsMed.getInt("quantite_medicament"));

            medList.add(medicament);
        }

        return new Pair<>(approList, medList);
    }




    public static void insertAppro( Medicament nomMed, String status, int prix, int qttCommande, int qttLivre, Date dateRecue, Fournisseur fournisseur,Medicament id_med) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO public.\"approvisionnement\" (nom,status, prix_fournisseur, quantite_commande, quantite_recue,date_recue,fournisseur,id_medicament) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nomMed.getNom_medicament() );
            statement.setString(2, status);
            statement.setInt(3, prix);
            statement.setInt(4, qttCommande);
            statement.setInt(5, qttLivre);
            statement.setDate(6, new Date(dateRecue.getTime()));
            statement.setString(7, fournisseur.getNom());
            statement.setInt(8, id_med.getId_medicament() );



            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Un nouvel approvisionnement a été inséré avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'insertion de approvisionnement : " + e);
            throw e;
        }
    }
    public static void updateApproAndMed(String status, int qttRecue, String medName) throws SQLException, ClassNotFoundException {
        String sqlAppro = "UPDATE public.\"approvisionnement\" SET status = ?, quantite_recue = ? WHERE nom = ?";
        String sqlMed = "UPDATE public.\"medicament\" SET quantite_medicament = quantite_medicament + ? WHERE nom_medicament = ?";
        Connection conn = null;
        PreparedStatement statementAppro = null;
        PreparedStatement statementMed = null;
        try {
            conn = new connection().getConnection();
            conn.setAutoCommit(false); // Début de la transaction
            // Vérifier si le statut est "Terminer" (ignorer la casse)
            if ("Terminer".equalsIgnoreCase(status.trim())) {
                // Mise à jour de la table "medicament" si le statut est "Terminer"
                statementMed = conn.prepareStatement(sqlMed);
                statementMed.setInt(1, qttRecue);
                statementMed.setString(2, medName);

                statementMed.executeUpdate();
            }
            // Mise à jour du statut de l'approvisionnement dans tous les cas
            statementAppro = conn.prepareStatement(sqlAppro);
            statementAppro.setString(1, status);
            statementAppro.setInt(2, qttRecue);
            statementAppro.setString(3, medName);
            statementAppro.executeUpdate();

            // Validation de la transaction
            conn.commit();
            System.out.println("Mise à jour réussie !");
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Annulation de la transaction en cas d'erreur
            }
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); // Rétablissement du mode de commit automatique
                conn.close();
            }
            if (statementAppro != null) {
                statementAppro.close();
            }
            if (statementMed != null) {
                statementMed.close();
            }
        }
    }
}
