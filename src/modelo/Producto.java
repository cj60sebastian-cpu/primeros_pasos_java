package modelo;

public class Producto {
    private int id;
    private String codigo;
    private String descripcion;
    private int precio;

    // Constructor vacío para insertar filas nuevas en la base de datos
    public Producto() {}

    // Constructor completo (con ID, para consultas)
    public Producto(int id, String codigo, String descripcion, int precio) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Constructor sin ID (para nuevos registros) la base esta en + autoincremento
    public Producto(String codigo, String descripcion, int precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }

    @Override
    public String toString() {
        return "ID: " + id + " | Código: " + codigo + " | Descripcion: " + descripcion + " | Precio: $" + precio;
    }
}