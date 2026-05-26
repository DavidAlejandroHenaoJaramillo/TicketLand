package prototype;

import model.Asiento;
import model.Tarifa;
import model.Zona;
import model.TipoZona;
import state.Disponible;

import java.util.ArrayList;
import java.util.List;

/**
 * RF-049: Patrón creacional Prototype.
 *
 * Problema (RF-014/031): cuando se crea un nuevo recinto con zonas y asientos,
 * copiar manualmente cada asiento es tedioso y propenso a errores.
 *
 * Propósito: permitir clonar una Zona "plantilla" completa con todos sus
 * asientos ya configurados, para reutilizarla en distintos recintos/eventos.
 *
 * Solución: ZonaPrototype implementa Cloneable. Se crea una zona modelo una
 * vez y se clona tantas veces como sea necesario, reseteando los asientos
 * a DISPONIBLE en cada clon.
 */
public class ZonaPrototype implements Cloneable {

    private int idZona;
    private String nombre;
    private int capacidad;
    private double precioBase;
    private TipoZona tipoZona;
    private int cantidadAsientos;
    private Tarifa tarifa;

    public ZonaPrototype(int idZona, String nombre, int capacidad,
                         double precioBase, TipoZona tipoZona, int cantidadAsientos, Tarifa tarifa) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.tipoZona = tipoZona;
        this.cantidadAsientos = cantidadAsientos;
        this.tarifa = tarifa;
    }

    /**
     * Crea una Zona real basándose en este prototipo.
     * Todos los asientos del clon quedan en estado DISPONIBLE.
     *
     * @param nuevoId   id que tendrá la zona clonada
     * @param filaBase  letra de fila inicial (ej: "A")
     */
    public Zona clonarComoZona(int nuevoId, String filaBase) {
        Zona zona = new Zona(nuevoId, nombre, capacidad, precioBase, tipoZona, tarifa);
        for (int i = 1; i <= cantidadAsientos; i++) {
            zona.agregarAsiento(new Asiento(i, filaBase, i, new Disponible()));
        }
        return zona;
    }

    @Override
    public ZonaPrototype clone() {
        try {
            return (ZonaPrototype) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar ZonaPrototype", e);
        }
    }

    // Getters
    public int getIdZona() { return idZona; }
    public void setIdZona(int idZona) { this.idZona = idZona; }
    public String getNombre() { return nombre; }
    public TipoZona getTipoZona() { return tipoZona; }
    public double getPrecioBase() { return precioBase; }

    @Override
    public String toString() {
        return "Prototipo[" + nombre + " - " + tipoZona + " - $" + precioBase + "]";
    }
}