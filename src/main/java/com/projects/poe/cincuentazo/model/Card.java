package com.projects.poe.cincuentazo.model;

import com.projects.poe.cincuentazo.model.exceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa el mazo de cartas de póker usado en el Cincuentazo.
 *
 * <p>El mazo se inicializa con 52 cartas (4 palos × 13 valores)
 * y permite tomar cartas una a una. Cuando el mazo se vacía,
 * acepta las cartas recicladas de la mesa para continuar el juego.</p>
 *
 * <p>Reglas de valor por carta:</p>
 * <ul>
 *   <li>A: suma 1 (pendiente definir con el profesor).</li>
 *   <li>2 al 8: suman su valor.</li>
 *   <li>9: no suma ni resta.</li>
 *   <li>10: suma 10.</li>
 *   <li>J, Q, K: restan 10.</li>
 * </ul>
 *
 * @author Jorge
 * @version 1.0
 */
public class Deck {

    private final List<Card> cards;

    /**
     * Construye un mazo vacío. Debe llamarse {@link #initialize()}
     * antes de usar el mazo.
     */
    public Deck() {
        this.cards = new ArrayList<>();
    }

    /**
     * Inicializa el mazo con las 52 cartas de la baraja de póker
     * y lo mezcla aleatoriamente.
     */
    public void initialize() {
        cards.clear();
        for (Card.Suit suit : Card.Suit.values()) {

            // A suma 1 por defecto (pendiente definir con el profesor)
            cards.add(new Card(suit, 1, Card.Effect.ADD, "A"));

            // 2 al 8 suman su valor
            for (int i = 2; i <= 8; i++) {
                cards.add(new Card(suit, i, Card.Effect.ADD, String.valueOf(i)));
            }

            // 9 no suma ni resta
            cards.add(new Card(suit, 9, Card.Effect.NONE, "9"));

            // 10 suma 10
            cards.add(new Card(suit, 10, Card.Effect.ADD, "10"));

            // J, Q, K restan 10
            cards.add(new Card(suit, 10, Card.Effect.SUBTRACT, "J"));
            cards.add(new Card(suit, 10, Card.Effect.SUBTRACT, "Q"));
            cards.add(new Card(suit, 10, Card.Effect.SUBTRACT, "K"));
        }
        shuffle();
    }

    /**
     * Mezcla aleatoriamente las cartas del mazo.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Toma y retorna la carta del tope del mazo.
     *
     * @return carta tomada
     * @throws EmptyDeckException si el mazo no tiene cartas disponibles
     */
    public Card drawCard() throws EmptyDeckException {
        if (isEmpty()) {
            throw new EmptyDeckException("El mazo no tiene más cartas disponibles.");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Agrega una lista de cartas al final del mazo y las mezcla.
     * Se usa para reciclar las cartas de la mesa cuando el mazo se vacía.
     *
     * @param recycled lista de cartas recicladas de la mesa
     */
    public void addRecycledCards(List<Card> recycled) {
        cards.addAll(0, recycled);
        shuffle();
    }

    /**
     * Agrega las cartas de un jugador eliminado al final del mazo.
     *
     * @param eliminatedCards cartas del jugador eliminado
     */
    public void addEliminatedPlayerCards(List<Card> eliminatedCards) {
        cards.addAll(0, eliminatedCards);
    }

    /**
     * Indica si el mazo está vacío.
     *
     * @return {@code true} si no quedan cartas
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Retorna la cantidad de cartas restantes en el mazo.
     *
     * @return número de cartas disponibles
     */
    public int getRemainingCount() {
        return cards.size();
    }
}