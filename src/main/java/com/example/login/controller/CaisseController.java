package com.example.login.controller;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javafx.print.PrinterJob;
import com.example.login.model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;

import javafx.util.Pair;

import javax.swing.text.Element;
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

            // Mettre à jour la base de données avec les médicaments vendus
            CaisseDAO.updateMed(qttMeds, idMeds, factureMeds);

            // Imprimer la facture
            printFacture(montantPayer, montantRecu, montantRendre, listMed.getItems());

            // Rafraîchir la vue
            refreshTableView();
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer des montants valides.");
        }
    }

    private void printFacture(Integer montantPayer, Integer montantRecu, Integer montantRendre, ObservableList<Caisse> medicamentsList) {
        // Créer une instance de PrinterJob
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null) {
            // Créer le contenu de la facture
            VBox contentPane = new VBox(); // Utiliser VBox pour organiser le contenu verticalement
            contentPane.setAlignment(Pos.CENTER); // Centrer le contenu

            // Ajouter l'image en haut de la facture
            Image logoImage = new Image("file:///C:/BTS/Année 1-2/Année 2/Yusuf/Gestion pharmacie/Pharma-master v3/src/main/image/Logo-Pharmacie-1.jpg");
            ImageView logoImageView = new ImageView(logoImage);
            logoImageView.setFitWidth(100); // Réglez la largeur de l'image selon vos besoins
            logoImageView.setPreserveRatio(true); // Conserver les proportions de l'image

            // Ajouter le logo et le titre "Facture" à la VBox
            contentPane.getChildren().add(logoImageView);
            Text titleText = new Text("\nFacture\n");
            titleText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;"); // Modifier le style du titre
            contentPane.getChildren().add(titleText);

            // Afficher l'heure et la date de création de la facture
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Text dateTimeText = new Text("Date et heure de création : " + dateFormat.format(new Date()) + "\n\n");
            contentPane.getChildren().add(dateTimeText);

            // Ajouter le reste du contenu de la facture
            StringBuilder content = new StringBuilder();
            content.append("--------------------------------------------\n");
            content.append("Médicaments achetés:\n");
            for (Caisse medicament : medicamentsList) {
                content.append("- ").append(medicament.getNomMed()).append(" (Quantité: ").append(medicament.getQtt()).append(", Prix unitaire: ").append(medicament.getPrix_unit()).append(" Rs").append(")\n");
            }
            content.append("--------------------------------------------\n");
            content.append("Montant reçu: ").append(montantRecu).append(" Rs").append("\n");
            content.append("Montant à payer: ").append(montantPayer).append(" Rs").append("\n");
            content.append("--------------------------------------------\n");
            content.append("Montant à rendre: ").append(montantRendre).append(" Rs").append("\n\n");
            content.append("--------------------------------------------\n");


            Text text = new Text(content.toString());
            contentPane.getChildren().add(text);

            // Imprimer le contenu
            boolean printed = printerJob.printPage(contentPane);
            if (printed) {
                printerJob.endJob();
            } else {
                System.out.println("Erreur lors de l'impression de la facture.");
            }
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



    @FXML
    void annulerBtnOnAction(ActionEvent event) {
        montantRendreText.clear();
        montantPayerText.clear();
        montantRecuText.clear();

    }
}
