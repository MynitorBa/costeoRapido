/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paqueteCosteoRapido;




import BuscadorInteligente.BuscadorInteligente;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import GUI.GuiPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import productosFavoritos.FavoritosManager;
import productosFavoritos.ProductoFavorito;
/**
 *
 * @author andre
 */
public class CosteoForm_Ingresar extends javax.swing.JFrame {
    
    private BuscadorInteligente buscador;
    private JPopupMenu popupMenu;
    private String currentUser;
    private final Color PLACEHOLDER_COLOR = new Color(128, 128, 128); // Color gris
    private final Color TEXT_COLOR = Color.BLACK;


    /**
     * Creates new form CosteoForm
     */
    public CosteoForm_Ingresar(String username) {
    this(username, "", 0, 0, 0);
}
    public CosteoForm_Ingresar(String username, String nombre, double costoFob, double flete, double margenVenta) {
        this.currentUser = username;
        
       
        initComponents();
        buscador = new BuscadorInteligente();
        popupMenu = new JPopupMenu();
        
        setupPlaceholders();
        
        if (!nombre.isEmpty()) {
        nombreDescripcionProducto.setText(nombre);
        nombreDescripcionProducto.setForeground(TEXT_COLOR);
    }
    
    if (costoFob > 0) {
        costoFobUSD$_Ingresar.setText(String.format("%.2f", costoFob));
        costoFobUSD$_Ingresar.setForeground(TEXT_COLOR);
    }
        
        
    searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });
    
    
    
    
    
    
    
    }
    
    
    
    private void setupPlaceholders() {
    setupPlaceholder(nombreDescripcionProducto, "Nombre o descripción del producto");
    setupPlaceholder(costoFobUSD$_Ingresar, "$0.00");
    setupPlaceholder(flete_Ingresar, "0%");
    setupPlaceholder(MargenVenta_Ingresar, "0%");
}
    
    private void setupPlaceholder(JTextField textField, String placeholder) {
    textField.setForeground(PLACEHOLDER_COLOR);
    
    textField.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(TEXT_COLOR);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setForeground(PLACEHOLDER_COLOR);
                textField.setText(placeholder);
            }
        }
    });
}
    
    private void handleSearch() {
        String query = searchField.getText().trim();
        List<String> sugerencias = buscador.obtenerSugerencias(query);
        
        if (!sugerencias.isEmpty()) {
            String[] sugerenciasArray = sugerencias.toArray(new String[0]);
            String seleccion = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione un producto:",
                "Resultados de búsqueda",
                JOptionPane.PLAIN_MESSAGE,
                null,
                sugerenciasArray,
                sugerenciasArray[0]
            );
            
            if (seleccion != null) {
                llenarCamposProducto(seleccion);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron productos que coincidan con la búsqueda.");
        }
    }
    
    private void llenarCamposProducto(String nombreProducto) {
        List<String[]> resultados = buscador.procesarConsulta(nombreProducto);
    if (!resultados.isEmpty()) {
        String[] producto = resultados.get(0);
        nombreDescripcionProducto.setText(producto[1]);
        nombreDescripcionProducto.setForeground(TEXT_COLOR);
        
        costoFobUSD$_Ingresar.setText(producto[2].replace("$", ""));
        costoFobUSD$_Ingresar.setForeground(TEXT_COLOR);
    }
    }

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {
        String query = searchField.getText().trim();
        if (query.length() >= 2) {
            List<String> sugerencias = buscador.obtenerSugerencias(query);
            mostrarSugerencias(sugerencias);
        } else {
            popupMenu.setVisible(false);
        }
    }
    
    private void mostrarSugerencias(List<String> sugerencias) {
        popupMenu.removeAll();
        for (String sugerencia : sugerencias) {
            JMenuItem item = new JMenuItem(sugerencia);
            item.addActionListener(e -> seleccionarSugerencia(sugerencia));
            popupMenu.add(item);
        }
        if (!sugerencias.isEmpty()) {
            popupMenu.show(searchField, 0, searchField.getHeight());
        } else {
            popupMenu.setVisible(false);
        }
    }

    private void seleccionarSugerencia(String sugerencia) {
        searchField.setText(sugerencia);
        popupMenu.setVisible(false);
        llenarCamposProducto(sugerencia);
    }
    
    private void resetearCampos() {
    nombreDescripcionProducto.setForeground(PLACEHOLDER_COLOR);
    nombreDescripcionProducto.setText("Nombre o descripción del producto");
    
    costoFobUSD$_Ingresar.setForeground(PLACEHOLDER_COLOR);
    costoFobUSD$_Ingresar.setText("$0.00");
    
    flete_Ingresar.setForeground(PLACEHOLDER_COLOR);
    flete_Ingresar.setText("0%");
    
    MargenVenta_Ingresar.setForeground(PLACEHOLDER_COLOR);
    MargenVenta_Ingresar.setText("0%");
    
    ClasificacionDAI_elegir.setSelectedIndex(0);
}

    private void calcularCosteo() {
        // Obtener los valores ingresados por el usuario
        try{
            
        
        String nombre = nombreDescripcionProducto.getText();
        double costoFob = Double.parseDouble(costoFobUSD$_Ingresar.getText().replace("$", "").replace(",", ""));
        double fletePercent = Double.parseDouble(flete_Ingresar.getText().replace("%", "")) / 100.0;
        String clasificacionDAI = (String) ClasificacionDAI_elegir.getSelectedItem();
        double margenVentaPercent = Double.parseDouble(MargenVenta_Ingresar.getText().replace("%", "")) / 100.0;

        // Realizar los cálculos necesarios
        double costoConFlete = costoFob * (1 + fletePercent);
        double daiPercent = obtenerPorcentajeDAI(clasificacionDAI);
        double costoConDAI = costoConFlete * (1 + daiPercent);
        double precioVenta = costoConDAI / (1 - margenVentaPercent);
        double precioConIVA = precioVenta * 1.12;  // Asumiendo un IVA del 12%

        // Crear y mostrar el formulario CosteoFinal con los resultados
            CosteoFinal costeoFinal = new CosteoFinal(currentUser);
            costeoFinal.setDatos(nombre, costoFob, costoConFlete, costoConDAI, precioVenta, precioConIVA, margenVentaPercent);
            costeoFinal.setVisible(true);
            this.dispose(); // Cierra la ventana actual
        } catch (Exception e) {
            mostrarError("Error al calcular el costeo: " + e.getMessage());
        }
    }


  private double obtenerPorcentajeDAI(String clasificacion) {
        switch (clasificacion) {
            case "Cámara 0%":
            case "Acceso 0%":
                return 0.0;
            case "Metal 15%":
            case "Grabador 15%":
                return 0.15;
            case "Aluminio 10%":
                return 0.10;
            default:
                return 0.0;
        }
    }     
    
        private boolean validarEntradas() {
    try {
        String nombre = nombreDescripcionProducto.getText();
        if (nombre.trim().isEmpty() || nombre.equals("Nombre o descripción del producto")) {
            mostrarError("Por favor, ingrese un nombre o descripción del producto.");
            return false;
        }
        
        String costoFobText = costoFobUSD$_Ingresar.getText().replace("$", "").replace(",", "");
        String fleteText = flete_Ingresar.getText().replace("%", "");
        String margenVentaText = MargenVenta_Ingresar.getText().replace("%", "");
        
        if (costoFobText.equals("0.00") || fleteText.equals("0") || margenVentaText.equals("0")) {
            mostrarError("Por favor, complete todos los campos.");
            return false;
        }
        
        double costoFob = Double.parseDouble(costoFobText);
        double flete = Double.parseDouble(fleteText);
        double margenVenta = Double.parseDouble(margenVentaText);
        
        if (costoFob <= 0 || flete < 0 || margenVenta < 0) {
            mostrarError("Los valores deben ser positivos.");
            return false;
        }
    } catch (NumberFormatException e) {
        mostrarError("Por favor, ingrese valores numéricos válidos.");
        return false;
    }
    return true;
}
        
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        MargenVenta_Ingresar = new javax.swing.JTextField();
        ClasificacionDAI_elegir = new javax.swing.JComboBox<>();
        flete_Ingresar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        CosteoRapido_calcular = new javax.swing.JButton();
        costoFobUSD$_Ingresar = new javax.swing.JTextField();
        nombreDescripcionProducto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        flechaIzquierda = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        recargar = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        favoritos = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        flechaDerecha = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(178, 171, 171));

        jLabel4.setFont(new java.awt.Font("Segoe UI Variable", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 144, 47));
        jLabel4.setText("Costeo Rápido");

        jPanel1.setBackground(new java.awt.Color(178, 171, 171));

        jLabel3.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        jLabel3.setText("DAI");

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        MargenVenta_Ingresar.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        MargenVenta_Ingresar.setText("0%");

        ClasificacionDAI_elegir.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        ClasificacionDAI_elegir.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cámara 0%", "Acceso 0%", "Metal 15%", "Grabador 15%", "Aluminio 10%" }));

        flete_Ingresar.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        flete_Ingresar.setText("0%");
        flete_Ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flete_IngresarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        jLabel1.setText("Costo Fob USD$");

        jLabel11.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        jLabel11.setText("Margen de Venta");

        jLabel2.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        jLabel2.setText("Flete");

        CosteoRapido_calcular.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        CosteoRapido_calcular.setText("COSTEO RAPIDO");
        CosteoRapido_calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CosteoRapido_calcularActionPerformed(evt);
            }
        });

        costoFobUSD$_Ingresar.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        costoFobUSD$_Ingresar.setText("$0.00");
        costoFobUSD$_Ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costoFobUSD$_IngresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CosteoRapido_calcular, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jButton1)
                        .addGap(40, 40, 40))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(costoFobUSD$_Ingresar)
                            .addComponent(flete_Ingresar)
                            .addComponent(ClasificacionDAI_elegir, 0, 167, Short.MAX_VALUE)
                            .addComponent(MargenVenta_Ingresar))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(costoFobUSD$_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(flete_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ClasificacionDAI_elegir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(MargenVenta_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(CosteoRapido_calcular))
                .addGap(25, 25, 25))
        );

        nombreDescripcionProducto.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        nombreDescripcionProducto.setText("Nombre o descripción del producto");
        nombreDescripcionProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreDescripcionProductoActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(178, 171, 171));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
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
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(favoritos)
                    .addComponent(menuButton)
                    .addComponent(flechaIzquierda)
                    .addComponent(searchButton)
                    .addComponent(flechaDerecha)
                    .addComponent(recargar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(nombreDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nombreDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    


    
    
    
    private void CosteoRapido_calcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CosteoRapido_calcularActionPerformed
        // TODO add your handling code here:
        if (validarEntradas()) {
            calcularCosteo();
        }
    }//GEN-LAST:event_CosteoRapido_calcularActionPerformed


   
    
    private void costoFobUSD$_IngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costoFobUSD$_IngresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_costoFobUSD$_IngresarActionPerformed

    private void flete_IngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flete_IngresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_flete_IngresarActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        handleSearch();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
    JPopupMenu popupMenu = new JPopupMenu();
   popupMenu.setBackground(Color.WHITE);
   popupMenu.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

   // Crear los items del menú
   JMenuItem inicioItem = new JMenuItem("🏠 Inicio");
JMenuItem perfilItem = new JMenuItem("👤 Perfil");
JMenuItem costeoItem = new JMenuItem("💰 Costeo Rápido");
JMenuItem productosItem = new JMenuItem("📦 Productos");
JMenuItem preguntasItem = new JMenuItem("❓ Preguntas Frecuentes");
JMenuItem historialItem = new JMenuItem("📋 Historial");
JMenuItem logoutItem = new JMenuItem("🚪 Cerrar Sesión");

   // Personalizar apariencia de los items
   Font menuFont = new Font("Segoe UI Emoji", Font.PLAIN, 14);
   Color hoverColor = new Color(240, 240, 240);
   
   for (JMenuItem item : new JMenuItem[]{inicioItem, perfilItem, costeoItem, 
       productosItem, preguntasItem, logoutItem}) {
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
   popupMenu.addSeparator();
   popupMenu.add(logoutItem);

   // Mostrar el menú
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
        handleSearch();
    }//GEN-LAST:event_searchFieldActionPerformed

    private void flechaIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flechaIzquierdaActionPerformed
        // TODO add your handling code here:
        
        
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new GuiPrincipal(currentUser).setVisible(true);
        });
    }//GEN-LAST:event_flechaIzquierdaActionPerformed

    private void nombreDescripcionProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreDescripcionProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreDescripcionProductoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        resetearCampos();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    // Método auxiliar para crear el panel de cada producto favorito
private JPanel crearPanelProductoFavorito(ProductoFavorito favorito) {
     JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout(8, 8));
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    panel.setBackground(Color.WHITE); // Fondo blanco limpio

    // Panel de información
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setBackground(Color.WHITE); // Fondo blanco
    infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    // Nombre del producto en negrita y verde oscuro
    JLabel nombreLabel = new JLabel(favorito.getNombre());
    nombreLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
    nombreLabel.setForeground(new Color(0, 128, 0)); // Verde oscuro
    infoPanel.add(nombreLabel);
    
    // Costo FOB en gris oscuro
    String detalles = String.format("<html><span style='color:gray;'>Costo FOB:</span> <span style='font-weight:bold;'>$%.2f</span></html>", 
                                    favorito.getCostoFobUSD());
    JLabel detallesLabel = new JLabel(detalles);
    detallesLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
    detallesLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
    infoPanel.add(detallesLabel);
    
    panel.add(infoPanel, BorderLayout.CENTER);

    // Panel de botones con diseño vertical y compacto
    JPanel botonesPanel = new JPanel();
    botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
    botonesPanel.setBackground(Color.WHITE); // Fondo blanco
    
    // Botón de costear en verde suave
    JButton costearButton = new JButton("Costear");
    costearButton.setBackground(new Color(0, 150, 0)); // Verde suave
    costearButton.setForeground(Color.WHITE);
    costearButton.setFont(new Font("SansSerif", Font.PLAIN, 11));
    costearButton.setFocusPainted(false);
    costearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    costearButton.setMaximumSize(new Dimension(80, 25));
    costearButton.addActionListener(e -> costearProductoFavorito(favorito));
    botonesPanel.add(costearButton);
    botonesPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Espacio entre botones

    // Botón de eliminar en gris oscuro
    JButton eliminarButton = new JButton("Eliminar");
    eliminarButton.setBackground(new Color(60, 60, 60)); // Gris oscuro
    eliminarButton.setForeground(Color.WHITE);
    eliminarButton.setFont(new Font("SansSerif", Font.PLAIN, 11));
    eliminarButton.setFocusPainted(false);
    eliminarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    eliminarButton.setMaximumSize(new Dimension(80, 25));
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
            java.util.logging.Logger.getLogger(CosteoForm_Ingresar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CosteoForm_Ingresar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CosteoForm_Ingresar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CosteoForm_Ingresar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CosteoForm_Ingresar("").setVisible(true); 
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ClasificacionDAI_elegir;
    private javax.swing.JButton CosteoRapido_calcular;
    private javax.swing.JTextField MargenVenta_Ingresar;
    private javax.swing.JTextField costoFobUSD$_Ingresar;
    private javax.swing.JButton favoritos;
    private javax.swing.JButton flechaDerecha;
    private javax.swing.JButton flechaIzquierda;
    private javax.swing.JTextField flete_Ingresar;
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton menuButton;
    private javax.swing.JTextField nombreDescripcionProducto;
    private javax.swing.JButton recargar;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
}
