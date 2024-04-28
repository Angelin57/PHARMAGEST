package com.example.login.controller;

import com.example.login.model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import java.io.File;

import static com.example.login.model.CaisseDAO.updateMed;

public class CaisseController {
    @FXML
    private Button annulerBtn;


    @FXML
    private TableColumn<Caisse, Timestamp> heureFactureColumn;

    @FXML
    private TableColumn<Caisse, Integer> idFactureColumn;

    @FXML
    private TableColumn<Caisse, Integer> idMedCaisseColumn;

    @FXML
    private TableColumn<Caisse,String> nomMedCaisseColumn;
    @FXML
    private TableColumn<Caisse, Integer> nbVente;

    @FXML
    private TableColumn<Caisse, Integer> prixFactureColumn;

    @FXML
    private TableColumn<Caisse, Integer> prixMedCaisseColumn;

    @FXML
    private TableColumn<Caisse, Integer> qttMedCaisseColumn;

    @FXML
    private TableColumn<Caisse, String> statutFactureColumn;
    @FXML
    private TextField montantPayerText;

    @FXML
    private TextField montantRecuText;

    @FXML
    private TextField montantRendreText;

    @FXML
    private TextField idMedTxt;
    @FXML
    private TextField qttMedText;
    @FXML
    private Button validerBtn;

    @FXML
    private TableView<Caisse> tableFacture;
    @FXML
    private TableView<Caisse> listMed;


    public void initialize() throws SQLException, ClassNotFoundException {
        // Appel à la méthode getAllFacture de CaisseDAO pour récupérer les données
        List<Caisse> factureList = CaisseDAO.getAllFacture();

        // Création d'une ObservableList pour stocker les données récupérées
        ObservableList<Caisse> factureObservableList = FXCollections.observableArrayList(factureList);

        // Liaison des données aux colonnes appropriées dans votre interface utilisateur graphique

        // Calcul du nombre de ventes et du prix total des ventes pour chaque heure et affichage dans les colonnes appropriées
        nbVente.setCellValueFactory(new PropertyValueFactory<>("nombreVentes"));
        prixFactureColumn.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        heureFactureColumn.setCellValueFactory(new PropertyValueFactory<>("heureCreation"));
        statutFactureColumn.setCellValueFactory(new PropertyValueFactory<>("Facture"));

        tableFacture.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Integer MontantApayer = newValue.getPrixTotal();
                montantPayerText.setText(String.valueOf(MontantApayer));
                idMedCaisseColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdMed()).asObject());
                nomMedCaisseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomMed()));
                qttMedCaisseColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQtt()).asObject());
                prixMedCaisseColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrix_unit()).asObject());

                try {
                    montantRendreText.clear();
                    montantRecuText.clear();
                    chargerMedicaments(newValue);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        // Affichage des données dans votre table
        tableFacture.setItems(factureObservableList);
    }

    private void chargerMedicaments(Caisse facture) throws SQLException, ClassNotFoundException {
        List<Caisse> medicamentsList = CaisseDAO.getMedicamentsForFacture(facture); // Nouvelle méthode dans la DAO
        ObservableList<Caisse> medicamentsObservableList = FXCollections.observableArrayList(medicamentsList);
        listMed.setItems(medicamentsObservableList);
    }

    @FXML
    void validerBtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            Integer montantRecu = Integer.valueOf(montantRecuText.getText());
            Integer montantPayer = Integer.valueOf(montantPayerText.getText());
            Integer montantRendre = montantRecu - montantPayer;

            montantRendreText.setText(String.valueOf(montantRendre));

            // Déclaration des variables pour stocker les valeurs des colonnes
            List<Integer> idMeds = new ArrayList<>();
            List<Integer> qttMeds = new ArrayList<>();
            List<String> factureMeds = new ArrayList<>();

            for (Caisse obj : listMed.getItems()) {
                // Obtenir les valeurs de chaque colonne pour l'élément actuel
                Integer idMed = obj.getIdMed();
                Integer qttMed = obj.getQtt();
                String factureMed = obj.getFacture();

                // Stocker les valeurs dans les variables déclarées
                idMeds.add(idMed);
                qttMeds.add(qttMed);
                factureMeds.add(factureMed);
            }
            CaisseDAO.updateMed(qttMeds,idMeds,factureMeds);
            refreshTableView();


        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer des montants valides.");
        }
    }

    @FXML
    void viewAllFacture(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            // Récupérer les données des factures depuis la base de données
            List<Caisse> factureList = CaisseDAO.getAllFacture();

            // Convertir la liste en ObservableList
            ObservableList<Caisse> factureObservableList = FXCollections.observableArrayList();

            // Afficher les données dans la table
            tableFacture.setItems(factureObservableList);

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des factures : " + e);
            throw e;
        }
    }
    private void refreshTableView() throws SQLException, ClassNotFoundException {
        viewAllFacture(null);
    }


    //    @FXML
//    void imprimerBtnOnAction(ActionEvent event) {
//        // Collecte des informations de la facture
//        String heureCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        Integer montantTotal = Integer.valueOf(montantPayerText.getText());
//        Integer montantRecu = Integer.valueOf(montantRecuText.getText());
//        Integer montantRendre = Integer.valueOf(montantRendreText.getText());
//        ObservableList<Caisse> medicaments = listMed.getItems();
//
//        // Création d'un nouveau document PDF
//        try (PDDocument document = new PDDocument()) {
//            PDPage page = new PDPage();
//            document.addPage(page);
//
//            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//                contentStream.setFont(PDType1Font.COURIER_BOLD, 12);
//                contentStream.beginText();
//                contentStream.newLineAtOffset(100, 700);
//                contentStream.showText("Facture");
//                contentStream.newLineAtOffset(0, -20);
//                contentStream.showText("Date et heure de création de la facture : " + heureCreation);
//                contentStream.newLineAtOffset(0, -20);
//                contentStream.showText("Montant total : " + montantTotal);
//                contentStream.newLineAtOffset(0, -20);
//                contentStream.showText("Montant reçu : " + montantRecu);
//                contentStream.newLineAtOffset(0, -20);
//                contentStream.showText("Montant à rendre : " + montantRendre);
//                contentStream.newLineAtOffset(0, -20);
//
//                // Insérer les détails des médicaments
//                contentStream.newLineAtOffset(0, -20);
//                contentStream.setFont(PDType1Font.HELVETICA, 10);
//                contentStream.showText("Détails des médicaments :");
//                contentStream.newLineAtOffset(0, -20);
//                for (Caisse medicament : medicaments) {
//                    contentStream.showText("Médicament : " + medicament.getNomMed() + ", Quantité : " + medicament.getQtt() + ", Prix unitaire : " + medicament.getPrix_unit());
//                    contentStream.newLineAtOffset(0, -15);
//                }
//
//                contentStream.endText();
//            }
//
//            // Enregistrement du fichier PDF
//            File file = new File("facture.pdf");
//            document.save(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @FXML
    void annulerBtnOnAction(ActionEvent event) {
        montantRendreText.clear();
        montantPayerText.clear();
        montantRecuText.clear();

    }


}
