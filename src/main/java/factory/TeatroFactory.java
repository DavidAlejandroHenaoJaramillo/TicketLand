package factory;

import model.Evento;
import model.Teatro;

public class TeatroFactory extends EventoFactory {

    /**
     * Metodo abstracto sobreescrito para crear un Teatro
     * @return atributos null para añadir dentro de la interfaz
     */
    @Override
    public Evento crearEvento() {
        return new Teatro(
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