import { Link } from "react-router-dom";

export const Navbar = () => {
  return (
    <nav
      style={{
        display: "flex",
        gap: "1rem",
        padding: "1rem",
        backgroundColor: "#f0f0f0",
        marginBottom: "2rem",
      }}
    >
      <Link to="/" style={{ textDecoration: "none", fontWeight: "bold" }}>
        Inventario
      </Link>
      <Link
        to="/nuevo-producto"
        style={{ textDecoration: "none", fontWeight: "bold" }}
      >
        Registrar Producto
      </Link>
    </nav>
  );
};
