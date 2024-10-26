/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Historial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import paqueteCosteoRapido.CosteoFinal;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;


/**
 *
 * @author andre
 */
public class HistorialViewer extends JFrame {
    private final JTable tabla;
    private final DefaultTableModel modeloTabla;
    private final HistorialManager historialManager;
    private final String currentUser;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final CosteoFinal parentForm; // Referencia al formulario principal

    public HistorialViewer(String usuario, CosteoFinal parentForm) {
        this.currentUser = usuario;
        this.historialManager = new HistorialManager();
        this.parentForm = parentForm;

        setTitle("Historial de Costeos");
        setSize(800, 400); // Tamaño fijo
        setResizable(false); // Deshabilitar redimensionado
        setLocationRelativeTo(null);
        
        // Configurar la tabla
        String[] columnas = {
            "Fecha", "Producto", "Costo FOB USD", "Costo USD Final",
            "Costo Quetzales", "Precio Venta", "Precio con IVA", "Margen"
        };
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Agregar listener para doble clic
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    cargarCosteoSeleccionado();
                }
            }
        });

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Panel superior con título
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Historial de Costeos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Panel inferior con botón de actualizar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Actualizar");
        refreshButton.addActionListener(e -> cargarHistorial());
        buttonPanel.add(refreshButton);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Configurar cierre automático cuando la ventana pierda el enfoque
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {}

            @Override
            public void windowLostFocus(WindowEvent e) {
                dispose(); // Cerrar la ventana al perder el enfoque
            }
        });

        cargarHistorial(); // Cargar solo el historial del usuario actual
    }

    private void cargarHistorial() {
        modeloTabla.setRowCount(0);
        List<HistorialEntry> historial = historialManager.cargarHistorial(currentUser);

        for (HistorialEntry entrada : historial) {
            modeloTabla.addRow(new Object[]{
                entrada.getFechaCalculo().format(dateFormatter),
                entrada.getNombreProducto(),
                "$" + df.format(entrada.getCostoFobUSD()),
                "$" + df.format(entrada.getCostoUSDFinal()),
                "Q" + df.format(entrada.getCostoQuetzales()),
                "Q" + df.format(entrada.getPrecioVenta()),
                "Q" + df.format(entrada.getPrecioConIVA()),
                df.format(entrada.getMargen() * 100) + "%"
            });
        }
    }

    private void cargarCosteoSeleccionado() {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow >= 0) {
            // Obtener los valores de la fila seleccionada
            String producto = (String) tabla.getValueAt(selectedRow, 1);
            String costoFobUSD = ((String) tabla.getValueAt(selectedRow, 2)).replace("$", "");
            String costoUSDFinal = ((String) tabla.getValueAt(selectedRow, 3)).replace("$", "");
            String costoQuetzales = ((String) tabla.getValueAt(selectedRow, 4)).replace("Q", "");
            String precioVenta = ((String) tabla.getValueAt(selectedRow, 5)).replace("Q", "");
            String precioConIVA = ((String) tabla.getValueAt(selectedRow, 6)).replace("Q", "");
            String margen = ((String) tabla.getValueAt(selectedRow, 7)).replace("%", "");

            // Cargar los valores en el formulario principal
            parentForm.cargarCosteoDesdeHistorial(
                producto, costoFobUSD, costoUSDFinal, costoQuetzales,
                precioVenta, precioConIVA, margen
            );
            
            dispose(); // Cerrar la ventana del historial
        }
    }
}