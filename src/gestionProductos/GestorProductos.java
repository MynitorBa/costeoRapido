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

/**
 *
 * @author andre
 */
public class GestorProductos {
    private static final String ARCHIVO_EXCEL = "C:\\\\Users\\\\andre\\\\OneDrive\\\\Documents\\\\nruhhhh\\\\costeoRapido\\\\resources\\\\productos.xlsx";
    private static final String HOJA_PRODUCTOS = "Productos";

    public GestorProductos() {
        inicializarArchivoExcel();
    }

    private void inicializarArchivoExcel() {
        File file = new File(ARCHIVO_EXCEL);
        if (!file.exists()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(HOJA_PRODUCTOS);
                Row headerRow = sheet.createRow(0);
                String[] headers = {"ID", "Nombre", "Precio USD", "Precio Quetzales", "Cantidad en Stock", "Tipo de Producto", "Marca", "Etiquetas"};
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }
                try (FileOutputStream outputStream = new FileOutputStream(ARCHIVO_EXCEL)) {
                    workbook.write(outputStream);
                }
                System.out.println("Archivo Excel de productos creado: " + ARCHIVO_EXCEL);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo Excel: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void agregarProducto(String nombre, double precioUSD, double precioQuetzales, int cantidad, String tipo, String marca, String etiquetas) {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_PRODUCTOS);
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);
            
            newRow.createCell(0).setCellValue(lastRowNum + 1); // ID
            newRow.createCell(1).setCellValue(nombre);
            newRow.createCell(2).setCellValue(precioUSD);
            newRow.createCell(3).setCellValue(precioQuetzales);
            newRow.createCell(4).setCellValue(cantidad);
            newRow.createCell(5).setCellValue(tipo);
            newRow.createCell(6).setCellValue(marca);
            newRow.createCell(7).setCellValue(etiquetas);

            try (FileOutputStream outputStream = new FileOutputStream(ARCHIVO_EXCEL)) {
                workbook.write(outputStream);
            }
            System.out.println("Producto agregado con éxito.");
        } catch (IOException e) {
            System.err.println("Error al agregar producto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<String[]> obtenerTodosLosProductos() {
        List<String[]> productos = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_PRODUCTOS);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                String[] producto = new String[8];
                for (int i = 0; i < 8; i++) {
                    Cell cell = row.getCell(i);
                    producto[i] = cell == null ? "" : cell.toString();
                }
                productos.add(producto);
            }
        } catch (IOException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    public void editarProducto(int id, String nombre, double precioUSD, double precioQuetzales, int cantidad, String tipo, String marca, String etiquetas) {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_PRODUCTOS);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                if (row.getCell(0).getNumericCellValue() == id) {
                    row.getCell(1).setCellValue(nombre);
                    row.getCell(2).setCellValue(precioUSD);
                    row.getCell(3).setCellValue(precioQuetzales);
                    row.getCell(4).setCellValue(cantidad);
                    row.getCell(5).setCellValue(tipo);
                    row.getCell(6).setCellValue(marca);
                    row.getCell(7).setCellValue(etiquetas);
                    break;
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(ARCHIVO_EXCEL)) {
                workbook.write(outputStream);
            }
            System.out.println("Producto editado con éxito.");
        } catch (IOException e) {
            System.err.println("Error al editar producto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarProducto(int id) {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_PRODUCTOS);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null && row.getCell(0).getNumericCellValue() == id) {
                    sheet.removeRow(row);
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                    break;
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(ARCHIVO_EXCEL)) {
                workbook.write(outputStream);
            }
            System.out.println("Producto eliminado con éxito.");
        } catch (IOException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String[] buscarProductoPorId(int id) {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_PRODUCTOS);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                if (row.getCell(0).getNumericCellValue() == id) {
                    String[] producto = new String[8];
                    for (int i = 0; i < 8; i++) {
                        Cell cell = row.getCell(i);
                        producto[i] = cell == null ? "" : cell.toString();
                    }
                    return producto;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}