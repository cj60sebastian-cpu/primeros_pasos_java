import React from "react";

interface Producto {
  id?: number;
  codigo: string;
  descripcion: string;
  precio: number;
  proveedorId?: number;
}

interface Props {
  productos: Producto[];
  onDelete: (codigo: string) => void;
}

export const ListaProductos: React.FC<Props> = ({ productos, onDelete }) => {
  return (
    <div>
      <h2>Inventario de Productos</h2>
      <table
        border={1}
        cellPadding={8}
        style={{ width: "100%", borderCollapse: "collapse" }}
      >
        <thead>
          <tr>
            <th>Código</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Categoría / Proveedor ID</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {productos.length === 0 ? (
            <tr>
              <td colSpan={4} style={{ textAlign: "center" }}>
                No hay productos registrados en el inventario.
              </td>
            </tr>
          ) : (
            productos.map((prod) => (
              <tr key={prod.id || prod.codigo}>
                <td>{prod.codigo}</td>
                <td>{prod.descripcion || "N/A"}</td>
                <td>${prod.precio}</td>
                <td>{prod.proveedorId ?? "General"}</td>
                <td>
                  <button
                    onClick={() => onDelete(prod.codigo)}
                    style={{
                      backgroundColor: "#fee2e2",
                      color: "#dc2626",
                      border: "none",
                      padding: "6px 12px",
                      borderRadius: "4px",
                      cursor: "pointer",
                      fontWeight: "bold",
                    }}
                  >
                    🗑️ Eliminar
                  </button>
                </td>
                ))
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};
