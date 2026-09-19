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
}

export const ListaProductos: React.FC<Props> = ({ productos }) => {
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
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};
