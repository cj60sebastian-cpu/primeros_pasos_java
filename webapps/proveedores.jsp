<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Proveedor" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Proveedores - MiniStock</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #0288d1; color: white; }
        .form-container { background: #f5f5f5; padding: 15px; border-radius: 5px; width: 300px; }
        input { width: 90%; padding: 8px; margin: 5px 0 15px 0; }
        button { background: #0288d1; color: white; border: none; padding: 10px 15px; cursor: pointer; }
    </style>
</head>
<body>

    <h2>Registrar Nuevo Proveedor</h2>
    <div class="form-container">
        <form action="ProveedorServlet" method="POST">
            <label>ID Proveedor:</label><br>
            <input type="number" name="id" required><br>
            
            <label>Nombre del Proveedor:</label><br>
            <input type="text" name="nombre" required><br>
            
            <button type="submit">Guardar Proveedor</button>
        </form>
    </div>

    <h2>Lista de Proveedores Registrados</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre del Proveedor</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Proveedor> lista = (List<Proveedor>) request.getAttribute("listaProveedores");
                if (lista != null && !lista.isEmpty()) {
                    for (Proveedor p : lista) {
            %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getNombreProveedor() %></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="2">No hay proveedores registrados.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>