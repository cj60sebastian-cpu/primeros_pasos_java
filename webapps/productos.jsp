<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Producto" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Inventario - MiniStock</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #f4f6f9; margin: 30px; }
        .container { max-width: 900px; margin: auto; background: white; padding: 25px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
        h1 { color: #2c3e50; text-align: center; margin-bottom: 25px; }
        form { display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 15px; margin-bottom: 30px; background: #eef2f7; padding: 20px; border-radius: 8px; }
        .form-group { display: flex; flex-direction: column; }
        label { font-weight: bold; margin-bottom: 5px; color: #34495e; }
        input { padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 14px; }
        .btn-container { grid-column: span 3; display: flex; gap: 10px; justify-content: flex-end; margin-top: 10px; }
        button { padding: 10px 20px; border: none; border-radius: 5px; font-weight: bold; cursor: pointer; color: white; }
        .btn-add { background-color: #27ae60; }
        .btn-clean { background-color: #7f8c8d; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #34495e; color: white; }
        tr:hover { background-color: #f1f1f1; }
    </style>
</head>
<body>

<div class="container">
    <h1>Gestión de Inventario - MiniStock Cloud</h1>

    <!-- FORMULARIO ENVIANDO DATOS POR MÉTODO POST AL SERVLET puente -->
    <form action="ProductoServlet" method="POST">
        <div class="form-group">
            <label>Código:</label>
            <input type="text" name="codigo" placeholder="Ej: P002" required>
        </div>
        <div class="form-group">
            <label>Descripción:</label>
            <input type="text" name="descripcion" placeholder="Ej: Leche Entera" required>
        </div>
        <div class="form-group">
            <label>Precio ($):</label>
            <input type="number" step="0.01" name="precio" placeholder="Ej: 3500" required>
        </div>
        <div class="btn-container">
            <button type="submit" class="btn-add">Guardar Producto</button>
            <button type="reset" class="btn-clean">Limpiar</button>
        </div>
    </form>

    <!-- TABLA QUE RECORRE LOS DATOS ENVIADOS DESDE EL SERVLET POR GET -->
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Código</th>
                <th>Descripción</th>
                <th>Precio</th>
            </tr>
        </thead>
        <tbody>
            <%
                // Recuperamos la lista que nos envía el Servlet
                List<Producto> lista = (List<Producto>) request.getAttribute("listaProductos");
                if (lista != null && !lista.isEmpty()) {
                    for (Producto p : lista) {
            %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getCodigo() %></td>
                <td><%= p.getDescripcion() %></td>
                <td>$<%= String.format("%.2f", p.getPrecio()) %></td>
            </tr>
            <% 
                    }
                } else {
            %>
            <tr>
                <td colspan="4" style="text-align: center;">No hay productos registrados.</td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

</body>
</html>