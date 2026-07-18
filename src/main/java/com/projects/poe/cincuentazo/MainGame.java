package com.projects.poe.cincuentazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Entry point of the Cincuentazo application.
 * Loads the start menu as the initial scene.
 *
 * @author Jorge Navia
 * @version 1.1
 * @since 1.0
 */
public class MainGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGame.class.getResource("startMenuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setTitle("50tazo");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
