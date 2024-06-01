package com.example.login.controller;

import com.example.login.connection;
import com.example.login.model.DashboardDAO;
import com.example.login.model.Medicament;
import com.example.login.model.Vente;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DashboardController {
    @FXML
    private Label objet1;
    @FXML
    private Label objet2;
    @FXML
    private Label objet3;
    @FXML
    private Label objet4;
    @FXML
    private AnchorPane medSeuil;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane anchorPane;


    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        objet1.setText(DashboardDAO.getVenteJournalier().get(0).getMontantTotal() +" Rs");
        objet2.setText(DashboardDAO.getVenteHebdomadaire().get(0).getMontantTotal() +" Rs");
        objet3.setText(DashboardDAO.getVenteMensuel().get(0).getMontantTotal() +" Rs");
        objet4.setText(DashboardDAO.getMed().get(0).getQuantite_medicament().toString());
    }

//    @FXML
//    void medOnClic(MouseEvent event) {
//        loadapprovisionnement();
//    }
//    public void loadapprovisionnement(){
//        try {
//            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/login/approvisionnement.fxml")));
//
//            ScrollPane mainSp = new ScrollPane();
//            mainSp.setContent(view);
//
//            borderPane.setCenter(mainSp);
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }


}
