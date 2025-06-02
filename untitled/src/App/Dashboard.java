package App;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Dashboard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {


        Image icon = new Image("resources/monitor.png"); // Application Icon
        stage.getIcons().add(icon);


        Label titleLabel = new Label("Report Craft");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10));


        TextField queryInput = new TextField(""); // this is where you input queries
        queryInput.setPrefWidth(300);

        // 🔹 Buttons
        Button runButton = new Button("Run"); // For running the query
        Button exportButton = new Button("Export"); // Exporting the graphs
        Button addSQLButton = new Button("Add SQL");// This allows you to import the database in the SQL format


        Label confirmationLabel = new Label(); // confirms if the sql file has been successfully uploaded
        Label statusLabel = new Label();

        //Backend for the "ADD SQL" button.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open SQL File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL Files", "*.sql"));


        addSQLButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    String content = Files.readString(file.toPath());
                    queryInput.setText(content);
                    confirmationLabel.setText("✅ Loaded: " + file.getName());
                    confirmationLabel.setStyle("-fx-text-fill: green;");
                    statusLabel.setText("Ready");
                } catch (IOException ex) {
                    confirmationLabel.setText("❌ Error reading file.");
                    confirmationLabel.setStyle("-fx-text-fill: red;");
                }
            } else {
                confirmationLabel.setText("⚠️ No file selected.");
                confirmationLabel.setStyle("-fx-text-fill: orange;");
            }
        });


        HBox actionBox = new HBox(10, queryInput,runButton, exportButton, addSQLButton);
        actionBox.setPadding(new Insets(10));
        actionBox.setAlignment(Pos.CENTER_LEFT);


        HBox statusBar = new HBox(10, confirmationLabel, statusLabel);
        statusBar.setPadding(new Insets(10));
        statusBar.setAlignment(Pos.CENTER_LEFT);


        VBox layout = new VBox(10, titleBox, actionBox, statusBar);
        layout.setPadding(new Insets(15));


        Scene scene = new Scene(layout, 800, 600);
        stage.setTitle("Report Craft");
        stage.setScene(scene);
        stage.show();
    }
}
