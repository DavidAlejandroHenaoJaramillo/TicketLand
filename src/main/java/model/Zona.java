package model;

import java.util.ArrayList;
import java.util.List;

public class Zona {

    private int capacidad;
    private double precioBase;
    private TipoZona tipoZona;
    private List<Asiento> asientos;

    public Zona(int capacidad, double precioBase, TipoZona tipoZona) {
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.tipoZona = tipoZona;
        asientos = new ArrayList<>();
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public TipoZona getTipoZona() {
        return tipoZona;
    }

    public void setTipoZona(TipoZona tipoZona) {
        this.tipoZona = tipoZona;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public void agregarAsiento(Asiento asiento) {
        asientos.add(asiento);
    }

    @Override
    public String toString() {

        return tipoZona +
                " - $" +
                precioBase;
    }
}