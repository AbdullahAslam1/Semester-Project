package com.example.cafeshopmanagementsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.example.cafeshopmanagementsystem.Dashboard.categories;
import static com.example.cafeshopmanagementsystem.Dashboard.showRestaurant;

public class AdminDashboard {

    TableView<MenuItem> tableView = new TableView<>();

    private static final String MENU_DATA_FILE = "MenuData.txt";

    public void showAdminDashboard() {
        Stage adminStage = new Stage();
        adminStage.setTitle("Admin Dashboard - Cafe Shop Management");

        // Define VBox for layout
        VBox vBox1 = new VBox(20);
        VBox vBox2 = new VBox(20);
        VBox vBox3 = new VBox(20);

        vBox1.setMaxWidth(150);
        vBox2.setMaxWidth(500);
        vBox3.setMaxWidth(300);

        // Set styles
        vBox1.getStyleClass().add("vbox1-style");
        vBox2.getStyleClass().add("vbox2-style");
        vBox3.getStyleClass().add("vbox3-style");

        setupVBox1(vBox1);
        setupVBox2(vBox2);

        HBox mainLayout = new HBox(20, vBox1, vBox2, vBox3);
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        adminStage.setScene(scene);
        adminStage.setFullScreen(false);
        adminStage.show();
    }

    // Set up VBox1 for navigation buttons
    private void setupVBox1(VBox vBox1) {
        vBox1.setPadding(new Insets(20));
        vBox1.setAlignment(Pos.TOP_CENTER);

        Button homeButton = new Button("Home");
//        homeButton.setOnAction(event -> {
//            // Implement Home functionality (if needed)
//        });

        Button signOutButton = new Button("Sign Out");
        signOutButton.setOnAction(event -> {
            // Implement Sign-out functionality to go back to the login form
            // Close the admin dashboard and show the login screen
            Stage stage = (Stage) signOutButton.getScene().getWindow();
            stage.close();  // Close the admin dashboard window
            new Main();  // Implement LoginForm to go back to the login screen
        });

        VBox buttons = new VBox(20, homeButton, signOutButton);
        buttons.setAlignment(Pos.CENTER);
        vBox1.getChildren().add(buttons);
    }

    // Set up VBox2 to display menu items and allow editing
    private void setupVBox2(VBox vBox2) {
        vBox2.setPadding(new Insets(10));
        vBox2.setAlignment(Pos.CENTER);

        // Define the columns for the table
        TableColumn<MenuItem, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setMinWidth(100);
        itemNameColumn.setCellValueFactory(cellData ->
               Bindings.createStringBinding(() -> cellData.getValue().getName()));

        TableColumn<MenuItem, Double> itemPriceColumn = new TableColumn<>("Price");
        itemPriceColumn.setMinWidth(100);
        itemPriceColumn.setCellValueFactory(cellData ->
                Bindings.createDoubleBinding(() -> cellData.getValue().getPrice()).asObject());

        TableColumn<MenuItem, String> itemImageColumn = new TableColumn<>("Image Path");
        itemImageColumn.setMinWidth(150);
        itemImageColumn.setCellValueFactory(cellData ->
               Bindings.createStringBinding(() -> cellData.getValue().getImagePath()));


        // Define the action to edit a menu item
        TableColumn<MenuItem, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setMinWidth(100);
        editColumn.setCellFactory(e -> new TableCell<MenuItem, Void>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    MenuItem menuItem = getTableView().getItems().get(getIndex());
                    showEditDialog(menuItem); // Show edit dialog
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        // Add the columns to the table
        tableView.getColumns().clear();
        tableView.getColumns().addAll(itemNameColumn, itemPriceColumn, itemImageColumn, editColumn);

        // Load menu items from the file
        List<MenuItem> menuItems = DataLoader.loadMenuItems(MENU_DATA_FILE);
        tableView.getItems().setAll(menuItems);

        vBox2.getChildren().clear();
        vBox2.getChildren().add(tableView);

        // Add "Save Changes" button to save changes
        Button saveChangesButton = new Button("Submit Changes");
        saveChangesButton.setOnAction(event -> saveMenuChanges());
        vBox2.getChildren().add(saveChangesButton);
    }

    // Show a dialog to edit a menu item
    private void showEditDialog(MenuItem menuItem) {
        TextInputDialog nameInput = new TextInputDialog(menuItem.getName());
        nameInput.setTitle("Edit Item");
        nameInput.setHeaderText("Edit Item Name");
        nameInput.setContentText("Name:");

        TextInputDialog priceInput = new TextInputDialog(String.valueOf(menuItem.getPrice()));
        priceInput.setTitle("Edit Item");
        priceInput.setHeaderText("Edit Price");
        priceInput.setContentText("Price:");

        TextInputDialog imageInput = new TextInputDialog(menuItem.getImagePath());
        imageInput.setTitle("Edit Item");
        imageInput.setHeaderText("Edit Image Path");
        imageInput.setContentText("Image Path:");

        // Show dialog and wait for the result
        nameInput.showAndWait().ifPresent(newName -> menuItem.setName(newName));
        priceInput.showAndWait().ifPresent(newPrice -> menuItem.setPrice(Double.parseDouble(newPrice)));
        imageInput.showAndWait().ifPresent(newImage -> menuItem.setImagePath(newImage));
    }

    // Save the changes made to the menu items back to the file
    private void saveMenuChanges() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MENU_DATA_FILE))) {
            for (MenuItem menuItem : tableView.getItems()) {
                writer.write(menuItem.getName() + "," + menuItem.getPrice() + "," + menuItem.getImagePath() + "\n");
            }
            showInfoDialog("Changes saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            showInfoDialog("Failed to save changes!");
        }
    }

    // Show an information dialog to the admin
    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
