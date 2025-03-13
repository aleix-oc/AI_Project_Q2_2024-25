import IA.Red.*;

import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.random;

public class Graph {
    //Quiza va bien guardar el numero actual de aristas, la suma de tamaños de todos los arraylists
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

    //Operadores:
    // - cambiar nodo destino
 //- cambiar nodo origen
 //- añadir conexión
 //- eliminar conexión


    //AUXILIARES PARA OPERADORES
    public boolean ableErase() {
        return !adjs.isEmpty();
    }
    //solo por probar borro la arista con mayor coste, sino tambien podemos borrar una random hay que experimentar
    public void eraseEdge() {
        Random r = new Random();
        int i = r.nextInt(ssize);//otra vez el problema de la adjacency list
        while (true) {
            if (adjs.containsKey(i)) break;
            i = r.nextInt(ssize);
        }
        int j = r.nextInt(adjs.get(i).size());
        adjs.get(i).remove(j);
        if (adjs.get(i).isEmpty()) adjs.remove(i);
    }

    public boolean ableAdd() {
        return adjs.size() < ssize*3 + csize*25;
    }

    //Pondre lo de dentro de if else en otra funcion auxiliar por legibilidad seguramente
    public void addEdge() {
        while (true) {
            int rand = (int) random();
            int aux = rand % 2;
            char mode;
            if (aux == 0) rand %= ssize;
            else rand %= csize;
            //rand %= size de uno de los 2 pero primero debemos hablar lo de la adjlist si ves este push hoy tranqui mañana lo entenderas pero mejor hablarlo en persona
            if (adjs.containsKey(rand) && adjs.get(rand).size() < 3) {
                int srand = (int) random();
                while (true) {
                    srand %= ssize; //da igual que rand(el primer indice) sea s o c, el siguiente siempre es s
                    if (srand != rand) break;
                }
                if (aux == 0) adjs.get(rand).add(new Edge(srand, rand, 's'));
                else adjs.get(rand).add(new Edge(srand, rand, 'c'));
                //añadir arista, puedo coger una a s rand o a c rand pero hay q hablar conds
                return;
            }
            else if(!adjs.containsKey(rand)) {
                adjs.put(rand,new ArrayList<Edge>());
                int srand;
                while (true) {
                    srand = (int) random();
                    srand %= ssize; //da igual que rand(el primer indice) sea s o c, el siguiente siempre es s
                    if (srand != rand) break;
                }
                if (aux == 0) adjs.get(rand).add(new Edge(srand, rand, 's'));
                else adjs.get(rand).add(new Edge(srand, rand, 'c'));
                return;
                //ídem
            }
        }
    }


    public bool ableSwitch() {
        return true;
        //tan costoso de mirar que hace que este operador no valga la pena, pendiente discutir
    }

    //en este caso no hace falta hacer erase y luego add, asi es mas eficiente
    public void switchOrigin() {
        Random r = new Random();
        int i = r.nextInt(ssize);//otra vez el problema de la adjacency list
        while (true) {
            if (adjs.containsKey(i)) break;
            i = r.nextInt(ssize);
        }
        int j = r.nextInt(adjs.get(i).size());
        adjs.get(i).get(j).setId1(4);
    }

    private bool ableSwitchDestination() {
        return true;
        //lo malo es que hay que tener en cuenta las aristas del nodo al que le quitaremos una
        //esto es porque hay que ver si nos queda capacidad para aristas sin contar el nodo destination previo 
    }

    public void switchDestination() {
        //if (ableSwitchDsetination)
        //lo unico que s eme ocurre es hacer la comprobacion aqui, sera costoso...
        Random r = new Random();
        int i = r.nextInt(ssize);//otra vez el problema de la adjacency list
        while (true) {
            if (adjs.containsKey(i)) break;
            i = r.nextInt(ssize);
        }
        int j = r.nextInt(adjs.get(i).size());
        j = adjs.get(i).get(j).getId1();
        //i = bucle hasta encontrar nuevo destino VÁLIDO
        //eraseSpecificEdge(x),
        //la i y la j seran el nuevo destino y el origen respectivamente
        //addSpecificEdge(i,j), implementación trivial
    }
}