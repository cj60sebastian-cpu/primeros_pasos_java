package modelo;

public class Empleado {
    private int id;
    private String nombre;
    private String apellidos;
    private String nro_cc; // <-- Agregar variable
    private String email;
    private String clave;

    public Empleado() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getNro_cc() { return nro_cc; } // <-- Agregar Getter
    public void setNro_cc(String nro_cc) { this.nro_cc = nro_cc; } // <-- Agregar Setter

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}