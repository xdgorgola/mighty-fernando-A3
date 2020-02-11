public class Arco extends Lado{

    public static Vertice obtenerVerticeInicial(Arco a) {
        return a.iVertice;
    }

    public static Vertice obtenerVerticeFinal(Arco a){
        return a.fVertice;
    }

    @Override
    public String toString(Lado l) {
        return "id: " + l.id + "iNode: (" + Vertice.toString(l.iVertice) + ", " +
                Vertice.toString(fVertice) + ") weight: " + l.peso;
    }

    public Arco(int id, Vertice iVertice, Vertice fVertice, double weight) {
        super(id, iVertice, fVertice, weight);
    }
}