package com.projects.poe.cincuentazo.model.exceptions;

/**
 * Excepción marcada que se lanza cuando un jugador intenta jugar
 * una carta que no es válida según las reglas del Cincuentazo.
 *
 * @author Jorge
 * @version 1.0
 */
public class InvalidCardException extends Exception {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message motivo por el que la carta es inválida
     */
    public InvalidCardException(String message) {
        super(message);
    }
}