package com.example.cafeshopmanagementsystem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Dashboard {

    public void showDashboard() {
        // New stage for the dashboard
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Dashboard");

        // Create the top HBox for the label "FOOD HIVE"
        HBox topHBox = new HBox(10); // Spacing of 10px between elements
        topHBox.setAlignment(Pos.CENTER);
        topHBox.setStyle("-fx-padding: 10px;");

        Label foodHiveLabel = new Label("FOOD HIVE");
        foodHiveLabel.getStyleClass().add("food-hive-label"); // Apply CSS class

        // Add the label to the top HBox
        topHBox.getChildren().add(foodHiveLabel);

        // Create three vertical boxes
        VBox vBox1 = new VBox(20);
        VBox vBox2 = new VBox(20);
        VBox vBox3 = new VBox(20);

        // Set width for the VBoxes
        vBox1.setMaxWidth(150);
        vBox3.setMaxWidth(300);

        // Set padding and alignment for the vertical boxes
        vBox1.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        vBox3.setAlignment(Pos.TOP_CENTER);

        // Apply CSS styles
        vBox1.getStyleClass().add("vbox1-style");
        vBox2.getStyleClass().add("vbox2-style");
        vBox3.getStyleClass().add("vbox3-style");

        // Add buttons to vBox1
        Button restaurantsButton = new Button("Restaurants");
        Button categoriesButton = new Button("Categories");
        Button anotherButton = new Button("Another Button");

        VBox buttonBox = new VBox(20, restaurantsButton, categoriesButton, anotherButton);
        buttonBox.setAlignment(Pos.CENTER);
        vBox1.getChildren().add(buttonBox);

        // Add a TilePane with a 3x3 grid of images to vBox2
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10));  // Padding around the grid
        tilePane.setHgap(20);                // Horizontal gap between items
        tilePane.setVgap(20);                // Vertical gap between items
        tilePane.setPrefColumns(3);          // Set the number of columns in the grid
        tilePane.setAlignment(Pos.CENTER);   // Align items in the center

        // Add 9 items with images and descriptions
        for (int i = 1; i <= 9; i++) {
            VBox itemBox = new VBox(20);  // Spacing between image and description
            itemBox.setAlignment(Pos.BOTTOM_CENTER);
            itemBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10px;");
            itemBox.setPrefSize(150, 100); // Increased size for each tile

            // Example image content
            //ImageView imageView = new ImageView(new Image("file:placeholder.jpg")); // Replace with actual image paths
            // imageView.setFitWidth(100);
            // imageView.setFitHeight(100);
            // imageView.setPreserveRatio(true);
            Label description = new Label("Item " + i + "\n$" + (5.0 * i)); // Example description
            description.setStyle("-fx-font-size: 14; -fx-text-fill: gray; -fx-text-alignment: center;");
            description.setWrapText(true);

            itemBox.getChildren().addAll(description);
            tilePane.getChildren().add(itemBox);
        }

        vBox2.getChildren().add(tilePane);

        // Add a TableView to vBox3
        TableView<Item> tableView = new TableView<>();

        // Define columns for the TableView
        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setMinWidth(150);
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(70);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(70);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(itemNameColumn, quantityColumn, priceColumn);

        // Add sample data to the TableView
        tableView.getItems().addAll(
                new Item("Apple", 50, 0.99),
                new Item("Banana", 30, 0.49),
                new Item("Carrot", 20, 1.29)
        );

        vBox3.getChildren().addAll(new Label("Your Order"), tableView);

        // Create an HBox to place the vertical boxes horizontally
        HBox hBox = new HBox(5, vBox1, vBox2, vBox3); // Spacing between each VBox
        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-background-color: white;");

        // Allow the VBoxes to expand equally
        HBox.setHgrow(vBox1, Priority.ALWAYS);
        HBox.setHgrow(vBox2, Priority.ALWAYS);
        HBox.setHgrow(vBox3, Priority.ALWAYS);

        // Create a main VBox to hold the top HBox and the HBox containing the vertical boxes
        VBox mainVBox = new VBox(10, topHBox, hBox);
        VBox.setVgrow(hBox, Priority.ALWAYS);

        // Set the scene and show the dashboard stage
        Scene scene = new Scene(mainVBox, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        dashboardStage.setScene(scene);
        dashboardStage.show();
    }

}
