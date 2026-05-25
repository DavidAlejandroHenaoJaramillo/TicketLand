package model;

import decorator.EntradaBase;

public class Entrada implements EntradaBase {

    // RF-038: identificador único de entrada
    private int idEntrada;
    private double precioFinal;
    private EstadoEntrada estadoEntrada;
    private Zona zona;
    private Asiento asiento;

    public Entrada(int idEntrada, double precioFinal, EstadoEntrada estadoEntrada,
                   Zona zona, Asiento asiento) {
        this.idEntrada = idEntrada;
        this.precioFinal = precioFinal;
        this.estadoEntrada = estadoEntrada;
        this.zona = zona;
        this.asiento = asiento;
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
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
        return "Entrada #" + idEntrada + " | " + zona.getTipoZona();
    }

    @Override
    public double getCosto() {
        return precioFinal;
    }

    // RF-040: anular entrada por cancelación o reembolso
    public boolean anular() {
        if (estadoEntrada == EstadoEntrada.ACTIVA) {
            estadoEntrada = EstadoEntrada.ANULADA;
            // liberar el asiento asociado si existe
            if (asiento != null) {
                asiento.liberar();
            }
            return true;
        }
        return false; // solo se anula si estaba activa
    }
    @Override
    public String toString() {
        return getDescripcion() + " - $" + precioFinal;
    }
}