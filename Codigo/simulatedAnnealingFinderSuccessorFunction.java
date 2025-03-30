
import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.Random;
public class simulatedAnnealingFinderSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object aState){
        Random rand = new Random();
        ArrayList<Successor> sucesores= new ArrayList<>();
        Estado estado = (Estado) aState;
        int ss = estado.getSsize();
        int cs = estado.getCsize();
        int id1 = rand.nextInt(ss);
        boolean added = false;
          while(!added){
            int id2 = rand.nextInt(ss+cs);
            if(id2 < ss){
                if (estado.ableSwitch(id1,id2,'s')) {
                    Estado aux = new Estado(estado.getRepresentacion());
                    if (aux.switchDestination(id1,id2,'s')){
                         sucesores.add(new Successor("D",aux));
                         added = true;
                    }
                }
            }
            else {
                id2 -= ss;
                if (estado.ableSwitch(id1,id2,'c')) {
                    Estado aux = new Estado(estado.getRepresentacion());
                    if (aux.switchDestination(id1,id2,'c')){
                         sucesores.add(new Successor("D",aux));
                         added = true;
                    }
                }
            }
          }
        return sucesores;
    }
}