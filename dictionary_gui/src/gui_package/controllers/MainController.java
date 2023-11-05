package gui_package.controllers;

import gui_package.models.MainModel;
import gui_package.models.Word;
import gui_package.services.tts;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    protected void tts_controller(String word) {
        tts speaker = new tts(word);
        speaker.start();
    }

}