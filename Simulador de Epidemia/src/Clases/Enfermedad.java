
package Clases;

/**
 *
 * @author Arnold Steven Navarro
 */
public class Enfermedad {
    
    private String nombre;
    private String tipoTransmision;
    private int tiempoIncubacion;
    private int tiempoVida;
    private double porcentajeMortalidad;

    public Enfermedad(String nombre, String tipoTransmision, int tiempoIncubacion, int tiempoVida, double porcentajeMortalidad) {
        this.nombre = nombre;
        this.tipoTransmision = tipoTransmision;
        this.tiempoIncubacion = tiempoIncubacion;
        this.tiempoVida = tiempoVida;
        this.porcentajeMortalidad = porcentajeMortalidad;
    }


    public String getNombre() {
        return nombre;
    }

    public String getTipoTransmision() {
        return tipoTransmision;
    }

    public int getTiempoIncubacion() {
        return tiempoIncubacion;
    }

    public int getTiempoVida() {
        return tiempoVida;
    }

    public double getPorcentajeMortalidad() {
        return porcentajeMortalidad;
    }

}
