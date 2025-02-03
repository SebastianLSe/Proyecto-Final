package Factura;

public class Producto {
    private String codigo;
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto(String codigo, String nombre, double precio, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double total(int cantidad) {
        return precio * cantidad;
    }

    public boolean vender(int cantidad) {
        if (this.cantidad >= cantidad) {
            this.cantidad -= cantidad;
            return true;
        }
        return false;
    }

    // Sobrescribir el método toString para que el Producto se muestre correctamente
    @Override
    public String toString() {
        return "Código: " + codigo + "\nNombre: " + nombre + "\nPrecio: " + precio + "\nCantidad: " + cantidad;
    }
}
