package factory;

import model.Conferencia;
import model.Evento;
import model.Recinto;

import java.time.LocalDate;

/**
 * RF-049: Factory Method para eventos de tipo Conferencia.
 */
public class ConferenciaFactory extends EventoFactory {

    private String nombre;
    private String ciudad;
    private LocalDate fecha;
    private String ponente;
    private String tema;
    private Recinto recinto;

    public ConferenciaFactory(String nombre, String ciudad, LocalDate fecha,
                              String ponente, String tema, Recinto recinto) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.ponente = ponente;
        this.tema = tema;
        this.recinto = recinto;
    }

    @Override
    public Evento crearEvento() {
        return new Conferencia(
                nombre,
                "Conferencia",
                fecha,
                ciudad,
                "Ponente: " + ponente + " — Tema: " + tema,
                "No reembolsable",
                ponente,
                tema,
                recinto
        );
    }
}