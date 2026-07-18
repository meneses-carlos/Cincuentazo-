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


/**
 * Controller responsible for managing the main gameplay.
 * <p>
 * It coordinates the interaction between the view and the model,
 * handles player actions, AI turns, game state updates, and
 * synchronizes the user interface during the match.
 *
 * @author Carlos Meneses
 * @version 1.0
 * @since 1.0
 */
public class MainGameController {

    private GameState gameState;

    private int numberOfBots;

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
    /**
     * Initializes the controller and creates the game state.
     * The game setup is completed later through {@code initializeGame(int)}.
     */
    @FXML
    public void initialize() {

        gameState = new GameState();

    }

    /**
     * Initializes a new game with the selected number of AI players.
     *
     * @param numberOfBots number of AI opponents selected by the user
     */
    public void initializeGame(int numberOfBots){

        this.numberOfBots = numberOfBots;

        startGame();

    }


    /**
     * Creates the players, prepares the game, and initializes
     * the user interface.
     */
    public void startGame() {

        gameState.addPlayer(new Player("Player", false));

        for (int i = 1; i <= numberOfBots; i++) {

            gameState.addPlayer(
                    new Player("Bot " + i, true)
            );

        }


        try {

            gameState.prepareGame();
            updateGameView();

        } catch (EmptyDeckException e) {

            e.printStackTrace();

        }

    }


    /**
     * Marks the current game as finished.
     */
    public void finishGame() {

        gameState.setGameOver(true);

    }


    //=========================================================>PLAYER ACTION METHODS
    /**
     * Handles the action of playing a card selected by the human player.
     * <p>
     * If the selected card is an Ace, the player chooses whether its value
     * will be 1 or 10 before validating and playing the card.
     *
     * @param event mouse event generated when a card is clicked
     */
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

    /**
     * Draws a card from the deck and gives it to the current player.
     * <p>
     * If the deck is empty, it is rebuilt before drawing again.
     */
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
    /**
     * Executes the turn of an AI player.
     * <p>
     * The AI waits for a random time between two and four seconds,
     * selects a valid card, plays it, draws a replacement card,
     * and passes the turn to the next player.
     */
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
    /**
     * Advances the game to the next active player's turn.
     * If the next player is controlled by the AI,
     * its turn is executed automatically.
     */
    public void nextTurn() {

        gameState.nextTurn();

        updateGameView();

        Player currentPlayer = gameState.getCurrentPlayer();

        if (currentPlayer.isMachine()) {

            processMachineTurn();

        }

    }


    //=========================================================>UI UPDATE METHODS
    /**
     * Refreshes all visual components of the game interface.
     */
    public void updateGameView() {

        updatePlayerHand();
        updateCurrentCard();
        updateTotal();
        updatePlayers();
        updateTurnIndicator();

    }

    /**
     * Updates the human player's hand displayed on the screen.
     */
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

    /**
     * Updates the visible card placed on the table.
     */
    public void updateCurrentCard() {

        currentCard.setImage(
                loadCardImage(gameState.getTable().getVisibleCard())
        );

    }

    /**
     * Updates the accumulated total shown on the interface.
     */
    public void updateTotal() {

        int accumulated = gameState.getTable().getAccumulated();

        total.setText(String.valueOf(accumulated));

    }

    /**
     * Updates the visual representation of the active players.
     */
    public void updatePlayers() {
    }

    /**
     * Moves the turn indicator to the current active player.
     */
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
    /**
     * Verifies whether the current player can continue playing.
     * If no valid moves remain, the player is eliminated.
     */
    public void checkPlayerStatus() {

        Player currentPlayer = gameState.getCurrentPlayer();

        boolean canContinue = gameState.getRules().hasPlayableCard(currentPlayer.getHand(), gameState.getTable().getAccumulated());

        if (!canContinue) {

            eliminatePlayer();

            checkWinner();

        }

    }

    /**
     * Eliminates the current player from the game and updates
     * the interface accordingly.
     */
    public void eliminatePlayer() {

        Player currentPlayer = gameState.getCurrentPlayer();

        gameState.eliminatePlayer(currentPlayer);

        updatePlayers();

    }

    /**
     * Checks whether only one active player remains.
     * If so, the game ends and the winner is announced.
     */
    public void checkWinner() {

        List<Player> activePlayers = gameState.getActivePlayers();

        if (activePlayers.size() == 1) {

            finishGame();

            showWinner();

        }

    }


    //=========================================================>END GAME METHODS
    /**
     * Displays the winner of the game and closes the application.
     */
    public void showWinner() {

        Player winner = gameState.getActivePlayers().get(0);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("The winner is " + winner.getName() + "!");

        alert.showAndWait();

        System.exit(0);

    }

    /**
     * Loads the image corresponding to the given playing card.
     *
     * @param card card whose image will be loaded
     * @return the image associated with the specified card
     */
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


