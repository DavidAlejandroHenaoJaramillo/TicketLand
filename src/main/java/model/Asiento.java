package model;

import state.EstadoAsiento;

public class Asiento {

    private String fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(String fila, int numero, EstadoAsiento estado) {
        this.fila = fila;
        this.numero = numero;
        this.estado = estado;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {

        return "Fila " +
                fila +
                " - Asiento " +
                numero;
    }
}