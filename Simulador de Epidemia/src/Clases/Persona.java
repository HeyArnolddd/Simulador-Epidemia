
package Clases;

/**
 *
 * @author Arnold Steven Navarro
 */
public class Persona {

    private int id;
    private int edad;
    private boolean enfermo;
    private boolean recuperado;
    private boolean vacunado;
    private boolean vivo;

    public Persona(int id, int edad) {
        this.id = id;
        this.edad = edad;
        this.enfermo = false;
        this.recuperado = false;
        this.vacunado = false;
        this.vivo = true;
    }

 
    public String estado() {
     if (!vivo) return "Fallecido";
        if (enfermo) return "Enfermo";
        if (recuperado) return "Recuperado";
        if (vacunado) return "Vacunado";
        return "Sano";

    }

    public void reiniciarEstado() {
        this.enfermo = false;
        this.recuperado = false;
        this.vacunado = false;
        this.vivo = true;
    }

   @Override
    public String toString() {
       return "Persona " + id + (vivo ? "" : " (Fallecido)");

    }
    

    public int getId() {
        return id;
    }

    public int getEdad() {
        return edad;
    }

    public boolean isEnfermo() {
        return enfermo;
    }

    public void setEnfermo(boolean enfermo) {
        this.enfermo = enfermo;
    }

    public boolean isRecuperado() {
        return recuperado;
    }

    public void setRecuperado(boolean recuperado) {
        this.recuperado = recuperado;
    }

    public boolean isVacunado() {
        return vacunado;
    }

    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
