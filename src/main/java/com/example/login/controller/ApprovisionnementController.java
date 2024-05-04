package com.example.login.controller;

import com.example.login.model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ApprovisionnementController {

    //Approvisionnement table//
    @FXML
    private TableColumn<Approvisionnement, Integer> idMedColumn;
    @FXML
    private TableColumn<Approvisionnement, String> nomColumn;
    @FXML
    private TableColumn<Approvisionnement, Date> dateRecueColumn;
    @FXML
    private TableColumn<Approvisionnement, Integer> prixFournisseurColumn;
    @FXML
    private TableColumn<Approvisionnement, String> statusColumn;
    @FXML
    private TableColumn<Approvisionnement, Integer> qttCommandeColumn;
    @FXML
    private TableColumn<Approvisionnement, Integer> qttRecueColumn;
    @FXML
    private TableColumn<Approvisionnement, String> fournisseurColumn;
    @FXML
    private TableColumn<Approvisionnement, Integer> id_medColumn;

    @FXML
    private TableView<Approvisionnement> approTable;

    //-------------------------------------------------------//

    //medicament en dessous de seuil//

    @FXML
    private TableView<Medicament> medTable;
    @FXML
    private TableColumn<Medicament, Integer> idMedDesousColumn;
    @FXML
    private TableColumn<Medicament, String> nomMedDessousColumn;
    @FXML
    private TableColumn<Medicament, String> qttStockColumn;

    //-------------------------------------------//

    @FXML
    private DatePicker datelivraisonType;
    @FXML
    private ChoiceBox<Fournisseur> fournisseurType;
    @FXML
    private ChoiceBox<Medicament> medicamentType;
    @FXML
    private ChoiceBox<Approvisionnement> medicamentType1;
    @FXML
    private TextField prixunitText;
    @FXML
    private TextField qttcommandeText;
    @FXML
    private TextField qttlivreText;
    @FXML
    private TextField id_med;
    @FXML
    private ChoiceBox<String> statusType;
    private DataService dataService;
    private Approvisionnement approvisionnement;

    public void initialize() throws SQLException, ClassNotFoundException {
        refreshTableView();
        premierTable();
        deuxiemeTable();
        //initialisation des services//
        dataService = new DataService();
        ObservableList<Fournisseur> fournisseurs = FXCollections.observableArrayList(dataService.getFournisseurs());
        fournisseurType.setItems(fournisseurs);
        ObservableList<Medicament> medicaments = FXCollections.observableArrayList(dataService.getMedicament());
        medicamentType.setItems(medicaments);

        ObservableList<String> profilList = FXCollections.observableArrayList(
                "En cours",
                "Annuler",
                "Terminer"
        );
        statusType.setItems(profilList);
        try {
            viewAllAppro(null);

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void premierTable(){
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        idMedColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdApprovisonnement()).asObject());
        dateRecueColumn.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getDateRecue(); // Supposons que vous avez une méthode getDateRecue() pour obtenir la date
            return new SimpleObjectProperty<>(date);
        });
        prixFournisseurColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrixFournisseur()).asObject());
        qttCommandeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantiteRecue()).asObject());
        qttRecueColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantiteRecue()).asObject());
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        fournisseurColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFournisseur()));
        id_medColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_med()).asObject());

    }

    public void deuxiemeTable() throws SQLException, ClassNotFoundException {

        idMedDesousColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_medicament()).asObject());
        nomMedDessousColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom_medicament()));
        qttStockColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantite_medicament()).asObject().asString());

        approTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Première table
                String nom = newValue.getNom();
                Integer qtt_recue = newValue.getQuantiteRecue();
                //Integer idmed = newValue.getId_med();
                String status = newValue.getStatus();

                statusType.setValue(status);
                qttlivreText.setText(String.valueOf(qtt_recue));


                //id_med.setText(String.valueOf(idmed));

                Approvisionnement approvisionnement = newValue;
                medicamentType1.setValue(approvisionnement);
                // Utilisez le nom pour définir la valeur de la medicamentType

            }
        });

        medTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Obtenir l'objet Medicament correspondant à partir de newValue
                Medicament medicament = newValue;

                // Définir l'objet Medicament comme valeur pour votre ChoiceBox
                medicamentType.setValue(medicament);
                Integer idmed = newValue.getId_medicament();
                id_med.setText(String.valueOf(idmed));

            }
        });
    }



    @FXML
    void ajoutApproOnAction(ActionEvent event) {
        Medicament nameValue = medicamentType.getValue();
        Fournisseur fournisseurValue = fournisseurType.getValue();
        String status = statusType.getValue();
        LocalDate dateRecue = datelivraisonType.getValue();
        // Supposons que id_med.getText() retourne une chaîne représentant un entier.
        String idText = id_med.getText();
        Integer idInt = Integer.parseInt(idText);
        Medicament idMed = new Medicament();
        idMed.setId_medicament(idInt);

        String prixFournisseur;
        int qttCommande;
        int qttLivre;

        try {
            prixFournisseur = String.valueOf(Integer.valueOf(prixunitText.getText()));
            qttCommande = Integer.parseInt(qttcommandeText.getText());
            qttLivre = Integer.parseInt(qttlivreText.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        if ( nameValue != null && fournisseurValue != null && dateRecue != null && status != null) {
            try {
                ApprovisonnementDAO.insertAppro(nameValue,status, Integer.parseInt(prixFournisseur), qttCommande, qttLivre, java.sql.Date.valueOf(dateRecue), fournisseurValue,idMed);
                refreshTableView();

                prixunitText.clear();
                qttcommandeText.clear();
                qttlivreText.clear();
                statusType.setValue(null);
                fournisseurType.setValue(null);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // Gérer le cas où les champs obligatoires ne sont pas remplis
        }
    }

    @FXML
    void updateApproOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Medicament nameValue = medicamentType.getValue();
        int qttRecue = Integer.parseInt(qttlivreText.getText());
        String status = String.valueOf(statusType.getValue()); // Correction pour récupérer la valeur du statut
        String medName = String.valueOf(medicamentType1.getValue());
        ApprovisonnementDAO.updateApproAndMed(status, qttRecue, medName);
        refreshTableView();
    }

    @FXML
    void refreshpproOnAction(ActionEvent event) {
        try {
            refreshTableView();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AnnulerBoutonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException{
        prixunitText.clear();
        qttcommandeText.clear();
        qttlivreText.clear();
        medicamentType.setValue(null);
        statusType.setValue(null);
    }
    @FXML
    void viewAllAppro(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Récupérer les deux listes distinctes à partir de la méthode getAllApprovisionnemet()
        Pair<List<Approvisionnement>, List<Medicament>> listsPair = ApprovisonnementDAO.getAllApprovisionnemet();
        List<Approvisionnement> approMedList = listsPair.getKey(); // Liste d'approvisionnements
        List<Medicament> medList = listsPair.getValue(); // Liste de médicaments

        ObservableList<Approvisionnement> approObservableList = FXCollections.observableArrayList(approMedList);
        ObservableList<Medicament> medObservableList = FXCollections.observableArrayList(medList);

        approTable.setItems(approObservableList); // Définir la liste d'approvisionnements dans approTable
        medTable.setItems(medObservableList); // Définir la liste de médicaments dans medTable
    }

    private void refreshTableView() throws SQLException, ClassNotFoundException {
        viewAllAppro(null);
    }


    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
