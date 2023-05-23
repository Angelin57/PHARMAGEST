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
    //logo menu//
    @FXML
    private ImageView logo_caisse;

    @FXML
    private ImageView logo_commande;

    @FXML
    private ImageView logo_maintenance;

    @FXML
    private ImageView logo_modif;

    @FXML
    private ImageView logo_recep;

    @FXML
    private ImageView logo_vente;
    //logo menu//
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File img = new File("src/main/image/sodapdf-converted.jpg");
        Image img1 = new Image(img.toURI().toString());
        croix_dashboard.setImage(img1);

        File med1 = new File("src/main/image/amenagement-pharmacie.jpg");
        Image med2 = new Image(med1.toURI().toString());
        medicament.setImage(med2);

        //initialisation logo menu//
        File log_vente = new File("src/main/image/logo_vente.png");
        Image log_vente1 = new Image(log_vente.toURI().toString());
        logo_vente.setImage(log_vente1);

        File log_caisse = new File("src/main/image/logo_caisse.png");
        Image log_caisse1 = new Image(log_caisse.toURI().toString());
        logo_caisse.setImage(log_caisse1);

        File log_commande = new File("src/main/image/logo_commande.png");
        Image log_commande1 = new Image(log_commande.toURI().toString());
        logo_commande.setImage(log_commande1);

        File log_recep = new File("src/main/image/logo_recep_com.png");
        Image log_recep1 = new Image(log_recep.toURI().toString());
        logo_recep.setImage(log_recep1);

        File log_modif = new File("src/main/image/logo_mod_profil.png");
        Image log_modif1 = new Image(log_modif.toURI().toString());
        logo_modif.setImage(log_modif1);

        File log_maintenance = new File("src/main/image/logo _maintenance.png");
        Image log_maintenance1 = new Image(log_maintenance.toURI().toString());
        logo_maintenance.setImage(log_maintenance1);
        //initialisation logo menu//

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
