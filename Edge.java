import java.util.Objects;

public class Edge {
    private double distancia;
    private int id1;
    private int id2;
    private char tipo;

    // Constructor
    public Edge(int i1, int i2, char t) {
        this.id1 = i1;
        this.id2 = i2;
        this.tipo = t;
    }

    public Edge(int i1, int i2, char t, double d){
        this.id1 = i1;
        this.id2 = i2;
        this.tipo = t;
        this.distancia = d;
    }

    // Getters
    public int getId1() {
        return id1;
    }

    public int getId2() {
        return id2;
    }

    public char getTipo() {
        return tipo;
    }

    public double getDistancia() { return distancia; }

    // Setter
    public void setId1(int id1) { this.id1=id1; }
    public void setId2(int id2) { this.id2=id2; }
    public void setDistancia(double d) { this.distancia=d; }

}
