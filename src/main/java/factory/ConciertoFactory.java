package factory;

import model.Concierto;
import model.Evento;
import model.Recinto;

import java.time.LocalDate;

/**
 * RF-049: Patrón creacional Factory Method.
 * Problema: el código cliente no debe conocer la clase concreta de Evento a instanciar.
 * Propósito: delegar la creación de cada tipo de Evento a su propia fábrica.
 * Solución: EventoFactory define crearEvento(); ConciertoFactory la implementa
 * retornando un Concierto con valores por defecto listos para configurar.
 */
public class ConciertoFactory extends EventoFactory {

    private String artista;
    private String genero;
    private String nombre;
    private String ciudad;
    private LocalDate fecha;
    private Recinto recinto;

    public ConciertoFactory(String nombre, String ciudad, LocalDate fecha,
                            String artista, String genero, Recinto recinto) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.artista = artista;
        this.genero = genero;
        this.recinto = recinto;
    }

    @Override
    public Evento crearEvento() {
        return new Concierto(
                nombre,
                "Concierto",
                fecha,
                ciudad,
                "Concierto de " + artista + " - " + genero,
                "No reembolsable después de 48h",
                artista,
                genero,
                recinto
        );
    }
}