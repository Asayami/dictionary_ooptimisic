package gui_package.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class DictionaryController implements Initializable {
    private double x;
    private double y;
    private Stage stage;
    private URL fxmlURL;
    private final ArrayList<String> words = new ArrayList<>(
            Arrays.asList("asshole", "bitch", "cunt", "dickhead", "retard", "motherfucker", "nibba"));
    private String lastWord;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextArea wordDetails;

    @FXML
    void search(ActionEvent event) {
        listView.getItems().clear();
        listView.getItems().addAll(searchList(searchBar.getText(), words));
    }

    @FXML
    private void listViewClicked(MouseEvent event) {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (lastWord == null) {
            lastWord = "";
        }
        if (selectedItem != null && !selectedItem.equals(lastWord)) {
            System.out.println(selectedItem);
            lastWord = selectedItem;
        }
        listView.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: nothing
    }

    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word -> input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }

    private String getDetails(String word) {
        StringBuilder details = new StringBuilder("Definition: " + word);
        details.append('\n').append("This is a curse word.");
        return details.toString();
    }

    public void loadScene(URL fxmlURL, Event event) throws IOException {
        Parent root = FXMLLoader.load(fxmlURL);
        Scene scene = new Scene(root);
        if (stage == null) {
            stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        }
        stage.setScene(scene);
        stage.show();
    }

    public void gameScene(ActionEvent event) throws IOException {
        fxmlURL = DictionaryController.class.getResource("/fxml/game-screen.fxml");
        loadScene(fxmlURL, event);
    }

    public void translateScene(ActionEvent event) throws IOException {
        fxmlURL = DictionaryController.class.getResource("/fxml/translate-screen.fxml");
        loadScene(fxmlURL, event);
    }

    public void aboutPopup(ActionEvent event) throws IOException {
        fxmlURL = DictionaryController.class.getResource("/fxml/about-popup.fxml");
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
}
