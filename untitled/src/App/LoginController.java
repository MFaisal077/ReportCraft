package App;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        TextField UserInput= new TextField("");
        UserInput.setPrefWidth(100);
        TextField PasswordInput= new TextField("");
        PasswordInput.setPrefWidth(100);
        VBox root = new VBox(UserInput, PasswordInput);
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root,800,600);
        stage.setTitle("Login");
        stage.show();
        stage.setScene(scene);


    }
}
