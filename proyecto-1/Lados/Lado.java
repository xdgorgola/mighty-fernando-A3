package Lados;
import Vertice.Vertice;

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

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
        return l.id;
    }

    public abstract String toString(Lado l);

    public Lado(int id, Vertice iVertice, Vertice fVertice, double peso) {
        this.id = id;
        this.iVertice = new Vertice(iVertice);
        this.fVertice = new Vertice(fVertice);
        this.peso = peso;
    }

    public Lado(Lado l){
        this.id = l.id;
        this.iVertice = new Vertice(l.iVertice);
        this.fVertice = new Vertice(l.fVertice);
        this.peso = l.peso;
    }
}