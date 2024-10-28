/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BuscadorInteligente;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
/**
 *
 * @author andre
 */
public class RandomProductDisplay {
    private final JFrame parentFrame;
    private final Map<Integer, String[]> currentDisplayedProducts;
    private final JLabel[] productLabels;
    private final JButton[] favoriteButtons;
    private final JButton[] costButtons;
    private final int MAX_PRODUCTS = 4;
    private static final String ARCHIVO_EXCEL = "resources\\productos.xlsx";
    private List<String[]> recentlyDisplayedProducts = new ArrayList<>(); // Nueva lista para evitar repeticiones

    public RandomProductDisplay(JFrame parentFrame, JLabel[] productLabels, JButton[] favoriteButtons, JButton[] costButtons) {
        if (productLabels.length != MAX_PRODUCTS || favoriteButtons.length != MAX_PRODUCTS || costButtons.length != MAX_PRODUCTS) {
            throw new IllegalArgumentException("All component arrays must have length " + MAX_PRODUCTS);
        }
        this.parentFrame = parentFrame;
        this.currentDisplayedProducts = new HashMap<>();
        this.productLabels = productLabels;
        this.favoriteButtons = favoriteButtons;
        this.costButtons = costButtons;

        initializeButtons();
    }

    public void displayRandomProducts() {
        try {
            List<String[]> products = readProductsFromExcel();
            if (products.isEmpty()) {
                handleNoProducts();
                return;
            }

            // Remover productos mostrados recientemente para evitar repetición
            products.removeAll(recentlyDisplayedProducts);

            // Si hay pocos productos en la lista después de remover los recientes, agregar los recientes de nuevo
            if (products.size() < MAX_PRODUCTS) {
                products.addAll(recentlyDisplayedProducts);
            }

            Collections.shuffle(products);
            currentDisplayedProducts.clear();

            int productsToShow = Math.min(MAX_PRODUCTS, products.size());
            recentlyDisplayedProducts.clear(); // Limpiar la lista de productos recientes
            for (int i = 0; i < MAX_PRODUCTS; i++) {
                if (i < productsToShow) {
                    displayProduct(i, products.get(i));
                    recentlyDisplayedProducts.add(products.get(i)); // Agregar producto mostrado a la lista reciente
                } else {
                    clearProductSlot(i);
                }
            }
        } catch (Exception e) {
            handleError("Error displaying random products", e);
        }
    }

    // Resto del código se mantiene igual
    private List<String[]> readProductsFromExcel() {
        List<String[]> products = new ArrayList<>();
        File file = new File(ARCHIVO_EXCEL);

        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) rowIterator.next(); // Saltar la fila de encabezado

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String[] productData = new String[8];

                for (int i = 0; i < productData.length; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    productData[i] = getCellValueAsString(cell);
                }

                if (productData[0] != null && !productData[0].trim().isEmpty() &&
                    productData[1] != null && !productData[1].trim().isEmpty() &&
                    productData[2] != null && !productData[2].trim().isEmpty()) {
                    products.add(productData);
                }
            }
        } catch (Exception e) {
            handleError("Error reading Excel file", e);
        }

        return products;
    }
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC: return DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue().toString() : String.format("%.2f", cell.getNumericCellValue());
            case BOOLEAN: return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA: 
                try { return cell.getStringCellValue(); } 
                catch (Exception e) { return String.valueOf(cell.getNumericCellValue()); }
            default: return "";
        }
    }

    private void displayProduct(int index, String[] product) {
        try {
            currentDisplayedProducts.put(index, product);
            String nombreProducto = product[1];
            String precio = product[2];

            productLabels[index].setText(String.format("📦 %s - $%s", nombreProducto, precio));
            enableButtons(index);
        } catch (Exception e) {
            handleError("Error displaying product at index " + index, e);
            clearProductSlot(index);
        }
    }

    private void initializeButtons() {
        for (int i = 0; i < MAX_PRODUCTS; i++) {
            final int index = i;

            
            costButtons[i].addActionListener(e -> handleCostClick(index));
        }
    }

    private void clearProductSlot(int index) {
        productLabels[index].setText("No hay producto disponible");
        disableButtons(index);
        currentDisplayedProducts.remove(index);
    }

    private void enableButtons(int index) {
        favoriteButtons[index].setEnabled(true);
        costButtons[index].setEnabled(true);
    }

    private void disableButtons(int index) {
        favoriteButtons[index].setEnabled(false);
        costButtons[index].setEnabled(false);
    }

    private void handleFavoriteClick(int index) {
        String[] product = currentDisplayedProducts.get(index);
        if (product != null) {
            JOptionPane.showMessageDialog(parentFrame, "Producto añadido a favoritos: " + product[1], "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleCostClick(int index) {
        String[] product = currentDisplayedProducts.get(index);
        if (product != null) {
            double precio = Double.parseDouble(product[2]);
            JOptionPane.showMessageDialog(parentFrame, String.format("Precio del producto %s: $%.2f", product[1], precio), "Información de Precio", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleNoProducts() {
        for (int i = 0; i < MAX_PRODUCTS; i++) clearProductSlot(i);
        JOptionPane.showMessageDialog(parentFrame, "No hay productos disponibles para mostrar.", "Sin Productos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleError(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        JOptionPane.showMessageDialog(parentFrame, message + "\nDetalles: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    public String[][] getRandomProducts() {
    String[][] productsArray = new String[currentDisplayedProducts.size()][];
    int index = 0;
    for (String[] product : currentDisplayedProducts.values()) {
        productsArray[index++] = product;
    }
    return productsArray;
}
}