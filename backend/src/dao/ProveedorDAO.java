package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Proveedor;
import util.ConexionBD;

public class ProveedorDAO {

    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        // NOMBRE EXACTO DE LA TABLA: provedores
        String sql = "SELECT id_, nombre_provedor, factura_compra_id FROM provedores";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("id_"));
                p.setNombreProveedor(rs.getString("nombre_provedor"));
                p.setFacturaCompraId(rs.getInt("factura_compra_id"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean insertar(Proveedor p) {
        // NOMBRE EXACTO DE LA TABLA: provedores
        String sql = "INSERT INTO provedores (id_, nombre_provedor, factura_compra_id) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getId());
            ps.setString(2, p.getNombreProveedor());
            
            if (p.getFacturaCompraId() > 0) {
                ps.setInt(3, p.getFacturaCompraId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}