package com.example.login.controller;

import com.example.login.model.Famille;
import com.example.login.model.FamilleDAO;
import com.example.login.model.Fournisseur;
import com.example.login.model.FournisseurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamilleController {
    @FXML
    private Button addFamilleButton;

    @FXML
    private Button deleteFamilleButton;


    @FXML
    private TextField familleIdText;


    @FXML
    private TextField NomFamille;

    @FXML
    private AnchorPane medCrud;



    @FXML
    private TextField newNom;

    @FXML
    private TextArea resultArea;

    @FXML
    private Button searchFamilleButton;

    @FXML
    private Button updateFamilleButton;

    @FXML
    private Button viewFamilleButton;

    @FXML
    private TableColumn<Famille, Integer> familleIdColumn;
    @FXML
    private TableColumn<Famille, String> familleNameColumn;
    @FXML
    private TableView<Famille> medicamentTable;

    @FXML
    private void initialize(){
        familleIdColumn.setCellValueFactory(new PropertyValueFactory<>("idFamille"));
        familleNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));


        // Ajouter un événement de sélection au TableView
        medicamentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Récupérer les données du fournisseur sélectionné
                int idFamille = newValue.getIdFamille();
                String nom = newValue.getNom();


                System.out.print(nom);
                // Afficher les données dans les champs de texte
                familleIdText.setText(String.valueOf(idFamille));
                NomFamille.setText(nom);

            }
        });
        try {
            viewFamilleButtonOnAction(null);
        } catch (SQLException | ClassNotFoundException e) {
            showErrorMessage("Erreur lors de la récupération des familles au démarrage : " + e.getMessage());
        }



    }
    @FXML
    void addFamilleButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            FamilleDAO.insertFamille(newNom.getText());
            refreshTableView();
            resultArea.setText("Fournisseur ajouté avec succès");
            newNom.clear();

        } catch (SQLException | ClassNotFoundException e) {
            showErrorMessage("Problème lors de l'ajout du fournisseur : " + e);
            resultArea.setText("Problème lors de l'ajout du fournisseur");
            throw e;
        }

    }

    @FXML
    void searchFamilleButtonOnActuion(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            String nomFamille = NomFamille.getText();
            int familleId = -1;

            try {
                familleId = Integer.parseInt(familleIdText.getText());
            } catch (NumberFormatException e) {
                // Si la conversion échoue, laissez l'ID du fournisseur à -1
            }

            // Appelez la méthode de recherche en fonction des paramètres saisis
            ObservableList<Famille> familles = FXCollections.observableArrayList(FamilleDAO.searchFamille(familleId, nomFamille));

            if (!familles.isEmpty()) {
                // Affichez les informations des fournisseurs trouvés dans les champs de texte appropriés ou dans un TextArea
                StringBuilder resultText = new StringBuilder("Fournisseurs trouvés : \n");
                for (Famille famille : familles) {
                    resultText.append("ID: ").append(famille.getIdFamille()).append("\n");
                    resultText.append("Nom: ").append(famille.getNom()).append("\n");

                }
                resultArea.setText(resultText.toString());
                medicamentTable.setItems(familles);
                familleIdText.clear();
                NomFamille.clear();
            } else {
                // Affichez un message si aucun fournisseur n'est trouvé
                resultArea.setText("Aucun fournisseur trouvé avec cet ID ou ce nom.");
                familleIdText.clear();
                NomFamille.clear();
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Gérez les exceptions SQL
            resultArea.setText("Erreur lors de la recherche du fournisseur : " + e.getMessage());
            throw e;
        }

    }

    @FXML
    void deleteFamilleButtonOnAction(ActionEvent event) {
        try {
            int id = Integer.parseInt(familleIdText.getText());

            FournisseurDAO.deleteFournisseur(id);
            // Rafraîchir le TableView après la suppression
            refreshTableView();
            resultArea.setText("Fournisseur supprimé avec succès !");
            familleIdText.clear();
            NomFamille.clear();

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
    void updateFamilleButtonOnAction(ActionEvent event) {
        try {
            int id = Integer.parseInt(familleIdText.getText());
            String nom = NomFamille.getText();

            FamilleDAO.updateFamille(id,nom);
            // Rafraîchir le TableView après la mise à jour
            refreshTableView();
            resultArea.setText("Fournisseur mis à jour avec succès !");
            familleIdText.clear();
            NomFamille.clear();

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
    void viewFamilleButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException{
        try {

            ObservableList<Famille> familles = FXCollections.observableArrayList(FamilleDAO.getAllFamilles());
            medicamentTable.setItems(familles);
            resultArea.setText("");
        } catch (SQLException e) {
            showErrorMessage("Problème lors de la récupération des familles : " + e);
            resultArea.setText("Problème lors de la récupération des familles : " + e);
            throw e;
        }

    }
    private void refreshTableView() throws SQLException, ClassNotFoundException {
        viewFamilleButtonOnAction(null);
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
