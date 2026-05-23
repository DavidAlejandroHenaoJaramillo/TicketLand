package model;

import java.time.LocalDate;

public class Teatro extends Evento {

    private String obra;
    private String director;

    public Teatro(String nombre, String categoria, LocalDate fecha, String ciudad, String descripcion, String politicas, String obra, String director) {

        super(nombre, categoria, fecha, ciudad, descripcion, politicas);
        this.obra = obra;
        this.director = director;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}