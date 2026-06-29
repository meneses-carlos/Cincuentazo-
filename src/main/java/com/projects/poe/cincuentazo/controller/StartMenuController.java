package com.projects.poe.cincuentazo.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartMenuController {

    @FXML
    private ComboBox<Integer> numberOfPlayersComboBox;


    @FXML
    public void initialize() {
        numberOfPlayersComboBox
                .getItems()
                .addAll(1, 2, 3);
        hideWarning();
    }

    @FXML
    private Button playButton;

    @FXML
    private Label warningLabel;

   


    // Validate menu input
    public boolean validateSelection(){
        if(numberOfPlayersComboBox.getValue() == null){
            showWarning("Selecciona para jugar");
            return false;
        }
        hideWarning();

        return true;
    }


    // Start the game
    public void startGame() {

        if (validateSelection()) {
            loadGameView();
        }
    }

    // Load the game scene
    public void loadGameView() {

    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projects/poe/cincuentazo/mainGameView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    catch (IOException e) {

        e.printStackTrace();
    }
}

    // Display a warning message
    public void showWarning(String message) {
        warningLabel.setText(message);
        warningLabel.setVisible(true);
    }

    // Hide warning messages
    public void hideWarning() {
        warningLabel.setText("");
        warningLabel.setVisible(false);
    }

}

