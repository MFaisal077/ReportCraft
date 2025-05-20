import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class src extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.RED);
Image icon=new Image("resources/ReportCraft Icon.png");
stage.getIcons().add(icon);
        stage.setTitle("Report Craft");
        stage.setScene(scene);
        stage.show();
    }
}
