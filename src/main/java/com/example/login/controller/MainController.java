package com.example.login.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.login.loginApplication;
import com.example.login.model.Medicament;
import com.example.login.model.MedicamentDAO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable {
    @FXML
    public ImageView deconnexion;
    @FXML
    private Label dashboardMessageLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label heureLabel;

    private Timeline timeline;


    @FXML
    private TextArea resultArea;

    private Executor exec;



    //menu//
    @FXML
    private Button approvisionnementBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button caisseBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button familleBtn;

    @FXML
    private Button fournisseurBtn;

    @FXML
    private Button medicamentBtn;

    @FXML
    private Button profileBtn;

    @FXML
    private Button venteBtn;
    @FXML
    private Button recepCommandeBtn;




    @FXML
    private AnchorPane medCrud;
    //menu//


    //setvisibility//
    public void setMenuVisibility(String userType) {
        // By default, set all menus to invisible
        approvisionnementBtn.setVisible(false);
        caisseBtn.setVisible(false);
        familleBtn.setVisible(false);
        profileBtn.setVisible(false);
        fournisseurBtn.setVisible(false);
        medicamentBtn.setVisible(false);
        venteBtn.setVisible(false);
        dashboardBtn.setVisible(false);
        recepCommandeBtn.setVisible(false);

        // Set visibility based on user type
        switch (userType) {
            case "Pharmacien":
                approvisionnementBtn.setVisible(true);
                caisseBtn.setVisible(true);
                familleBtn.setVisible(true);
                profileBtn.setVisible(true);
                fournisseurBtn.setVisible(true);
                medicamentBtn.setVisible(true);
                venteBtn.setVisible(true);
                dashboardBtn.setVisible(true);
                recepCommandeBtn.setVisible(true);
                loadDashboard();
                break;
            case "Vendeur":
                venteBtn.setVisible(true);
                loadVente();

                break;
            case "Caissier":
                caisseBtn.setVisible(true);
                loadcaisse();

                break;
            // Add more cases if needed for other user types

        }


    }

    //setvisibility//

    @FXML
    void dashboardOnAction(ActionEvent event) {
        loadDashboard();


    }
    public void loadDashboard(){
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/login/dashboard.fxml")));

            ScrollPane mainSp = new ScrollPane();
            mainSp.setContent(view);
            borderPane.setCenter(mainSp);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    void venteOnAction(ActionEvent event) {
        loadVente();
    }

    public  void loadVente(){
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/login/vente.fxml")));

            ScrollPane mainSp = new ScrollPane();
            mainSp.setContent(view);
            borderPane.setCenter(mainSp);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void caisseOnAction(ActionEvent event) {
        loadcaisse();
    }

    public void loadcaisse(){
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/login/caisse.fxml")));

            ScrollPane mainSp = new ScrollPane();
            mainSp.setContent(view);
            borderPane.setCenter(mainSp);

        }catch (Exception e){
            System.out.println(e);
        }
    }


    @FXML
    public void medicamentOnAction(ActionEvent event){
        loadmedicament();
    }

    public void loadmedicament(){
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/login/medicament.fxml")));

            ScrollPane mainSp = new ScrollPane();
            mainSp.setContent(view);
            borderPane.setCenter(mainSp);

        } catch (Exception e) {
            System.out.println(e);
        }
    }



    @FXML
    public void fournisseurOnAction(ActionEvent event) {
        loadfournisseur();

    }


    public void loadfournisseur() {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/login/fournisseur.fxml")));

            ScrollPane mainSp = new ScrollPane();
            mainSp.setContent(view);

            borderPane.setCenter(mainSp);

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @FXML
    void familleOnAction(ActionEvent event) {
        loadfamille();

    }

    public void loadfamille(){
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/login/famille.fxml")));

            ScrollPane mainSp = new ScrollPane();
            mainSp.setContent(view);

            borderPane.setCenter(mainSp);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    void profileOnAction(ActionEvent event) {
        loadprofile();
    }

    public void loadprofile(){
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/login/utilisateur.fxml")));

            ScrollPane mainSp = new ScrollPane();
            mainSp.setContent(view);

            borderPane.setCenter(mainSp);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @FXML
    void approvisionnementOnAction(ActionEvent event) {
        loadapprovisionnement();
    }
    public void loadapprovisionnement(){
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/login/approvisionnement.fxml")));

            ScrollPane mainSp = new ScrollPane();
            mainSp.setContent(view);

            borderPane.setCenter(mainSp);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @FXML
    void receptionOnAction(ActionEvent event) {
        loadreception();
    }
    public void loadreception(){
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/login/recept_commande.fxml")));

            ScrollPane mainSp = new ScrollPane();
            mainSp.setContent(view);

            borderPane.setCenter(mainSp);

        } catch (Exception e) {
            System.out.println(e);
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;



        });


        String loggedUser = loginController.getLoggedUsername();
        dashboardMessageLabel.setText(loggedUser);

        String loggedFunction = loginController.getLoggedUserfunction();
        setMenuVisibility(loggedFunction);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateLabel.setText(now.format(formatterDate));

        updateTime();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        deconnexion.setOnMouseClicked(mouseEvent -> {
            Parent caisse = null;
            try {
                caisse = FXMLLoader.load(Objects.requireNonNull(loginApplication.class.getResource("login.fxml")));
                // Créer une nouvelle instance de Stage

                Stage stage = new Stage();
                Scene scene = new Scene(caisse);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            // Ferme l'ancienne fenêtre
            Stage ancienneStage = (Stage)  fournisseurBtn.getScene().getWindow();
            ancienneStage.close();

        });
    }

    private void updateTime() {
        DateTimeFormatter formatterHeure = DateTimeFormatter.ofPattern("HH:mm:ss");
        heureLabel.setText(LocalDateTime.now().format(formatterHeure));
    }


}
