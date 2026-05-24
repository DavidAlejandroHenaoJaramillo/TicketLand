package factory;

import model.Concierto;
import model.Evento;

public class ConciertoFactory extends EventoFactory {

    /**
     * Metodo abstracto sobreescrito para crear un Concierto
     * @return atributos null para añadir dentro de la interfaz
     */
    @Override
    public Evento crearEvento() {
        return new Concierto(
                null, null, null, null, null, null,
                null, null,
                null  // recinto
        );
    }
}