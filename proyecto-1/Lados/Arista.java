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
        return "id: " + l.id + "iNode: (" + Vertice.toString(l.iVertice) + ", " +
                Vertice.toString(fVertice) + ") weight: " + l.peso;
    }

    public Arista(int id, Vertice iVertice, Vertice fVertice, double weight) {
        super(id, iVertice, fVertice, weight);
    }
}