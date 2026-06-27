package com.projects.poe.cincuentazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGame.class.getResource("mainGameView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1536, 1024);
        stage.setTitle("50tazo");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }
}
