package Lados;

import Vertice.Vertice; 

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

public class Arco extends Lado{

    /**
     * Obtiene el vertice inicial del arco.
     * 
     * @return retorna vertice inicial del arco
     */
    public Vertice obtenerVerticeInicial() {
        return iVertice;
    }

    /**
     * Obtiene el vertice final del arco.
     * 
     * @return retorna vertice final del arco
     */
    public Vertice obtenerVerticeFinal(){
        return fVertice;
    }

    @Override
    public String toString(Lado l) {
        return "Arco id: " + l.id + "\nNodos: \niNode: (" + Vertice.toString(l.iVertice) + "\nfNode: (" +
                Vertice.toString(fVertice) + ") \nweight: " + l.peso + "\nlinea: " + l.linea;
    }

    public Arco(int id, Vertice iVertice, Vertice fVertice, double weight, String linea) {
        super(id, iVertice, fVertice, weight, linea);
    }


    public Arco(Arco a){
        super(a);
    }
}