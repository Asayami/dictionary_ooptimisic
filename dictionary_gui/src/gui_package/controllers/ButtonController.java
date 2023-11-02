package gui_package.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ButtonController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private URL fxmlURL;

    public void loadScene(URL fxmlURL, Event event) throws IOException {
        root = FXMLLoader.load(fxmlURL);
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void dictionaryScene(ActionEvent event) throws IOException {
        fxmlURL = ButtonController.class.getResource("/fxml/main-screen.fxml");
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
