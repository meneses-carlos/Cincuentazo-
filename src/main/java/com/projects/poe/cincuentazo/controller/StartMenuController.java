package com.projects.poe.cincuentazo.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Controller responsible for managing the start menu of the game.
 * <p>
 * It allows the player to select the number of AI opponents,
 * validates the menu input, and loads the main game scene.
 *
 * @author Carlos Meneses
 * @version 1.0
 * @since 1.0
 */
public class StartMenuController {

    @FXML
    private ComboBox<Integer> numberOfPlayersComboBox;

    /**
     * Initializes the start menu components.
     * <p>
     * Populates the player selection combo box and hides
     * the warning message.
     */
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


    /**
     * Validates that the user has selected the number of AI players.
     *
     * @return {@code true} if a valid selection exists;
     *         {@code false} otherwise
     */
    public boolean validateSelection(){
        if(numberOfPlayersComboBox.getValue() == null){
            showWarning("Selecciona para jugar");
            return false;
        }
        hideWarning();

        return true;
    }


    /**
     * Starts the game if the menu selection is valid.
     */
    public void startGame() {

        if (validateSelection()) {
            loadGameView();
        }
    }

    /**
     * Loads the main game view, initializes the game with the
     * selected number of AI players, and displays the new scene.
     */
    public void loadGameView() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projects/poe/cincuentazo/mainGameView.fxml"));
            Scene scene = new Scene(loader.load());
            MainGameController controller = loader.getController();
            controller.initializeGame(numberOfPlayersComboBox.getValue());
            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch (IOException e) {

            e.printStackTrace();

        }
    }

    /**
     * Displays a warning message in the start menu.
     *
     * @param message message to be displayed
     */
    public void showWarning(String message) {
        warningLabel.setText(message);
        warningLabel.setVisible(true);
    }

    /**
     * Hides the warning message from the start menu.
     */
    public void hideWarning() {
        warningLabel.setText("");
        warningLabel.setVisible(false);
    }

}

