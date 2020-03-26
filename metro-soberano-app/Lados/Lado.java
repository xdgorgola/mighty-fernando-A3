package Lados;
import Vertice.Vertice;

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

public abstract class Lado {
    
    protected int id;
    protected String linea;

    protected Vertice iVertice;
    protected Vertice fVertice;

    protected double peso;

    /**
     * Obtiene la ID del lado.
     * 
     * @return retorna la ID del lado
     */
    public int obtenerID(){
        return this.id;
    }
    
    /**
     * Obtiene la linea a la que pertenece el lado.
     * 
     * @return retorna la linea del lado
     */
    public String obtenerLinea(){
        return linea;
    }

    /**
     * Chequea si el lado incide en un vertice.
     * 
     * @param v Vertice a chequear incidencia
     * @return true si el vertice v incide en el lado/false de otro modo
     */
    public boolean incide(Vertice v){
        return v.obtenerID() == this.iVertice.obtenerID() || 
        v.obtenerID() == this.fVertice.obtenerID();
    }

    public int obtenerTipo(){
        return this.id;
    }

    public abstract String toString(Lado l);

    public Lado(int id, Vertice iVertice, Vertice fVertice, double peso, String linea) {
        this.id = id;
        this.iVertice = new Vertice(iVertice);
        this.fVertice = new Vertice(fVertice);
        this.peso = peso;
        this.linea = linea;
    }

    public Lado(Lado l){
        this.id = l.id;
        this.iVertice = new Vertice(l.iVertice);
        this.fVertice = new Vertice(l.fVertice);
        this.peso = l.peso;
        this.linea = l.linea;
    }
}