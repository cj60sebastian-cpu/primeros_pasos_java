package controlador;

import dao.ProductoDAO;
import modelo.Producto;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {

    private ProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Producto> listaProductos = productoDAO.listar();
        String format = request.getParameter("format");
        String acceptHeader = request.getHeader("Accept");

        // Si la petición pide JSON (por parámetro ?format=json o Header Accept: application/json)
        if ("json".equalsIgnoreCase(format) || (acceptHeader != null && acceptHeader.contains("application/json"))) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            Gson gson = new Gson();
            String jsonRespuesta = gson.toJson(listaProductos);
            
            response.getWriter().write(jsonRespuesta);
        } else {
            // Respuesta normal renderizando la vista HTML/JSP en el navegador
            request.setAttribute("listaProductos", listaProductos);
            request.setAttribute("listaProveedores", Collections.emptyList());

            request.getRequestDispatcher("productos.jsp").forward(request, response);
        }
    }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    try {
        String codigo = request.getParameter("codigo");
        String descripcion = request.getParameter("descripcion");
        
        String precioParam = request.getParameter("precio");
        double precio = (precioParam != null && !precioParam.trim().isEmpty()) 
                        ? Double.parseDouble(precioParam) : 0.0;

        String provIdParam = request.getParameter("proveedorId");
        int proveedorId = (provIdParam != null && !provIdParam.trim().isEmpty()) 
                          ? Integer.parseInt(provIdParam) : 0;

        Producto nuevoProducto = new Producto(0, codigo, descripcion, precio, proveedorId);
        boolean insertado = productoDAO.insertar(nuevoProducto);

        String format = request.getParameter("format");
        String acceptHeader = request.getHeader("Accept");

        // Si la petición viene de Postman esperando JSON o trae ?format=json
        if ("json".equalsIgnoreCase(format) || (acceptHeader != null && acceptHeader.contains("application/json"))) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            if (insertado) {
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Producto creado correctamente\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"status\": \"error\", \"message\": \"No se pudo insertar el producto\"}");
            }
        } else {
            // Comportamiento normal desde el navegador web
            response.sendRedirect("ProductoServlet");
        }

    } catch (Exception e) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
    }
}
    }
