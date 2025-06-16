package App;

import javafx.fxml.FXML;
import org.mindrot.jbcrypt.BCrypt;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;




public class SignUpController {

    @FXML private TextField emailField;
    @FXML private PasswordField PasswordField;
    @FXML private TextField nameField;

    @FXML
    private void handleSignUp(ActionEvent event) {
        String email = emailField.getText();
        String password = PasswordField.getText();
        String name = nameField.getText();

        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields.");
            alert.show();
            return;
        }

        try (Connection conn = Database.jdbc.connect()) {
            String sql = "INSERT INTO user_details (email, full_name, password_hash) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // 🔐 bcrypt the password
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            stmt.setString(1, email);
            stmt.setString(2, name);
            stmt.setString(3, hashedPassword);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Sign-up successful!");
                alert.show();
                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something went wrong. Please try again.");
                alert.show();
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Could not connect to database: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void switchtologin(ActionEvent event) {
        try {
            MainApp.showLoginScreen();
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
            nameField.clear();
            PasswordField.clear();
        }

    }

