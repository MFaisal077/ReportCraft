package App;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private TextField PasswordField;
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    );
    
    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = PasswordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please Enter All Fields");
            alert.show();
            return;
        }

        try (Connection conn = Database.jdbc.connect()) {
            String sql = "SELECT * FROM user_details WHERE email = ? AND password_hash = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Login Successful!");
                alert.show();

                // Launch Dashboard
                try {
                    Dashboard dashboard = new Dashboard();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    dashboard.start(stage);
                } catch (Exception e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setContentText("Failed to load Dashboard");
                    errorAlert.show();
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid email or password");
                alert.show();
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Connection failed. Please try again.");
            alert.show();
            e.printStackTrace();
        }
    }


    @FXML
    public void switchtoSignUp(ActionEvent event) {
        try {
            MainApp.showSignupScreen();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Unable to switch screens");
            alert.show();
            e.printStackTrace();
        }
    }

    private void clearFields() {
        emailField.clear();
        PasswordField.clear();
    }
}

