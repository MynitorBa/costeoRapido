/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Historial;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class HistorialManager {
    static final String HISTORIAL_DIR = "historial";

    public HistorialManager() {
        // Crear directorio si no existe
        File dir = new File(HISTORIAL_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void guardarCosteo(HistorialEntry entrada) {
        String fileName = HISTORIAL_DIR + File.separator + entrada.getUsuario() + "_historial.dat";
        List<HistorialEntry> historial = cargarHistorial(entrada.getUsuario());
        historial.add(entrada);

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            oos.writeObject(historial);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<HistorialEntry> cargarHistorial(String usuario) {
        String fileName = HISTORIAL_DIR + File.separator + usuario + "_historial.dat";
        List<HistorialEntry> historial = new ArrayList<>();

        File file = new File(fileName);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fileName))) {
                historial = (List<HistorialEntry>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return historial;
    }
}