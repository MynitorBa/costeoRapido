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
import java.util.ArrayList;
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
    costear.addActionListener(e -> mostrarDialogoCostear());
}
    
    private void mostrarDialogoBuscar() {
    JDialog dialogo = new JDialog(this, "Búsqueda de Productos", true);
    dialogo.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    
    // ComboBox para el criterio de búsqueda
    String[] criterios = {"ID", "Nombre", "Tipo", "Marca"};
    JComboBox<String> comboCriterio = new JComboBox<>(criterios);
    
    // Campo de texto para la búsqueda
    JTextField campoBusqueda = new JTextField(20);
    
    // Botón de búsqueda
    JButton botonBuscar = new JButton("Buscar");
    
    // Añadir componentes al diálogo
    gbc.gridx = 0;
    gbc.gridy = 0;
    dialogo.add(new JLabel("Buscar por:"), gbc);
    
    gbc.gridx = 1;
    dialogo.add(comboCriterio, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 1;
    dialogo.add(new JLabel("Criterio:"), gbc);
    
    gbc.gridx = 1;
    dialogo.add(campoBusqueda, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    dialogo.add(botonBuscar, gbc);
    
    botonBuscar.addActionListener(e -> {
        String criterio = comboCriterio.getSelectedItem().toString();
        String valorBusqueda = campoBusqueda.getText().trim();
        
        if (!valorBusqueda.isEmpty()) {
            modeloTabla.setRowCount(0);
            List<String[]> productos = gestorProductos.obtenerTodosLosProductos();
            List<String[]> resultados = new ArrayList<>();
            
            for (String[] producto : productos) {
                boolean coincide = false;
                switch (criterio) {
                    case "ID":
                        try {
                            int id = Integer.parseInt(valorBusqueda);
                            coincide = producto[0].equals(String.valueOf(id));
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(dialogo, 
                                "Por favor, ingrese un ID válido (número entero).", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        break;
                    case "Nombre":
                        coincide = producto[1].toLowerCase()
                                  .contains(valorBusqueda.toLowerCase());
                        break;
                    case "Tipo":
                        coincide = producto[5].toLowerCase()
                                  .contains(valorBusqueda.toLowerCase());
                        break;
                    case "Marca":
                        coincide = producto[6].toLowerCase()
                                  .contains(valorBusqueda.toLowerCase());
                        break;
                }
                
                if (coincide) {
                    resultados.add(producto);
                }
            }
            
            if (!resultados.isEmpty()) {
                for (String[] resultado : resultados) {
                    modeloTabla.addRow(resultado);
                }
                dialogo.dispose();
            } else {
                JOptionPane.showMessageDialog(dialogo,
                    "No se encontraron productos con ese criterio.",
                    "Sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(dialogo,
                "Por favor, ingrese un valor para buscar.",
                "Campo vacío",
                JOptionPane.WARNING_MESSAGE);
        }
    });
    
    // Configurar el diálogo
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
    dialogo.setLayout(new GridLayout(8, 2));

    // Crear los componentes
    JTextField campoNombre = new JTextField();
    JTextField campoPrecio = new JTextField();
    JComboBox<String> comboMoneda = new JComboBox<>(new String[]{"USD", "Quetzales"});
    JTextField campoCantidad = new JTextField();
    
    // Crear el ComboBox para tipos de producto
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
        costear = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        flechaIzquierda = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        recargar = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        bookmarkButton = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        flechaDerecha = new javax.swing.JButton();

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

        costear.setText("Costear");

        flechaIzquierda.setText("←");
        flechaIzquierda.setToolTipText("");

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

        bookmarkButton.setText("🔖");
        bookmarkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookmarkButtonActionPerformed(evt);
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
                .addComponent(bookmarkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(bookmarkButton)
                    .addComponent(menuButton)
                    .addComponent(flechaIzquierda)
                    .addComponent(searchButton)
                    .addComponent(flechaDerecha)
                    .addComponent(recargar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(152, 152, 152))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Agregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Editar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Eliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Buscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(guardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(costear)))
                        .addGap(15, 15, 15))))
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Agregar)
                    .addComponent(Editar)
                    .addComponent(Eliminar)
                    .addComponent(Buscar)
                    .addComponent(guardar)
                    .addComponent(costear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuButtonActionPerformed

    private void bookmarkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookmarkButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookmarkButtonActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

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
    private javax.swing.JButton bookmarkButton;
    private javax.swing.JButton costear;
    private javax.swing.JButton flechaDerecha;
    private javax.swing.JButton flechaIzquierda;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton menuButton;
    private javax.swing.JButton recargar;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables

    private void mostrarDialogoCostear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
