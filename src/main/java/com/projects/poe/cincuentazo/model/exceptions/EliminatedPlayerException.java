package com.projects.poe.cincuentazo.model.exceptions;

/**
 * Unchecked exception thrown when an eliminated player
 * attempts to perform an action in the game.
 *
 * @author Jorge Navia
 * @version 1.0
 * @since 1.0
 */
public class EliminatedPlayerException extends RuntimeException {

    /**
     * Builds the exception with a descriptive message.
     *
     * @param message reason for the error
     */
    public EliminatedPlayerException(String message) {
        super(message);
    }
}
