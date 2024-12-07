package com.example.cafeshopmanagementsystem;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dashboard {
    public void showDashboard() {
        // New stage for the dashboard
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Dashboard");

        // Create the top HBox for the label "FOOD HIVE"
        HBox topHBox = new HBox(10); // Spacing of 10px between the label and any other controls
        topHBox.setAlignment(Pos.CENTER);
        topHBox.setStyle("-fx-padding: 10px;");

        Label foodHiveLabel = new Label("FOOD HIVE");
        foodHiveLabel.getStyleClass().add("food-hive-label");  // Apply CSS class

        // Create buttons
        Button restaurantsButton = new Button("Restaurants");
        restaurantsButton.getStyleClass().add("button-style");  // Apply CSS class
        Button categoriesButton = new Button("Categories");
        categoriesButton.getStyleClass().add("button-style");  // Apply CSS class
        Button anotherButton = new Button("Another Button");
        anotherButton.getStyleClass().add("button-style");  // Apply CSS class

        // Add action events to the buttons
        restaurantsButton.setOnAction(e -> {
            System.out.println("Restaurants button clicked");
            // Add logic for Restaurants button
        });
        categoriesButton.setOnAction(e -> {
            System.out.println("Categories button clicked");
            // Add logic for Categories button
        });
        anotherButton.setOnAction(e -> {
            System.out.println("Another button clicked");
            // Add logic for the third button
        });

        // Create an HBox for the buttons
        VBox buttonBox = new VBox(20); // 20px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(restaurantsButton, categoriesButton, anotherButton);

        // Add the label and buttons to the top HBox
        topHBox.getChildren().addAll(foodHiveLabel);

        // Create three vertical boxes
        VBox vBox1 = new VBox(20);
        VBox vBox2 = new VBox(20);
        VBox vBox3 = new VBox(20);

        // Set width of vboxes
        vBox3.setPrefWidth(30);
        vBox1.setPrefWidth(30);

        // Set padding and alignment for the vertical boxes
        vBox1.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        vBox3.setAlignment(Pos.TOP_CENTER);

        // Apply CSS styles
        vBox1.getStyleClass().add("vbox1-style");
        vBox2.getStyleClass().add("vbox2-style");
        vBox3.getStyleClass().add("vbox3-style");

        // Add labels or other controls to the vertical boxes
        vBox1.getChildren().add(buttonBox);
        vBox2.getChildren().add(new Label("Inventory"));
        vBox3.getChildren().add(new Label("Customers"));

        // Add TableView to the third VBox
        TableView<Item> tableView = new TableView<>();

        // Define columns for the TableView
        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setMinWidth(100); // Adjusted width for better fit
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(100); // Adjusted width for better fit
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100); // Adjusted width for better fit
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Add columns to the TableView
        tableView.getColumns().addAll(itemNameColumn, quantityColumn, priceColumn);

        // Add sample data to the TableView
        tableView.getItems().addAll(
                new Item("Apple", 50, 0.99),
                new Item("Banana", 30, 0.49),
                new Item("Carrot", 20, 1.29)
        );

        // Set VBox properties to make the TableView fit and leave space below
        VBox.setVgrow(tableView, Priority.ALWAYS);  // Stop the TableView from growing

        // Set the preferred and max height for the TableView
        tableView.setPrefHeight(350);  // Set the height you want
        tableView.setMaxHeight(350);

        Region spacer = new Region();
        spacer.setPrefHeight(20); // Set the height of the spacer

        vBox3.getChildren().addAll(tableView, spacer);

        // Create an HBox to place the vertical boxes horizontally
        HBox hBox = new HBox(5); // Spacing between each VBox
        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-background-color : white;");
        hBox.getChildren().addAll(vBox1, vBox2, vBox3);

        // Make each VBox expand equally
        HBox.setHgrow(vBox1, Priority.ALWAYS);
        HBox.setHgrow(vBox2, Priority.ALWAYS);
        HBox.setHgrow(vBox3, Priority.ALWAYS);

        // Create a main VBox to hold the top HBox and the HBox containing the vertical boxes
        VBox mainVBox = new VBox(10);
        mainVBox.getChildren().addAll(topHBox, hBox);

        // Allow the HBox to grow inside the VBox
        VBox.setVgrow(hBox, Priority.ALWAYS);

        // Set the scene and show the dashboard stage
        Scene scene = new Scene(mainVBox, 1000, 600); // Increased height and width for the scene

        // Link the CSS file
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        dashboardStage.setScene(scene);
        dashboardStage.show();
    }
}
