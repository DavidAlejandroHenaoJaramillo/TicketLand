package state;

public class CompraConfirmada implements EstadoCompra {

    @Override
    public String manejarEstado() {
        return "La compra fue confirmada";
    }

    @Override
    public String toString() {
        return "CONFIRMADA";
    }
}
