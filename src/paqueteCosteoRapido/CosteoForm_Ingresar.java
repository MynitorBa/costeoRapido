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
import java.util.List;
/**
 *
 * @author andre
 */
public class CosteoForm_Ingresar extends javax.swing.JFrame {
    
    private BuscadorInteligente buscador;
    private JPopupMenu popupMenu;
    private String currentUser;

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
        
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetearCampos();
            }
        });
    searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });
    
    nombreDescripcionProducto.setText(nombre);
        costoFobUSD$_Ingresar.setText(String.format("$%.2f", costoFob));
        flete_Ingresar.setText(String.format("%.2f%%", flete * 100));
        MargenVenta_Ingresar.setText(String.format("%.2f%%", margenVenta * 100));
        ClasificacionDAI_elegir.setSelectedIndex(0);

        // Seleccionar la clasificación DAI apropiada (puedes ajustar esto según tus necesidades)
        ClasificacionDAI_elegir.setSelectedIndex(0);
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
            costoFobUSD$_Ingresar.setText(producto[2].replace("$", ""));
            // Asumiendo que el flete y el margen de venta no están en el producto, los dejamos como están
            // Si tienes esta información en el producto, ajusta los índices según corresponda
            // flete_Ingresar.setText(...);
            // MargenVenta_Ingresar.setText(...);
            
            // Para ClasificacionDAI_elegir, selecciona la opción más cercana basada en el DAI del producto
            String daiProducto = producto[5]; // Asumiendo que el DAI está en el índice 5
            String[] opciones = {"Cámara 0%", "Acceso 0%", "Metal 15%", "Grabador 15%", "Aluminio 10%"};
            String opcionMasCercana = opciones[0];
            for (String opcion : opciones) {
                if (opcion.contains(daiProducto)) {
                    opcionMasCercana = opcion;
                    break;
                }
            }
            ClasificacionDAI_elegir.setSelectedItem(opcionMasCercana);
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
    if (nombreDescripcionProducto.getText().equals("Nombre o descripción del producto")) {
        nombreDescripcionProducto.setText("Nombre o descripción del producto");
    }
    if (costoFobUSD$_Ingresar.getText().equals("$0.00")) {
        costoFobUSD$_Ingresar.setText("$0.00");
    }
    if (flete_Ingresar.getText().equals("0%")) {
        flete_Ingresar.setText("0%");
    }
    if (MargenVenta_Ingresar.getText().equals("0%")) {
        MargenVenta_Ingresar.setText("0%");
    }
    ClasificacionDAI_elegir.setSelectedIndex(0);
}
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonRegresarGUIPrincipal = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        nombreDescripcionProducto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        costoFobUSD$_Ingresar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        MargenVenta_Ingresar = new javax.swing.JTextField();
        ClasificacionDAI_elegir = new javax.swing.JComboBox<>();
        flete_Ingresar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        CosteoRapido_calcular = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        botonRegresarGUIPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/left_arrow (1).png"))); // NOI18N
        botonRegresarGUIPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegresarGUIPrincipalActionPerformed(evt);
            }
        });

        nombreDescripcionProducto.setText("Nombre o descripción del producto");

        jLabel3.setText("DAI");

        costoFobUSD$_Ingresar.setText("$0.00");
        costoFobUSD$_Ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costoFobUSD$_IngresarActionPerformed(evt);
            }
        });

        jButton1.setText("Reset");

        MargenVenta_Ingresar.setText("0%");

        ClasificacionDAI_elegir.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cámara 0%", "Acceso 0%", "Metal 15%", "Grabador 15%", "Aluminio 10%" }));

        flete_Ingresar.setText("0%");
        flete_Ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flete_IngresarActionPerformed(evt);
            }
        });

        jLabel1.setText("Costo Fob USD$");

        jLabel11.setText("Margen de Venta");

        jLabel2.setText("Flete");

        CosteoRapido_calcular.setText("COSTEO RAPIDO");
        CosteoRapido_calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CosteoRapido_calcularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MargenVenta_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ClasificacionDAI_elegir, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(flete_Ingresar, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(costoFobUSD$_Ingresar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(CosteoRapido_calcular, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(0, 66, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(nombreDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombreDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(costoFobUSD$_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(flete_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ClasificacionDAI_elegir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MargenVenta_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CosteoRapido_calcular)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel4.setText("Costeo Rápido");

        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });

        jLabel5.setText("Buscar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonRegresarGUIPrincipal)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                        .addGap(32, 32, 32))))
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(botonRegresarGUIPrincipal))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    


    
    
    
    private void CosteoRapido_calcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CosteoRapido_calcularActionPerformed
        // TODO add your handling code here:
        if (validarEntradas()) {
            calcularCosteo();
        }
    }//GEN-LAST:event_CosteoRapido_calcularActionPerformed


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
            CosteoFinal costeoFinal = new CosteoFinal();
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
            if (nombreDescripcionProducto.getText().trim().isEmpty()) {
                mostrarError("Por favor, ingrese un nombre o descripción del producto.");
                return false;
            }
            
            double costoFob = Double.parseDouble(costoFobUSD$_Ingresar.getText().replace("$", "").replace(",", ""));
            double flete = Double.parseDouble(flete_Ingresar.getText().replace("%", ""));
            double margenVenta = Double.parseDouble(MargenVenta_Ingresar.getText().replace("%", ""));
            
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
    
    private void costoFobUSD$_IngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costoFobUSD$_IngresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_costoFobUSD$_IngresarActionPerformed

    private void flete_IngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flete_IngresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_flete_IngresarActionPerformed

    private void botonRegresarGUIPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresarGUIPrincipalActionPerformed
        // TODO add your handling code here:
      botonRegresarGUIPrincipal.setEnabled(false);
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new GuiPrincipal(currentUser).setVisible(true);
        });
    }//GEN-LAST:event_botonRegresarGUIPrincipalActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    handleSearch();

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
                new CosteoForm_Ingresar("admin").setVisible(true); // Usar un valor por defecto para pruebas
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ClasificacionDAI_elegir;
    private javax.swing.JButton CosteoRapido_calcular;
    private javax.swing.JTextField MargenVenta_Ingresar;
    private javax.swing.JButton botonRegresarGUIPrincipal;
    private javax.swing.JTextField costoFobUSD$_Ingresar;
    private javax.swing.JTextField flete_Ingresar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nombreDescripcionProducto;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
}
