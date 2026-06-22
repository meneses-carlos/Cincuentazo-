package com.projects.poe.cincuentazo.controller;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class mainGameController {

    @FXML
    private  ImageView card;
    private TranslateTransition cardTransition;

    @FXML
    public void initialize() {

        cardTransition = new TranslateTransition(Duration.seconds(0.25),card);
        cardTransition.setToX(100);
        cardTransition.setToY(100);
        cardTransition.setInterpolator(Interpolator.EASE_OUT);



    }

    public void Animacion() {
        cardTransition.play();




    }













    }


