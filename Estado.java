

public class Estado {
    private static Graph representacion;
    public Estado(Graph g) {
        representacion=g;
    }
    private void simpleSolution() {
        representacion.simpleSolution();
    }

    private void complexSol() {

    }

    public void switchEdges() {
        int sz = representacion.getSsize();
        for (int id1 = 0; id1 < sz; ++id1) for (int id2 = 0; id2 < sz; ++id2) if (id1 != id2) representacion.switchEdges(id1,id2);
    }

    public void SwitchDestination() {
        int sz = representacion.getSsize();
        for (int id = 0; id < sz; ++id) if (ableSwitchDestination(id)) representacion.SwitchDestination(id);
    }

    public int heuristica() {} //habrá que implementar funciones privadas de calculo de coste y datos aunque sea en grafo.


}