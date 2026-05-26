package model;

public class ServicioAdicional {

    private String idServicio;
    private String nombre;
    private double precio;
    private String descripcion;

    public ServicioAdicional(String idServicio, String nombre,
                             double precio, String descripcion) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public String getIdServicio() { return idServicio; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }

    @Override
    public String toString() {
        return nombre + " (+$" + precio + ")";
    }
}