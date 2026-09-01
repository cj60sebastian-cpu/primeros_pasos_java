package controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import dao.ProductoDAO;
import modelo.Producto;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductoDAO productoDAO = new ProductoDAO();
    private Gson gson = new Gson();

    private void configurarHeadersCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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
        
        List<Producto> listaProductos = productoDAO.listar();
        String jsonRespuesta = gson.toJson(listaProductos);

        PrintWriter out = response.getWriter();
        out.print(jsonRespuesta);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        configurarHeadersCORS(response);

        // Leer el cuerpo JSON de la petición
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        // Convertir JSON a objeto Producto
        Producto nuevoProducto = gson.fromJson(buffer.toString(), Producto.class);
        
        boolean exito = productoDAO.guardar(nuevoProducto);

        PrintWriter out = response.getWriter();
        if (exito) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"mensaje\": \"Producto guardado exitosamente\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"No se pudo guardar el producto\"}");
        }
        out.flush();
    }
}