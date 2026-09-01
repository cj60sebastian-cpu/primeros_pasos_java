import { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import { ListaProductos } from "./components/ListaProductos";
import type { Producto } from "./components/ListaProductos";
import { FormProducto } from "./components/FormProducto";
import { obtenerProductos, crearProducto } from "./services/api";

function App() {
  const [productos, setProductos] = useState<Producto[]>([]);
  const [cargando, setCargando] = useState<boolean>(true);

  // Trae los productos reales cargados desde MySQL / Servlet Java
  const cargarProductos = async () => {
    try {
      setCargando(true);
      const data = await obtenerProductos();
      setProductos(data);
    } catch (error) {
      console.error("Error al cargar productos de la API:", error);
    } finally {
      setCargando(false);
    }
  };

  useEffect(() => {
    cargarProductos();
  }, []);

  const handleGuardarProducto = async (
    nuevoProducto: Producto,
  ): Promise<void> => {
    try {
      await crearProducto(nuevoProducto); // Envía el POST al Servlet
      await cargarProductos(); // Vuelve a hacer GET para actualizar la tabla
    } catch (error) {
      console.error("Error al guardar el producto:", error);
      throw error;
    }
  };

  return (
    <BrowserRouter>
      <nav
        style={{
          padding: "10px",
          backgroundColor: "#f0f0f0",
          marginBottom: "20px",
        }}
      >
        <Link to="/" style={{ marginRight: "15px" }}>
          Inventario
        </Link>
        <Link to="/nuevo-producto">Registrar Producto</Link>
      </nav>

      <h1>MiniStock Cloud</h1>

      <Routes>
        <Route
          path="/"
          element={
            cargando ? (
              <p>Cargando datos...</p>
            ) : (
              <ListaProductos productos={productos} />
            )
          }
        />
        <Route
          path="/nuevo-producto"
          element={<FormProducto onGuardar={handleGuardarProducto} />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
