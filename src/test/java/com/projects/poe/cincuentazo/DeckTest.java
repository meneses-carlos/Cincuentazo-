package com.projects.poe.cincuentazo;

import com.projects.poe.cincuentazo.model.Card;
import com.projects.poe.cincuentazo.model.Deck;
import com.projects.poe.cincuentazo.model.exceptions.EmptyDeckException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias para {@link Deck}.
 *
 * <p>Cubre la inicialización del mazo, la extracción de cartas, el
 * reciclaje de cartas de la mesa y la recepción de cartas de un
 * jugador eliminado.</p>
 */
class DeckTest {

    private Deck deck;

    // Se crea un mazo nuevo antes de cada prueba
    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    @DisplayName("Un mazo recien creado, sin inicializar, debe estar vacio")
    void newDeckShouldBeEmptyBeforeInitialize() {
        assertTrue(deck.isEmpty());
        assertEquals(0, deck.getRemainingCount());
    }

    @Test
    @DisplayName("initialize() debe crear las 52 cartas de la baraja de poker")
    void initializeShouldCreate52Cards() {
        deck.initialize();

        assertFalse(deck.isEmpty());
        assertEquals(52, deck.getRemainingCount());
    }

    @Test
    @DisplayName("drawCard() debe reducir en uno la cantidad de cartas restantes")
    void drawCardShouldReduceRemainingCount() throws EmptyDeckException {
        deck.initialize();
        int before = deck.getRemainingCount();

        Card drawn = deck.drawCard();

        assertEquals(before - 1, deck.getRemainingCount());
        assertTrue(drawn != null);
    }

    @Test
    @DisplayName("drawCard() debe lanzar EmptyDeckException cuando el mazo esta vacio")
    void drawCardShouldThrowWhenDeckIsEmpty() {
        // El mazo nunca fue inicializado, por lo tanto esta vacio
        assertThrows(EmptyDeckException.class, () -> deck.drawCard());
    }

    @Test
    @DisplayName("drawCard() debe vaciar el mazo tras extraer las 52 cartas")
    void drawCardShouldEmptyDeckAfterDrawingAllCards() throws EmptyDeckException {
        deck.initialize();

        for (int i = 0; i < 52; i++) {
            deck.drawCard();
        }

        assertTrue(deck.isEmpty());
        assertThrows(EmptyDeckException.class, () -> deck.drawCard());
    }

    @Test
    @DisplayName("addRecycledCards() debe devolver cartas al mazo cuando este esta vacio")
    void addRecycledCardsShouldRefillEmptyDeck() {
        List<Card> recycled = new ArrayList<>();
        recycled.add(new Card(Card.Suit.HEARTS, 5, Card.Effect.ADD, "5"));
        recycled.add(new Card(Card.Suit.SPADES, 10, Card.Effect.SUBTRACT, "K"));

        assertTrue(deck.isEmpty());

        deck.addRecycledCards(recycled);

        assertFalse(deck.isEmpty());
        assertEquals(2, deck.getRemainingCount());
    }

    @Test
    @DisplayName("addEliminatedPlayerCards() debe agregar las cartas del jugador eliminado al mazo")
    void addEliminatedPlayerCardsShouldIncreaseRemainingCount() {
        deck.initialize();
        int before = deck.getRemainingCount();

        List<Card> eliminatedHand = new ArrayList<>();
        eliminatedHand.add(new Card(Card.Suit.CLUBS, 1, Card.Effect.ADD, "A"));
        eliminatedHand.add(new Card(Card.Suit.DIAMONDS, 9, Card.Effect.NONE, "9"));

        deck.addEliminatedPlayerCards(eliminatedHand);

        assertEquals(before + 2, deck.getRemainingCount());
    }

    @Test
    @DisplayName("shuffle() no debe alterar la cantidad de cartas en el mazo")
    void shuffleShouldNotChangeCardCount() {
        deck.initialize();
        int before = deck.getRemainingCount();

        deck.shuffle();

        assertEquals(before, deck.getRemainingCount());
    }
}
