import { useState } from "react";
import { Login } from "./components/Login";
import { Register } from "./components/Register";
import { Dashboard } from "./components/Dashboard";

export function App() {
  // Estado para controlar la autenticación
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

  // Estado para alternar entre el formulario de Login y Registro
  const [view, setView] = useState<"login" | "register">("login");

  // Si el usuario inició sesión correctamente, renderiza el Dashboard principal
  if (isAuthenticated) {
    return <Dashboard onLogout={() => setIsAuthenticated(false)} />;
  }

  // Si no está autenticado, alterna entre Login y Registro
  return (
    <div>
      {view === "login" ? (
        <Login
          onLoginSuccess={() => setIsAuthenticated(true)}
          onSwitchToRegister={() => setView("register")}
        />
      ) : (
        <Register onSwitchToLogin={() => setView("login")} />
      )}
    </div>
  );
}

export default App;
