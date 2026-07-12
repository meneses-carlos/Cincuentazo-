package com.projects.poe.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Cincuentazo game table.
 * Holds all the played cards and the current accumulated value, which must not exceed 50.
 *
 * @author Jorge Navia
 * @version 1.2
 * @since 1.0
 */
public class Table {

    private final List<Card> playedCards;
    private int accumulated;

    /**
     * Builds an empty table with the accumulated total at zero.
     */
    public Table() {
        this.playedCards = new ArrayList<>();
        this.accumulated = 0;
    }

    /**
     * Returns the card currently visible on the table (the last one played).
     *
     * @return visible card, or {@code null} if the table is empty
     */
    public Card getVisibleCard() {
        if (playedCards.isEmpty()) {
            return null;
        }
        return playedCards.get(playedCards.size() - 1);
    }

    /**
     * Returns the current accumulated value of the table.
     */
    public int getAccumulated() {
        return accumulated;
    }

    /**
     * Places a card on the table and updates the accumulated total
     * according to the card's effect.
     *
     * @param card played card
     */
    public void placeCard(Card card) {
        playedCards.add(card);
        switch (card.getEffect()) {
            case ADD:
                accumulated += card.getValue();
                break;
            case SUBTRACT:
                accumulated -= card.getValue();
                break;
            case NONE:
                break;
        }
    }

    /**
     * Recycles the table's cards when the deck runs out, leaving
     * only the last played card as the visible card.
     * The table's accumulated total is not modified.
     *
     * @return list of recycled cards (all except the last one)
     */
    public List<Card> recycleCards() {
        List<Card> recycled = new ArrayList<>();
        if (playedCards.size() > 1) {
            Card lastCard = playedCards.remove(playedCards.size() - 1);
            recycled.addAll(playedCards);
            playedCards.clear();
            playedCards.add(lastCard);
        }
        return recycled;
    }

    /**
     * Resets the table for a new game, clearing the played cards and the accumulated total.
     */
    public void reset() {
        playedCards.clear();
        accumulated = 0;
    }
}
