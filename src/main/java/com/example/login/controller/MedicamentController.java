package com.example.login.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MedicamentController {

    @FXML
    private TextField CodeText;

    @FXML
    private Button addMedBtn;

    @FXML
    private TextField dateFabText;

    @FXML
    private TextField datePerText;

    @FXML
    private TextField datefab;

    @FXML
    private TextField dateper;

    @FXML
    private Button deleteMedBtn;

    @FXML
    private ChoiceBox<?> familleText;

    @FXML
    private TableColumn<?, ?> medCodeColumn;

    @FXML
    private AnchorPane medCrud;

    @FXML
    private TableColumn<?, ?> medDatefabColumn;

    @FXML
    private TableColumn<?, ?> medDateperColumn;

    @FXML
    private TableColumn<?, ?> medFamilleColumn;

    @FXML
    private TableColumn<?, ?> medIdColumn;

    @FXML
    private TextField medIdText;

    @FXML
    private TextField medIdText1;

    @FXML
    private TextField medIdText2;

    @FXML
    private TableColumn<?, ?> medNameColumn;

    @FXML
    private TableColumn<?, ?> medPrixlotColumn;

    @FXML
    private TableColumn<?, ?> medPrixunitColumn;

    @FXML
    private TableView<?> medicamentTable;

    @FXML
    private TextField nameText;

    @FXML
    private TextField prixlot;

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
    private Executor exec;

    @FXML
    void deleteMedicament(ActionEvent event) {

    }

    @FXML
    void insertMedicament(ActionEvent event) {

    }

    @FXML
    void searchMedicament(ActionEvent event) {

    }

    @FXML
    void searchMedicaments(ActionEvent event) {

    }

    @FXML
    void updatePrix(ActionEvent event) {

    }


}
