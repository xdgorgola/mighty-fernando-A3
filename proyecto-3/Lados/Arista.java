package Lados;

import Vertice.Vertice; 

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

public class Arista extends Lado{

    /**
     * Obtiene vertice 1 de la arista.
     * 
     * @return retorna vertice 1 de la arista
     */
    public Vertice obtenerVertice1() {
        return iVertice;
    }

    /**
     * Obtiene vertice 2 de la arista.
     * 
     * @return retorna vertice 2 de la arista
     */
    public Vertice obtenerVertice2(){
        return fVertice;
    }

    @Override
    public String toString(Lado l) {
        return "Arista id: " + l.id + "\nNodos: \niNode: (" + Vertice.toString(l.iVertice) + "\nfNode: (" +
                Vertice.toString(fVertice) + ") \nweight: " + l.peso + "\nlinea: " + l.linea;
    }

    public Arista(int id, Vertice iVertice, Vertice fVertice, double weight, String linea) {
        super(id, iVertice, fVertice, weight, linea);
    }

    public Arista(Arista a){
        super(a);
    }
}