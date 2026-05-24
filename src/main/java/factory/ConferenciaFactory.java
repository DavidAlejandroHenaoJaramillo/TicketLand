package factory;

import model.Conferencia;
import model.Evento;

public class ConferenciaFactory extends EventoFactory {

    /**
     * Metodo abstracto sobreescrito para crear una Conferencia
     * @return atributos null para añadir dentro de la interfaz
     */
    @Override
    public Evento crearEvento() {
        return new Conferencia(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}