import IA.Red.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.Random;

public class ExperimentoDos{
    public static void main(String[] args) {
        for(int i=0; i<10; ++i){
            Random rand = new Random();
            int randomNum = rand.nextInt(10000);
            Sensores s = new Sensores(100,randomNum);
            randomNum = rand.nextInt(10000);
            CentrosDatos c = new CentrosDatos(4,randomNum);
            long tiempoInicial = System.currentTimeMillis();
            Graph gs = new Graph(s,c);
            Estado es = new Estado(gs);
            es.simpleSolution();
            
            Problem problem = new Problem(es, new hillClimbingFinderSuccessorFunction(), new goalTest(), new heuristicFunction());
            Search search = new HillClimbingSearch();
            try {
                SearchAgent agent = new SearchAgent(problem,search);
                System.out.println("Acciones:");
                System.out.println(agent.getActions());
                System.out.println("\nInstrumentación:");
                agent.getInstrumentation().forEach((key, value) -> System.out.println(key + " : " + value));
                Estado finalState = (Estado) search.getGoalState();
                System.out.println(finalState.getCoste());
                System.out.println(finalState.getVolumen());
                System.out.println(finalState.getCoste() - 2 * finalState.getVolumen() * finalState.getVolumen());
                long tiempoFinal = System.currentTimeMillis();
                long tiempoTranscurrido = tiempoFinal - tiempoInicial;
                System.out.println("El programa tardó " + tiempoTranscurrido + " milisegundos.");
            } catch (Exception e) {
                e.printStackTrace();
            }
            tiempoInicial = System.currentTimeMillis();
            Graph gc = new Graph(s,c);
            Estado ec = new Estado(gc);
            ec.complexSolution();

            problem = new Problem(ec, new hillClimbingFinderSuccessorFunction(), new goalTest(), new heuristicFunction());
            search = new HillClimbingSearch();
            try {
                SearchAgent agent = new SearchAgent(problem,search);
                System.out.println("Acciones:");
                System.out.println(agent.getActions());
                System.out.println("\nInstrumentación:");
                agent.getInstrumentation().forEach((key, value) -> System.out.println(key + " : " + value));
                Estado finalState = (Estado) search.getGoalState();
                
                System.out.println(finalState.getCoste());
                System.out.println(finalState.getVolumen());
                System.out.println(finalState.getCoste() - 2 * finalState.getVolumen() * finalState.getVolumen());
                long tiempoFinal = System.currentTimeMillis();
                long tiempoTranscurrido = tiempoFinal - tiempoInicial;
                System.out.println("El programa tardó " + tiempoTranscurrido + " milisegundos.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}