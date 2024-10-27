/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perfilUsuario;

import GUI.GuiPrincipal;
import paqueteInicioSesion.AdministradorUsuario;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import paqueteCosteoRapido.CosteoForm_Ingresar;
import productosFavoritos.FavoritosManager;
import productosFavoritos.ProductoFavorito;

/**
 *
 * @author mynit
 */
public class PerfilUsuario extends javax.swing.JFrame {

    
    private String currentUser;
    private AdministradorUsuario adminUsuario;
    private String currentEmail;
    /**
     * Creates new form PerfilUsuario
     */
    
    public PerfilUsuario(String username) {
         this.currentUser = username;
        this.adminUsuario = new AdministradorUsuario();
        initComponents();
        cargarDatosUsuario();
        configurarBotonesExistentes();
    }
    
    
    
    private void cargarDatosUsuario() {
        // Obtener datos del usuario actual
        List<String[]> usuarios = adminUsuario.listarUsuarios();
        for (String[] usuario : usuarios) {
            if (usuario[0].equals(currentUser)) {
                txtUsuario.setText(usuario[0]);
                txtCorreo.setText(usuario[1]);
                currentEmail = usuario[1];
                break;
            }
        }
    }

    private void configurarBotonesExistentes() {
        // Modificar los event handlers existentes para los botones
        btnEditarUsuario.addActionListener(evt -> editarUsuario());
        btnEditarCorreo.addActionListener(evt -> editarCorreo());
        btnEditarContrasena.addActionListener(evt -> cambiarContrasena());
    }

    private void editarUsuario() {
        if ("admin".equals(currentUser)) {
            JOptionPane.showMessageDialog(this,
                "No se puede modificar el usuario administrador",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuevoUsuario = JOptionPane.showInputDialog(this, 
            "Ingrese nuevo nombre de usuario:", 
            txtUsuario.getText());
            
        if (nuevoUsuario != null && !nuevoUsuario.isEmpty()) {
            if (adminUsuario.nombreUsuarioExiste(nuevoUsuario) && !nuevoUsuario.equals(currentUser)) {
                JOptionPane.showMessageDialog(this,
                    "Este nombre de usuario ya está en uso",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (adminUsuario.actualizarUsuario(currentUser, nuevoUsuario, currentEmail)) {
                currentUser = nuevoUsuario;
                txtUsuario.setText(nuevoUsuario);
                JOptionPane.showMessageDialog(this,
                    "Nombre de usuario actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al actualizar el nombre de usuario",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarCorreo() {
        if ("admin".equals(currentUser)) {
            JOptionPane.showMessageDialog(this,
                "No se puede modificar el usuario administrador",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuevoEmail = JOptionPane.showInputDialog(this, 
            "Ingrese nuevo correo electrónico:", 
            txtCorreo.getText());
            
        if (nuevoEmail != null && !nuevoEmail.isEmpty()) {
            // Validar formato de email
            if (!nuevoEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this,
                    "Por favor ingrese un email válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (adminUsuario.emailExiste(nuevoEmail) && !nuevoEmail.equals(currentEmail)) {
                JOptionPane.showMessageDialog(this,
                    "Este correo ya está en uso",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (adminUsuario.actualizarUsuario(currentUser, currentUser, nuevoEmail)) {
                currentEmail = nuevoEmail;
                txtCorreo.setText(nuevoEmail);
                JOptionPane.showMessageDialog(this,
                    "Correo electrónico actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al actualizar el correo electrónico",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cambiarContrasena() {
        if ("admin".equals(currentUser)) {
            JOptionPane.showMessageDialog(this,
                "No se puede modificar el usuario administrador",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        JPasswordField actualPass = new JPasswordField();
        JPasswordField nuevaPass = new JPasswordField();
        JPasswordField confirmPass = new JPasswordField();
        
        panel.add(new JLabel("Contraseña actual:"));
        panel.add(actualPass);
        panel.add(new JLabel("Nueva contraseña:"));
        panel.add(nuevaPass);
        panel.add(new JLabel("Confirmar nueva contraseña:"));
        panel.add(confirmPass);

        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Cambiar Contraseña", JOptionPane.OK_CANCEL_OPTION);
            
        if (result == JOptionPane.OK_OPTION) {
            String actualPassword = new String(actualPass.getPassword());
            String nuevaPassword = new String(nuevaPass.getPassword());
            String confirmPassword = new String(confirmPass.getPassword());

            // Verificar contraseña actual
            if (!adminUsuario.iniciarSesion(currentUser, actualPassword)) {
                JOptionPane.showMessageDialog(this,
                    "La contraseña actual es incorrecta",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar que las contraseñas nuevas coincidan
            if (!nuevaPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this,
                    "Las contraseñas nuevas no coinciden",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar requisitos de la nueva contraseña
            String mensajeValidacion = adminUsuario.verificarContrasena(nuevaPassword);
            if (mensajeValidacion != null) {
                JOptionPane.showMessageDialog(this,
                    mensajeValidacion,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cambiar la contraseña
            if (adminUsuario.cambiarContrasena(currentEmail, nuevaPassword)) {
                JOptionPane.showMessageDialog(this,
                    "Contraseña actualizada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al actualizar la contraseña",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
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
        jPanel2 = new javax.swing.JPanel();
        flechaIzquierda = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        recargar = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        favoritos = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        flechaDerecha = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        btnEditarCorreo = new javax.swing.JButton();
        txtCorreo = new javax.swing.JTextField();
        btnEditarUsuario = new javax.swing.JButton();
        btnEditarContrasena = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(178, 171, 171));

        jPanel2.setBackground(new java.awt.Color(178, 171, 171));

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
        flechaDerecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flechaDerechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
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
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jLabel1.setText("Perfil Usuario");

        jLabel2.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        jLabel2.setText("CORREO:");

        jLabel3.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        jLabel3.setText("USUARIO:");

        txtUsuario.setEditable(false);
        txtUsuario.setBackground(new java.awt.Color(204, 204, 204));
        txtUsuario.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        txtUsuario.setText("Nombre Usuario");
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });

        btnEditarCorreo.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnEditarCorreo.setForeground(new java.awt.Color(102, 102, 0));
        btnEditarCorreo.setText("EDITAR");
        btnEditarCorreo.setToolTipText("");
        btnEditarCorreo.setBorderPainted(false);
        btnEditarCorreo.setContentAreaFilled(false);

        txtCorreo.setEditable(false);
        txtCorreo.setBackground(new java.awt.Color(204, 204, 204));
        txtCorreo.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        txtCorreo.setText("Nombre Usuario");
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });

        btnEditarUsuario.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnEditarUsuario.setForeground(new java.awt.Color(102, 102, 0));
        btnEditarUsuario.setText("EDITAR");
        btnEditarUsuario.setToolTipText("");
        btnEditarUsuario.setBorderPainted(false);
        btnEditarUsuario.setContentAreaFilled(false);
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });

        btnEditarContrasena.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        btnEditarContrasena.setForeground(new java.awt.Color(102, 102, 0));
        btnEditarContrasena.setText("CAMBIAR CONTRASEÑA");
        btnEditarContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarContrasenaActionPerformed(evt);
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(txtUsuario)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                            .addComponent(btnEditarContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(btnEditarCorreo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(82, 82, 82)
                    .addComponent(jLabel3)
                    .addContainerGap(263, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(180, Short.MAX_VALUE)
                    .addComponent(btnEditarUsuario)
                    .addGap(172, 172, 172)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(89, 89, 89)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarCorreo)
                .addGap(40, 40, 40)
                .addComponent(btnEditarContrasena)
                .addContainerGap(123, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(173, 173, 173)
                    .addComponent(jLabel3)
                    .addContainerGap(401, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(263, 263, 263)
                    .addComponent(btnEditarUsuario)
                    .addContainerGap(309, Short.MAX_VALUE)))
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

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        // TODO add your handling code here:
     JPopupMenu popupMenu = new JPopupMenu();
    popupMenu.setBackground(Color.WHITE);
    popupMenu.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

    // Crear los items del menú
    JMenuItem inicioItem = new JMenuItem("\uD83C\uDFE0 Inicio");
    JMenuItem perfilItem = new JMenuItem("\uD83D\uDC64 Perfil");
    JMenuItem costeoItem = new JMenuItem("\uD83D\uDCB0 Costeo Rápido");
    JMenuItem productosItem = new JMenuItem("\uD83D\uDCE6 Productos");
    JMenuItem preguntasItem = new JMenuItem("❓ Preguntas Frecuentes");
    JMenuItem favoritosItem = new JMenuItem("❤ Favoritos");
    JMenuItem historialItem = new JMenuItem("\uD83D\uDCC3 Historial");
    JMenuItem logoutItem = new JMenuItem("\uD83D\uDEAA Cerrar Sesión");

    // Personalizar apariencia de los items
    Font menuFont = new Font("Arial", Font.PLAIN, 14);
    Color hoverColor = new Color(240, 240, 240);
    
    for (JMenuItem item : new JMenuItem[]{inicioItem, perfilItem, costeoItem, 
        productosItem, preguntasItem, favoritosItem, historialItem, logoutItem}) {
        item.setFont(menuFont);
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        item.setOpaque(true);
        
        // Efecto hover
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                item.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                item.setBackground(Color.WHITE);
            }
        });
    }

    // Agregar acciones a los items
    inicioItem.addActionListener(e -> {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new GUI.GuiPrincipal(currentUser).setVisible(true);
        });
    });

    perfilItem.addActionListener(e -> {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new perfilUsuario.PerfilUsuario(currentUser).setVisible(true);
        });
    });

    costeoItem.addActionListener(e -> {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new paqueteCosteoRapido.CosteoForm_Ingresar(currentUser).setVisible(true);
        });
    });

    productosItem.addActionListener(e -> {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new gestionProductos.Gui(currentUser).setVisible(true);
        });
    });

    preguntasItem.addActionListener(e -> {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new preguntasFrecuentes.PreguntasFrecuentesForm(currentUser).setVisible(true);
        });
    });

    favoritosItem.addActionListener(e -> {
        // TODO: Implementar vista de favoritos
        JOptionPane.showMessageDialog(this, 
            "Función de favoritos en desarrollo", 
            "Próximamente", 
            JOptionPane.INFORMATION_MESSAGE);
    });

    historialItem.addActionListener(e -> {
        // TODO: Implementar vista de historial
        JOptionPane.showMessageDialog(this, 
            "Función de historial en desarrollo", 
            "Próximamente", 
            JOptionPane.INFORMATION_MESSAGE);
    });

    logoutItem.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que quieres cerrar sesión?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                new paqueteInicioSesion.LoginRegistroForm().setVisible(true);
            });
        }
    });

    // Agregar items al menú
    popupMenu.add(inicioItem);
    popupMenu.addSeparator();
    popupMenu.add(perfilItem);
    popupMenu.add(costeoItem);
    popupMenu.add(productosItem);
    popupMenu.add(preguntasItem);
    
    // Agregar gestión de usuarios solo para admin
    if ("admin".equals(currentUser)) {
        JMenuItem adminItem = new JMenuItem("\uD83D\uDC65 Gestión de Usuarios");
        adminItem.setFont(menuFont);
        adminItem.setBackground(Color.WHITE);
        adminItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        adminItem.setOpaque(true);
        adminItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                adminItem.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                adminItem.setBackground(Color.WHITE);
            }
        });
        adminItem.addActionListener(e -> {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                new gestionUsuarios.GestionUsuarios(currentUser).setVisible(true);
            });
        });
        popupMenu.add(adminItem);
    }
    
    popupMenu.addSeparator();
    popupMenu.add(favoritosItem);
    popupMenu.add(historialItem);
    popupMenu.addSeparator();
    popupMenu.add(logoutItem);

    // Mostrar el menú usando menuButton1
    popupMenu.show(menuButton, 0, menuButton.getHeight());
    }//GEN-LAST:event_menuButtonActionPerformed

    private void favoritosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoritosActionPerformed
        // TODO add your handling code here:
        try {
        // Usamos el FavoritosManager que ya está implementado
        FavoritosManager favoritosManager = new FavoritosManager();
        List<ProductoFavorito> favoritos = favoritosManager.obtenerFavoritosUsuario(currentUser);
        
        if (favoritos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No tienes productos favoritos guardados",
                "Sin Favoritos",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear panel principal con layout vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Agregar cada producto favorito al panel
        for (ProductoFavorito favorito : favoritos) {
            JPanel productoPanel = crearPanelProductoFavorito(favorito);
            mainPanel.add(productoPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre productos
        }

        // Agregar scroll al panel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        // Mostrar el diálogo con los favoritos
        JOptionPane.showMessageDialog(this, 
            scrollPane, 
            "Mis Productos Favoritos", 
            JOptionPane.PLAIN_MESSAGE);
            
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error al cargar favoritos: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_favoritosActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
        // TODO add your handling code here:
        editarUsuario();
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void btnEditarContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarContrasenaActionPerformed
        // TODO add your handling code here:
        cambiarContrasena();
    }//GEN-LAST:event_btnEditarContrasenaActionPerformed

    private void flechaIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flechaIzquierdaActionPerformed
        // TODO add your handling code here:
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new GuiPrincipal(currentUser).setVisible(true);
        });
    }//GEN-LAST:event_flechaIzquierdaActionPerformed

    private void flechaDerechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flechaDerechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_flechaDerechaActionPerformed

    
    
    
    // Método auxiliar para crear el panel de cada producto favorito
private JPanel crearPanelProductoFavorito(ProductoFavorito favorito) {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    
    // Panel de información
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    
    // Nombre del producto en negrita
    JLabel nombreLabel = new JLabel(favorito.getNombre());
    nombreLabel.setFont(new Font(nombreLabel.getFont().getName(), Font.BOLD, 14));
    infoPanel.add(nombreLabel);
    
    // Detalles del producto
    String detalles = String.format("<html>Costo FOB: $%.2f<br>" +
                                  "Costo Final USD: $%.2f<br>" +
                                  "Costo en Q.: Q%.2f<br>" +
                                  "Precio Venta: Q%.2f<br>" +
                                  "Margen: %.1f%%</html>",
        favorito.getCostoFobUSD(),
        favorito.getCostoUSDFinal(),
        favorito.getCostoQuetzales(),
        favorito.getPrecioVenta(),
        favorito.getMargen() * 100);
    
    JLabel detallesLabel = new JLabel(detalles);
    detallesLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
    infoPanel.add(detallesLabel);
    
    panel.add(infoPanel, BorderLayout.CENTER);
    
    // Panel de botones
    JPanel botonesPanel = new JPanel();
    botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
    
    // Botón de costear
    JButton costearButton = new JButton("Costear");
    costearButton.addActionListener(e -> costearProductoFavorito(favorito));
    botonesPanel.add(costearButton);
    botonesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    
    // Botón de eliminar
    JButton eliminarButton = new JButton("Eliminar");
    eliminarButton.addActionListener(e -> eliminarProductoFavorito(favorito));
    botonesPanel.add(eliminarButton);
    
    panel.add(botonesPanel, BorderLayout.EAST);
    
    return panel;
}

// Método para costear un producto favorito
private void costearProductoFavorito(ProductoFavorito favorito) {
    SwingUtilities.invokeLater(() -> {
        CosteoForm_Ingresar costeoForm = new CosteoForm_Ingresar(
            currentUser,
            favorito.getNombre(),
            favorito.getCostoFobUSD(),
            0.0, // Flete por defecto
            favorito.getMargen() * 100 // Convertir margen a porcentaje
        );
        costeoForm.setVisible(true);
        this.dispose();
    });
}

// Método para eliminar un producto favorito
private void eliminarProductoFavorito(ProductoFavorito favorito) {
    int confirmacion = JOptionPane.showConfirmDialog(this,
        "¿Estás seguro de que deseas eliminar '" + favorito.getNombre() + "' de tus favoritos?",
        "Confirmar Eliminación",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
        
    if (confirmacion == JOptionPane.YES_OPTION) {
        try {
            FavoritosManager favoritosManager = new FavoritosManager();
            favoritosManager.eliminarFavorito(favorito, currentUser);
            
            JOptionPane.showMessageDialog(this,
                "Producto eliminado de favoritos exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
                
            // Actualizar la vista de favoritos
            favoritosActionPerformed(null);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al eliminar el favorito: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
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
            java.util.logging.Logger.getLogger(PerfilUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PerfilUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PerfilUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PerfilUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PerfilUsuario("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditarContrasena;
    private javax.swing.JButton btnEditarCorreo;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JButton favoritos;
    private javax.swing.JButton flechaDerecha;
    private javax.swing.JButton flechaIzquierda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton menuButton;
    private javax.swing.JButton recargar;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
