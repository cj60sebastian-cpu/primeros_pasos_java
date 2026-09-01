package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Ajusta estos datos según la configuración de tu MySQL local
    private static final String URL = "jdbc:mysql://localhost:3306/bd_supermark?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678"; // Coloca aquí tu contraseña de MySQL si tienes una

    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa a la base de datos MiniStock!");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver JDBC de MySQL no encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }
}