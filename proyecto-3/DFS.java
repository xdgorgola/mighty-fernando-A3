import java.util.ArrayList;

import Lados.Arco;
import Lados.Arista;
import Lados.Lado;
import Vertice.Vertice;

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

/**
 * Clase que contiene funciones basadas en DFS
 */
public class DFS {
    /**
     * Mediante DFS chequea si un vertice puede alcanzar a otro en un grafo dirigido
     * 
     * @param g Grafo dirigido
     * @param v1 Vertice inicial
     * @param v2 Vertice a alcanzar
     * @return un camino de v1 a v2/null si no existe
     */
    public static NodePath alcanzaDirigido(GrafoDirigido g, Vertice v1, Vertice v2){
        NodePath loAlcanza = null;
        NodePath init = new NodePath(v1);
        ArrayList<Integer> visitados = new ArrayList<Integer>();
        loAlcanza = alcanzaDirigidoRecursion(g, v2, visitados, init);
        return loAlcanza;
    }

    /**
     * Mediante DFS chequea si un vertice puede alcanzar a otro en un grafo dirigido
     * 
     * @param g Grafo dirigido
     * @param v2 Vertice a alcanzar
     * @param visitados Vertices ya visitados en el DFS
     * @param path Camino a expander
     * @return un camino de v1 a v2/null si no existe
     */
    private static NodePath alcanzaDirigidoRecursion(GrafoDirigido g, Vertice v2, 
        ArrayList<Integer> visitados, NodePath path){
        Vertice lastVert = path.getLastVert();
        visitados.add(lastVert.obtenerID());
        if (lastVert.obtenerID() == v2.obtenerID()){
            return path;
        }
        for (Lado lado : g.incidentes(lastVert.obtenerID())) {
            Arco arco = (Arco)lado;
            if (arco.obtenerVerticeInicial().obtenerID() == lastVert.obtenerID() &&
                !visitados.contains(arco.obtenerVerticeFinal().obtenerID())){
                    NodePath toTest = new NodePath(path);
                    toTest.extendPathArco(arco);
                    NodePath foundPath = alcanzaDirigidoRecursion(g, v2, visitados, toTest);
                    if (foundPath != null){
                        return foundPath;
                    }
                }
        }
        return null;
    }

    /**
     * Mediante DFS chequea si un vertice puede alcanzar a otro en un grafo no dirigido
     * 
     * @param g Grafo dirigido
     * @param v1 Vertice inicial
     * @param v2 Vertice a alcanzar
     * @return un camino de v1 a v2/null si no existe
     */
    public static NodePath alcanzaNoDirigido(GrafoNoDirigido g, Vertice v1, Vertice v2){
        NodePath loAlcanza = null;
        NodePath init = new NodePath(v1);
        ArrayList<Integer> visitados = new ArrayList<Integer>();
        loAlcanza = alcanzaNoDirigidoRecursion(g, v2, visitados, init);
        return loAlcanza;
    }

     /**
     * Mediante DFS chequea si un vertice puede alcanzar a otro en un grafo dirigido
     * 
     * @param g Grafo dirigido
     * @param v2 Vertice a alcanzar
     * @param visitados Vertices ya visitados en el DFS
     * @param path Camino a expander
     * @return un camino de v1 a v2/null si no existe
     */
    private static NodePath alcanzaNoDirigidoRecursion(GrafoNoDirigido g, Vertice v2, 
    ArrayList<Integer> visitados, NodePath path){
        Vertice lastVert = path.getLastVert();
        int idLast = lastVert.obtenerID();
        visitados.add(idLast);
        if (idLast == v2.obtenerID()){
            return path;
        }
        for (Lado lado : g.incidentes(lastVert.obtenerID())) {
            Arista arista = (Arista)lado;
            int id1 = arista.obtenerVertice1().obtenerID();
            int id2 = arista.obtenerVertice2().obtenerID();
            if ((id1 == idLast && !visitados.contains(id2)) || (id2 == idLast && !visitados.contains(id1))){
                NodePath toTest = new NodePath(path);
                toTest.extendPathArista(arista);
                NodePath foundPath = alcanzaNoDirigidoRecursion(g, v2, visitados, toTest);
                if (foundPath != null){
                    return foundPath;
                }
            }
        }
        return null;
    }
}