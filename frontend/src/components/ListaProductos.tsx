import React from "react";

export interface Producto {
  id?: number;
  nombre: string;
  precio: number;
  stock: number;
  categoria: string;
}

interface ListaProductosProps {
  productos: Producto[];
}

export const ListaProductos: React.FC<ListaProductosProps> = ({
  productos,
}) => {
  return (
    <div style={{ padding: "10px" }}>
      <h2>Inventario de Productos</h2>

      <table
        border={1}
        cellPadding={8}
        cellSpacing={0}
        style={{ width: "100%", textAlign: "left", marginTop: "10px" }}
      >
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Stock / Cantidad</th>
            <th>Categoría</th>
          </tr>
        </thead>
        <tbody>
          {productos && productos.length > 0 ? (
            productos.map((prod: any, index: number) => (
              <tr key={prod.id || index}>
                <td>{prod.nombre || prod.nombreProducto || "N/A"}</td>
                <td>${prod.precio ?? 0}</td>
                <td>{prod.stock ?? prod.cantidad ?? 0}</td>
                <td>{prod.categoria || "General"}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={4} style={{ textAlign: "center" }}>
                No hay productos registrados en el inventario.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};
