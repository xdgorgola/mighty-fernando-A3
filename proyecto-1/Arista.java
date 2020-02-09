public class Arista extends Lado{

    public static Vertice getVertex1(Arista a) {
        return a.iVertice;
    }

    public static Vertice getVertex2(Arista a){
        return a.fVertice;
    }

    @Override
    public String toString(Lado l) {
        return "id: " + l.id + "iNode: (" + Vertice.toString(l.iVertice) + ", " +
                Vertice.toString(fVertice) + ") weight: " + l.weight;
    }

    public Arista(int id, Vertice iVertice, Vertice fVertice, double weight) {
        super(id, iVertice, fVertice, weight);
    }
}