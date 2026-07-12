package com.projects.poe.cincuentazo.model;

/**
 * Represents a poker deck card used in Cincuentazo.
 * Each card has a suit, a numeric value, an effect and a readable label.
 *
 * @author Jorge Navia
 * @version 1.0
 * @since 1.0
 */
public class Card {

    /**
     * Suits of the poker deck.
     */
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    /**
     * Effect that the card produces on the table's accumulated total.
     */
    public enum Effect {
        ADD, SUBTRACT, NONE
    }

    private final Suit suit;
    private final int value;
    private final Effect effect;
    private final String label;

    /**
     * Builds a card with its suit, value, effect and label.
     *
     * @param suit   suit of the card
     * @param value  numeric value used to calculate the accumulated total
     * @param effect effect that the card produces on the accumulated total
     * @param label  readable label of the card (for example "A", "J")
     */
    public Card(Suit suit, int value, Effect effect, String label) {
        this.suit = suit;
        this.value = value;
        this.effect = effect;
        this.label = label;
    }

    /**
     * Returns the suit of the card.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the numeric value of the card.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the effect that the card produces on the accumulated total.
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     * Returns the readable label of the card.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns a readable representation of the card.
     */
    @Override
    public String toString() {
        return label + " of " + suit;
    }
}
