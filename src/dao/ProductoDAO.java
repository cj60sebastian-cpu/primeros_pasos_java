package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;
import util.ConexionBD;

public class ProductoDAO {

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.id_, p.codigo, p.descripcion, p.precio, p.provedores_id, pr.nombre_provedor " +
                     "FROM productos p LEFT JOIN provedores pr ON p.provedores_id = pr.id_";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_"));
                p.setCodigo(rs.getString("codigo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setProveedorId(rs.getInt("provedores_id"));
                p.setNombreProveedor(rs.getString("nombre_provedor") != null ? rs.getString("nombre_provedor") : "Sin Proveedor");
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean insertar(Producto p) {
        String sql = "INSERT INTO productos (id_, codigo, descripcion, precio, provedores_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getId());
            ps.setString(2, p.getCodigo());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());

            if (p.getProveedorId() > 0) {
                ps.setInt(5, p.getProveedorId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}