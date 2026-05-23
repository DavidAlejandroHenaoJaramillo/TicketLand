package model;

import java.util.ArrayList;
import java.util.List;
import state.Disponible;

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

    // RF-030: consultar ocupación por zona
    public int calcularOcupacion() {
        int ocupados = 0;
        for (Asiento a : asientos) {
            if (!(a.getEstado() instanceof Disponible)) {
                ocupados++;
            }
        }
        return ocupados;
    }

    // RF-025: consultar asientos disponibles para selección de entradas
    public List<Asiento> getAsientosDisponibles() {
        List<Asiento> disponibles = new ArrayList<>();
        for (Asiento a : asientos) {
            if (a.getEstado() instanceof Disponible) {
                disponibles.add(a);
            }
        }
        return disponibles;
    }

    // RF-029: verificar si la zona tiene capacidad para más entradas
    public boolean hayDisponibilidad() {
        return !getAsientosDisponibles().isEmpty();
    }

    @Override
    public String toString() {

        return tipoZona +
                " - $" +
                precioBase;
    }
}