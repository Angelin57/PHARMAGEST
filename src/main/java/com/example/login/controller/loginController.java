package com.example.login.controller;
import com.example.login.connection;
import javafx.animation.PauseTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginController implements Initializable {
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
    private RadioButton adminToggle;
    @FXML
    private RadioButton caisseToggle;
    @FXML
    private RadioButton venteToggle;


    @FXML
    public void loginButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (usernameTextField.getText().isBlank() && passwordPasswordField.getText().isBlank()) {
            loginMessageLabel.setText("Veuiller entrer votre username et mdp");
        } else {
            if (!adminToggle.isSelected() && !caisseToggle.isSelected() && !venteToggle.isSelected()) {
                loginMessageLabel.setText("Veuillez choisir votre fonction");
            } else {
                validatelogin(event);
            }
        }
    }

    public void cancelButtonOnAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    private static String loggedUsername;

    private static String loggedUserfunction;

    public static String getLoggedUserfunction() {
        return loggedUserfunction;
    }

    public static String getLoggedUsername() {
        return loggedUsername;
    }

    public void validatelogin(ActionEvent event) throws SQLException, ClassNotFoundException {

        connection connectNow = new connection();
        Connection connectDb = connectNow.getConnection();

        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        String userfunction = getSelectedFunction();
        if (userfunction == null) {
            loginMessageLabel.setText("Veuillez choisir entre admin, caisse ou vente");
            return;
        }

        try {
            String verifyLogin = "SELECT COUNT(1) FROM \"utilisateur\" WHERE identifiant = ? AND mdp = ?";
            PreparedStatement ps = connectDb.prepareStatement(verifyLogin);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet result = ps.executeQuery();
            if(!result.next() || result.getInt(1) == 0) {
                loginMessageLabel.setText("Nom d'utilisateur ou mot de passe incorrect");
                return;
            }

            // Vérifier la fonction
                String verifyFunction = "SELECT COUNT(1) FROM \"utilisateur\" WHERE identifiant = ? AND profil = ?";
            PreparedStatement ps2 = connectDb.prepareStatement(verifyFunction);
            ps2.setString(1, username);
            ps2.setString(2, userfunction);

            ResultSet result2 = ps2.executeQuery();
            if(!result2.next() || result2.getInt(1) == 0) {
                loginMessageLabel.setText("Fonction incorrecte pour cet utilisateur");
                return;
            }

            ResultSet queryResult = ps2.executeQuery();

            if (queryResult.next() && queryResult.getInt(1) > 0) {
                // Connexion réussie
                loggedUsername = username;
                loggedUserfunction = userfunction;

                // Maintenant, vous pouvez exécuter une requête pour vérifier si l'administrateur existe dans la table user_logins
                String checkExistingUser = "SELECT identifiant FROM user_logins WHERE identifiant = ?";
                PreparedStatement checkStatement = connectDb.prepareStatement(checkExistingUser);
                checkStatement.setString(1, username);
                ResultSet existingUserResult = checkStatement.executeQuery();

                if (existingUserResult.next()) {
                    // L'administrateur existe, mettez à jour l'heure et la date de connexion
                    String updateLoginTime = "UPDATE user_logins SET login_time = NOW() WHERE identifiant = ?";
                    PreparedStatement updateStatement = connectDb.prepareStatement(updateLoginTime);
                    updateStatement.setString(1, username);
                    updateStatement.executeUpdate();
                } else {
                    // L'administrateur n'existe pas, insérez un nouvel enregistrement
                    String logLogin = "INSERT INTO user_logins (identifiant, login_time) VALUES (?, NOW())";
                    PreparedStatement logStatement = connectDb.prepareStatement(logLogin);
                    logStatement.setString(1, username);
                    logStatement.executeUpdate();
                }
                // Ensuite, exécutez votre méthode pour changer de fenêtre
                changerFenetre(event);
            } else {
                // L'authentification a échoué
                loginMessageLabel.setText("Utilisateur inconnu");
            }

            ps2.close();
            queryResult.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String getSelectedFunction() {
        if (adminToggle.isSelected()) {
            return "Pharmacien";
        } else if (caisseToggle.isSelected()) {
            return "Caissier";
        } else if (venteToggle.isSelected()) {
            return "Vendeur";
        }
        return null;
    }

    @FXML
    private ProgressBar progressBar;
    @FXML
    public void augmentbar(){

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(progressBar.progressProperty(),1);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(3),keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

    }



    @FXML
    private void changerFenetre(ActionEvent event) throws IOException {
        progressBar.setVisible(true);
        augmentbar();
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> {
            try {
                Parent viewmain = FXMLLoader.load(Objects.requireNonNull(MainController.class.getResource("/com/example/login/viewMain.fxml")));

                // Créer une nouvelle instance de Stage
                Stage stage = new Stage();
                Scene scene = new Scene(viewmain);
                stage.setScene(scene);
                stage.show();

                // Ferme l'ancienne fenêtre
                Stage ancienneStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                ancienneStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        delay.play();
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