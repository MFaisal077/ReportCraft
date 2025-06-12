package App;

import Database.jdbc;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import static App.AlertHelper.showAlert;

public class Dashboard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {


        Image icon = new Image("resources/monitor.png"); // Application Icon
        stage.getIcons().add(icon);
        TableView<ObservableList<String>> resultTable = new TableView<>();


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
        Button ImportCSV=new Button("Import CSV");

        Label confirmationLabel = new Label(); // confirms if the sql file has been successfully uploaded
        Label statusLabel = new Label();

        //Opens the file explorer
        FileChooser fileChooser = new FileChooser(); //.SQL files
        fileChooser.setTitle("Open SQL File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL Files", "*.sql"));
        FileChooser csvChooser=new FileChooser(); // .CSV files
        csvChooser.setTitle("Open CSV File");
        csvChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        addSQLButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    String content = Files.readString(file.toPath());
                    queryInput.setText(content);
                    confirmationLabel.setText(" Loaded: " + file.getName());
                    confirmationLabel.setStyle("-fx-text-fill: green;");
                    statusLabel.setText("Ready");
                } catch (IOException ex) {
                    confirmationLabel.setText("Error reading file.");
                    confirmationLabel.setStyle("-fx-text-fill: red;");
                }
            } else {
                confirmationLabel.setText("No file selected.");
                confirmationLabel.setStyle("-fx-text-fill: orange;");
            }
        });


        runButton.setOnAction(e -> {
            String query = queryInput.getText().trim();

            // Optional: block non-SELECT queries
            if (!query.toLowerCase().startsWith("select")) {
                showAlert(Alert.AlertType.WARNING, "Error","ONLY_SELECT_QUERY");
                return;
            }
            try (Connection conn = jdbc.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                resultTable.getItems().clear();
                resultTable.getColumns().clear();

                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();

                // Create columns dynamically
                for (int i = 1; i <= columnCount; i++) {
                    final int colIndex = i - 1;
                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(meta.getColumnName(i));
                    column.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(colIndex)));
                    resultTable.getColumns().add(column);
                }

                // Add data rows
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(rs.getString(i));
                    }
                    resultTable.getItems().add(row);
                }

            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Query Failed: " + ex.getMessage(),"Exeption");
            }
        });

        ImportCSV.setOnAction(e -> {
            File csvFile = csvChooser.showOpenDialog(stage);
            if (csvFile != null) {
                resultTable.getItems().clear();
                resultTable.getColumns().clear();

                try {
                    String content = Files.readString(csvFile.toPath());
                    String[] lines = content.split("\n");

                    if (lines.length > 0) {
                        String[] headers = lines[0].split(",");

                        // Create columns
                        for (int i = 0; i < headers.length; i++) {
                            final int colIndex = i;
                            TableColumn<ObservableList<String>, String> column = new TableColumn<>(headers[i].trim());
                            column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(colIndex)));
                            resultTable.getColumns().add(column);
                        }

                        // Add rows
                        for (int i = 1; i < lines.length; i++) {
                            String[] values = lines[i].split(",");
                            ObservableList<String> row = FXCollections.observableArrayList();
                            for (String value : values) {
                                row.add(value.trim());
                            }
                            resultTable.getItems().add(row);
                        }

                        confirmationLabel.setText("✅ Imported: " + csvFile.getName());
                        confirmationLabel.setStyle("-fx-text-fill: green;");
                    }

                } catch (IOException ex) {
                    showAlert(Alert.AlertType.ERROR, "CSV Read Error: " + ex.getMessage(), "Error");
                }
            } else {
                confirmationLabel.setText("⚠️ No CSV file selected.");
                confirmationLabel.setStyle("-fx-text-fill: orange;");
            }
        });

        HBox actionBox = new HBox(10, queryInput,runButton, exportButton, addSQLButton,ImportCSV);
        actionBox.setPadding(new Insets(10));
        actionBox.setAlignment(Pos.CENTER_LEFT);


        HBox statusBar = new HBox(10, confirmationLabel, statusLabel);
        statusBar.setPadding(new Insets(10));
        statusBar.setAlignment(Pos.CENTER_LEFT);


        VBox layout = new VBox(10, titleBox, actionBox, statusBar,resultTable);
        layout.setPadding(new Insets(15));


        Scene scene = new Scene(layout, 800, 600);
        stage.setTitle("Report Craft");
        stage.setScene(scene);
        stage.show();
    }
}
