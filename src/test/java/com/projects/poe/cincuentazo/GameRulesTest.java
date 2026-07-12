package com.projects.poe.cincuentazo;

import com.projects.poe.cincuentazo.model.Card;
import com.projects.poe.cincuentazo.model.GameRules;
import com.projects.poe.cincuentazo.model.exceptions.InvalidCardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias para {@link GameRules}.
 *
 * <p>Cubre la validación del límite de 50, el cálculo del acumulado
 * para cada efecto de carta y la detección de una carta jugable en mano.</p>
 */
class GameRulesTest {

    private GameRules rules;

    // Se crea una instancia nueva de las reglas antes de cada prueba
    @BeforeEach
    void setUp() {
        rules = new GameRules();
    }

    @Test
    @DisplayName("calculateNewAccumulated() debe sumar el valor cuando el efecto es ADD")
    void calculateNewAccumulatedShouldAddValue() {
        Card card = new Card(Card.Suit.HEARTS, 7, Card.Effect.ADD, "7");

        assertEquals(37, rules.calculateNewAccumulated(card, 30));
    }

    @Test
    @DisplayName("calculateNewAccumulated() debe restar el valor cuando el efecto es SUBTRACT")
    void calculateNewAccumulatedShouldSubtractValue() {
        Card card = new Card(Card.Suit.SPADES, 10, Card.Effect.SUBTRACT, "K");

        assertEquals(20, rules.calculateNewAccumulated(card, 30));
    }

    @Test
    @DisplayName("calculateNewAccumulated() no debe modificar el acumulado cuando el efecto es NONE")
    void calculateNewAccumulatedShouldKeepAccumulatedUnchanged() {
        Card card = new Card(Card.Suit.CLUBS, 9, Card.Effect.NONE, "9");

        assertEquals(30, rules.calculateNewAccumulated(card, 30));
    }

    @Test
    @DisplayName("isCardPlayable() debe ser true cuando el nuevo acumulado es exactamente 50")
    void isCardPlayableShouldBeTrueAtExactLimit() {
        Card card = new Card(Card.Suit.DIAMONDS, 5, Card.Effect.ADD, "5");

        assertTrue(rules.isCardPlayable(card, 45));
    }

    @Test
    @DisplayName("isCardPlayable() debe ser false cuando el nuevo acumulado supera 50")
    void isCardPlayableShouldBeFalseWhenExceedingLimit() {
        Card card = new Card(Card.Suit.DIAMONDS, 6, Card.Effect.ADD, "6");

        assertFalse(rules.isCardPlayable(card, 45));
    }

    @Test
    @DisplayName("isCardPlayable() debe ser true cuando la carta resta y baja el acumulado")
    void isCardPlayableShouldBeTrueWhenSubtractingLowersAccumulated() {
        Card card = new Card(Card.Suit.HEARTS, 10, Card.Effect.SUBTRACT, "Q");

        assertTrue(rules.isCardPlayable(card, 48));
    }

    @Test
    @DisplayName("hasPlayableCard() debe ser true si al menos una carta de la mano es jugable")
    void hasPlayableCardShouldBeTrueWhenAtLeastOneCardFits() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Card.Suit.SPADES, 10, Card.Effect.ADD, "10"));   // 45 + 10 = 55, no jugable
        hand.add(new Card(Card.Suit.HEARTS, 10, Card.Effect.SUBTRACT, "J")); // 45 - 10 = 35, jugable

        assertTrue(rules.hasPlayableCard(hand, 45));
    }

    @Test
    @DisplayName("hasPlayableCard() debe ser false cuando ninguna carta de la mano es jugable")
    void hasPlayableCardShouldBeFalseWhenNoCardFits() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Card.Suit.SPADES, 8, Card.Effect.ADD, "8"));  // 45 + 8 = 53
        hand.add(new Card(Card.Suit.CLUBS, 10, Card.Effect.ADD, "10")); // 45 + 10 = 55

        assertFalse(rules.hasPlayableCard(hand, 45));
    }

    @Test
    @DisplayName("validatePlay() no debe lanzar excepcion cuando la carta es jugable")
    void validatePlayShouldNotThrowWhenCardIsPlayable() {
        Card card = new Card(Card.Suit.HEARTS, 1, Card.Effect.ADD, "A");

        assertDoesNotThrow(() -> rules.validatePlay(card, 45));
    }

    @Test
    @DisplayName("validatePlay() debe lanzar InvalidCardException cuando la carta no es jugable")
    void validatePlayShouldThrowWhenCardIsNotPlayable() {
        Card card = new Card(Card.Suit.CLUBS, 10, Card.Effect.ADD, "10");

        assertThrows(InvalidCardException.class, () -> rules.validatePlay(card, 45));
    }
}
