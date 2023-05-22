package com.example.login;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControleDash implements Initializable {

    @FXML
    public AnchorPane VENTE;

    @FXML
    public AnchorPane DASH;

    @FXML
    private AnchorPane CAISSE;

    @FXML
    private ImageView croix_dashboard;

    @FXML
    private ImageView medicament;

    @FXML
    private AnchorPane COMMANDE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File img = new File("src/main/image/sodapdf-converted.jpg");
        Image img1 = new Image(img.toURI().toString());
        croix_dashboard.setImage(img1);

        File med1 = new File("src/main/image/amenagement-pharmacie.jpg");
        Image med2 = new Image(med1.toURI().toString());
        medicament.setImage(med2);
    }




    @FXML
    public void clickvente(ActionEvent env) {
        CAISSE.setVisible(false);
        DASH.setVisible(false);
        COMMANDE.setVisible(false);
        VENTE.setVisible(true);
    }

    @FXML
    public void clickcaisse(ActionEvent env) {
        VENTE.setVisible(false);
        DASH.setVisible(false);
        COMMANDE.setVisible(false);
        CAISSE.setVisible(true);
    }

    @FXML
    public void clickcommande(ActionEvent env) {
        CAISSE.setVisible(false);
        DASH.setVisible(false);
        VENTE.setVisible(false);
        COMMANDE.setVisible(true);
    }
}
