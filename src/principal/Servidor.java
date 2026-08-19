package principal;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import dao.ProductoDAO;
import modelo.Producto;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Servidor {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/productos", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                ProductoDAO dao = new ProductoDAO();
                List<Producto> lista = dao.listar();

                StringBuilder html = new StringBuilder();
                html.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
                html.append("<title>Gestion de Productos - MiniStock</title>");
                html.append("<style>");
                html.append("  body { font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 30px; }");
                html.append("  .container { max-width: 900px; margin: auto; background: white; padding: 25px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }");
                html.append("  h1 { color: #2c3e50; text-align: center; margin-bottom: 25px; }");
                html.append("  form { display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 15px; margin-bottom: 30px; background: #eef2f7; padding: 20px; border-radius: 8px; }");
                html.append("  .form-group { display: flex; flex-direction: column; }");
                html.append("  label { font-weight: bold; margin-bottom: 5px; color: #34495e; }");
                html.append("  input { padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 14px; }");
                html.append("  .btn-container { grid-column: span 3; display: flex; gap: 10px; justify-content: flex-end; margin-top: 10px; }");
                html.append("  button { padding: 10px 20px; border: none; border-radius: 5px; font-weight: bold; cursor: pointer; color: white; }");
                html.append("  .btn-add { background-color: #27ae60; }");
                html.append("  .btn-clean { background-color: #7f8c8d; }");
                html.append("  table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
                html.append("  th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
                html.append("  th { background-color: #34495e; color: white; }");
                html.append("  tr:hover { background-color: #f1f1f1; }");
                html.append("  .btn-action { padding: 5px 10px; font-size: 12px; border-radius: 3px; border: none; cursor: pointer; margin-right: 5px; color: white; }");
                html.append("  .btn-edit { background-color: #f39c12; }");
                html.append("  .btn-delete { background-color: #e74c3c; }");
                html.append("</style></head><body>");

                html.append("<div class='container'>");
                html.append("  <h1>Gestion de Inventario - MiniStock</h1>");

                // Formulario
                html.append("  <form>");
                html.append("    <div class='form-group'><label>Codigo:</label><input type='text' placeholder='Ej: P002'></div>");
                html.append("    <div class='form-group'><label>Descripcion:</label><input type='text' placeholder='Ej: Leche Entera'></div>");
                html.append("    <div class='form-group'><label>Precio ($):</label><input type='number' placeholder='Ej: 3500'></div>");
                html.append("    <div class='btn-container'>");
                html.append("      <button type='button' class='btn-add'>Guardar Producto</button>");
                html.append("      <button type='reset' class='btn-clean'>Limpiar</button>");
                html.append("    </div>");
                html.append("  </form>");

                // Tabla
                html.append("  <table>");
                html.append("    <thead><tr><th>ID</th><th>Codigo</th><th>Descripcion</th><th>Precio</th><th>Acciones</th></tr></thead>");
                html.append("    <tbody>");

                for (Producto p : lista) {
                    html.append("<tr>");
                    html.append("  <td>").append(p.getId()).append("</td>");
                    html.append("  <td>").append(p.getCodigo()).append("</td>");
                    html.append("  <td>").append(p.getDescripcion()).append("</td>");
                    html.append("  <td>$").append(String.format("%.2f", p.getPrecio())).append("</td>");
                    html.append("  <td>");
                    html.append("    <button class='btn-action btn-edit'>Editar</button>");
                    html.append("    <button class='btn-action btn-delete'>Eliminar</button>");
                    html.append("  </td>");
                    html.append("</tr>");
                }

                html.append("    </tbody>");
                html.append("  </table>");
                html.append("</div></body></html>");

                byte[] responseBytes = html.toString().getBytes(StandardCharsets.UTF_8);
                
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, responseBytes.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                    os.flush();
                }
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Servidor Web MiniStock Listo en http://localhost:8080/productos");
    }
}