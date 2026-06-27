package com.projects.poe.cincuentazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGame.class.getResource("startMenuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setTitle("50tazo");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // no esta hecha la transicion del inicio al principal si quieren verlos cambien el get resourse por el nombre del que quiere ver
        //ajusten el ancho y largo
        //main game 1048x1024
        //start menu 700x600
    }
}
