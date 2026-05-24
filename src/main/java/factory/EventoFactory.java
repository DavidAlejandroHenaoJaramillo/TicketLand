package factory;

import model.Evento;

public abstract class EventoFactory {
    /**
     * Metodo abstracto que heredaran las clases hijas para crear un evento
     * @return
     */
    public abstract Evento crearEvento();
}