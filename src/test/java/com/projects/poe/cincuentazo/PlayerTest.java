package com.projects.poe.cincuentazo;

import com.projects.poe.cincuentazo.model.Card;
import com.projects.poe.cincuentazo.model.Player;
import com.projects.poe.cincuentazo.model.exceptions.EliminatedPlayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link Player}.
 *
 * <p>Covers hand management and the unchecked exception thrown
 * when an eliminated player attempts to play a card.</p>
 */
class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Ana", false);
    }

    @Test
    @DisplayName("receiveCard() should add the card to the player's hand")
    void receiveCardShouldAddCardToHand() {
        player.receiveCard(new Card(Card.Suit.HEARTS, 5, Card.Effect.ADD, "5"));

        assertEquals(1, player.getHand().size());
    }

    @Test
    @DisplayName("playCard() should remove and return the card at the given index")
    void playCardShouldRemoveAndReturnCard() {
        Card card = new Card(Card.Suit.HEARTS, 5, Card.Effect.ADD, "5");
        player.receiveCard(card);

        Card played = player.playCard(0);

        assertEquals(card, played);
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    @DisplayName("playCard() should throw EliminatedPlayerException when the player is not active")
    void playCardShouldThrowWhenPlayerIsEliminated() {
        player.receiveCard(new Card(Card.Suit.HEARTS, 5, Card.Effect.ADD, "5"));
        player.setActive(false);

        assertThrows(EliminatedPlayerException.class, () -> player.playCard(0));
    }

    @Test
    @DisplayName("setActive(false) should mark the player as inactive")
    void setActiveShouldUpdatePlayerStatus() {
        player.setActive(false);

        assertFalse(player.isActive());
    }
}
