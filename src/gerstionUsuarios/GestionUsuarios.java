/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gerstionUsuarios;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import paqueteInicioSesion.AdministradorUsuario;
import GUI.GuiPrincipal;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author mynit
 */
public class GestionUsuarios extends javax.swing.JFrame {

    /**
     * Creates new form GestionUsuarios
     */
    
    private String currentUser;
    private AdministradorUsuario adminUsuario;
    private DefaultTableModel tableModel;

    
     public GestionUsuarios() {
        this("admin"); // Asigna un valor por defecto
    }
    public GestionUsuarios(String username) {
        this.currentUser = username;
        this.adminUsuario = new AdministradorUsuario();
        initComponents();
        setupTable();
        loadUsers();
        if (!"admin".equals(currentUser)) {
            JOptionPane.showMessageDialog(this, "Acceso no autorizado", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
    }
    
   private void setupTable() {
    String[] columnNames = {"Usuario", "Email", "Contraseña"}; // Quitamos "Acciones"
    tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Ninguna columna es editable
        }
        
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }
    };
    jTable1.setModel(tableModel);
    
    // Configurar el renderer personalizado para la columna de contraseña
    jTable1.getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel("********");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
                label.setForeground(table.getSelectionForeground());
            } else {
                label.setBackground(table.getBackground());
                label.setForeground(table.getForeground());
            }
            label.setOpaque(true);
            return label;
        }
    });
}

    private void loadUsers() {
    tableModel.setRowCount(0);
    List<String[]> usuarios = adminUsuario.listarUsuarios();
    
    for (String[] usuario : usuarios) {
        // No mostrar el usuario admin en la lista
        if (!"admin".equals(usuario[0])) {
            tableModel.addRow(new Object[]{
                usuario[0], 
                usuario[1], 
                "********" // Contraseña oculta
            });
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnVerProductos = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(178, 171, 171));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/blackboxsecurity (2)_1.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 0));
        jLabel2.setText("GESTIÓN DE USUARIOS");

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

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnVerProductos.setText("Ver Productos");
        btnVerProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductosActionPerformed(evt);
            }
        });

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnEditar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnVerProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnRegresar)
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnVerProductos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addComponent(btnRegresar)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
    if (selectedRow >= 0) {
        String username = (String) jTable1.getValueAt(selectedRow, 0);
        String email = (String) jTable1.getValueAt(selectedRow, 1);
        
        // Verificar si es el usuario admin
        if ("admin".equals(username)) {
            JOptionPane.showMessageDialog(this,
                "No se puede modificar el usuario administrador",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Mostrar diálogo de edición
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JTextField usernameField = new JTextField(username);
        JTextField emailField = new JTextField(email);
        JPasswordField passwordField = new JPasswordField();
        JCheckBox changePasswordCheckbox = new JCheckBox("Cambiar contraseña");
        
        panel.add(new JLabel("Usuario:"));
        panel.add(usernameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(changePasswordCheckbox);
        panel.add(new JLabel("Nueva contraseña:"));
        panel.add(passwordField);
        
        // Inicialmente deshabilitar el campo de contraseña
        passwordField.setEnabled(false);
        
        // Habilitar/deshabilitar campo de contraseña según el checkbox
        changePasswordCheckbox.addActionListener(e -> 
            passwordField.setEnabled(changePasswordCheckbox.isSelected()));
        
        int result = JOptionPane.showConfirmDialog(null, panel, 
            "Editar Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String nuevoUsuario = usernameField.getText().trim();
            String nuevoEmail = emailField.getText().trim();
            
            // Validar campos vacíos
            if (nuevoUsuario.isEmpty() || nuevoEmail.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar formato de email
            if (!nuevoEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this,
                    "Por favor ingrese un email válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                if (adminUsuario.actualizarUsuario(username, nuevoUsuario, nuevoEmail)) {
                    // Si se seleccionó cambiar contraseña
                    if (changePasswordCheckbox.isSelected()) {
                        String nuevaContrasena = new String(passwordField.getPassword());
                        if (!nuevaContrasena.isEmpty()) {
                            if (adminUsuario.cambiarContrasena(nuevoEmail, nuevaContrasena)) {
                                JOptionPane.showMessageDialog(this,
                                    "Usuario y contraseña actualizados exitosamente",
                                    "Éxito",
                                    JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(this,
                                    "Usuario actualizado pero hubo un error al cambiar la contraseña",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Usuario actualizado exitosamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "No se pudo actualizar el usuario",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al actualizar usuario: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    } else {
        JOptionPane.showMessageDialog(this,
            "Por favor seleccione un usuario para editar",
            "Aviso",
            JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
    if (selectedRow >= 0) {
        String username = (String) jTable1.getValueAt(selectedRow, 0);
        
        // Verificar si es el usuario admin
        if ("admin".equals(username)) {
            JOptionPane.showMessageDialog(this,
                "No se puede eliminar el usuario administrador",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirmar eliminación
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que quieres eliminar el usuario " + username + "?\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (adminUsuario.eliminarUsuario(username)) {
                    JOptionPane.showMessageDialog(this,
                        "Usuario eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadUsers(); // Recargar la tabla
                } else {
                    JOptionPane.showMessageDialog(this,
                        "No se pudo eliminar el usuario",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar usuario: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    } else {
        JOptionPane.showMessageDialog(this,
            "Por favor seleccione un usuario para eliminar",
            "Aviso",
            JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVerProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerProductosActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
         SwingUtilities.invokeLater(() -> {
        new GuiPrincipal(currentUser).setVisible(true);
    });
    this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

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
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnVerProductos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
