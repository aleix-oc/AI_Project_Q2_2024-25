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

        for (int id1 = 0; id1 < ss; ++id1) {
            for (int id2 = 0; id2 < ss; ++id2) {
                if (estado.ableSwitch(id1,id2,'s')) {
                    Estado aux = new Estado(estado.getRepresentacion());
                    if (aux.switchDestination(id1,id2,'s')){
                         sucesores.add(new Successor("D",aux));
                    }
                }
            }
            for (int id2 = 0; id2 < cs; ++id2) {
                if (estado.ableSwitch(id1,id2,'c')) {
                    Estado aux = new Estado(estado.getRepresentacion());
                    if (aux.switchDestination(id1,id2,'c')){
                         sucesores.add(new Successor("D",aux));
                    }
                }
            }
        }
        for (int id = 0; id < ss; ++id) {
            Edge e = estado.getEdge(id);
            if (e.getTipo() != 'c') e = estado.getEdge(e.getId2());
            else continue;
            Estado aux;
            while (e.getTipo() != 'c') {//Nos intentamos conectar a siguientes capas de sensores
                
                if (estado.ableSwitch(id,e.getId2(),'s')) {
                    aux = new Estado(estado.getRepresentacion());
                    if(aux.switchDestination(id,e.getId2(),'s')) sucesores.add(new Successor("J", aux));
                }
                e = estado.getEdge(e.getId2());
            }
            if (estado.ableSwitch(id,e.getId2(),'c')) {
                aux = new Estado(estado.getRepresentacion());
                if(aux.switchDestination(id,e.getId2(),'c')) sucesores.add(new Successor("J", aux));
            }
        }
        
        return sucesores;
    }

}