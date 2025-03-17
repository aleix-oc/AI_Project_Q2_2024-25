import IA.Red.*;

import java.util.*;
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

    public void simpleSolution() {
        int i = 0;//Sensor
        int j = 0;//Centro
        while (true) {
            if (i == ssize) return;//Hemos puesto todos los sensores
            if (j == csize) break;
            adjs.put(i,new Edge(i,j,'c'));
            ++Ccons[j];
            if (Ccons[j] == 25) ++j;
            ++i;
        }
        //Si he salido del bucle quedan sensores pero todos los centros están llenos
        //Ahora j serán los sensores ya conectados
        j = 0;
        while (true) {
            if (i == ssize) return;
            adjs.put(i,new Edge(i,j,'s'));
            ++Scons[j];
            if (Scons[j] == 3) ++j;
            ++i;
        }
    }

    //Operadores:
    
    //Cambiar 2 aristas
    private boolean ableSwitchEdges() {
        return true;
    }

    public void switchEdges(int id1, int id2) {
        Edge a = adjs.get(id1);
        Edge b = adjs.get(id2);
        a.setId1(id2);
        b.setId1(id1);
        adjs.put(id1,b);
        adjs.put(id2,a);
    }

    //Cambiar el nodo apuntado por una arista
    private boolean ableSwitchDestination(int id) {
        int id2 = adjs.get(id).getId2();
        for (int i = 0; i < ssize; ++i) if (i != id && i != id2 && Scons[i] < 3) return true;
        for (int i = 0; i < csize; ++i) if (i != id && i != id2 Ccons[i] < 25) return true;
        return false;
    }

    public void SwitchDestination(int id) {
        //Deberíamos haberlo hablado pero así por intuición hago de hacer el switch a un nuevo nodo random
        //Piensa que quizá es demasiado hacer para todas las aristas probar a conectarlas a todos los otros posibles nodos
        //Es mi opiniónlo podemos discutir
        int id2 = adjs.get(id).getId2();
        char tipo = adjs.get(id).getTipo();
        Random r = new Random();
        int i = r.nextInt(2);
        int[] cons;
        char t;
        int lim;
        if (i == 0) {cons = Ccons; t = 'c'; lim = 25;}
        else {cons = Scons; t = 's'; lim = 3;}
        int sz = cons.size();
        i = r.nextInt(sz);
        while (true) {
            if (((tipo != t) || (i != id && i != id2)) && cons[i] < lim) {
                adjs.put(id,new Edge(id,i,t));
                return;
            }
            i = r.nextInt(sz);
        }
    }

}