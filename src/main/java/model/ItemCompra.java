package model;

public class ItemCompra {

    private Asiento asiento;
    private double precio;

    public ItemCompra(Asiento asiento, double precio) {
        this.asiento = asiento;
        this.precio = precio;
    }

    public Asiento getAsiento() { return asiento; }
    public double getPrecio() { return precio; }

    @Override
    public String toString() {
        return "Item[" + asiento + " - $" + precio + "]";
    }
}