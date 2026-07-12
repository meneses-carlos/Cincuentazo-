package com.projects.poe.cincuentazo.model;

import com.projects.poe.cincuentazo.model.exceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the global state of a Cincuentazo game.
 * Coordinates the players, the table, the deck and the rules throughout the game.
 *
 * @author Jorge Navia
 * @version 1.5
 * @since 1.0
 */
public class GameState {

    private final List<Player> players;
    private final Table table;
    private final Deck deck;
    private final GameRules rules;
    private int currentTurnIndex;
    private boolean gameOver;

    /**
     * Builds an initial game state with the table, deck and rules ready.
     */
    public GameState() {
        this.players = new ArrayList<>();
        this.table = new Table();
        this.deck = new Deck();
        this.rules = new GameRules();
        this.currentTurnIndex = 0;
        this.gameOver = false;
    }

    /**
     * Prepares the game: initializes the deck, deals 4 cards
     * to each player and places an initial card on the table.
     *
     * @throws EmptyDeckException if the deck runs out during the deal
     */
    public void prepareGame() throws EmptyDeckException {
        deck.initialize();

        // Repartir 4 cartas a cada jugador
        for (Player player : players) {
            for (int i = 0; i < 4; i++) {
                player.receiveCard(deck.drawCard());
            }
        }

        // Colocar carta inicial en la mesa
        table.placeCard(deck.drawCard());
    }

    /**
     * Adds a player to the game.
     *
     * @param player player to add
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Returns the full list of players, both active and inactive.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns only the players who are still active in the game.
     */
    public List<Player> getActivePlayers() {
        List<Player> active = new ArrayList<>();
        for (Player player : players) {
            if (player.isActive()) {
                active.add(player);
            }
        }
        return active;
    }

    /**
     * Returns the player whose turn is current.
     *
     * @return current player, or {@code null} if there are no active players
     */
    public Player getCurrentPlayer() {
        List<Player> active = getActivePlayers();
        if (active.isEmpty()) {
            return null;
        }
        return active.get(currentTurnIndex % active.size());
    }

    /**
     * Advances the turn to the next active player.
     */
    public void nextTurn() {
        List<Player> active = getActivePlayers();
        if (!active.isEmpty()) {
            currentTurnIndex = (currentTurnIndex + 1) % active.size();
        }
    }

    /**
     * Eliminates a player from the game and sends their cards to the deck.
     *
     * @param player player to eliminate
     */
    public void eliminatePlayer(Player player) {
        player.setActive(false);
        deck.addEliminatedPlayerCards(player.getHand());
        player.getHand().clear();
    }

    /**
     * Handles the empty deck by recycling the table's cards.
     * The table's total is not modified.
     */
    public void handleEmptyDeck() {
        List<Card> recycled = table.recycleCards();
        deck.addRecycledCards(recycled);
    }

    /**
     * Returns the current game table.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Returns the game's card deck.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Returns the rules of the game.
     */
    public GameRules getRules() {
        return rules;
    }

    /**
     * Indicates whether the game has ended.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Sets the game-over state.
     *
     * @param gameOver {@code true} to mark the game as finished
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
