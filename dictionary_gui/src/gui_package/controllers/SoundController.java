package gui_package.controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundController extends Thread {
    String option;

    public SoundController(String option) {
        this.option = option;
    }

    @Override
    public void run() {
        makeSound(option);
    }

    public static void makeSound(String option) {
        String filename = "";
        if (option == "click") {

        } else if (option == "popup") {

        }
        Media hit = new Media(new File(filename).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();

    }
}
