module main_package {
    requires javafx.controls;
    requires javafx.fxml;

    opens gui_package to javafx.fxml;
    exports gui_package;
    exports gui_package.controllers;
    opens gui_package.controllers to javafx.fxml;
}