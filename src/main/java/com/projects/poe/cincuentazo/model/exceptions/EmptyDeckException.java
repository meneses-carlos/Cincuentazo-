package com.projects.poe.cincuentazo.model.exceptions;

/**
 * Excepción marcada que se lanza cuando se intenta tomar una carta
 * de un mazo que ya no tiene cartas disponibles.
 *
 * @author Jorge
 * @version 1.0
 */
public class EmptyDeckException extends Exception {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message motivo del error
     */
    public EmptyDeckException(String message) {
        super(message);
    }
}