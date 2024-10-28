/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BuscadorInteligente;
import gestionProductos.GestorProductos2;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author andre
 */
public class BuscadorInteligente {
    private GestorProductos2 gestorProductos;
    private static final int MAX_SUGERENCIAS = 5;
    private Map<String, Integer> historialBusquedas;
    private Map<String, List<String>> sinonimos;

    public BuscadorInteligente() {
        gestorProductos = new GestorProductos2();
        historialBusquedas = new HashMap<>();
        inicializarSinonimos();
    }

    private void inicializarSinonimos() {
        sinonimos = new HashMap<>();
        sinonimos.put("laptop", Arrays.asList("portátil", "notebook", "computadora portátil"));
        sinonimos.put("celular", Arrays.asList("móvil", "smartphone", "teléfono"));
        sinonimos.put("tv", Arrays.asList("televisor", "televisión", "smart tv"));
    }

    public List<String[]> procesarConsulta(String consulta) {
        if (consulta.isEmpty()) {
            return new ArrayList<>();
        }
        
        registrarBusqueda(consulta);
        Set<String> terminosBusqueda = expandirConsulta(consulta);
        
        List<String[]> todosLosProductos = gestorProductos.obtenerTodosLosProductos();
        return todosLosProductos.stream()
                .filter(p -> contieneAlgunTermino(p, terminosBusqueda))
                .collect(Collectors.toList());
    }

    private boolean contieneAlgunTermino(String[] producto, Set<String> terminos) {
        return terminos.stream()
                .anyMatch(termino -> contieneConsulta(producto, termino));
    }

    private Set<String> expandirConsulta(String consulta) {
        Set<String> terminos = new HashSet<>();
        terminos.add(consulta.toLowerCase());
        
        for (Map.Entry<String, List<String>> entry : sinonimos.entrySet()) {
            if (consulta.toLowerCase().contains(entry.getKey())) {
                terminos.addAll(entry.getValue());
            }
        }
        
        return terminos;
    }

    private boolean contieneConsulta(String[] producto, String consulta) {
        String consultaLower = consulta.toLowerCase();
        return producto[1].toLowerCase().contains(consultaLower) ||
               producto[5].toLowerCase().contains(consultaLower) ||
               producto[6].toLowerCase().contains(consultaLower) ||
               producto[7].toLowerCase().contains(consultaLower) ||
               coincideConPrecio(producto[2], consulta);
    }

    private boolean coincideConPrecio(String precio, String consulta) {
        try {
            if (consulta.startsWith("menor") || consulta.startsWith("<")) {
                double maxPrecio = extraerNumero(consulta);
                return Double.parseDouble(precio) <= maxPrecio;
            } else if (consulta.startsWith("mayor") || consulta.startsWith(">")) {
                double minPrecio = extraerNumero(consulta);
                return Double.parseDouble(precio) >= minPrecio;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double extraerNumero(String consulta) {
        return Double.parseDouble(consulta.replaceAll("[^0-9.]", ""));
    }

    public List<String[]> filtrarResultados(List<String[]> resultados, Map<String, String> filtros) {
        return resultados.stream()
                .filter(producto -> cumpleFiltros(producto, filtros))
                .collect(Collectors.toList());
    }

    private boolean cumpleFiltros(String[] producto, Map<String, String> filtros) {
        return filtros.entrySet().stream()
                .allMatch(filtro -> {
                    switch (filtro.getKey().toLowerCase()) {
                        case "marca":
                            return producto[6].toLowerCase().equals(filtro.getValue().toLowerCase());
                        case "tipo":
                            return producto[5].toLowerCase().equals(filtro.getValue().toLowerCase());
                        case "precio_min":
                            return Double.parseDouble(producto[2]) >= Double.parseDouble(filtro.getValue());
                        case "precio_max":
                            return Double.parseDouble(producto[2]) <= Double.parseDouble(filtro.getValue());
                        default:
                            return true;
                    }
                });
    }
    
    public List<String[]> ordenarResultados(List<String[]> resultados, String consulta, String criterioOrden) {
    Comparator<String[]> comparador;
    
    switch (criterioOrden.toLowerCase()) {
        case "precio_asc":
            comparador = (p1, p2) -> {
                double precio1 = Double.parseDouble(p1[2]);
                double precio2 = Double.parseDouble(p2[2]);
                return Double.compare(precio1, precio2);
            };
            break;
        case "precio_desc":
            comparador = (p1, p2) -> {
                double precio1 = Double.parseDouble(p1[2]);
                double precio2 = Double.parseDouble(p2[2]);
                return Double.compare(precio2, precio1);
            };
            break;
        case "nombre":
            comparador = (p1, p2) -> p1[1].toLowerCase().compareTo(p2[1].toLowerCase());
            break;
        case "relevancia":
        default:
            comparador = (p1, p2) -> Integer.compare(
                calcularRelevancia(p2, consulta),
                calcularRelevancia(p1, consulta)
            );
    }
    
    ArrayList<String[]> resultadosOrdenados = new ArrayList<>(resultados);
    resultadosOrdenados.sort(comparador);
    return resultadosOrdenados;
}


    private int calcularRelevancia(String[] producto, String consulta) {
        int relevancia = 0;
        String consultaLower = consulta.toLowerCase();
        
        if (producto[1].toLowerCase().contains(consultaLower)) relevancia += 3;
        if (producto[5].toLowerCase().contains(consultaLower)) relevancia += 2;
        if (producto[6].toLowerCase().contains(consultaLower)) relevancia += 2;
        if (producto[7].toLowerCase().contains(consultaLower)) relevancia += 1;
        
        relevancia += historialBusquedas.getOrDefault(producto[1].toLowerCase(), 0);
        
        if (producto[1].toLowerCase().equals(consultaLower)) relevancia += 5;
        
        return relevancia;
    }

    private void registrarBusqueda(String consulta) {
        historialBusquedas.merge(consulta.toLowerCase(), 1, Integer::sum);
    }

    public List<String> obtenerSugerencias(String consulta) {
        if (consulta.isEmpty()) {
            return obtenerBusquedasPopulares();
        }

        List<String[]> resultados = procesarConsulta(consulta);
        resultados = ordenarResultados(resultados, consulta, "relevancia");
        
        return resultados.stream()
                .limit(MAX_SUGERENCIAS)
                .map(p -> p[1])
                .collect(Collectors.toList());
    }

    public List<String> obtenerBusquedasPopulares() {
        return historialBusquedas.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(MAX_SUGERENCIAS)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Map<String, List<String>> obtenerSinonimos() {
        return new HashMap<>(sinonimos);
    }

    public void agregarSinonimos(String termino, List<String> nuevosSinonimos) {
        sinonimos.computeIfAbsent(termino.toLowerCase(), k -> new ArrayList<>())
                .addAll(nuevosSinonimos);
    }
    
    public List<String[]> obtenerTodosLosProductos() {
    // This should return all products from your database
    // Implementation depends on your database structure
    return procesarConsulta(""); // Empty query to get all products
}
    
}