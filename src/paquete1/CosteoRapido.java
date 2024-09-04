/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paquete1;

/**
 *
 * @author andre
 */
public class CosteoRapido {

    // Método para calcular el costo total del producto importado
    public double calcularCostoTotal(double costoBase, double impuestos, double flete, double margenGanancia) {
        // Aplica impuestos, flete y margen de ganancia al costo base
        double costoConImpuestos = costoBase + aplicarImpuestos(costoBase, impuestos);
        double costoConFlete = costoConImpuestos + calcularFlete(flete);
        return aplicarMargenGanancia(costoConFlete, margenGanancia);
    }

    // Método para aplicar impuestos al costo base según la categoría del producto
    private double aplicarImpuestos(double costoBase, double tasaImpuestos) {
        return costoBase * (tasaImpuestos / 100);
    }

    // Método para estimar el costo de flete basado en origen y características del producto
    private double calcularFlete(double costoFlete) {
        return costoFlete;
    }

    // Método para añadir el margen de ganancia al costo calculado
    private double aplicarMargenGanancia(double costo, double margenGanancia) {
        return costo + (costo * (margenGanancia / 100));
    }

    public static void main(String[] args) {
        CosteoRapido costeoRapido = new CosteoRapido();
        double costoBase = 1000.0; // Costo base del producto
        double impuestos = 18.0;   // Tasa de impuestos en porcentaje
        double flete = 150.0;      // Costo estimado de flete
        double margenGanancia = 20.0; // Margen de ganancia en porcentaje

        double costoTotal = costeoRapido.calcularCostoTotal(costoBase, impuestos, flete, margenGanancia);
        System.out.println("El costo total del producto es: " + costoTotal);
    }
}
