package com.example.login.model;

import com.example.login.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaisseDAO {

    public static List<Facture> getAllFacturesNonPayees() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM facture WHERE statut = 'non payée' ORDER BY date DESC";
        List<Facture> listeFactures = new ArrayList<>();

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Facture facture = new Facture();
                facture.setIdFacture(rs.getInt("id_facture"));
                facture.setDateCreation(rs.getDate("date").toLocalDate());
                facture.setHeureCreation(rs.getTime("heure").toLocalTime());
                facture.setMontantTotal(rs.getInt("montant_total"));
                facture.setStatut(rs.getString("statut"));
                facture.setOrdonnance(rs.getBoolean("ordonnance"));

                listeFactures.add(facture);
            }

        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la récupération des factures non payées : " + e);
            throw e;
        }

        return listeFactures;
    }

    public static List<Vente> getVentesFacture(int idFacture) throws SQLException, ClassNotFoundException {
        String sql = "SELECT v.* FROM vente v JOIN facture_vente fv ON v.id_vente = fv.vente_id WHERE fv.facture_id = ?";
        List<Vente> listeVentes = new ArrayList<>();

        try (Connection conn = new connection().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idFacture); // Définir la valeur du paramètre pour idFacture
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Vente vente = new Vente();
                vente.setIdVente(rs.getInt("id_vente"));
                vente.setNomMedicament(rs.getString("nom_medicament"));
                vente.setPrixMedicament(rs.getInt("prix_unit"));
                vente.setQttAchete(rs.getInt("quantite_achetee"));
                vente.setPrixTotal(rs.getInt("prix_total"));
                vente.setStatut(rs.getString("statut"));
                vente.setIdMedicament(rs.getInt("id_medicament"));
                listeVentes.add(vente);
            }

        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la récupération des ventes par facture : " + e);
            throw e;
        }

        return listeVentes;
    }

    public static  void updateMed(List<Integer> qttMeds, List<Integer> idMeds)  throws SQLException, ClassNotFoundException {
        String updateQuantiteStmt = "UPDATE medicament SET quantite_medicament = quantite_medicament - ? WHERE id_medicament = ?";

        Connection conn = null;
        PreparedStatement quantitePreparedStatement = null;


        try {
            conn = new connection().getConnection();
            quantitePreparedStatement = conn.prepareStatement(updateQuantiteStmt);


            for (int i = 0; i < qttMeds.size(); i++) {
                int idMed = idMeds.get(i); // Récupérer l'ID du médicament

                // Mettre à jour la quantité du médicament
                quantitePreparedStatement.setInt(1, qttMeds.get(i));
                quantitePreparedStatement.setInt(2, idMed);
                quantitePreparedStatement.executeUpdate();


            }
        } catch (SQLException e) {
            System.out.println("L'opération de mise à jour SQL a échoué : " + e);
            throw e;
        } finally {
            if (quantitePreparedStatement != null) {
                quantitePreparedStatement.close();


            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void updateStatutFacture(int idFacture) throws SQLException, ClassNotFoundException {
        String updateStatutFactureStmt = "UPDATE facture SET statut = 'payée' WHERE id_facture = ?";

        Connection conn = null;
        PreparedStatement statutFacturePreparedStatement = null;

        try {
            conn = new connection().getConnection();
            statutFacturePreparedStatement = conn.prepareStatement(updateStatutFactureStmt);

            // Mettre à jour le statut de la facture
            statutFacturePreparedStatement.setInt(1, idFacture);
            statutFacturePreparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("L'opération de mise à jour du statut de la facture a échoué : " + e);
            throw e;
        } finally {
            if (statutFacturePreparedStatement != null) {
                statutFacturePreparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


}


