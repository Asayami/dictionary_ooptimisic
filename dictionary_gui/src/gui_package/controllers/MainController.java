package gui_package.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    private double x;
    private double y;
    private Stage stage;
    private URL fxmlURL;

    @FXML
    private Button ButtonDictionary;

    @FXML
    private Button ButtonGame;

    @FXML
    private Button ButtonTheme;

    @FXML
    private Button ButtonTranslate;

    @FXML
    private ImageView button_close_icon;

    @FXML
    private Pane WorkPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: open Dictionary
        try {
            dictionaryScene(new ActionEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetButtons() {
        ButtonDictionary.setDisable(false);
        ButtonGame.setDisable(false);
        ButtonTranslate.setDisable(false);
    }

    public void dictionaryScene(ActionEvent event) throws IOException {
        fxmlURL = MainController.class.getResource("/fxml/dictionary-screen.fxml");
        WorkPane.getChildren().clear();
        WorkPane.getChildren().add(FXMLLoader.load(fxmlURL));
        resetButtons();
        ButtonDictionary.setDisable(true);
    }

    public void gameScene(ActionEvent event) throws IOException {
        fxmlURL = MainController.class.getResource("/fxml/game-screen.fxml");
        WorkPane.getChildren().clear();
        WorkPane.getChildren().add(FXMLLoader.load(fxmlURL));
        resetButtons();
        ButtonGame.setDisable(true);
    }

    public void translateScene(ActionEvent event) throws IOException {
        fxmlURL = MainController.class.getResource("/fxml/translate-screen.fxml");
        WorkPane.getChildren().clear();
        WorkPane.getChildren().add(FXMLLoader.load(fxmlURL));
        resetButtons();
        ButtonTranslate.setDisable(true);
    }

    public void aboutPopup(ActionEvent event) throws IOException {
        fxmlURL = MainController.class.getResource("/fxml/about-popup.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlURL));
        if (stage == null) {
            stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        }
        x = stage.getX();
        y = stage.getY();

        Stage popup = new Stage();
        Scene scene = new Scene(root, 350, 230);
        popup.setTitle("About us");
        popup.initStyle(StageStyle.UNDECORATED);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setResizable(false);
        popup.setScene(scene);
        popup.show();
        popup.setX(x + 337);
        popup.setY(y + 249);
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

    public void close_button_highlight(MouseEvent event) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1.0);
        button_close_icon.setEffect(colorAdjust);
    }

    public void close_button_reset(MouseEvent event) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);
        button_close_icon.setEffect(colorAdjust);
    }
}