/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paqueteSolicitudDePedido;
    
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.List;
import paqueteCosteoRapido.CosteoFinal;
import paqueteCosteoRapido.CosteoForm_Ingresar;
import paqueteInicioSesion.AdministradorUsuario;

/**
 *
 * @author andre
 */
public class SolicitudDePedido_mandarCorreo extends javax.swing.JFrame {
    
    private String nombreProducto;
    private double costoFobUSD;
    private double costoUSD;
    private double costoQuetzales;
    private double precioVenta;
    private double precioConIVA;
    private double margen;
    private String currentUser;
    private AdministradorUsuario adminUsuario;

    /**
     * Creates new form SolicitudDePedido_mandarCorreo
     */
   public SolicitudDePedido_mandarCorreo(String username) {
    this.currentUser = username;
    this.adminUsuario = new AdministradorUsuario();
    initComponents();
    
}

public SolicitudDePedido_mandarCorreo() {
    this("admin"); // Valor por defecto
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
        bookmarkButton = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        flechaDerecha = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        historial = new javax.swing.JButton();
        nombreDescripcionProductoFINAL = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        costoFOBUSD$_FINAL = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        CostoUSD$_FINAL = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        costoQuetzales_FINAL = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        PrecioVenta_FINAL = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        ConIVA_FINAL = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        margen_FINAL = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        recibirCorreo = new javax.swing.JTextField();
        guardar_al_historial = new javax.swing.JButton();
        enviarCorreo = new javax.swing.JButton();
        menuButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(178, 171, 171));
        setResizable(false);

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

        jLabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 0));
        jLabel1.setText("Solicitud de Pedido");

        historial.setBackground(new java.awt.Color(178, 171, 171));
        historial.setFont(new java.awt.Font("Segoe UI Variable", 2, 14)); // NOI18N
        historial.setForeground(new java.awt.Color(51, 51, 0));
        historial.setText("Historial");

        nombreDescripcionProductoFINAL.setEditable(false);
        nombreDescripcionProductoFINAL.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        nombreDescripcionProductoFINAL.setText("Nombre o descripción del producto");
        nombreDescripcionProductoFINAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreDescripcionProductoFINALActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel2.setText("Producto costeado");

        jLabel3.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        jLabel3.setText("Costo FOB USD$");

        costoFOBUSD$_FINAL.setEditable(false);
        costoFOBUSD$_FINAL.setBackground(new java.awt.Color(178, 171, 171));

        jLabel4.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        jLabel4.setText("Costo USD$");

        CostoUSD$_FINAL.setEditable(false);
        CostoUSD$_FINAL.setBackground(new java.awt.Color(178, 171, 171));

        jLabel5.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        jLabel5.setText("Costo en Quetzales");

        costoQuetzales_FINAL.setEditable(false);
        costoQuetzales_FINAL.setBackground(new java.awt.Color(178, 171, 171));
        costoQuetzales_FINAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costoQuetzales_FINALActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        jLabel7.setText("Precio de Venta");

        PrecioVenta_FINAL.setEditable(false);
        PrecioVenta_FINAL.setBackground(new java.awt.Color(178, 171, 171));

        jLabel8.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        jLabel8.setText("Con IVA");

        ConIVA_FINAL.setEditable(false);
        ConIVA_FINAL.setBackground(new java.awt.Color(178, 171, 171));
        ConIVA_FINAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConIVA_FINALActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        jLabel9.setText("Margen");

        margen_FINAL.setEditable(false);
        margen_FINAL.setBackground(new java.awt.Color(178, 171, 171));

        jLabel6.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        jLabel6.setText("Enviar a:");

        recibirCorreo.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        recibirCorreo.setText("ejemplo@gmail.com");
        recibirCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recibirCorreoActionPerformed(evt);
            }
        });

        guardar_al_historial.setFont(new java.awt.Font("Segoe UI Variable", 3, 12)); // NOI18N
        guardar_al_historial.setText("Guardar");
        guardar_al_historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_al_historialActionPerformed(evt);
            }
        });

        enviarCorreo.setFont(new java.awt.Font("Segoe UI Variable", 3, 12)); // NOI18N
        enviarCorreo.setText("Enviar");
        enviarCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarCorreoActionPerformed(evt);
            }
        });

        menuButton1.setText("☰");
        menuButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(flechaIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(flechaDerecha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(recargar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(historial)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bookmarkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menuButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(474, 474, 474)
                .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(guardar_al_historial, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(enviarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(PrecioVenta_FINAL)
                                    .addComponent(costoQuetzales_FINAL)
                                    .addComponent(CostoUSD$_FINAL)
                                    .addComponent(costoFOBUSD$_FINAL, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(recibirCorreo))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(61, 61, 61)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(margen_FINAL, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ConIVA_FINAL, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(nombreDescripcionProductoFINAL, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookmarkButton)
                    .addComponent(menuButton)
                    .addComponent(flechaIzquierda)
                    .addComponent(searchButton)
                    .addComponent(flechaDerecha)
                    .addComponent(recargar)
                    .addComponent(menuButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(historial))
                .addGap(18, 18, 18)
                .addComponent(nombreDescripcionProductoFINAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(costoFOBUSD$_FINAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CostoUSD$_FINAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(costoQuetzales_FINAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(PrecioVenta_FINAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(ConIVA_FINAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(margen_FINAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(recibirCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(enviarCorreo)
                    .addComponent(guardar_al_historial))
                .addGap(90, 90, 90))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 448, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public void setDatos(String nombre, double costoFob, double costoUSD, double costoQuetzales, double precioVenta, double precioConIVA, double margen) {
        this.nombreProducto = nombre;
        this.costoFobUSD = costoFob;
        this.costoUSD = costoUSD;
        this.costoQuetzales = costoQuetzales;
        this.precioVenta = precioVenta;
        this.precioConIVA = precioConIVA;
        this.margen = margen;

        actualizarCampos();
    }

    private void actualizarCampos() {
       DecimalFormat df = new DecimalFormat("#,##0.00");
        
        nombreDescripcionProductoFINAL.setText(nombreProducto);
        costoFOBUSD$_FINAL.setText("$" + df.format(costoFobUSD));
        CostoUSD$_FINAL.setText("$" + df.format(costoUSD));
        costoQuetzales_FINAL.setText("Q" + df.format(costoQuetzales));
        PrecioVenta_FINAL.setText("Q" + df.format(precioVenta));
        ConIVA_FINAL.setText("Q" + df.format(precioConIVA));
        margen_FINAL.setText(df.format(margen * 100) + "%");
    }
    
    
private void enviarCorreo(String destinatario) {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    props.put("mail.debug", "true");
    props.put("javax.net.debug", "ssl,handshake");
    
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
        message.setSubject("Costeo Rápido Generado");
        
        // Crear el contenedor multipart
        Multipart multipart = new MimeMultipart();
        
        // Primera parte - el texto con HTML
        BodyPart messageBodyPart = new MimeBodyPart();
        String contenido = "<html><body>" +
                        "<h2><strong>Detalles de su solicitud de pedido:</h2>" +
                        "<p><strong>Producto:</strong> " + nombreProducto + "</p>" +
                        "<p><strong>Costo FOB USD:</strong> " + costoFOBUSD$_FINAL.getText() + "</p>" +
                        "<p><strong>Costo USD:</strong> " + CostoUSD$_FINAL.getText() + "</p>" +
                        "<p><strong>Costo en Quetzales:</strong> " + costoQuetzales_FINAL.getText() + "</p>" +
                        "<p><strong>Precio de Venta:</strong> " + PrecioVenta_FINAL.getText() + "</p>" +
                        "<p><strong>Precio con IVA:</strong> " + ConIVA_FINAL.getText() + "</p>" +
                        "<p><strong>Margen:</strong> " + margen_FINAL.getText() + "</p>" +
                        "<img src='cid:imagen'>" +
                        "</body></html>";
        messageBodyPart.setContent(contenido, "text/html");
        multipart.addBodyPart(messageBodyPart);
        
        // Segunda parte - la imagen embebida
        messageBodyPart = new MimeBodyPart();
        // Corregir el path de la imagen
        String rutaImagen = "src\\Imagenes\\Gray Modern Digital Marketing Linkedln Article Cover Image (1).png";
        
        // Verificar si el archivo existe antes de intentar adjuntarlo
        File imageFile = new File(rutaImagen);
        if (!imageFile.exists()) {
            throw new MessagingException("La imagen no se encuentra en la ruta especificada: " + rutaImagen);
        }
        
        DataSource source = new FileDataSource(imageFile);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setHeader("Content-ID", "<imagen>");
        multipart.addBodyPart(messageBodyPart);
        
        // Establecer el contenido completo del mensaje
        message.setContent(multipart);
        
        Transport.send(message);
        JOptionPane.showMessageDialog(this, "Correo enviado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
    } catch (MessagingException e) {
        String errorMessage = "Error al enviar el correo: " + e.getMessage();
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    

    
    
    
    private void nombreDescripcionProductoFINALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreDescripcionProductoFINALActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreDescripcionProductoFINALActionPerformed

    private void ConIVA_FINALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConIVA_FINALActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConIVA_FINALActionPerformed

    private void costoQuetzales_FINALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costoQuetzales_FINALActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_costoQuetzales_FINALActionPerformed

    private void enviarCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarCorreoActionPerformed
        // TODO add your handling code here:
        
       String destinatario = recibirCorreo.getText();
        if (validarCorreo(destinatario)) {
            if (confirmarEnvio()) {
                enviarCorreo(destinatario);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una dirección de correo válida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_enviarCorreoActionPerformed

    private boolean validarCorreo(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }
    
   private boolean confirmarEnvio() {
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea enviar el correo?", 
            "Confirmar envío", 
            JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }
    
    
    
    private void guardar_al_historialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_al_historialActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Funcionalidad de guardar aún no implementada");

    }//GEN-LAST:event_guardar_al_historialActionPerformed

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

    private void flechaIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flechaIzquierdaActionPerformed
        // TODO add your handling code here:
        
        this.dispose();
        SwingUtilities.invokeLater(() -> {
        new CosteoFinal(currentUser).setVisible(true);
    });
    }//GEN-LAST:event_flechaIzquierdaActionPerformed

    private void recibirCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recibirCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recibirCorreoActionPerformed

    private void menuButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuButton1ActionPerformed
    private void historialActionPerformed(java.awt.event.ActionEvent evt) {
        
        JOptionPane.showMessageDialog(this, "Funcionalidad de historial aún no implementada");
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
            java.util.logging.Logger.getLogger(SolicitudDePedido_mandarCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SolicitudDePedido_mandarCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SolicitudDePedido_mandarCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SolicitudDePedido_mandarCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SolicitudDePedido_mandarCorreo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ConIVA_FINAL;
    private javax.swing.JTextField CostoUSD$_FINAL;
    private javax.swing.JTextField PrecioVenta_FINAL;
    private javax.swing.JButton bookmarkButton;
    private javax.swing.JTextField costoFOBUSD$_FINAL;
    private javax.swing.JTextField costoQuetzales_FINAL;
    private javax.swing.JButton enviarCorreo;
    private javax.swing.JButton flechaDerecha;
    private javax.swing.JButton flechaIzquierda;
    private javax.swing.JButton guardar_al_historial;
    private javax.swing.JButton historial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField margen_FINAL;
    private javax.swing.JButton menuButton;
    private javax.swing.JButton menuButton1;
    private javax.swing.JTextField nombreDescripcionProductoFINAL;
    private javax.swing.JButton recargar;
    private javax.swing.JTextField recibirCorreo;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
}
