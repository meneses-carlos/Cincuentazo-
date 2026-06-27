package com.projects.poe.cincuentazo.model;

/**
 * Representa la mesa de juego del Cincuentazo.
 *
 * <p>Mantiene la carta visible en la pila central y el valor
 * acumulado actual, que no debe superar 50.</p>
 *
 * @author Jorge
 * @version 1.0
 */
public class Table {

    private Card cartaVisible;
    private int acumulado;

    /**
     * Construye una mesa vacía con acumulado en cero.
     */
    public Table() {
        this.cartaVisible = null;
        this.acumulado    = 0;
    }

    /**
     * Retorna la carta visible actualmente en la mesa.
     *
     * @return carta visible, o {@code null} si la mesa está vacía
     */
    public Card getCartaVisible() {
        return cartaVisible;
    }

    /**
     * Retorna el valor acumulado actual de la mesa.
     *
     * @return acumulado actual
     */
    public int getAcumulado() {
        return acumulado;
    }

    /**
     * Coloca una carta en la mesa y actualiza el acumulado
     * según el efecto de la carta.
     *
     * @param carta carta jugada
     */
    public void colocarCarta(Card carta) {
        this.cartaVisible = carta;
        switch (carta.getEfecto()) {
            case SUMAR:
                acumulado += carta.getValor();
                break;
            case RESTAR:
                acumulado -= carta.getValor();
                break;
            case CINCUENTA:
                acumulado = 50;
                break;
            case NINGUNO:
                break;
        }
    }

    /**
     * Reinicia la mesa para una nueva partida,
     * limpiando la carta visible y el acumulado.
     */
    public void reiniciar() {
        this.cartaVisible = null;
        this.acumulado    = 0;
    }
}