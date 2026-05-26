package service;

import model.Pago;
import strategy.PagoStrategy;

public class PagoService {

    // RF-007: procesar pago y registrar
    public Pago procesarPago(int idPago, double monto, PagoStrategy estrategia) {
        Pago pago = new Pago(idPago, monto, estrategia);
        pago.procesar();
        return pago;
    }

    // RF-016: reembolso simulado
    public boolean reembolsar(Pago pago) {
        return pago.reembolsar();
    }
}