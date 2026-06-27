package com.projects.poe.cincuentazo.model.exceptions;

/**
 * Excepción no marcada que se lanza cuando un jugador eliminado
 * intenta realizar una acción en la partida.
 *
 * @author Jorge
 * @version 1.0
 */
public class EliminatedPlayerException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message motivo del error
     */
    public EliminatedPlayerException(String message) {
        super(message);
    }
}