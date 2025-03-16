import IA.Red.*;

import java.util.*;
import static java.lang.Math.min;

public class Graph {
    private int[] Scons;
    private int[] Ccons;
    private int ssize;
    private int csize;
    private static Sensores Snodes;
    private static CentrosDatos Cnodes;
    private HashMap<Integer, Edge> adjs;

    public Graph(Sensores s, CentrosDatos c) {
        Snodes = s;
        Cnodes = c;
        ssize = Snodes.size();
        csize = Cnodes.size();
        Scons = new int[ssize];
        Ccons = new int[csize];
        adjs = new HashMap<>();
    }

    public void simpleSolution() {
        Random myRandom = new Random();
        // Añadimos aristas entre sensores y centros de datos, con un límite de 25 conexiones por centro de datos
        for (int i = 0; i < csize; ++i) {
            for (int j = 0; j < min(25, ssize); ++j) {
                int next = myRandom.nextInt(ssize);
                Edge newEdge = new Edge(next, i, 'c');

                // Si no hay una arista existente para el nodo 'next', la añadimos
                if (!adjs.containsKey(next)) {
                    adjs.put(next, newEdge);
                    ++Ccons[i];
                }
            }
        }


        // Añadimos a cada sensor una arista hacia él mismo
        for (int i = 0; i < ssize; ++i) {
            for (int j = 0; j < min(1, ssize - 1); ++j) {
                int next = myRandom.nextInt(ssize);
                if (i != next) {
                    Edge newEdge = new Edge(next, i, 's');
                    // Si no hay una arista existente para el nodo 'i', la añadimos
                    if (!adjs.containsKey(i)) {
                        adjs.put(next, newEdge);
                        ++Scons[i];
                    }
                }
            }
        }
    }

    //Operadores:
    // - cambiar nodo destino //hacerlo entre 2 aristas
 //- cambiar nodo origen //hacerlo entre 2 aristas
 //- añadir conexión
 //- eliminar conexión


    public boolean ableErase() {
        return !adjs.isEmpty(); //si fuesen nulls iria igual bien
    }

    public void eraseEdge() {
        Random r = new Random();
        int i = r.nextInt(ssize);

        // Find a non-empty node
        while (!adjs.containsKey(i)) {
            i = r.nextInt(ssize);
        }

        Edge selectedEdge = adjs.get(i);
        // Eliminar la arista del mapa
        adjs.remove(i);

        // Actualizar los contadores de conexiones
        char t = selectedEdge.getTipo();
        int id = selectedEdge.getId2();

        if (t == 's') --Scons[id];
        else --Ccons[id];
    }


    //considerando max edges = ssize
    public boolean ableAdd() {
        return adjs.size() < ssize;
        /*
         for (int i = 0; i < ssize; ++i) {
            if (!adjs.containsKey(i) || adjs.get(i) == null) {
                return true;
            }
        }
        return false;
         */
    }

    public void addEdge() {
        Random r = new Random();
        int i = r.nextInt(ssize);
        while (adjs.containsKey(i)) {
            i = r.nextInt(ssize);
        }
        // Como sabemos que podemos añadir una, intentamos con certeza en todos los nodos
        for (int j = 0; j < csize; ++j) {
            if (Ccons[j] < 25) {
                adjs.put(i, new Edge(i, j, 'c'));
                return;
            }
        }

        for (int j = 0; j < ssize; ++j) {
            if (Scons[j] < 3) {
                adjs.put(i, new Edge(i, j, 's'));
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