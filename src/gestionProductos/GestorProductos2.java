/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionProductos;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
/**
 *
 * @author andre
 */
public class GestorProductos2 {
    private static final String ARCHIVO_EXCEL = "resources\\productos.xlsx";
    private static final String ARCHIVO_TEMPORAL = "resources\\productos_temp.xlsx";
    private static final String HOJA_PRODUCTOS = "Productos";
    private static final double TASA_CAMBIO = 7.8;

    private void inicializarArchivoExcel() {
        File archivo = new File(ARCHIVO_EXCEL);
        if (!archivo.exists()) {
            try (Workbook libro = new XSSFWorkbook()) {
                Sheet hoja = libro.createSheet(HOJA_PRODUCTOS);
                Row encabezado = hoja.createRow(0);
                String[] columnas = {"ID", "Nombre", "Precio USD", "Precio Quetzales", "Cantidad", "Tipo", "Marca", "Etiquetas", "Otros"};
                for (int i = 0; i < columnas.length; i++) {
                    encabezado.createCell(i).setCellValue(columnas[i]);
                }
                guardarLibro(libro, ARCHIVO_EXCEL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private String obtenerEmojiPorTipo(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "CAMARA" -> "\uD83D\uDCF7";
            case "SISTEMA DE ALMACENAMIENTO" -> "\uD83D\uDCBE";
            case "ACCESORIO CCTV" -> "\uD83D\uDD0C";
            case "CONTROL DE ACCESO Y SEGURIDAD" -> "\uD83D\uDD12";
            case "SISTEMAS DE RED" -> "\uD83C\uDF10";
            default -> "";
        };
    }

    private void guardarLibro(Workbook libro, String rutaArchivo) throws IOException {
        File archivoTemporal = new File(ARCHIVO_TEMPORAL);
        try (FileOutputStream outputStream = new FileOutputStream(archivoTemporal)) {
            libro.write(outputStream);
        }
        Files.move(archivoTemporal.toPath(), new File(rutaArchivo).toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    
    public void agregarProducto(String nombre, double precioUSD, double precioQuetzales, int cantidad, String tipo, String marca, String etiquetas) {
        try {
            Workbook libro = cargarLibro();
            Sheet hoja = libro.getSheet(HOJA_PRODUCTOS);
            int ultimaFila = hoja.getLastRowNum();
            Row nuevaFila = hoja.createRow(ultimaFila + 1);
            
            int nuevoId = 1;
            if (ultimaFila > 0) {
                for (Row fila : hoja) {
                    if (fila.getRowNum() == 0) continue;
                    Cell idCell = fila.getCell(0);
                    if (idCell != null) {
                        int currentId = getCellValueAsInt(idCell);
                        if (currentId >= nuevoId) {
                            nuevoId = currentId + 1;
                        }
                    }
                }
            }

            nuevaFila.createCell(0).setCellValue(nuevoId);
            nuevaFila.createCell(1).setCellValue(nombre);
            nuevaFila.createCell(2).setCellValue(precioUSD);
            nuevaFila.createCell(3).setCellValue(precioQuetzales);
            nuevaFila.createCell(4).setCellValue(cantidad);
            nuevaFila.createCell(5).setCellValue(tipo);
            nuevaFila.createCell(6).setCellValue(marca);
            nuevaFila.createCell(7).setCellValue(etiquetas);
            nuevaFila.createCell(8).setCellValue(obtenerEmojiPorTipo(tipo));

            guardarLibro(libro, ARCHIVO_EXCEL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editarProducto(int id, String nombre, double precioUSD, double precioQuetzales, int cantidad, String tipo, String marca, String etiquetas) {
        try {
            Workbook libro = cargarLibro();
            Sheet hoja = libro.getSheet(HOJA_PRODUCTOS);
            for (Row fila : hoja) {
                if (getCellValueAsInt(fila.getCell(0)) == id) {
                    fila.getCell(1).setCellValue(nombre);
                    fila.getCell(2).setCellValue(precioUSD);
                    fila.getCell(3).setCellValue(precioQuetzales);
                    fila.getCell(4).setCellValue(cantidad);
                    fila.getCell(5).setCellValue(tipo);
                    fila.getCell(6).setCellValue(marca);
                    fila.getCell(7).setCellValue(etiquetas);
                    fila.getCell(8).setCellValue(obtenerEmojiPorTipo(tipo));
                    break;
                }
            }
            guardarLibro(libro, ARCHIVO_EXCEL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarProducto(int id) {
    try {
        Workbook libro = cargarLibro();
        Sheet hoja = libro.getSheet(HOJA_PRODUCTOS);
        int lastRowNum = hoja.getLastRowNum();
        int rowToDelete = -1;

        // First find the row to delete
        for (int i = 1; i <= lastRowNum; i++) {
            Row fila = hoja.getRow(i);
            if (fila != null && getCellValueAsInt(fila.getCell(0)) == id) {
                rowToDelete = i;
                break;
            }
        }

        // If we found the row to delete
        if (rowToDelete != -1) {
            // If it's the last row, just remove it
            if (rowToDelete == lastRowNum) {
                Row removingRow = hoja.getRow(rowToDelete);
                if (removingRow != null) {
                    hoja.removeRow(removingRow);
                }
            } else {
                // Remove the row
                Row removingRow = hoja.getRow(rowToDelete);
                if (removingRow != null) {
                    hoja.removeRow(removingRow);
                }
                
                // Only shift if there are rows below to shift
                if (rowToDelete < lastRowNum) {
                    hoja.shiftRows(rowToDelete + 1, lastRowNum, -1);
                }
            }
            
            guardarLibro(libro, ARCHIVO_EXCEL);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    public String[] buscarProductoPorId(int id) {
        try {
            Workbook libro = cargarLibro();
            Sheet hoja = libro.getSheet(HOJA_PRODUCTOS);
            for (Row fila : hoja) {
                if (getCellValueAsInt(fila.getCell(0)) == id) {
                    return extraerDatosProducto(fila);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] buscarProductoPorNombre(String nombre) {
        try {
            Workbook libro = cargarLibro();
            Sheet hoja = libro.getSheet(HOJA_PRODUCTOS);
            for (Row fila : hoja) {
                if (fila.getCell(1).getStringCellValue().equalsIgnoreCase(nombre)) {
                    return extraerDatosProducto(fila);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String[]> obtenerTodosLosProductos() {
        List<String[]> productos = new ArrayList<>();
        try {
            Workbook libro = cargarLibro();
            Sheet hoja = libro.getSheet(HOJA_PRODUCTOS);
            for (Row fila : hoja) {
                if (fila.getRowNum() == 0) continue; // Saltar el encabezado
                productos.add(extraerDatosProducto(fila));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productos;
    }

    private Workbook cargarLibro() throws IOException {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL)) {
            return WorkbookFactory.create(fis);
        }
    }

    private String[] extraerDatosProducto(Row fila) {
        String[] producto = new String[9]; // Aumentado a 9 para incluir la columna "Otros"
        for (int i = 0; i < 9; i++) {
            Cell celda = fila.getCell(i);
            if (celda != null) {
                switch (celda.getCellType()) {
                    case NUMERIC:
                        if (i == 2) {
                            producto[i] = String.format("$%.2f", celda.getNumericCellValue());
                        } else if (i == 3) {
                            producto[i] = String.format("Q%.2f", celda.getNumericCellValue());
                        } else {
                            producto[i] = String.valueOf((int) celda.getNumericCellValue());
                        }
                        break;
                    case STRING:
                        producto[i] = celda.getStringCellValue();
                        break;
                    default:
                        producto[i] = "";
                }
            } else {
                producto[i] = "";
            }
        }
        return producto;
    }

    private int getCellValueAsInt(Cell cell) {
        if (cell == null) {
            return -1;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return -1;
                }
            default:
                return -1;
        }
    }

    public double convertirUSDaQuetzales(double usd) {
        return usd * TASA_CAMBIO;
    }

    public double convertirQuetzalesAUSD(double quetzales) {
        return quetzales / TASA_CAMBIO;
    }

    
}