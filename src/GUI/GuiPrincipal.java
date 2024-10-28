/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;


import BuscadorInteligente.BuscadorInteligente;
import BuscadorInteligente.RandomProductDisplay;
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
import java.util.Collections;
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
    private RandomProductDisplay randomProductDisplay;
    
    /**
     * Creates new form GuiPrincipal
     */
    public GuiPrincipal(String username) {
        this.currentUser = username;
        initComponents();
        customizeComponents();
        initializeRandomProductDisplay();
        this.buscador = new BuscadorInteligente();
        this.sugerenciasPopup = new JPopupMenu();
        configurarComponentes();
        initializeRandomProductDisplay();
        if (randomProductDisplay != null) {
            SwingUtilities.invokeLater(() -> randomProductDisplay.displayRandomProducts());
        }
        
        
    }
    
    private void initializeRandomProductDisplay() {
    try {
        // Verify components exist before creating arrays
        if (productoamostrar1 == null || añadirFavorito == null || costearProducto1 == null) {
            throw new IllegalStateException("UI components not properly initialized");
        }

        JLabel[] productLabels = {
            productoamostrar1,
            productoamostrar2,
            productoamostrar3,
            productoamostrar4
        };

        JButton[] favoriteButtons = {
            añadirFavorito,
            añadirFavorito2,
            añadirFavorito3,
            añadirFavorito4
        };

        JButton[] costButtons = {
            costearProducto1,
            costearProducto2,
            costearProducto3,
            costearProducto4
        };

        // Verify all components are non-null
        for (JLabel label : productLabels) {
            if (label == null) throw new IllegalStateException("Product label not initialized");
        }
        for (JButton button : favoriteButtons) {
            if (button == null) throw new IllegalStateException("Favorite button not initialized");
        }
        for (JButton button : costButtons) {
            if (button == null) throw new IllegalStateException("Cost button not initialized");
        }

        this.randomProductDisplay = new RandomProductDisplay(
            this,
            productLabels,
            favoriteButtons,
            costButtons
        );

        // Call to display products should be after initialization
        SwingUtilities.invokeLater(() -> randomProductDisplay.displayRandomProducts());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error initializing product display: " + e.getMessage(),
            "Initialization Error",
            JOptionPane.ERROR_MESSAGE);
    }
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
    try {
        String precioStr = producto[2].replaceAll("[^0-9.]", "");
        double precioFOB = Double.parseDouble(precioStr);
        
        double costoUSDFinal = precioFOB * 1.1; 
        double costoQuetzales = costoUSDFinal * 7.85; 
        double margen = 30.0; 
        double precioVenta = costoQuetzales * (1 + (margen / 100));
        
        ProductoFavorito favorito = new ProductoFavorito(
            currentUser,    
            producto[1],    
            precioFOB,     
            costoUSDFinal, 
            costoQuetzales,
            precioVenta
        );
        
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
        final String nombre = producto[1];
        
        String precioStr = producto[2].replaceAll("[^0-9.]", "");
        double costoFob = Double.parseDouble(precioStr);

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

    popupMenu.addSeparator();

    // Agregar opciones al menú
    addMenuItem("👤 Perfil de Usuario", e -> abrirPerfilUsuario());
    addMenuItem("💰 Costeo Rápido", e -> abrirCosteoRapido());
    addMenuItem("📦 Productos", e -> abrirGestionProductos());
    addMenuItem("❓Preguntas Frecuentes", e -> abrirPreguntasFrecuentes());
    // Opción para administradores
    if ("admin".equals(currentUser)) {
        addMenuItem("👥 Gestión de Usuarios", e -> abrirGestionUsuarios());
    }

    popupMenu.addSeparator();
    addMenuItem("🔚 Cerrar Sesión", e -> logout());
}
    
   private void addMenuItem(String text, ActionListener action) {
    JMenuItem menuItem = new JMenuItem(text);
    menuItem.addActionListener(action);
    popupMenu.add(menuItem);
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
    


private void mostrarProductosAleatorios() {
    try {
        if (randomProductDisplay != null) {
            randomProductDisplay.displayRandomProducts();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error: Sistema de visualización no inicializado",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        String errorMessage = "Error displaying random products: " + e.getMessage();
        System.err.println(errorMessage);
        JOptionPane.showMessageDialog(this,
            errorMessage,
            "Display Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

        
        
        
        
public String getCurrentUser() {
        return currentUser;
    }
public RandomProductDisplay getRandomProductDisplay() {
        return randomProductDisplay;
    }
public void refreshRandomProducts() {
        SwingUtilities.invokeLater(this::mostrarProductosAleatorios);
    }

    
    
    private void logAndShowError(String message, Exception e) {
    // Log the full stack trace
    System.err.println(message);
    e.printStackTrace();
    
    // Show a user-friendly error message
    SwingUtilities.invokeLater(() -> {
        String errorDetails = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        JOptionPane.showMessageDialog(null,
            message + ": " + errorDetails,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    });
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
        productoamostrar1 = new javax.swing.JLabel();
        favoritos1 = new javax.swing.JButton();
        añadirFavorito = new javax.swing.JButton();
        costearProducto1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        productoamostrar2 = new javax.swing.JLabel();
        costearProducto2 = new javax.swing.JButton();
        añadirFavorito2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        flechaIzquierda = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        recargar = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        favoritos = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        flechaDerecha = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        productoamostrar3 = new javax.swing.JLabel();
        favoritos4 = new javax.swing.JButton();
        añadirFavorito3 = new javax.swing.JButton();
        costearProducto3 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        productoamostrar4 = new javax.swing.JLabel();
        favoritos6 = new javax.swing.JButton();
        añadirFavorito4 = new javax.swing.JButton();
        costearProducto4 = new javax.swing.JButton();

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
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel6.add(productoamostrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 114, 88));

        favoritos1.setText("❤");
        favoritos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoritos1ActionPerformed(evt);
            }
        });

        añadirFavorito.setText("❤");
        añadirFavorito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                añadirFavoritoActionPerformed(evt);
            }
        });

        costearProducto1.setText("Costear");
        costearProducto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costearProducto1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(costearProducto1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(añadirFavorito, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(favoritos1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(56, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(añadirFavorito)
                    .addComponent(costearProducto1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(74, 74, 74)
                    .addComponent(favoritos1)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel7.add(productoamostrar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 114, 88));

        costearProducto2.setText("Costear");
        costearProducto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costearProducto2ActionPerformed(evt);
            }
        });

        añadirFavorito2.setText("❤");
        añadirFavorito2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                añadirFavorito2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(costearProducto2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(añadirFavorito2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(costearProducto2)
                    .addComponent(añadirFavorito2))
                .addContainerGap(28, Short.MAX_VALUE))
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

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel12.add(productoamostrar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 114, 88));

        favoritos4.setText("❤");
        favoritos4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoritos4ActionPerformed(evt);
            }
        });

        añadirFavorito3.setText("❤");
        añadirFavorito3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                añadirFavorito3ActionPerformed(evt);
            }
        });

        costearProducto3.setText("Costear");
        costearProducto3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costearProducto3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(costearProducto3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(añadirFavorito3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(favoritos4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(68, Short.MAX_VALUE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(añadirFavorito3)
                    .addComponent(costearProducto3))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(74, 74, 74)
                    .addComponent(favoritos4)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel14.add(productoamostrar4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 114, 88));

        favoritos6.setText("❤");
        favoritos6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoritos6ActionPerformed(evt);
            }
        });

        añadirFavorito4.setText("❤");
        añadirFavorito4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                añadirFavorito4ActionPerformed(evt);
            }
        });

        costearProducto4.setText("Costear");
        costearProducto4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costearProducto4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(costearProducto4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(añadirFavorito4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(favoritos6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(56, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(añadirFavorito4)
                    .addComponent(costearProducto4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(74, 74, 74)
                    .addComponent(favoritos6)
                    .addContainerGap(76, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 430, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(81, 81, 81)
                            .addComponent(jLabel1)
                            .addGap(39, 39, 39))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(20, 20, 20)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(costeoRapidoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
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

    private void costearProducto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costearProducto2ActionPerformed
        // TODO add your handling code here:
         String[] producto = randomProductDisplay.getRandomProducts()[1]; // Obtener el segundo producto aleatorio
    costearProducto(producto);
    }//GEN-LAST:event_costearProducto2ActionPerformed

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
     try {
        // Get the current window and verify it's the correct type
        Window ventanaActual = SwingUtilities.getWindowAncestor((Component) evt.getSource());
        if (!(ventanaActual instanceof GuiPrincipal)) {
            throw new IllegalStateException("Current window is not a GuiPrincipal instance");
        }
        
        
        
        GuiPrincipal frameActual = (GuiPrincipal) ventanaActual;
        String currentUser = frameActual.getCurrentUser();
        
        // Save current window state
        Point location = frameActual.getLocation();
        Dimension size = frameActual.getSize();
        boolean isMaximized = (frameActual.getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0;
        
        // Create and configure new window
        SwingUtilities.invokeLater(() -> {
            try {
                GuiPrincipal nuevaVentana = new GuiPrincipal(currentUser);
                
                if (isMaximized) {
                    nuevaVentana.setExtendedState(JFrame.MAXIMIZED_BOTH);
                } else {
                    nuevaVentana.setLocation(location);
                    nuevaVentana.setSize(size);
                }
                
                // Ensure random products are refreshed in the new window
                RandomProductDisplay randomProductDisplay = nuevaVentana.getRandomProductDisplay();
                if (randomProductDisplay != null) {
                    nuevaVentana.refreshRandomProducts();
                }
                
                // Show new window and dispose old one
                nuevaVentana.setVisible(true);
                frameActual.dispose();
                
            } catch (Exception e) {
                logAndShowError("Error creating new window", e);
            }
        });
        
    } catch (Exception e) {
        logAndShowError("Error reloading window", e);
    }
    
    }//GEN-LAST:event_recargarActionPerformed

    private void añadirFavoritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_añadirFavoritoActionPerformed
        // TODO add your handling code here:
        String[] producto = randomProductDisplay.getRandomProducts()[0]; // Obtener el primer producto aleatorio
    agregarAFavoritos(producto);
    }//GEN-LAST:event_añadirFavoritoActionPerformed

    private void costearProducto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costearProducto1ActionPerformed
        // TODO add your handling code here:
        
        String[] producto = randomProductDisplay.getRandomProducts()[0]; // Obtener el primer producto aleatorio
    costearProducto(producto);
    }//GEN-LAST:event_costearProducto1ActionPerformed

    private void añadirFavorito2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_añadirFavorito2ActionPerformed
        // TODO add your handling code here:
        String[] producto = randomProductDisplay.getRandomProducts()[1]; // Obtener el segundo producto aleatorio
    agregarAFavoritos(producto);
    }//GEN-LAST:event_añadirFavorito2ActionPerformed

    private void añadirFavorito3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_añadirFavorito3ActionPerformed
        // TODO add your handling code here:
        String[] producto = randomProductDisplay.getRandomProducts()[2]; // Obtener el tercer producto aleatorio
    agregarAFavoritos(producto);
    }//GEN-LAST:event_añadirFavorito3ActionPerformed

    private void costearProducto3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costearProducto3ActionPerformed
        // TODO add your handling code here:
        String[] producto = randomProductDisplay.getRandomProducts()[2]; // Obtener el tercer producto aleatorio
    costearProducto(producto);
    }//GEN-LAST:event_costearProducto3ActionPerformed

    private void añadirFavorito4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_añadirFavorito4ActionPerformed
        // TODO add your handling code here:
        String[] producto = randomProductDisplay.getRandomProducts()[3]; // Obtener el cuarto producto aleatorio
    agregarAFavoritos(producto);
    }//GEN-LAST:event_añadirFavorito4ActionPerformed

    private void costearProducto4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costearProducto4ActionPerformed
        // TODO add your handling code here:
        String[] producto = randomProductDisplay.getRandomProducts()[3]; // Obtener el cuarto producto aleatorio
    costearProducto(producto);
    }//GEN-LAST:event_costearProducto4ActionPerformed

    private void favoritos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoritos1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_favoritos1ActionPerformed

    private void favoritos6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoritos6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_favoritos6ActionPerformed

    private void favoritos4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoritos4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_favoritos4ActionPerformed

    
    
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
    private javax.swing.JButton añadirFavorito;
    private javax.swing.JButton añadirFavorito2;
    private javax.swing.JButton añadirFavorito3;
    private javax.swing.JButton añadirFavorito4;
    private javax.swing.JButton costearProducto1;
    private javax.swing.JButton costearProducto2;
    private javax.swing.JButton costearProducto3;
    private javax.swing.JButton costearProducto4;
    private javax.swing.JButton costeoRapidoButton;
    private javax.swing.JButton favoritos;
    private javax.swing.JButton favoritos1;
    private javax.swing.JButton favoritos4;
    private javax.swing.JButton favoritos6;
    private javax.swing.JButton flechaDerecha;
    private javax.swing.JButton flechaIzquierda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JButton menuButton;
    private javax.swing.JLabel productoamostrar1;
    private javax.swing.JLabel productoamostrar2;
    private javax.swing.JLabel productoamostrar3;
    private javax.swing.JLabel productoamostrar4;
    private javax.swing.JButton recargar;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables

    
}
