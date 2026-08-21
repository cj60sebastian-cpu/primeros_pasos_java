package dao;

import util.ConexionBD;
import modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // 1. INSERCIÓN (Crear) off & apagado 
    public boolean insertar(Producto p) {
        String sql = "INSERT INTO productos (codigo, descripcion, precio) VALUES (?, ?, ?)";
        try (Connection cn = ConexionBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getPrecio());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    // 2. CONSULTA (Leer todos)
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id_, codigo, descripcion, precio FROM productos";
        try (Connection cn = ConexionBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id_"),
                    rs.getString("codigo"),
                    rs.getString("descripcion"),
                    rs.getInt("precio")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar productos: " + e.getMessage());
        }
        return lista;
    }

    // 3. ACTUALIZACIÓN (Modificar)
    public boolean actualizar(Producto p) {
        String sql = "UPDATE productos SET codigo = ?, descripcion = ?, precio = ? WHERE id_ = ?";
        try (Connection cn = ConexionBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getPrecio());
            ps.setInt(4, p.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    // 4. ELIMINACIÓN (Borrar)
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id_ = ?";
        try (Connection cn = ConexionBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}