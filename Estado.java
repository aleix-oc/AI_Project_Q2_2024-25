

public class Estado {
    private static Graph representacion;
    public Estado(Graph g) {
        representacion=g;
    }
    private void simpleSolution() {

    }

    private void complexSol() {

    }

    /// OPERATORS
    public boolean ableErase() {return representacion.ableErase();}

    //public??
    public void eraseEdge() {
        if (ableErase()) representacion.eraseEdge();
    }

    public boolean ableAdd() {return representacion.ableAdd();}

    public void addEdge() {
        if (ableAdd()) representacion.addEdge();
    }

    public int heuristica() {} //habrá que implementar funciones privadas de calculo de coste y datos aunque sea en grafo.


}
