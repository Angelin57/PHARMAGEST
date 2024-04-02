package com.example.login.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ApprovisionnementController {

    @FXML
    private TableColumn<?, ?> actionColumn;

    @FXML
    private Button ajoutBtn;

    @FXML
    private Button annulerBtn;

    @FXML
    private TableColumn<?, ?> dateRecueColumn;

    @FXML
    private DatePicker datelivraisonType;

    @FXML
    private ComboBox<?> fournisseurType;

    @FXML
    private TableColumn<?, ?> idMedColumn;

    @FXML
    private TableColumn<?, ?> idMedDesousColumn;

    @FXML
    private TextField medicamentText;

    @FXML
    private TableColumn<?, ?> nomColumn;

    @FXML
    private TableColumn<?, ?> nomMedDessousColumn;

    @FXML
    private TableColumn<?, ?> prixFournisseurColumn;

    @FXML
    private TableColumn<?, ?> prixUnitColumn;

    @FXML
    private TextField prixunitText;

    @FXML
    private TableColumn<?, ?> qttCommandeColumn;

    @FXML
    private TableColumn<?, ?> qttRecueColumn;

    @FXML
    private TableColumn<?, ?> qttStockColumn;

    @FXML
    private TextField qttcommandeText;

    @FXML
    private TextField qttlivreText;

    @FXML
    private TableColumn<?, ?> seuilColumn;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    private ComboBox<?> statusType;

}
