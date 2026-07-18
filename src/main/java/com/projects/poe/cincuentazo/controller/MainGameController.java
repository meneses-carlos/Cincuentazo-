package com.projects.poe.cincuentazo.controller;

import com.projects.poe.cincuentazo.model.Card;
import com.projects.poe.cincuentazo.model.GameState;
import com.projects.poe.cincuentazo.model.Player;
import com.projects.poe.cincuentazo.model.exceptions.EmptyDeckException;
import com.projects.poe.cincuentazo.model.exceptions.InvalidCardException;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;

public class MainGameController {

    private GameState gameState;

    @FXML
    private ImageView playerCard1;

    @FXML
    private ImageView playerCard2;

    @FXML
    private ImageView playerCard3;

    @FXML
    private ImageView playerCard4;

    @FXML
    private ImageView currentCard;

    @FXML
    private Label total;

    @FXML
    private ImageView turnIndicator;


    //========================================================>GAME LIFE CYCLE METHODS
    //Initialize game components
    @FXML
    public void initialize() {

        gameState = new GameState();

        startGame();

    }

    //Start a new game
    public void startGame() {

        gameState.addPlayer(new Player("Player", false));
        gameState.addPlayer(new Player("Bot 1", true));
        gameState.addPlayer(new Player("Bot 2", true));
        gameState.addPlayer(new Player("Bot 3", true));


        try {

            gameState.prepareGame();
            updateGameView();

        } catch (EmptyDeckException e) {

            e.printStackTrace();

        }

    }

    //End the current game
    public void finishGame() {

        gameState.setGameOver(true);

    }


    //=========================================================>PLAYER ACTION METHODS
    //Play the selected card
    @FXML
    public void playCard(MouseEvent event) {

        Player currentPlayer = gameState.getCurrentPlayer();

        if (currentPlayer.isMachine()) {
            return;
        }

        ImageView clickedCard = (ImageView) event.getSource();

        int cardIndex;

        if (clickedCard == playerCard1) {
            cardIndex = 0;
        } else if (clickedCard == playerCard2) {
            cardIndex = 1;
        } else if (clickedCard == playerCard3) {
            cardIndex = 2;
        } else {
            cardIndex = 3;
        }

        Card selectedCard = currentPlayer.getHand().get(cardIndex);

        try {

            Card playedCard;

            if (selectedCard.getLabel().equals("A")) {

                ButtonType one = new ButtonType("1");
                ButtonType ten = new ButtonType("10");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Valor del As");
                alert.setHeaderText("Selecciona el valor del As");
                alert.setContentText("¿Deseas que el As valga 1 o 10?");
                alert.getButtonTypes().setAll(one, ten);

                Optional<ButtonType> result = alert.showAndWait();

                int aceValue;

                if (result.isPresent() && result.get() == ten) {
                    aceValue = 10;
                } else {
                    aceValue = 1;
                }

                gameState.getRules().validatePlay(
                        selectedCard,
                        gameState.getTable().getAccumulated(),
                        aceValue
                );

                playedCard = currentPlayer.playCard(cardIndex);

                gameState.getTable().placeCard(
                        playedCard,
                        aceValue
                );

            } else {

                gameState.getRules().validatePlay(
                        selectedCard,
                        gameState.getTable().getAccumulated()
                );

                playedCard = currentPlayer.playCard(cardIndex);

                gameState.getTable().placeCard(playedCard);

            }

            drawCard();
            updateGameView();
            checkPlayerStatus();
            nextTurn();

        } catch (InvalidCardException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Jugada inválida");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());

            alert.showAndWait();

        }

    }

    //Draw a card from the deck
    public void drawCard() {

        try {

            Card card = gameState.getDeck().drawCard();
            gameState.getCurrentPlayer().receiveCard(card);

        } catch (EmptyDeckException e) {

            gameState.handleEmptyDeck();

            try {

                Card card = gameState.getDeck().drawCard();
                gameState.getCurrentPlayer().receiveCard(card);

            } catch (EmptyDeckException ex) {

                ex.printStackTrace();

            }

        }

    }


    //=========================================================>AI METHODS
    //Execute an AI player's turn
    public void processMachineTurn() {

        new Thread(() -> {

            try {

                // Espera entre 2 y 4 segundos
                Thread.sleep((long) (2000 + Math.random() * 2000));

                Platform.runLater(() -> {

                    Player currentPlayer = gameState.getCurrentPlayer();

                    int accumulated = gameState.getTable().getAccumulated();

                    for (int i = 0; i < currentPlayer.getHand().size(); i++) {

                        Card card = currentPlayer.getHand().get(i);

                        try {

                            Card playedCard;

                            if (card.getLabel().equals("A")) {

                                int aceValue;

                                if (gameState.getRules().isCardPlayable(card, accumulated, 10)) {
                                    aceValue = 10;
                                } else if (gameState.getRules().isCardPlayable(card, accumulated, 1)) {
                                    aceValue = 1;
                                } else {
                                    continue;
                                }

                                playedCard = currentPlayer.playCard(i);

                                gameState.getTable().placeCard(playedCard, aceValue);

                            } else {

                                gameState.getRules().validatePlay(card, accumulated);

                                playedCard = currentPlayer.playCard(i);

                                gameState.getTable().placeCard(playedCard);

                            }

                            // Espera antes de robar
                            new Thread(() -> {

                                try {

                                    Thread.sleep((long) (2000 + Math.random() * 2000));

                                    Platform.runLater(() -> {

                                        drawCard();
                                        updateGameView();
                                        checkPlayerStatus();
                                        nextTurn();

                                    });

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }).start();

                            return;

                        } catch (InvalidCardException e) {

                            // La carta no sirve, probar la siguiente.

                        }

                    }

                    // No encontró ninguna jugada válida
                    eliminatePlayer();
                    checkWinner();
                    nextTurn();

                });

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }).start();

    }


    //=========================================================>TURN MANAGEMENT METHODS
    //Move to the next player
    public void nextTurn() {

        gameState.nextTurn();

        updateGameView();

        Player currentPlayer = gameState.getCurrentPlayer();

        if (currentPlayer.isMachine()) {

            processMachineTurn();

        }

    }


    //=========================================================>UI UPDATE METHODS
    //Refresh the interface
    public void updateGameView() {

        updatePlayerHand();
        updateCurrentCard();
        updateTotal();
        updatePlayers();
        updateTurnIndicator();

    }

    //Update the player's hand in the UI
    public void updatePlayerHand() {

        Player humanPlayer = gameState.getPlayers().get(0);

        List<Card> hand = humanPlayer.getHand();

        ImageView[] playerCards = {
                playerCard1,
                playerCard2,
                playerCard3,
                playerCard4
        };

        for (int i = 0; i < hand.size(); i++) {

            playerCards[i].setImage(loadCardImage(hand.get(i)));

        }

    }

    //Update the current card in the UI
    public void updateCurrentCard() {

        currentCard.setImage(
                loadCardImage(gameState.getTable().getVisibleCard())
        );

    }

    //Update the total points in the UI
    public void updateTotal() {

        int accumulated = gameState.getTable().getAccumulated();

        total.setText(String.valueOf(accumulated));

    }

    //Update the active players in the UI
    public void updatePlayers() {
    }

    //Update the turn indicator in the UI
    public void updateTurnIndicator() {

        Player currentPlayer = gameState.getCurrentPlayer();

        int index = gameState.getPlayers().indexOf(currentPlayer);

        switch (index) {

            case 0:
                turnIndicator.setLayoutX(870);
                turnIndicator.setLayoutY(670);
                break;

            case 1:
                turnIndicator.setLayoutX(240);
                turnIndicator.setLayoutY(220);
                break;

            case 2:
                turnIndicator.setLayoutX(870);
                turnIndicator.setLayoutY(20);
                break;

            case 3:
                turnIndicator.setLayoutX(1160);
                turnIndicator.setLayoutY(220);
                break;

        }

    }


    //=========================================================>GAME STATE METHODS
    //Check if a player can continue
    public void checkPlayerStatus() {

        Player currentPlayer = gameState.getCurrentPlayer();

        boolean canContinue = gameState.getRules().hasPlayableCard(currentPlayer.getHand(), gameState.getTable().getAccumulated());

        if (!canContinue) {

            eliminatePlayer();

            checkWinner();

        }

    }

    //Remove player from the game
    public void eliminatePlayer() {

        Player currentPlayer = gameState.getCurrentPlayer();

        gameState.eliminatePlayer(currentPlayer);

        updatePlayers();

    }

    //Verify if the game has a winner
    public void checkWinner() {

        List<Player> activePlayers = gameState.getActivePlayers();

        if (activePlayers.size() == 1) {

            finishGame();

            showWinner();

        }

    }


    //=========================================================>END GAME METHODS
    //Show the winner of the game
    public void showWinner() {

        Player winner = gameState.getActivePlayers().get(0);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("The winner is " + winner.getName() + "!");

        alert.showAndWait();

        System.exit(0);

    }

    private Image loadCardImage(Card card) {

        int suitOffset = card.getSuit().ordinal() * 13;

        int valueOffset;

        switch (card.getLabel()) {
            case "A":
                valueOffset = 13;
                break;
            case "J":
                valueOffset = 10;
                break;
            case "Q":
                valueOffset = 11;
                break;
            case "K":
                valueOffset = 12;
                break;
            default:
                valueOffset = Integer.parseInt(card.getLabel()) - 1;
                break;
        }

        int imageNumber = suitOffset + valueOffset;

        String path = "/com/projects/poe/cincuentazo/cardImages/front/" + imageNumber + ".png";

        return new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream(path)
                )
        );
    }

}


