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

    public Usuario(String nombre, String email, String contrasena) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.email = email;
        this.contrasenaHash = hashContrasena(contrasena);
    }

    // Constructor adicional para crear un Usuario desde los datos almacenados
    public Usuario(String id, String nombre, String email, String contrasenaHash) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenaHash = contrasenaHash;
    }

    private String hashContrasena(String contrasena) {
        // Aquí deberías implementar un algoritmo de hash real
        // Por ahora, simplemente devolveremos la contraseña como está
        return contrasena;
    }

    public boolean verificarContrasena(String contrasena) {
        return this.contrasenaHash.equals(hashContrasena(contrasena));
    }

    // Getters y setters
    public String getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasenaHash() { return contrasenaHash; }

    public void cambiarContrasena(String nuevaContrasena) {
        this.contrasenaHash = hashContrasena(nuevaContrasena);
    }

    @Override
    public String toString() {
        return "Usuario{id='" + id + "', nombre='" + nombre + "', email='" + email + "'}";
    }
    
}
