package model;

import strategy.PagoStrategy;

import java.time.LocalDateTime;

/**
 * RF-044: Entidad Pago requerida en el diagrama de clases.
 *
 * Representa el registro del pago asociado a una Compra.
 * Separa la responsabilidad del pago (cuándo, cuánto, con qué método)
 * de la Compra en sí (SRP — RF-047).
 *
 * Relación: Compra tiene un Pago (composición 1:1 tras pagar).
 */
public class Pago {

    public enum EstadoPago {
        PENDIENTE,
        APROBADO,
        RECHAZADO,
        REEMBOLSADO
    }

    private int idPago;
    private double monto;
    private LocalDateTime fechaHora;
    private EstadoPago estado;
    private PagoStrategy metodoPago;
    private String referencia; // número de transacción simulado

    public Pago(int idPago, double monto, PagoStrategy metodoPago) {
        this.idPago = idPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.fechaHora = LocalDateTime.now();
        this.estado = EstadoPago.PENDIENTE;
        this.referencia = "TXN-" + System.currentTimeMillis();
    }

    /** Procesa el pago usando la estrategia configurada. */
    public boolean procesar() {
        boolean exitoso = metodoPago.procesarPago(monto);
        estado = exitoso ? EstadoPago.APROBADO : EstadoPago.RECHAZADO;
        return exitoso;
    }

    /** Marca el pago como reembolsado. */
    public boolean reembolsar() {
        if (estado != EstadoPago.APROBADO) return false;
        estado = EstadoPago.REEMBOLSADO;
        System.out.println("[Pago] Reembolso de $" + monto + " procesado. Ref: " + referencia);
        return true;
    }

    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public LocalDateTime getFechaHora() { return fechaHora; }

    public EstadoPago getEstado() { return estado; }
    public void setEstado(EstadoPago estado) { this.estado = estado; }

    public PagoStrategy getMetodoPago() { return metodoPago; }

    public String getReferencia() { return referencia; }

    @Override
    public String toString() {
        return "Pago #" + idPago +
                " | $" + monto +
                " | " + metodoPago +
                " | " + estado +
                " | Ref: " + referencia;
    }
}