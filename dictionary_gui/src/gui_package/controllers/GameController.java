package gui_package.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class GameController {

    @FXML
    private void mouseClick(MouseEvent event) {
        Label label = (Label) event.getSource();
        System.out.println(label.getText());
    }
}
