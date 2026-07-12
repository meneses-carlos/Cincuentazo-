package com.projects.poe.cincuentazo;

import com.projects.poe.cincuentazo.model.Card;
import com.projects.poe.cincuentazo.model.GameState;
import com.projects.poe.cincuentazo.model.Player;
import com.projects.poe.cincuentazo.model.exceptions.EmptyDeckException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias para {@link GameState}.
 *
 * <p>Cubre la preparación de la partida, la eliminación de jugadores
 * y el reciclaje de las cartas de la mesa cuando el mazo se vacía.</p>
 */
class GameStateTest {

    private GameState gameState;

    // Se crea un estado de juego nuevo antes de cada prueba
    @BeforeEach
    void setUp() {
        gameState = new GameState();
    }

    @Test
    @DisplayName("addPlayer() debe agregar jugadores a la lista de la partida")
    void addPlayerShouldAddPlayersToTheGame() {
        gameState.addPlayer(new Player("Ana", false));
        gameState.addPlayer(new Player("Maquina", true));

        assertEquals(2, gameState.getPlayers().size());
    }

    @Test
    @DisplayName("prepareGame() debe repartir 4 cartas a cada jugador y colocar una carta inicial en la mesa")
    void prepareGameShouldDealFourCardsAndPlaceInitialCard() throws EmptyDeckException {
        Player player1 = new Player("Ana", false);
        Player player2 = new Player("Luis", false);
        gameState.addPlayer(player1);
        gameState.addPlayer(player2);

        gameState.prepareGame();

        assertEquals(4, player1.getHand().size());
        assertEquals(4, player2.getHand().size());
        assertNotNull(gameState.getTable().getVisibleCard());

        // 52 cartas - 4 por jugador (2 jugadores) - 1 carta inicial de mesa
        assertEquals(52 - 4 * 2 - 1, gameState.getDeck().getRemainingCount());
    }

    @Test
    @DisplayName("prepareGame() debe lanzar EmptyDeckException si no hay cartas suficientes para repartir")
    void prepareGameShouldThrowWhenNotEnoughCards() {
        // 13 jugadores * 4 cartas = 52, mas la carta inicial de la mesa supera el mazo de 52 cartas
        for (int i = 0; i < 13; i++) {
            gameState.addPlayer(new Player("Jugador" + i, true));
        }

        assertThrows(EmptyDeckException.class, () -> gameState.prepareGame());
    }

    @Test
    @DisplayName("getActivePlayers() debe excluir a los jugadores eliminados")
    void getActivePlayersShouldExcludeEliminatedPlayers() {
        Player player1 = new Player("Ana", false);
        Player player2 = new Player("Luis", false);
        gameState.addPlayer(player1);
        gameState.addPlayer(player2);

        gameState.eliminatePlayer(player1);

        assertEquals(1, gameState.getActivePlayers().size());
        assertTrue(gameState.getActivePlayers().contains(player2));
        assertFalse(gameState.getActivePlayers().contains(player1));
    }

    @Test
    @DisplayName("eliminatePlayer() debe desactivar al jugador, vaciar su mano y devolver sus cartas al mazo")
    void eliminatePlayerShouldDeactivateAndReturnCardsToDeck() throws EmptyDeckException {
        Player player = new Player("Ana", false);
        gameState.addPlayer(player);
        gameState.prepareGame();
        int deckCountBeforeElimination = gameState.getDeck().getRemainingCount();
        int handSize = player.getHand().size();

        gameState.eliminatePlayer(player);

        assertFalse(player.isActive());
        assertTrue(player.getHand().isEmpty());
        assertEquals(deckCountBeforeElimination + handSize, gameState.getDeck().getRemainingCount());
    }

    @Test
    @DisplayName("handleEmptyDeck() debe reciclar las cartas de la mesa sin modificar el acumulado")
    void handleEmptyDeckShouldRecycleTableCardsWithoutChangingAccumulated() {
        gameState.getTable().placeCard(new Card(Card.Suit.HEARTS, 5, Card.Effect.ADD, "5"));
        gameState.getTable().placeCard(new Card(Card.Suit.CLUBS, 3, Card.Effect.ADD, "3"));
        int accumulatedBefore = gameState.getTable().getAccumulated();

        gameState.handleEmptyDeck();

        // Solo la ultima carta jugada queda en la mesa; el resto vuelve al mazo
        assertEquals(1, gameState.getDeck().getRemainingCount());
        assertEquals(accumulatedBefore, gameState.getTable().getAccumulated());
    }

    @Test
    @DisplayName("getCurrentPlayer() debe retornar null cuando no hay jugadores activos")
    void getCurrentPlayerShouldReturnNullWhenNoActivePlayers() {
        assertEquals(null, gameState.getCurrentPlayer());
    }

    @Test
    @DisplayName("getCurrentPlayer() debe retornar el primer jugador activo por defecto")
    void getCurrentPlayerShouldReturnFirstActivePlayer() {
        Player player1 = new Player("Ana", false);
        Player player2 = new Player("Luis", false);
        gameState.addPlayer(player1);
        gameState.addPlayer(player2);

        assertEquals(player1, gameState.getCurrentPlayer());
    }

    @Test
    @DisplayName("nextTurn() debe avanzar el turno al siguiente jugador activo")
    void nextTurnShouldAdvanceToNextActivePlayer() {
        Player player1 = new Player("Ana", false);
        Player player2 = new Player("Luis", false);
        gameState.addPlayer(player1);
        gameState.addPlayer(player2);

        gameState.nextTurn();

        assertEquals(player2, gameState.getCurrentPlayer());
    }

    @Test
    @DisplayName("isGameOver() y setGameOver() deben reflejar el estado de fin de partida")
    void gameOverFlagShouldBeSettable() {
        assertFalse(gameState.isGameOver());

        gameState.setGameOver(true);

        assertTrue(gameState.isGameOver());
    }
}
