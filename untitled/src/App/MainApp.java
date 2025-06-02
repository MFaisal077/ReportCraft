package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/FXMLfiles/Sign up.fxml")); // adjust path as needed
        Scene scene = new Scene(root);
        stage.setTitle("Sign Up - Report Craft");
        stage.setScene(scene);
        stage.show();
    }
}
