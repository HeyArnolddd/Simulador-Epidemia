package Clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Arnold Steven Navarro
 */
public class MainSimulacion {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el numero de personas en la poblacion: ");
        int numeroPersonas = scanner.nextInt();

        BaseGrafo grafo = new BaseGrafo();
        List<Persona> personas = new ArrayList<>();
        for (int i = 1; i <= numeroPersonas; i++) {
            Persona persona = new Persona(i, (int) (Math.random() * 80 + 1));
            grafo.agregarPersona(persona);
            personas.add(persona);
        }

        Enfermedad enfermedad = configurarEnfermedad(scanner);
        LogicaSimulacion simulador = new LogicaSimulacion(grafo, enfermedad);

        simulador.establecerConexionesAleatorias();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menu de Opciones ---");
            System.out.println("1. Observar comportamiento del virus");
            System.out.println("2. Vacunar a un porcentaje de la poblacion");
            System.out.println("3. Aplicar cuarentena");
            System.out.println("4. Salir");
            System.out.println("5. Tendencia");
            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:

                    Random random = new Random();
                    Persona pacienteCero = personas.get(random.nextInt(personas.size()));
                    pacienteCero.setEnfermo(true);
                    System.out.println("\nPaciente cero: " + pacienteCero);

                    System.out.print("Ingrese el numero de días a simular: ");
                    int dias = scanner.nextInt();
                    simulador.iniciarSimulacion(dias);
                    System.out.print("Enfermedad: "+enfermedad.getNombre());

                    break;
                case 2:

                    simulador.reiniciarEstadoPersonas();
                    System.out.print("Ingrese el porcentaje de personas a vacunar (0 a 100): ");
                    double porcentajeVacunacion = scanner.nextDouble() / 100;
                    simulador.vacunarPoblacion(porcentajeVacunacion);
                    break;
                case 3:
                    System.out.print("Ingrese el porcentaje de reducción de conexiones (0 a 100): ");
                    double porcentajeReduccion = scanner.nextDouble() / 100;
                    simulador.aplicarCuarentena(porcentajeReduccion);
                    break;
                case 4:
                    salir = true;
                    break;
                case 5:
                    System.out.print("Ingrese numerode veces a repetir la simulacion:");
                    int n = scanner.nextInt();

                    Random r = new Random();

                    System.out.print("Ingrese el numero de días a simular: ");
                    int d = scanner.nextInt();

                    for (int i = 0; i < n; i++) {
                        Persona p = personas.get(r.nextInt(personas.size()));
                        p.setEnfermo(true);
                        simulador.iniciarSimulacion2(d);
                        simulador.reiniciarSimulacion();
                    }
                    System.out.print("Enfermedad: "+enfermedad.getNombre());
                default:
                    System.out.println("Opción inválida. Por favor, seleccione nuevamente.");
            }
        }

    }

    private static Enfermedad configurarEnfermedad(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Ingrese el nombre de la enfermedad: ");
        String nombreEnfermedad = scanner.nextLine();

        String tipoTransmision;
        while (true) {
            System.out.print("Ingrese el tipo de transmisiin (contacto/respiratorio): ");
            tipoTransmision = scanner.nextLine().toLowerCase();
            if (tipoTransmision.equals("contacto") || tipoTransmision.equals("respiratorio")) {
                break;
            } else {
                System.out.println("Tipo de transmisión invilido. Intente nuevamente.");
            }
        }

        System.out.print("Ingrese el tiempo de incubaciin (en dias): ");
        int tiempoIncubacion = scanner.nextInt();

        System.out.print("Ingrese el tiempo de vida de la enfermedad (en dias): ");
        int tiempoVida = scanner.nextInt();

        System.out.print("Ingrese el porcentaje de mortalidad (0 a 1): ");
        double porcentajeMortalidad = scanner.nextDouble();

        return new Enfermedad(nombreEnfermedad, tipoTransmision, tiempoIncubacion, tiempoVida, porcentajeMortalidad);
    }

}
