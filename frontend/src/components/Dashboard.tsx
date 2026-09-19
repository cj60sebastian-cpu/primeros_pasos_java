import React, { useState, useEffect } from "react";
import "./Dashboard.css";

interface Producto {
  id_: number;
  codigo: string;
  precio: number;
  descripcion: string;
  imagen_url?: string;
}

interface DashboardProps {
  onLogout: () => void;
}

export const Dashboard: React.FC<DashboardProps> = ({ onLogout }) => {
  const [activeTab, setActiveTab] = useState("productos");
  const [productosBD, setProductosBD] = useState<Producto[]>([]);

  // Estados para Control de Carga y Errores superando el recto
  const [cargando, setCargando] = useState(true);
  const [errorCarga, setErrorCarga] = useState("");
  const [mensajeExito, setMensajeExito] = useState("");

  // Estado para el Formulario de Nuevo Producto
  const [nuevoProducto, setNuevoProducto] = useState({
    codigo: "",
    precio: "",
    descripcion: "",
    imagen_url: "",
  });

  // Función para obtener productos desde la API,yaesta trabajando con mySQL y Tomcat
  const obtenerProductos = async () => {
    setCargando(true);
    setErrorCarga("");
    try {
      const res = await fetch("http://localhost:8080/backend/ProductosServlet");
      if (!res.ok) {
        throw new Error(`Error en el servidor: HTTP Status ${res.status}`);
      }
      const data: Producto[] = await res.json();
      setProductosBD(data);
    } catch (err: any) {
      setErrorCarga("No se pudo conectar con Tomcat o la base de datos MySQL.");
      console.error("Error al cargar productos:", err);
    } finally {
      setCargando(false);
    }
  };

  useEffect(() => {
    if (activeTab === "productos") {
      obtenerProductos();
    }
  }, [activeTab]);

  const handleChangeForm = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNuevoProducto({
      ...nuevoProducto,
      [e.target.name]: e.target.value,
    });
  };

  // Enviar nuevo producto a la base de datos
  const handleGuardarProducto = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrorCarga("");
    setMensajeExito("");

    try {
      const response = await fetch(
        "http://localhost:8080/backend/ProductosServlet",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            codigo: nuevoProducto.codigo,
            precio: parseFloat(nuevoProducto.precio),
            descripcion: nuevoProducto.descripcion,
            imagen_url:
              nuevoProducto.imagen_url ||
              "https://cdn-icons-png.flaticon.com/512/3081/3081559.png",
          }),
        },
      );

      if (response.ok) {
        setMensajeExito("¡Producto guardado exitosamente en la base de datos!");
        setNuevoProducto({
          codigo: "",
          precio: "",
          descripcion: "",
          imagen_url: "",
        });
        obtenerProductos(); // Recargar la lista de productos con el estado activeTAB
      } else {
        setErrorCarga("Error al intentar registrar el producto.");
      }
    } catch (err) {
      setErrorCarga("Error de conexión con Tomcat al enviar el producto.");
    }
  };

  return (
    <div className="dashboard-layout">
      {/* Sidebar Flotante */}
      <aside className="sidebar">
        <div>
          <div className="sidebar-brand">MiniStock Cloud</div>
          <ul className="sidebar-menu">
            <li
              className={`menu-item ${activeTab === "panel" ? "active" : ""}`}
              onClick={() => setActiveTab("panel")}
            >
              📊 Panel de Control
            </li>
            <li
              className={`menu-item ${activeTab === "accesos" ? "active" : ""}`}
              onClick={() => setActiveTab("accesos")}
            >
              👤 Accesos
            </li>
            <li
              className={`menu-item ${activeTab === "productos" ? "active" : ""}`}
              onClick={() => setActiveTab("productos")}
            >
              📦 Productos
            </li>
            <li
              className={`menu-item ${activeTab === "ventas" ? "active" : ""}`}
              onClick={() => setActiveTab("ventas")}
            >
              🛒 Ventas
            </li>
            <li
              className={`menu-item ${activeTab === "reportes" ? "active" : ""}`}
              onClick={() => setActiveTab("reportes")}
            >
              📈 Reporte de Ventas
            </li>
          </ul>
        </div>

        <button className="btn-logout" onClick={onLogout}>
          Cerrar Sesión
        </button>
      </aside>

      {/* Contenido Principal */}
      <main className="main-content">
        <header className="main-header">
          <h1>{activeTab.charAt(0).toUpperCase() + activeTab.slice(1)}</h1>
        </header>

        {/* Notificaciones Globales de Carga y Errores */}
        {errorCarga && (
          <div className="alert-error" style={{ marginBottom: "1.5rem" }}>
            {errorCarga}
          </div>
        )}
        {mensajeExito && (
          <div
            style={{
              backgroundColor: "#f0fdf4",
              color: "#16a34a",
              border: "1px solid #bbf7d0",
              padding: "0.75rem",
              borderRadius: "8px",
              marginBottom: "1.5rem",
              fontSize: "0.875rem",
            }}
          >
            {mensajeExito}
          </div>
        )}

        {activeTab === "productos" && (
          <div>
            {/* Formulario para Agregar Nuevo Producto */}
            <div
              style={{
                backgroundColor: "#ffffff",
                padding: "1.5rem",
                borderRadius: "12px",
                marginBottom: "2rem",
                border: "1px solid #e2e8f0",
              }}
            >
              <h3 style={{ marginTop: 0, color: "#0f172a" }}>
                Agregar Nuevo Producto
              </h3>
              <form
                onSubmit={handleGuardarProducto}
                style={{
                  display: "grid",
                  gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
                  gap: "1rem",
                  alignItems: "end",
                }}
              >
                <div className="form-group" style={{ margin: 0 }}>
                  <label>Código</label>
                  <input
                    type="text"
                    name="codigo"
                    value={nuevoProducto.codigo}
                    onChange={handleChangeForm}
                    placeholder="Ej: P003"
                    required
                  />
                </div>

                <div className="form-group" style={{ margin: 0 }}>
                  <label>Descripción / Nombre</label>
                  <input
                    type="text"
                    name="descripcion"
                    value={nuevoProducto.descripcion}
                    onChange={handleChangeForm}
                    placeholder="Ej: Aceite 2 Tiempos 500ml"
                    required
                  />
                </div>

                <div className="form-group" style={{ margin: 0 }}>
                  <label>Precio ($)</label>
                  <input
                    type="number"
                    name="precio"
                    value={nuevoProducto.precio}
                    onChange={handleChangeForm}
                    placeholder="Ej: 15000"
                    required
                  />
                </div>

                <div className="form-group" style={{ margin: 0 }}>
                  <label>URL de la Imagen</label>
                  <input
                    type="text"
                    name="imagen_url"
                    value={nuevoProducto.imagen_url}
                    onChange={handleChangeForm}
                    placeholder="https://..."
                  />
                </div>

                <button
                  type="submit"
                  className="btn-submit"
                  style={{ marginTop: 0, height: "42px" }}
                >
                  Guardar Producto
                </button>
              </form>
            </div>

            {/* Listado / Galería de Productos */}
            {cargando ? (
              <div
                style={{
                  textAlign: "center",
                  padding: "3rem",
                  color: "#64748b",
                }}
              >
                <p>Cargando productos desde MySQL...</p>
              </div>
            ) : (
              <div className="product-grid">
                {productosBD.map((prod) => (
                  <div className="product-card" key={prod.id_}>
                    <img
                      src={
                        prod.imagen_url ||
                        "https://cdn-icons-png.flaticon.com/512/3081/3081559.png"
                      }
                      alt={prod.descripcion}
                      className="product-img"
                      onError={(e) => {
                        (e.target as HTMLImageElement).src =
                          "https://cdn-icons-png.flaticon.com/512/3081/3081559.png";
                      }}
                    />
                    <div className="product-title">{prod.descripcion}</div>
                    <div className="product-stock">Cód: {prod.codigo}</div>
                    <div
                      className="product-stock"
                      style={{
                        fontWeight: "bold",
                        color: "#2563eb",
                        marginTop: "4px",
                      }}
                    >
                      ${prod.precio}
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}

        {activeTab !== "productos" && (
          <div
            style={{
              padding: "2rem",
              backgroundColor: "#fff",
              borderRadius: "12px",
            }}
          >
            <h3>Módulo {activeTab} en desarrollo</h3>
          </div>
        )}
      </main>
    </div>
  );
};
