

public class Estado {
    private static Graph representacion;
    public Estado(Graph g) {
        representacion=g;
    }
    private void simpleSolution() {
        representacion.simpleSolution();
    }

    private void complexSol() {
        representacion.complexSol();
    }

    public boolean switchEdges(int id1, int id2) { return representacion.switchEdges(id1, id2);}

    public boolean switchDestination(int id1, int id2, char t) { return representacion.switchDestination(id1,id2,t);}

    public boolean jump(int id) { return representacion.jump(id); }

    public int heuristica() {
        return (int) representacion.getCoste() - representacion.getVolumen();
    } //habrá que implementar funciones privadas de calculo de coste y datos aunque sea en grafo.

    public int getSsize() { return representacion.getSsize();}

    public int getCsize() { return representacion.getCsize();}
    
}
