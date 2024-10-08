/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;


import BuscadorInteligente.BuscadorInteligente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import paqueteInicioSesion.LoginRegistroForm;
import paqueteCosteoRapido.CosteoForm_Ingresar;
import gestionProductos.Gui;
/**
 *
 * @author mynit
 */

public class GuiPrincipal extends javax.swing.JFrame {

    private BuscadorInteligente buscador;
    private JPopupMenu sugerenciasPopup;
    private JPopupMenu popupMenu;
    /**
     * Creates new form GuiPrincipal
     */
    public GuiPrincipal() {
        initComponents();
        customizeComponents();
        buscador = new BuscadorInteligente();
        configurarBuscadorInteligente();
    }
    
    private void configurarBuscadorInteligente() {
        sugerenciasPopup = new JPopupMenu();
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizarSugerencias(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizarSugerencias(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizarSugerencias(); }
        });

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    realizarBusqueda();
                }
            }
        });

        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realizarBusqueda();
            }
        });
    }

    private void actualizarSugerencias() {
        SwingUtilities.invokeLater(() -> {
            String texto = searchField.getText();
            List<String> sugerencias = buscador.obtenerSugerencias(texto);
            mostrarSugerencias(sugerencias);
        });
    }

    private void mostrarSugerencias(List<String> sugerencias) {
        sugerenciasPopup.removeAll();
        for (String sugerencia : sugerencias) {
            JMenuItem item = new JMenuItem(sugerencia);
            item.addActionListener(e -> {
                searchField.setText(sugerencia);
                realizarBusqueda();
            });
            sugerenciasPopup.add(item);
        }
        if (!sugerencias.isEmpty()) {
            sugerenciasPopup.show(searchField, 0, searchField.getHeight());
        } else {
            sugerenciasPopup.setVisible(false);
        }
    }

    private void realizarBusqueda() {
        String consulta = searchField.getText();
        List<String[]> resultados = buscador.procesarConsulta(consulta);
        resultados = buscador.filtrarResultados(resultados, ""); // Puedes agregar filtros adicionales aquí
        resultados = buscador.ordenarResultados(resultados, consulta);
        mostrarResultados(resultados);
    }

    private void mostrarResultados(List<String[]> resultados) {
    JPanel resultPanel = new JPanel();
    resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

    for (String[] producto : resultados) {
        JPanel productoPanel = new JPanel();
        productoPanel.setLayout(new BorderLayout());
        productoPanel.setBorder(BorderFactory.createEtchedBorder());

        JLabel nombreLabel = new JLabel(producto[1]);
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        productoPanel.add(nombreLabel, BorderLayout.NORTH);

        JLabel detallesLabel = new JLabel(String.format("Tipo: %s, Marca: %s, Precio: %s", producto[5], producto[6], producto[2]));
        productoPanel.add(detallesLabel, BorderLayout.CENTER);

        JButton verMasButton = new JButton("Ver más");
        verMasButton.addActionListener(e -> mostrarDetallesProducto(producto));
        productoPanel.add(verMasButton, BorderLayout.EAST);

        resultPanel.add(productoPanel);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    JScrollPane scrollPane = new JScrollPane(resultPanel);
    scrollPane.setPreferredSize(new Dimension(400, 300));

    JOptionPane.showMessageDialog(this, scrollPane, "Resultados de la búsqueda", JOptionPane.PLAIN_MESSAGE);
}
    private void mostrarDetallesProducto(String[] producto) {
    StringBuilder detalles = new StringBuilder();
    detalles.append("ID: ").append(producto[0]).append("\n");
    detalles.append("Nombre: ").append(producto[1]).append("\n");
    detalles.append("Precio USD: ").append(producto[2]).append("\n");
    detalles.append("Precio Quetzales: ").append(producto[3]).append("\n");
    detalles.append("Cantidad: ").append(producto[4]).append("\n");
    detalles.append("Tipo: ").append(producto[5]).append("\n");
    detalles.append("Marca: ").append(producto[6]).append("\n");
    detalles.append("Etiquetas: ").append(producto[7]);

    JTextArea textArea = new JTextArea(detalles.toString());
    textArea.setEditable(false);
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(300, 200));

    JOptionPane.showMessageDialog(this, scrollPane, "Detalles del Producto", JOptionPane.INFORMATION_MESSAGE);
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
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        bookmarkButton = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        costeoRapidoButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        addProductButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        addProductButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        addProductButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        addProductButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(178, 171, 171));

        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });

        searchButton.setText("jButton1");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        bookmarkButton.setText("jButton1");
        bookmarkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookmarkButtonActionPerformed(evt);
            }
        });

        menuButton.setText("jButton1");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        costeoRapidoButton.setText("COSTEO RÁPIDO");
        costeoRapidoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costeoRapidoButtonActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        addProductButton1.setText("jButton1");
        addProductButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(addProductButton1)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addProductButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        addProductButton2.setText("jButton1");
        addProductButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(addProductButton2)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addProductButton2)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        addProductButton3.setText("jButton1");
        addProductButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(addProductButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProductButton3)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        addProductButton4.setText("jButton1");
        addProductButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(addProductButton4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProductButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/blackboxsecurity (2)_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(20, 20, 20)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(64, 64, 64)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(costeoRapidoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(17, 17, 17)
                                    .addComponent(jLabel1)))
                            .addGap(22, 22, 22)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bookmarkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton)
                    .addComponent(bookmarkButton)
                    .addComponent(menuButton))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(costeoRapidoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

    private void costeoRapidoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costeoRapidoButtonActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(() -> {
        new paqueteCosteoRapido.CosteoForm_Ingresar().setVisible(true);

        });
        this.dispose();
        
    }//GEN-LAST:event_costeoRapidoButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        realizarBusqueda();

    }//GEN-LAST:event_searchButtonActionPerformed

    private void bookmarkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookmarkButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookmarkButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuButtonActionPerformed

    private void addProductButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProductButton2ActionPerformed

    private void addProductButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProductButton1ActionPerformed

    private void addProductButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProductButton3ActionPerformed

    private void addProductButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProductButton4ActionPerformed

    
    private void customizeComponents() {
        // Personalizar campo de búsqueda
        searchField.setPreferredSize(new Dimension(200, 30));

        // Personalizar botones
        searchButton.setText("🔍");
        bookmarkButton.setText("🔖");
        menuButton.setText("☰");

        // Personalizar botón de Costeo Rápido
        costeoRapidoButton.setFont(new Font("Arial", Font.BOLD, 18));
        costeoRapidoButton.setBackground(new Color(255, 255, 255));
        costeoRapidoButton.setForeground(new Color(0, 0, 0));
        costeoRapidoButton.setPreferredSize(new Dimension(300, 60));
        costeoRapidoButton.setMaximumSize(new Dimension(300, 60));
        costeoRapidoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear un panel para los productos con GridBagLayout
        JPanel productsPanel = new JPanel(new GridBagLayout());
        productsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Personalizar y agregar paneles de productos
        gbc.gridx = 0;
        gbc.gridy = 0;
        customizeProductPanel(jPanel2, jPanel6, addProductButton1, "Producto 1");
        productsPanel.add(jPanel2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        customizeProductPanel(jPanel3, jPanel7, addProductButton2, "Producto 2");
        productsPanel.add(jPanel3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        customizeProductPanel(jPanel4, jPanel8, addProductButton3, "Producto 3");
        productsPanel.add(jPanel4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        customizeProductPanel(jPanel5, jPanel9, addProductButton4, "Producto 4");
        productsPanel.add(jPanel5, gbc);

        // Actualizar el layout del panel principal
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
        jPanel1.removeAll();

        // Agregar componentes al panel principal
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(bookmarkButton);
        searchPanel.add(menuButton);

        jPanel1.add(Box.createVerticalStrut(20));
        jPanel1.add(searchPanel);
        jPanel1.add(Box.createVerticalStrut(20));
        jPanel1.add(jLabel1);
        jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel1.add(Box.createVerticalStrut(20));
        jPanel1.add(costeoRapidoButton);
        jPanel1.add(Box.createVerticalStrut(20));
        jPanel1.add(productsPanel);
        productsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel1.add(Box.createVerticalGlue());

        // Crear y personalizar el menú desplegable
        createPopupMenu();

        // Vincular el menú desplegable al botón de menú
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(menuButton, 0, menuButton.getHeight());
            }
        });
    }
    //Deberia estar arreglado

    
    
    
     private void customizeProductPanel(JPanel outerPanel, JPanel innerPanel, JButton addButton, String productName) {
         outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
        outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outerPanel.setOpaque(false);

        // Configurar el panel interno como una imagen de placeholder
        innerPanel.setPreferredSize(new Dimension(100, 100));
        innerPanel.setMaximumSize(new Dimension(100, 100));
        innerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        innerPanel.setOpaque(true);
        innerPanel.setBackground(Color.LIGHT_GRAY);

        JLabel nameLabel = new JLabel(productName);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        addButton.setText("Agregar");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        outerPanel.add(innerPanel);
        outerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        outerPanel.add(nameLabel);
        outerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        outerPanel.add(addButton);
    }
     
     private void createPopupMenu() {
        popupMenu = new JPopupMenu();
        popupMenu.setBackground(Color.WHITE);
        popupMenu.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        // Añadir campo de búsqueda al menú
        JTextField searchField = new JTextField(15);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            searchField.getBorder(), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        popupMenu.add(searchField);
        popupMenu.addSeparator();

        // Añadir elementos del menú
        addMenuItem("Perfil", "\uD83D\uDC64");
        addMenuItem("Costeo Rápido", "\uD83D\uDCB0", e -> abrirCosteoRapido());
        addMenuItem("Productos", "\uD83D\uDCE6", e -> abrirGestionProductos());
        addMenuItem("Favoritos", "\u2764");
        addMenuItem("Historial", "\uD83D\uDCC3");
        addMenuItem("Configuración", "\u2699");
        
        popupMenu.addSeparator();
        addMenuItem("Cerrar Sesión", "\uD83D\uDEAA", e -> logout());
    }

    private void addMenuItem(String text, String icon) {
        addMenuItem(text, icon, null);
    }

    private void addMenuItem(String text, String icon, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(icon + " " + text);
        menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        menuItem.setOpaque(true);
        menuItem.setBackground(Color.WHITE);
        if (listener != null) {
            menuItem.addActionListener(listener);
        } else {
            menuItem.addActionListener(e -> System.out.println(text + " seleccionado"));
        }
        popupMenu.add(menuItem);
    }
    
    private void abrirCosteoRapido() {
        SwingUtilities.invokeLater(() -> {
            new CosteoForm_Ingresar().setVisible(true);
        });
        this.dispose();
    }

    private void abrirGestionProductos() {
        SwingUtilities.invokeLater(() -> {
            new Gui().setVisible(true);
        });
        this.dispose();
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que quieres cerrar sesión?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                new LoginRegistroForm().setVisible(true);
            });
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
            java.util.logging.Logger.getLogger(GuiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProductButton1;
    private javax.swing.JButton addProductButton2;
    private javax.swing.JButton addProductButton3;
    private javax.swing.JButton addProductButton4;
    private javax.swing.JButton bookmarkButton;
    private javax.swing.JButton costeoRapidoButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JButton menuButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
}
