package com.projects.poe.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un jugador dentro de una partida de Cincuentazo,
 * ya sea humano o controlado por la máquina.
 *
 * @author Jorge
 * @version 1.0
 */
public class Player {

    private final String nombre;
    private final boolean esMaquina;
    private final List<Card> mano;
    private boolean activo;

    /**
     * Construye un jugador con su nombre e indicador de tipo.
     *
     * @param nombre    nombre del jugador
     * @param esMaquina {@code true} si es controlado por la IA
     */
    public Player(String nombre, boolean esMaquina) {
        this.nombre    = nombre;
        this.esMaquina = esMaquina;
        this.mano      = new ArrayList<>();
        this.activo    = true;
    }

    /**
     * Retorna el nombre del jugador.
     *
     * @return nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Indica si el jugador es controlado por la máquina.
     *
     * @return {@code true} si es jugador máquina
     */
    public boolean isEsMaquina() {
        return esMaquina;
    }

    /**
     * Indica si el jugador sigue activo en la partida.
     *
     * @return {@code true} si no ha sido eliminado
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Establece el estado activo del jugador.
     *
     * @param activo {@code false} para eliminar al jugador
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Retorna las cartas actuales en la mano del jugador.
     *
     * @return lista de cartas en mano
     */
    public List<Card> getMano() {
        return mano;
    }

    /**
     * Agrega una carta a la mano del jugador.
     *
     * @param carta carta recibida
     */
    public void recibirCarta(Card carta) {
        mano.add(carta);
    }

    /**
     * Elimina y retorna la carta en la posición indicada.
     *
     * @param indice posición de la carta en la mano (0-based)
     * @return carta jugada
     */
    public Card jugarCarta(int indice) {
        return mano.remove(indice);
    }

    /**
     * Retorna una representación legible del jugador.
     *
     * @return nombre del jugador
     */
    @Override
    public String toString() {
        return nombre;
    }
}