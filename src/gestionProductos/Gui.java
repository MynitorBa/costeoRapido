/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestionProductos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import GUI.GuiPrincipal;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import paqueteCosteoRapido.CosteoForm_Ingresar;
/**
 *
 * @author andre
 */
public class Gui extends javax.swing.JFrame {
    private DefaultTableModel modeloTabla;
    private GestorProductos2 gestorProductos;
    private String currentUser;
    

    /**
     * Creates new form Gui
     */
    public Gui(String username) {
        this.currentUser = username;
        initComponents();
        gestorProductos = new GestorProductos2();
        inicializarTabla();
        cargarProductos();
        configurarBotones();
        
    }
    
    
    private void inicializarTabla() {
        String[] columnas = {"ID", "Nombre", "Precio USD", "Precio Quetzales", "Cantidad", "Tipo", "Marca", "Etiquetas", "Otros"};
    modeloTabla = new DefaultTableModel(columnas, 0);
    jTable1.setModel(modeloTabla);
    }

    private void cargarProductos() {
        modeloTabla.setRowCount(0);
        List<String[]> productos = gestorProductos.obtenerTodosLosProductos();
        for (String[] producto : productos) {
            modeloTabla.addRow(producto);
        }
    }

    private void configurarBotones() {
    Agregar.addActionListener(e -> mostrarDialogoAgregar());
    Editar.addActionListener(e -> mostrarDialogoEditar());
    Eliminar.addActionListener(e -> mostrarDialogoEliminar());
    Buscar.addActionListener(e -> mostrarDialogoBuscar());
    guardar.addActionListener(e -> guardarCambios());
    costear.addActionListener(e -> mostrarDialogoCostear());
}
    
    private void mostrarDialogoBuscar() {
    JDialog dialogo = new JDialog(this, "Búsqueda de Productos", true);
    dialogo.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    
    // Campo de texto para la búsqueda
    JTextField campoBusqueda = new JTextField(30);
    campoBusqueda.setText("Buscar por ID, Nombre, Tipo, Marca o Etiqueta");
    campoBusqueda.setForeground(Color.GRAY);
    
    // Placeholder behavior
    configurarPlaceholder(campoBusqueda, "Buscar por ID, Nombre, Tipo, Marca o Etiqueta");
    
    // Botón de búsqueda
    JButton botonBuscar = new JButton("Buscar");
    
    // Añadir componentes al diálogo
    gbc.gridx = 0;
    gbc.gridy = 0;
    dialogo.add(new JLabel("Buscar:"), gbc);
    
    gbc.gridx = 1;
    dialogo.add(campoBusqueda, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    dialogo.add(botonBuscar, gbc);
    
    botonBuscar.addActionListener(e -> {
        buscarYMostrarResultados(campoBusqueda.getText().trim(), modeloTabla);
        dialogo.dispose();
    });
    
    dialogo.pack();
    dialogo.setLocationRelativeTo(this);
    dialogo.setVisible(true);
}

    
    private void abrirVentanaCosteoIngresar(String[] producto) {
    SwingUtilities.invokeLater(() -> {
        try {
            String nombre = producto[1];
            double costoFob = Double.parseDouble(producto[2].replace("$", ""));
            double flete = 0;
            double margenVenta = 0;

            CosteoForm_Ingresar costeoForm = new CosteoForm_Ingresar(currentUser, nombre, costoFob, flete, margenVenta);
            costeoForm.setVisible(true);
            this.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al procesar los datos del producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
}
    
    
    
    private void guardarCambios() {
    int fila = jTable1.getSelectedRow();
    if (fila != -1) {
        try {
            int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            String nombre = modeloTabla.getValueAt(fila, 1).toString();
            
            // Obtener ambos precios de la tabla
            String precioUSDStr = modeloTabla.getValueAt(fila, 2).toString().replace("$", "");
            String precioQuetzalesStr = modeloTabla.getValueAt(fila, 3).toString().replace("Q", "");
            
            // Verificar cuál precio fue modificado comparando con los datos originales
            String[] productoOriginal = gestorProductos.buscarProductoPorId(id);
            double precioUSDOriginal = Double.parseDouble(productoOriginal[2].replace("$", ""));
            double precioQuetzalesOriginal = Double.parseDouble(productoOriginal[3].replace("Q", ""));
            
            double precioUSD = Double.parseDouble(precioUSDStr);
            double precioQuetzales = Double.parseDouble(precioQuetzalesStr);
            
            // Si el precio USD cambió, actualizar el precio en Quetzales
            if (precioUSD != precioUSDOriginal) {
                precioQuetzales = precioUSD * 7.8;
            }
            // Si el precio en Quetzales cambió, actualizar el precio USD
            else if (precioQuetzales != precioQuetzalesOriginal) {
                precioUSD = precioQuetzales / 7.8;
            }
            
            int cantidad = Integer.parseInt(modeloTabla.getValueAt(fila, 4).toString());
            String tipo = modeloTabla.getValueAt(fila, 5).toString();
            String marca = modeloTabla.getValueAt(fila, 6).toString();
            String etiquetas = modeloTabla.getValueAt(fila, 7).toString();

            gestorProductos.editarProducto(id, nombre, precioUSD, precioQuetzales, cantidad, tipo, marca, etiquetas);
            cargarProductos();
            JOptionPane.showMessageDialog(this, "Cambios guardados con éxito.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto para guardar los cambios.");
    }
}
    
    
    
    private void mostrarDialogoAgregar() {
        
        JDialog dialogo = new JDialog(this, "Agregar Producto", true);
    dialogo.setLayout(new GridLayout(8, 2));

    // Crear los componentes
    JTextField campoNombre = new JTextField();
    JTextField campoPrecio = new JTextField();
    JComboBox<String> comboMoneda = new JComboBox<>(new String[]{"USD", "Quetzales"});
    JTextField campoCantidad = new JTextField();
    
    String[] tiposProducto = {
        "CAMARA",
        "SISTEMA DE ALMACENAMIENTO",
        "ACCESORIO CCTV",
        "CONTROL DE ACCESO Y SEGURIDAD",
        "SISTEMAS DE RED"
    };
    JComboBox<String> comboTipo = new JComboBox<>(tiposProducto);
    
    JTextField campoMarca = new JTextField();
    JTextField campoEtiquetas = new JTextField();

    // Agregar los componentes al diálogo
    dialogo.add(new JLabel("Nombre:"));
    dialogo.add(campoNombre);
    dialogo.add(new JLabel("Precio:"));
    dialogo.add(campoPrecio);
    dialogo.add(new JLabel("Moneda:"));
    dialogo.add(comboMoneda);
    dialogo.add(new JLabel("Cantidad:"));
    dialogo.add(campoCantidad);
    dialogo.add(new JLabel("Tipo:"));
    dialogo.add(comboTipo);
    dialogo.add(new JLabel("Marca:"));
    dialogo.add(campoMarca);
    dialogo.add(new JLabel("Etiquetas:"));
    dialogo.add(campoEtiquetas);

    JButton botonAgregar = new JButton("Agregar");
    botonAgregar.addActionListener(e -> {
        try {
            String nombre = campoNombre.getText();
            double precio = Double.parseDouble(campoPrecio.getText());
            boolean esUSD = comboMoneda.getSelectedItem().equals("USD");
            int cantidad = Integer.parseInt(campoCantidad.getText());
            String tipo = comboTipo.getSelectedItem().toString();
            String marca = campoMarca.getText();
            String etiquetas = campoEtiquetas.getText();

            double precioUSD = esUSD ? precio : precio / 7.8;
            double precioQuetzales = esUSD ? precio * 7.8 : precio;

            gestorProductos.agregarProducto(nombre, precioUSD, precioQuetzales, cantidad, tipo, marca, etiquetas);
            cargarProductos();
            dialogo.dispose();
            JOptionPane.showMessageDialog(this, "Producto agregado con éxito.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialogo, "Por favor, ingrese valores numéricos válidos.");
        }
    });

    dialogo.add(botonAgregar);
    dialogo.pack();
    dialogo.setLocationRelativeTo(this);
    dialogo.setVisible(true);
}
    

    private void mostrarDialogoEditar() {
    JDialog dialogoSeleccion = new JDialog(this, "Seleccionar Producto para Editar", true);
    dialogoSeleccion.setLayout(new BorderLayout(10, 10));
    dialogoSeleccion.setSize(500, 400);
    
    // Panel de búsqueda
    JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
    JTextField campoBusqueda = new JTextField(30);
    configurarPlaceholder(campoBusqueda, "Buscar por ID, Nombre, Tipo, Marca o Etiqueta");
    panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
    panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
    
    // Lista de productos
    DefaultListModel<ProductoListItem> modeloLista = new DefaultListModel<>();
    JList<ProductoListItem> listaProductos = new JList<>(modeloLista);
    JScrollPane scrollPane = new JScrollPane(listaProductos);
    
    // Cargar productos iniciales
    cargarProductosEnLista(modeloLista);
    
    // Configurar búsqueda en tiempo real
    campoBusqueda.getDocument().addDocumentListener(new DocumentListener() {
        private void searchProduct() {
            actualizarListaProductos(campoBusqueda.getText(), modeloLista);
        }
        
        @Override
        public void insertUpdate(DocumentEvent e) { searchProduct(); }
        @Override
        public void removeUpdate(DocumentEvent e) { searchProduct(); }
        @Override
        public void changedUpdate(DocumentEvent e) { searchProduct(); }
    });
    
    // Doble clic para editar
    listaProductos.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                ProductoListItem item = listaProductos.getSelectedValue();
                if (item != null) {
                    String[] producto = gestorProductos.buscarProductoPorId(item.getId());
                    dialogoSeleccion.dispose();
                    mostrarDialogoEdicionProducto(producto);
                }
            }
        }
    });
    
    dialogoSeleccion.add(panelBusqueda, BorderLayout.NORTH);
    dialogoSeleccion.add(scrollPane, BorderLayout.CENTER);
    dialogoSeleccion.setLocationRelativeTo(this);
    dialogoSeleccion.setVisible(true);
}

    
    
    private void mostrarDialogoEdicionProducto(String[] producto) {
    JDialog dialogo = new JDialog(this, "Editar Producto", true);
    dialogo.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;

    // Crear los componentes
    JTextField campoNombre = new JTextField(20);
    JTextField campoPrecio = new JTextField(20);
    JComboBox<String> comboMoneda = new JComboBox<>(new String[]{"USD", "Quetzales"});
    JTextField campoCantidad = new JTextField(20);
    String[] tiposProducto = {
        "CAMARA",
        "SISTEMA DE ALMACENAMIENTO",
        "ACCESORIO CCTV",
        "CONTROL DE ACCESO Y SEGURIDAD",
        "SISTEMAS DE RED"
    };
    JComboBox<String> comboTipo = new JComboBox<>(tiposProducto);
    JTextField campoMarca = new JTextField(20);
    JTextField campoEtiquetas = new JTextField(20);

    // Establecer valores actuales
    campoNombre.setText(producto[1]);
    campoPrecio.setText(producto[2].replace("$", ""));
    comboMoneda.setSelectedItem("USD");
    campoCantidad.setText(producto[4]);
    comboTipo.setSelectedItem(producto[5]);
    campoMarca.setText(producto[6]);
    campoEtiquetas.setText(producto[7]);

    // Agregar componentes al diálogo
    dialogo.add(new JLabel("Nombre:"), gbc);
    gbc.gridx = 1;
    dialogo.add(campoNombre, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    dialogo.add(new JLabel("Precio:"), gbc);
    gbc.gridx = 1;
    dialogo.add(campoPrecio, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    dialogo.add(new JLabel("Moneda:"), gbc);
    gbc.gridx = 1;
    dialogo.add(comboMoneda, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    dialogo.add(new JLabel("Cantidad:"), gbc);
    gbc.gridx = 1;
    dialogo.add(campoCantidad, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    dialogo.add(new JLabel("Tipo:"), gbc);
    gbc.gridx = 1;
    dialogo.add(comboTipo, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    dialogo.add(new JLabel("Marca:"), gbc);
    gbc.gridx = 1;
    dialogo.add(campoMarca, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    dialogo.add(new JLabel("Etiquetas:"), gbc);
    gbc.gridx = 1;
    dialogo.add(campoEtiquetas, gbc);

    // Botón de guardar cambios
    JButton botonGuardar = new JButton("Guardar Cambios");
    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    dialogo.add(botonGuardar, gbc);

    botonGuardar.addActionListener(e -> {
        try {
            int id = Integer.parseInt(producto[0]);
            String nombre = campoNombre.getText();
            double precio = Double.parseDouble(campoPrecio.getText());
            boolean esUSD = comboMoneda.getSelectedItem().equals("USD");
            int cantidad = Integer.parseInt(campoCantidad.getText());
            String tipo = comboTipo.getSelectedItem().toString();
            String marca = campoMarca.getText();
            String etiquetas = campoEtiquetas.getText();

            double precioUSD = esUSD ? precio : precio / 7.8;
            double precioQuetzales = esUSD ? precio * 7.8 : precio;

            gestorProductos.editarProducto(id, nombre, precioUSD, precioQuetzales, cantidad, tipo, marca, etiquetas);
            cargarProductos();
            dialogo.dispose();
            JOptionPane.showMessageDialog(this, "Producto actualizado con éxito.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialogo, "Por favor, ingrese valores numéricos válidos.");
        }
    });

    dialogo.pack();
    dialogo.setLocationRelativeTo(this);
    dialogo.setVisible(true);
}

private boolean validarCampos(JTextField nombre, JTextField precioUSD, JTextField precioQuetzales, JTextField cantidad) {
    if (nombre.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    try {
        double precio = Double.parseDouble(precioUSD.getText().trim());
        if (precio <= 0) {
            JOptionPane.showMessageDialog(null, "El precio debe ser mayor que 0.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "El precio USD debe ser un número válido.", "Error de validación", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    try {
        double precio = Double.parseDouble(precioQuetzales.getText().trim());
        if (precio <= 0) {
            JOptionPane.showMessageDialog(null, "El precio debe ser mayor que 0.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "El precio en Quetzales debe ser un número válido.", "Error de validación", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    try {
        int cant = Integer.parseInt(cantidad.getText().trim());
        if (cant < 0) {
            JOptionPane.showMessageDialog(null, "La cantidad no puede ser negativa.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero válido.", "Error de validación", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    return true;
}



    private void mostrarDetallesProducto(String[] producto) {
        modeloTabla.setRowCount(0);
        modeloTabla.addRow(producto);

        JButton botonGuardar = new JButton("Guardar Cambios");
        botonGuardar.addActionListener(e -> {
            int fila = 0;
            try {
                int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                String nombre = modeloTabla.getValueAt(fila, 1).toString();
                double precioUSD = Double.parseDouble(modeloTabla.getValueAt(fila, 2).toString().replace("$", ""));
                double precioQuetzales = Double.parseDouble(modeloTabla.getValueAt(fila, 3).toString().replace("Q", ""));
                int cantidad = Integer.parseInt(modeloTabla.getValueAt(fila, 4).toString());
                String tipo = modeloTabla.getValueAt(fila, 5).toString();
                String marca = modeloTabla.getValueAt(fila, 6).toString();
                String etiquetas = modeloTabla.getValueAt(fila, 7).toString();

                gestorProductos.editarProducto(id, nombre, precioUSD, precioQuetzales, cantidad, tipo, marca, etiquetas);
                cargarProductos();
                JOptionPane.showMessageDialog(this, "Producto actualizado con éxito.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonGuardar);
        this.add(panelBoton, BorderLayout.SOUTH);
        this.revalidate();
    }
    private void mostrarDialogoEliminar() {
    JDialog dialogoSeleccion = new JDialog(this, "Seleccionar Producto para Eliminar", true);
    dialogoSeleccion.setLayout(new BorderLayout(10, 10));
    dialogoSeleccion.setSize(500, 400);
    
    // Panel de búsqueda
    JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
    JTextField campoBusqueda = new JTextField(30);
    configurarPlaceholder(campoBusqueda, "Buscar por ID, Nombre, Tipo, Marca o Etiqueta");
    panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
    panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
    
    // Lista de productos
    DefaultListModel<ProductoListItem> modeloLista = new DefaultListModel<>();
    JList<ProductoListItem> listaProductos = new JList<>(modeloLista);
    JScrollPane scrollPane = new JScrollPane(listaProductos);
    
    // Cargar productos iniciales
    cargarProductosEnLista(modeloLista);
    
    // Configurar búsqueda en tiempo real
    campoBusqueda.getDocument().addDocumentListener(new DocumentListener() {
        private void searchProduct() {
            actualizarListaProductos(campoBusqueda.getText(), modeloLista);
        }
        
        @Override
        public void insertUpdate(DocumentEvent e) { searchProduct(); }
        @Override
        public void removeUpdate(DocumentEvent e) { searchProduct(); }
        @Override
        public void changedUpdate(DocumentEvent e) { searchProduct(); }
    });
    
    // Panel de botones
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton botonEliminar = new JButton("Eliminar");
    JButton botonCancelar = new JButton("Cancelar");
    
    botonEliminar.addActionListener(e -> {
        ProductoListItem item = listaProductos.getSelectedValue();
        if (item != null) {
            int confirmacion = JOptionPane.showConfirmDialog(
                dialogoSeleccion,
                "¿Está seguro de que desea eliminar este producto?\n" + item.toString(),
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                gestorProductos.eliminarProducto(item.getId());
                cargarProductos();
                JOptionPane.showMessageDialog(this, "Producto eliminado con éxito.");
                dialogoSeleccion.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(dialogoSeleccion,
                "Por favor, seleccione un producto para eliminar.",
                "Ningún producto seleccionado",
                JOptionPane.WARNING_MESSAGE);
        }
    });
    
    botonCancelar.addActionListener(e -> dialogoSeleccion.dispose());
    
    panelBotones.add(botonEliminar);
    panelBotones.add(botonCancelar);
    
    dialogoSeleccion.add(panelBusqueda, BorderLayout.NORTH);
    dialogoSeleccion.add(scrollPane, BorderLayout.CENTER);
    dialogoSeleccion.add(panelBotones, BorderLayout.SOUTH);
    dialogoSeleccion.setLocationRelativeTo(this);
    dialogoSeleccion.setVisible(true);
}

    private void mostrarDialogoCostear() {
        int filaSeleccionada = jTable1.getSelectedRow();
    if (filaSeleccionada != -1) {
        // Obtener los datos del producto seleccionado
        String[] producto = new String[]{
            modeloTabla.getValueAt(filaSeleccionada, 0).toString(), // ID
            modeloTabla.getValueAt(filaSeleccionada, 1).toString(), // Nombre
            modeloTabla.getValueAt(filaSeleccionada, 2).toString(), // Precio USD
        };
        
        // Abrir la ventana de costeo con los datos del producto
        abrirVentanaCosteoIngresar(producto);
    } else {
        JOptionPane.showMessageDialog(this,
            "Por favor, seleccione un producto para costear.",
            "Ningún producto seleccionado",
            JOptionPane.WARNING_MESSAGE);
    }
    }
    
    private class ProductoListItem {
    private final int id;
    private final String nombre;
    private final String tipo;
    private final String marca;
    private final String etiquetas;
    
    public ProductoListItem(String[] producto) {
        this.id = Integer.parseInt(producto[0]);
        this.nombre = producto[1];
        this.tipo = producto[5];
        this.marca = producto[6];
        this.etiquetas = producto[7];
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d - %s - Tipo: %s - Marca: %s", id, nombre, tipo, marca);
    }
}
    private void configurarPlaceholder(JTextField campo, String placeholder) {
    campo.setText(placeholder);
    campo.setForeground(Color.GRAY);
    
    campo.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            if (campo.getText().equals(placeholder)) {
                campo.setText("");
                campo.setForeground(Color.BLACK);
            }
        }
        
        @Override
        public void focusLost(FocusEvent e) {
            if (campo.getText().isEmpty()) {
                campo.setText(placeholder);
                campo.setForeground(Color.GRAY);
            }
        }
    });
}
    private void cargarProductosEnLista(DefaultListModel<ProductoListItem> modeloLista) {
    modeloLista.clear();
    List<String[]> productos = gestorProductos.obtenerTodosLosProductos();
    for (String[] producto : productos) {
        modeloLista.addElement(new ProductoListItem(producto));
    }
}

private void actualizarListaProductos(String busqueda, DefaultListModel<ProductoListItem> modeloLista) {
    busqueda = busqueda.toLowerCase().trim();
    if (busqueda.equals("buscar por id, nombre, tipo, marca o etiqueta")) {
        cargarProductosEnLista(modeloLista);
        return;
    }
    
    modeloLista.clear();
    List<String[]> productos = gestorProductos.obtenerTodosLosProductos();
    
    for (String[] producto : productos) {
        if (coincideConBusqueda(producto, busqueda)) {
            modeloLista.addElement(new ProductoListItem(producto));
        }
    }
}

private boolean coincideConBusqueda(String[] producto, String busqueda) {
    if (busqueda.isEmpty()) return true;
    
    // Buscar en ID
    if (producto[0].toLowerCase().contains(busqueda)) return true;
    
    // Buscar en Nombre
    if (producto[1].toLowerCase().contains(busqueda)) return true;
    
    // Buscar en Tipo
    if (producto[5].toLowerCase().contains(busqueda)) return true;
    
    // Buscar en Marca
    if (producto[6].toLowerCase().contains(busqueda)) return true;
    
    // Buscar en Etiquetas
    return producto[7].toLowerCase().contains(busqueda);
}

private void buscarYMostrarResultados(String valorBusqueda, DefaultTableModel modeloTabla) {
    valorBusqueda = valorBusqueda.toLowerCase();
    if (valorBusqueda.isEmpty() || 
        valorBusqueda.equals("buscar por id, nombre, tipo, marca o etiqueta")) {
        JOptionPane.showMessageDialog(null,
            "Por favor, ingrese un término de búsqueda válido.",
            "Campo vacío",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    modeloTabla.setRowCount(0);
    List<String[]> productos = gestorProductos.obtenerTodosLosProductos();
    List<String[]> resultados = new ArrayList<>();
    
    for (String[] producto : productos) {
        if (coincideConBusqueda(producto, valorBusqueda)) {
            resultados.add(producto);
        }
    }
    
    if (!resultados.isEmpty()) {
        for (String[] resultado : resultados) {
            modeloTabla.addRow(resultado);
        }
    } else {
        JOptionPane.showMessageDialog(null,
            "No se encontraron productos que coincidan con la búsqueda.",
            "Sin resultados",
            JOptionPane.INFORMATION_MESSAGE);
    }
}


    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        flechaIzquierda = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        recargar = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        favoritos = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        flechaDerecha = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Agregar = new javax.swing.JButton();
        Editar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Buscar = new javax.swing.JButton();
        guardar = new javax.swing.JButton();
        costear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(178, 171, 171));

        jPanel1.setBackground(new java.awt.Color(178, 171, 171));

        flechaIzquierda.setText("←");
        flechaIzquierda.setToolTipText("");
        flechaIzquierda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flechaIzquierdaActionPerformed(evt);
            }
        });

        searchButton.setText("🔍");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        recargar.setText("🔄");

        menuButton.setText("☰");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        favoritos.setText("❤");
        favoritos.setToolTipText("");
        favoritos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoritosActionPerformed(evt);
            }
        });

        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });

        flechaDerecha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        flechaDerecha.setText("→");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(flechaIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(flechaDerecha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(recargar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(favoritos, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(favoritos)
                    .addComponent(menuButton)
                    .addComponent(flechaIzquierda)
                    .addComponent(searchButton)
                    .addComponent(flechaDerecha)
                    .addComponent(recargar))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 0));
        jLabel1.setText("Productos");

        Agregar.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        Agregar.setText("Agregar");

        Editar.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        Editar.setText("Editar");

        Eliminar.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        Eliminar.setText("Eliminar");

        Buscar.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        Buscar.setText("Buscar");

        guardar.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        guardar.setText("Guardar");

        costear.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        costear.setText("Costear");

        jScrollPane1.setFont(new java.awt.Font("Segoe UI Variable", 0, 12)); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Agregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Editar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Eliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Buscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(guardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(costear)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Agregar)
                    .addComponent(Editar)
                    .addComponent(Eliminar)
                    .addComponent(Buscar)
                    .addComponent(guardar)
                    .addComponent(costear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuButtonActionPerformed

    private void favoritosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoritosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_favoritosActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

    private void flechaIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flechaIzquierdaActionPerformed
        // TODO add your handling code here:
        
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new GuiPrincipal(currentUser).setVisible(true);
        });
    }//GEN-LAST:event_flechaIzquierdaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui("admin").setVisible(true); // Usar un valor por defecto para pruebas
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar;
    private javax.swing.JButton Buscar;
    private javax.swing.JButton Editar;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton costear;
    private javax.swing.JButton favoritos;
    private javax.swing.JButton flechaDerecha;
    private javax.swing.JButton flechaIzquierda;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton menuButton;
    private javax.swing.JButton recargar;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables

    
}
