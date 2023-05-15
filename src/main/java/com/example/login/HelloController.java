package com.example.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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


    public void loginButtonOnAction(ActionEvent e){
        if (!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()){
            validatelogin();
        }else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    public void cancelButtonOnAction(ActionEvent e){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validatelogin(){
        connection connectNow= new connection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = " select count(1) from \"UserAccounts\" where username = '" + usernameTextField.getText() + "' and password = '" + passwordPasswordField.getText() + "' ";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if (queryResult.getInt(1) == 1 ){
                    loginMessageLabel.setText("Welcome");
                }else {
                    loginMessageLabel.setText("Utilisateur inconnu");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}