import Red.*;

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
    private boolean ableErase() {return representacion.ableErase();}

    //public??
    public void eraseEdge() {
        if (ableErase()) representacion.eraseEdge();
    }

    private boolean ableAdd() {return representacion.ableAdd();}

    public void addEdge() {
        if (ableAdd()) representacion.addEdge();
    }
}
