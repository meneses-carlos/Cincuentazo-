package com.projects.poe.cincuentazo.model;

import com.projects.poe.cincuentazo.model.exceptions.MazoVacioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa el mazo de cartas de la baraja española usado en el Cincuentazo.
 *
 * <p>El mazo se inicializa con 40 cartas (4 palos × 10 valores),
 * se puede mezclar y permite tomar cartas una a una.</p>
 *
 * @author Jorge
 * @version 1.0
 */
public class Deck {

    private final List<Card> cartas;

    /**
     * Construye un mazo vacío. Debe llamarse {@link #inicializar()}
     * antes de usar el mazo.
     */
    public Deck() {
        this.cartas = new ArrayList<>();
    }

    /**
     * Inicializa el mazo con las 40 cartas de la baraja española
     * y lo mezcla aleatoriamente.
     *
     * <p>Las cartas y sus efectos según las reglas del Cincuentazo:</p>
     * <ul>
     *   <li>1 al 7: suman su valor al acumulado.</li>
     *   <li>10 (sota): resta 10 al acumulado.</li>
     *   <li>11 (caballo): no modifica el acumulado.</li>
     *   <li>12 (rey): lleva el acumulado exactamente a 50.</li>
     * </ul>
     */
    public void inicializar() {
        cartas.clear();
        for (Card.Palo palo : Card.Palo.values()) {
            for (int valor = 1; valor <= 7; valor++) {
                cartas.add(new Card(palo, valor, Card.Efecto.SUMAR));
            }
            cartas.add(new Card(palo, 10, Card.Efecto.RESTAR));
            cartas.add(new Card(palo, 11, Card.Efecto.NINGUNO));
            cartas.add(new Card(palo, 12, Card.Efecto.CINCUENTA));
        }
        mezclar();
    }

    /**
     * Mezcla aleatoriamente las cartas del mazo.
     */
    public void mezclar() {
        Collections.shuffle(cartas);
    }

    /**
     * Toma y retorna la carta del tope del mazo.
     *
     * @return carta tomada
     * @throws MazoVacioException si el mazo no tiene cartas disponibles
     */
    public Card tomarCarta() throws MazoVacioException {
        if (estaVacio()) {
            throw new MazoVacioException("El mazo no tiene más cartas disponibles.");
        }
        return cartas.remove(cartas.size() - 1);
    }

    /**
     * Indica si el mazo está vacío.
     *
     * @return {@code true} si no quedan cartas
     */
    public boolean estaVacio() {
        return cartas.isEmpty();
    }

    /**
     * Retorna la cantidad de cartas restantes en el mazo.
     *
     * @return número de cartas disponibles
     */
    public int getCantidadRestante() {
        return cartas.size();
    }
}