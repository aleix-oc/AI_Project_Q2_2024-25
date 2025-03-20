import IA.Red.*;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

import static java.lang.Math.max;
import static java.lang.Math.min;

//𓃵 MESSI
public class Graph {
    private int[] Scons;
    private int[] Ccons;
    private int[] Calmacenamiento;
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
        Calmacenamiento = new int[csize];
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
        // Usamos TreeSet con un Comparator similar al que usabamos para PriorityQueue

        TreeSet<Edge> treeSet = new TreeSet<>(Comparator
                .comparingDouble(Edge::getVolumenReal).reversed()  // Orden por volumen real decreciente
                .thenComparingDouble(Edge::getDistancia));  // Si hay empate, orden por distancia creciente

        // Calcular las distancias entre cada par de puntos
        for (int i = 0; i < csize; i++) {
            for (int j = 0; j < ssize; j++) {
                treeSet.add(new Edge(j, i, 'c', calcularDistancia(Cnodes.get(i), Snodes.get(j)), (int)Snodes.get(j).getCapacidad(), (int)Snodes.get(j).getCapacidad() ));
            }
        }

        while (!treeSet.isEmpty() && adjs.size() < ssize) {
            Edge selected = treeSet.pollFirst(); // Obtiene el primer (más prioritario) elemento
            int id1 = selected.getId1();
            int id2 = selected.getId2();
            char t = selected.getTipo();
            double d = selected.getDistancia();
            int v1 = selected.getVolumenReal();
            int v2 = selected.getVolumenFalso();

            if (!adjs.containsKey(id1)) {
                if (t == 'c') {
                    if (Ccons[id2] < 25 && Calmacenamiento[id2] < 150) {
                        adjs.put(id1, new Edge(id1, id2, t, d, min(v1, 150-Calmacenamiento[id2]), v2));
                        Calmacenamiento[id2] = min(150 , Calmacenamiento[id2] + v1);
                        ++Ccons[id2];
                        for (int j = 0; j < ssize; ++j) {
                            if (!adjs.containsKey(j)) {
                                // Al agregar al TreeSet, se asegura de que no se añadan duplicados
                                treeSet.add(new Edge(j, id1, 's', d + calcularDistancia(Snodes.get(id1), Snodes.get(j)), (int)Snodes.get(j).getCapacidad(), (int)Snodes.get(j).getCapacidad()));
                            }
                        }
                    }
                } else {
                    Edge previo = adjs.get(id2);
                    int capacidadp = (int)Snodes.get(id2).getCapacidad();
                    int capacidada = (int)Snodes.get(id1).getCapacidad();
                    if (Scons[id2] < 3 && previo.getVolumenFalso()<3*capacidadp) {
                        Edge next = new Edge(id1, id2, t, d, min(3*capacidadp, previo.getVolumenReal() + capacidada), v2);
                        ++Scons[id2];
                        adjs.put(id1, next);
                        enfonsarVolumen(next);

                        for (int j = 0; j < ssize; ++j) {
                            if (!adjs.containsKey(j)) {
                                // Al agregar al TreeSet, se asegura de que no se añadan duplicados
                                treeSet.add(new Edge(j, id1, 's', d + calcularDistancia(Snodes.get(id1), Snodes.get(j)), (int)Snodes.get(j).getCapacidad(), (int)Snodes.get(j).getCapacidad()));
                            }
                        }
                    }
                }
            }
        }

        for(int i=0; i<ssize ; ++i){
            Edge selected = adjs.get(i);
            if(selected.getTipo()=='c'){
                selected.setDistancia(calcularDistancia(Cnodes.get(selected.getId2()), Snodes.get(selected.getId1())));
            }
            else {
                selected.setDistancia(calcularDistancia(Snodes.get(selected.getId1()), Snodes.get(selected.getId2())));
            }
        }
    }

    public void enfonsarVolumen(Edge selected){

        int id1 = selected.getId1();
        int id2 = selected.getId2();

        Edge previo = adjs.get(id2);
        int capacidadp = (int)Snodes.get(id2).getCapacidad();
        while(previo.getTipo() != 'c' || previo.getVolumenFalso()<3*capacidadp){

            capacidadp = (int)Snodes.get(selected.getId2()).getCapacidad();
            int capacidadpp = (int)Snodes.get(previo.getId2()).getCapacidad();
            previo.setVolumenFalso(min(3*capacidadp, previo.getVolumenFalso()+selected.getVolumenReal()));
            previo.setVolumenReal(min(previo.getVolumenReal()+selected.getVolumenReal(), 3*capacidadpp-adjs.get(previo.getId2()).getVolumenReal()));
            selected = previo;
            previo = adjs.get(selected.getId2());
        }
        if(previo.getTipo() == 'c') {
            capacidadp = (int)Snodes.get(selected.getId2()).getCapacidad();
            int idc = previo.getId2();
            previo.setVolumenFalso(min(3 * capacidadp, previo.getVolumenFalso() + selected.getVolumenReal()));
            previo.setVolumenReal(min(previo.getVolumenReal() + selected.getVolumenReal(), 150 - Calmacenamiento[idc]));
            Calmacenamiento[idc] = min(150, Calmacenamiento[idc] + previo.getVolumenReal());
        }

    }

    public void desenfonsarVolumen(Edge selected){

        int id2 = selected.getId2();
        //vf2 = vf2 - vr1, vr2 = vr2 - (vr1 - (vf2 - vr2))

        Edge previo = adjs.get(id2);
        boolean acabado = false;
        while(previo.getTipo() != 'c' || selected.getVolumenReal() > 0 || !acabado){
            previo.setVolumenFalso(previo.getVolumenFalso()-selected.getVolumenReal());
            if(previo.getVolumenFalso() - previo.getVolumenReal() < selected.getVolumenReal()) previo.setVolumenReal(previo.getVolumenReal()-(selected.getVolumenReal()-(previo.getVolumenFalso()-previo.getVolumenReal())));
            else acabado = true;
            selected = previo;
            previo = adjs.get(selected.getId2());
        }
        if(previo.getTipo() == 'c') {
            int idc = previo.getId2();
            int temp = previo.getVolumenReal();
            previo.setVolumenFalso(previo.getVolumenFalso()-selected.getVolumenReal());
            if(previo.getVolumenFalso() - previo.getVolumenReal() < selected.getVolumenReal()) previo.setVolumenReal(previo.getVolumenReal()-(selected.getVolumenReal()-(previo.getVolumenFalso()-previo.getVolumenReal())));
            Calmacenamiento[idc] = Calmacenamiento[idc] - (temp - previo.getVolumenReal());
        }
    }

    public void simpleSolution() {
        int i = 0;//Sensor
        int j = 0;//Centro
        while (j < csize) {
            if (i == ssize) return;//Hemos puesto todos los sensores
            Sensor s = Snodes.get(i);
            adjs.put(i,new Edge(i,j,'c', calcularDistancia(Cnodes.get(j),s ),min((int)s.getCapacidad(), 150-Calmacenamiento[j]), (int)s.getCapacidad() ) );
            Calmacenamiento[j] = min(150 , Calmacenamiento[j] + (int)s.getCapacidad());
            ++Ccons[j];
            if (Ccons[j] == 25) ++j;
            ++i;
        }
        //Si he salido del bucle quedan sensores pero todos los centros están llenos
        //Ahora j serán los sensores ya conectados
        j = 0;
        while (i < ssize) {
            Sensor s1 = Snodes.get(i);
            Sensor s2 = Snodes.get(j);
            double d = adjs.get(j).getDistancia();
            int vr = adjs.get(j).getVolumenReal();
            Edge next = new Edge(i,j,'s', calcularDistancia(s1, s2), min(3*(int)s2.getCapacidad(), vr + (int)s1.getCapacidad()), (int)s1.getCapacidad());
            adjs.put(i, next);
            enfonsarVolumen(next);
            ++Scons[j];
            if (Scons[j] == 3) ++j;
            ++i;
        }
    }

    //Sacar coste

    public double getCoste() {
        double coste = 0;
        for (int i = 0; i < ssize; ++i) {
            coste += adjs.get(i).getDistancia()*adjs.get(i).getDistancia()+adjs.get(i).getVolumenFalso();
        }
        return coste;
    }

    public int getVolumen(){
        int volumen = 0;
        for (int i = 0; i < ssize; ++i) {
            if(adjs.get(i).getTipo() == 'c'){
                volumen += adjs.get(i).getVolumenReal();
            }
        }
        return volumen;
    }

    //Operadores:

    //Auxiliar para comprobar si se puede
    private boolean topologicalSort() {
        int[] cons = Scons.clone();
        HashSet<Integer> s = new HashSet<>();
        int count = ssize;
        for (int i = 0; i < ssize; ++i) {
            if (Scons[i]==0) s.add(i);
            --count;
        }
        if (count == ssize) return false;
        while (!s.isEmpty()) {
            Iterator<Integer> iterator = s.iterator();
            int node = iterator.next();
            iterator.remove();
            Edge edge = adjs.get(node);
            if (edge.getTipo() != 'c') {
                int son = edge.getId2();
                --cons[son];
                if (cons[son] == 0) {
                    s.add(son);
                    --count;
                }
            }
        }
        return count == 0;
    }
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

    public boolean switchDestination(int id1, int id2, char t) {
        Edge backup = adjs.get(id1);
        //antes de enfonsar, vemos si habrá ciclo
        adjs.put(id1,new Edge(id1,id2,t));
        if (!topologicalSort()) return false;
        //Ahora restauramos estado original y quitamos y ponemos con enfonsar
        adjs.remove(id1);
        if(backup.getTipo() == 'c') {
            int idc = backup.getId2();
            int temp = backup.getVolumenReal();
            Calmacenamiento[idc] = Calmacenamiento[idc] - (temp);
            --Ccons[idc];
        }
        else{
            desenfonsarVolumen(backup);
            --Scons[backup.getId2()];
        }
        adjs.put(id1, new Edge(id1,id2,t));
        adjs.get(id1).setVolumenFalso(backup.getVolumenFalso());
        if(t == 'c'){
            adjs.get(id1).setDistancia(calcularDistancia(Cnodes.get(id2), Snodes.get(id1)));
            adjs.get(id1).setVolumenReal(min(backup.getVolumenFalso(), 150-Calmacenamiento[id2]));
            ++Ccons[id2];
            Calmacenamiento[id2] = Calmacenamiento[id2] + adjs.get(id1).getVolumenReal();

        }
        else{
            adjs.get(id1).setDistancia(calcularDistancia(Snodes.get(id1), Snodes.get(id2)));
            adjs.get(id1).setVolumenReal(min(backup.getVolumenFalso(), 3*Snodes.get(id2).getCapacidad()-adjs.get(id2).getVolumenReal()));
            ++Scons[id2];
            enfonsarVolumen(adjs.get(id1));
        }
        return true;
    }

    public boolean jump(int id) {
        if (adjs.get(id).getTipo() == 'c') return false;
        //Primero vemos si podemos, quiza todos los sucesores estan llenos o no hay...
        Edge backup = adjs.get(id);


        int act = adjs.get(id).getId2();
        Edge actEdge = adjs.get(act);
        while (actEdge.getTipo() != 'c') {
            if (Scons[actEdge.getId2()] < 3) {
                adjs.remove(id);
                desenfonsarVolumen(backup);
                --Scons[backup.getId2()];

                adjs.put(id,new Edge(id,actEdge.getId2(),'s'));
                adjs.get(id).setVolumenFalso(backup.getVolumenFalso());
                adjs.get(id).setDistancia(calcularDistancia(Snodes.get(id), Snodes.get(actEdge.getId2())));
                adjs.get(id).setVolumenReal(min(backup.getVolumenFalso(), 3*Snodes.get(actEdge.getId2()).getCapacidad()- adjs.get(actEdge.getId2()).getVolumenReal()));
                ++Scons[actEdge.getId2()];
                enfonsarVolumen(adjs.get(id));
                return true;
            }
            act = adjs.get(act).getId2();
            actEdge = adjs.get(act);
        }
        return false;
    }

    public int getSsize() {
        return ssize;
    }
    public int getCsize() {
        return csize;
    }

    public Edge getEdge(int id) {return adjs.get(id);}

}
