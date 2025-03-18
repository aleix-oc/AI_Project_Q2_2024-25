import IA.Red.*;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.Comparator;
import static java.lang.Math.min;

//𓃵 MESSI
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

    public static double calcularDistancia(int x1, int y1, int x2, int y2) {
        int diferenciaX = x2 - x1;
        int diferenciaY = y2 - y1;
        return Math.sqrt(Math.pow(diferenciaX, 2) + Math.pow(diferenciaY, 2));
    }

    public static double calcularDistancia(Centro a, Sensor b){
        int x1 = a.getCoordX();
        int y1 = a.getCoordY();
        int x2 = b.getCoordX();
        int y2 = b.getCoordY();
        return calcularDistancia(x1, y1, x2, y2);
    }
    public static double calcularDistancia(Sensor a, Sensor b){
        int x1 = a.getCoordX();
        int y1 = a.getCoordY();
        int x2 = b.getCoordX();
        int y2 = b.getCoordY();
        return calcularDistancia(x1, y1, x2, y2);
    }


    public void complexSolution(){
        // Usamos TreeSet con un Comparator similar al que usabas para PriorityQueue
        TreeSet<Edge> treeSet = new TreeSet<>(Comparator
                .comparingInt((Edge edge) -> snodes.get(edge.getId1()).getCapacidad()).reversed()  // Orden por capacidad decreciente
                .thenComparingDouble(Edge::getDistancia));  // Si hay empate, orden por distancia creciente

        // Calcular las distancias entre cada par de puntos
        for (int i = 0; i < csize; i++) {
            for (int j = 0; j < ssize; j++) {
                treeSet.add(new Edge(j, i, 'c', calcularDistancia(Cnodes.get(i), Snodes.get(j))));
            }
        }

        while (!treeSet.isEmpty() && adjs.size() < ssize) {
            Edge selected = treeSet.pollFirst(); // Obtiene el primer (más prioritario) elemento
            int id1 = selected.getId1();
            int id2 = selected.getId2();
            char t = selected.getTipo();
            double d = selected.getDistancia();

            if (!adjs.containsKey(id1)) {
                if (t == 'c') {
                    if (Ccons[id2] < 25) {
                        adjs.put(id1, new Edge(id1, id2, t));
                        for (int j = 0; j < ssize; ++j) {
                            if (id1 != j) {
                                // Al agregar al TreeSet, se asegura de que no se añadan duplicados
                                treeSet.add(new Edge(j, id1, 's', d + calcularDistancia(Snodes.get(i), Snodes.get(j))));
                            }
                        }
                    }
                } else {
                    if (Scons[id2] < 3) {
                        adjs.put(id1, new Edge(id1, id2, t));
                        for (int j = 0; j < ssize; ++j) {
                            if (id1 != j) {
                                // Al agregar al TreeSet, se asegura de que no se añadan duplicados
                                treeSet.add(new Edge(j, id1, 's', d + calcularDistancia(Snodes.get(i), Snodes.get(j))));
                            }
                        }
                    }
                }
            }
        }
    }

    public void simpleSolution() {
        int i = 0;//Sensor
        int j = 0;//Centro
        while (j < csize) {
            if (i == ssize) return;//Hemos puesto todos los sensores
            adjs.put(i,new Edge(i,j,'c'));
            ++Ccons[j];
            if (Ccons[j] == 25) ++j;
            ++i;
        }
        //Si he salido del bucle quedan sensores pero todos los centros están llenos
        //Ahora j serán los sensores ya conectados
        j = 0;
        while (i < ssize) {
            adjs.put(i,new Edge(i,j,'s'));
            ++Scons[j];
            if (Scons[j] == 3) ++j;
            ++i;
        }
    }

    //Operadores:
    
    //Cambiar 2 aristas
    public void switchEdges(int id1, int id2) {
        Edge a = adjs.get(id1);
        Edge b = adjs.get(id2);
        a.setId1(id2);
        b.setId1(id1);
        adjs.put(id1,b);
        adjs.put(id2,a);
    }

    //id1: Nodo cuyo destino queremos cambiar
    //d: Posible nuevo destino
    public boolean ableSwitch(int id1, int d, char tipo) {
        int[] cons;
        int lim;
        if (tipo == 'c') {cons = Ccons; lim = 25;}
        else {cons = Scons; lim = 3;}
        int id2 = adjs.get(id1).getId2();
        char t = adjs.get(id1).getTipo();
        if (cons[d] < lim) {
            if (t == 's' && tipo == 's') return d != id1 && d != id2;
            else if (t == 'c' && tipo == 's') return d != id1;
            else if (t == 'c' && tipo == 'c') return d != id2;
            else return true;
        }
        else return false;
    }

    public void SwitchDestination(int id1, int id2, char t) {
        adjs.put(id1,new Edge(id1,id2,t));
    }

    public int getSsize() {
        return ssize;
    }
    public int getCsize() {
        return csize;
    }

}