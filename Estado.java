

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

    public void switchDestination() {
        int ss = representacion.getSsize();
        int cs = representacion.getCsize();
        for (int id1 = 0; id1 < ss; ++id1) {
            for (int id2 = 0; id2 < ss; ++id2) if (representacion.ableSwitch(id1,id2,'s')) representacion.SwitchDestination(id1,id2,'s');
            for (int id2 = 0; id2 < cs; ++id2) if (representacion.ableSwitch(id1,id2,'c')) representacion.SwitchDestination(id1,id2,'c');
        }
    }

    public int heuristica() {
        return (int) representacion.getCoste() - representacion.getVolumen();
    } //habrá que implementar funciones privadas de calculo de coste y datos aunque sea en grafo.


}