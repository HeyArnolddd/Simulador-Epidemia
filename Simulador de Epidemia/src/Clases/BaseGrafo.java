
package Clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Arnold Steven Navarro
 */
public class BaseGrafo {
    
private Map<Persona, List<Persona>> adjList;

    public BaseGrafo() {
        adjList = new HashMap<>();
    }

    public void agregarPersona(Persona p) {
        if (!adjList.containsKey(p)) {
            adjList.put(p, new ArrayList<>());
        }

    }

    public void agregarConexion(Persona p1, Persona p2) {
       
        adjList.get(p1).add(p2);
        adjList.get(p2).add(p1);
    }

    public List<Persona> obtenerConexiones(Persona p) {
        return adjList.get(p);
    }

    public Set<Persona> obtenerPersonas() {
        return adjList.keySet();
    }

    public void reiniciarGrafo() {
        for (Persona p : adjList.keySet()) {
            adjList.put(p, new ArrayList<>());
        }
       
    }
}
