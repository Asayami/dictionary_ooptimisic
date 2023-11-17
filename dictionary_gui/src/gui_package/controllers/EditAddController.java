package gui_package.controllers;

import gui_package.models.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.*;

import static gui_package.models.MainModel.getWord;
import static gui_package.models.MainModel.removeWord;

public class EditAddController {
    private double x;
    private double y;
    DictionaryController dic = new DictionaryController();

    private Word currentWord;

    @FXML
    private Button setEditButton;

    @FXML
    private Button button_close;

    @FXML
    private ImageView button_close_icon;

    @FXML
    private ImageView editWordImage;

    @FXML
    private TextField wordTypeTextField;

    @FXML
    private TextField pronunciationTextField;

    @FXML
    private TextArea meaningTextArea;

    @FXML
    private TextArea exampleTextArea;

    @FXML
    private Button removeWordButton;

    @FXML
    public void titleBarDragged(MouseEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    public void titleBarPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void close_button_highlight(MouseEvent mouseEvent) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1.0);
        button_close_icon.setEffect(colorAdjust);
    }

    public void close_button_reset(MouseEvent mouseEvent) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);
        button_close_icon.setEffect(colorAdjust);
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    @FXML
    public void removeSelectedWord() throws SQLException {
        removeWordButton.setOnAction(event -> {
            try {
                removeWord(dic.currentWordId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void editedWordContent() throws SQLException {
        currentWord = getWord(dic.currentWordId);
        currentWord.setWord_type(wordTypeTextField.getText());
        currentWord.setPronunciation((pronunciationTextField).getText());
        currentWord.setWord_explain((meaningTextArea).getText());
        currentWord.setExample(exampleTextArea.getText());
    }
}
