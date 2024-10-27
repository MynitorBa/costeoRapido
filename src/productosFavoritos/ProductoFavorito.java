/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productosFavoritos;

import java.io.Serializable;

/**
 *
 * @author andre
 */
public class ProductoFavorito implements Serializable {
    private String nombre;
    private double costoFobUSD;
    private double costoUSDFinal;
    private double costoQuetzales;
    private double precioVenta;
    private double precioConIVA;
    private double margen;
    
    // Constructor completo
    public ProductoFavorito(String nombre, double costoFobUSD, double costoUSDFinal, 
                          double costoQuetzales, double precioVenta, double precioConIVA, 
                          double margen) {
        this.nombre = nombre;
        this.costoFobUSD = costoFobUSD;
        this.costoUSDFinal = costoUSDFinal;
        this.costoQuetzales = costoQuetzales;
        this.precioVenta = precioVenta;
        this.precioConIVA = precioConIVA;
        this.margen = margen;
    }
    
    // Constructor vacío
    public ProductoFavorito() {
        this.nombre = "";
        this.costoFobUSD = 0.0;
        this.costoUSDFinal = 0.0;
        this.costoQuetzales = 0.0;
        this.precioVenta = 0.0;
        this.precioConIVA = 0.0;
        this.margen = 0.0;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public double getCostoFobUSD() { return costoFobUSD; }
    public double getCostoUSDFinal() { return costoUSDFinal; }
    public double getCostoQuetzales() { return costoQuetzales; }
    public double getPrecioVenta() { return precioVenta; }
    public double getPrecioConIVA() { return precioConIVA; }
    public double getMargen() { return margen; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCostoFobUSD(double costoFobUSD) { this.costoFobUSD = costoFobUSD; }
    public void setCostoUSDFinal(double costoUSDFinal) { this.costoUSDFinal = costoUSDFinal; }
    public void setCostoQuetzales(double costoQuetzales) { this.costoQuetzales = costoQuetzales; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public void setPrecioConIVA(double precioConIVA) { this.precioConIVA = precioConIVA; }
    public void setMargen(double margen) { this.margen = margen; }
}
