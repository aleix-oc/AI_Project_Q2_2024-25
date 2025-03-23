import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

public class hillClimbingFinderSuccessorFunction implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList sucesores= new ArrayList<>();
        Estado estado = (Estado) aState;
        int ss = estado.getSsize();
        int cs = estado.getCsize();
        int p = 0;
        int s = 0;
        /*for (int id1 = 0; id1 < ss; ++id1) {
            for (int id2 = 0; id2 < ss; ++id2) {
                if (id1 != id2) sucesores.add(new Successor("E",new Estado(estado.getRepresentacion()).switchEdges(id1,id2)));
            }
        }*/
        for (int id1 = 0; id1 < ss; ++id1) {
            for (int id2 = 0; id2 < ss; ++id2) {
                if (estado.ableSwitch(id1,id2,'s')) {
                    ++p;
                    Estado aux = new Estado(estado.getRepresentacion());
                    if (aux.switchDestination(id1,id2,'s')){
                        ++s;
                         sucesores.add(new Successor("D",aux));
                    }
                }
            }
            for (int id2 = 0; id2 < cs; ++id2) {
                if (estado.ableSwitch(id1,id2,'c')) {
                    ++p;
                    Estado aux = new Estado(estado.getRepresentacion());
                    if (aux.switchDestination(id1,id2,'c')){
                        ++s;
                         sucesores.add(new Successor("D",aux));
                    }
                }
            }
        }
        /*for (int id = 0; id < ss; ++id) {
            Estado aux = new Estado(estado.getRepresentacion());
            Edge e = aux.getEdge(id);
            if (aux.jump(id)) sucesores.add(new Successor("J",aux));
        }*/
       // System.out.println(p);
       // System.out.println(s);
       // System.out.println(sucesores.size());
        return sucesores;
    }

}