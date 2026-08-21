package principal;
import dao.ProductoDAO;
import modelo.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormularioProductos extends JFrame {

    // Componentes de la interfaz
    private JTextField txtId, txtCodigo, txtDescripcion, txtPrecio;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    // Conexión con el DAO
    private ProductoDAO productoDAO;

    public FormularioProductos() {
        productoDAO = new ProductoDAO();

        // Configuración básica de la ventana (JFrame) ventana flotante 
        setTitle("MiniStock Cloud - Gestión de Inventario");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout(10, 10));

        // --- 1. PANEL SUPERIOR (Formulario de Entrada) ---
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        panelFormulario.add(new JLabel(" ID (Solo lectura):"));
        txtId = new JTextField();
        txtId.setEditable(false); // El ID no se edita a mano, se genera o selecciona
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel(" Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel(" Descripción:"));
        txtDescripcion = new JTextField();
        panelFormulario.add(txtDescripcion);

        panelFormulario.add(new JLabel(" Precio ($):"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        // --- 2. PANEL DE BOTONES (Operaciones CRUD) ---
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar Camppos");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Agrupamos Formulario y Botones en el panel norte
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        // --- 3. PANEL CENTRAL (Tabla para visualizar registros) ---
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Código", "Descripción", "Precio"}, 0);
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Listado de Productos en Base de Datos"));
        add(scrollTabla, BorderLayout.CENTER);

        // --- 4. EVENTOS DE LOS BOTONES ---
        
        // Guardar
        btnGuardar.addActionListener(e -> guardarProducto());

        // Actualizar
        btnActualizar.addActionListener(e -> actualizarProducto());

        // Eliminar
        btnEliminar.addActionListener(e -> eliminarProducto());

        // Limpiar campos
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Seleccionar fila de la tabla para cargar datos en los campos
        tablaProductos.getSelectionModel().addListSelectionListener(e -> seleccionarFila());

        // Cargar los datos iniciales de MySQL en la tabla
        cargarDatosTabla();
    }

    // --- MÉTODOS DE LÓGICA DE INTERFAZ ---

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); // Limpiar filas anteriores
        List<Producto> lista = productoDAO.listar();
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getCodigo(), p.getDescripcion(), p.getPrecio()});
        }
    }

    private void guardarProducto() {
        if (validarCampos()) {
            String codigo = txtCodigo.getText();
            String descripcion = txtDescripcion.getText();
            int precio = Integer.parseInt(txtPrecio.getText());

            Producto p = new Producto(codigo, descripcion, precio);
            if (productoDAO.insertar(p)) {
                JOptionPane.showMessageDialog(this, "✅ Producto guardado con éxito.");
                limpiarCampos();
                cargarDatosTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarProducto() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Seleccione un producto de la tabla para actualizar.");
            return;
        }

        if (validarCampos()) {
            int id = Integer.parseInt(txtId.getText());
            String codigo = txtCodigo.getText();
            String descripcion = txtDescripcion.getText();
            int precio = Integer.parseInt(txtPrecio.getText());

            Producto p = new Producto(id, codigo, descripcion, precio);
            if (productoDAO.actualizar(p)) {
                JOptionPane.showMessageDialog(this, "✅ Producto actualizado con éxito.");
                limpiarCampos();
                cargarDatosTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al actualizar producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarProducto() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Seleccione un producto de la tabla para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            if (productoDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "✅ Producto eliminado con éxito.");
                limpiarCampos();
                cargarDatosTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al eliminar producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void seleccionarFila() {
        int fila = tablaProductos.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtCodigo.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtPrecio.setText(modeloTabla.getValueAt(fila, 3).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        tablaProductos.clearSelection();
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor llene todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtPrecio.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ El precio debe ser un número entero válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}