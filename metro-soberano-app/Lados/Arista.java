package Lados;

import Vertice.Vertice; 

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

public class Arista extends Lado{

    public Vertice obtenerVertice1() {
        return iVertice;
    }

    public Vertice obtenerVertice2(){
        return fVertice;
    }

    @Override
    public String toString(Lado l) {
        return "Arista id: " + l.id + "\nNodos: \niNode: (" + Vertice.toString(l.iVertice) + "\nfNode: (" +
                Vertice.toString(fVertice) + ") \nweight: " + l.peso;
    }

    public Arista(int id, Vertice iVertice, Vertice fVertice, double weight) {
        super(id, iVertice, fVertice, weight);
    }

    public Arista(Arista a){
        super(a);
    }
}