package com.example.login.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.print.PrinterJob;
import com.example.login.model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaisseController {

    @FXML
    private Button annulerBtn;
    @FXML
    private Button validerBtn;

    @FXML
    private TableColumn<Facture, Timestamp> heureFactureColumn;

    @FXML
    private TableColumn<Facture, Integer> idFactureColumn;

    @FXML
    private TableColumn<Facture, LocalDate> dateFacture;

    @FXML
    private TableColumn<Facture, Integer> prixFactureColumn;

    @FXML
    private TableColumn<Facture, String> statutFactureColumn;

    @FXML
    private TableColumn<Vente, Integer> idMedCaisseColumn;  // Ajout de l'annotation @FXML
    @FXML
    private TableColumn<Vente, String> nomMedCaisseColumn;
    @FXML
    private TableColumn<Vente, Integer> qttMedCaisseColumn;
    @FXML
    private TableColumn<Vente, Integer> prixMedCaisseColumn;

    @FXML
    private TextField montantPayerText;

    @FXML
    private TextField montantRecuText;

    @FXML
    private TextField montantRendreText;

    @FXML
    private TextField idMedTxt;

    @FXML
    private TableView<Facture> tableFacture;
    @FXML
    private TableView<Vente> listMed;
    private ObservableList<Facture> listeFactures;
    private ObservableList<Vente> listeVentes;

    public void initialize() throws SQLException, ClassNotFoundException {
        try {
            List<Facture> facturesNonPayees = CaisseDAO.getAllFacturesNonPayees();
            listeFactures = FXCollections.observableArrayList(facturesNonPayees);

            // Configuration du TableView des factures
            idFactureColumn.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
            dateFacture.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
            heureFactureColumn.setCellValueFactory(new PropertyValueFactory<>("heureCreation"));
            prixFactureColumn.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
            statutFactureColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

            tableFacture.setItems(listeFactures);

            // Configuration du TableView des ventes
            idMedCaisseColumn.setCellValueFactory(new PropertyValueFactory<>("idVente"));
            nomMedCaisseColumn.setCellValueFactory(new PropertyValueFactory<>("nomMed"));
            qttMedCaisseColumn.setCellValueFactory(new PropertyValueFactory<>("qttAchete"));
            prixMedCaisseColumn.setCellValueFactory(new PropertyValueFactory<>("prixMed"));


            // Ajout d'un gestionnaire d'événement pour afficher les ventes lorsqu'une facture est sélectionnée
            tableFacture.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        afficherVentesFacture(newValue);
                        afficherMontantFacture(newValue);
                    }
            );
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void validerBtnOnAction(ActionEvent event) {
        Facture factureSel = tableFacture.getSelectionModel().getSelectedItem();
        if (factureSel != null) {
            try {
                List<Vente> ventesFacture = CaisseDAO.getVentesFacture(factureSel.getIdFacture());

                if (ventesFacture != null && !ventesFacture.isEmpty()) {
                    List<Integer> qttMeds = new ArrayList<>();
                    List<Integer> idMeds = new ArrayList<>();

                    for (Vente vente : ventesFacture) {
                        if (vente != null) {
                            qttMeds.add(vente.getQttAchete());
                            idMeds.add(vente.getIdMedicament());
                        }
                    }

                    if (!qttMeds.isEmpty() && !idMeds.isEmpty()) {
                        String montantRecuTextValue = this.montantRecuText.getText().trim();
                        if (!montantRecuTextValue.isEmpty()) {
                            int montantRecu = Integer.parseInt(montantRecuTextValue);
                            int montantPayer = factureSel.getMontantTotal();

                            if (montantRecu >= montantPayer) {
                                CaisseDAO.updateMed(qttMeds, idMeds);
                                CaisseDAO.updateStatutFacture(factureSel.getIdFacture());

                                listeFactures.remove(factureSel);

                                int montantRendre = montantRecu - montantPayer;
                                montantRendreText.setText(String.valueOf(montantRendre));

                                printFacture(montantPayer, montantRecu, montantRendre, FXCollections.observableArrayList(ventesFacture));

                                montantRendreText.clear();
                                montantPayerText.clear();
                                montantRecuText.clear();
                            } else {
                                System.out.println("Le montant reçu est insuffisant.");
                            }
                        } else {
                            System.out.println("Veuillez saisir un montant reçu.");
                        }
                    } else {
                        System.out.println("Les listes qttMeds, idMeds ou factureMeds sont vides ou contiennent des valeurs nulles.");
                    }
                } else {
                    System.out.println("La liste ventesFacture est nulle ou vide.");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        afficherMontantFacture(tableFacture.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void annulerBtnOnAction(ActionEvent event) {
        montantRendreText.clear();
        montantPayerText.clear();
        montantRecuText.clear();
    }

    private void afficherVentesFacture(Facture facture) {
        if (facture != null) {
            try {
                List<Vente> ventesFacture = CaisseDAO.getVentesFacture(facture.getIdFacture());
                listeVentes = FXCollections.observableArrayList(ventesFacture);
                listMed.setItems(listeVentes);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            listeVentes.clear();
            listMed.setItems(listeVentes);
        }
    }

    private void afficherMontantFacture(Facture facture) {
        if (facture != null) {
            montantPayerText.setText(String.valueOf(facture.getMontantTotal()));
        } else {
            montantPayerText.clear();
        }
    }

    private void printFacture(Integer montantPayer, Integer montantRecu, Integer montantRendre, ObservableList<Vente> medicamentsList) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null) {
            VBox contentPane = new VBox();
            contentPane.setAlignment(Pos.CENTER);

            Image logoImage = new Image("file:///C:/BTS/Année 1-2/Année 2/Yusuf/Gestion pharmacie/Pharma-master v3/src/main/image/Logo-Pharmacie-1.jpg");
            ImageView logoImageView = new ImageView(logoImage);
            logoImageView.setFitWidth(100);
            logoImageView.setPreserveRatio(true);

            contentPane.getChildren().add(logoImageView);
            Text titleText = new Text("\nFacture\n");
            titleText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
            contentPane.getChildren().add(titleText);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Text dateTimeText = new Text("Date et heure de création : " + dateFormat.format(new Date()) + "\n\n");
            contentPane.getChildren().add(dateTimeText);

            StringBuilder content = new StringBuilder();
            content.append("--------------------------------------------\n");
            content.append("Médicaments achetés:\n");
            for (Vente medicament : medicamentsList) {
                content.append("- ").append(medicament.getNomMed()).append(" (Quantité: ").append(medicament.getQttAchete()).append(", Prix unitaire: ").append(medicament.getPrixMed()).append(" Rs").append(")\n");
            }
            content.append("--------------------------------------------\n");
            content.append("Montant reçu: ").append(montantRecu).append(" Rs").append("\n");
            content.append("Montant à payer: ").append(montantPayer).append(" Rs").append("\n");
            content.append("--------------------------------------------\n");
            content.append("Montant à rendre: ").append(montantRendre).append(" Rs").append("\n\n");
            content.append("--------------------------------------------\n");

            Text text = new Text(content.toString());
            contentPane.getChildren().add(text);

            boolean printed = printerJob.printPage(contentPane);
            if (printed) {
                printerJob.endJob();
            } else {
                System.out.println("Erreur lors de l'impression de la facture.");
            }
        }
    }
}
