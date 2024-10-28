/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paqueteCosteoRapido;
import GUI.GuiPrincipal;
import Historial.HistorialEntry;
import Historial.HistorialManager;
import Historial.HistorialViewer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import paqueteSolicitudDePedido.SolicitudDePedido_mandarCorreo;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import productosFavoritos.FavoritosManager;
import productosFavoritos.ProductoFavorito;
/**
 *
 * @author andre
 */
public class CosteoFinal extends javax.swing.JFrame {
    
    
    private String currentUser;
    private String[] producto;
    /**
     * Creates new form CosteoFinal
     */
    public CosteoFinal(String username) {
        this.currentUser = username;
        initComponents();
    }
    
    private double parseNumber(String text) throws ParseException {
    if (text == null || text.trim().isEmpty()) {
        throw new ParseException("Valor vacío no permitido", 0);
    }
    // Limpiar símbolos monetarios y convertir a número
    String cleanText = text.replace("$", "")
                          .replace("Q", "")
                          .replace("%", "")
                          .replace(",", "")
                          .trim();
    try {
        return Double.parseDouble(cleanText);
    } catch (NumberFormatException e) {
        throw new ParseException("Valor inválido: " + cleanText, 0);
    }
}
    
public void guardarProductoFavorito() {
    try {
        // Obtener el nombre del producto y validar que no esté vacío
        String nombreProducto = nombreDescripcionProducto.getText();
        if (nombreProducto == null || nombreProducto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El nombre del producto no puede estar vacío",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el producto favorito
        ProductoFavorito favorito = new ProductoFavorito();
        
        // Establecer los valores asegurándonos de que el nombre se establece correctamente
        favorito.setUsuario(currentUser);
        favorito.setNombre(nombreProducto.trim()); // Aseguramos que el nombre se establece
        
        // Remover símbolos y convertir a números
        favorito.setCostoFobUSD(parseNumber(costoFobUSD$_FINAL.getText()));
        favorito.setCostoUSDFinal(parseNumber(CostoUSD$_FINAL.getText()));
        favorito.setCostoQuetzales(parseNumber(CostoQuetzales_FINAL.getText()));
        
        // Convertir valores a USD dividiendo por 7.8
        double precioVentaUSD = parseNumber(PrecioVenta_FINAL.getText()) / 7.8;
        double precioConIVAUSD = parseNumber(jTextField6.getText()) / 7.8;
        
        favorito.setPrecioVenta(precioVentaUSD);
        favorito.setPrecioConIVA(precioConIVAUSD);
        
        // Convertir el margen a decimal
        double margenDecimal = parseNumber(margen_FINAL.getText()) / 100.0;
        favorito.setMargen(margenDecimal);
        
        // Verificar que el objeto se creó correctamente
        if (favorito.getNombre() == null || favorito.getNombre().isEmpty()) {
            throw new IllegalStateException("El nombre del producto no se estableció correctamente");
        }
        
        // Guardar el favorito
        FavoritosManager favoritosManager = new FavoritosManager();
        favoritosManager.guardarFavorito(favorito, currentUser);
        
        JOptionPane.showMessageDialog(this,
            String.format("Producto '%s' guardado en favoritos exitosamente", nombreProducto),
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);
            
    } catch (ParseException e) {
        JOptionPane.showMessageDialog(this,
            "Error al procesar los valores numéricos: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error al guardar el favorito: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
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
        nombreDescripcionProducto = new javax.swing.JTextField();
        costoFobUSD$_FINAL = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CostoUSD$_FINAL = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CostoQuetzales_FINAL = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        PrecioVenta_FINAL = new javax.swing.JTextField();
        ConIVA_FINAL = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        margen_FINAL = new javax.swing.JTextField();
        mandarSolicitudPedido = new javax.swing.JButton();
        ModificarCosteo = new javax.swing.JButton();
        GuardarCosteo = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        botonVerHistorial = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        flechaIzquierda = new javax.swing.JButton();
        flechaDerecha = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(178, 171, 171));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nombreDescripcionProducto.setEditable(false);
        nombreDescripcionProducto.setBackground(new java.awt.Color(178, 171, 171));
        nombreDescripcionProducto.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        nombreDescripcionProducto.setText("Nombre o descripción del producto");
        nombreDescripcionProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreDescripcionProductoActionPerformed(evt);
            }
        });
        jPanel1.add(nombreDescripcionProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 172, 288, -1));

        costoFobUSD$_FINAL.setEditable(false);
        costoFobUSD$_FINAL.setBackground(new java.awt.Color(178, 171, 171));
        costoFobUSD$_FINAL.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        costoFobUSD$_FINAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costoFobUSD$_FINALActionPerformed(evt);
            }
        });
        jPanel1.add(costoFobUSD$_FINAL, new org.netbeans.lib.awtextra.AbsoluteConstraints(222, 210, 141, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel3.setText("Costo Fob USD$");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 210, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel4.setText("Costo USD$");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 248, -1, -1));

        CostoUSD$_FINAL.setEditable(false);
        CostoUSD$_FINAL.setBackground(new java.awt.Color(178, 171, 171));
        CostoUSD$_FINAL.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        jPanel1.add(CostoUSD$_FINAL, new org.netbeans.lib.awtextra.AbsoluteConstraints(222, 248, 141, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel5.setText("Costo en Quetzales");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 286, -1, -1));

        CostoQuetzales_FINAL.setEditable(false);
        CostoQuetzales_FINAL.setBackground(new java.awt.Color(178, 171, 171));
        CostoQuetzales_FINAL.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jPanel1.add(CostoQuetzales_FINAL, new org.netbeans.lib.awtextra.AbsoluteConstraints(222, 288, 141, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel6.setText("Precio de venta");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 327, -1, -1));

        PrecioVenta_FINAL.setEditable(false);
        PrecioVenta_FINAL.setBackground(new java.awt.Color(178, 171, 171));
        PrecioVenta_FINAL.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        PrecioVenta_FINAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrecioVenta_FINALActionPerformed(evt);
            }
        });
        jPanel1.add(PrecioVenta_FINAL, new org.netbeans.lib.awtextra.AbsoluteConstraints(222, 329, 141, -1));

        ConIVA_FINAL.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        ConIVA_FINAL.setText("Con IVA");
        jPanel1.add(ConIVA_FINAL, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 365, -1, -1));

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(178, 171, 171));
        jTextField6.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(222, 365, 141, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel8.setText("Margen");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 406, 141, -1));

        margen_FINAL.setEditable(false);
        margen_FINAL.setBackground(new java.awt.Color(178, 171, 171));
        margen_FINAL.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        margen_FINAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                margen_FINALActionPerformed(evt);
            }
        });
        jPanel1.add(margen_FINAL, new org.netbeans.lib.awtextra.AbsoluteConstraints(222, 409, 141, -1));

        mandarSolicitudPedido.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        mandarSolicitudPedido.setText("Mandar solicitud de pedido");
        mandarSolicitudPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandarSolicitudPedidoActionPerformed(evt);
            }
        });
        jPanel1.add(mandarSolicitudPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 453, -1, -1));

        ModificarCosteo.setFont(new java.awt.Font("Segoe UI Variable", 0, 12)); // NOI18N
        ModificarCosteo.setText("Modificar");
        ModificarCosteo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarCosteoActionPerformed(evt);
            }
        });
        jPanel1.add(ModificarCosteo, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 491, 126, -1));

        GuardarCosteo.setFont(new java.awt.Font("Segoe UI Variable", 0, 12)); // NOI18N
        GuardarCosteo.setText("Guardar");
        GuardarCosteo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarCosteoActionPerformed(evt);
            }
        });
        jPanel1.add(GuardarCosteo, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 491, 130, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Variable", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 144, 47));
        jLabel7.setText("Costeo Final");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 98, -1, -1));

        jPanel2.setBackground(new java.awt.Color(178, 171, 171));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/blackboxsecurity (2)_1.png"))); // NOI18N

        botonVerHistorial.setText(" 📝");
        botonVerHistorial.setToolTipText("");
        botonVerHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVerHistorialActionPerformed(evt);
            }
        });

        menuButton.setText("☰");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        flechaIzquierda.setText("←");
        flechaIzquierda.setToolTipText("");
        flechaIzquierda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flechaIzquierdaActionPerformed(evt);
            }
        });

        flechaDerecha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        flechaDerecha.setText("→");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(flechaIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(flechaDerecha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonVerHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 31, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(botonVerHistorial)
                        .addComponent(menuButton))
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(flechaIzquierda)
                        .addComponent(flechaDerecha)))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 120));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GuardarCosteoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarCosteoActionPerformed
        // TODO add your handling code here:

        if (validarDatos()) {
            guardarCosteo();
        }
    }//GEN-LAST:event_GuardarCosteoActionPerformed

    private void ModificarCosteoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarCosteoActionPerformed
        // TODO add your handling code here:
        try {
            String nombre = nombreDescripcionProducto.getText();
            double costoFob = parseNumber(costoFobUSD$_FINAL.getText().replace("$", ""));
            double costoConDAI = parseNumber(CostoUSD$_FINAL.getText().replace("$", ""));
            double precioVenta = parseNumber(PrecioVenta_FINAL.getText().replace("Q", "")) / 7.8;
            double margenVenta = parseNumber(margen_FINAL.getText().replace("%", "")) / 100;

            // Calcular el flete
            double flete = (costoConDAI - costoFob) / costoFob;

            // Pasar el currentUser al crear la nueva instancia
            CosteoForm_Ingresar costeoForm = new CosteoForm_Ingresar(
                currentUser, nombre, costoFob, flete, margenVenta);
            costeoForm.setVisible(true);
            this.dispose();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                "Error al procesar los números: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ModificarCosteoActionPerformed

    private void mandarSolicitudPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandarSolicitudPedidoActionPerformed
        // TODO add your handling code here:
        try {
            SolicitudDePedido_mandarCorreo solicitud = new SolicitudDePedido_mandarCorreo(currentUser);
            solicitud.setDatos(
                nombreDescripcionProducto.getText(),
                parseNumber(costoFobUSD$_FINAL.getText().replace("$", "")),
                parseNumber(CostoUSD$_FINAL.getText().replace("$", "")),
                parseNumber(CostoQuetzales_FINAL.getText().replace("Q", "")),
                parseNumber(PrecioVenta_FINAL.getText().replace("Q", "")),
                parseNumber(jTextField6.getText().replace("Q", "")),
                parseNumber(margen_FINAL.getText().replace("%", "")) / 100
            );
            solicitud.setVisible(true);
            this.dispose();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Error al procesar los números: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_mandarSolicitudPedidoActionPerformed

    private void margen_FINALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_margen_FINALActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_margen_FINALActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void PrecioVenta_FINALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrecioVenta_FINALActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrecioVenta_FINALActionPerformed

    private void costoFobUSD$_FINALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costoFobUSD$_FINALActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_costoFobUSD$_FINALActionPerformed

    private void nombreDescripcionProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreDescripcionProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreDescripcionProductoActionPerformed

    private void flechaIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flechaIzquierdaActionPerformed
        // TODO add your handling code here:
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new CosteoForm_Ingresar(currentUser).setVisible(true);
        });
    }//GEN-LAST:event_flechaIzquierdaActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        // TODO add your handling code here:
       JPopupMenu popupMenu = new JPopupMenu();
       popupMenu.setBackground(Color.WHITE);
       popupMenu.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

       // Crear los items del menú usando códigos Unicode
       JMenuItem inicioItem = new JMenuItem("\uD83C\uDFE0 Inicio");
       JMenuItem perfilItem = new JMenuItem("\uD83D\uDC64 Perfil");
       JMenuItem costeoItem = new JMenuItem("\uD83D\uDCB0 Costeo Rápido");
       JMenuItem productosItem = new JMenuItem("\uD83D\uDCE6 Productos");
       JMenuItem preguntasItem = new JMenuItem("❓ Preguntas Frecuentes");
       JMenuItem historialItem = new JMenuItem("\uD83D\uDCCB Historial");
       JMenuItem logoutItem = new JMenuItem("\uD83D\uDEAA Cerrar Sesión");

       // Personalizar apariencia de los items usando una fuente que soporte emojis
       Font menuFont = new Font("Segoe UI Emoji", Font.PLAIN, 14);
       Color hoverColor = new Color(240, 240, 240);

       for (JMenuItem item : new JMenuItem[]{inicioItem, perfilItem, costeoItem,
           productosItem, preguntasItem, historialItem, logoutItem}) {
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

        historialItem.addActionListener(e -> {
            // TODO: Implementar vista de historial
            HistorialViewer viewer = new HistorialViewer(currentUser, this);
            viewer.setVisible(true);
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
           adminItem.setFont(menuFont); // Usar la misma fuente que soporta emojis
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
       popupMenu.add(historialItem);
       popupMenu.addSeparator();
       popupMenu.add(logoutItem);

       // Mostrar el menú
       popupMenu.show(menuButton, 0, menuButton.getHeight());
   
    }//GEN-LAST:event_menuButtonActionPerformed

    private void botonVerHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVerHistorialActionPerformed
        // TODO add your handling code here:
        HistorialViewer viewer = new HistorialViewer(currentUser, this);
        viewer.setVisible(true);
    }//GEN-LAST:event_botonVerHistorialActionPerformed

    

     public void setDatos(String nombre, double costoFob, double costoConFlete, double costoConDAI, double precioVenta, double precioConIVA, double margenVentaPercent) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        nombreDescripcionProducto.setText(nombre);
        costoFobUSD$_FINAL.setText("$" + df.format(costoFob));
        CostoUSD$_FINAL.setText("$" + df.format(costoConDAI));
        CostoQuetzales_FINAL.setText("Q" + df.format(costoConDAI * 7.8)); // Asumiendo un tipo de cambio de 1 USD = 7.8 GTQ
        PrecioVenta_FINAL.setText("Q" + df.format(precioVenta * 7.8));
        jTextField6.setText("Q" + df.format(precioConIVA * 7.8));
        margen_FINAL.setText(df.format(margenVentaPercent * 100) + "%");
    }
 
    
    
    
    private void guardarCosteo() {
        try {
        // Crear una nueva entrada de historial
        HistorialEntry entrada = new HistorialEntry(
            currentUser,
            nombreDescripcionProducto.getText(),
            parseNumber(costoFobUSD$_FINAL.getText().replace("$", "")),
            parseNumber(CostoUSD$_FINAL.getText().replace("$", "")),
            parseNumber(CostoQuetzales_FINAL.getText().replace("Q", "")),
            parseNumber(PrecioVenta_FINAL.getText().replace("Q", "")),
            parseNumber(jTextField6.getText().replace("Q", "")),
            parseNumber(margen_FINAL.getText().replace("%", "")) / 100
        );

        // Guardar en el historial
        HistorialManager manager = new HistorialManager();
        manager.guardarCosteo(entrada);

        JOptionPane.showMessageDialog(this, 
            "Costeo guardado exitosamente", 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
    } catch (ParseException e) {
        JOptionPane.showMessageDialog(this, 
            "Error al guardar el costeo: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
    public void cargarCosteoDesdeHistorial(String producto, String costoFobUSD, 
    String costoUSDFinal, String costoQuetzales, String precioVenta, 
    String precioConIVA, String margen) {
        
    nombreDescripcionProducto.setText(producto);
    costoFobUSD$_FINAL.setText("$" + costoFobUSD);
    CostoUSD$_FINAL.setText("$" + costoUSDFinal);
    CostoQuetzales_FINAL.setText("Q" + costoQuetzales);
    PrecioVenta_FINAL.setText("Q" + precioVenta);
    jTextField6.setText("Q" + precioConIVA);
    margen_FINAL.setText(margen + "%");
}
    
        private boolean validarDatos() {
        return true; 
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
            java.util.logging.Logger.getLogger(CosteoFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CosteoFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CosteoFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CosteoFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CosteoFinal("").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ConIVA_FINAL;
    private javax.swing.JTextField CostoQuetzales_FINAL;
    private javax.swing.JTextField CostoUSD$_FINAL;
    private javax.swing.JButton GuardarCosteo;
    private javax.swing.JButton ModificarCosteo;
    private javax.swing.JTextField PrecioVenta_FINAL;
    private javax.swing.JButton botonVerHistorial;
    private javax.swing.JTextField costoFobUSD$_FINAL;
    private javax.swing.JButton flechaDerecha;
    private javax.swing.JButton flechaIzquierda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JButton mandarSolicitudPedido;
    private javax.swing.JTextField margen_FINAL;
    private javax.swing.JButton menuButton;
    private javax.swing.JTextField nombreDescripcionProducto;
    // End of variables declaration//GEN-END:variables
}
