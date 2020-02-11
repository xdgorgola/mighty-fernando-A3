public abstract class Lado {
    
    protected int id;

    protected Vertice iVertice;
    protected Vertice fVertice;

    protected double peso;

    public static int obtenerID(Lado l){
        return l.id;
    }

    public static double obtenerPeso(Lado l){
        return l.peso;
    }

    public static boolean incide(Lado l, Vertice v){
        return v.equals(l.iVertice) || v.equals(l.fVertice);
    }

    public static int obtenerTipo(Lado l){
        if (l.getClass().getSimpleName() == "Arista"){
            // 0 para arista
            return 0;
        }
        else{
            // 1 para arco
            return 1;
        }
    }

    public abstract String toString(Lado l);

    public Lado(int id, Vertice iVertice, Vertice fVertice, double peso) {
        this.id = id;
        this.iVertice = iVertice;
        this.fVertice = fVertice;
        this.peso = peso;
    }

    
}