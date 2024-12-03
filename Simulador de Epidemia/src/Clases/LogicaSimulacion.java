package Clases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 *
 * @author Arnold Steven Navarro
 */
public class LogicaSimulacion {

    private BaseGrafo grafo;
    private Enfermedad enfermedad;
    private Random random;
    private Map<Persona, Integer> contadorIncubacion;

    public LogicaSimulacion(BaseGrafo grafo, Enfermedad enfermedad) {
        this.grafo = grafo;
        this.enfermedad = enfermedad;
        this.random = new Random();
        this.contadorIncubacion = new HashMap<>();

    }

    private void pararHilo(int milisegundos) {

        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void iniciarSimulacion(int dias) {
       for (int dia = 0; dia < dias; dia++) {
            System.out.println("\n--- Dia " + (dia + 1) + " ---");
            propagarEnfermedad();
            actualizarEstadoPersonas();
            mostrarEstadoPoblacion();
            pararHilo(500);
        }
    }
    
    public void iniciarSimulacion2(int dias) {
       for (int dia = 0; dia < dias; dia++) {
           
            propagarEnfermedad2();
            actualizarEstadoPersonas2();
            
       //     pararHilo(1000);
        }
         mostrarEstadoPoblacion();
    }

    public void propagarEnfermedad() {

        List<Persona> nuevasInfecciones = new ArrayList<>();
        double probabilidadContagio=0;
        
        for (Persona p : grafo.obtenerPersonas()) {
            if (p.isEnfermo() && p.isVivo()) {
                for (Persona vecino : grafo.obtenerConexiones(p)) {
                    if (!vecino.isEnfermo() && !vecino.isRecuperado() && vecino.isVivo() && !vecino.isVacunado()) {
                        Random r = new Random();
                        
                        if (enfermedad.getTipoTransmision().equalsIgnoreCase("contacto")) {

                            probabilidadContagio = 0.15 * r.nextDouble();

                        } else if (enfermedad.getTipoTransmision().equalsIgnoreCase("respiratorio")) {
                            probabilidadContagio = 0.3 * r.nextDouble();

                        }
                         if (random.nextDouble() < probabilidadContagio) {
                            nuevasInfecciones.add(vecino);
                        }
                    }
                    
                }
            }

        }
        
        
        for (Persona p : nuevasInfecciones) {
            p.setEnfermo(true);
            contadorIncubacion.put(p, 0);
            System.out.println(p + " ha sido infectada.");
        }
    }
    public void propagarEnfermedad2() {

        List<Persona> nuevasInfecciones = new ArrayList<>();
        double probabilidadContagio=0;
        
        for (Persona p : grafo.obtenerPersonas()) {
            if (p.isEnfermo() && p.isVivo()) {
                for (Persona vecino : grafo.obtenerConexiones(p)) {
                    if (!vecino.isEnfermo() && !vecino.isRecuperado() && vecino.isVivo() && !vecino.isVacunado()) {
                        Random r = new Random();
                        
                        if (enfermedad.getTipoTransmision().equalsIgnoreCase("contacto")) {

                            probabilidadContagio = 0.15 * r.nextDouble();

                        } else if (enfermedad.getTipoTransmision().equalsIgnoreCase("respiratorio")) {
                            probabilidadContagio = 0.3 * r.nextDouble();

                        }
                         if (random.nextDouble() < probabilidadContagio) {
                            nuevasInfecciones.add(vecino);
                        }
                    }
                    
                }
            }

        }
        
        
        for (Persona p : nuevasInfecciones) {
            p.setEnfermo(true);
            contadorIncubacion.put(p, 0);
           
        }
    }
    
    private void actualizarEstadoPersonas() {
        
        List<Persona> personasAEliminar = new ArrayList<>();

    
        for (Persona p : contadorIncubacion.keySet()) {
   
            int diasEnfermo = contadorIncubacion.get(p) + 1;
            contadorIncubacion.put(p, diasEnfermo);

            if (diasEnfermo >= enfermedad.getTiempoVida()) {

                if (random.nextDouble()< enfermedad.getPorcentajeMortalidad()) {

                    p.setVivo(false);
                    p.setEnfermo(false);
                    System.out.println(p + " ha fallecido.");
                } else {

                    p.setRecuperado(true);
                    p.setEnfermo(false);
                    System.out.println(p + " se ha recuperado.");
                }

                personasAEliminar.add(p);
            }
        }
    
        for (Persona p : personasAEliminar) {
            contadorIncubacion.remove(p);
        }
    }
    
    private void actualizarEstadoPersonas2() {
        
        List<Persona> personasAEliminar = new ArrayList<>();

    
        for (Persona p : contadorIncubacion.keySet()) {
   
            int diasEnfermo = contadorIncubacion.get(p) + 1;
            contadorIncubacion.put(p, diasEnfermo);

            if (diasEnfermo >= enfermedad.getTiempoVida()) {

                if (random.nextDouble()< enfermedad.getPorcentajeMortalidad()) {

                    p.setVivo(false);
                    p.setEnfermo(false);
                  
                } else {

                    p.setRecuperado(true);
                    p.setEnfermo(false);
                 
                }

                personasAEliminar.add(p);
            }
        }
    
        for (Persona p : personasAEliminar) {
            contadorIncubacion.remove(p);
        }
    }
    
    public void vacunarPoblacion(double porcentajeVacunados) {

        reiniciarEstadoPersonas();

        List<Persona> personas = new ArrayList<>(grafo.obtenerPersonas());
        int totalVacunados = (int) (personas.size() * porcentajeVacunados);
        Collections.shuffle(personas);

        for (int i = 0; i < totalVacunados; i++) {
            personas.get(i).setVacunado(true);
            System.out.println(personas.get(i) + " ha sido vacunada.");
        }
    }

    public void aplicarCuarentena(double porcentajeReduccion) {
        System.out.println("Conexiones antes de la cuarentena:");
        mostrarNumeroConexiones();

        for (Persona p : grafo.obtenerPersonas()) {
            List<Persona> conexiones = new ArrayList<>(grafo.obtenerConexiones(p));
            int conexionesARemover = (int) (conexiones.size() * porcentajeReduccion);

            for (int i = 0; i < conexionesARemover; i++) {
                if (conexiones.isEmpty()) break;
                Persona vecino = conexiones.get(i);
                grafo.obtenerConexiones(p).remove(vecino);
                grafo.obtenerConexiones(vecino).remove(p);
            }
        }

        System.out.println("Conexiones despues de la cuarentena:");
        mostrarNumeroConexiones();

        System.out.println("Se ha aplicado cuarentena, reduciendo las conexiones en un " + (porcentajeReduccion * 100) + "%.");
    }
    
    public void mostrarNumeroConexiones() {
        int totalConexiones = 0;
        for (Persona p : grafo.obtenerPersonas()) {
            totalConexiones += grafo.obtenerConexiones(p).size();
        }
        System.out.println("Total de conexiones en el grafo: " + (totalConexiones / 2));
    }
    
    private void mostrarEstadoPoblacion() {
        int sanos = 0, enfermos = 0, recuperados = 0, fallecidos = 0;

        for (Persona p : grafo.obtenerPersonas()) {
            if (!p.isVivo()) {
                fallecidos++;
            } else if (p.isEnfermo()) {
                enfermos++;
            } else if (p.isRecuperado()) {
                recuperados++;
            } else {
                sanos++;
            }
        }

        System.out.println("Sanos: " + sanos + " | Enfermos: " + enfermos + " | Recuperados: " + recuperados + " | Fallecidos: " + fallecidos);
    }
    
    public void reiniciarEstadoPersonas() {
        contadorIncubacion.clear();
        for (Persona p : grafo.obtenerPersonas()) {
            p.reiniciarEstado();
        }
       
    }
    
     public void establecerConexionesAleatorias() {
        List<Persona> personas = new ArrayList<>(grafo.obtenerPersonas());
        Random random = new Random();

        for (Persona p : personas) {
            int numeroConexiones = random.nextInt(1,3);

            for (int i = 0; i < numeroConexiones; i++) {
                Persona otraPersona = personas.get(random.nextInt(personas.size()));
                if (p != otraPersona && !grafo.obtenerConexiones(p).contains(otraPersona)) {
                    grafo.agregarConexion(p, otraPersona);
                }
            }
        }
    }
     
     public void reiniciarSimulacion() {
    contadorIncubacion.clear();
    for (Persona p : grafo.obtenerPersonas()) {
        p.reiniciarEstado(); 
    }
    grafo.reiniciarGrafo();
    establecerConexionesAleatorias(); 
}
}
