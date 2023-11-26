package gui_package.controllers;

import gui_package.Start;
import gui_package.models.MainModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    private Node dictionaryNode;
    private Node gameNode;
    private Node translateNode;

    public static boolean isMusicOn = true;

    Media bgmMedia = new Media(Objects.requireNonNull(SoundController.class.getResource("/sounds/bgm.mp3")).toString());
    MediaPlayer mediaPlayer = new MediaPlayer(bgmMedia);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: open Dictionary
        try {
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
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
        SoundController.makeSound("tab");
        if (dictionaryNode == null) {
            fxmlURL = MainController.class.getResource("/fxml/dictionary-screen.fxml");
            assert fxmlURL != null;
            dictionaryNode = FXMLLoader.load(fxmlURL);
            WorkPane.getChildren().add(dictionaryNode);
        }
        dictionaryNode.setDisable(false);
        dictionaryNode.setVisible(true);
        if (gameNode != null) {
            gameNode.setDisable(true);
            gameNode.setVisible(false);
        }
        if (translateNode != null) {
            translateNode.setDisable(true);
            translateNode.setVisible(false);
        }
        resetButtons();
        ButtonDictionary.setDisable(true);
    }

    public void gameScene(ActionEvent event) throws IOException {
        SoundController.makeSound("tab");
        if (gameNode == null) {
            fxmlURL = MainController.class.getResource("/fxml/game-screen.fxml");
            assert fxmlURL != null;
            gameNode = FXMLLoader.load(fxmlURL);
            WorkPane.getChildren().add(gameNode);
        }
        gameNode.setDisable(false);
        gameNode.setVisible(true);
        if (dictionaryNode != null) {
            dictionaryNode.setDisable(true);
            dictionaryNode.setVisible(false);
        }
        if (translateNode != null) {
            translateNode.setDisable(true);
            translateNode.setVisible(false);
        }
        resetButtons();
        ButtonGame.setDisable(true);
    }

    public void translateScene(ActionEvent event) throws IOException {
        SoundController.makeSound("tab");
        if (translateNode == null) {
            fxmlURL = MainController.class.getResource("/fxml/translate-screen.fxml");
            assert fxmlURL != null;
            translateNode = FXMLLoader.load(fxmlURL);
            WorkPane.getChildren().add(translateNode);
        }
        translateNode.setDisable(false);
        translateNode.setVisible(true);
        if (dictionaryNode != null) {
            dictionaryNode.setDisable(true);
            dictionaryNode.setVisible(false);
        }
        if (gameNode != null) {
            gameNode.setDisable(true);
            gameNode.setVisible(false);
        }
        resetButtons();
        ButtonTranslate.setDisable(true);
    }

    public void aboutPopup(ActionEvent event) throws IOException {
        SoundController.makeSound("click");
        fxmlURL = MainController.class.getResource("/fxml/about-popup.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlURL));
        if (stage == null) {
            stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        }
        x = stage.getX();
        y = stage.getY();

        Stage popup = new Stage();
        Scene scene = new Scene(root, 350, 230);
        popup.setTitle("Dictionary Ultra Pro");
        popup.getIcons().add(new Image(String.valueOf(Start.class.getResource("views/images/logo.png"))));
        popup.initStyle(StageStyle.UNDECORATED);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setResizable(false);
        popup.setScene(scene);
        popup.show();
        popup.setX(x + 337);
        popup.setY(y + 249);
    }

    public void musicStateChange(ActionEvent event) {
        SoundController.makeSound("click");
        ImageView musicChangeImage = (ImageView) ((Node) event.getSource()).getScene().lookup("#musicChangeImage");
        if (isMusicOn) {
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/soundOn.png")).toExternalForm());
            musicChangeImage.setImage(image);
            isMusicOn = false;
            mediaPlayer.pause();
        } else {
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/soundOff.png")).toExternalForm());
            musicChangeImage.setImage(image);
            isMusicOn = true;
            mediaPlayer.play();
        }
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
        SoundController.makeSound("click");
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.setIconified(true);
    }

    public void close(ActionEvent event) {
        SoundController.makeSound("click");
        try {
            MainModel.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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