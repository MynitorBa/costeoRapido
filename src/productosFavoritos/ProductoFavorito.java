/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productosFavoritos;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author andre
 */
public class ProductoFavorito implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    private String nombre;
    private String precio;
    private LocalDateTime fechaAgregado;
    private double precioOriginal;
    private double costoFinal;
    private double precioVenta;
    private double margen;

    // Constructor básico
    public ProductoFavorito(String usuario, String nombre, String precio, LocalDateTime fechaAgregado) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaAgregado = fechaAgregado;
    }

    // Constructor completo para cálculos de precios
    public ProductoFavorito(String usuario, String nombre, double precioOriginal, 
                          double costoFinal, double costoQuetzales, 
                          double precioVenta, double precioConIVA, double margen) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.precioOriginal = precioOriginal;
        this.costoFinal = costoFinal;
        this.precioVenta = precioVenta;
        this.margen = margen;
        this.fechaAgregado = LocalDateTime.now();
        this.precio = String.format("Q%.2f", precioConIVA);
    }

    // Getters y setters necesarios
    public String getUsuario() { return usuario; }
    public String getNombre() { return nombre; }
    public String getPrecio() { return precio; }
    public LocalDateTime getFechaAgregado() { return fechaAgregado; }
    public double getPrecioOriginal() { return precioOriginal; }
    public double getCostoFinal() { return costoFinal; }
    public double getPrecioVenta() { return precioVenta; }
    public double getMargen() { return margen; }
}

