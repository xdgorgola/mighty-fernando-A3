public abstract class Lado {
    
    protected int id;

    protected Vertice iVertice;
    protected Vertice fVertice;

    protected double weight;

    public int getID(){
        return id;
    }

    public double getWeight(){
        return weight;
    }

    public boolean incides(Vertice v){
        return v.equals(iVertice) || v.equals(fVertice);
    }

    public static int getType(Lado l){
        // Para aristas!
        return 0;
    }

    public abstract String toString();

    public Lado(int id, Vertice iVertice, Vertice fVertice, double weight) {
        this.id = id;
        this.iVertice = iVertice;
        this.fVertice = fVertice;
        this.weight = weight;
    }

    
}