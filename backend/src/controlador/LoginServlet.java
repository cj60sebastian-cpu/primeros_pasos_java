package controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import dao.EmpleadoDAO;
import modelo.Empleado;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private Gson gson = new Gson();

    private void configurarCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configurarCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configurarCORS(response);

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        Empleado datosLogin = gson.fromJson(buffer.toString(), Empleado.class);
        Empleado empleado = empleadoDAO.autenticar(datosLogin.getEmail(), datosLogin.getClave());

        PrintWriter out = response.getWriter();
        if (empleado != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(gson.toJson(empleado));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("{\"error\": \"Credenciales incorrectas\"}");
        }
        out.flush();
    }
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    configurarCORS(response);
    
    List<Empleado> empleados = empleadoDAO.listarTodos();
    
    PrintWriter out = response.getWriter();
    response.setStatus(HttpServletResponse.SC_OK);
    out.print(gson.toJson(empleados));
    out.flush();
}
}