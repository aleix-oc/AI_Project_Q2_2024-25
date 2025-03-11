import java.util.*;

import static java.lang.Math.min;
import static java.lang.Math.random;

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


    //easter egg, video top de e-k:https://www.youtube.com/watch?v=RppuJYwlcI8
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
                }
            }
        }
    }
    public boolean limit3(int id2){
        // Verificamos si existe la clave en el HashMap y si la lista asociada tiene más de 3 aristas
        return adjs.containsKey(id2) && adjs.get(id2).size() > 3;
    }




    //AUXILIARES PARA OPERADORES
    public boolean ableErase() {
        return !adjs.isEmpty();
    }
    //solo por probar borro la arista con mayor coste, sino tambien podemos borrar una random hay que experimentar
    public void eraseEdge() {
        int maxi = -1,maxj = -1,maxw = -1;
        for (Iterator<HashMap.Entry<Integer, ArrayList<Edge>>> it = adjs.entrySet().iterator();it.hasNext();) {
            HashMap.Entry<Integer,ArrayList<Edge>> entry = it.next();
            ArrayList<Edge> adj = entry.getValue();
            for (int i = 0; i < adj.size(); ++i) {
                if (adj.get(i).getWeight() > maxw) {
                    maxw = adj.get(i).getWeight();
                    maxi = entry.getKey();
                    maxj = i;
                }
            }
        }
        adjs.get(maxi).remove(maxj);
        if (adjs.get(maxi).isEmpty()) adjs.remove(maxi);
    }

    public boolean ableAdd() {
        //deberiamos hablar lo de la cantidad de aristas, aqui basta con mirar
        //return adjs.size() < Snodes.size()*3 +...
        return true;
    }

    public void addEdge() {
        boolean found = false;
        while (!found) {
            int rand = (int) random();
            rand %= ssize;
            if (adjs.containsKey(rand)) {
                if (adjs.get(rand).size() < 3) {
                    found = true;
                    //añadir arista, puedo coger una a s rand o a c rand pero hay q hablar conds                }
                }
            }
            else {
                found = true;
                //ídem
            }
        }
    }

    public void switchEdge() {
        //hay 3 casos,
    }
}
