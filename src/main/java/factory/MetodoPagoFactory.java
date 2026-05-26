package factory;

import model.MetodoPagoUsuario;
import model.TipoMetodoPago;
import strategy.*;

// RF-049 (Factory): crea la estrategia de pago correcta según el tipo
public class MetodoPagoFactory {

    /**
     * Crea la estrategia de pago correcta usando los datos
     * que el usuario ya tiene registrados en su MetodoPagoUsuario.
     */
    public static PagoStrategy crear(MetodoPagoUsuario metodo) {
        TipoMetodoPago tipo = metodo.getTipoMetodo();
        String referencia = metodo.getNumeroReferencia(); // nro tarjeta / celular / banco / cuenta

        return switch (tipo) {
            case TARJETA  -> new PagoTarjeta(referencia, "Titular");
            case PSE      -> new PagoPSE(referencia);
            case NEQUI    -> new PagoNequi(referencia);
            case PAYPAL   -> new PagoPaypal(referencia);
            case EFECTIVO -> new PagoEfectivo();
        };
    }
}