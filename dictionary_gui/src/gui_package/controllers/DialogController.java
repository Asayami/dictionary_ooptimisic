package gui_package.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DialogController {
    @FXML
    private Button button_cancel;

    private void hideCancel() {
        button_cancel.setDisable(true);
        button_cancel.setVisible(false);
    }

    public static String cancel(ActionEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();

        return "cancel";
    }

    public static String okay(ActionEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();

        return "okay";
    }
}
