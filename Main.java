//Input: Número de semilla
public class Main {
    public static void main(String[] args) {
        Sensores s = new Sensores(100,1234);
        CentrosDatos c = new CentrosDatos(4,1234);
        Graph g = new Graph(s,c);
        Estado st = new Estado(g);
        Problem problem = new Problem(st, new hillClimbingFinderSuccessorFunction)
        Search search = new HillClimbingSearch();
        SearchAgent agent = new SearchAgent(problem,search);
    }
}