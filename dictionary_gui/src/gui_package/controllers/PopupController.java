package gui_package.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PopupController {
    private double x;
    private double y;

    public void titleBarDragged(MouseEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    public void titleBarPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }
}
