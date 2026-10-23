package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelo.Empleado;
import util.ConexionBD;

public class EmpleadoDAO {

    public Empleado autenticar(String email, String clave) {
        Empleado emp = null;
        String sql = "SELECT id_ AS id, nombre, apellidos, email FROM empleados WHERE email = ? AND clave = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emp = new Empleado();
                    emp.setId(rs.getInt("id"));
                    emp.setNombre(rs.getString("nombre"));
                    emp.setApellidos(rs.getString("apellidos"));
                    emp.setEmail(rs.getString("email"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;
    }

    public List<Empleado> listarTodos() {
    List<Empleado> lista = new ArrayList<>();
    String sql = "SELECT id_ AS id, nombre, apellidos, email FROM empleados";

    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Empleado emp = new Empleado();
            emp.setId(rs.getInt("id"));
            emp.setNombre(rs.getString("nombre"));
            emp.setApellidos(rs.getString("apellidos"));
            emp.setEmail(rs.getString("email"));
            lista.add(emp);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}
public boolean registrar(Empleado emp) {
    String sql = "INSERT INTO empleados (nombre, apellidos, nro_cc, email, clave) VALUES (?, ?, ?, ?, ?)";
    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, emp.getNombre());
        ps.setString(2, emp.getApellidos());
        ps.setString(3, emp.getNro_cc());
        ps.setString(4, emp.getEmail());
        ps.setString(5, emp.getClave());
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}
