import Vertice.Vertice;
import Lados.*;

import java.util.PriorityQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;


// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

/**
 * Clase que contiene el algoritmo y programa principal para calcular el camino
 * mas rapido desde una estacion a otra
 */
public class MejorRuta {
    
    /**
     * Aplica el algoritmo de búsqueda A* para grafos no dirigidos.
     * 
     * @param g Grafo en el que se buscara
     * @param i Vertice desde el que se parte
     * @param f Vertice al que se llega
     * @return Camino mas rapido para llegar de i a f
     */
    public static NodePath AStarNoDirigido(GrafoNoDirigido g, Vertice i, Vertice f) {
        PriorityQueue<NodePath> openPaths = new PriorityQueue<>();

        NodePath p = new NodePath(i,f, g.getMaxSpeed());
        openPaths.add(p);

        while (!openPaths.isEmpty()) {
            NodePath pExtracted = new NodePath(openPaths.poll(), true);
            Vertice v = pExtracted.getLastVert();

            if (v.obtenerID() == f.obtenerID()) {
                return pExtracted;
            }
            
            for (Lado e : g.getLados()) {
                 if ( ((Arista)e).obtenerVertice1().obtenerID() == v.obtenerID() || ((Arista)e).obtenerVertice2().obtenerID() == v.obtenerID()){
                        NodePath pClone = new NodePath(pExtracted, true);
                        pClone.extendPathArista(((Arista)e));
                        openPaths.add(pClone);           
                }
            }
        } 
        return null;      
    }

    /**
     * Aplica el algoritmo de búsqueda A* para grafos dirigidos.
     * 
     * @param g Grafo en el que se buscara
     * @param i Vertice desde el que se parte
     * @param f Vertice al que se llega
     * @return Camino mas rapido para llegar de i a f
     */
    public static NodePath AStarDirigido(GrafoDirigido g, Vertice i, Vertice f) {
        PriorityQueue<NodePath> openPaths = new PriorityQueue<>();

        NodePath p = new NodePath(i, f, g.getMaxSpeed());
        openPaths.add(p);

        while (!openPaths.isEmpty()) {
            NodePath pExtracted = new NodePath(openPaths.poll(), true);
            Vertice v = pExtracted.getLastVert();

            if (v.obtenerID() == f.obtenerID()) {
                return pExtracted;
            }

            for (Lado e : g.getLados()) {
                 if ( ((Arco)e).obtenerVerticeInicial().obtenerID() == v.obtenerID()){
                        NodePath pClone = new NodePath(pExtracted, true);
                        pClone.extendPathArco(((Arco)e));
                        openPaths.add(pClone);           
                }
            }
        } 
        return null;      
    }

    public static void main(String[] args) throws FileNotFoundException {
        
        if (args.length != 4){
            System.err.println("No introdujiste los parametros pedidos!");
            return;
        }

        try {
            
            Scanner map = new Scanner(new File(args[0]));
            String t = map.nextLine().trim();
            int src = Integer.parseInt(args[2]);
            int dst = Integer.parseInt(args[3]);
            Grafo graph;
            Grafo act;
            NodePath all = null;
            NodePath activesBest = null;
            NodePath activesTransbor = null;
            switch (t) {
                case "d":
                    graph = new GrafoDirigido();
                    ((GrafoDirigido)graph).cargarGrafo(args[0]);
                    act = PlanearTransbordos.generateLinesGraphD((GrafoDirigido)graph, args[1]);
                    all = MejorRuta.AStarDirigido((GrafoDirigido)graph, ((GrafoDirigido)graph).obtenerVertice(src), ((GrafoDirigido)graph).obtenerVertice(dst));
                    activesBest = MejorRuta.AStarDirigido((GrafoDirigido)act, ((GrafoDirigido)act).obtenerVertice(src), ((GrafoDirigido)act).obtenerVertice(dst));
                    activesTransbor = PlanearTransbordos.getBestRouteD((GrafoDirigido)act, src, dst);
                    break;
                case "n":
                    graph = new GrafoNoDirigido();
                    ((GrafoNoDirigido)graph).cargarGrafo(args[0]);
                    act = PlanearTransbordos.generateLinesGraphND((GrafoNoDirigido)graph, args[1]);
                    all = MejorRuta.AStarNoDirigido((GrafoNoDirigido)graph, ((GrafoNoDirigido)graph).obtenerVertice(src), ((GrafoNoDirigido)graph).obtenerVertice(dst));
                    activesBest = MejorRuta.AStarNoDirigido((GrafoNoDirigido)act, ((GrafoNoDirigido)act).obtenerVertice(src), ((GrafoNoDirigido)act).obtenerVertice(dst));
                    activesTransbor = PlanearTransbordos.getBestRouteND((GrafoNoDirigido)act, src, dst);
                    break;
                default:
                    break;
            }
            map.close();
            if (all == null) {
                System.out.println("No se puede llegar a la estacion de ninguna forma.");
            }
            else{
                if (activesBest != null && activesTransbor != null){
                    System.out.println(activesBest.toString());
                    System.out.println();
                    System.out.println(activesTransbor.toString());
                }
                else{
                    System.out.println("No se puede llegar a la estacion unicamente con esas lineas!");
                }
            }
        } catch (Exception e) {
            if (e instanceof FileNotFoundException){
                System.err.println("Archivo no encontrado. Por favor revise el path especificado.");
            }
            else if (e instanceof NumberFormatException){
                System.err.println("Debería ir un número.");
            }
            else if (e instanceof NoSuchElementException){
                System.err.println("Una estacion introducida no existe en el mapa.");
            }
            return;
        }
    }
}