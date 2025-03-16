import java.util.Objects;

public class Edge {
    private int weight;
    private int id1;
    private int id2;
    private char tipo;

    // Constructor
    public Edge(int i1, int i2, char t) {
        this.id1 = i1;
        this.id2 = i2;
        this.tipo = t;
    }

    // Getters
    public int getId1() {
        return id1;
    }

    public int getId2() {
        return id2;
    }

    public int getWeight() {
        return weight;
    }

    public char getTipo() {
        return tipo;
    }

    // Setter
    public void setWeight(int w) {
        this.weight = w;
    }

    // Implementación de equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Son el mismo objeto en memoria
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return id1 == edge.id1 && id2 == edge.id2 && tipo == edge.tipo;
    }

    // Implementación de hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(id1, id2, tipo);
    }
}
