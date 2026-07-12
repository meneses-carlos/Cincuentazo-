package com.projects.poe.cincuentazo.model.exceptions;

/**
 * Checked exception thrown when attempting to draw a card
 * from a deck that has no cards available.
 *
 * @author Jorge Navia
 * @version 1.0
 * @since 1.0
 */
public class EmptyDeckException extends Exception {

    /**
     * Builds the exception with a descriptive message.
     *
     * @param message reason for the error
     */
    public EmptyDeckException(String message) {
        super(message);
    }
}
