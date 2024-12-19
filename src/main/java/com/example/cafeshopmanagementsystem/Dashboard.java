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
    static VBox categories = new VBox(20);

    ComboBox<String> categoryComboBox = new ComboBox<>();

    public void showDashboard() {
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Cafe Shop Management System");
        vBox2.setVisible(true);
        showRestaurant.setVisible(false);
        categories.setVisible(false);

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
        showRestaurant.getStyleClass().add("show-restaurant-style");
        categories.getStyleClass().add("categories-style");

        setupVBox1();
        setupVBox2();
        setupVBox3();

        StackPane mainScreen = new StackPane(vBox2, showRestaurant , categories);
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
        vBox1.setPadding(new Insets(5));
        vBox1.setAlignment(Pos.CENTER);

        Button homeButton = new Button("Home");
        homeButton.getStyleClass().add("home-button");
        homeButton.setOnAction(event -> {
            showRestaurant.setVisible(false);
            categories.setVisible(false);
            vBox2.setVisible(true);
            categoryComboBox.setValue(null);
            categoryComboBox.getEditor().clear();
        });
        homeButton.setPrefSize(220, 40);

        Button restaurantsButton = new Button("Restaurants");
        restaurantsButton.getStyleClass().add("restaurants-button");
        restaurantsButton.setPrefSize(220, 40);
        restaurantsButton.setOnAction(event -> {
            vBox2.setVisible(false);
            categories.setVisible(false);
            showRestaurant.setVisible(true);
            showRestaurants();
            categoryComboBox.setValue(null);
            categoryComboBox.getEditor().clear();
        });

        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Fast Food", "Desi Food", "Sweets");
        categoryComboBox.setPrefSize(220, 40);
        categoryComboBox.setPromptText("Category");
        categoryComboBox.getStyleClass().add("categories-comboBox");
        categoryComboBox.setOnAction(event -> {
            String selectedCategory = categoryComboBox.getValue();
            switch (selectedCategory) {
                case "Fast Food":
                    showRestaurant.setVisible(false);
                    vBox2.setVisible(false);
                    categories.setVisible(true);
                    displayFastFoodItem();
                    break;
                case "Desi Food":
                    showRestaurant.setVisible(false);
                    vBox2.setVisible(false);
                    categories.setVisible(true);
                    displayDesiFoodItem();
                    break;
                case "Sweets":
                    showRestaurant.setVisible(false);
                    vBox2.setVisible(false);
                    categories.setVisible(true);
                    displaySweatsItem();
                    break;
            }
        });

        VBox buttonBox = new VBox(20, homeButton, restaurantsButton,  categoryComboBox);
        buttonBox.setAlignment(Pos.CENTER);
        vBox1.getChildren().add(buttonBox);
    }

    private void setupVBox2() {
        categories.getChildren().clear();
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

    private void setupVBox3() {
        vBox3.setPadding(new Insets(10));
        vBox3.setAlignment(Pos.TOP_CENTER);

        Label orderLabel = new Label("Your Order");
        orderLabel.getStyleClass().add("orderLabel");

        // Table columns
        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setMinWidth(100);
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(40);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMaxWidth(50);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Remove button column
        TableColumn<Item, Void> removeColumn = new TableColumn<>("Remove");
        removeColumn.setMaxWidth(70);

        // Add a "Remove" button to each row
        removeColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removeButton = new Button("\uD83D\uDDD1");
            {
                removeButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-radius: 5px;");
                removeButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    tableView.getItems().remove(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });

        // Add columns to the table
        tableView.getColumns().clear();
        tableView.getColumns().addAll(itemNameColumn, quantityColumn, priceColumn, removeColumn);

        // Total price label
        Label totalPriceLabel = new Label();
        totalPriceLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");

        Label paymentLabel = new Label("Choose a payment option:");

        Button payButton = new Button("Pay Now");
        payButton.getStyleClass().add("payButton");
        payButton.setPrefSize(220,40);
        payButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");
        payButton.setOnAction(event -> {
            tableView.getItems().clear();

            totalPriceLabel.setText("Total Price: $0.00");

            // Create a custom dialog to show the checkboxes for payment options
            CheckBox cashCheckBox = new CheckBox("CASH");
            CheckBox cardCheckBox = new CheckBox("CARD");

            // Create a VBox to hold the checkboxes
            VBox vbox = new VBox(cashCheckBox, cardCheckBox);
            vbox.setSpacing(10);

            // Create the dialog with checkboxes
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Payment Method");
            alert.setHeaderText("How would you like to pay?");
            alert.getDialogPane().setContent(vbox);

            // Show the dialog and wait for user response
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (cashCheckBox.isSelected()) {
                        paymentLabel.setText("Give cash to driver.");
                    } else if (cardCheckBox.isSelected()) {
                        TextInputDialog pinDialog = new TextInputDialog();
                        pinDialog.setTitle("Enter PIN");
                        pinDialog.setHeaderText("Please enter your PIN to confirm.");
                        pinDialog.setContentText("PIN:");

                        pinDialog.showAndWait().ifPresent(pin -> {
                            if (pin != null && !pin.isEmpty()) {
                                paymentLabel.setText("Order confirmed. Payment successful.");
                            } else {
                                paymentLabel.setText("Order cancelled.");
                            }
                        });
                    }
                }
            });
        });
        // Update the total price whenever the table changes
        Runnable updateTotalPrice = () -> {
            double total = tableView.getItems().stream()
                    .mapToDouble(Item::getPrice)
                    .sum();
            totalPriceLabel.setText("Total Price: $" + String.format("%.2f", total));
        };

        tableView.getItems().addListener((ListChangeListener<Item>) change -> updateTotalPrice.run());
        updateTotalPrice.run();

        vBox3.getChildren().clear();
        vBox3.getChildren().addAll(orderLabel, tableView, totalPriceLabel, payButton);
    }

    private void addItemToTable(String itemName, int quantity, double itemPrice) {
        ObservableList<Item> items = tableView.getItems();

        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setPrice(itemPrice * item.getQuantity());
                tableView.refresh();
                return;
            }
        }
        items.add(new Item(itemName, quantity, itemPrice));
    }

    private void showRestaurants() {
        categories.getChildren().clear();
        showRestaurant.getChildren().clear();
        showRestaurant.setPadding(new Insets(20));

        // Create a FlowPane for restaurants
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(2 , 10 , 5 ,10));
        flowPane.setHgap(25);
        flowPane.setVgap(25);
        flowPane.setPrefWidth(470);
        flowPane.setAlignment(Pos.CENTER);

        String[][] restaurants = {
                {"/kfc.png", "Amazing broast and burgers. Must visit!","KFC"},
                {"/macdonald.png", "Best burgers fries in town.","Macdonald's"},
                {"/ButtKarahi.png", "Delicious and consistent karahi.","Butt Karahi" },
                {"/AlKhan.png", "Unlimited Buffet on daily basis.","Al-Khan"},
                {"/monal.png","Monal Lahore is serving the finest Cuisines with a combination of traditional and exciting flavors.","Monal"},
                {"/wok.png","Discover diverse flavors at our Hi-Tea Buffet that's just around the corner!!","Asian Wok"}
        };

        for (String[] restaurant : restaurants) {
            // Create a VBox for each restaurant
            VBox restaurantBox = new VBox(5);
            restaurantBox.setPrefSize(180, 280);
            restaurantBox.getStyleClass().add("restaurantBox");
            restaurantBox.setAlignment(Pos.CENTER);
            // Add restaurant name and description
            Button nameLabel = new Button(restaurant[2]);
            nameLabel.setPrefSize(150 , 30);
            nameLabel.getStyleClass().add("nameLabel");

            ImageView imageView = new ImageView(restaurant[0]);
            imageView.setFitWidth(230);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

            Label descriptionLabel = new Label(restaurant[1]);
            descriptionLabel.setWrapText(true);
            descriptionLabel.setStyle("-fx-text-fill: #ffce1b; -fx-font-size: 12; -fx-font-weight: bold; ");

            // Set action on restaurant button
            nameLabel.setOnMouseClicked(event -> showMenu(restaurant[2]));

            restaurantBox.getChildren().addAll(imageView, descriptionLabel , nameLabel);
            flowPane.getChildren().add(restaurantBox);
        }

        showRestaurant.getChildren().add(flowPane);
    }


    private void showMenu(String restaurantName) {
        // Clear the existing content
        showRestaurant.getChildren().clear();

        // Center-align the menu pane
        showRestaurant.setPadding(new Insets(20));
        showRestaurant.setAlignment(Pos.CENTER);

        // Add a back button
        Button backButton = new Button("Back to Restaurants");
        backButton.setStyle("-fx-font-size: 14; -fx-background-color: #ffce1b; -fx-text-fill: black;");
        backButton.setOnAction(event -> showRestaurants());
        // Restaurant menu items
        String[][] menuItems = null;

        switch (restaurantName) {
            case "KFC":
                menuItems = new String[][]{
                        {"Zinger Burger", "4.99", "/image1.png"},
                        {"Chicken Wings", "5.99", "/wings.png"},
                        {"Bucket Meal", "12.99", "/image7.png"}
                };
                break;
            case "Macdonald's":
                menuItems = new String[][]{
                        {"Big Mac", "5.99", "/image1.png"},
                        {"Fries", "2.99", "/fries.png"},
                        {"McFlurry", "3.50", "/mcflurry.png"}
                };
                break;
            case "Butt Karahi":
                menuItems = new String[][]{
                        {"Biryani", "15.99", "/image5.png"},
                        {"Chicken Karahi", "12.99", "/image2.png"},
                        {"Naan", "1.50", "/naan.png"}
                };
                break;
            case "Monal":
                menuItems = new String[][]{
                        {"BBQ", "14.99", "/bbq.png"},
                        {"Pasta", "19.99", "/pasta.png"}
                };
                break;
            case "Al-Khan":
                menuItems = new String[][]{
                        {"Makhni Handi", "14.99", "/Handi.png"},
                        {"Grilled Steak", "19.99", "/steak.png"}
                };
                break;
            case "Asian Wok":
                menuItems = new String[][]{
                        {"Chow Mein", "14.99", "/chowmein.png"},
                        {"Chicken Soup", "19.99", "/soup.png"},
                        {"Egg Fried Rice", "10.00" ,"/rice.png"}
                };
                break;
            default:
                menuItems = new String[0][0];
                break;
        }

        // Create a TilePane for menu items
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(5));
        tilePane.setHgap(10);
        tilePane.setVgap(5);
        tilePane.setPrefColumns(3);
        tilePane.setAlignment(Pos.CENTER);

        // Generate menu items
        for (String[] menuItem : menuItems) {
            VBox itemBox = new VBox(10);
            itemBox.setPadding(new Insets(10));
            itemBox.setAlignment(Pos.CENTER);
            itemBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10px;");

            // Menu item details
            String itemName = menuItem[0];
            double itemPrice = Double.parseDouble(menuItem[1]);
            String imagePath = menuItem[2];

            // Load image
            ImageView imageView = null;
            URL resource = getClass().getResource(imagePath);
            if (resource != null) {
                imageView = new ImageView(resource.toExternalForm());
                imageView.setFitWidth(150);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
            }

            // Labels for name and price
            Label itemNameLabel = new Label(itemName);
            itemNameLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #ffce1b;");

            Label itemPriceLabel = new Label("$" + itemPrice);
            itemPriceLabel.setStyle("-fx-font-size: 12; -fx-text-fill: white;");

            // Quantity controls
            HBox counterBox = new HBox(10);
            counterBox.setAlignment(Pos.CENTER);

            Button minusButton = new Button("-");
            minusButton.getStyleClass().add("minus-button");

            Label quantityLabel = new Label("0");
            quantityLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #ffce1b;");

            Button plusButton = new Button("+");
            plusButton.getStyleClass().add("plus-button");

            counterBox.getChildren().addAll(quantityLabel);

            // Array to track quantity (for use in lambda)
            final int[] quantity = {0};

            // Minus button action
            minusButton.setOnAction(e -> {
                if (quantity[0] > 0) {
                    quantity[0]--;
                    quantityLabel.setText(String.valueOf(quantity[0]));
                }
            });

            // Plus button action
            plusButton.setOnAction(e -> {
                quantity[0]++;
                quantityLabel.setText(String.valueOf(quantity[0]));
            });

            // Add to Cart Button
            Button addToCartButton = new Button("Add to Order");
            addToCartButton.setStyle("-fx-background-color: #ffce1b; -fx-text-fill: black;");
            addToCartButton.setOnAction(event -> {
                if (quantity[0] > 0) {
                    addItemToTable(itemName, quantity[0], itemPrice * quantity[0]);
                    quantity[0] = 0; // Reset quantity after adding to cart
                    quantityLabel.setText(String.valueOf(quantity[0]));
                }
            });

            HBox namePriceBox = new HBox(10);
            namePriceBox.setAlignment(Pos.CENTER);
            namePriceBox.getChildren().addAll(itemNameLabel, itemPriceLabel);

            HBox hBoxCart = new HBox(10);
            hBoxCart.setAlignment(Pos.CENTER);
            hBoxCart.getChildren().addAll(minusButton, addToCartButton, plusButton);

            itemBox.getChildren().addAll(namePriceBox , imageView , counterBox ,hBoxCart);

            tilePane.getChildren().add(itemBox);
        }

        showRestaurant.getChildren().addAll(backButton ,tilePane);
    }

    private void displayFastFoodItem() {
        categories.getChildren().clear();
        categories.setAlignment(Pos.CENTER);
        categories.setPadding(new Insets(5));

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(5));
        tilePane.setHgap(10);
        tilePane.setVgap(5);
        tilePane.setPrefColumns(3);
        tilePane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 5; i++) {
            VBox itemBox = new VBox(5);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10px;");

            // Loading image from the resource path
            ImageView imageView = null;
            String imagePath = "/fast" + (i + 1) + ".png";
            URL resource = getClass().getResource(imagePath);
            if (resource == null) {
                System.out.println("Image not found: " + imagePath);
            } else {
                imageView = new ImageView(resource.toExternalForm());
            }

            imageView.setFitWidth(250);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

            String[] itemNames = {"Burger", "Pizza", "Shawarma", "Wings", "Beef Burger"};
            double[] itemPrices = {5.99, 8.99, 7.49, 4.50, 6.00};

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
        categories.getChildren().add(tilePane);
    }

    private void displayDesiFoodItem() {
        categories.getChildren().clear();
        categories.setAlignment(Pos.CENTER);
        categories.setPadding(new Insets(5));

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(5));
        tilePane.setHgap(10);
        tilePane.setVgap(5);
        tilePane.setPrefColumns(3);
        tilePane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 6; i++) {
            VBox itemBox = new VBox(5);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10px;");

            // Loading image from the resource path
            ImageView imageView = null;
            String imagePath = "/desi" + (i + 1) + ".png";
            URL resource = getClass().getResource(imagePath);
            if (resource == null) {
                System.out.println("Image not found: " + imagePath);
            } else {
                imageView = new ImageView(resource.toExternalForm());
            }

            imageView.setFitWidth(250);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

            String[] itemNames = {"Chicker Karahi", "Biryani", "BBQ", "Naan", "Seekh Kabab", "Salad"};
            double[] itemPrices = {5.99, 8.99, 7.49, 4.50, 6.00, 3.50};

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
        categories.getChildren().add(tilePane);
    }

    private void displaySweatsItem() {
        categories.getChildren().clear();
        categories.setAlignment(Pos.CENTER);
        categories.setPadding(new Insets(5));

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(5));
        tilePane.setHgap(10);
        tilePane.setVgap(5);
        tilePane.setPrefColumns(3);
        tilePane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 4; i++) {
            VBox itemBox = new VBox(5);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10px;");

            // Loading image from the resource path
            ImageView imageView = null;
            String imagePath = "/sweat" + (i + 1) + ".png";
            URL resource = getClass().getResource(imagePath);
            if (resource == null) {
                System.out.println("Image not found: " + imagePath);
            } else {
                imageView = new ImageView(resource.toExternalForm());
            }

            imageView.setFitWidth(250);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

            String[] itemNames = {"Cake","Shakes", "Ice-Cream", "McFlurry"};
            double[] itemPrices = {5.99, 8.99, 7.49, 4.50, 6.00};

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
        categories.getChildren().add(tilePane);
    }
}