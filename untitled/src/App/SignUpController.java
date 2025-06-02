package App;

import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;




public class SignUpController {

    @FXML private TextField emailField;
    @FXML private TextField PasswordField;
    @FXML private TextField nameField;

    @FXML private void handleSignUp (ActionEvent event){
        String email=emailField.getText();
        String password=PasswordField.getText();
        String name=nameField.getText();

        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog:Please Enter All Fields");


        }
        try (Connection conn = Database.jdbc.connect()) {
            String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setString(3, email);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");

                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog:Please Try Again");
                //showAlert(Alert.AlertType.ERROR, "Something went wrong.");
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog:Please Try Again");

        }}

    @FXML
    private void switchtologin(ActionEvent event) {
        // Example action: just print
        System.out.println("Switch to login clicked");
        // Later: you can load login.fxml here
    }

        private void clearFields() {
            emailField.clear();
            nameField.clear();
            PasswordField.clear();
        }

    }

