package gui_package.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class DictionaryController {
    private final ArrayList<String> words = new ArrayList<>(
            Arrays.asList("asshole", "bitch", "cunt", "dickhead", "retard", "motherfucker", "nibba"));
    private String lastWord;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> listView;

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

    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word -> input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }

}
