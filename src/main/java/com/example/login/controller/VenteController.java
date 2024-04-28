package com.example.login.controller;

import com.example.login.model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.List;

public class VenteController {


    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    @FXML
    private TableView<Vente> tableauAchatMedicament;
    @FXML
    private TableColumn<Vente, Integer> codeColumn;
    @FXML
    private TableColumn<Vente, String> nomMedColumn;
    @FXML
    private TableColumn<Vente, Integer> qttColumn;
    @FXML
    private TableColumn<Vente, Integer> prixColumn;
    @FXML
    private TableColumn<Vente, Integer> prixTotalColumn;
    @FXML
    private TableColumn<Vente, String> statusColumn;
    @FXML
    private TableView<Medicament> listMed;
    @FXML
    private TableColumn<Medicament, Integer> idMedListe;
    @FXML
    private TableColumn<Medicament, String> nomMedList;
    @FXML
    private TableColumn<Medicament, String> familleMedListe;
    @FXML
    private TableColumn<Medicament, Integer> prixMedListe;


    @FXML
    private Button searchMedicamentButton;

    @FXML
    private Button sendButton;


    @FXML
    private TextField txtFamillyMedicament;

    @FXML
    private TextField txtNameClient;

    @FXML
    private TextField txtNameDoctor;

    @FXML
    private TextField txtNameMedicament;

    @FXML
    private TextField txtPriceMedicament;

    @FXML
    private TextField txtQuantiteMedicament;

    @FXML
    private TextField txtSearchMedicament;
    @FXML
    private TextField id_med;

    @FXML
    private ToggleButton withPrescriptionButton;

    @FXML
    private ToggleButton withoutPrescriptionButton;
    @FXML
    private Pane ordonnance;
    private Medicament medicament;


    public void initialize() throws SQLException, ClassNotFoundException{
        withPrescriptionButton.setOnAction(event -> {
            // Rendre la visibilité de la pane ordonnance à true lorsque le bouton withPrescriptionButton est cliqué
            ordonnance.setVisible(false);
        });

        withoutPrescriptionButton.setOnAction(event -> {
            // Rendre la visibilité de la pane ordonnance à false lorsque le bouton withoutPrescriptionButton est cliqué
            ordonnance.setVisible(true);
        });
        refreshTableView();
        codeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdVente()).asObject());
        nomMedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomMed()));
        prixColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrixMed()).asObject());
        qttColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQttAcheteMed()).asObject());
        prixTotalColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrixTotal()).asObject());
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));


        //Tableau de recherche
        idMedListe.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_medicament()).asObject());
        nomMedList.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom_medicament()));
        familleMedListe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFamille_medicament()));
        prixMedListe.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrix_medicament()).asObject());

        listMed.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Integer idMed = newValue.getId_medicament();
                id_med.setText(String.valueOf(idMed));
                String nomMed = newValue.getNom_medicament();
                txtNameMedicament.setText(nomMed);
                String famille = newValue.getFamille_medicament();
                txtFamillyMedicament.setText(famille);
                Integer prixUnit = newValue.getPrix_medicament();
                txtPriceMedicament.setText(String.valueOf(prixUnit));

            }
        });
        tableauAchatMedicament.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Integer idMed = newValue.getIdVente();
                id_med.setText(String.valueOf(idMed));


            }
        });
    }
    @FXML
    void searchMedicamentButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String medNom = txtSearchMedicament.getText().isEmpty() ? "" : txtSearchMedicament.getText();

        List<Medicament> medicaments = VenteDAO.searchMedicament(medNom);
        listMed.setItems(FXCollections.observableArrayList(medicaments));



    }

    @FXML
    void viewAllMedOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        viewAllVente(null);

    }
    @FXML
    void addButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException{
        String nameValue = txtNameMedicament.getText();
        String id = id_med.getText();
        int qtt_achetee;
        int prixUnit;
        prixUnit = Integer.parseInt(txtPriceMedicament.getText());
        qtt_achetee = Integer.parseInt(txtQuantiteMedicament.getText());
        String client = txtNameClient.getText();
        String medecin = txtNameDoctor.getText();



        if (!nameValue.isEmpty()) {
            try {
                VenteDAO.insertVente(Integer.parseInt(id),nameValue, qtt_achetee,client,medecin,prixUnit);
                refreshTableView();
                id_med.clear();
                txtNameMedicament.clear();
                txtQuantiteMedicament.clear();
                txtPriceMedicament.clear();
                txtFamillyMedicament.clear();
                txtNameClient.clear();
                txtNameDoctor.clear();

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    @FXML
    void cancelButtonOnAction(ActionEvent event)throws SQLException, ClassNotFoundException {
        txtNameMedicament.clear();
        txtFamillyMedicament.clear();
        txtPriceMedicament.clear();
        txtQuantiteMedicament.clear();


    }
    @FXML
    void cancelButtonOnAction2(ActionEvent event)throws SQLException,ClassNotFoundException {
        try {
            int id = Integer.parseInt(id_med.getText());

            VenteDAO.cancelvente(id);
            // Rafraîchir le TableView après la suppression
            refreshTableView();
            id_med.clear();

        } catch (SQLException e) {
          e.printStackTrace();
        }


    }



    @FXML
    void sendButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        VenteDAO.envoiCaisse();
        refreshTableView();

    }

    @FXML
    void withPrescriptionButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

    }

    @FXML
    void withoutPrescriptionButtonOnAction(ActionEvent event) {

    }
    @FXML
    void viewAllVente(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Récupérer les deux listes distinctes à partir de la méthode getAllApprovisionnemet()
        Pair<List<Vente>, List<Medicament>> listsPair = VenteDAO.getAllVente();
        List<Vente> approMedList = listsPair.getKey(); // Liste d'approvisionnements
        List<Medicament> medList = listsPair.getValue(); // Liste de médicaments

        ObservableList<Vente> approObservableList = FXCollections.observableArrayList(approMedList);
        ObservableList<Medicament> medObservableList = FXCollections.observableArrayList(medList);

        tableauAchatMedicament.setItems(approObservableList); // Définir la liste d'approvisionnements dans approTable
        listMed.setItems(medObservableList); // Définir la liste de médicaments dans medTable
    }




    private void refreshTableView() throws SQLException, ClassNotFoundException {
        viewAllVente(null);
    }

}
