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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author andre
 */
public class ProductoFavorito implements Serializable {
    private String usuario;
    private String nombre;
    private double costoFobUSD;
    private double costoUSDFinal;
    private double costoQuetzales;
    private double precioVenta;
    private double precioConIVA;
    private double margen;
    
    // Constructor actualizado para incluir usuario
     public ProductoFavorito(String usuario, String nombre, double costoFobUSD, 
            double costoUSDFinal, double costoQuetzales, double precioVenta, 
            double precioConIVA, double margen) {
        this.usuario = usuario;
        this.nombre = nombre; // Corregido: ahora usa el parámetro nombre
        this.costoFobUSD = costoFobUSD;
        this.costoUSDFinal = costoUSDFinal;
        this.costoQuetzales = costoQuetzales;
        this.precioVenta = precioVenta;
        this.precioConIVA = precioConIVA;
        this.margen = margen;
    }
    
    // Constructor vacío
    public ProductoFavorito() {
        this.usuario = "";
        this.nombre = "";
        this.costoFobUSD = 0.0;
        this.costoUSDFinal = 0.0;
        this.costoQuetzales = 0.0;
        this.precioVenta = 0.0;
        this.precioConIVA = 0.0;
        this.margen = 0.0;
    }
    
    // Getters
    public String getUsuario() { return usuario; }
    public String getNombre() { return nombre; }
    public double getCostoFobUSD() { return costoFobUSD; }
    public double getCostoUSDFinal() { return costoUSDFinal; }
    public double getCostoQuetzales() { return costoQuetzales; }
    public double getPrecioVenta() { return precioVenta; }
    public double getPrecioConIVA() { return precioConIVA; }
    public double getMargen() { return margen; }
    
    // Setters
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCostoFobUSD(double costoFobUSD) { this.costoFobUSD = costoFobUSD; }
    public void setCostoUSDFinal(double costoUSDFinal) { this.costoUSDFinal = costoUSDFinal; }
    public void setCostoQuetzales(double costoQuetzales) { this.costoQuetzales = costoQuetzales; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public void setPrecioConIVA(double precioConIVA) { this.precioConIVA = precioConIVA; }
    public void setMargen(double margen) { this.margen = margen; }
}

