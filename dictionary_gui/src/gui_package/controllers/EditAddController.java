package gui_package.controllers;

import gui_package.models.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.*;

import static gui_package.models.MainModel.*;

public class EditAddController {
    private double x;
    private double y;

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
    private TextField wordTargetTextField;

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
    private TextField addWordTarget;

    @FXML
    private TextField addWordType;

    @FXML
    private TextField addWordPronunciation;

    @FXML
    private TextArea addWordMeaning;

    @FXML
    private TextArea addWordExample;

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
    public void removeSelectedWord(ActionEvent event) throws SQLException {
//        System.out.println(dic.getSelectedWord().getWord_target());
//        System.out.println("Removing: " + dic.currentWord.getWord_target());
        Button okButton = DialogController.appear(((Node) event.getSource()).getScene(), true, "Confirm Removal",
                "Are you sure you want to remove this word?");
        okButton.setOnAction(eventHandler -> {
            try {
                removeWord(DictionaryController.currentWordId);
            } catch (NullPointerException e) {
                // when dic.currentWordId is null
                try {
                    removeWord(DictionaryController.currentWord.getWord_target());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void editedWordContent() throws SQLException {
        Word currWord = DictionaryController.currentWord;
        wordTargetTextField.setText(currWord.getWord_target());
        wordTargetTextField.setEditable(false);
        if (currWord == null) {
            // print: you can only edit a word if you have selected a word
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
        } else {
            updateWord(DictionaryController.currentWordId, wordTypeTextField.getText(),
                    meaningTextArea.getText(), pronunciationTextField.getText(), exampleTextArea.getText());
        }
//        editOrAddLabel.setText("Edit Word");
    }

    @FXML
    public void addWord() throws SQLException {
//        editOrAddLabel.setText("Add a new word!");
        if (findWord(addWordTarget.getText())) {
            Word word = new Word(addWordTarget.getText(), addWordType.getText(),
                    addWordMeaning.getText(), addWordPronunciation.getText(), addWordExample.getText());
            createWord(word);
        } else {
            System.out.println("This word already exists in the database!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
            //
        }
    }


}
