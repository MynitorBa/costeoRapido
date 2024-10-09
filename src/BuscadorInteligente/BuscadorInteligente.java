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

    public BuscadorInteligente() {
        gestorProductos = new GestorProductos2();
    }

    public List<String[]> procesarConsulta(String consulta) {
        if (consulta.isEmpty()) {
            return new ArrayList<>();
        }
        List<String[]> todosLosProductos = gestorProductos.obtenerTodosLosProductos();
        return todosLosProductos.stream()
                .filter(p -> contieneConsulta(p, consulta))
                .collect(Collectors.toList());
    }

    private boolean contieneConsulta(String[] producto, String consulta) {
        String consultaLower = consulta.toLowerCase();
        return producto[1].toLowerCase().contains(consultaLower) || // Nombre
               producto[5].toLowerCase().contains(consultaLower) || // Tipo
               producto[6].toLowerCase().contains(consultaLower) || // Marca
               producto[7].toLowerCase().contains(consultaLower);   // Etiquetas
    }

    public List<String[]> filtrarResultados(List<String[]> resultados, String filtro) {
        // Implementa lógica de filtrado adicional si es necesario
        return resultados;
    }

    public List<String[]> ordenarResultados(List<String[]> resultados, String consulta) {
        resultados.sort((a, b) -> {
            int relevanciaA = calcularRelevancia(a, consulta);
            int relevanciaB = calcularRelevancia(b, consulta);
            return Integer.compare(relevanciaB, relevanciaA);
        });
        return resultados;
    }

    private int calcularRelevancia(String[] producto, String consulta) {
        int relevancia = 0;
        String consultaLower = consulta.toLowerCase();
        if (producto[1].toLowerCase().contains(consultaLower)) relevancia += 3; // Nombre
        if (producto[5].toLowerCase().contains(consultaLower)) relevancia += 2; // Tipo
        if (producto[6].toLowerCase().contains(consultaLower)) relevancia += 2; // Marca
        if (producto[7].toLowerCase().contains(consultaLower)) relevancia += 1; // Etiquetas
        return relevancia;
    }

    public List<String> obtenerSugerencias(String consulta) {
        List<String[]> resultados = procesarConsulta(consulta);
        resultados = ordenarResultados(resultados, consulta);
        return resultados.stream()
                .limit(5)
                .map(p -> p[1]) // Devuelve solo los nombres de los productos
                .collect(Collectors.toList());
    }
}
   