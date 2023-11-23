package gui_package.controllers;

import gui_package.models.MainModel;
import gui_package.models.Word;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.events.Event;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class GameController {

    @FXML
    private Label c11;

    private int currentRow = 1;

    private String wordRow = "";

    private String selectedWord = "WHERE";//MainModel.getWordleWord().toUpperCase();

    private boolean isGameFinished = false;

    public GameController() throws SQLException {
        //clear();
    }

    @FXML
    private void mouseClick(MouseEvent event) throws SQLException {
        String objectId = event.getPickResult().getIntersectedNode().getId();
        if (Objects.equals(objectId, "backspaceButton")) {
            backspace(event);
        } else {
            Label label = (Label) event.getSource();
            String labelText = label.getText();
            if (Objects.equals(labelText, "Enter")) {
                enter(event);
            } else {
                addCharacter(event, labelText);
            }
        }
    }

    @FXML
    private void gameInfo(ActionEvent event) {
        Button ts = DialogController.appear(event, true, "", "");
        ts.setOnAction(eventHandler -> {
            //run when press okay
            System.out.println("Pressed Okay");
            DialogController.okay();
        });
    }

    private void addCharacter(MouseEvent event, String character) {
        if (currentRow <= 6 && wordRow.length() < 5 && !isGameFinished) {
            wordRow += character;
            Label label = (Label) ((Label) event.getSource()).getScene().lookup("#c" + Integer.toString(currentRow) + Integer.toString(wordRow.length()));
            label.setText(character);
            label.setTextFill(Color.BLACK);
        }
    }

    private void backspace(MouseEvent event) {
        if (!wordRow.isEmpty()) {
            Label label = (Label) ((StackPane) event.getSource()).getScene().lookup("#c" + Integer.toString(currentRow) + Integer.toString(wordRow.length()));
            label.setText("");
            wordRow = wordRow.substring(0, wordRow.length() - 1);
        }
    }

    private void enter(MouseEvent event) throws SQLException {
        if (wordRow.length() == 5 && currentRow <= 6) {
            Word checkValidWord = MainModel.getWord(wordRow);
            if (checkValidWord.getWord_target() != null) {
                for (int i = 0; i < wordRow.length(); i++) {
                    Label label = (Label) ((Label) event.getSource()).getScene().lookup("#c" + Integer.toString(currentRow) + Integer.toString(i + 1));
                    label.setTextFill(Color.WHITE);
                    StackPane stackPane = (StackPane) ((Label) event.getSource()).getScene().lookup("#c" + Integer.toString(currentRow) + Integer.toString(i + 1)).getParent();
                    char c = wordRow.charAt(i);
                    StackPane stackPaneChar = (StackPane) ((Label) event.getSource()).getScene().lookup("#" + c);
                    Label labelChar = (Label) stackPaneChar.getChildren().get(0);
                    if (selectedWord.indexOf(c) != -1) {
                        if (selectedWord.charAt(i) == c) {
                            stackPane.setStyle("-fx-background-color: #6aaa64;-fx-background-radius:5;");
                            stackPaneChar.setStyle("-fx-background-color: #6aaa64;-fx-background-radius:5;");
                        } else {
                            stackPane.setStyle("-fx-background-color: #c9b458;-fx-background-radius:5;");
                            stackPaneChar.setStyle("-fx-background-color: #c9b458;-fx-background-radius:5;");
                        }
                    } else {
                        stackPane.setStyle("-fx-background-color: #787c7e;-fx-background-radius:5;");
                        stackPaneChar.setStyle("-fx-background-color: #787c7e;-fx-background-radius:5;");
                    }
                    labelChar.setStyle("-fx-text-fill: white;");
                }
                if (wordRow.equals(selectedWord)) {
                    isGameFinished = true;
                }
                currentRow += 1;
                wordRow = "";
            }
        }
    }

    private void clear() {
        Scene scene = c11.getScene();
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 5; j++) {
                Label label = (Label) scene.lookup("#c" + Integer.toString(i) + Integer.toString(j));
                label.setText("");
                StackPane stackPane = (StackPane) label.getParent();
                stackPane.setStyle("-fx-background-color: white;");
            }
        }
    }

}
