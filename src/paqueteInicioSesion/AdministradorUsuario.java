/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteInicioSesion;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author mynit
 */
public class AdministradorUsuario {
    private Map<String, Usuario> usuarios;

    public AdministradorUsuario() {
        this.usuarios = new HashMap<>();
    }

    public boolean registrarNuevoUsuario(String nombre, String email, String contrasena) {
        // Verificar si el email ya está en uso
        if (usuarios.values().stream().anyMatch(u -> u.getEmail().equals(email))) {
            return false; // El email ya está registrado
        }

        Usuario nuevoUsuario = new Usuario(nombre, email, contrasena);
        usuarios.put(nuevoUsuario.getId(), nuevoUsuario);
        return true;
    }

    public boolean iniciarSesion(String email, String contrasena) {
        Usuario usuario = usuarios.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (usuario != null) {
            return usuario.verificarContrasena(contrasena);
        }
        return false;
    }

    public Usuario obtenerUsuario(String id) {
        return usuarios.get(id);
    }

    public boolean modificarUsuario(String id, String nuevoNombre, String nuevoEmail) {
        Usuario usuario = usuarios.get(id);
        if (usuario != null) {
            // Verificar si el nuevo email ya está en uso por otro usuario
            if (!usuario.getEmail().equals(nuevoEmail) &&
                usuarios.values().stream().anyMatch(u -> u.getEmail().equals(nuevoEmail))) {
                return false; // El nuevo email ya está en uso
            }
            usuario.setNombre(nuevoNombre);
            usuario.setEmail(nuevoEmail);
            return true;
        }
        return false;
    }

    public boolean eliminarUsuario(String id) {
        return usuarios.remove(id) != null;
    }

    public boolean cambiarContrasena(String id, String nuevaContrasena) {
        Usuario usuario = usuarios.get(id);
        if (usuario != null) {
            usuario.cambiarContrasena(nuevaContrasena);
            return true;
        }
        return false;
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios.values());
    }
    
}
