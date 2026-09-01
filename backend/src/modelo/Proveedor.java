package modelo;

public class Proveedor {
    private int id;
    private String nombreProveedor;
    private int facturaCompraId;

    public Proveedor() {}

    public Proveedor(int id, String nombreProveedor, int facturaCompraId) {
        this.id = id;
        this.nombreProveedor = nombreProveedor;
        this.facturaCompraId = facturaCompraId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }

    public int getFacturaCompraId() { return facturaCompraId; }
    public void setFacturaCompraId(int facturaCompraId) { this.facturaCompraId = facturaCompraId; }
}