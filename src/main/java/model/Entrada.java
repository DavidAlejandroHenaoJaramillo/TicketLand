package model;

import decorator.EntradaBase;

public class Entrada implements EntradaBase {

    private double precioFinal;
    private EstadoEntrada estadoEntrada;
    private Zona zona;
    private Asiento asiento;

    public Entrada(double precioFinal, EstadoEntrada estadoEntrada, Zona zona, Asiento asiento) {
        this.precioFinal = precioFinal;
        this.estadoEntrada = estadoEntrada;
        this.zona = zona;
        this.asiento = asiento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public EstadoEntrada getEstadoEntrada() {
        return estadoEntrada;
    }

    public void setEstadoEntrada(EstadoEntrada estadoEntrada) {
        this.estadoEntrada = estadoEntrada;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    @Override
    public String getDescripcion() {
        return "Entrada " +
                zona.getTipoZona();
    }

    @Override
    public double getCosto() {
        return precioFinal;
    }

    @Override
    public String toString() {

        return getDescripcion() +
                " - $" +
                precioFinal;
    }
}