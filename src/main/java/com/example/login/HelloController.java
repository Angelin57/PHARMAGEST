package com.example.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class HelloController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;


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

             Parent caisse = FXMLLoader.load(HelloApplication.class.getResource("dashboard.fxml"));

                // Créez une nouvelle instance de Stage
                Stage   stage = new Stage();
                Scene  scene = new Scene(caisse);
                stage.setScene(scene);
                stage.show();

                // Ferme l'ancienne fenêtre
                Stage ancienneStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                ancienneStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}