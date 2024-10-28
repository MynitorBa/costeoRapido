/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BuscadorInteligente;
import gestionProductos.GestorProductos2;
import productosFavoritos.ProductoFavorito;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author andre
 */
public class SistemaBusquedaIntegrado {
    private static final String ARCHIVO_FAVORITOS = "favoritos_usuarios.dat";
    private static final int MAX_SUGERENCIAS = 5;
    private static final double UMBRAL_SIMILITUD = 0.5;
    private GestorProductos2 gestorProductos;
    private Map<String, Integer> historialBusquedas;
    private Map<Integer, List<String[]>> historialUsuario;
    private Map<String, List<String>> sinonimos;
    private Map<String, List<ProductoFavorito>> favoritosPorUsuario;

    public SistemaBusquedaIntegrado() {
        gestorProductos = new GestorProductos2();
        historialBusquedas = new HashMap<>();
        historialUsuario = new HashMap<>();
        favoritosPorUsuario = new HashMap<>();
        cargarFavoritos();
        inicializarSinonimos();
    }

    // Sistema unificado de favoritos
    public void agregarFavorito(ProductoFavorito favorito) {
        String usuario = favorito.getUsuario();
        List<ProductoFavorito> favoritos = favoritosPorUsuario.computeIfAbsent(usuario, k -> new ArrayList<>());
        boolean existe = favoritos.stream()
                .anyMatch(f -> f.getNombre().equals(favorito.getNombre()));
        if (!existe) {
            favoritos.add(favorito);
            guardarFavoritos();
        }
    }

    public void eliminarFavorito(String usuario, String nombreProducto) {
        List<ProductoFavorito> favoritos = favoritosPorUsuario.getOrDefault(usuario, new ArrayList<>());
        favoritos.removeIf(f -> f.getNombre().equals(nombreProducto));
        favoritosPorUsuario.put(usuario, favoritos);
        guardarFavoritos();
    }

    public List<ProductoFavorito> obtenerFavoritosUsuario(String usuario) {
        return favoritosPorUsuario.getOrDefault(usuario, new ArrayList<>());
    }

    // Búsqueda mejorada con gestión de favoritos
    public List<String[]> buscarProductos(String consulta, int idUsuario) {
        if (consulta.isEmpty()) {
            return new ArrayList<>();
        }
        
        registrarBusqueda(consulta, idUsuario);
        Set<String> terminosBusqueda = expandirConsulta(consulta);
        
        List<String[]> resultados = gestorProductos.obtenerTodosLosProductos().stream()
                .filter(p -> contieneAlgunTermino(p, terminosBusqueda))
                .collect(Collectors.toList());
        
        return enriquecerResultadosConFavoritos(resultados, idUsuario);
    }

    private List<String[]> enriquecerResultadosConFavoritos(List<String[]> resultados, int idUsuario) {
        Set<String> favoritos = obtenerFavoritosUsuario(String.valueOf(idUsuario)).stream()
                .map(ProductoFavorito::getNombre)
                .collect(Collectors.toSet());

        return resultados.stream()
                .map(producto -> {
                    String[] productoEnriquecido = Arrays.copyOf(producto, producto.length + 1);
                    productoEnriquecido[producto.length] = favoritos.contains(producto[1]) ? "true" : "false";
                    return productoEnriquecido;
                })
                .collect(Collectors.toList());
    }

    // Persistencia de favoritos mejorada
    @SuppressWarnings("unchecked")
    private void cargarFavoritos() {
        File archivo = new File(ARCHIVO_FAVORITOS);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                favoritosPorUsuario = (Map<String, List<ProductoFavorito>>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                favoritosPorUsuario = new HashMap<>();
            }
        }
    }

    private void guardarFavoritos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_FAVORITOS))) {
            oos.writeObject(favoritosPorUsuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
