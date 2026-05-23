package model;

import java.time.LocalDate;

public class Concierto extends Evento {

    private String artista;
    private String genero;

    public Concierto(String nombre, String categoria, LocalDate fecha, String ciudad, String descripcion, String politicas, String artista, String genero, Recinto recinto) {
        super(nombre, categoria, fecha, ciudad, descripcion, politicas, recinto);
        this.artista = artista;
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}