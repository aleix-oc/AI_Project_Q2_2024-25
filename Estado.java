

public class Estado {
    private static Graph representacion;
    public Estado(Graph g) {
        representacion=g;
    }
    private void simpleSolution() {
        representacion.simpleSolution();
    }

    private void complexSolution() {
        representacion.complexSolution();
    }

    public Edge getEdge(int id) {return representacion.getEdge(id);}

    public boolean ableSwitch(int id1, int id2, char t) { return representacion.ableSwitch(id1,id2,t);}

    public void switchEdges(int id1, int id2) { representacion.switchEdges(id1, id2);}

    public boolean switchDestination(int id1, int id2, char t) { return representacion.switchDestination(id1,id2,t);}

    public boolean jump(int id) { return representacion.jump(id); }

    public int heuristica() {
        return (int) representacion.getCoste() - representacion.getVolumen();
    } //habrá que implementar funciones privadas de calculo de coste y datos aunque sea en grafo.

    public int getSsize() { return representacion.getSsize();}

    public int getCsize() { return representacion.getCsize();}

    public static Graph getRepresentacion() {
        return representacion;
    }
}
