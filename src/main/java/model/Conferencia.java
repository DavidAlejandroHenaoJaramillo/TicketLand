package model;

import java.time.LocalDate;

public class Conferencia extends Evento {

    private String ponente;
    private String tema;

    public Conferencia(String nombre, String categoria, LocalDate fecha, String ciudad, String descripcion, String politicas, String ponente, String tema) {

        super(nombre, categoria, fecha, ciudad, descripcion, politicas);
        this.ponente = ponente;
        this.tema = tema;
    }

    public String getPonente() {
        return ponente;
    }

    public void setPonente(String ponente) {
        this.ponente = ponente;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}