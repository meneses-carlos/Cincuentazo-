package com.projects.poe.cincuentazo.model;

import com.projects.poe.cincuentazo.model.exceptions.InvalidCardException;

import java.util.List;

/**
 * Contiene y aplica las reglas del juego Cincuentazo.
 *
 * <p>Es la clase central de validación: determina si una carta
 * puede ser jugada, calcula el nuevo acumulado hipotético y
 * verifica si un jugador tiene alguna jugada disponible.</p>
 *
 * @author Jorge
 * @version 1.0
 */
public class GameRules {

    /** Límite máximo del acumulado en el Cincuentazo. */
    private static final int LIMIT = 50;

    /**
     * Verifica si una carta puede ser jugada dado el acumulado actual.
     *
     * <p>Una carta es jugable si al aplicar su efecto el nuevo
     * acumulado no supera 50.</p>
     *
     * @param card        carta que se desea jugar
     * @param accumulated valor acumulado actual de la mesa
     * @return {@code true} si la carta es jugable
     */
    public boolean isCardPlayable(Card card, int accumulated) {
        return calculateNewAccumulated(card, accumulated) <= LIMIT;
    }

    /**
     * Calcula el nuevo acumulado si se jugara la carta dada,
     * sin modificar el estado real de la mesa.
     *
     * @param card        carta a evaluar
     * @param accumulated acumulado actual de la mesa
     * @return nuevo acumulado hipotético
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
     * Verifica si existe al menos una carta jugable en la mano dada.
     *
     * <p>Si no hay ninguna carta jugable, el jugador queda eliminado
     * según las reglas del Cincuentazo.</p>
     *
     * @param hand        lista de cartas en mano del jugador
     * @param accumulated acumulado actual de la mesa
     * @return {@code true} si hay al menos una carta jugable
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
     * Valida que la carta seleccionada sea jugable y lanza una
     * excepción marcada si no lo es.
     *
     * @param card        carta que se intenta jugar
     * @param accumulated acumulado actual de la mesa
     * @throws InvalidCardException si la carta no es jugable
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
}