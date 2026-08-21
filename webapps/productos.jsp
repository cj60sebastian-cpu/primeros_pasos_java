<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Producto" %>
<%@ page import="modelo.Proveedor" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Productos - MiniStock</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .nav { margin-bottom: 20px; }
        .nav a { margin-right: 15px; text-decoration: none; color: #0288d1; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #0288d1; color: white; }
        .form-container { background: #f5f5f5; padding: 15px; border-radius: 5px; width: 320px; }
        input, select { width: 95%; padding: 8px; margin: 5px 0 12px 0; }
        button { background: #0288d1; color: white; border: none; padding: 10px 15px; cursor: pointer; }
    </style>
</head>
<body>

    <div class="nav">
        <a href="ProductoServlet">Gestión de Productos</a> | 
        <a href="ProveedorServlet">Gestión de Proveedores</a>
    </div>

    <h2>Registrar Nuevo Producto</h2>
    <div class="form-container">
        <form action="ProductoServlet" method="POST">
            <label>ID Producto:</label><br>
            <input type="number" name="id" required><br>

            <label>Código:</label><br>
            <input type="text" name="codigo" required><br>

            <label>Descripción:</label><br>
            <input type="text" name="descripcion" required><br>

            <label>Precio:</label><br>
            <input type="number" step="0.01" name="precio" required><br>

            <label>Proveedor:</label><br>
            <select name="proveedorId" required>
                <option value="">-- Seleccione un Proveedor --</option>
                <%
                    List<Proveedor> proveedores = (List<Proveedor>) request.getAttribute("listaProveedores");
                    if (proveedores != null) {
                        for (Proveedor prov : proveedores) {
                %>
                    <option value="<%= prov.getId() %>"><%= prov.getNombreProveedor() %></option>
                <%
                        }
                    }
                %>
            </select><br>

            <button type="submit">Guardar Producto</button>
        </form>
    </div>

    <h2>Lista de Productos Registrados</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Código</th>
                <th>Descripción</th>
                <th>Precio</th>
                <th>Proveedor Asignado</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Producto> productos = (List<Producto>) request.getAttribute("listaProductos");
                if (productos != null && !productos.isEmpty()) {
                    for (Producto p : productos) {
            %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getCodigo() %></td>
                <td><%= p.getDescripcion() %></td>
                <td>$<%= p.getPrecio() %></td>
                <td><%= p.getNombreProveedor() %></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="5">No hay productos registrados.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

</body>
</html>