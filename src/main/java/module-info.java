module com.example.cafeshopmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cafeshopmanagementsystem to javafx.fxml;
    exports com.example.cafeshopmanagementsystem;
}