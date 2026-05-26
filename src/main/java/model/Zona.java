package model;

import java.util.ArrayList;
import java.util.List;
import state.Disponible;

public class Zona {

    // RF-028: identificador único de zona
    private int idZona;
    private String nombre;
    private int capacidad;
    private double precioBase;
    private TipoZona tipoZona;
    private List<Asiento> asientos;
    private Tarifa tarifa;

    public Zona(int idZona, String nombre, int capacidad, double precioBase, TipoZona tipoZona, Tarifa tarifa) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.tipoZona = tipoZona;
        asientos = new ArrayList<>();
        this.tarifa = tarifa;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Tarifa getTarifa() {return tarifa;}

    public void setTarifa(Tarifa tarifa) {this.tarifa = tarifa;}

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
        return idZona + " | " + nombre + " (" + tipoZona + ") - $" + precioBase;
    }
}