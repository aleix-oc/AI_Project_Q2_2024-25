package javadoc;

public class Edge {
    private int weight;
    private static int id1;
    private static int id2;
    //tipo tiene 2 valores: s, arista entre sensores; c, arista centro-sensor
    private static char tipo;

    public Edge(int i1, int i2, char t) {
        id1 = i1;
        id2 = i2;
        tipo = t;
    }

    public void setWeight(int w) {
        weight = w;
    }
}
