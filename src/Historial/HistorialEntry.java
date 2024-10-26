/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Historial;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author andre
 */
public class HistorialEntry implements Serializable {
    private String usuario;
    private String nombreProducto;
    private double costoFobUSD;
    private double costoUSDFinal;
    private double costoQuetzales;
    private double precioVenta;
    private double precioConIVA;
    private double margen;
    private LocalDateTime fechaCalculo;

    public HistorialEntry(String usuario, String nombreProducto, double costoFobUSD,
                         double costoUSDFinal, double costoQuetzales, double precioVenta,
                         double precioConIVA, double margen) {
        this.usuario = usuario;
        this.nombreProducto = nombreProducto;
        this.costoFobUSD = costoFobUSD;
        this.costoUSDFinal = costoUSDFinal;
        this.costoQuetzales = costoQuetzales;
        this.precioVenta = precioVenta;
        this.precioConIVA = precioConIVA;
        this.margen = margen;
        this.fechaCalculo = LocalDateTime.now();
    }

    // Getters
    public String getUsuario() { return usuario; }
    public String getNombreProducto() { return nombreProducto; }
    public double getCostoFobUSD() { return costoFobUSD; }
    public double getCostoUSDFinal() { return costoUSDFinal; }
    public double getCostoQuetzales() { return costoQuetzales; }
    public double getPrecioVenta() { return precioVenta; }
    public double getPrecioConIVA() { return precioConIVA; }
    public double getMargen() { return margen; }
    public LocalDateTime getFechaCalculo() { return fechaCalculo; }
}