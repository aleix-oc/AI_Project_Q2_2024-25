//package proyecto??
import IA.Red.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class Main {
    public static void main(String[] args) {
        Sensores s = new Sensores(100,1234);
        CentrosDatos c = new CentrosDatos(4,1234);
        Graph g = new Graph(s,c);
        Estado st = new Estado(g);
        Problem problem = new Problem(st, new hillClimbingFinderSuccessorFunction(), new goalTest());
        Search search = new HillClimbingSearch();
        try {
            SearchAgent agent = new SearchAgent(problem,search);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
