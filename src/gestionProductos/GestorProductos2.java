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
    private static final double TASA_CAMBIO = 7.8; // 1 USD = 7.8 Quetzales

    public GestorProductos2() {
        inicializarArchivoExcel();
    }

    private void inicializarArchivoExcel() {
        File archivo = new File(ARCHIVO_EXCEL);
        if (!archivo.exists()) {
            try (Workbook libro = new XSSFWorkbook()) {
                Sheet hoja = libro.createSheet(HOJA_PRODUCTOS);
                Row encabezado = hoja.createRow(0);
                String[] columnas = {"ID", "Nombre", "Precio USD", "Precio Quetzales", "Cantidad", "Tipo", "Marca", "Etiquetas"};
                for (int i = 0; i < columnas.length; i++) {
                    encabezado.createCell(i).setCellValue(columnas[i]);
                }
                guardarLibro(libro, ARCHIVO_EXCEL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

            nuevaFila.createCell(0).setCellValue(ultimaFila + 1); // ID
            nuevaFila.createCell(1).setCellValue(nombre);
            nuevaFila.createCell(2).setCellValue(precioUSD);
            nuevaFila.createCell(3).setCellValue(precioQuetzales);
            nuevaFila.createCell(4).setCellValue(cantidad);
            nuevaFila.createCell(5).setCellValue(tipo);
            nuevaFila.createCell(6).setCellValue(marca);
            nuevaFila.createCell(7).setCellValue(etiquetas);

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
            for (int i = 1; i <= hoja.getLastRowNum(); i++) {
                Row fila = hoja.getRow(i);
                if (fila != null && getCellValueAsInt(fila.getCell(0)) == id) {
                    hoja.removeRow(fila);
                    hoja.shiftRows(i + 1, hoja.getLastRowNum(), -1);
                    break;
                }
            }
            guardarLibro(libro, ARCHIVO_EXCEL);
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
        String[] producto = new String[8];
        for (int i = 0; i < 8; i++) {
            Cell celda = fila.getCell(i);
            if (celda != null) {
                switch (celda.getCellType()) {
                    case NUMERIC:
                        if (i == 2) { // Precio USD
                            producto[i] = String.format("$%.2f", celda.getNumericCellValue());
                        } else if (i == 3) { // Precio Quetzales
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