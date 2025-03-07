
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

    public static int getId1() {
        return id1;
    }

    public static int getId2() {
        return id2;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int w) {
        weight = w;
    }

    public static char getTipo() {
        return tipo;
    }


}
