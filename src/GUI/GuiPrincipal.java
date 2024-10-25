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
import gerstionUsuarios.GestionUsuarios;
/**
 *
 * @author mynit
 */

public class GuiPrincipal extends javax.swing.JFrame {

    private BuscadorInteligente buscador;
    private JPopupMenu sugerenciasPopup;
    private JPopupMenu popupMenu;
    private String currentUser;
    /**
     * Creates new form GuiPrincipal
     */
    public GuiPrincipal(String username) {
        this.currentUser = username;
        initComponents();
        customizeComponents();
        buscador = new BuscadorInteligente();
        configurarBuscadorInteligente();
        configurarEventos();
    }
    private void configurarEventos() {
    // Configurar eventos del buscador
    searchButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            realizarBusqueda();
        }
    });
    
    searchField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            realizarBusqueda();
        }
    });
    
    // Solo configuramos los eventos que no están en el form
    searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizarSugerencias(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizarSugerencias(); }
        public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizarSugerencias(); }
    });
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
        jPanel10 = new javax.swing.JPanel();
        flechaIzquierda = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        recargar = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        bookmarkButton = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        flechaDerecha = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(178, 171, 171));

        costeoRapidoButton.setBackground(new java.awt.Color(204, 255, 255));
        costeoRapidoButton.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        costeoRapidoButton.setForeground(new java.awt.Color(102, 102, 0));
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

        addProductButton1.setText("Añadir");
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

        addProductButton2.setText("Añadir");
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

        addProductButton3.setText("Añadir");
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

        addProductButton4.setText("Añadir");
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

        jPanel10.setBackground(new java.awt.Color(178, 171, 171));

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
        recargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recargarActionPerformed(evt);
            }
        });

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
        flechaDerecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flechaDerechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
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
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookmarkButton)
                    .addComponent(menuButton)
                    .addComponent(flechaIzquierda)
                    .addComponent(searchButton)
                    .addComponent(flechaDerecha)
                    .addComponent(recargar))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                            .addGap(81, 81, 81)
                            .addComponent(jLabel1)
                            .addGap(39, 39, 39)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(costeoRapidoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addContainerGap(13, Short.MAX_VALUE))
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

    private void addProductButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButton4ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    new Gui(currentUser).setVisible(true);
    this.dispose();
    }//GEN-LAST:event_addProductButton4ActionPerformed

    private void addProductButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButton3ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    new Gui(currentUser).setVisible(true);
    this.dispose();
    }//GEN-LAST:event_addProductButton3ActionPerformed

    private void addProductButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    new Gui(currentUser).setVisible(true);
    this.dispose();
    }//GEN-LAST:event_addProductButton2ActionPerformed

    private void addProductButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    new Gui(currentUser).setVisible(true);
    this.dispose();
    }//GEN-LAST:event_addProductButton1ActionPerformed

    private void costeoRapidoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costeoRapidoButtonActionPerformed
        // TODO add your handling code here:
        SwingUtilities.invokeLater(() -> {
        this.setVisible(false);  // Oculta la ventana actual
        new CosteoForm_Ingresar(currentUser).setVisible(true);
        this.dispose();  // Libera los recursos de la ventana actual
    });

    }//GEN-LAST:event_costeoRapidoButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        // TODO add your handling code here:
        popupMenu.show(menuButton, 0, menuButton.getHeight());
    }//GEN-LAST:event_menuButtonActionPerformed

    private void bookmarkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookmarkButtonActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Función de marcadores");
    }//GEN-LAST:event_bookmarkButtonActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

    private void flechaIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flechaIzquierdaActionPerformed
        // TODO add your handling code here:
        
        JOptionPane.showMessageDialog(this, "Navegando hacia atrás");
    }//GEN-LAST:event_flechaIzquierdaActionPerformed

    private void flechaDerechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flechaDerechaActionPerformed
        // TODO add your handling code here:
         JOptionPane.showMessageDialog(this, "Navegando hacia adelante");
    }//GEN-LAST:event_flechaDerechaActionPerformed

    private void recargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recargarActionPerformed
        // TODO add your handling code here:
        customizeComponents();
        SwingUtilities.updateComponentTreeUI(this);
        JOptionPane.showMessageDialog(this, "Página recargada");
    }//GEN-LAST:event_recargarActionPerformed

    
    private void gestionarProducto(int numeroProducto) {
        // Implementar la gestión de productos según el botón presionado
        JOptionPane.showMessageDialog(this, 
            "Gestionando producto " + numeroProducto);
    }

    private void createPopupMenu() {
        popupMenu = new JPopupMenu();
        popupMenu.setBackground(Color.WHITE);
        popupMenu.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JTextField searchField = new JTextField(15);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            searchField.getBorder(), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        popupMenu.add(searchField);
        popupMenu.addSeparator();

        addMenuItem("Perfil", "\uD83D\uDC64");
        addMenuItem("Costeo Rápido", "\uD83D\uDCB0", e -> abrirCosteoRapido());
        addMenuItem("Productos", "\uD83D\uDCE6", e -> abrirGestionProductos());
        
        if ("admin".equals(currentUser)) {
            addMenuItem("Gestión de Usuarios", "\uD83D\uDC65", e -> abrirGestionUsuarios());
        }
        
        addMenuItem("Favoritos", "\u2764");
        addMenuItem("Historial", "\uD83D\uDCC3");
        
        
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
            new CosteoForm_Ingresar(currentUser).setVisible(true);
        });
        this.dispose();
    }

    private void abrirGestionProductos() {
        SwingUtilities.invokeLater(() -> {
            new Gui(currentUser).setVisible(true);
        });
        this.dispose();
    }

    private void abrirGestionUsuarios() {
        SwingUtilities.invokeLater(() -> {
            new GestionUsuarios(currentUser).setVisible(true);
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

    private void customizeComponents() {
    // Configurar solo los íconos necesarios para los botones
    searchButton.setText("🔍");
    bookmarkButton.setText("🔖");
    menuButton.setText("☰");
    flechaIzquierda.setText("←");
    flechaDerecha.setText("→");
    recargar.setText("🔄");

    // Crear el menú desplegable
    createPopupMenu();
}

    private void customizeProductPanel(JPanel outerPanel, JPanel innerPanel, JButton addButton, String productName) {
    
    // Solo personalizamos la apariencia
    innerPanel.setBackground(new Color(204, 204, 204));
    addButton.setText("Agregar " + productName);
    addButton.setBackground(new Color(255, 255, 255));
    addButton.setForeground(new Color(0, 0, 0));
    addButton.setFont(new Font("Arial", Font.PLAIN, 12));
    
    // No modificamos la estructura de los paneles ya que está definida en el form
    outerPanel.setBackground(new Color(178, 171, 171));
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
            // Al iniciar desde main, usamos "invitado" o redirigimos al login
            new LoginRegistroForm().setVisible(true);
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
    private javax.swing.JButton flechaDerecha;
    private javax.swing.JButton flechaIzquierda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JButton menuButton;
    private javax.swing.JButton recargar;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
}
