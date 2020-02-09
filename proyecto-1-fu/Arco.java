public class Arco extends Lado{

    public Vertice getInitialVertex() {
        return iVertice;
    }

    public Vertice getFinalVertex(){
        return fVertice;
    }

    @Override
    public String toString() {
        return "id: " + id + "iNode: (" + iVertice.toString() + ", " +
                fVertice.toString() + ") weight: " + weight;
    }

    public Arco(int id, Vertice iVertice, Vertice fVertice, double weight) {
        super(id, iVertice, fVertice, weight);
    }
}