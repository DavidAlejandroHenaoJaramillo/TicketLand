package factory;

import model.Evento;
import model.Recinto;
import model.Teatro;

import java.time.LocalDate;

/**
 * RF-049: Factory Method para eventos de tipo Teatro.
 */
public class TeatroFactory extends EventoFactory {

    private String nombre;
    private String ciudad;
    private LocalDate fecha;
    private String obra;
    private String director;
    private Recinto recinto;

    public TeatroFactory(String nombre, String ciudad, LocalDate fecha,
                         String obra, String director, Recinto recinto) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.obra = obra;
        this.director = director;
        this.recinto = recinto;
    }

    @Override
    public Evento crearEvento() {
        return new Teatro(
                nombre,
                "Teatro",
                fecha,
                ciudad,
                "Obra: " + obra + " — Director: " + director,
                "Reembolsable hasta 24h antes",
                obra,
                director,
                recinto
        );
    }
}