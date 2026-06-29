package com.projects.poe.cincuentazo.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

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
    public void validateSelection() {

    }

    // Start the game
    public void startGame() {

    }

    // Load the game scene
    public void loadGameView() {

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

