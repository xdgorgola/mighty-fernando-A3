package Lados;

import Vertice.Vertice; 

public class Arista extends Lado{

    public static Vertice obtenerVertice1(Arista a) {
        return a.iVertice;
    }

    public static Vertice obtenerVertice2(Arista a){
        return a.fVertice;
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