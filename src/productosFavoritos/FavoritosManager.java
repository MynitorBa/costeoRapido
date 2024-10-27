/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productosFavoritos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class FavoritosManager {
    private static final String ARCHIVO_FAVORITOS = "favoritos_usuarios.dat";
    
    // Obtener favoritos específicos de un usuario
    public List<ProductoFavorito> obtenerFavoritosUsuario(String usuario) {
        List<ProductoFavorito> todosFavoritos = obtenerTodosFavoritos();
        List<ProductoFavorito> favoritosUsuario = new ArrayList<>();
        
        for (ProductoFavorito favorito : todosFavoritos) {
            if (favorito.getUsuario().equals(usuario)) {
                favoritosUsuario.add(favorito);
            }
        }
        
        return favoritosUsuario;
    }
    
    // Obtener todos los favoritos
    private List<ProductoFavorito> obtenerTodosFavoritos() {
        List<ProductoFavorito> favoritos = new ArrayList<>();
        File archivo = new File(ARCHIVO_FAVORITOS);
        
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(archivo))) {
                favoritos = (List<ProductoFavorito>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return favoritos;
    }
    
    // Guardar favorito para un usuario específico
    public void guardarFavorito(ProductoFavorito favorito, String usuario) {
        List<ProductoFavorito> todosFavoritos = obtenerTodosFavoritos();
        
        // Verificar si ya existe por nombre y usuario
        boolean existe = todosFavoritos.stream()
            .anyMatch(f -> 
                f.getUsuario() != null &&
                f.getUsuario().equals(usuario) &&
                f.getNombre() != null &&
                f.getNombre().equals(favorito.getNombre())
            );
        
        if (!existe) {
            favorito.setUsuario(usuario); // Asegurar que el favorito tiene el usuario correcto
            todosFavoritos.add(favorito);
            guardarTodosFavoritos(todosFavoritos);
        }
    }
    
    // Método original para eliminar favorito (mantenerlo por compatibilidad)
    public boolean eliminarFavorito(String usuario, String nombreProducto) {
        List<ProductoFavorito> todosFavoritos = obtenerTodosFavoritos();
        boolean removido = todosFavoritos.removeIf(f -> 
            f.getUsuario().equals(usuario) && f.getNombre().equals(nombreProducto));
        
        if (removido) {
            guardarTodosFavoritos(todosFavoritos);
        }
        return removido;
    }
    
    // Nuevo método para eliminar favorito usando el objeto ProductoFavorito
    public void eliminarFavorito(ProductoFavorito favorito, String usuario) throws Exception {
        List<ProductoFavorito> todosFavoritos = obtenerTodosFavoritos();
        boolean removido = todosFavoritos.removeIf(f -> 
            f.getUsuario().equals(usuario) && 
            f.getNombre().equals(favorito.getNombre()));
        
        if (removido) {
            guardarTodosFavoritos(todosFavoritos);
        } else {
            throw new Exception("No se encontró el producto favorito para eliminar");
        }
    }
    
    // Guardar todos los favoritos
    private void guardarTodosFavoritos(List<ProductoFavorito> favoritos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_FAVORITOS))) {
            oos.writeObject(favoritos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
