import React, { useState } from "react";

interface ProductoInput {
  codigo: string;
  descripcion: string;
  precio: number;
  proveedorId: number;
}

interface Props {
  onGuardar: (producto: ProductoInput) => void;
}

export const FormProducto: React.FC<Props> = ({ onGuardar }) => {
  const [codigo, setCodigo] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [precio, setPrecio] = useState("");
  const [proveedorId, setProveedorId] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!codigo || !descripcion || !precio) {
      alert("Por favor completa todos los campos requeridos.");
      return;
    }

    onGuardar({
      codigo,
      descripcion,
      precio: parseFloat(precio),
      proveedorId: proveedorId ? parseInt(proveedorId) : 0,
    });

    setCodigo("");
    setDescripcion("");
    setPrecio("");
    setProveedorId("");
  };

  return (
    <form
      onSubmit={handleSubmit}
      style={{
        display: "flex",
        flexDirection: "column",
        gap: "10px",
        maxWidth: "400px",
      }}
    >
      <h2>Registrar Producto</h2>

      <div>
        <label>Código:</label>
        <input
          type="text"
          value={codigo}
          onChange={(e) => setCodigo(e.target.value)}
          required
          style={{ width: "100%", padding: "8px" }}
        />
      </div>

      <div>
        <label>Descripción / Nombre:</label>
        <input
          type="text"
          value={descripcion}
          onChange={(e) => setDescripcion(e.target.value)}
          required
          style={{ width: "100%", padding: "8px" }}
        />
      </div>

      <div>
        <label>Precio:</label>
        <input
          type="number"
          step="0.01"
          value={precio}
          onChange={(e) => setPrecio(e.target.value)}
          required
          style={{ width: "100%", padding: "8px" }}
        />
      </div>

      <div>
        <label>ID Proveedor (Opcional):</label>
        <input
          type="number"
          value={proveedorId}
          onChange={(e) => setProveedorId(e.target.value)}
          style={{ width: "100%", padding: "8px" }}
        />
      </div>

      <button
        type="submit"
        style={{ padding: "10px", marginTop: "10px", cursor: "pointer" }}
      >
        Guardar Producto
      </button>
    </form>
  );
};
