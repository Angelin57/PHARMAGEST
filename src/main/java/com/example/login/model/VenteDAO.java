package com.example.login.model;

import com.example.login.connection;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VenteDAO {
    public static Pair<List<Vente>, List<Medicament>> getAllVente() throws SQLException, ClassNotFoundException {
        String selectVenteStmt = "SELECT * FROM public.\"vente\" WHERE status = 'non envoyé' ORDER BY id_vente ASC";
        String selectMedStmt = "SELECT * FROM public.\"medicament\" ORDER BY id_medicament ASC";

        try {
            Connection conn = new connection().getConnection();
            ResultSet rsAppro = conn.createStatement().executeQuery(selectVenteStmt);
            ResultSet rsMed = conn.createStatement().executeQuery(selectMedStmt);

            Pair<List<Vente>, List<Medicament>> listsPair = getVenteMedLists(rsAppro, rsMed); // Passer les deux ResultSets à la méthode
            conn.close();
            return listsPair;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }


    public static Pair<List<Vente>, List<Medicament>> getVenteMedLists(ResultSet rsAppro, ResultSet rsMed) throws SQLException {
        List<Vente> approList = new ArrayList<>();
        List<Medicament> medList = new ArrayList<>();

        while (rsAppro.next()) {
            // Données d'approvisionnement
            Vente vente = new Vente();
            vente.setIdVente(rsAppro.getInt("id_vente"));
            vente.setNomMed(rsAppro.getString("nom_medicament"));
            vente.setPrixMed(rsAppro.getInt("prix_unit"));
            vente.setQttAcheteMed(rsAppro.getInt("quatite_achetee"));
            vente.setPrixTotal(rsAppro.getInt("prix_total"));
            vente.setStatus(rsAppro.getString("status"));

            approList.add(vente);
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

        return new Pair<>(approList, medList);
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
//            medicament.setQuantite_medicament(rs.getInt("quantite_medicament"));
//            medicament.setFournisseur_medicament(rs.getString("fournisseur_medicament"));
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
        String sql = "INSERT INTO public.\"vente\" (id_medicament, nom_medicament, quatite_achetee, client, medecin, prix_unit, prix_total) VALUES (?, ?, ?, ?, ?, ?, ?)";

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

    public static void envoiCaisse() throws SQLException, ClassNotFoundException {
        String sql = "UPDATE public.\"vente\"\n" +
                "SET status = 'envoyé', heure_creation = NOW()\n" +
                "WHERE status = 'non envoyé';\n";

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Envoi effectué !");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'envoi : " + e);
            throw e;
        }
    }





}
