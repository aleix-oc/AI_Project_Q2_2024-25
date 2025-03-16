import IA.Red.*;

import java.util.*;
import static java.lang.Math.min;

//UN SENSOR SOLO SACA UNA ARISTA VERDAD??? MUY IMPORTANTE
public class Graph {
    private int[] Scons;
    private int[] Ccons;
    private int ssize;
    private int csize;
    private static Sensores Snodes;
    private static CentrosDatos Cnodes;
    private HashMap<Integer, HashSet<Edge>> adjs;

    public Graph(Sensores s, CentrosDatos c) {
        Snodes = s;
        Cnodes = c;
        ssize = Snodes.size();
        csize = Cnodes.size();
        Scons = new int[ssize];
        Ccons = new int[csize];
        adjs = new HashMap<>();
    }

    //Nota: Mirar si el nextint no repetirá, hay que estar seguros
    public void simpleSolution() {
        Random myRandom = new Random();
        // Añadimos aristas entre sensores y centros de datos, con un límite de 25 conexiones por centro de datos
        for (int i = 0; i < csize; ++i) {
            for (int j = 0; j < min(25, ssize); ++j) {
                int next = myRandom.nextInt(ssize);
                Edge newEdge = new Edge(next, i, 'c');

                HashSet<Edge> edges = adjs.computeIfAbsent(next, k -> new HashSet<>());
                if (edges.add(newEdge)) {
                    ++Ccons[i];
                }
            }
        }


        // Añadimos a cada sensor un maximo de 3 aristas hacia él mismo
        for (int i = 0; i < ssize; ++i) {
            for (int j = 0; j < min(3, ssize - 1); ++j) {
                int next = myRandom.nextInt(ssize);
                if (i != next) {
                    Edge newEdge = new Edge(next, i, 's');
                    HashSet<Edge> edges = adjs.computeIfAbsent(i, k -> new HashSet<>());
                    if (edges.add(newEdge)) {
                        ++Scons[i];
                    }
                }
            }
        }
    }

    //Operadores:
    // - cambiar nodo destino
 //- cambiar nodo origen
 //- añadir conexión
 //- eliminar conexión


    public boolean ableErase() {
        /* // Check if there is any node with at least one edge
        for (HashSet<Edge> edges : adjs.values()) {
            if (!edges.isEmpty()) {
                return true; // Found a node with at least one edge
            }
        }
        return false; // No nodes with edges
        */
        return !adjs.isEmpty();
    }

    public void eraseEdge() {
        Random r = new Random();
        int i = r.nextInt(ssize);

        // Find a non-empty node
        while (!adjs.containsKey(i) || adjs.get(i).isEmpty()) {
            i = r.nextInt(ssize);
        }

        HashSet<Edge> edges = adjs.get(i);
        int setSize = edges.size();

        // Select a random edge from the set
        int edgeIndex = r.nextInt(setSize);
        Iterator<Edge> it = edges.iterator();

        Edge selectedEdge = null;
        for (int j = 0; j <= edgeIndex; ++j) {
            selectedEdge = it.next();
        }

        // Remove the selected edge
        it.remove();

        // Update the connection counters
        char t = selectedEdge.getTipo();
        int id = selectedEdge.getId2();

        if (t == 's') --Scons[id];
        else --Ccons[id];

        // Remove empty sets from the map
        //if (edges.isEmpty()) {
        //    adjs.remove(i);
       // }
    }


    //MUY IMPORTANTE:Nunca llegaremos a tener 3*ssize+25*csize porque solo sale una edge de cada sensor
    //haria una variable total correspondiente a las casillas del hash no nulas y luego
    //return total < ssize;
    //es abusar de atributos? tu decides
    public boolean ableAdd() {
        for (int i = 0; i < ssize; ++i) {
            if (!adjs.containsKey(i) || adjs.get(i).isEmpty()) { return true; } //solo si no hacemos erase de las vacías
        }
        return false;
    }

    public void addEdge() {
        Random r = new Random();
        int i = r.nextInt(ssize);
        while (adjs.containsKey(i)) {
            i = r.nextInt(ssize);
        }
        //As we know we are able to add one, we just try with certainty across all the nodes
        for (int j = 0; j < csize; ++j) {
            if (Ccons[j] < 25) {
                adjs.computeIfAbsent(i, k -> new HashSet<>()).add(new Edge(i, j, 'c'));
                return;
            }
        }

        for (int j = 0; j < ssize; ++j) {
            if (Scons[j] < 3) {
                adjs.computeIfAbsent(i, k -> new HashSet<>()).add(new Edge(i, j, 's'));
                return;
            }
        }
    }


    public boolean ableSwitchOrigin() {
        if (adjs.isEmpty()) return false;
        boolean hueco = false;
        boolean arista = false;
        for (int i = 0; i < ssize; ++i) {
            if (!hueco && adjs.get(i) == null) {
                if (arista) return true;
                else hueco = true;
            }
            if (!arista && adjs.get(i) != null) {
                if (hueco) return true;
                else arista = true;
            }
        }
        return false;
    }

    //en este caso no hace falta hacer erase y luego add, asi es mas eficiente
    public void switchOrigin() {
        Random r = new Random();
        int i = r.nextInt(ssize);
        int j = i;
        boolean foundi = false;
        boolean foundj = false;
        while (!foundi && !foundj) {
            if (!foundi && adjs.get(i) == null) foundi = true;
            else if (!foundi) i = r.nextInt(ssize);
            if (!foundj && adjs.get(j) != null) foundj = true;
            else if (!foundj) j = r.nextInt(ssize);
        }
        Edge aux = new Edge(i,adjs.get(j).getId2,adjs.get(j).getTipo);
        adjs.put(i,aux);
        adjs.put(j,null);
    }


    private boolean ableSwitchDestination() {
        if (adjs.isEmpty()) return false;
        //Hay que mirar todo y ver si hay al menos 2 nodos con huecos!
        //Porque si hay solo una cabe la posibilidad de que me toque hacer switch destination de esa misma y entonces sería como no hacer nada
        int count = 0;
        for (int i = 0; i < csize; ++i) {
            if (Ccons[i] < 25) ++count;
            if (count == 2) return true;
        }
        for (int i = 0; i < ssize; ++i) {
            if (Scons[i] < 3) ++count;
            if (count == 2) return true;
        }
        return false;
    }

    public void switchDestination() {
        Random r = new Random();
        int i = r.nextInt(ssize);
        int j = i;
        boolean foundi = false;
        boolean foundj = false;
        char t;
        while (!foundi && !foundj) {
            if (!foundi && adjs.get(i) != null) foundi = true;
            else if (!foundi) i = r.nextInt(ssize);
            if (!foundj) {
                if (Ccons[j] < 25) {foundj = true; t = 'c';}
                else if (Scons[j] < 3) {foundj = true; t = 's';}
            }
            else if (!foundj) j = r.nextInt(ssize);
        }
        Edge aux = new Edge(i,j,t);
        adjs.put(i,aux);
    }
}