
import aima.search.framework.HeuristicFunction;

public class heuristicFuntction implements  HeuristifFunction {
    @Override
    public boolean equals(Object obj) {
        boolean retValue;

        retValue = super.equals(obj);
        return retValue;
    }

    public double getHeuristicValue(Object state) {
        Estado estado=(Estado)state;
        return (estado.heuristica());
    }
}