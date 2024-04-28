package com.example.login.model;

import com.example.login.connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map;
import java.util.HashMap;
public class CaisseDAO {
    public static List<Caisse> getAllFacture() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT \n" +
                "    heure_creation,\n" +
                "    facture,\n" +
                "    COUNT(*) AS nombre_ventes,\n" +
                "    SUM(prix_total) AS prix_total\n" +
                "FROM \n" +
                "    vente\n" +
                "WHERE \n" +
                "    status = 'envoyé'\n" +
                "AND facture = 'non payé'\n"+
                "GROUP BY \n" +
                "    heure_creation,\n" +
                "    facture;\n";

        try {
            Connection conn = new connection().getConnection();
            ResultSet rs = conn.createStatement().executeQuery(selectStmt);
            List<Caisse> resultList = new ArrayList<>();

            while (rs.next()) {
                Timestamp heureCreation = rs.getTimestamp("heure_creation");
                int nombreVentes = rs.getInt("nombre_ventes");
                int prixTotal = rs.getInt("prix_total");
                String Facture = rs.getString("facture");

                Caisse resultatVente = new Caisse();
                resultatVente.setHeureCreation(heureCreation);
                resultatVente.setNombreVentes(nombreVentes);
                resultatVente.setPrixTotal(prixTotal);
                resultatVente.setFacture(Facture);

                resultList.add(resultatVente);
            }

            conn.close();
            return resultList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }

    public static List<Caisse> getMedicamentsForFacture(Caisse heure_creation) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT id_medicament, nom_medicament, quatite_achetee, prix_unit FROM vente WHERE heure_creation = ?";
        try {
            Connection conn = new connection().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(selectStmt);
            preparedStatement.setTimestamp(1, heure_creation.getHeureCreation());
            ResultSet rs = preparedStatement.executeQuery();
            List<Caisse> medicamentsList = new ArrayList<>();
            while (rs.next()) {
                // Ajoutez des détails supplémentaires si nécessaire pour créer un objet de type Caisse pour chaque médicament
                // Par exemple:
                Caisse caisse = new Caisse();
                caisse.setIdMed(rs.getInt("id_medicament"));
                caisse.setNomMed(rs.getString("nom_medicament"));
                caisse.setQtt(rs.getInt("quatite_achetee"));
                caisse.setPrix_unit(rs.getInt("prix_unit"));
                medicamentsList.add(caisse);
            }
            conn.close();
            return medicamentsList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }

    public static void updateMed(List<Integer> qttMeds, List<Integer> idMeds, List<String> factureMeds) throws SQLException, ClassNotFoundException {
        String updateQuantiteStmt = "UPDATE medicament SET quantite_medicament = quantite_medicament - ? WHERE id_medicament = ?";
        String updateFactureStmt = "UPDATE vente SET facture = 'payé' WHERE id_medicament = ?";

        Connection conn = null;
        PreparedStatement quantitePreparedStatement = null;
        PreparedStatement facturePreparedStatement = null;

        try {
            conn = new connection().getConnection(); // Assurez-vous que votre classe de connexion est correctement implémentée

            // Préparation des deux requêtes
            quantitePreparedStatement = conn.prepareStatement(updateQuantiteStmt);
            facturePreparedStatement = conn.prepareStatement(updateFactureStmt);

            // Boucle à travers les listes de quantités et d'IDs pour mettre à jour chaque médicament
            for (int i = 0; i < qttMeds.size(); i++) {
                // Mise à jour de la quantité
                quantitePreparedStatement.setInt(1, qttMeds.get(i));
                quantitePreparedStatement.setInt(2, idMeds.get(i));
                quantitePreparedStatement.executeUpdate();

                // Mise à jour de la facture
                facturePreparedStatement.setInt(1, idMeds.get(i));
                facturePreparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("L'opération de mise à jour SQL a échoué : " + e);
            throw e;
        } finally {
            if (quantitePreparedStatement != null) {
                quantitePreparedStatement.close();
            }
            if (facturePreparedStatement != null) {
                facturePreparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }




}
