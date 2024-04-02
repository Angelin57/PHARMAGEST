package com.example.login.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class CaisseController {
    @FXML
    private Button annulerBtn;

    @FXML
    private TableColumn<?, ?> dateFactureColumn;

    @FXML
    private TableColumn<?, ?> heureFactureColumn;

    @FXML
    private TableColumn<?, ?> idFactureColumn;

    @FXML
    private TableColumn<?, ?> idMedCaisseColumn;

    @FXML
    private TextField montantPayerText;

    @FXML
    private TextField montantRecuText;

    @FXML
    private TextField montantRendreText;

    @FXML
    private TableColumn<?, ?> nomMedCaisseColumn;

    @FXML
    private TableColumn<?, ?> prixFactureColumn;

    @FXML
    private TableColumn<?, ?> prixMedCaisseColumn;

    @FXML
    private TableColumn<?, ?> qttMedCaisseColumn;

    @FXML
    private TableColumn<?, ?> statutFactureColumn;

    @FXML
    private Button validerBtn;
}
