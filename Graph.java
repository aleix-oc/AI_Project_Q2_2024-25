import java.util.ArrayList;
import static java.lang.Math.min;
package.Red.*;

public class Graph {
    private static Sensores Snodes;
    private static CentrosDatos Cnodes;
    private ArrayList<Edge> adjs;

    public Graph(Sensores s,CentrosDatos c) {
        Snodes=s;
        Cnodes=c;
        adjs=new ArrayList<Edge>;

    }

    public fillEdges() {
        for (int i = 0; i < Snodes.size(); ++i) {
            for (int j = 0; j < Snodes.size(); ++j) if (i!=j) adjs.add(new Edge(i,j,'s'));
        }
        for (int i = 0; i < Snodes.size(); ++i) {
            for (int j = 0; j < Cnodes.size(); ++j) {
                adjs.add(new Edge(i,j,'c'));
                adjs.add(new Edge(j,i,'c'));
            }
        }
    }

    public simpleSolution() {
        int n = 3*Snodes.size();
        int x = 25*Cnodes.size();
        for (int i = 0; i < Cnodes.size(); ++i) {
            for (int j = 0; j < min(25,Snodes.size()); ++j) adjs.add(new Edge(i,))
        }
    }
}
