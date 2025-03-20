import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

public class hillClimbingFinderSuccessorFunction implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList sucesores= new ArrayList<>();
        Estado estado = (Estado) aState;
        int sz = estado.getSsize();
        for (int id1 = 0; id1 < sz; ++id1) {
            for (int id2 = 0; id2 < sz; ++id2) {
                if (id1 != id2) sucesores.add(new Successor("E",new Estado(estado.getRepresentacion()).switchEdges(id1,id2)));
            }
        }
        int ss = estado.getSsize();
        int cs = estado.getCsize();
        for (int id1 = 0; id1 < ss; ++id1) {
            for (int id2 = 0; id2 < ss; ++id2) {
                if (estado.ableSwitch(id1,id2,'s')) sucesores.add(new Succesor("D",new Estado(estado.getRepresentacion()).switchDestination(id1,id2,'s')));
            }
            for (int id2 = 0; id2 < cs; ++id2) {
                if (estado.ableSwitch(id1,id2,'c')) sucesores.add(new Succesor("D",new Estado(estado.getRepresentacion()).switchDestination(id1,id2,'c')));
            }
        }
        return sucesores;
    }

}