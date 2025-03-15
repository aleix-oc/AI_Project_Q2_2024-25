import IA.Red.*;

import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.random;

//UN SENSOR SOLO SACA UNA ARISTA VERDAD??? MUY IMPORTANTE
public class Graph {
    private int[] Scons;
    private int[] Ccons;
    private int ssize;
    private int csize;
    private static Sensores Snodes;
    private static CentrosDatos Cnodes;
    private HashMap<Integer, ArrayList<Edge>> adjs;

    public Graph(Sensores s, CentrosDatos c) {
        Snodes = s;
        Cnodes = c;
        ssize = Snodes.size();
        csize = Cnodes.size();
        Scons = new int[ssize];
        Ccons = new int[csize];
        adjs = new HashMap<>();
    }

    /*
    //easter egg, video top de e-k:https://www.youtube.com/watch?v=RppuJYwlcI8
    public void fillEdges() {
        // Añadimos las aristas entre nodos de sensores
        for (int i = 0; i < ssize; ++i) {
            for (int j = 0; j < ssize; ++j) {
                if (i != j) {
                    Sadjs.computeIfAbsent(j, k -> new ArrayList<>()).add(new Edge(i, j, 's'));  // Usamos computeIfAbsent para inicializar la lista si no existe
                }
            }
        }
        // Añadimos las aristas entre nodos de sensores y centros de datos
        for (int i = 0; i < ssize; ++i) {
            for (int j = 0; j < csize; ++j) {
                Cadjs.computeIfAbsent(j, k -> new ArrayList<>()).add(new Edge(i, j, 'c'));
            }
        }

        // Falta pensar cómo manejar los límites de 3 y 25, así como los pesos/capacidad
    }
    */

    //Nota: Mirar si el nextint no repetirá, hay que estar seguros
    public void simpleSolution() {
        Random myRandom = new Random();
        // Añadimos aristas entre sensores y centros de datos, con un límite de 25 conexiones por centro de datos
        for (int i = 0; i < csize; ++i) {
            for (int j = 0; j < min(25, ssize); ++j) {
                int next = myRandom.nextInt(ssize);
                adjs.computeIfAbsent(next, k -> new ArrayList<>()).add(new Edge(next, i, 'c'));
                ++Ccons[i];
            }
        }

        //HAY QUE REHACER ESTA PARTE MAÑANA LO MIRO NO ME HA DADO TIEMPO 
        // Añadimos aristas entre sensores, con un límite de 3 conexiones por sensor
        for (int i = 0; i < ssize; ++i) {
            for (int j = 0; j < min(3, ssize - 1); ++j) {
                int next = myRandom.nextInt(ssize);
                if (i != next && !adjs.containsKey(i) && Scons[next] < 3) {
                    adjs.computeIfAbsent(i, k -> new ArrayList<>()).add(new Edge(i, next, 's'));
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
        return !adjs.isEmpty();
    }

    public void eraseEdge() {
        Random r = new Random();
        int i = r.nextInt(sz);
        while (true) {
            if (adjs.get(i) == null) break;
            i = r.nextInt(sz);
        }
        int id = adjs.get(i).getId2;
        char t = adj.get(i).getTipo;
        adjs.put(i,null);
        if (t == 's') --Scons[id];
        else --Ccons[id];
    }

    //MUY IMPORTANTE:Nunca llegaremos a tener 3*ssize+25*csize porque solo sale una edge de cada sensor
    //haria una variable total correspondiente a las casillas del hash no nulas y luego
    //return total < ssize;
    //es abusar de atributos? tu decides
    public boolean ableAdd() {
        for (int i = 0; i < ssize; ++i) {
            if (adjs.get(i) == null) return true;
        }
        return false;
    }

    public void addEdge() {
        Random r = new Random();
        int i = r.nextInt(ssize);
        while (true) {
            if (adjs.get(i) == null) break;
            i = r.nextInt(ssize);
        }
        //As we know we are able to add one, we just try with certainty across all the nodes
        for (int j = 0; j < csize; ++j) if (Ccons[j] < 25) {adjs.put(i,new Edge(i,j,'c')); return;}
        for (int j = 0; j < ssize; ++j) if (Ccons[j] < 3) {adjs.put(i,new Edge(i,j,'s')); return;}
    }


    public boolean ableSwitchOrigin() {
        if (adjs.isEmpty()) return false;
        bool hueco = false;
        bool arista = false;
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