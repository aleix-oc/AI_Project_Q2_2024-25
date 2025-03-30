import IA.Red.*;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.lang.reflect.Array;
import javax.annotation.processing.ProcessingEnvironment;

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

    
    public Graph(Graph other) {
    this.ssize = other.ssize;
    this.csize = other.csize;
    this.Snodes = other.Snodes;
    this.Cnodes = other.Cnodes;
    
    this.Scons = other.Scons.clone();
    this.Ccons = other.Ccons.clone();
    this.Calmacenamiento = other.Calmacenamiento.clone();
    
    this.adjs = new HashMap<>();
    for (Map.Entry<Integer, Edge> entry : other.adjs.entrySet()) {
        this.adjs.put(entry.getKey(), new Edge(entry.getValue())); 
        }
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
        // Usamos TreeSet 

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
                    
                        Edge next = new Edge(id1, id2, t, d, min(3*capacidadp - previo.getVolumenReal(), capacidada), v2);
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
        int sum = selected.getVolumenReal();

        while(previo.getTipo() != 'c' && sum > 0){
            int temp = previo.getVolumenReal();
            int capacidadp = (int)Snodes.get(selected.getId2()).getCapacidad();
            int capacidadpp = (int)Snodes.get(previo.getId2()).getCapacidad();
            previo.setVolumenFalso(min(3*capacidadp, previo.getVolumenFalso()+sum));
            previo.setVolumenReal(min(previo.getVolumenReal()+sum, 3*capacidadpp-max(adjs.get(previo.getId2()).getVolumenReal() - previo.getVolumenReal(), capacidadpp)));
            sum = previo.getVolumenReal() - temp;
            selected = previo;
            previo = adjs.get(selected.getId2());
        }
        if(previo.getTipo() == 'c' && sum > 0) {

            int idc = previo.getId2();

            int capacidadp = (int)Snodes.get(selected.getId2()).getCapacidad();

            int temp = previo.getVolumenReal();
            previo.setVolumenFalso(min(3 * capacidadp, previo.getVolumenFalso() + sum));
            
            previo.setVolumenReal(min(temp + sum, temp + 150 - Calmacenamiento[idc]));
          
            Calmacenamiento[idc] = min(150, Calmacenamiento[idc] + previo.getVolumenReal() - temp);

        }

    }

    public void desenfonsarVolumen(Edge selected){

        int id2 = selected.getId2();

        Edge previo = adjs.get(id2);

        int rest = selected.getVolumenReal();
        while(previo.getTipo() != 'c' && rest > 0){
            int capacidadp = (int)Snodes.get(selected.getId2()).getCapacidad();
            
            int tempf = previo.getVolumenFalso();
            int tempr = previo.getVolumenReal();
            previo.setVolumenFalso(previo.getVolumenFalso()-rest);
            if(tempf - previo.getVolumenReal() < rest) previo.setVolumenReal(previo.getVolumenReal()-(rest-(tempf-previo.getVolumenReal())));

            rest = tempr - previo.getVolumenReal();
            selected = previo;
            previo = adjs.get(selected.getId2());
        }
        if(previo.getTipo() == 'c' && rest > 0) {
            int idc = previo.getId2();
            int tempr = previo.getVolumenReal();
            int tempf = previo.getVolumenFalso();
            previo.setVolumenFalso(previo.getVolumenFalso()-rest);
            if(tempf - previo.getVolumenReal() < rest) previo.setVolumenReal(previo.getVolumenReal()-(rest-(tempf-previo.getVolumenReal())));
            Calmacenamiento[idc] = Calmacenamiento[idc] - (tempr - previo.getVolumenReal());
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
            int vr = adjs.get(j).getVolumenReal();
            Edge next = new Edge(i,j,'s', calcularDistancia(s1, s2), min(3*(int)s2.getCapacidad() - vr,(int)s1.getCapacidad()), (int)s1.getCapacidad());
            adjs.put(i, next);
            enfonsarVolumen(next);
            ++Scons[j];
            if (Scons[j] == 3) ++j;
            ++i;
        }
    }


    public double getCoste() {
        double coste = 0;
        for (int i = 0; i < ssize; ++i) {
            coste += adjs.get(i).getDistancia()*adjs.get(i).getDistancia()*adjs.get(i).getVolumenFalso();
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
    public boolean topologicalSort() {
        int[] cons = Scons.clone();
        HashSet<Integer> s = new HashSet<>();
        int count = ssize;
        for (int i = 0; i < ssize; ++i) {
            if (Scons[i]==0) {
                s.add(i);
            --count;
            }
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
    public boolean switchEdges(int id1, int id2) {
        Edge backup1 = adjs.get(id1);
        Edge backup2 = adjs.get(id2);
        Edge a = new Edge(backup1);
        Edge b = new Edge(backup2);
        a.setId2(backup2.getId2());
        b.setId2(backup1.getId2());
        adjs.put(id1,a);
        adjs.put(id2,b);
        if(!topologicalSort()){
            adjs.put(id1, backup1);
            adjs.put(id2, backup2);
            return false;
        }
        if(backup1.getTipo() == 'c') {
            int idc = backup1.getId2();
            int temp = backup1.getVolumenReal();
            Calmacenamiento[idc] = Calmacenamiento[idc] - (temp);
            
            adjs.get(id2).setDistancia(calcularDistancia(Cnodes.get(idc), Snodes.get(id2)));
            adjs.get(id2).setVolumenReal(min(backup2.getVolumenFalso(), 150-Calmacenamiento[idc]));
            
            Calmacenamiento[idc] = Calmacenamiento[idc] + adjs.get(id2).getVolumenReal();
        }

        else{
            desenfonsarVolumen(backup1);
            ArrayList<Edge> enf = new ArrayList<>();
            for(int i=0; i<ssize;++i){
                if(adjs.get(i).getId2() == backup1.getId2()){
                    enf.add(adjs.get(i));
                    desenfonsarVolumen(adjs.get(i));
                }
            }
            for(int i=0; i<enf.size();++i){
                Edge en = enf.get(i);
                if(en.getTipo() == 'c'){
                    en.setVolumenReal(min(en.getVolumenFalso(), 150-Calmacenamiento[en.getId2()]));
                    Calmacenamiento[en.getId2()] = Calmacenamiento[en.getId2()] + en.getVolumenReal();
                }
                else {
                    en.setVolumenReal(min(en.getVolumenFalso(), 3*(int)Snodes.get(en.getId2()).getCapacidad()-max((int)Snodes.get(en.getId2()).getCapacidad(),  adjs.get(en.getId2()).getVolumenReal())));
                    enfonsarVolumen(en);
                }
            }
            int ids = backup1.getId2();
            adjs.get(id2).setDistancia(calcularDistancia(Snodes.get(id2), Snodes.get(ids)));
            adjs.get(id2).setVolumenReal(min(backup2.getVolumenFalso(), 3*(int)Snodes.get(ids).getCapacidad()-max((int)Snodes.get(ids).getCapacidad(),  adjs.get(ids).getVolumenReal())));
            enfonsarVolumen(adjs.get(id2));
        }

        if(backup2.getTipo() == 'c') {
            int idc = backup2.getId2();
            int temp = backup2.getVolumenReal();
            
            Calmacenamiento[idc] = Calmacenamiento[idc] - (temp);
            
            adjs.get(id1).setDistancia(calcularDistancia(Cnodes.get(idc), Snodes.get(id1)));
            adjs.get(id1).setVolumenReal(min(backup1.getVolumenFalso(), 150-Calmacenamiento[idc]));
            
            Calmacenamiento[idc] = Calmacenamiento[idc] + adjs.get(id1).getVolumenReal();
        }

        else{
            desenfonsarVolumen(backup2);
            ArrayList<Edge> enf = new ArrayList<>();
            for(int i=0; i<ssize;++i){
                if(adjs.get(i).getId2() == backup2.getId2()){
                    enf.add(adjs.get(i));
                    desenfonsarVolumen(adjs.get(i));
                }
            }
            for(int i=0; i<enf.size();++i){
                Edge en = enf.get(i);
                if(en.getTipo() == 'c'){
                    en.setVolumenReal(min(en.getVolumenFalso(), 150-Calmacenamiento[en.getId2()]));
                    Calmacenamiento[en.getId2()] = Calmacenamiento[en.getId2()] + en.getVolumenReal();
                }
                else {
                    en.setVolumenReal(min(en.getVolumenFalso(), 3*(int)Snodes.get(en.getId2()).getCapacidad()-max((int)Snodes.get(en.getId2()).getCapacidad(),  adjs.get(en.getId2()).getVolumenReal())));
                    enfonsarVolumen(en);
                }
            }
            int ids = backup2.getId2();
            adjs.get(id1).setDistancia(calcularDistancia(Snodes.get(id1), Snodes.get(ids)));
            adjs.get(id1).setVolumenReal(min(backup1.getVolumenFalso(), 3*(int)Snodes.get(ids).getCapacidad()-max((int)Snodes.get(ids).getCapacidad(),  adjs.get(ids).getVolumenReal())));
            enfonsarVolumen(adjs.get(id1));
        }

        return true;
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
        if(backup.getTipo() == 'c') --Ccons[backup.getId2()];
        else --Scons[backup.getId2()];
        adjs.put(id1,new Edge(id1,id2,t));
        if(t == 'c') ++Ccons[id2];
        else ++Scons[id2];
        if (!topologicalSort()) {
            adjs.put(id1,backup);
            if(backup.getTipo() == 'c') ++Ccons[backup.getId2()];
            else ++Scons[backup.getId2()];
            if(t == 'c') --Ccons[id2];
            else --Scons[id2];
            return false;

        }
        //Ahora restauramos estado original y quitamos y ponemos con enfonsar

        if(backup.getTipo() == 'c') {
            int idc = backup.getId2();
            int temp = backup.getVolumenReal();
            
            Calmacenamiento[idc] = Calmacenamiento[idc] - (temp);
        }
        else{
            desenfonsarVolumen(backup);
            ArrayList<Edge> enf = new ArrayList<>();
            for(int i=0; i<ssize;++i){
                if(adjs.get(i).getId2() == backup.getId2()){
                    enf.add(adjs.get(i));
                    desenfonsarVolumen(adjs.get(i));
                }
            }
            for(int i=0; i<enf.size();++i){
                Edge en = enf.get(i);
                if(en.getTipo() == 'c'){
                    en.setVolumenReal(min(en.getVolumenFalso(), 150-Calmacenamiento[en.getId2()]));
                    Calmacenamiento[en.getId2()] = Calmacenamiento[en.getId2()] + en.getVolumenReal();
                }
                else {
                    en.setVolumenReal(min(en.getVolumenFalso(), 3*(int)Snodes.get(en.getId2()).getCapacidad()-max((int)Snodes.get(en.getId2()).getCapacidad(),  adjs.get(en.getId2()).getVolumenReal())));
                    enfonsarVolumen(en);
                }
            }

        }
        adjs.put(id1, new Edge(id1,id2,t));
        adjs.get(id1).setVolumenFalso(backup.getVolumenFalso());

        if(t == 'c'){
            adjs.get(id1).setDistancia(calcularDistancia(Cnodes.get(id2), Snodes.get(id1)));
            adjs.get(id1).setVolumenReal(min(backup.getVolumenFalso(), 150-Calmacenamiento[id2]));
            
            Calmacenamiento[id2] = Calmacenamiento[id2] + adjs.get(id1).getVolumenReal();

        }
        else{
            adjs.get(id1).setDistancia(calcularDistancia(Snodes.get(id1), Snodes.get(id2)));
            adjs.get(id1).setVolumenReal(min(backup.getVolumenFalso(), 3*(int)Snodes.get(id2).getCapacidad()-max((int)Snodes.get(id2).getCapacidad(),  adjs.get(id2).getVolumenReal())));
            
            enfonsarVolumen(adjs.get(id1));
        }
        return true;
    }

    public int getSsize() {
        return ssize;
    }
    public int getCsize() {
        return csize;
    }

    public Edge getEdge(int id) {return adjs.get(id);}

    public int getadjssize(){
        return adjs.size();
    }
    public int[] getScons(){
        return Scons;
    }
    public int[] getCcons(){
        return Ccons;
    }
    public Sensores getSnodes(){
        return Snodes;
    }
    public int [] getAlmacenamiento(){
        return Calmacenamiento;
    }

}
