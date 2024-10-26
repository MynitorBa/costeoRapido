/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AlgoritmoProductoSimilar;
import gestionProductos.GestorProductos2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
/**
 *
 * @author andre
 */
public class BuscadorRecomendador {
    
    private GestorProductos2 gestorProductos;
    private Map<String, Integer> historialBusquedas;
    private Map<Integer, List<String[]>> historialUsuario;
    private Map<Integer, List<String[]>> recomendacionesUsuario;
    private Map<String, List<String>> sinonimos;
    
    private static final int MAX_SUGERENCIAS = 5;
    private static final double UMBRAL_SIMILITUD = 0.5;
    
    public BuscadorRecomendador() {
        gestorProductos = new GestorProductos2();
        historialBusquedas = new HashMap<>();
        historialUsuario = new HashMap<>();
        recomendacionesUsuario = new HashMap<>();
        inicializarSinonimos();
    }

    public void registrarBusquedaUsuario(int idUsuario, String[] producto) {
        historialBusquedas.merge(producto[1].toLowerCase(), 1, Integer::sum);
        historialUsuario.computeIfAbsent(idUsuario, k -> new ArrayList<>()).add(producto);
        actualizarRecomendaciones(idUsuario);
    }

    private double calcularSimilitud(String[] producto1, String[] producto2) {
        double similitud = 0.0;
        
        if (producto1[6].equalsIgnoreCase(producto2[6])) {
            similitud += 0.25;
        }
        
        if (producto1[5].equalsIgnoreCase(producto2[5])) {
            similitud += 0.25;
        }
        
        Set<String> etiquetas1 = new HashSet<>(Arrays.asList(producto1[7].split(",")));
        Set<String> etiquetas2 = new HashSet<>(Arrays.asList(producto2[7].split(",")));
        
        Set<String> interseccion = new HashSet<>(etiquetas1);
        interseccion.retainAll(etiquetas2);
        
        Set<String> union = new HashSet<>(etiquetas1);
        union.addAll(etiquetas2);
        
        double similitudEtiquetas = union.isEmpty() ? 0 : 
            (double) interseccion.size() / union.size();
        similitud += (similitudEtiquetas * 0.30);
        
        double precio1 = Double.parseDouble(producto1[2]);
        double precio2 = Double.parseDouble(producto2[2]);
        double diferenciaPrecio = Math.abs(precio1 - precio2);
        double maxPrecio = Math.max(precio1, precio2);
        double similitudPrecio = 1 - (diferenciaPrecio / maxPrecio);
        similitud += (similitudPrecio * 0.20);
        
        return similitud;
    }

    private void actualizarRecomendaciones(int idUsuario) {
        List<String[]> historialUsuarioActual = historialUsuario.getOrDefault(idUsuario, new ArrayList<>());
        if (historialUsuarioActual.isEmpty()) {
            return;
        }

        List<String[]> todosLosProductos = gestorProductos.obtenerTodosLosProductos();
        Map<String, Double> puntuacionProductos = new HashMap<>();
        
        for (String[] productoHistorial : historialUsuarioActual) {
            for (String[] productoCandidate : todosLosProductos) {
                if (!contieneProducto(historialUsuarioActual, productoCandidate[0])) {
                    double similitud = calcularSimilitud(productoHistorial, productoCandidate);
                    puntuacionProductos.merge(productoCandidate[0], 
                                           similitud, 
                                           Double::sum);
                }
            }
        }

        List<String[]> recomendaciones = puntuacionProductos.entrySet().stream()
            .filter(entry -> entry.getValue() >= UMBRAL_SIMILITUD)
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(MAX_SUGERENCIAS)
            .map(entry -> encontrarProductoPorId(todosLosProductos, entry.getKey()))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        recomendacionesUsuario.put(idUsuario, recomendaciones);
    }

    public List<String[]> obtenerRecomendaciones(int idUsuario) {
        return recomendacionesUsuario.getOrDefault(idUsuario, new ArrayList<>());
    }

    public List<String[]> obtenerProductosSimilares(String[] producto) {
        List<String[]> todosLosProductos = gestorProductos.obtenerTodosLosProductos();
        return todosLosProductos.stream()
            .filter(p -> !p[0].equals(producto[0]))
            .filter(p -> calcularSimilitud(producto, p) >= UMBRAL_SIMILITUD)
            .sorted((p1, p2) -> Double.compare(
                calcularSimilitud(producto, p2),
                calcularSimilitud(producto, p1)))
            .limit(MAX_SUGERENCIAS)
            .collect(Collectors.toList());
    }

    private boolean contieneProducto(List<String[]> productos, String id) {
        return productos.stream().anyMatch(p -> p[0].equals(id));
    }

    private String[] encontrarProductoPorId(List<String[]> productos, String id) {
        return productos.stream()
            .filter(p -> p[0].equals(id))
            .findFirst()
            .orElse(null);
    }

    public Map<String, Integer> obtenerEstadisticasUsuario(int idUsuario) {
        Map<String, Integer> estadisticas = new HashMap<>();
        List<String[]> historial = historialUsuario.getOrDefault(idUsuario, new ArrayList<>());
        
        historial.stream()
            .map(p -> p[5])
            .forEach(tipo -> estadisticas.merge("tipo_" + tipo, 1, Integer::sum));
        
        historial.stream()
            .map(p -> p[6])
            .forEach(marca -> estadisticas.merge("marca_" + marca, 1, Integer::sum));
        
        return estadisticas;
    }

    public List<String> obtenerTendencias() {
        return historialBusquedas.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(MAX_SUGERENCIAS)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    private void inicializarSinonimos() {
    sinonimos = new HashMap<>();
    sinonimos.put("cámaras de vigilancia", Arrays.asList("cámaras IP", "cámaras analógicas", "cámaras WiFi"));
    sinonimos.put("sistemas de almacenamiento", Arrays.asList("discos duros", "discos de estado sólido", "memorias USB", "memorias MicroSD"));
    sinonimos.put("accesorios CCTV", Arrays.asList("cables", "conectores", "herramientas", "testers"));
    sinonimos.put("control de acceso y seguridad", Arrays.asList("barreras", "cercos eléctricos", "alarmas"));
    sinonimos.put("sistemas de red y comunicación", Arrays.asList("routers", "switches", "antenas"));
}
    
    private Set<String> expandirConsulta(String consulta) {
    Set<String> terminos = new HashSet<>();
    String consultaLower = consulta.toLowerCase();
    terminos.add(consultaLower);
    
    // Buscar coincidencias parciales en las claves de sinónimos
    for (Map.Entry<String, List<String>> entry : sinonimos.entrySet()) {
        String clave = entry.getKey().toLowerCase();
        // Si la consulta contiene la palabra clave o viceversa
        if (consultaLower.contains(clave) || clave.contains(consultaLower)) {
            terminos.addAll(entry.getValue());
            terminos.add(clave);
        }
        // Buscar en los sinónimos también
        for (String sinonimo : entry.getValue()) {
            if (consultaLower.contains(sinonimo.toLowerCase()) || 
                sinonimo.toLowerCase().contains(consultaLower)) {
                terminos.addAll(entry.getValue());
                terminos.add(clave);
                break;
            }
        }
    }
    
    return terminos;
}
}
    

