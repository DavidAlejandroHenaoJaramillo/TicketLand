package model;

import java.util.ArrayList;
import java.util.List;

public class Recinto {
    private static int contadorId = 1;
    private int idRecinto;
    private String nombre;
    private String direccion;
    private String ciudad;
    private List<Zona> zonas;

    public Recinto(String nombre, String direccion, String ciudad) {
        this.idRecinto = contadorId++;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        zonas = new ArrayList<>();
    }

    public int getIdRecinto() { return idRecinto; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setZonas(List<Zona> zonas) {
        this.zonas = zonas;
    }

    public List<Zona> getZonas() {
        return zonas;
    }

    public void agregarZona(Zona zona) {
        zonas.add(zona);
    }

    @Override
    public String toString() {
        return nombre + " - " + direccion;
    }
}