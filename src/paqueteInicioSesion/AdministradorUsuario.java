/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteInicioSesion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;
import java.security.SecureRandom;
/**
 *
 * @author mynit
 */
public class AdministradorUsuario {
    private static final String ARCHIVO_EXCEL = "resources/usuarios.xlsx";
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

    public boolean emailExiste(String email) {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_USUARIOS);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la fila de encabezado
                Cell emailCell = row.getCell(1); // Asumiendo que el email está en la segunda columna
                if (emailCell != null && emailCell.getStringCellValue().trim().equalsIgnoreCase(email.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
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

  
     private Map<String, String> codigosRecuperacion = new HashMap<>();
    private static final String CARACTERES_ALFANUMERICOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LONGITUD_CODIGO = 8;

    public String generarCodigoRecuperacion(String email) {
        SecureRandom random = new SecureRandom();
        StringBuilder codigo = new StringBuilder(LONGITUD_CODIGO);
        for (int i = 0; i < LONGITUD_CODIGO; i++) {
            codigo.append(CARACTERES_ALFANUMERICOS.charAt(random.nextInt(CARACTERES_ALFANUMERICOS.length())));
        }
        String codigoGenerado = codigo.toString();
        codigosRecuperacion.put(codigoGenerado, email);
        return codigoGenerado;
    }

    public boolean validarCodigoRecuperacion(String codigo, String email) {
        String emailAsociado = codigosRecuperacion.remove(codigo);
        return email.equals(emailAsociado);
    }
    public boolean cambiarContrasena(String email, String nuevaContrasena) {
        if (!esContrasenaSegura(nuevaContrasena)) {
            System.out.println("La nueva contraseña no cumple con los requisitos de seguridad.");
            return false;
        }

        try (FileInputStream fis = new FileInputStream(ARCHIVO_EXCEL);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet(HOJA_USUARIOS);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la fila de encabezado
                Cell emailCell = row.getCell(1);
                if (emailCell != null && emailCell.getStringCellValue().equals(email)) {
                    Cell passwordCell = row.getCell(2);
                    passwordCell.setCellValue(nuevaContrasena); // Aquí deberías aplicar el hash a la nueva contraseña
                    try (FileOutputStream outputStream = new FileOutputStream(ARCHIVO_EXCEL)) {
                        workbook.write(outputStream);
                    }
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
     private boolean esContrasenaSegura(String contrasena) {
        // Verifica que la contraseña tenga al menos 7 caracteres, una mayúscula y un carácter especial
        return contrasena.length() >= 7 &&
               contrasena.matches(".*[A-Z].*") &&
               contrasena.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
     
     
     public String verificarContrasena(String contrasena) {
        StringBuilder mensaje = new StringBuilder("La contraseña debe cumplir las siguientes condiciones:");
        boolean faltaCondicion = false;

        if (contrasena.length() < 7) {
            mensaje.append("\n- Tener al menos 7 caracteres");
            faltaCondicion = true;
        }
        if (!contrasena.matches(".*[A-Z].*")) {
            mensaje.append("\n- Incluir al menos una letra mayúscula");
            faltaCondicion = true;
        }
        if (!contrasena.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            mensaje.append("\n- Incluir al menos un carácter especial");
            faltaCondicion = true;
        }

        return faltaCondicion ? mensaje.toString() : null;
    }
    
    
    
}