package com.example.cafeshopmanagementsystem;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.URL;

public class Dashboard {

    Item item = new Item();
    TableView<Item> tableView = new TableView<>();

    // Create three vertical boxes
    static VBox vBox1 = new VBox(20);
    static VBox vBox2 = new VBox(20);
    static VBox vBox3 = new VBox(20);
    static VBox showRestaurant = new VBox(20);

    public void showDashboard() {
        // New stage for the dashboard
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Cafe Shop Management System");
        vBox2.setVisible(true);
        showRestaurant.setVisible(false);

        // Create the top HBox for the label "FOOD HIVE"
        HBox topHBox = new HBox(20);
        topHBox.getStyleClass().add("top-hbox-style");
        topHBox.setAlignment(Pos.CENTER);

        Label foodHiveLabel = new Label("FOOD HIVE");
        foodHiveLabel.getStyleClass().add("food-hive-label");

        Label logo = new Label("\uD83C\uDF7D");
        logo.setStyle("-fx-font-size: 50; -fx-text-fill: #ffce1b;");

        topHBox.getChildren().addAll(logo, foodHiveLabel);

        vBox1.setMaxWidth(150);
        vBox3.setMaxWidth(300);

        vBox1.getStyleClass().add("vbox1-style");
        vBox2.getStyleClass().add("vbox2-style");
        vBox3.getStyleClass().add("vbox3-style");

        setupVBox1();
        setupVBox2();
        setupVBox3();

        StackPane mainScreen = new StackPane(vBox2, showRestaurant);
        mainScreen.setPrefSize(800, 600);

        HBox hBox = new HBox(5, vBox1, mainScreen, vBox3);
        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-background-color: white;");

        HBox.setHgrow(vBox1, Priority.ALWAYS);
        HBox.setHgrow(vBox2, Priority.ALWAYS);
        HBox.setHgrow(vBox3, Priority.ALWAYS);

        VBox mainVBox = new VBox(10, topHBox, hBox);
        VBox.setVgrow(hBox, Priority.ALWAYS);

        Scene scene = new Scene(mainVBox, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        dashboardStage.setScene(scene);
        dashboardStage.setFullScreen(true);
        dashboardStage.show();
    }

    private void setupVBox1() {
        vBox1.setPadding(new Insets(20));
        vBox1.setAlignment(Pos.CENTER);

        Button homeButton = new Button("Home");
        homeButton.getStyleClass().add("home-button");
        homeButton.setOnAction(event -> {
            vBox2.setVisible(true);
            showRestaurant.setVisible(false);
        });
        homeButton.setPrefSize(200, 40);

        Button restaurantsButton = new Button("Restaurants");
        restaurantsButton.getStyleClass().add("restaurants-button");
        restaurantsButton.setPrefSize(200, 40);
        restaurantsButton.setOnAction(event -> {
            vBox2.setVisible(false);
            showRestaurant.setVisible(true);
            showRestaurants();
        });

        Button categoriesButton = new Button("Categories");
        categoriesButton.getStyleClass().add("categories-button");
        categoriesButton.setPrefSize(200, 40);

        VBox buttonBox = new VBox(20, homeButton, restaurantsButton, categoriesButton);
        buttonBox.setAlignment(Pos.CENTER);
        vBox1.getChildren().add(buttonBox);
    }

    private void setupVBox2() {
        vBox2.getChildren().clear();
        vBox2.setPadding(new Insets(5));
        vBox2.setAlignment(Pos.CENTER);

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(5));
        tilePane.setHgap(10);
        tilePane.setVgap(5);
        tilePane.setPrefColumns(3);
        tilePane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 9; i++) {
            VBox itemBox = new VBox(5);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10px;");

            // Loading image from the resource path
            ImageView imageView = null;
            String imagePath = "/image" + (i + 1) + ".png";
            URL resource = getClass().getResource(imagePath);
            if (resource == null) {
                System.out.println("Image not found: " + imagePath);
            } else {
                imageView = new ImageView(resource.toExternalForm());
            }

            imageView.setFitWidth(250);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

            String[] itemNames = {"Burger", "Karahi", "Cake", "Pizza", "Biryani", "Ice Cream", "Nuggets", "Kabab", "Shakes"};
            double[] itemPrices = {5.99, 8.99, 7.49, 4.50, 6.00, 3.50, 2.99, 3.00, 1.99};

            String itemName = itemNames[i];
            double itemPrice = itemPrices[i];

            Label itemNameLabel = new Label(itemName);
            itemNameLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #ffce1b;");

            Label itemPriceLabel = new Label("$" + itemPrice);
            itemPriceLabel.setStyle("-fx-font-size: 12; -fx-text-fill: white;");

            Label titleLabel = new Label("Select Quantity");
            titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

            HBox counterBox = new HBox(10);
            counterBox.setAlignment(Pos.CENTER);

            Button minusButton = new Button("-");
            minusButton.getStyleClass().add("minus-button");

            Label quantityLabel = new Label("0");
            quantityLabel.setStyle("-fx-font-size: 16; -fx-background-color:transparent; -fx-text-fill:#ffce1b; ");


            Button plusButton = new Button("+");
            plusButton.getStyleClass().add("plus-button");

            counterBox.getChildren().addAll(quantityLabel);

            final int[] quantity = {0}; // Use array to modify in lambda
            minusButton.setOnAction(e -> {
                if (quantity[0] > 0) {
                    quantity[0]--;
                    quantityLabel.setText(String.valueOf(quantity[0]));
                }
            });
            plusButton.setOnAction(e -> {
                quantity[0]++;
                quantityLabel.setText(String.valueOf(quantity[0]));
            });

            Button addToCartButton = new Button("Add to Order");
            addToCartButton.setPrefSize(100, 20);
            addToCartButton.setStyle("-fx-background-color: #ffce1b;  -fx-text-fill: black; -fx-border-radius: 5px;\n"
                    + "    -fx-cursor: hand;");
            addToCartButton.setOnAction(event -> {
                if (quantity[0] > 0) {
                    addItemToTable(itemName, quantity[0], itemPrice * quantity[0]);
                    quantity[0] = 0;
                    quantityLabel.setText(String.valueOf(quantity[0]));
                }
            });

            // Create an HBox for name and price to be aligned horizontally
            HBox namePriceBox = new HBox(10);
            namePriceBox.setAlignment(Pos.CENTER);
            namePriceBox.getChildren().addAll(itemNameLabel, itemPriceLabel);

            HBox hBoxcart = new HBox(10);
            hBoxcart.setAlignment(Pos.CENTER);
            hBoxcart.getChildren().addAll(minusButton, addToCartButton, plusButton);

            // Add the HBox (name and price) above the image in the VBox
            itemBox.getChildren().addAll(namePriceBox, imageView, counterBox, hBoxcart);
            tilePane.getChildren().add(itemBox);
        }

        tilePane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double tileWidth = newValue.doubleValue() / 3 - tilePane.getHgap() * 2;
            for (Node node : tilePane.getChildren()) {
                if (node instanceof VBox) {
                    VBox item = (VBox) node;
                    item.setPrefWidth(tileWidth);
                }
            }
        });

        vBox2.getChildren().add(tilePane);
    }

    private void addItemToTable(String itemName, int quantity, double itemPrice) {
        ObservableList<Item> items = tableView.getItems();
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setPrice(item.getPrice() + (itemPrice * quantity));
                tableView.refresh();
                return;
            }
        }

        items.add(new Item(itemName, quantity, itemPrice * quantity));
    }

    private void setupVBox3() {
        vBox3.setPadding(new Insets(10));
        vBox3.setAlignment(Pos.TOP_CENTER);

        Label orderLabel = new Label("Your Order");
        orderLabel.getStyleClass().add("orderLabel");

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setMinWidth(100);
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(70);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(70);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(itemNameColumn, quantityColumn, priceColumn);

        Label totalPriceLabel = new Label();
        totalPriceLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");

        Runnable updateTotalPrice = () -> {
            double total = tableView.getItems().stream()
                    .mapToDouble(Item::getPrice)
                    .sum();
            totalPriceLabel.setText("Total Price: $" + String.format("%.2f", total));
        };

        tableView.getItems().addListener((ListChangeListener<Item>) change -> updateTotalPrice.run());
        updateTotalPrice.run();

        vBox3.getChildren().addAll(orderLabel, tableView, totalPriceLabel);
    }

    private void showRestaurants() {

        showRestaurant.getChildren().clear();

        showRestaurant.setPadding(new Insets(20));
        showRestaurant.setAlignment(Pos.TOP_CENTER);

        String[][] restaurants = {
                {"KFC", "image1.jpg", "Amazing broast and burgers. Must visit!"},
                {"Mcdonald's", "image2.png", "Best burgers fries in town."},
                {"Butt Karahi", "image3.png", "Delicious and consistent karahi."},
                {"Village", "image4.png", "Unlimited  Buffet on daily basis."}
        };

        for (String[] restaurant : restaurants) {
            HBox restaurantBox = new HBox(10);
            restaurantBox.setPadding(new Insets(10));
            restaurantBox.setAlignment(Pos.CENTER_LEFT);
            restaurantBox.setPrefHeight(600);
            restaurantBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10px; -fx-text-fill: black;");

            VBox infoBox = new VBox(5);
            Button nameLabel = new Button(restaurant[0]);
            nameLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;-fx-text-fill: black;");
            Label descriptionLabel = new Label(restaurant[2]);
            descriptionLabel.setWrapText(true);
            descriptionLabel.setStyle("-fx-text-fill: black;");
            nameLabel.setOnMouseClicked(event -> {

            });
            infoBox.getChildren().addAll(nameLabel, descriptionLabel);
            
            restaurantBox.getChildren().addAll(infoBox);
            showRestaurant.getChildren().add(restaurantBox);
        }
    }
}

