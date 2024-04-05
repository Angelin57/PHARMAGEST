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
import java.util.List;

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
    void insertMedicament(ActionEvent event) {
        String nameValue = nameText.getText();
        int quantiteValue;
        int prixUnitValue;
        Famille familleValue;
        Fournisseur fournisseurValue;

        try {
            quantiteValue = Integer.parseInt(quantiteText.getText());
            prixUnitValue = Integer.parseInt(prixunit.getText());
            familleValue = familleText.getValue();
            fournisseurValue = FournisseurText.getValue();
        } catch (NumberFormatException e) {
            resultArea.setText("Erreur lors de la conversion des valeurs : " + e.getMessage());
            return;
        }

        if (!nameValue.isEmpty() && familleValue != null && fournisseurValue != null) {
            try {
                MedicamentDAO.insertMedicament(nameValue, quantiteValue, prixUnitValue, familleValue, fournisseurValue);
                refreshTableView();
                nameText.clear();
                quantiteText.clear();
                prixunit.clear();
                familleText.setValue(null);
                FournisseurText.setValue(null);
                resultArea.setText("Médicament ajouté avec succès !");
            } catch (SQLException | ClassNotFoundException e) {
                resultArea.setText("Erreur lors de l'ajout du Médicament : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            resultArea.setText("Veuillez remplir tous les champs.");
        }
    }


    @FXML
    void searchMedicament(ActionEvent event) throws SQLException,ClassNotFoundException{
        try {
            int medId = medIdText.getText().isEmpty() ? -1 : Integer.parseInt(medIdText.getText());
            String medNom = newNom.getText().isEmpty() ? "" : newNom.getText();
            Integer medQuantite = newQuantite.getText().isEmpty() ? -1 : Integer.parseInt(newQuantite.getText());
            Integer medPrix = newPrixunit.getText().isEmpty() ? -1 : Integer.parseInt(newPrixunit.getText());


            // Appelez la méthode de recherche en fonction des paramètres saisis
            List<Medicament> medicaments = MedicamentDAO.searchMedicament(medId, medNom, medQuantite,medPrix);

            if (!medicaments.isEmpty()) {
                // Affichez les informations des utilisateurs trouvés dans les champs de texte appropriés ou dans un TextArea
                StringBuilder resultText = new StringBuilder("Medicament trouvés : \n");
                for (Medicament medicament : medicaments) {
                    resultText.append("ID: ").append(medicament.getId_medicament()).append("\n");
                    resultText.append("Nom: ").append(medicament.getNom_medicament()).append("\n");
                    resultText.append("Quantite: ").append(medicament.getQuantite_medicament()).append("\n");
                    resultText.append("Prix: ").append(medicament.getPrix_medicament()).append("\n\n");
                }
                resultArea.setText(resultText.toString());
                // mettre à jour un TableView avec les résultats
                medicamentTable.setItems(FXCollections.observableArrayList(medicaments));
                medIdText.clear();
                newNom.clear();
                newQuantite.clear();
                newPrixunit.clear();
            } else {
                // Affichez un message si aucun utilisateur n'est trouvé
                resultArea.setText("Aucun medicament trouvé .");
                newNom.clear();
            }
        } catch (NumberFormatException e) {
            resultArea.setText("Veuillez saisir un ID medicament valide.");
        } catch (SQLException e) {
            // Gérez les exceptions SQL
            resultArea.setText("Erreur lors de la recherche de l'utilisateur : " + e.getMessage());
            throw e;
        }

    }

    @FXML
    void viewAllMedicament(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Medicament> users = FXCollections.observableArrayList(MedicamentDAO.getAllMedicaments());
            medicamentTable.setItems(users);
            resultArea.setText("");
        } catch (SQLException e) {
            resultArea.setText("Problème lors de la récupération des utilisateurs : " + e);
            throw e;
        }


    }

    @FXML
    void updateMed(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            int id = Integer.parseInt(medIdText.getText());
            String nom = newNom.getText();
            int quantite = Integer.parseInt(newQuantite.getText());
            int prix = Integer.parseInt(newPrixunit.getText());


            MedicamentDAO.updateMedicament(id,nom, prix, quantite);
            // Rafraîchir le TableView après la mise à jour
            refreshTableView();
            resultArea.setText("Medicament mis à jour avec succès !");
            medIdText.clear();
            newPrixunit.clear();
            nameText.clear();
            newQuantite.clear();
        } catch (SQLException e) {
            showErrorMessage("Erreur lors de la mise à jour du Medicament : " + e.getMessage());
            resultArea.setText("Erreur lors de la mise à jour du Medicament : " + e.getMessage());
        } catch (NumberFormatException e) {
            showErrorMessage("Veuillez saisir une valeur numérique valide pour le Prix.");
            resultArea.setText("Veuillez saisir une valeur numérique valide pour le Prix.");
        } catch (ClassNotFoundException e) {
            showErrorMessage("Classe non trouvée : " + e.getMessage());
            resultArea.setText("Classe non trouvée : " + e.getMessage());
        }

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
