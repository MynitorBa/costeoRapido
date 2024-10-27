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
    private static final String ARCHIVO_FAVORITOS = "favoritos.dat";
    
    public List<ProductoFavorito> obtenerFavoritos() {
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
    
    public void guardarFavorito(ProductoFavorito favorito, String favorito11) {
        List<ProductoFavorito> favoritos = obtenerFavoritos();
        // Verificar si ya existe por nombre
        boolean existe = favoritos.stream()
            .anyMatch(f -> f.getNombre().equals(favorito.getNombre()));
        
        if (!existe) {
            favoritos.add(favorito);
            guardarFavoritos(favoritos);
        }
    }
    
    private void guardarFavoritos(List<ProductoFavorito> favoritos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_FAVORITOS))) {
            oos.writeObject(favoritos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
