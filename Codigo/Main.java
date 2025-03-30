//package proyecto??
import IA.Red.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import java.util.Random;
import java.util.List;
import java.util.concurrent.TimeUnit;
public class Main {
    public static void main(String[] args) {
        int i = 100;
        int l = 2;
        int[] seeds = {2212,1232,3463,5683,3478,4554,7886,2341,9876,4337};
        int[] seedsc = {6563,5525,2585,1473,8575,2644,4552,7855,7877,4150};
        
            double sum = 0.0;
        double sumt = 0.0;
        for (int k = 0; k < 10; k++) {
            int r1 = seeds[k];
            int r2 = seedsc[k];
            Sensores s = new Sensores(i,r1);
            CentrosDatos c = new CentrosDatos(l,r2);
            Graph g = new Graph(s,c);
            Estado st = new Estado(g);
            st.complexSolution();
            Problem problem = new Problem(st, new hillClimbingFinderSuccessorFunction(), new goalTest(), new heuristicFunction());
            Search search = new HillClimbingSearch();
            try {
                long startTime = System.currentTimeMillis();
                SearchAgent agent = new SearchAgent(problem,search);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
                
                Estado finalState = (Estado) search.getGoalState();
                System.out.println(finalState.getCoste());
                
        

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
}
    public static double experimento(int r1, int r2, int niter, int titer, int k, double lambda, int[] rValues) {
        Sensores s = new Sensores(100,r1);
        CentrosDatos c = new CentrosDatos(4,r2);
        Graph g = new Graph(s,c);
        Estado st = new Estado(g);
        st.complexSolution();
        Problem problem = new Problem(st, new simulatedAnnealingFinderSuccessorFunction(), new goalTest(), new heuristicFunction());
        Search search = new SimulatedAnnealingSearch(niter, titer, k, lambda);
        try {
            SearchAgent agent = new SearchAgent(problem,search);
            rValues[0] = search.getPathStates().size(); // Guardar r0
            Estado finalState = (Estado) search.getGoalState();
           return finalState.getCoste() - 2 * finalState.getVolumen() * finalState.getVolumen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
