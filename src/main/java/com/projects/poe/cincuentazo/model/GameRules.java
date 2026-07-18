package com.projects.poe.cincuentazo.model;

import com.projects.poe.cincuentazo.model.exceptions.InvalidCardException;

import java.util.List;

/**
 * Holds and applies the rules of the Cincuentazo game.
 * Determines whether a card can be played and whether a player has any available move.
 *
 * @author Jorge Navia
 * @version 1.4
 * @since 1.0
 */
public class GameRules {

    /** Maximum accumulated limit in Cincuentazo. */
    public static final int LIMIT = 50;

    /**
     * Checks whether a card can be played given the current accumulated total.
     * A card is playable if, after applying its effect, the new
     * accumulated total does not exceed the {@link #LIMIT}.
     *
     * @param card        card to be played
     * @param accumulated current accumulated value of the table
     * @return {@code true} if the card is playable
     */
    public boolean isCardPlayable(Card card, int accumulated) {
        return calculateNewAccumulated(card, accumulated) <= LIMIT;
    }

    /**
     * Calculates the new accumulated total if the given card were played,
     * without modifying the table's actual state.
     *
     * @param card        card to evaluate
     * @param accumulated current accumulated value of the table
     * @return hypothetical new accumulated total
     */
    public int calculateNewAccumulated(Card card, int accumulated) {
        switch (card.getEffect()) {
            case ADD:
                return accumulated + card.getValue();
            case SUBTRACT:
                return accumulated - card.getValue();
            case NONE:
                return accumulated;
            default:
                return accumulated;
        }
    }

    /**
     * Checks whether at least one playable card exists in the given hand.
     * If there is no playable card, the player is eliminated according to the Cincuentazo rules.
     *
     * @param hand        list of cards in the player's hand
     * @param accumulated current accumulated value of the table
     * @return {@code true} if at least one card is playable
     */
    public boolean hasPlayableCard(List<Card> hand, int accumulated) {
        for (Card card : hand) {
            if (isCardPlayable(card, accumulated)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates that the selected card is playable and throws a
     * checked exception if it is not.
     *
     * @param card        card being attempted
     * @param accumulated current accumulated value of the table
     * @throws InvalidCardException if the card is not playable
     */
    public void validatePlay(Card card, int accumulated) throws InvalidCardException {
        if (!isCardPlayable(card, accumulated)) {
            throw new InvalidCardException(
                    "La carta '" + card + "' no es jugable. " +
                            "Acumulado actual: " + accumulated +
                            ", nuevo acumulado sería: " + calculateNewAccumulated(card, accumulated) +
                            ", límite: " + LIMIT + "."
            );
        }
    }

    //Overload
    public int calculateNewAccumulated(Card card, int accumulated, int aceValue) {
        if (card.getLabel().equals("A")) {
            return accumulated + aceValue;
        }
        return calculateNewAccumulated(card, accumulated);
    }

    public boolean isCardPlayable(Card card, int accumulated, int aceValue){
        return calculateNewAccumulated(card, accumulated, aceValue) <= LIMIT;
    }

    public void validatePlay(Card card, int accumulated, int aceValue) throws InvalidCardException {
        if (!isCardPlayable(card, accumulated, aceValue)) {
            throw new InvalidCardException(
                    "La carta '" + card + "' no es jugable. " +
                            "Acumulado actual: " + accumulated +
                            ", nuevo acumulado sería: " + calculateNewAccumulated(card, accumulated, aceValue) +
                            ", límite: " + LIMIT + "."
            );
        }
    }
}
