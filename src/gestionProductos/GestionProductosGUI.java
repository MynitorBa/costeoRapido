/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestionProductos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author andre
 */
public class GestionProductosGUI extends javax.swing.JFrame {

    private GestorProductos gestor;
    private DefaultTableModel modeloTabla;


    /**
     * Creates new form GestionProductosGUI
     */
    public GestionProductosGUI() {
        initComponents();
        try {
        gestor = new GestorProductos();
        inicializarTabla();
        cargarProductos();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al inicializar la gestión de productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
        Agregar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        AgregarActionPerformed(e);
    }
});
        jTable2.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow != -1) {
                    txtId.setText(jTable2.getValueAt(selectedRow, 0).toString());
                    txtNombre.setText(jTable2.getValueAt(selectedRow, 1).toString());
                    txtPrecioUSD.setText(jTable2.getValueAt(selectedRow, 2).toString());
                    txtPrecioQuetzales.setText(jTable2.getValueAt(selectedRow, 3).toString());
                    txtCantidad.setText(jTable2.getValueAt(selectedRow, 4).toString());
                    txtTipo.setText(jTable2.getValueAt(selectedRow, 5).toString());
                    txtMarca.setText(jTable2.getValueAt(selectedRow, 6).toString());
                    txtEtiquetas.setText(jTable2.getValueAt(selectedRow, 7).toString());
                }
            }
        });

Editar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        EditarActionPerformed(e);
    }
});

Eliminar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        EliminarActionPerformed(e);
    }
});

Buscar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        BuscarActionPerformed(e);
    }
});
    }
    private void inicializarTabla() {
        modeloTabla = (DefaultTableModel) jTable2.getModel();
    }

    private void cargarProductos() {
        modeloTabla.setRowCount(0);
        List<String[]> productos = gestor.obtenerTodosLosProductos();
        for (String[] producto : productos) {
            modeloTabla.addRow(producto);
            System.out.println("Número de productos obtenidos: " + productos.size());

        }
    }
    
    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String nombre = txtNombre.getText();
            double precioUSD = Double.parseDouble(txtPrecioUSD.getText());
            double precioQuetzales = Double.parseDouble(txtPrecioQuetzales.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());
            String tipo = txtTipo.getText();
            String marca = txtMarca.getText();
            String etiquetas = txtEtiquetas.getText();

            gestor.agregarProducto(nombre, precioUSD, precioQuetzales, cantidad, tipo, marca, etiquetas);
            cargarProductos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto agregado con éxito.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void EditarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            double precioUSD = Double.parseDouble(txtPrecioUSD.getText());
            double precioQuetzales = Double.parseDouble(txtPrecioQuetzales.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());
            String tipo = txtTipo.getText();
            String marca = txtMarca.getText();
            String etiquetas = txtEtiquetas.getText();

            gestor.editarProducto(id, nombre, precioUSD, precioQuetzales, cantidad, tipo, marca, etiquetas);
            cargarProductos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto editado con éxito.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int id = Integer.parseInt(txtId.getText());
            gestor.eliminarProducto(id);
            cargarProductos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto eliminado con éxito.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int id = Integer.parseInt(txtId.getText());
            String[] producto = gestor.buscarProductoPorId(id);
            if (producto != null) {
                txtNombre.setText(producto[1]);
                txtPrecioUSD.setText(producto[2]);
                txtPrecioQuetzales.setText(producto[3]);
                txtCantidad.setText(producto[4]);
                txtTipo.setText(producto[5]);
                txtMarca.setText(producto[6]);
                txtEtiquetas.setText(producto[7]);
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtPrecioUSD.setText("");
        txtPrecioQuetzales.setText("");
        txtCantidad.setText("");
        txtTipo.setText("");
        txtMarca.setText("");
        txtEtiquetas.setText("");
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtId = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtPrecioUSD = new javax.swing.JTextField();
        txtPrecioQuetzales = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtTipo = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtEtiquetas = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Agregar = new javax.swing.JButton();
        Editar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Buscar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(464, 460));

        txtId.setText("ID");

        txtNombre.setText("Nombre");

        txtPrecioUSD.setText("Precio USD$");

        txtPrecioQuetzales.setText("Precio Quetzales");

        txtCantidad.setText("Cantidad");
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        txtTipo.setText("Tipo");
        txtTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoActionPerformed(evt);
            }
        });

        txtMarca.setText("Marca");
        txtMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarcaActionPerformed(evt);
            }
        });

        txtEtiquetas.setText("Etiquetas");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Productos");

        Agregar.setText("Agregar");

        Editar.setText("Editar");

        Eliminar.setText("Eliminar");

        Buscar.setText("Buscar");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio USD", "Precio Quetzales", "Cantidad", "Tipo", "Marca", "Etiquetas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Agregar)
                                .addGap(26, 26, 26)
                                .addComponent(Editar)
                                .addGap(31, 31, 31)
                                .addComponent(Eliminar)
                                .addGap(27, 27, 27)
                                .addComponent(Buscar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtEtiquetas, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrecioUSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(txtPrecioQuetzales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecioUSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecioQuetzales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEtiquetas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Agregar)
                    .addComponent(Editar)
                    .addComponent(Eliminar)
                    .addComponent(Buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(397, 397, 397))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoActionPerformed

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
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(GestionProductosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new GestionProductosGUI().setVisible(true);
        }
    });
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar;
    private javax.swing.JButton Buscar;
    private javax.swing.JButton Editar;
    private javax.swing.JButton Eliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtEtiquetas;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecioQuetzales;
    private javax.swing.JTextField txtPrecioUSD;
    private javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables
}
