//package proyecto??
import IA.Red.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class Main {
    public static void main(String[] args) {
        int i = 100;
        int l = 4;
        Sensores s = new Sensores(i,4321);
        CentrosDatos c = new CentrosDatos(l,1234);
        Graph g = new Graph(s,c);
        

        /*int x2; 
        int y2;
        double distance;
        double coste = 0;
        int vol = 0;

        for(int j=0; j<i; ++j){
            Sensor si = s.get(j);
            int x1 = si.getCoordX();
            int y1 = si.getCoordY();
            Edge e = eee.getEdge(j);
            System.out.println("Id1:"+e.getId1());
            System.out.println("Id2:"+e.getId2());
            System.out.println(e.getTipo());
            System.out.println(e.getDistancia());
            /*if(e.getTipo()=='c'){
                int k = e.getId2();
                distance = g.calcularDistancia(x1,y1,c.get(k).getCoordX(),c.get(k).getCoordY());
            }
            else {
                int k = e.getId2();
                distance = g.calcularDistancia(x1,y1,s.get(k).getCoordX(),s.get(k).getCoordY());
            }
            //System.out.println(distance);/* */
            /*System.out.println(si.getCapacidad());
            System.out.println(e.getVolumenReal());
            System.out.println(e.getVolumenFalso());
            coste+=e.getDistancia()*e.getDistancia()*e.getVolumenFalso();
            if(e.getTipo()=='c') vol+=e.getVolumenReal();

            /*if(j==25 || j==26){
                Edge next = g.getEdge(e.getId2());
                System.out.println(s.get(e.getId2()).getCapacidad());
                System.out.println(next.getVolumenReal());
                System.out.println(next.getVolumenFalso());
            }/* */
        //}
        
        //System.out.println(g.getCoste());
        //System.out.println(coste);
        //System.out.println(g.getVolumen());
        //System.out.println(vol);
        Estado st = new Estado(g);
        st.complexSolution();
        System.out.println(st.getCoste());
        System.out.println(st.getVolumen());
        Problem problem = new Problem(st, new hillClimbingFinderSuccessorFunction(), new goalTest(), new heuristicFunction());
        Search search = new HillClimbingSearch();
        try {
            SearchAgent agent = new SearchAgent(problem,search);
            // Imprimir las acciones realizadas durante la búsqueda
            System.out.println("Acciones:");
            System.out.println(agent.getActions());

    // Imprimir la instrumentación (información sobre la búsqueda)
            System.out.println("\nInstrumentación:");
            agent.getInstrumentation().forEach((key, value) -> System.out.println(key + " : " + value));
            Estado finalState = (Estado) search.getGoalState();
            System.out.println(finalState.getCoste());
            System.out.println(finalState.getVolumen());
            int [] Scons = finalState.getScons();
            int [] Ccons = finalState.getCcons();
            int totals = 0;
            for(int j=0; j<i; ++j){
                Sensor si = s.get(j);
                int x1 = si.getCoordX();
                int y1 = si.getCoordY();
                Edge e = finalState.getEdge(j);
              //  System.out.println("Id1:"+e.getId1());
              //  System.out.println("Id2:"+e.getId2());
            //    System.out.println(e.getTipo());
               // System.out.println(e.getDistancia());
                /*if(e.getTipo()=='c'){
                    int k = e.getId2();
                    distance = g.calcularDistancia(x1,y1,c.get(k).getCoordX(),c.get(k).getCoordY());
                }
                else {
                    int k = e.getId2();
                    distance = g.calcularDistancia(x1,y1,s.get(k).getCoordX(),s.get(k).getCoordY());
                }
                //System.out.println(distance);/* */
          //      System.out.println(si.getCapacidad());
                totals+=si.getCapacidad();
          //      System.out.println(e.getVolumenReal());
           //     System.out.println(e.getVolumenFalso());
            //    System.out.println(Scons[j]);
                
            }
            System.out.println(Ccons[0]);
            System.out.println(finalState.getAlmacenamiento()[0]);
            System.out.println(totals);


    

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
