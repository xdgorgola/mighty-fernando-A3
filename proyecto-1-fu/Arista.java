public class Arista extends Lado{

    public Vertice getVertex1() {
        return iVertice;
    }

    public Vertice getVertex2(){
        return fVertice;
    }

    @Override
    public String toString() {
        return "id: " + id + "iNode: (" + iVertice.toString() + ", " +
                fVertice.toString() + ") weight: " + weight;
    }

    public Arista(int id, Vertice iVertice, Vertice fVertice, double weight) {
        super(id, iVertice, fVertice, weight);
    }
}