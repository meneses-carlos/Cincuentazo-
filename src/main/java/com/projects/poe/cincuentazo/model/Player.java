package com.projects.poe.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player within a Cincuentazo game, either human or machine-controlled.
 * Each player holds a hand of cards and a flag indicating whether they remain in the game.
 *
 * @author Jorge Navia
 * @version 1.1
 * @since 1.0
 */
public class Player {

    private final String name;
    private final boolean isMachine;
    private final List<Card> hand;
    private boolean active;

    /**
     * Builds a player with its name and type flag.
     *
     * @param name      name of the player
     * @param isMachine {@code true} if the player is AI-controlled
     */
    public Player(String name, boolean isMachine) {
        this.name = name;
        this.isMachine = isMachine;
        this.hand = new ArrayList<>();
        this.active = true;
    }

    /**
     * Returns the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Indicates whether the player is machine-controlled.
     */
    public boolean isMachine() {
        return isMachine;
    }

    /**
     * Indicates whether the player is still active in the game.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active state of the player.
     *
     * @param active {@code false} to mark the player as eliminated
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the cards currently in the player's hand.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card received card
     */
    public void receiveCard(Card card) {
        hand.add(card);
    }

    /**
     * Removes and returns the card located at the given position in the hand.
     *
     * @param index position of the card in the hand (0-based)
     * @return played card
     */
    public Card playCard(int index) {
        return hand.remove(index);
    }

    /**
     * Returns a readable representation of the player.
     */
    @Override
    public String toString() {
        return name;
    }
}
