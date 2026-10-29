package controlador; 

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ProductosServlet")
public class ProductosServlet extends HttpServlet {

    private final String DB_URL = "jdbc:mysql://localhost:3306/bd_supermark?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "12345678";

    private void configurarHeadersCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        configurarHeadersCORS(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        configurarHeadersCORS(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String sql = "SELECT * FROM productos";
        List<Map<String, Object>> listaProductos = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement pstmt = conexion.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                while (rs.next()) {
                    Map<String, Object> producto = new HashMap<>();
                    producto.put("id_", rs.getInt("id_"));
                    producto.put("codigo", rs.getString("codigo"));
                    producto.put("descripcion", rs.getString("descripcion"));
                    // Ajustado a getInt para coincidir con tu BD
                    producto.put("precio", rs.getInt("precio")); 
                    producto.put("imagen_url", rs.getString("imagen_url"));
                    listaProductos.add(producto);
                }
                
                Gson gson = new Gson();
                out.print(gson.toJson(listaProductos));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String errorMsg = e.getMessage().replace("\"", "'").replace("\n", " ");
            out.print("{\"error\": \"Error SQL: " + errorMsg + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        configurarHeadersCORS(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        String accion = request.getParameter("accion");
            
        // --- LÓGICA PARA ELIMINAR ---
        if ("eliminar".equals(accion)) {
            String codigo = request.getParameter("codigo");
            String sql = "DELETE FROM productos WHERE codigo = ?";
            
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conexion = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                     PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                    
                    pstmt.setString(1, codigo);
                    int filasAfectadas = pstmt.executeUpdate();
                    
                    if (filasAfectadas > 0) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        out.print("{\"mensaje\": \"Producto eliminado con éxito\"}");
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print("{\"error\": \"No se encontró el producto con ese código\"}");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                // Limpiamos el error para que el JSON no se rompa y lo puedas leer en React
                String errorMsg = e.getMessage().replace("\"", "'").replace("\n", " ");
                out.print("{\"error\": \"Error SQL al eliminar: " + errorMsg + "\"}");
            }
        } 
        
        // --- LÓGICA PARA GUARDAR ---
        else if ("guardar".equals(accion)) {
            String codigo = request.getParameter("codigo");
            String descripcion = request.getParameter("descripcion");
            String precioStr = request.getParameter("precio");
            String imagenUrl = request.getParameter("imagen_url");
            
            String sql = "INSERT INTO productos (codigo, descripcion, precio, imagen_url) VALUES (?, ?, ?, ?)";
            
            try {
                // Convertimos el string a int para coincidir con tu BD
                int precio = (int) Double.parseDouble(precioStr); 
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                try (Connection conexion = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                     PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                    
                    pstmt.setString(1, codigo);
                    pstmt.setString(2, descripcion);
                    pstmt.setInt(3, precio);
                    pstmt.setString(4, imagenUrl);
                    
                    int filasAfectadas = pstmt.executeUpdate();
                    
                    if (filasAfectadas > 0) {
                        response.setStatus(HttpServletResponse.SC_CREATED);
                        out.print("{\"mensaje\": \"Producto guardado exitosamente\"}");
                    } else {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print("{\"error\": \"No se pudo guardar el producto\"}");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                // Limpiamos el error para que el JSON no se rompa
                String errorMsg = e.getMessage().replace("\"", "'").replace("\n", " ");
                out.print("{\"error\": \"Error SQL al guardar: " + errorMsg + "\"}");
            }
        }
    }
}