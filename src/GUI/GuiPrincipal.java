/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;


import BuscadorInteligente.BuscadorInteligente;
import Historial.HistorialViewer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import paqueteInicioSesion.LoginRegistroForm;
import paqueteCosteoRapido.CosteoForm_Ingresar;
import paqueteCosteoRapido.CosteoFinal;
import gestionProductos.Gui;
import gestionUsuarios.GestionUsuarios;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import java.awt.event.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import preguntasFrecuentes.PreguntasFrecuentesForm;
import productosFavoritos.FavoritosManager;
import productosFavoritos.ProductoFavorito;
/**
 *
 * @author mynit
 */

public class GuiPrincipal extends javax.swing.JFrame {

    private BuscadorInteligente buscador;
    private JPopupMenu sugerenciasPopup;
    private JPopupMenu popupMenu;
    private String currentUser;
    private boolean isUpdatingSuggestions = false;
    private boolean isProcessingEvent = false;
    
    /**
     * Creates new form GuiPrincipal
     */
    public GuiPrincipal(String username) {
        this.currentUser = username;
        initComponents();
        customizeComponents();
        buscador = new BuscadorInteligente();
        sugerenciasPopup = new JPopupMenu();
        configurarComponentes();
    }
    
    private void configurarComponentes() {
        configurarPlaceholder();
        configurarEventosBusqueda();
        configurarSugerencias();
    }
    private void configurarPlaceholder() {
        searchField.setText("Buscar");
        searchField.setForeground(Color.GRAY);
        searchField.setEditable(true);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Buscar")) {
                    isProcessingEvent = true;
                    try {
                        searchField.setText("");
                        searchField.setForeground(Color.BLACK);
                    } finally {
                        isProcessingEvent = false;
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    isProcessingEvent = true;
                    try {
                        searchField.setText("Buscar");
                        searchField.setForeground(Color.GRAY);
                    } finally {
                        isProcessingEvent = false;
                    }
                }
            }
    });
}
    
    private void configurarEventosBusqueda() {
        // Evento para el botón de búsqueda
        searchButton.addActionListener(e -> realizarBusqueda());
        
        // Evento para la tecla Enter en el campo de búsqueda
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    realizarBusqueda();
                }
            }
        });
    }
    
    private void configurarSugerencias() {
    // Configurar el DocumentListener
    searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            if (!isProcessingEvent) {
                actualizarSugerenciasDelayado();
            }
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            if (!isProcessingEvent) {
                actualizarSugerenciasDelayado();
            }
        }

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            if (!isProcessingEvent) {
                actualizarSugerenciasDelayado();
            }
        }
    });

    // Configurar el popup para que no quite el foco
    sugerenciasPopup.addPopupMenuListener(new PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> searchField.requestFocusInWindow());
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> searchField.requestFocusInWindow());
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> searchField.requestFocusInWindow());
        }
    });

    // Manejar teclas especiales
    searchField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    sugerenciasPopup.setVisible(false);
                    break;
                case KeyEvent.VK_ENTER:
                    realizarBusqueda();
                    break;
                case KeyEvent.VK_DOWN:
                    if (sugerenciasPopup.isVisible() && sugerenciasPopup.getComponentCount() > 0) {
                        ((JMenuItem)sugerenciasPopup.getComponent(0)).requestFocusInWindow();
                    }
                    break;
            }
        }
    });
}
    private Timer delayTimer;
private static final int DELAY = 300; // milisegundos

private void actualizarSugerenciasDelayado() {
    if (delayTimer != null && delayTimer.isRunning()) {
        delayTimer.restart();
    } else {
        delayTimer = new Timer(DELAY, e -> {
            SwingUtilities.invokeLater(this::actualizarSugerencias);
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }
}


    
    
    
private void manejarCambioTexto() {
    // Eliminamos la verificación de isUpdatingSuggestions que estaba causando el problema
    if (searchField.getText().equals("Buscar")) {
        return;
    }
    
    SwingUtilities.invokeLater(() -> {
        String texto = searchField.getText();
        if (!texto.isEmpty() && !texto.equals("Buscar")) {
            List<String> sugerencias = buscador.obtenerSugerencias(texto);
            mostrarSugerencias(sugerencias);
        } else {
            sugerenciasPopup.setVisible(false);
        }
    });
}
    private void configurarBuscadorInteligente() {
        sugerenciasPopup = new JPopupMenu();
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { 
                if (!searchField.getText().equals("Buscar")) {
                    actualizarSugerencias(); 
                }
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { 
                if (!searchField.getText().equals("Buscar")) {
                    actualizarSugerencias(); 
                }
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { 
                if (!searchField.getText().equals("Buscar")) {
                    actualizarSugerencias(); 
                }
            }});

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
        if (isProcessingEvent) return;
        
        String texto = searchField.getText();
        if (texto.equals("Buscar") || texto.isEmpty()) {
            sugerenciasPopup.setVisible(false);
            return;
        }

        List<String> sugerencias = buscador.obtenerSugerencias(texto);
        mostrarSugerencias(sugerencias);
    }
    
    
    private void mostrarSugerencias(List<String> sugerencias) {
    isProcessingEvent = true;
    try {
        sugerenciasPopup.removeAll();
        
        if (sugerencias.isEmpty()) {
            sugerenciasPopup.setVisible(false);
            return;
        }

        for (String sugerencia : sugerencias) {
            JMenuItem item = new JMenuItem(sugerencia) {
                // Sobreescribir el método para mantener el foco
                @Override
                public void processMouseEvent(MouseEvent e) {
                    if (e.getID() == MouseEvent.MOUSE_RELEASED) {
                        searchField.setText(getText());
                        searchField.requestFocusInWindow();
                        sugerenciasPopup.setVisible(false);
                    }
                    super.processMouseEvent(e);
                }
            };
            
            item.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
                    searchField.setText(sugerencia);
                    searchField.requestFocusInWindow();
                    sugerenciasPopup.setVisible(false);
                });
            });
            
            sugerenciasPopup.add(item);
        }

        if (!sugerenciasPopup.isVisible() && searchField.hasFocus()) {
            SwingUtilities.invokeLater(() -> {
                sugerenciasPopup.show(searchField, 0, searchField.getHeight());
                searchField.requestFocusInWindow();
            });
        }
        
        sugerenciasPopup.revalidate();
        sugerenciasPopup.repaint();
        
    } finally {
        isProcessingEvent = false;
        // Aseguramos que el campo mantenga el foco
        SwingUtilities.invokeLater(() -> searchField.requestFocusInWindow());
    }
}

    private void realizarBusqueda() {
        if (searchField.getText().equals("Buscar")) {
            return;
        }
        
        String consulta = searchField.getText();
        
        // Cerrar el popup de sugerencias si está visible
        sugerenciasPopup.setVisible(false);
        
        // Ejecutar la búsqueda en un hilo separado para no bloquear la UI
        SwingWorker<List<String[]>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<String[]> doInBackground() {
                List<String[]> resultados = buscador.procesarConsulta(consulta);
                Map<String, String> filtros = new HashMap<>();
                resultados = buscador.filtrarResultados(resultados, filtros);
                return buscador.ordenarResultados(resultados, consulta, "relevancia");
            }
            
            @Override
            protected void done() {
                try {
                    List<String[]> resultados = get();
                    mostrarResultados(resultados);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(GuiPrincipal.this,
                        "Error al realizar la búsqueda: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        
        worker.execute();
    }
    
    private void mostrarResultados(List<String[]> resultados) {
    JPanel resultPanel = new JPanel();
    resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

    for (String[] producto : resultados) {
        JPanel productoPanel = new JPanel();
        productoPanel.setLayout(new BorderLayout());
        productoPanel.setBorder(BorderFactory.createEtchedBorder());

        // Panel para el nombre y detalles
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel nombreLabel = new JLabel(producto[1]);
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(nombreLabel);

        JLabel detallesLabel = new JLabel(String.format("Tipo: %s, Marca: %s, Precio: %s", 
                                        producto[5], producto[6], producto[2]));
        infoPanel.add(detallesLabel);
        
        productoPanel.add(infoPanel, BorderLayout.CENTER);

        // Panel para los botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));

        JButton favoritosButton = new JButton("Añadir a favoritos");
        favoritosButton.addActionListener(e -> agregarAFavoritos(producto));
        botonesPanel.add(favoritosButton);

        JButton costearButton = new JButton("Costear");
        costearButton.addActionListener(e -> costearProducto(producto));
        botonesPanel.add(costearButton);

        productoPanel.add(botonesPanel, BorderLayout.EAST);

        resultPanel.add(productoPanel);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    JScrollPane scrollPane = new JScrollPane(resultPanel);
    scrollPane.setPreferredSize(new Dimension(400, 300));

    JOptionPane.showMessageDialog(this, scrollPane, "Resultados de la búsqueda", 
                                JOptionPane.PLAIN_MESSAGE);
}
    
    
    private void agregarAFavoritos(String[] producto) {
        // TODO: Implementar la lógica para agregar a favoritos
        try {
        // Convertir el precio a double, limpiando caracteres especiales
        String precioStr = producto[2].replaceAll("[^0-9.]", "");
        double precioFOB = Double.parseDouble(precioStr);
        
        // Calcular los valores necesarios
        double costoUSDFinal = precioFOB * 1.1; 
        double costoQuetzales = costoUSDFinal * 7.85; 
        double margen = 30.0; 
        double precioVenta = costoQuetzales * (1 + (margen/100));
        double precioConIVA = precioVenta * 1.12; 
        
        // Crear el producto favorito usando el constructor correcto
        ProductoFavorito favorito = new ProductoFavorito(
            currentUser,    // usuario actual
            producto[1],    // nombre
            precioFOB,     // costoFobUSD
            costoUSDFinal, // costoUSDFinal
            costoQuetzales,// costoQuetzales
            precioVenta,   // precioVenta
            precioConIVA,  // precioConIVA
            margen         // margen
        );
        
        // Guardar en favoritos con el usuario actual
        FavoritosManager favoritosManager = new FavoritosManager();
        favoritosManager.guardarFavorito(favorito, currentUser);
        
        JOptionPane.showMessageDialog(this, 
            "Producto '" + producto[1] + "' guardado en favoritos", 
            "Favorito Guardado", 
            JOptionPane.INFORMATION_MESSAGE);
            
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
            "Error al procesar el precio del producto",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error al guardar el favorito: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

    
    
    
    private void costearProducto(String[] producto) {
    try {
        // Extract only the name and price from the product array
        final String nombre = producto[1];
        
        // Convert price string to double, removing special characters
        final double costoFob;
        String precioStr = producto[2].replaceAll("[^0-9.]", "");
        if (!precioStr.isEmpty()) {
            costoFob = Double.parseDouble(precioStr);
        } else {
            costoFob = 0.0;
        }
        
        // Open CosteoForm_Ingresar with just name and price
        SwingUtilities.invokeLater(() -> {
            CosteoForm_Ingresar costeoForm = new CosteoForm_Ingresar(
                currentUser,
                nombre,
                costoFob,
                0.0, // Default flete
                0.0  // Default margenVenta
            );
            costeoForm.setVisible(true);
            this.dispose();
        });
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error al abrir el formulario de costeo: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
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

    popupMenu.addSeparator();

    // Agregar la opción de Perfil de Usuario
    addMenuItem("Perfil de Usuario", "\uD83D\uDC64", e -> abrirPerfilUsuario());
    addMenuItem("Costeo Rápido", "\uD83D\uDCB0", e -> abrirCosteoRapido());
    addMenuItem("Productos", "\uD83D\uDCE6", e -> abrirGestionProductos());
    addMenuItem("Preguntas Frecuentes", "❓", e -> abrirPreguntasFrecuentes());
    
    if ("admin".equals(currentUser)) {
        addMenuItem("Gestión de Usuarios", "\uD83D\uDC65", e -> abrirGestionUsuarios());
    }
    


    
    popupMenu.addSeparator();
    addMenuItem("Cerrar Sesión", "\uD83D\uDEAA", e -> logout());
}

   

// Agregar el método para abrir el perfil de usuario
private void abrirPerfilUsuario() {
    SwingUtilities.invokeLater(() -> {
        new perfilUsuario.PerfilUsuario(currentUser).setVisible(true);
    });
    this.dispose();
}
    private void abrirPreguntasFrecuentes() {
    SwingUtilities.invokeLater(() -> {
        new PreguntasFrecuentesForm(currentUser).setVisible(true);
    });
    this.dispose();
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
    searchField.setEditable(true);
    searchField.setEnabled(true);    
    searchField.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentShown(ComponentEvent e) {
            searchField.setEditable(true);
            searchField.setEnabled(true);
        }
    });

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
    
    private void mostrarFavoritos() {
        FavoritosManager favoritosManager = new FavoritosManager();
        List<ProductoFavorito> favoritos = favoritosManager.obtenerFavoritosUsuario(currentUser);
        
        if (favoritos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No tienes productos favoritos guardados",
                "Sin Favoritos",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        for (ProductoFavorito favorito : favoritos) {
            JPanel productoPanel = crearPanelProductoFavorito(favorito);
            mainPanel.add(productoPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre productos
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, 
            scrollPane, 
            "Mis Productos Favoritos", 
            JOptionPane.PLAIN_MESSAGE);
    }

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
    
    private void costearProductoFavorito(ProductoFavorito favorito) {
        SwingUtilities.invokeLater(() -> {
            CosteoForm_Ingresar costeoForm = new CosteoForm_Ingresar(
                currentUser,
                favorito.getNombre(),
                favorito.getCostoFobUSD(),
                0.0, // Puedes ajustar el flete si lo tienes guardado
                favorito.getMargen() * 100 // Convertir el margen decimal a porcentaje
            );
            costeoForm.setVisible(true);
            this.dispose();
        });
    }
    
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
                mostrarFavoritos();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar el favorito: " + e.getMessage(),
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
        favoritos = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        flechaDerecha = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(178, 171, 171));

        costeoRapidoButton.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        costeoRapidoButton.setForeground(new java.awt.Color(33, 192, 80));
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
                .addComponent(favoritos, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(favoritos)
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
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 430, Short.MAX_VALUE)
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

    private void favoritosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoritosActionPerformed
        // TODO add your handling code here:
        mostrarFavoritos();

    }//GEN-LAST:event_favoritosActionPerformed

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
    private javax.swing.JButton costeoRapidoButton;
    private javax.swing.JButton favoritos;
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
