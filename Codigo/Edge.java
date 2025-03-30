
public class Edge {
    private double distancia;
    private int id1;
    private int id2;
    private char tipo;
    private int volumenReal;
    private int volumenFalso;

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

    public Edge(int i1, int i2, char t, double d, int v1, int v2){
        this.id1 = i1;
        this.id2 = i2;
        this.tipo = t;
        this.distancia = d;
        this.volumenReal = v1;
        this.volumenFalso = v2;
    }
    // Constructor de copia
    public Edge(Edge other) {
        this.id1 = other.id1;
        this.id2 = other.id2;
        this.tipo = other.tipo;
        this.distancia = other.distancia;
        this.volumenReal = other.volumenReal;
        this.volumenFalso = other.volumenFalso;
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

    public int getVolumenReal() { return volumenReal; }

    public int getVolumenFalso() { return volumenFalso; }

    // Setter
    public void setId1(int id1) { this.id1=id1; }
    public void setId2(int id2) { this.id2=id2; }
    public void setDistancia(double d) { this.distancia=d; }
    public void setVolumenReal(int v) { this.volumenReal = v; }
    public void setVolumenFalso(int v) { this.volumenFalso = v; }

}
