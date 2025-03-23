import IA.Red.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class Especial{
    public static void main(String[] args) {
        long tiempoInicial = System.currentTimeMillis();
        Sensores s = new Sensores(100,4321);
        CentrosDatos c = new CentrosDatos(4,1234);
        Graph g = new Graph(s,c);
        Estado st = new Estado(g);
        st.complexSolution();
        Problem problem = new Problem(st, new hillClimbingFinderSuccessorFunction(), new goalTest(), new heuristicFunction());
        Search search = new HillClimbingSearch();
        try {
            SearchAgent agent = new SearchAgent(problem,search);
            Estado finalState = (Estado) search.getGoalState();
            System.out.println(finalState.getCoste());
        } catch (Exception e) {
            e.printStackTrace();
        }
        long tiempoFinal = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoFinal - tiempoInicial;
        System.out.println("El programa tardó " + tiempoTranscurrido + " milisegundos.");
    }
}