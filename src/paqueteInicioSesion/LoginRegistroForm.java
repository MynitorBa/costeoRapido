/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paqueteInicioSesion;

/**
 *
 * @author mynit
 * 
 * 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.UUID;
import java.text.DecimalFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class LoginRegistroForm extends javax.swing.JFrame {
    
    
    private AdministradorUsuario adminUsuario;

    public LoginRegistroForm() {
        initComponents();
        adminUsuario = new AdministradorUsuario();
    }
    
     

    private void limpiarCamposRegistro() {
        usuarioRegistroTextField1.setText("");
        emailRegisterTextField.setText("");
        contrasenaEmailPasswordField.setText("");
    }
    
    private boolean enviarCorreoCambioContrasena(String destinatario, String codigo) {
     Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    final String username = "stylematezelda@gmail.com";
    final String password = "ucom vaej vocj tdvc";

    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject("Código para cambio de contraseña");
        
        String contenido = "Tu código para cambiar la contraseña es: " + codigo + "\n\n" +
                           "Ingresa este código de 8 dígitos en la aplicación para establecer tu nueva contraseña.";

        message.setText(contenido);

        Transport.send(message);

        return true;
    } catch (MessagingException e) {
        e.printStackTrace();
        return false;
    }
}
    


    /**
     * Creates new form LoginRegistroForm
     */

    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        inicioSesionPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        usuarioLoginTextField1 = new javax.swing.JTextField();
        iniciarSesionButton = new java.awt.Button();
        olvidasteContraseñaButton = new javax.swing.JButton();
        contrasenaLoginPasswordField = new javax.swing.JPasswordField();
        registroPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        usuarioRegistroTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        emailRegisterTextField = new javax.swing.JTextField();
        registrarUsuarioButton = new java.awt.Button();
        contrasenaEmailPasswordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setBackground(new java.awt.Color(210, 204, 204));

        inicioSesionPanel.setBackground(new java.awt.Color(178, 171, 171));
        inicioSesionPanel.setForeground(new java.awt.Color(172, 170, 170));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logoBoxSecurity (1).png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(45, 161, 45));
        jLabel1.setText("INICIO DE SESIÓN");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("CONTRASEÑA:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("USUARIO:");

        usuarioLoginTextField1.setBackground(new java.awt.Color(69, 67, 67));
        usuarioLoginTextField1.setForeground(new java.awt.Color(255, 255, 255));

        iniciarSesionButton.setBackground(new java.awt.Color(255, 255, 255));
        iniciarSesionButton.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        iniciarSesionButton.setForeground(new java.awt.Color(39, 130, 39));
        iniciarSesionButton.setLabel("INICIAR SESIÓN");
        iniciarSesionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarSesionButtonActionPerformed(evt);
            }
        });

        olvidasteContraseñaButton.setForeground(new java.awt.Color(45, 161, 45));
        olvidasteContraseñaButton.setText("¿Olvidaste tu contraseña?");
        olvidasteContraseñaButton.setBorderPainted(false);
        olvidasteContraseñaButton.setContentAreaFilled(false);
        olvidasteContraseñaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        olvidasteContraseñaButton.setDefaultCapable(false);
        olvidasteContraseñaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                olvidasteContraseñaButtonActionPerformed(evt);
            }
        });

        contrasenaLoginPasswordField.setBackground(new java.awt.Color(69, 67, 67));
        contrasenaLoginPasswordField.setForeground(new java.awt.Color(255, 255, 255));
        contrasenaLoginPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasenaLoginPasswordFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inicioSesionPanelLayout = new javax.swing.GroupLayout(inicioSesionPanel);
        inicioSesionPanel.setLayout(inicioSesionPanelLayout);
        inicioSesionPanelLayout.setHorizontalGroup(
            inicioSesionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inicioSesionPanelLayout.createSequentialGroup()
                .addGroup(inicioSesionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inicioSesionPanelLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jLabel2))
                    .addGroup(inicioSesionPanelLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(iniciarSesionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(inicioSesionPanelLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(inicioSesionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inicioSesionPanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(olvidasteContraseñaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(contrasenaLoginPasswordField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usuarioLoginTextField1, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        inicioSesionPanelLayout.setVerticalGroup(
            inicioSesionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inicioSesionPanelLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(usuarioLoginTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contrasenaLoginPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(olvidasteContraseñaButton)
                .addGap(34, 34, 34)
                .addComponent(iniciarSesionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inicio de Sesión", inicioSesionPanel);

        registroPanel.setBackground(new java.awt.Color(178, 171, 171));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logoBoxSecurity (1).png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(45, 161, 45));
        jLabel6.setText("NUEVA");

        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(45, 161, 45));
        jLabel7.setText("CREA UNA CUENTA");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("EMAIL:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("NOMBRE DE USUARIO:");

        usuarioRegistroTextField1.setBackground(new java.awt.Color(69, 67, 67));
        usuarioRegistroTextField1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("CONTRASEÑA:");

        emailRegisterTextField.setBackground(new java.awt.Color(69, 67, 67));
        emailRegisterTextField.setForeground(new java.awt.Color(255, 255, 255));

        registrarUsuarioButton.setBackground(new java.awt.Color(255, 255, 255));
        registrarUsuarioButton.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        registrarUsuarioButton.setForeground(new java.awt.Color(39, 130, 39));
        registrarUsuarioButton.setLabel("REGISTRAR USUARIO");
        registrarUsuarioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarUsuarioButtonActionPerformed(evt);
            }
        });

        contrasenaEmailPasswordField.setBackground(new java.awt.Color(69, 67, 67));
        contrasenaEmailPasswordField.setForeground(new java.awt.Color(255, 255, 255));
        contrasenaEmailPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasenaEmailPasswordFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout registroPanelLayout = new javax.swing.GroupLayout(registroPanel);
        registroPanel.setLayout(registroPanelLayout);
        registroPanelLayout.setHorizontalGroup(
            registroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registroPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(registroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(registroPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(registroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(contrasenaEmailPasswordField)
                            .addComponent(usuarioRegistroTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(emailRegisterTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(registrarUsuarioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 53, Short.MAX_VALUE))
            .addGroup(registroPanelLayout.createSequentialGroup()
                .addGroup(registroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registroPanelLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addGroup(registroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(registroPanelLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel6))))
                    .addGroup(registroPanelLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        registroPanelLayout.setVerticalGroup(
            registroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registroPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usuarioRegistroTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailRegisterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contrasenaEmailPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(registrarUsuarioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Registro", registroPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registrarUsuarioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarUsuarioButtonActionPerformed
        // TODO add your handling code here:
        
        String nombre = usuarioRegistroTextField1.getText().trim();
    String email = emailRegisterTextField.getText().trim();
    String contrasena = contrasenaEmailPasswordField.getText().trim();
    
    if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    try {
        if (adminUsuario.registrarNuevoUsuario(nombre, email, contrasena)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposRegistro();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario. El email ya puede estar en uso.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
        
        
    }//GEN-LAST:event_registrarUsuarioButtonActionPerformed

    private void iniciarSesionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarSesionButtonActionPerformed
        // TODO add your handling code here:
    String nombreUsuario = usuarioLoginTextField1.getText().trim();
    String contrasena = contrasenaLoginPasswordField.getText().trim();
    
    
    
    if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    

    
    try {
        if (adminUsuario.iniciarSesion(nombreUsuario, contrasena)) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Abrir la ventana principal de la aplicación
            java.awt.EventQueue.invokeLater(() -> {
                new GUI.GuiPrincipal().setVisible(true);
            });
            
            this.dispose(); // Cerrar la ventana de login
        } else {
            JOptionPane.showMessageDialog(this, "Nombre de usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al iniciar sesión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_iniciarSesionButtonActionPerformed

    private void olvidasteContraseñaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_olvidasteContraseñaButtonActionPerformed
        // TODO add your handling code here:
        String email = JOptionPane.showInputDialog(this, "Ingresa tu email para cambiar la contraseña:");
    if (email != null && !email.isEmpty()) {
        if (adminUsuario.emailExiste(email)) {
            String codigo = adminUsuario.generarCodigoRecuperacion(email);
            
            // Enviar correo electrónico con el código
            if (enviarCorreoCambioContrasena(email, codigo)) {
                JOptionPane.showMessageDialog(this, "Se ha enviado un correo con un código de 8 dígitos para cambiar tu contraseña.", "Correo enviado", JOptionPane.INFORMATION_MESSAGE);
                
                // Crear un panel personalizado para el diálogo
                JPanel panel = new JPanel(new GridLayout(0, 1));
                JTextField codigoField = new JTextField(10);
                JPasswordField newPasswordField = new JPasswordField(10);
                JPasswordField confirmPasswordField = new JPasswordField(10);
                
                panel.add(new JLabel("Ingresa el código de 8 dígitos recibido por correo:"));
                panel.add(codigoField);
                panel.add(new JLabel("Nueva contraseña:"));
                panel.add(newPasswordField);
                panel.add(new JLabel("Confirma la nueva contraseña:"));
                panel.add(confirmPasswordField);

                int result = JOptionPane.showConfirmDialog(null, panel, 
                    "Cambio de contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
                if (result == JOptionPane.OK_OPTION) {
                    String inputCodigo = codigoField.getText();
                    String newPassword = new String(newPasswordField.getPassword());
                    String confirmPassword = new String(confirmPasswordField.getPassword());
                    
                    if (!newPassword.equals(confirmPassword)) {
                        JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (adminUsuario.validarCodigoRecuperacion(inputCodigo, email)) {
                        if (adminUsuario.cambiarContrasena(email, newPassword)) {
                            JOptionPane.showMessageDialog(this, "Tu contraseña ha sido cambiada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Hubo un problema al cambiar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "El código ingresado es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un problema al enviar el correo. Por favor, intenta nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró una cuenta asociada a ese email.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_olvidasteContraseñaButtonActionPerformed

    private void contrasenaLoginPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasenaLoginPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contrasenaLoginPasswordFieldActionPerformed

    private void contrasenaEmailPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasenaEmailPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contrasenaEmailPasswordFieldActionPerformed

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
            java.util.logging.Logger.getLogger(LoginRegistroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginRegistroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginRegistroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginRegistroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginRegistroForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField contrasenaEmailPasswordField;
    private javax.swing.JPasswordField contrasenaLoginPasswordField;
    private javax.swing.JTextField emailRegisterTextField;
    private java.awt.Button iniciarSesionButton;
    private javax.swing.JPanel inicioSesionPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton olvidasteContraseñaButton;
    private java.awt.Button registrarUsuarioButton;
    private javax.swing.JPanel registroPanel;
    private javax.swing.JTextField usuarioLoginTextField1;
    private javax.swing.JTextField usuarioRegistroTextField1;
    // End of variables declaration//GEN-END:variables
}
