package com.example.login.controller;

import com.example.login.model.Fournisseur;
import com.example.login.model.FournisseurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class FournisseurController {
    @FXML
    private TextField nomText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField telText;
    @FXML
    private TextField IdText;

    @FXML
    private TextField newName;
    @FXML
    private TextField newTel;
    @FXML
    private TextField newEmail;

    @FXML
    private TableView<Fournisseur> fournisseurTable;
    @FXML
    private TableColumn<Fournisseur, Integer> idColumn;
    @FXML
    private TableColumn<Fournisseur, String> nomColumn;
    @FXML
    private TableColumn<Fournisseur, String> emailColumn;
    @FXML
    private TableColumn<Fournisseur, Integer> telColumn;
    @FXML
    private Button addBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button allFourBtn;
    @FXML
    private TextArea resultArea;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idFournisseur"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        // Ajouter un événement de sélection au TableView
        fournisseurTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Récupérer les données du fournisseur sélectionné
                int idFournisseur = newValue.getIdFournisseur();
                String nom = newValue.getNom();
                String email = newValue.getEmail();
                int telephone = newValue.getTelephone();

                // Afficher les données dans les champs de texte
                IdText.setText(String.valueOf(idFournisseur));
                nomText.setText(nom);
                emailText.setText(email);
                telText.setText(String.valueOf(telephone));
            }
        });
        try {
            getAllFournisseurs(null);
        } catch (SQLException | ClassNotFoundException e) {
            showErrorMessage("Erreur lors de la récupération des fournisseurs au démarrage : " + e.getMessage());
        }
    }

    @FXML
    private void addFournisseur(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            FournisseurDAO.insertFournisseur(newName.getText(), newEmail.getText(), Integer.parseInt(newTel.getText()));
            refreshTableView();
            resultArea.setText("Fournisseur ajouté avec succès");
            newName.clear();
            newEmail.clear();
            newTel.clear();
        } catch (SQLException e) {
            showErrorMessage("Problème lors de l'ajout du fournisseur : " + e);
            resultArea.setText("Problème lors de l'ajout du fournisseur");
            throw e;
        }
    }

    @FXML
    private void searchFournisseur(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            String nomFournisseur = nomText.getText();
            int fournisseurId = -1;

            try {
                fournisseurId = Integer.parseInt(IdText.getText());
            } catch (NumberFormatException e) {
                // Si la conversion échoue, laissez l'ID du fournisseur à -1
            }

            // Appelez la méthode de recherche en fonction des paramètres saisis
            ObservableList<Fournisseur> fournisseurs = FXCollections.observableArrayList(FournisseurDAO.searchFournisseur(fournisseurId, nomFournisseur));

            if (!fournisseurs.isEmpty()) {
                // Affichez les informations des fournisseurs trouvés dans les champs de texte appropriés ou dans un TextArea
                StringBuilder resultText = new StringBuilder("Fournisseurs trouvés : \n");
                for (Fournisseur fournisseur : fournisseurs) {
                    resultText.append("ID: ").append(fournisseur.getIdFournisseur()).append("\n");
                    resultText.append("Nom: ").append(fournisseur.getNom()).append("\n");
                    resultText.append("Email: ").append(fournisseur.getEmail()).append("\n");
                    resultText.append("Téléphone: ").append(fournisseur.getTelephone()).append("\n\n");
                }
                resultArea.setText(resultText.toString());
                fournisseurTable.setItems(fournisseurs);
                nomText.clear();
                IdText.clear();
            } else {
                // Affichez un message si aucun fournisseur n'est trouvé
                resultArea.setText("Aucun fournisseur trouvé avec cet ID ou ce nom.");
                nomText.clear();
                IdText.clear();
            }
        } catch (SQLException e) {
            // Gérez les exceptions SQL
            resultArea.setText("Erreur lors de la recherche du fournisseur : " + e.getMessage());
            throw e;
        }
    }



    @FXML
    private void deleteFournisseur(ActionEvent event) {
        try {
            int id = Integer.parseInt(IdText.getText());

            FournisseurDAO.deleteFournisseur(id);
            // Rafraîchir le TableView après la suppression
            refreshTableView();
            resultArea.setText("Fournisseur supprimé avec succès !");
            IdText.clear();
            nomText.clear();
            emailText.clear();
            telText.clear();
        } catch (SQLException e) {
            showErrorMessage("Erreur lors de la suppression du fournisseur : " + e.getMessage());
            resultArea.setText("Erreur lors de la suppression du fournisseur : " + e.getMessage());
        } catch (NumberFormatException e) {
            showErrorMessage("Veuillez saisir une valeur numérique valide pour l'ID du fournisseur.");
            resultArea.setText("Veuillez saisir une valeur numérique valide pour l'ID du fournisseur.");
        } catch (ClassNotFoundException e) {
            showErrorMessage("Classe non trouvée : " + e.getMessage());
            resultArea.setText("Classe non trouvée : " + e.getMessage());
        }
    }
    @FXML
    private void updateFournisseur(ActionEvent event) {
        try {
            int id = Integer.parseInt(IdText.getText());
            String nom = nomText.getText();
            String email = emailText.getText();
            int telephone = Integer.parseInt(telText.getText());

            FournisseurDAO.updateFournisseur(id,nom, email, telephone);
            // Rafraîchir le TableView après la mise à jour
            refreshTableView();
            resultArea.setText("Fournisseur mis à jour avec succès !");
            IdText.clear();
            nomText.clear();
            emailText.clear();
            telText.clear();
        } catch (SQLException e) {
            showErrorMessage("Erreur lors de la mise à jour du fournisseur : " + e.getMessage());
            resultArea.setText("Erreur lors de la mise à jour du fournisseur : " + e.getMessage());
        } catch (NumberFormatException e) {
            showErrorMessage("Veuillez saisir une valeur numérique valide pour le téléphone.");
            resultArea.setText("Veuillez saisir une valeur numérique valide pour le téléphone.");
        } catch (ClassNotFoundException e) {
            showErrorMessage("Classe non trouvée : " + e.getMessage());
            resultArea.setText("Classe non trouvée : " + e.getMessage());
        }
    }


    @FXML
    private void getAllFournisseurs(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Fournisseur> fournisseurs = FXCollections.observableArrayList(FournisseurDAO.getAllFournisseurs());
            fournisseurTable.setItems(fournisseurs);
            resultArea.setText("");
        } catch (SQLException e) {
            showErrorMessage("Problème lors de la récupération des fournisseurs : " + e);
            resultArea.setText("Problème lors de la récupération des fournisseurs : " + e);
            throw e;
        }
    }

    private void refreshTableView() throws SQLException, ClassNotFoundException {
        getAllFournisseurs(null);
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}