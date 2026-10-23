package controlador;

import com.google.gson.Gson;
import dao.EmpleadoDAO;
import modelo.Empleado;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = request.getReader();
        Empleado nuevoEmpleado = gson.fromJson(reader, Empleado.class);

        boolean registrado = empleadoDAO.registrar(nuevoEmpleado);

        PrintWriter out = response.getWriter();
        if (registrado) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"mensaje\": \"Empleado registrado exitosamente\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"No se pudo registrar el empleado\"}");
        }
        out.flush();
    }
}