package com.projects.poe.cincuentazo.model;

import com.projects.poe.cincuentazo.model.exceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.List;

/**
 * Mantiene el estado global de una partida de Cincuentazo.
 *
 * <p>Es el punto de entrada principal que usan los controladores
 * para consultar y modificar la partida. Coordina los jugadores,
 * la mesa, el mazo y las reglas durante toda la partida.</p>
 *
 * @author Jorge
 * @version 1.0
 */
public class GameState {

    private final List<Player> players;
    private final Table table;
    private final Deck deck;
    private final GameRules rules;
    private int currentTurnIndex;
    private boolean gameOver;

    /**
     * Construye un estado de juego inicial con mesa, mazo y reglas listos.
     */
    public GameState() {
        this.players          = new ArrayList<>();
        this.table            = new Table();
        this.deck             = new Deck();
        this.rules            = new GameRules();
        this.currentTurnIndex = 0;
        this.gameOver         = false;
    }

    /**
     * Prepara la partida: inicializa el mazo, reparte 4 cartas
     * a cada jugador y coloca una carta inicial en la mesa.
     *
     * @throws EmptyDeckException si el mazo se vacía durante el reparto
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
     * Agrega un jugador a la partida.
     *
     * @param player jugador a agregar
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Retorna la lista completa de jugadores, activos e inactivos.
     *
     * @return lista de todos los jugadores
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Retorna únicamente los jugadores que siguen activos en la partida.
     *
     * @return lista de jugadores activos
     */
    public List<Player> getActivePlayers() {
        List<Player> active = new ArrayList<>();
        for (Player p : players) {
            if (p.isActive()) {
                active.add(p);
            }
        }
        return active;
    }

    /**
     * Retorna el jugador cuyo turno es el actual.
     *
     * @return jugador actual, o {@code null} si no hay jugadores activos
     */
    public Player getCurrentPlayer() {
        List<Player> active = getActivePlayers();
        if (active.isEmpty()) return null;
        return active.get(currentTurnIndex % active.size());
    }

    /**
     * Avanza el turno al siguiente jugador activo.
     */
    public void nextTurn() {
        List<Player> active = getActivePlayers();
        if (!active.isEmpty()) {
            currentTurnIndex = (currentTurnIndex + 1) % active.size();
        }
    }

    /**
     * Elimina un jugador de la partida y envía sus cartas al mazo.
     *
     * @param player jugador a eliminar
     */
    public void eliminatePlayer(Player player) {
        player.setActive(false);
        deck.addEliminatedPlayerCards(player.getHand());
        player.getHand().clear();
    }

    /**
     * Maneja el mazo vacío reciclando las cartas de la mesa.
     * La suma de la mesa no se modifica.
     */
    public void handleEmptyDeck() {
        List<Card> recycled = table.recycleCards();
        deck.addRecycledCards(recycled);
    }

    /**
     * Retorna la mesa de juego actual.
     *
     * @return mesa de la partida
     */
    public Table getTable() {
        return table;
    }

    /**
     * Retorna el mazo de cartas de la partida.
     *
     * @return mazo de la partida
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Retorna las reglas del juego.
     *
     * @return reglas del Cincuentazo
     */
    public GameRules getRules() {
        return rules;
    }

    /**
     * Indica si la partida ha terminado.
     *
     * @return {@code true} si el juego terminó
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Establece el estado de fin de juego.
     *
     * @param gameOver {@code true} para marcar la partida como terminada
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}