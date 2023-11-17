package gui_package.controllers;

import gui_package.models.Word;
import gui_package.services.tts;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static gui_package.models.MainModel.*;
import static gui_package.services.tts.speak;

public class DictionaryController implements Initializable {
    private final Set<String> setForListView = new HashSet<>();
    private String lastWord;
    private Word currentWord;
    protected int currentWordId = -1;
    private Node editNode;
    private URL fxmlURL;
    private Stage stage;
    private double x;
    private double y;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> listView;

    @FXML
    private Label searchedWordLabel;

    @FXML
    private Label wordFormLabel;

    @FXML
    private TextArea wordPronunciationTextArea;

    @FXML
    private TextArea wordMeaningTextArea;

    @FXML
    private TextArea wordExampleTextArea;

    @FXML
    private Button pronunciationButton;

    @FXML
    private Button copiedTextButton;

    @FXML
    private Button removeWordButton;

    @FXML
    private Button editWordButton;

    @FXML
    private ImageView editWordImage;

    @FXML
    private BorderPane editBorderPane;

    @FXML
    void search(KeyEvent event) throws SQLException {
        listView.getItems().clear();
        ResultSet resultSet = getWordByString(searchBar.getText());
        while (resultSet.next()) {
            String sword = resultSet.getString("Word");
            if (sword.isEmpty()) {
                continue;
            }
            setForListView.add(sword.toLowerCase());
        }

        // listView.getItems().addAll((getWordByString("")));
        listView.getItems().addAll(searchList(searchBar.getText(), setForListView));
    }

    @FXML
    private void listViewClicked(MouseEvent event) throws SQLException {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        currentWord = getWord(selectedItem);
        currentWordId = currentWord.getId();
        if (lastWord == null) {
            lastWord = "";
        }
        if (selectedItem != null && !selectedItem.equals(lastWord)) {
//            System.out.println(selectedItem);
            searchedWordLabel.setText(selectedItem);
            wordFormLabel.setText(Objects.requireNonNull(currentWord).getWord_type());
            wordPronunciationTextArea.setText(Objects.requireNonNull(currentWord).getPronunciation());
            wordMeaningTextArea.setText(Objects.requireNonNull(currentWord).getWord_explain());
            if (currentWord.getExample() == null) { //this doesn't really work
                wordExampleTextArea.setText("No example located in database!\n" + "Từ này chưa có ví dụ!");
            }
            else {
                wordExampleTextArea.setText(Objects.requireNonNull(currentWord).getExample());
            }
//            wordExampleTextArea.setScrollable(true);
            lastWord = selectedItem;
        }
        listView.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: disable copying
        wordMeaningTextArea.setTextFormatter(new TextFormatter<String>(change ->  {
            change.setAnchor(change.getCaretPosition());
            return change ;
        }));
        wordExampleTextArea.setTextFormatter(new TextFormatter<String>(change ->  {
            change.setAnchor(change.getCaretPosition());
            return change;
        }));
    }

    @FXML
    public void Pronunciation(ActionEvent event) {
        tts.speak(searchedWordLabel.getText());
    }

    private List<String> searchList(String searchWords, Set<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word -> input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }

    @FXML
    public void copyLabel(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(searchedWordLabel.getText());
        clipboard.setContent(content);
    }

    @FXML
    private void editScene(ActionEvent event) throws IOException {
        fxmlURL = EditAddController.class.getResource("/fxml/edit-add-box.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlURL));
        if (stage == null) {
            stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        }
        x = stage.getX();
        y = stage.getY();

        Stage popup = new Stage();
        Scene scene = new Scene(root, 560, 610);
        popup.setTitle("Edit word");
        popup.initStyle(StageStyle.UNDECORATED);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setResizable(false);
        popup.setScene(scene);
        popup.show();
        popup.setX(x + 232);
        popup.setY(y + 79);
    }
}
