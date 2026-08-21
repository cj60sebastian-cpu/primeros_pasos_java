package controlador;

import dao.ProductoDAO;
import modelo.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {

    private ProductoDAO productoDAO = new ProductoDAO();

    // 1. MÉTODO GET: Se ejecuta cuando entramos a la página para LISTAR los productos
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Consultamos la lista en MySQL usando el DAO
        List<Producto> lista = productoDAO.listar();
        
        // Guardamos la lista en la solicitud para que el JSP la pueda leer
        request.setAttribute("listaProductos", lista);
        
        // Reenviamos el control a la página JSP
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    // 2. MÉTODO POST: Se ejecuta cuando enviamos el FORMULARIO para GUARDAR un producto
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Capturamos los campos enviados desde el formulario HTML en productos.jsp
        String codigo = request.getParameter("codigo");
        String descripcion = request.getParameter("descripcion");
// Cambia la línea 43 por esta:
int precio = Integer.parseInt(request.getParameter("precio"));
        // Creamos el objeto Producto (id en 0 porque es AUTO_INCREMENT en MySQL)
        // Instanciamos el objeto vacío
Producto nuevoProducto = new Producto();

// Asignamos las propiedades una a una mediante setters
nuevoProducto.setCodigo(codigo);
nuevoProducto.setDescripcion(descripcion);
nuevoProducto.setPrecio(precio);
        
        // Insertamos en la base de datos (asegúrate de tener    el método guardar/insertar en ProductoDAO)
        productoDAO.insertar(nuevoProducto);

        // Redirigimos nuevamente al Servlet vía GET para recargar la lista de productos
        response.sendRedirect("ProductoServlet");
    }
}