public abstract class Lado {
    
    protected int id;

    protected Vertice iVertice;
    protected Vertice fVertice;

    protected double weight;

    public static int getID(Lado l){
        return l.id;
    }

    public static double getWeight(Lado l){
        return l.weight;
    }

    public static boolean incides(Lado l, Vertice v){
        return v.equals(l.iVertice) || v.equals(l.fVertice);
    }

    public static int getType(Lado l){
        return 0;
    }

    public abstract String toString(Lado l);

    public Lado(int id, Vertice iVertice, Vertice fVertice, double weight) {
        this.id = id;
        this.iVertice = iVertice;
        this.fVertice = fVertice;
        this.weight = weight;
    }

    
}