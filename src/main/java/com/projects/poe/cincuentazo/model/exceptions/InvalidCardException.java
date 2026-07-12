package com.projects.poe.cincuentazo.model.exceptions;

/**
 * Checked exception thrown when a player attempts to play
 * a card that is not valid according to the Cincuentazo rules.
 *
 * @author Jorge Navia
 * @version 1.0
 * @since 1.0
 */
public class InvalidCardException extends Exception {

    /**
     * Builds the exception with a descriptive message.
     *
     * @param message reason the card is invalid
     */
    public InvalidCardException(String message) {
        super(message);
    }
}
