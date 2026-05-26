package model;

/**
 * RF-044: Entidad Tarifa requerida en el diagrama de clases.
 *
 * Representa el precio de acceso a una Zona para un Evento específico.
 * Permite que una misma Zona tenga precios distintos según el evento
 * (ej: zona VIP cuesta más en un concierto que en una conferencia).
 *
 * Relación: Zona tiene una Tarifa; Evento puede tener tarifas especiales.
 */
public class Tarifa {

    private int idTarifa;
    private String nombre;       // ej: "VIP Especial", "General Estudiante"
    private double monto;        // precio en pesos
    private String descripcion;
    private TipoZona tipoZona;   // zona a la que aplica

    public Tarifa(int idTarifa, String nombre, double monto,
                  String descripcion, TipoZona tipoZona) {
        this.idTarifa = idTarifa;
        this.nombre = nombre;
        this.monto = monto;
        this.descripcion = descripcion;
        this.tipoZona = tipoZona;
    }

    public int getIdTarifa() { return idTarifa; }
    public void setIdTarifa(int idTarifa) { this.idTarifa = idTarifa; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public TipoZona getTipoZona() { return tipoZona; }
    public void setTipoZona(TipoZona tipoZona) { this.tipoZona = tipoZona; }

    @Override
    public String toString() {
        return nombre + " (" + tipoZona + ") - $" + monto;
    }
}