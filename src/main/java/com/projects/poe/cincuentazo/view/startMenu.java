package com.projects.poe.cincuentazo.view;

import com.projects.poe.cincuentazo.mainGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class startMenu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(mainGame.class.getResource("startMenuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("50tazo");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
}
