/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteInicioSesion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;
/**
 *
 * @author mynit
 */
public class AdministradorUsuario {
    private static final String ARCHIVO_EXCEL = "usuarios.xlsx";
    private static final String HOJA_USUARIOS = "Usuarios";

    public AdministradorUsuario() {
        inicializarArchivoExcel();
    }
    
    public void imprimirContenidoExcelDetallado() {
    try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
         Workbook workbook = WorkbookFactory.create(fis)) {
        Sheet sheet = workbook.getSheet(HOJA_USUARIOS);
        System.out.println("Contenido del Excel:");
        for (Row row : sheet) {
            System.out.print("Fila " + row.getRowNum() + ": ");
            for (int i = 0; i < 3; i++) {
                Cell cell = row.getCell(i);
                String valor = (cell == null) ? "VACÍO" : getCellValueAsString(cell);
                System.out.print("[Col " + i + ": " + valor + "] ");
            }
            System.out.println();
        }
    } catch (IOException e) {
        System.err.println("Error al leer el archivo Excel: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void inicializarArchivoExcel() {
        File file = new File(ARCHIVO_EXCEL);
        if (!file.exists()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(HOJA_USUARIOS);
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Usuario");
                headerRow.createCell(1).setCellValue("Email");
                headerRow.createCell(2).setCellValue("Contraseña");

                try (FileOutputStream outputStream = new FileOutputStream(ARCHIVO_EXCEL)) {
                    workbook.write(outputStream);
                }
                System.out.println("Archivo Excel de usuarios creado: " + ARCHIVO_EXCEL);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo Excel: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean registrarNuevoUsuario(String usuario, String email, String contrasena) {
        if (emailExiste(email)) {
            System.out.println("El email ya está registrado: " + email);
            return false;
        }

        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_USUARIOS);
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);
            newRow.createCell(0).setCellValue(usuario);
            newRow.createCell(1).setCellValue(email);
            newRow.createCell(2).setCellValue(contrasena);

            try (FileOutputStream outputStream = new FileOutputStream(ARCHIVO_EXCEL)) {
                workbook.write(outputStream);
            }
            
            return true;
        } catch (IOException e) {
            
            e.printStackTrace();
            return false;
        }
    }

     public boolean iniciarSesion(String nombreUsuario, String contrasena) {

        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_USUARIOS);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la fila de encabezado
                Cell userCell = row.getCell(0);
                Cell passwordCell = row.getCell(2);
                if (userCell != null && passwordCell != null) {
                    String storedUser = userCell.getStringCellValue().trim();
                    String storedPassword = passwordCell.getStringCellValue().trim();
                    if (storedUser.equals(nombreUsuario.trim()) && storedPassword.equals(contrasena.trim())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar sesión: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Inicio de sesión fallido para: " + nombreUsuario);
        return false;
    }

    public boolean nombreUsuarioExiste(String nombreUsuario) {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_USUARIOS);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la fila de encabezado
                Cell userCell = row.getCell(0);
                if (userCell != null && userCell.getStringCellValue().trim().equals(nombreUsuario.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al verificar nombre de usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean emailExiste(String email) {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_USUARIOS);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                Cell emailCell = row.getCell(1);
                if (emailCell != null && emailCell.getStringCellValue().equals(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al verificar email: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<String[]> listarUsuarios() {
        List<String[]> usuarios = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_USUARIOS);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                String[] usuario = new String[3];
                usuario[0] = getCellValueAsString(row.getCell(0)); // Usuario
                usuario[1] = getCellValueAsString(row.getCell(1)); // Email
                usuario[2] = getCellValueAsString(row.getCell(2)); // Contraseña
                usuarios.add(usuario);
            }
        } catch (IOException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    public void imprimirContenidoExcel() {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_USUARIOS);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.print(getCellValueAsString(cell) + "\t");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}