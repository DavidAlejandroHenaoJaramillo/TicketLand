package builder;

import model.*;

// Dirige la construcción de Compras predefinidas
public class DirectorCompra {

    private final CompraConcretaBuilder builder;

    public DirectorCompra(CompraConcretaBuilder builder) {
        this.builder = builder;
    }

    public Compra construirCompraBasica() {
        return builder.construir();
    }

    public Compra construirCompraVIP() {
        // Agrega servicio VIP por defecto
        builder.agregarServicio(
                new ServicioAdicional("SRV-VIP", "Acceso VIP", 150_000, "Zona VIP exclusiva")
        );
        return builder.construir();
    }
}