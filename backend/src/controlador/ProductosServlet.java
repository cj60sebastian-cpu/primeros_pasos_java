package controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/ProductosServlet")
public class ProductosServlet extends HttpServlet {

    private final String DB_URL = "jdbc:mysql://localhost:3306/bd_supermark";
    private final String DB_USER = "root";
    private final String DB_PASS = "12345678"; // Reemplaza por tu contraseña de MySQL

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        configurarHeadersCORS(response);
        PrintWriter out = response.getWriter();
        List<Producto> lista = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement("SELECT id_, codigo, precio, descripcion, imagen_url FROM productos");
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Producto p = new Producto(
                        rs.getInt("id_"),
                        rs.getString("codigo"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion"),
                        rs.getString("imagen_url")
                      
                    );
                    lista.add(p);
                }
            }
            out.print(new Gson().toJson(lista));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        configurarHeadersCORS(response);
        BufferedReader reader = request.getReader();
        Producto nuevo = new Gson().fromJson(reader, Producto.class);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO productos (codigo, precio, descripcion, imagen_url) VALUES (?, ?, ?, ?)")) {

                ps.setString(1, nuevo.getCodigo());
                ps.setDouble(2, nuevo.getPrecio());
                ps.setString(3, nuevo.getDescripcion());
                ps.setString(4, nuevo.getImagenUrl());

                ps.executeUpdate();
                response.setStatus(HttpServletResponse.SC_CREATED);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        configurarHeadersCORS(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void configurarHeadersCORS(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    // Clase Modelo Producto interna
    class Producto {
        private int id_;
        private String codigo;
        private double precio;
        private String descripcion;
        private String imagen_url;

        public Producto(int id_, String codigo, double precio, String descripcion, String imagen_url) {
            this.id_ = id_;
            this.codigo = codigo;
            this.precio = precio;
            this.descripcion = descripcion;
            this.imagen_url = imagen_url;
        }
        public int getId_() { return id_; } // <-- Agregar este getter
    public String getCodigo() { return codigo; }
    public double getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }
    public String getImagenUrl() { return imagen_url; }
}

    
}