package App;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        Image icon=new Image("resources/ReportCraft Icon.png"); // Icon
        Label titleLabel = new Label("Report Craft");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Scene scene = new Scene(layout, 800, 600);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setPadding(new Insets(10));
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        titleLabel.setAlignment(javafx.geometry.Pos.CENTER); // Align text inside label
        Button Run = new Button("Run Button");
Button Export = new Button("Export Button");
Button cancel = new Button("Cancel Button");
        HBox titleBox = new HBox(titleLabel);
        titleBox.setPadding(new Insets(10));
        titleBox.setAlignment(javafx.geometry.Pos.CENTER);
        layout.getChildren().addAll(titleBox,Run,Export,cancel);

        stage.getIcons().add(icon);
        stage.setTitle("Report Craft");
        stage.setScene(scene);
        stage.show();

    }
}