package com.example.login.model;

import com.example.login.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListeCommandeDAO {
    public static List<ListeCommande> getAllCommande() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT approvisionnement.*, medicament.quantite_medicament, medicament.prix_medicament\n" +
                "FROM approvisionnement\n" +
                "INNER JOIN public.\"medicament\" ON approvisionnement.id_medicament = public.\"medicament\".id_medicament\n" +
                "ORDER BY public.\"medicament\".id_medicament ASC;\n";

        try {
            Connection conn = new connection().getConnection();
            ResultSet rsMedicament = conn.createStatement().executeQuery(selectStmt);
            List<ListeCommande> MedicamentList = getListe(rsMedicament);
            conn.close();
            return MedicamentList;
        } catch (SQLException e) {
            System.out.println("L'opération de sélection SQL a échoué : " + e);
            throw e;
        }
    }

    private static List<ListeCommande> getListe(ResultSet rs) throws SQLException {
        List<ListeCommande> medicamentList = new ArrayList<>();

        while (rs.next()) {
            ListeCommande listeCommande = new ListeCommande();

            // Récupération des données de la table ListeCommande
            listeCommande.setIdAppro(rs.getInt("id_approvisionnemet"));
            listeCommande.setNomMed(rs.getString("nom"));
            listeCommande.setDateRecue(rs.getDate("date_recue"));
            listeCommande.setPrixFournisseur(rs.getInt("prix_fournisseur"));
            listeCommande.setQttCommande(rs.getInt("quantite_commande"));
            listeCommande.setQttRecue(rs.getInt("quantite_recue"));
            listeCommande.setStatus(rs.getString("status"));
            listeCommande.setFournisseur(rs.getString("fournisseur"));

            // Récupération des données de la table Medicament

            listeCommande.setQttInitial(rs.getInt("quantite_medicament"));
            listeCommande.setPrixUnit(rs.getInt("prix_medicament"));

            // Ajout des objets ListeCommande et Medicament à la liste
          //  listeCommande.setMedicament(medicament);
            medicamentList.add(listeCommande);
        }

        return medicamentList;
    }

}
