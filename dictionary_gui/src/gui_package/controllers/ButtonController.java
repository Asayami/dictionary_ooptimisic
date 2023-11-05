package gui_package.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ButtonController implements Initializable {
    private double x;
    private double y;
    private Stage stage;
    private URL fxmlURL;

    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: nothing
    }

    public void titleBarDragged(MouseEvent event) {
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    public void titleBarPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void minimized(ActionEvent event) {
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.setIconified(true);
    }

    public void close(ActionEvent event) {
        javafx.application.Platform.exit();
    }

    public void loadScene(URL fxmlURL, Event event) throws IOException {
        Parent root = FXMLLoader.load(fxmlURL);
        scene = new Scene(root);
        if (stage == null) {
            stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        }
        stage.setScene(scene);
        stage.show();
    }

    public void dictionaryScene(ActionEvent event) throws IOException {
        fxmlURL = ButtonController.class.getResource("/fxml/dictionary-screen.fxml");
        loadScene(fxmlURL, event);
    }

    public void gameScene(ActionEvent event) throws IOException {
        fxmlURL = ButtonController.class.getResource("/fxml/game-screen.fxml");
        loadScene(fxmlURL, event);
    }

    public void translateScene(ActionEvent event) throws IOException {
        fxmlURL = ButtonController.class.getResource("/fxml/translate-screen.fxml");
        loadScene(fxmlURL, event);
    }
}
