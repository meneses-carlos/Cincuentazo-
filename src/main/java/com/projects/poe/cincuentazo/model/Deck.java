package com.projects.poe.cincuentazo.model;

import com.projects.poe.cincuentazo.model.exceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the poker deck used in Cincuentazo.
 * The deck is initialized with 52 cards and allows cards to be drawn one at a time.
 *
 * <p>Value rules per card: A adds 1, 2 through 8 add their value, 9 has no
 * effect, 10 adds its value, and J, Q, K subtract 10.</p>
 *
 * @author Jorge Navia
 * @version 1.3
 * @since 1.0
 */
public class Deck {

    private final List<Card> cards;

    /**
     * Builds an empty deck. {@link #initialize()} must be called
     * before using the deck.
     */
    public Deck() {
        this.cards = new ArrayList<>();
    }

    /**
     * Initializes the deck with the 52 poker deck cards
     * and shuffles it randomly.
     */
    public void initialize() {
        cards.clear();
        for (Card.Suit suit : Card.Suit.values()) {

            // A suma 1 (valor fijo por ahora)
            cards.add(new Card(suit, 1, Card.Effect.ADD, "A"));

            // 2 al 8 suman su valor
            for (int i = 2; i <= 8; i++) {
                cards.add(new Card(suit, i, Card.Effect.ADD, String.valueOf(i)));
            }

            // 9 no suma ni resta
            cards.add(new Card(suit, 9, Card.Effect.NONE, "9"));

            // 10 suma su valor
            cards.add(new Card(suit, 10, Card.Effect.ADD, "10"));

            // J, Q, K restan 10
            cards.add(new Card(suit, 10, Card.Effect.SUBTRACT, "J"));
            cards.add(new Card(suit, 10, Card.Effect.SUBTRACT, "Q"));
            cards.add(new Card(suit, 10, Card.Effect.SUBTRACT, "K"));
        }
        shuffle();
    }

    /**
     * Randomly shuffles the deck's cards.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws and returns the card at the top of the deck.
     *
     * @return drawn card
     * @throws EmptyDeckException if the deck has no cards available
     */
    public Card drawCard() throws EmptyDeckException {
        if (isEmpty()) {
            throw new EmptyDeckException("El mazo no tiene más cartas disponibles.");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Adds a list of cards to the deck and shuffles them.
     * Used to recycle the table's cards when the deck runs out.
     *
     * @param recycled list of cards recycled from the table
     */
    public void addRecycledCards(List<Card> recycled) {
        cards.addAll(0, recycled);
        shuffle();
    }

    /**
     * Adds an eliminated player's cards to the deck.
     *
     * @param eliminatedCards cards of the eliminated player
     */
    public void addEliminatedPlayerCards(List<Card> eliminatedCards) {
        cards.addAll(0, eliminatedCards);
    }

    /**
     * Indicates whether the deck is empty.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Returns the number of cards remaining in the deck.
     */
    public int getRemainingCount() {
        return cards.size();
    }
}
