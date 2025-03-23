
import static java.lang.Math.sqrt;

import IA.Red.*;


public class Estado {
    private Graph representacion;
    public Estado(Graph g) {
        representacion = new Graph(g);
    }
    public void simpleSolution() {
        representacion.simpleSolution();
    }

    public void complexSolution() {
        representacion.complexSolution();
    }

    public Edge getEdge(int id) {return representacion.getEdge(id);}

    public boolean ableSwitch(int id1, int id2, char t) { return representacion.ableSwitch(id1,id2,t);}

    public void switchEdges(int id1, int id2) { representacion.switchEdges(id1, id2);}

    public boolean switchDestination(int id1, int id2, char t) { return representacion.switchDestination(id1,id2,t);}

    public boolean jump(int id) { return representacion.jump(id); }

    public double heuristica() {
       // double r = 0;
        //int caps = 0;
        //for(int i=0; i<representacion.getSsize(); ++i){
          //  caps += representacion.getSnodes().get(i).getCapacidad();
        //    if(representacion.getEdge(i).getVolumenFalso() < (int) representacion.getSnodes().get(i).getCapacidad() || representacion.getEdge(i).getVolumenReal()<0 || representacion.getEdge(i).getVolumenFalso() > 3*(int) representacion.getSnodes().get(i).getCapacidad() || representacion.getEdge(i).getVolumenReal() > 3*(int) representacion.getSnodes().get(i).getCapacidad()) r = Double.MAX_VALUE;
        //}
        //if(representacion.getVolumen() > caps) r = Double.MAX_VALUE;
        //if(r==0) r = representacion.getCoste() -  representacion.getSsize()* representacion.getVolumen() * representacion.getVolumen();
        return representacion.getCoste() - representacion.getSsize() * representacion.getVolumen() * representacion.getVolumen() * representacion.getVolumen();
    } //habrá que implementar funciones privadas de calculo de coste y datos aunque sea en grafo.

    public int getSsize() { return representacion.getSsize();}

    public int getCsize() { return representacion.getCsize();}

    public Graph getRepresentacion() {
        return representacion;
    }
    public double getCoste() {
        return representacion.getCoste();
    }
    public int getVolumen() {
        return representacion.getVolumen();
    }
    public int [] getScons(){
        return representacion.getScons();
    }
    public int [] getCcons(){
        return representacion.getCcons();
    }
    public Sensores getSnodes(){
        return representacion.getSnodes();
    }
    public int [] getAlmacenamiento(){
        return representacion.getAlmacenamiento();
    }
}
