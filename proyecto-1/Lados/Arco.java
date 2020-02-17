package Lados;

import Vertice.Vertice; 

public class Arco extends Lado{

    public static Vertice obtenerVerticeInicial(Arco a) {
        return a.iVertice;
    }

    public static Vertice obtenerVerticeFinal(Arco a){
        return a.fVertice;
    }

    @Override
    public String toString(Lado l) {
        return "Arco id: " + l.id + "\nNodos: \niNode: (" + Vertice.toString(l.iVertice) + "\nfNode: (" +
                Vertice.toString(fVertice) + ") \nweight: " + l.peso;
    }

    public Arco(int id, Vertice iVertice, Vertice fVertice, double weight) {
        super(id, iVertice, fVertice, weight);
    }
}