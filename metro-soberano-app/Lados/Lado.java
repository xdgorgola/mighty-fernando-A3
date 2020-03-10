package Lados;
import Vertice.Vertice;

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

public abstract class Lado {
    
    protected int id;
    protected String linea;

    protected Vertice iVertice;
    protected Vertice fVertice;

    protected double peso;

    public int obtenerID(){
        return this.id;
    }
    
    public boolean incide(Vertice v){
        return v.equals(this.iVertice) || v.equals(this.fVertice);
    }

    public int obtenerTipo(){
        return this.id;
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