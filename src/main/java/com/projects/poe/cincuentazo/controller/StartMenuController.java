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
        numberOfPlayersComboBox.getItems().addAll(1, 2, 3);
    }

    @FXML
    private Button playButton;

    @FXML
    private Label warningLabel;

}
