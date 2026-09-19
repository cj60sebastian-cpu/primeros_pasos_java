import React, { useState } from "react";
import heroImg from "../assets/login.svg";
import "./Login.css";

interface LoginProps {
  onLoginSuccess: () => void;
  onSwitchToRegister: () => void;
}

export const Login: React.FC<LoginProps> = ({
  onLoginSuccess,
  onSwitchToRegister,
}) => {
  const [email, setEmail] = useState("");
  const [clave, setClave] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    try {
      const response = await fetch(
        "http://localhost:8080/backend/LoginServlet",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email, clave }),
        },
      );

      if (response.ok) {
        onLoginSuccess(); // <-- Aquí se utiliza la propiedad
      } else {
        setError("Correo electrónico o contraseña incorrectos.");
      }
    } catch (err) {
      setError("Error de conexión con el servidor Tomcat.");
    }
  };

  return (
    <div className="login-container">
      {/* Sección Izquierda: Banner Gráfico */}
      <div className="login-banner">
        <img src={heroImg} alt="MiniStock Cloud Ilustración" />
      </div>

      {/* Sección Derecha: Formulario */}
      <div className="login-form-container">
        <div className="login-box">
          <div className="login-header">
            <h2>MiniStock Cloud</h2>
            <p>Ingresa tus credenciales para acceder al sistema</p>
          </div>

          {error && <div className="alert-error">{error}</div>}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="email">Correo Electrónico</label>
              <input
                id="email"
                type="email"
                placeholder="ejemplo@ministock.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="clave">Contraseña</label>
              <input
                id="clave"
                type="password"
                placeholder="••••••••"
                value={clave}
                onChange={(e) => setClave(e.target.value)}
                required
              />
            </div>

            <button type="submit" className="btn-submit">
              Iniciar Sesión
            </button>
          </form>

          <div className="login-footer">
            ¿No tienes una cuenta?{" "}
            <button
              type="button"
              onClick={onSwitchToRegister}
              style={{
                background: "none",
                border: "none",
                color: "#2563eb",
                fontWeight: 600,
                cursor: "pointer",
                padding: 0,
              }}
            >
              Regístrate aquí
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
