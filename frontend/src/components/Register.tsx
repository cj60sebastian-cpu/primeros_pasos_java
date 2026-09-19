import React, { useState } from "react";
import heroImg from "../assets/login.svg";
import "./Login.css";

interface RegisterProps {
  onSwitchToLogin: () => void;
}

export const Register: React.FC<RegisterProps> = ({ onSwitchToLogin }) => {
  const [formData, setFormData] = useState({
    nombre: "",
    apellidos: "",
    nro_cc: "",
    email: "",
    clave: "",
  });

  const [mensaje, setMensaje] = useState("");
  const [error, setError] = useState("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setMensaje("");

    try {
      const response = await fetch(
        "http://localhost:8080/backend/RegistroServlet",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(formData),
        },
      );

      if (response.ok) {
        setMensaje(
          "¡Usuario registrado exitosamente! Redirigiendo al login...",
        );
        setTimeout(() => {
          onSwitchToLogin();
        }, 2000);
      } else {
        setError("No se pudo registrar el usuario. Verifica los datos.");
      }
    } catch (err) {
      setError("Error de conexión con el servidor Tomcat.");
    }
  };

  return (
    <div className="login-container">
      <div className="login-banner">
        <img src={heroImg} alt="MiniStock Cloud Ilustración" />
      </div>

      <div className="login-form-container">
        <div className="login-box">
          <div className="login-header">
            <h2>Crear Cuenta</h2>
            <p>Ingresa tus datos para registrarte en MiniStock Cloud</p>
          </div>

          {error && <div className="alert-error">{error}</div>}
          {mensaje && (
            <div
              style={{
                backgroundColor: "#f0fdf4",
                color: "#16a34a",
                border: "1px solid #bbf7d0",
                padding: "0.75rem",
                borderRadius: "8px",
                marginBottom: "1.25rem",
                fontSize: "0.875rem",
              }}
            >
              {mensaje}
            </div>
          )}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Nombre</label>
              <input
                type="text"
                name="nombre"
                value={formData.nombre}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Apellidos</label>
              <input
                type="text"
                name="apellidos"
                value={formData.apellidos}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Número de Cédula (opcional)</label>
              <input
                type="text"
                name="nro_cc"
                value={formData.nro_cc}
                onChange={handleChange}
              />
            </div>

            <div className="form-group">
              <label>Correo Electrónico</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Contraseña</label>
              <input
                type="password"
                name="clave"
                value={formData.clave}
                onChange={handleChange}
                required
              />
            </div>

            <button type="submit" className="btn-submit">
              Registrar Usuario
            </button>
          </form>

          <div className="login-footer">
            ¿Ya tienes una cuenta?{" "}
            <button
              type="button"
              onClick={onSwitchToLogin}
              style={{
                background: "none",
                border: "none",
                color: "#2563eb",
                fontWeight: 600,
                cursor: "pointer",
                padding: 0,
              }}
            >
              Inicia sesión aquí
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
