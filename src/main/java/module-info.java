module com.dealerhship {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.dealerhship to javafx.fxml;
    exports com.dealerhship;
}