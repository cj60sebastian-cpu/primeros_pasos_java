package principal;
public class Main {
    public static void main(String[] args) {
        try {
            // Levantamos el servidor HTTP
            Servidor.main(args);
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}