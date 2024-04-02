package com.example.login.controller;


import com.example.login.model.User;
import com.example.login.model.userDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private Button addUserButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private TextField newIdentifiantText;

    @FXML
    private TextField identifiantText;
    @FXML
    private TextField mdpText;
    @FXML
    private TextField newMdpText;


    @FXML
    private TextField idText;


    @FXML
    private TableView<User> profilTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> identifiantColumn;
    @FXML
    private TableColumn<User, String> mdpColumn;
    @FXML
    private TableColumn<User, String> profileColumn;

    @FXML
    private TextArea resultArea;

    @FXML
    private Button searchUserButton;

    @FXML
    private Button updateUserButton;



    @FXML
    private Button viewUserButton;

    @FXML
    private ChoiceBox<String>addprofildroptext;
    @FXML
    private ChoiceBox<String> ProfilDropText;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Création de la liste des options du ChoiceBox
        ObservableList<String> profilList = FXCollections.observableArrayList(
                "Pharmacien",
                "Caissier",
                "Vendeur"
        );

        // Ajout des options au ChoiceBox addProfilDropText et ProfilDropText
        addprofildroptext.setItems(profilList);
        ProfilDropText.setItems(profilList);
        initialize();

    }
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        identifiantColumn.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        mdpColumn.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        profileColumn.setCellValueFactory(new PropertyValueFactory<>("profil"));

        // Charger les données des utilisateurs dans la TableView
        try {
            ObservableList<User> userList = FXCollections.observableArrayList(userDAO.getAllUsers());
            profilTable.setItems(userList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Ajouter un événement de sélection au TableView
        profilTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Récupérer les données de l'utilisateur sélectionné
                int idUser = newValue.getIdUser();
                String identifiant = newValue.getIdentifiant();
                String mdp = newValue.getMdp();
                String profil = newValue.getProfil();

                // Afficher les données dans les champs de texte
                idText.setText(String.valueOf(idUser));
                identifiantText.setText(identifiant);
                mdpText.setText(mdp);
                ProfilDropText.setValue(profil);
            }
        });
    }


    @FXML
    private void handleSelection() {
        String selectedProfil = addprofildroptext.getValue();
        String selectedprofil= ProfilDropText.getValue();
        System.out.println("Profil sélectionné : " + selectedProfil);
        System.out.println("Profil sélectionné : " + selectedprofil);

    }
    @FXML
    private void addUser(ActionEvent event) {
        String newIdentifiant = newIdentifiantText.getText();
        String newMdp = newMdpText.getText();
        String newProfil = addprofildroptext.getValue();
        if (!newIdentifiant.isEmpty() && !newMdp.isEmpty() && newProfil !=  null) {
            try {
                userDAO.insertUser(newIdentifiant, newMdp, newProfil);
                refreshTableView(); // Méthode pour actualiser la TableView
                newIdentifiantText.clear();
                newMdpText.clear();
                addprofildroptext.setValue(null);
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
    private void getAllUsers(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<User> users = FXCollections.observableArrayList(userDAO.getAllUsers());
            profilTable.setItems(users);
            resultArea.setText("");
        } catch (SQLException e) {
            resultArea.setText("Problème lors de la récupération des utilisateurs : " + e);
            throw e;
        }
    }
    @FXML
    private void searchUser(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            int userId = idText.getText().isEmpty() ? -1 : Integer.parseInt(idText.getText());
            String identifiant = identifiantText.getText();
            String selectedProfil = ProfilDropText.getValue();

            // Appelez la méthode de recherche en fonction des paramètres saisis
            List<User> users = userDAO.searchUser(userId, identifiant, selectedProfil);

            if (!users.isEmpty()) {
                // Affichez les informations des utilisateurs trouvés dans les champs de texte appropriés ou dans un TextArea
                StringBuilder resultText = new StringBuilder("Utilisateurs trouvés : \n");
                for (User user : users) {
                    resultText.append("ID: ").append(user.getIdUser()).append("\n");
                    resultText.append("Identifiant: ").append(user.getIdentifiant()).append("\n");
                    resultText.append("Mot de passe: ").append(user.getMdp()).append("\n");
                    resultText.append("Profil: ").append(user.getProfil()).append("\n\n");
                }
                resultArea.setText(resultText.toString());
                // mettre à jour un TableView avec les résultats
                 profilTable.setItems(FXCollections.observableArrayList(users));
                 idText.clear();
                identifiantText.clear();
                ProfilDropText.getSelectionModel().clearSelection(); // Efface la sélection dans le ChoiceBox
            } else {
                // Affichez un message si aucun utilisateur n'est trouvé
                resultArea.setText("Aucun utilisateur trouvé avec cet identifiant ou ce profil.");
                identifiantText.clear();
                ProfilDropText.getSelectionModel().clearSelection(); // Efface la sélection dans le ChoiceBox
            }
        } catch (NumberFormatException e) {
            resultArea.setText("Veuillez saisir un ID utilisateur valide.");
        } catch (SQLException e) {
            // Gérez les exceptions SQL
            resultArea.setText("Erreur lors de la recherche de l'utilisateur : " + e.getMessage());
            throw e;
        }
    }
    @FXML
    private void updateUser(ActionEvent event) {
        try {
            int id = Integer.parseInt(idText.getText());
            String identifiant = identifiantText.getText();
            String mdp = mdpText.getText();
            String profil = ProfilDropText.getValue();

            userDAO.updateUser(id, identifiant, mdp, profil);
            // Rafraîchir le TableView après la mise à jour
            refreshTableView();
            resultArea.setText("Utilisateur mis à jour avec succès !");
            idText.clear();
            identifiantText.clear();
            mdpText.clear();
            ProfilDropText.getSelectionModel().clearSelection(); // Efface la sélection dans le ChoiceBox
        } catch (SQLException e) {
            resultArea.setText("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        } catch (NumberFormatException e) {
            resultArea.setText("Veuillez saisir une valeur numérique valide pour l'ID.");
        } catch (ClassNotFoundException e) {
            resultArea.setText("Classe non trouvée : " + e.getMessage());
        }
    }
    @FXML
    private void deleteUser(ActionEvent event) {
        try {
            int id = Integer.parseInt(idText.getText());

            userDAO.deleteUser(id);
            // Rafraîchir le TableView après la suppression
            refreshTableView();
            resultArea.setText("Utilisateur supprimé avec succès !");
            idText.clear();
            identifiantText.clear();
            mdpText.clear();
            ProfilDropText.getSelectionModel().clearSelection(); // Efface la sélection dans le ChoiceBox
        } catch (SQLException e) {
            resultArea.setText("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        } catch (NumberFormatException e) {
            resultArea.setText("Veuillez saisir une valeur numérique valide pour l'ID de l'utilisateur.");
        } catch (ClassNotFoundException e) {
            resultArea.setText("Classe non trouvée : " + e.getMessage());
        }
    }



    private void refreshTableView() throws SQLException, ClassNotFoundException {
        getAllUsers(null);
    }


}