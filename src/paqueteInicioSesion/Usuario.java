/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteInicioSesion;
import java.util.Date;
import java.util.UUID;
import java.util.Base64;
/**
 *
 * @author mynit
 */
public class Usuario {
    private String id;
    private String nombre;
    private String email;
    private String contrasenaHash;
    private Date fechaRegistro;

    public Usuario(String nombre, String email, String contrasena) {
        this.id = generarId();
        this.nombre = nombre;
        this.email = email;
        this.contrasenaHash = hashContrasena(contrasena);
        this.fechaRegistro = new Date();
    }

    private String generarId() {
        // En una implementación real, esto podría ser un UUID o un valor autoincrementado de la base de datos
        return UUID.randomUUID().toString();
    }

    private String hashContrasena(String contrasena) {
        // En una implementación real, usarías un algoritmo de hash seguro como bcrypt
        // Este es un ejemplo simplificado y NO SEGURO, solo para demostración
        return Base64.getEncoder().encodeToString(contrasena.getBytes());
    }

    public boolean verificarContrasena(String contrasena) {
        return this.contrasenaHash.equals(hashContrasena(contrasena));
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public Date getFechaRegistro() { return fechaRegistro; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }

    public void cambiarContrasena(String nuevaContrasena) {
        this.contrasenaHash = hashContrasena(nuevaContrasena);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
    
}
