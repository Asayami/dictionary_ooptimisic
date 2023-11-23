package gui_package.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DialogController {

    /*
    Button ts = DialogController.appear(event, true, "title", "message"); //chinh true thanh false de an nut cancel
    ts.setOnAction(eventHandler -> {
        //run when press okay
        System.out.println("Pressed Okay");
        DialogController.okay();
    });
     */

    private static Stage popupStage;
    private static final URL fxmlURL = DialogController.class.getResource("/fxml/dialog-box.fxml");
    private static final Parent root;

    static {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(fxmlURL));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Scene scene = new Scene(root, 480, 174);

    public DialogController() {
    }

    public static Button appear(ActionEvent event, boolean isCancelDisplay, String title, String message) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        double x = stage.getX();
        double y = stage.getY();
        if (popupStage == null) {
            popupStage = new Stage();
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setResizable(false);
            popupStage.setScene(scene);
            popupStage.setX(x + 337);
            popupStage.setY(y + 249);
        }
        Button cancelButton = (Button) popupStage.getScene().lookup("#button_cancel");
        Button okayButton = (Button) popupStage.getScene().lookup("#button_okay");
        Label titleLabel = (Label) popupStage.getScene().lookup("#title");
        Label messageLabel = (Label) popupStage.getScene().lookup("#message");
        titleLabel.setText(title);
        messageLabel.setText(message);
        if (isCancelDisplay) {
            cancelButton.setVisible(true);
            cancelButton.setDisable(false);
        } else {
            cancelButton.setVisible(false);
            cancelButton.setDisable(true);
        }
        popupStage.show();
        return okayButton;
    }

    public void cancel(ActionEvent event) {
        popupStage.hide();
    }

    public static void okay() {
        popupStage.hide();
    }


}
