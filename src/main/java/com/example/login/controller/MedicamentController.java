package com.example.login.controller;

import com.example.login.model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class MedicamentController {
    @FXML
    private TextField nameText;
    @FXML
    private TextField quantiteText;
    @FXML
    private ChoiceBox<Fournisseur> FournisseurText;
    @FXML
    private ChoiceBox<Famille> familleText;
    @FXML
    private TextField newNom;
    @FXML
    private TextField newPrixunit;
    @FXML
    private TextField newQuantite;
    @FXML
    private Button addMedBtn;
    @FXML
    private Button deleteMedBtn;
    @FXML
    private AnchorPane medCrud;
    @FXML
    private TableColumn<Medicament, Integer> medIdColumn;
    @FXML
    private TableColumn<Medicament, String> medNameColumn;
    @FXML
    private TableColumn<Medicament, Integer> medPrixunitColumn;
    @FXML
    private TableColumn<Medicament, String> medQuantiteColumn;
    @FXML
    private TableColumn<Medicament, String> medFournisseurColumn;
    @FXML
    private TableColumn<Medicament, String> medFamilleColumn;
    @FXML
    private TextField medIdText;
    @FXML
    private TableView<Medicament> medicamentTable;
    @FXML
    private TextField prixunit;
    @FXML
    private TextArea resultArea;
    @FXML
    private Button searchMedsBtn;
    @FXML
    private Button searchMedtn;
    @FXML
    private Button updateMedBtn;
    private DataService dataService; // Vous devez créer cette classe pour accéder à la base de données

    @FXML
    private void initialize(){
        medIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_medicament()).asObject());
        medNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom_medicament()));
        medQuantiteColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantite_medicament()).asObject().asString());
        medFamilleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFamille_medicament()));
        medFournisseurColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFournisseur_medicament()));
        medPrixunitColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrix_medicament()).asObject());

        medicamentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int idMedicament = newValue.getId_medicament();
                String nom = newValue.getNom_medicament();
                Integer quantiteMedicament = newValue.getQuantite_medicament();
                String familleMedicament = newValue.getFamille_medicament();
                String fournisseurMedicament = newValue.getFournisseur_medicament();
                Integer prixMedicament = newValue.getPrix_medicament();

                medIdText.setText(String.valueOf(idMedicament));
                newNom.setText(nom);
                newQuantite.setText(String.valueOf(quantiteMedicament));
                newPrixunit.setText(String.valueOf(prixMedicament));
            }
        });
        // Initialisez votre service de données
        dataService = new DataService();

        dataService = new DataService();

        ObservableList<Fournisseur> fournisseurs = FXCollections.observableArrayList(dataService.getFournisseurs());
        ObservableList<Famille> familles = FXCollections.observableArrayList(dataService.getFamilles());

        FournisseurText.setItems(fournisseurs);
        familleText.setItems(familles);

        try {
            viewAllMedicament(null);
        } catch (Exception e) {
            showErrorMessage("Erreur lors de la récupération des medicaments au démarrage : " + e.getMessage());
        }
    }


    @FXML
    void deleteMedicament(ActionEvent event) {
        try {
            int id = Integer.parseInt(medIdText.getText());

            MedicamentDAO.deleteMedicament(id);
            // Rafraîchir le TableView après la suppression
            refreshTableView();
            resultArea.setText("Medicament supprimé avec succès !");
            medIdText.clear();
            newNom.clear();
            newPrixunit.clear();
            newQuantite.clear();

        } catch (SQLException e) {
            showErrorMessage("Erreur lors de la suppression du Medicament : " + e.getMessage());
            resultArea.setText("Erreur lors de la suppression du Medicament : " + e.getMessage());
        } catch (NumberFormatException e) {
            showErrorMessage("Veuillez saisir une valeur numérique valide pour l'ID du Medicament.");
            resultArea.setText("Veuillez saisir une valeur numérique valide pour l'ID du Medicament.");
        } catch (ClassNotFoundException e) {
            showErrorMessage("Classe non trouvée : " + e.getMessage());
            resultArea.setText("Classe non trouvée : " + e.getMessage());
        }

    }

    @FXML
    void insertMedicament(ActionEvent event) throws SQLException, ClassNotFoundException{
        String nom = nameText.getText();
        String Quantite = String.valueOf(Integer.valueOf(quantiteText.getText()));
        Integer Prix = Integer.valueOf(prixunit.getText());
        String Famille = String.valueOf(familleText.getValue());
        String Fournisseur = String.valueOf(FournisseurText.getValue());

        if (!nom.isEmpty() && Quantite != null && Prix != null && Famille !=  null && Fournisseur != null) {
            try {
                userDAO.insertUser(nom, Quantite, String.valueOf(Prix));
                refreshTableView(); // Méthode pour actualiser la TableView
                nameText.clear();
                quantiteText.clear();
                prixunit.clear();
                familleText.setValue(null);
                FournisseurText.setValue(null);
                resultArea.setText("Utilisateur ajouté avec succès !");
            } catch (SQLException | ClassNotFoundException e) {
                resultArea.setText("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            resultArea.setText("Veuillez remplir tous les champs.");
        }
    }


    @FXML
    void searchMedicament(ActionEvent event) {

    }

    @FXML
    void viewAllMedicament(ActionEvent event) {

    }

    @FXML
    void updatePrix(ActionEvent event) {

    }
    private void refreshTableView() throws SQLException, ClassNotFoundException {
        viewAllMedicament(null);
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
