package gui_package.controllers;

import gui_package.models.MainModel;
import gui_package.models.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.Objects;


public class GameController {

    @FXML
    private Label c11;

    private int currentRow = 1;

    private String wordRow = "";

    private String selectedWord = "WHERE";//MainModel.getWordleWord().toUpperCase();

    private boolean isGameFinished = false;

    public GameController() {
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
        Button ts = DialogController.appear(((Node) event.getSource()).getScene(), false, "How To Play", "• Đoán một từ có 5 chữ cái có nghĩa và được phép đoán 6 lần.\n• Màu của ô chữ sẽ thay đổi dựa trên độ tương đồng với từ cần tìm.", 184);
        ts.setOnAction(eventHandler -> {
            //run when press okay
            DialogController.okay();
        });
    }

    @FXML
    private void restartGame(ActionEvent event) {
        Button ts = DialogController.appear(((Node) event.getSource()).getScene(), true, "Alert", "Do you want to start a new game ?", 174);
        ts.setOnAction(eventHandler -> {
            try {
                Scene scene = c11.getScene();
                //5x6 board
                for (int i = 1; i <= 6; i++) {
                    for (int j = 1; j <= 5; j++) {

                        Label label = (Label) scene.lookup("#c" + i + j);
                        label.setText("");
                        StackPane stackPane = (StackPane) label.getParent();
                        stackPane.setStyle("-fx-background-color: white;-fx-background-radius: 5;-fx-border-color: #d3d6da;-fx-border-radius:5;-fx-border-width:2;");
                    }
                }

                //A-Z keyboard
                for (char c = 'A'; c <= 'Z'; c++) {
                    StackPane stackPaneChar = (StackPane) (scene.lookup("#" + c));
                    stackPaneChar.setStyle("-fx-background-color: #d3d6da;-fx-background-radius:5;");
                    Label labelChar = (Label) stackPaneChar.getChildren().get(0);
                    labelChar.setStyle("-fx-text-fill: black;");
                }

                wordRow = "";
                currentRow = 1;
                selectedWord = MainModel.getWordleWord().toUpperCase();
                isGameFinished = false;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //run when press okay
            DialogController.okay();
        });
    }

    @FXML
    private void userStatistic(ActionEvent event) {
        String message = "Games Played: 2.\nWin Rate: 100%.\nCurrent Streak: 2.\nMax Streak: 2.\nGuess Distribution: 3.5.";
        Button ts = DialogController.appear(((Node) event.getSource()).getScene(), false, "Statistics", message, 200); //chinh true thanh false de an nut cancel
        ts.setOnAction(eventHandler -> {
            //run when press okay
            DialogController.okay();
        });
    }

    private void addCharacter(MouseEvent event, String character) {
        if (currentRow <= 6 && wordRow.length() < 5 && !isGameFinished) {
            wordRow += character;
            Label label = (Label) ((Label) event.getSource()).getScene().lookup("#c" + currentRow + wordRow.length());
            label.setText(character);
            label.setTextFill(Color.BLACK);
        }
    }

    private void backspace(MouseEvent event) {
        if (!wordRow.isEmpty()) {
            Label label = (Label) ((StackPane) event.getSource()).getScene().lookup("#c" + currentRow + wordRow.length());
            label.setText("");
            wordRow = wordRow.substring(0, wordRow.length() - 1);
        }
    }

    private void enter(MouseEvent event) throws SQLException {
        if (wordRow.length() == 5 && currentRow <= 6) {
            Word checkValidWord = MainModel.getWord(wordRow);
            if (checkValidWord.getWord_target() != null) {
                for (int i = 0; i < wordRow.length(); i++) {
                    Label label = (Label) ((Label) event.getSource()).getScene().lookup("#c" + currentRow + (i + 1));
                    label.setTextFill(Color.WHITE);
                    StackPane stackPane = (StackPane) ((Label) event.getSource()).getScene().lookup("#c" + currentRow + (i + 1)).getParent();
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
                    gameFinishNoti();
                }
                currentRow += 1;
                wordRow = "";
            }
        }
    }

    private void gameFinishNoti() {
        Scene scene = c11.getScene();
        Button ts = DialogController.appear(scene, false, "Congratulation !!!", "You completed this game in " + currentRow + " word(s).", 174); //chinh true thanh false de an nut cancel
        ts.setOnAction(eventHandler -> {
            //run when press okay
            DialogController.okay();
        });
    }
}
