package com.example.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private ImageView fondlogin;
    @FXML
    private ImageView logo_login;




    @FXML
    public void loginButtonOnAction(ActionEvent event){
        if (usernameTextField.getText().isBlank() && passwordPasswordField.getText().isBlank()){
            loginMessageLabel.setText("Please enter username and password");
        }else {
            validatelogin(event);
        }
    }

    public void cancelButtonOnAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validatelogin(ActionEvent event){
        connection connectNow= new connection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = " select count(1) from \"UserAccounts\" where username = '" + usernameTextField.getText() + "' and password = '" + passwordPasswordField.getText() + "' ";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if (queryResult.getInt(1) == 1 ){
                    loginMessageLabel.setText("Welcome");

                    changerFenetre(event);

                }else {
                    loginMessageLabel.setText("Utilisateur inconnu");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private void changerFenetre(ActionEvent event) throws IOException {
        try {

             Parent caisse = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("dashboard.fxml")));


                // Créez une nouvelle instance de Stage
                Stage   stage = new Stage();
                Scene  scene = new Scene(caisse);
                stage.setScene(scene);
                stage.show();

                // Définir une taille personnalisée pour la fenêtre
                stage.setMinWidth(1100); // Remplacez 800 par la largeur souhaitée
                stage.setMinHeight(700); // Remplacez 600 par la hauteur souhaitée


                // Ferme l'ancienne fenêtre
                Stage ancienneStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                ancienneStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File img = new File("src/main/image/pharmacien-pharmacie-pharmaciens-sont-prets-donner-conseils-utilisation-medicaments_9026-52.png");
        Image img1 = new Image(img.toURI().toString());
        fondlogin.setImage(img1);

        File img2 = new File("src/main/image/kindpng_2351000.png");
        Image img3 = new Image(img2.toURI().toString());
        logo_login.setImage(img3);




    }

}