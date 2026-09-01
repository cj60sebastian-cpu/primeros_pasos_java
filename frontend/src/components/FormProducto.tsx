import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import type { Producto } from "./ListaProductos";

interface FormProductoProps {
  onGuardar: (nuevoProducto: Producto) => Promise<void>;
}

export const FormProducto: React.FC<FormProductoProps> = ({ onGuardar }) => {
  const navigate = useNavigate();

  const [producto, setProducto] = useState<Omit<Producto, "id">>({
    nombre: "",
    precio: 0,
    stock: 0,
    categoria: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setProducto((prev) => ({
      ...prev,
      [name]: name === "precio" || name === "stock" ? Number(value) : value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      // 1. Enviar los datos al backend mediante la prop
      await onGuardar(producto as Producto);

      // 2. Alerta de éxito
      alert("¡Producto guardado exitosamente!");

      // 3. Redirección al inventario para visualizar la lista actualizada
      navigate("/");
    } catch (error) {
      console.error("Error al guardar el producto:", error);
      alert("Ocurrió un error al guardar el producto.");
    }
  };

  return (
    <div style={{ maxWidth: "400px", margin: "20px auto" }}>
      <h2>Registrar Nuevo Producto</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: "12px" }}>
          <label style={{ display: "block", marginBottom: "4px" }}>
            Nombre:
          </label>
          <input
            type="text"
            name="nombre"
            value={producto.nombre}
            onChange={handleChange}
            required
            style={{ width: "100%", padding: "8px", boxSizing: "border-box" }}
          />
        </div>

        <div style={{ marginBottom: "12px" }}>
          <label style={{ display: "block", marginBottom: "4px" }}>
            Precio:
          </label>
          <input
            type="number"
            name="precio"
            step="0.01"
            value={producto.precio}
            onChange={handleChange}
            required
            style={{ width: "100%", padding: "8px", boxSizing: "border-box" }}
          />
        </div>

        <div style={{ marginBottom: "12px" }}>
          <label style={{ display: "block", marginBottom: "4px" }}>
            Stock / Material:
          </label>
          <input
            type="number"
            name="stock"
            value={producto.stock}
            onChange={handleChange}
            required
            style={{ width: "100%", padding: "8px", boxSizing: "border-box" }}
          />
        </div>

        <div style={{ marginBottom: "12px" }}>
          <label style={{ display: "block", marginBottom: "4px" }}>
            Categoría:
          </label>
          <input
            type="text"
            name="categoria"
            value={producto.categoria}
            onChange={handleChange}
            required
            style={{ width: "100%", padding: "8px", boxSizing: "border-box" }}
          />
        </div>

        <button
          type="submit"
          style={{
            padding: "10px 15px",
            backgroundColor: "#4CAF50",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          Guardar Producto
        </button>
      </form>
    </div>
  );
};
