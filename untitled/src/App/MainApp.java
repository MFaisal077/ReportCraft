package App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;
    private static final String LOGIN_FXML = "/FXMLfiles/Login.fxml";
    private static final String SIGNUP_FXML = "/FXMLfiles/Sign up.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Login/Signup Application");

        // Start with login screen
        showLoginScreen();

        primaryStage.show();
    }

    public static void showLoginScreen() {
        try {
            Parent root = FXMLLoader.load(MainApp.class.getResource(LOGIN_FXML));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSignupScreen() {
        try {
            Parent root = FXMLLoader.load(MainApp.class.getResource(SIGNUP_FXML));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}