package model;

import state.EstadoAsiento;
import state.Bloqueado;
import state.Disponible;
import state.Reservado;
import state.Vendido;

public class Asiento {

    // RF-031: identificador único de asiento
    private int idAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(int idAsiento, String fila, int numero, EstadoAsiento estado) {
        this.idAsiento = idAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estado = estado;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    public String mostrarEstadoAsiento() {
        return estado.manejarEstado();
    }

    // RF-032: reservar asiento (al seleccionarlo antes de pagar)
    public boolean reservar() {
        if (estado instanceof Disponible) {
            estado = new Reservado();
            return true;
        }
        return false;
    }

    // RF-032: liberar asiento (al cancelar compra o eliminar entrada)
    public boolean liberar() {
        if (estado instanceof Reservado || estado instanceof Vendido) {
            estado = new Disponible();
            return true;
        }
        return false;
    }

    // RF-015: bloquear asiento (acción del administrador)
    public boolean bloquear() {
        if (estado instanceof Disponible) {
            estado = new Bloqueado();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Asiento #" + idAsiento + " | Fila " + fila + " - " + numero;
    }
}