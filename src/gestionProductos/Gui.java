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
/**
 *
 * @author andre
 */
public class Gui extends javax.swing.JFrame {
    private DefaultTableModel modeloTabla;
    private GestorProductos2 gestorProductos;
    

    /**
     * Creates new form Gui
     */
    public Gui() {
        initComponents();
        gestorProductos = new GestorProductos2();
        inicializarTabla();
        cargarProductos();
        configurarBotones();
        botonRegresarGUIPrincipal.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            botonRegresarGUIPrincipalActionPerformed(evt);
        }
        });
    }
    
    
    private void inicializarTabla() {
        String[] columnas = {"ID", "Nombre", "Precio USD", "Precio Quetzales", "Cantidad", "Tipo", "Marca", "Etiquetas"};
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
}
    private void guardarCambios() {
    int fila = jTable1.getSelectedRow();
    if (fila != -1) {
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
        dialogo.setLayout(new GridLayout(9, 2));

        JTextField campoId = new JTextField();
        JTextField campoNombre = new JTextField();
        JTextField campoPrecio = new JTextField();
        JComboBox<String> comboMoneda = new JComboBox<>(new String[]{"USD", "Quetzales"});
        JTextField campoCantidad = new JTextField();
        JTextField campoTipo = new JTextField();
        JTextField campoMarca = new JTextField();
        JTextField campoEtiquetas = new JTextField();

        dialogo.add(new JLabel("ID:"));
        dialogo.add(campoId);
        dialogo.add(new JLabel("Nombre:"));
        dialogo.add(campoNombre);
        dialogo.add(new JLabel("Precio:"));
        dialogo.add(campoPrecio);
        dialogo.add(new JLabel("Moneda:"));
        dialogo.add(comboMoneda);
        dialogo.add(new JLabel("Cantidad:"));
        dialogo.add(campoCantidad);
        dialogo.add(new JLabel("Tipo:"));
        dialogo.add(campoTipo);
        dialogo.add(new JLabel("Marca:"));
        dialogo.add(campoMarca);
        dialogo.add(new JLabel("Etiquetas:"));
        dialogo.add(campoEtiquetas);

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                String nombre = campoNombre.getText();
                double precio = Double.parseDouble(campoPrecio.getText());
                boolean esUSD = comboMoneda.getSelectedItem().equals("USD");
                int cantidad = Integer.parseInt(campoCantidad.getText());
                String tipo = campoTipo.getText();
                String marca = campoMarca.getText();
                String etiquetas = campoEtiquetas.getText();

                double precioUSD = esUSD ? precio : precio / 7.8;
                double precioQuetzales = esUSD ? precio * 7.8 : precio;

                gestorProductos.agregarProducto(nombre, precioUSD, precioQuetzales, cantidad, tipo, marca, etiquetas);
                cargarProductos();
                dialogo.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "Por favor, ingrese valores numéricos válidos.");
            }
        });

        dialogo.add(botonAgregar);
        dialogo.pack();
        dialogo.setVisible(true);
    }

    private void mostrarDialogoEditar() {
        String[] nombresProductos = gestorProductos.obtenerTodosLosProductos().stream()
                .map(p -> p[1])
                .toArray(String[]::new);

        String productoSeleccionado = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione un producto para editar:",
                "Editar Producto",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombresProductos,
                nombresProductos[0]);

        if (productoSeleccionado != null) {
            String[] producto = gestorProductos.buscarProductoPorNombre(productoSeleccionado);
            if (producto != null) {
                mostrarDetallesProducto(producto);
            }
        }
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
        String[] nombresProductos = gestorProductos.obtenerTodosLosProductos().stream()
                .map(p -> p[1])
                .toArray(String[]::new);

        String productoSeleccionado = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione un producto para eliminar:",
                "Eliminar Producto",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombresProductos,
                nombresProductos[0]);

        if (productoSeleccionado != null) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de que desea eliminar el producto " + productoSeleccionado + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                String[] producto = gestorProductos.buscarProductoPorNombre(productoSeleccionado);
                if (producto != null) {
                    int id = Integer.parseInt(producto[0]);
                    gestorProductos.eliminarProducto(id);
                    cargarProductos();
                    JOptionPane.showMessageDialog(this, "Producto eliminado con éxito.");
                }
            }
        }
    }

    private void mostrarDialogoBuscar() {
        String criterioBusqueda = JOptionPane.showInputDialog(this, "Ingrese el nombre o ID del producto a buscar:");
        if (criterioBusqueda != null && !criterioBusqueda.isEmpty()) {
            String[] producto = null;
            try {
                int id = Integer.parseInt(criterioBusqueda);
                producto = gestorProductos.buscarProductoPorId(id);
            } catch (NumberFormatException e) {
                producto = gestorProductos.buscarProductoPorNombre(criterioBusqueda);
            }

            if (producto != null) {
                mostrarDetallesProducto(producto);
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
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

        jLabel1 = new javax.swing.JLabel();
        Agregar = new javax.swing.JButton();
        Editar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Buscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        guardar = new javax.swing.JButton();
        botonRegresarGUIPrincipal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Productos");

        Agregar.setText("Agregar");

        Editar.setText("Editar");

        Eliminar.setText("Eliminar");

        Buscar.setText("Buscar");

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

        guardar.setText("Guardar");

        botonRegresarGUIPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/left_arrow (1).png"))); // NOI18N
        botonRegresarGUIPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegresarGUIPrincipalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonRegresarGUIPrincipal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Agregar)
                                .addGap(25, 25, 25)
                                .addComponent(Editar)
                                .addGap(31, 31, 31)
                                .addComponent(Eliminar)
                                .addGap(26, 26, 26)
                                .addComponent(Buscar)
                                .addGap(18, 18, 18)
                                .addComponent(guardar)))
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(botonRegresarGUIPrincipal))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Agregar)
                    .addComponent(Editar)
                    .addComponent(Eliminar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Buscar)
                        .addComponent(guardar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonRegresarGUIPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresarGUIPrincipalActionPerformed
        // TODO add your handling code here:
        botonRegresarGUIPrincipal.setEnabled(false);

    // Cerrar la ventana actual (GUI de productos)
    this.dispose();
    
    // Crear y mostrar la GUI principal
    java.awt.EventQueue.invokeLater(() -> {
        new GuiPrincipal().setVisible(true);
    });
    }//GEN-LAST:event_botonRegresarGUIPrincipalActionPerformed

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
                new Gui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar;
    private javax.swing.JButton Buscar;
    private javax.swing.JButton Editar;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton botonRegresarGUIPrincipal;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
