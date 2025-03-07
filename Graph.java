import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.min;

public class Graph {
    private int ssize;
    private int csize;
    private static Sensores Snodes;
    private static CentrosDatos Cnodes;
    private HashMap<Integer, ArrayList<Edge>> adjs;  // Usamos HashMap para almacenar varias aristas por id2

    public Graph(Sensores s, CentrosDatos c) {
        Snodes = s;
        Cnodes = c;
        adjs = new HashMap<>();  // Inicialización del HashMap
    }

    public void fillEdges() {
        // Añadimos las aristas entre nodos de sensores
        for (int i = 0; i < ssize; ++i) {
            for (int j = 0; j < ssize; ++j) {
                if (i != j) {
                    adjs.computeIfAbsent(j, k -> new ArrayList<>()).add(new Edge(i, j, 's'));  // Usamos computeIfAbsent para inicializar la lista si no existe
                }
            }
        }
        // Añadimos las aristas entre nodos de sensores y centros de datos
        for (int i = 0; i < ssize; ++i) {
            for (int j = 0; j < csize; ++j) {
                adjs.computeIfAbsent(j, k -> new ArrayList<>()).add(new Edge(i, j, 'c'));
                adjs.computeIfAbsent(i, k -> new ArrayList<>()).add(new Edge(j, i, 'c'));
            }
        }

        // Falta pensar cómo manejar los límites de 3 y 25, así como los pesos/capacidad
    }

    public void simpleSolution() {
        Random myRandom = new Random();

        // Añadimos aristas entre sensores y centros de datos, con un límite de 25 conexiones por centro de datos
        for (int i = 0; i < csize; ++i) {
            for (int j = 0; j < min(25, ssize); ++j) {
                int next = myRandom.nextInt(ssize);
                if (!limit3(next)) {
                    adjs.computeIfAbsent(next, k -> new ArrayList<>()).add(new Edge(i, next, 'c'));
                    adjs.computeIfAbsent(i, k -> new ArrayList<>()).add(new Edge(next, i, 'c'));
                }
            }
        }

        // Añadimos aristas entre sensores, con un límite de 3 conexiones por sensor
        for (int i = 0; i < ssize; ++i) {
            for (int j = 0; j < min(3, ssize - 1); ++j) {
                int next = myRandom.nextInt(ssize);
                if (i != next && !limit3(next) && !limit3(i)) {
                    adjs.computeIfAbsent(next, k -> new ArrayList<>()).add(new Edge(i, next, 's'));
                    adjs.computeIfAbsent(i, k -> new ArrayList<>()).add(new Edge(next, i, 's'));
                }
            }
        }
    }
    public boolean limit3(int id2){
        // Verificamos si existe la clave en el HashMap y si la lista asociada tiene más de 3 aristas
        return adjs.containsKey(id2) && adjs.get(id2).size() > 3;
    }
}
