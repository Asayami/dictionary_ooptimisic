module main_package {
    requires javafx.controls;
    requires javafx.fxml;

    opens main_package to javafx.fxml;
    exports main_package;
    exports main_package.controllers;
    opens main_package.controllers to javafx.fxml;
}