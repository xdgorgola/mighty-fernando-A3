import Lados.Arco;
import Lados.Arista;
import Lados.Lado;
import Vertice.Vertice;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

/**
 * Clase que contiene el algoritmo y programa principal para calcular
 * el minimo numero de transbordos con su ruta asociada de una estacion
 * a otra
 */
public class PlanearTransbordos {

    /**
     * Elimina de un grafo dirigido aquellos arcos que no pertenezcan a un set de lineas definidas en un archivo.
     * 
     * @param base Grafo base
     * @param lines Path de archivo que contiene lineas activas
     * @return Grafo con solo las lineas activas
     * @throws FileNotFoundException
     */
    public static GrafoDirigido generateLinesGraphD(GrafoDirigido base, String lines) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(lines));

        // Clonamos el grafo base
        GrafoDirigido g = (GrafoDirigido)base.clone();
        
        // Agregamos las lineas activas desde el archivo
        ArrayList<String> lineas = new ArrayList<String>();
        while (scanner.hasNextLine()){
            lineas.add(scanner.nextLine().trim());
        }

        // Revisamos todos los lados del grafo y si este no pertenece a una linea activa, lo eliminamos
        ArrayList<Lado> lados = new ArrayList<Lado>(g.lados());
        for (Lado lado : lados) {
            if (!lineas.contains(lado.obtenerLinea())){
                String vi = ((Arco)lado).obtenerVerticeInicial().obtenerNombre();
                String vf = ((Arco)lado).obtenerVerticeFinal().obtenerNombre();
                g.eliminarArco(vi, vf, lado.obtenerID());
            }
        }
        return g;
    }


    /**
     * Elimina de un grafo no dirigido aquellas aristas que no pertenezcan a un set de lineas definidas en un archivo.
     * 
     * @param base Grafo base
     * @param lines Path de archivo que contiene lineas activas
     * @return Grafo con solo las lineas activas
     * @throws FileNotFoundException
     */
    public static GrafoNoDirigido generateLinesGraphND(GrafoNoDirigido base, String lines) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(lines));
        
        // Clonamos el grafo
        GrafoNoDirigido g = (GrafoNoDirigido)base.clone();
        
        // Agregamos las lineas activas desde el archivo
        ArrayList<String> lineas = new ArrayList<String>();
        while (scanner.hasNextLine()){
            lineas.add(scanner.nextLine().trim());
        }

        // Revisamos todos los lados del grafo y si este no pertenece a una linea activa, lo eliminamos
        ArrayList<Lado> lados = new ArrayList<Lado>(g.lados());
        for (Lado lado : lados) {
            if (!lineas.contains(lado.obtenerLinea())){
                g.eliminarArista(lado.obtenerID());
            }
        }
        return g;
    }


    /**
     * Consigue la ruta con menor numero de trasbordos en un grafo no dirigido.
     * Usa la estrategia backtracking.
     * 
     * @param g Grafo a buscar ruta
     * @param src Estacion inicial
     * @param dst Estacion destino
     * @return Ruta con menor numero de trasbordos entre src y dst
     */
    public static NodePath getBestRouteND(GrafoNoDirigido g, int src, int dst){
        if (!g.estaVertice(src) || !g.estaVertice(dst)) {
            System.err.println("Una de las estaciones no se encuentra en el mapa!");
            return null;
        }
        // Calcula si es alcanzable consiguiendo un camino de src a dst cualquiera.
        // este camino puede no ser el mejor pero se toma como el mejor para evitar
        // explorar soluciones mas costosas!
        NodePath randomPath = DFS.alcanzaNoDirigido(g, g.obtenerVertice(src), g.obtenerVertice(dst));
        if (randomPath == null){
            return null;
        }
        randomPath.calculateTrasbordosCost();
        
        // Si es alcanzable
        System.out.println("Planeando...");
        NodePath initial = new NodePath(g.obtenerVertice(src));
        NodePath best  = PlanearTransbordos.getBestRouteRecurND(g, src, dst, initial, randomPath);
        // Si sigue siendo null es que nunca llego jejejejje
        // se usa null para representar el mejor camino no encontrado aun
        return best;
    }


    /**
     * Consigue la ruta con menor numero de trasbordos en un grafo no dirigido. Parte recursiva interna.
     * Usa la estrategia backtracking.
     * 
     * @param g Grafo a buscar ruta
     * @param src Estacion inicial
     * @param dst Estacion destino
     * @param act Camino a chequear/explorar
     * @param best Mejor camino conseguido en el backtracking hasta ahora
     * @return Ruta con menor numero de trasbordos entre src y dst conseguida hasta los momentos
     */
    public static NodePath getBestRouteRecurND(GrafoNoDirigido g, int src, int dst, NodePath act, NodePath best) {
        // Chequeamos si es solucion y si es mejor ruta que la mejor ruta actual
        if (PlanearTransbordos.isSolution(act, src, dst)){
            if (PlanearTransbordos.isWorth(act, best)) return act;
            return best;
        }
        
        // Buscamos todos los posibes tramos que se pueden tomar y expadimos el camino y probamos
        Vertice ult = act.getLastVert();
        for (Lado suc : PlanearTransbordos.getValidEdgesND(g, act, ult)) {
            NodePath testSol = new NodePath(act);
            testSol.extendPathArista((Arista)suc);
            if (isWorth(testSol, best)){
                best = PlanearTransbordos.getBestRouteRecurND(g, src, dst, testSol, best);
            }
        }
        return best;
    }


     /**
     * Consigue la ruta con menor numero de trasbordos en un grafo dirigido.
     * Usa la estrategia backtracking.
     * 
     * @param g Grafo a buscar ruta
     * @param src Estacion inicial
     * @param dst Estacion destino
     * @return Ruta con menor numero de trasbordos entre src y dst
     */
    public static NodePath getBestRouteD(GrafoDirigido g, int src, int dst) {
        if (!g.estaVertice(src) || !g.estaVertice(dst)) {
            System.err.println("Una de las estaciones no se encuentra en el mapa!");
            return null;
        }
        NodePath randomPath = DFS.alcanzaDirigido(g, g.obtenerVertice(src), g.obtenerVertice(dst));
        if (randomPath == null){
            return null;
        }
        randomPath.calculateTrasbordosCost();

        System.out.println("Planeando...");
        NodePath initial = new NodePath(g.obtenerVertice(src));
        NodePath best  = PlanearTransbordos.getBestRouteRecurD(g, src, dst, initial, randomPath);
        // Si sigue siendo null es que nunca llego jejejejje
        // se usa null para representar el mejor camino no encontrado aun
        return best;
    }


    /**
     * Consigue la ruta con menor numero de trasbordos en un grafo no dirigido. Parte recursiva interna.
     * Usa la estrategia backtracking.
     * 
     * @param g Grafo a buscar ruta
     * @param src Estacion inicial
     * @param dst Estacion destino
     * @param act Camino a chequear/explorar
     * @param best Mejor camino conseguido en el backtracking hasta ahora
     * @return Ruta con menor numero de trasbordos entre src y dst conseguida hasta los momentos
     */
    public static NodePath getBestRouteRecurD(GrafoDirigido g, int src, int dst, NodePath act, NodePath best) {
        if (PlanearTransbordos.isSolution(act, src, dst)){
            if (PlanearTransbordos.isWorth(act, best)) return act;
            return best;
        }
        Vertice ult = act.getLastVert();
        for (Lado suc : PlanearTransbordos.getValidEdgesD(g, act, ult)) {
            NodePath testSol = new NodePath(act);
            testSol.extendPathArco((Arco)suc);
            if (isWorth(testSol, best)){
                best = PlanearTransbordos.getBestRouteRecurD(g, src, dst, testSol, best);
            }
        }
        return best;
    }


    /**
     * Indica si un camino va de src a dst.
     * 
     * @param toTest Camino a chequear si es solucion
     * @param src Estacion fuente
     * @param dst Estacion destino
     * @return Si el camino va de src a dst
     */
    public static boolean isSolution(NodePath toTest, int src, int dst){
        // Chequeo para evitar error
        if (toTest.getPathE().size() == 0) return false;
        Vertice init = toTest.getFirstVert();
        Vertice fin = toTest.getLastVert();
        return init.obtenerID() == src && fin.obtenerID() == dst;
    }

    
    /**
     * Indica si vale la pena explorar un camino. Un camino (no tiene que ser final) vale la pena explorarlo si su costo actual
     * es menor que el costo del mejor conseguido hasta ahora.
     *  
     * @param toTest Camino a chequear si vale la pena
     * @param best El mejor camino hasta los momentos
     * @return Si vale la pena explorar el camino
     */
    public static boolean isWorth(NodePath toTest, NodePath best){
        // null indica que no hay mejor camino aun.
        if (best == null) return true;
        return toTest.getCostTrasbordos() < best.getCostTrasbordos();
    }


    /**
     * Busca los lados/tramos que se pueden tomar para expandir un camino. No son lados validos aquellos que ya hayan sido
     * visitados en el camino. Version para grafos no dirigidos.
     * 
     * @param g Grafo a buscar tramos validos
     * @param act Camino a buscar tramos validos
     * @param last Ultimo vertice
     * @return Tramos validos para expandir un camino
     */
    public static LinkedList<Lado> getValidEdgesND(GrafoNoDirigido g, NodePath act, Vertice last){
        int lastID = last.obtenerID();
        LinkedList<Lado> adys = g.incidentes(lastID);
        LinkedList<Lado> finals = new LinkedList<Lado>();
        if (act.getPathE().size() > 0) adys.remove(act.getLastEdge());
        // Si no fue recorrido ya esa arista y el vertice/estacion no ha sido visitado antes en el camino
        for (Lado lado : adys) {
            Arista arista = (Arista)lado;
            if (!act.isBeforeLastLado(lado) ){
                if (last.obtenerID() == arista.obtenerVertice1().obtenerID()){
                    if (!act.isBeforeLastVert(arista.obtenerVertice2())){
                        finals.add(lado);
                    }
                }
                else if (last.obtenerID() == arista.obtenerVertice2().obtenerID()){
                    if (!act.isBeforeLastVert(arista.obtenerVertice1())){
                        finals.add(lado);
                    }
                }
            }
        }   
        return finals;
    }


    /**
     * Busca los lados/tramos que se pueden tomar para expandir un camino. No son lados validos aquellos que ya hayan sido
     * visitados en el camino y donde el ultimo vertice no sea el inicial del arco. Version para grafos dirigidos.
     * 
     * @param g Grafo a buscar tramos validos
     * @param act Camino a buscar tramos validos
     * @param last Ultimo vertice
     * @return Tramos validos para expandir un camino
     */
    public static LinkedList<Lado> getValidEdgesD(GrafoDirigido g, NodePath act, Vertice last){
        int lastID = last.obtenerID();
        // Lados incidentes en el ultimo vertice del path
        LinkedList<Lado> incs = g.incidentes(lastID);
        // Lados finales
        LinkedList<Lado> finals = new LinkedList<Lado>();
        // Si el path es de largo minimo 1, quitamos de incidentes el ultimo vertice que claramente incide
        // pero ya fue usado
        if (act.getPathE().size() > 0) incs.remove(act.getLastEdge());
        for (Lado lado : incs) {
            Arco arco = (Arco)lado;
            // Si no fue recorrido ya ese arco, ademas el ult vertice es vertice inicial y
            // el vertice/estacion no ha sido visitado antes en el camino
            if (!act.isBeforeLastLado(arco) && arco.obtenerVerticeInicial().obtenerID() == last.obtenerID() && 
            !act.getPathV().contains(arco.obtenerVerticeFinal()))
                finals.add(lado);
            }
        return finals;
    }

    public static void main(String[] args){
        if (args.length != 4){
            System.err.println("No introdujiste los parametros pedidos!");
            return;
        }
        try {
            Scanner map = new Scanner(new File(args[0]));
            String t = map.nextLine().trim();
            int src = Integer.parseInt(args[2]);
            int dst = Integer.parseInt(args[3]);
            Grafo g;
            Grafo act;
            NodePath all = null;
            NodePath actives = null;
            switch (t) {
                case "d":
                    g = new GrafoDirigido();
                    ((GrafoDirigido)g).cargarGrafo(args[0]);
                    act = PlanearTransbordos.generateLinesGraphD((GrafoDirigido)g, args[1]);
                    all = PlanearTransbordos.getBestRouteD((GrafoDirigido)g, src, dst);
                    actives = PlanearTransbordos.getBestRouteD((GrafoDirigido)act, src, dst);
                    break;
                case "n":
                    g = new GrafoNoDirigido();
                    ((GrafoNoDirigido)g).cargarGrafo(args[0]);
                    act = PlanearTransbordos.generateLinesGraphND((GrafoNoDirigido)g, args[1]);
                    all = PlanearTransbordos.getBestRouteND((GrafoNoDirigido)g, src, dst);
                    actives = PlanearTransbordos.getBestRouteND((GrafoNoDirigido)act, src, dst);
                    break;
                default:
                    break;
            }
            map.close();
            if (all == null) {
                System.out.println("No se puede llegar a la estacion de ninguna forma :(");
            }
            else{
                System.out.println(all.toString());
                System.out.println();
                if (actives != null){
                    System.out.println(actives.toString());
                }
                else{
                    System.out.println("No se puede llegar a la estacion unicamente con esas lineas!");
                }
            }
        } catch (Exception e) {
            if (e instanceof FileNotFoundException){
                System.err.println("Archivo no encontrado! Por favor revise el path especificado.");
            }
            else if (e instanceof NumberFormatException){
                System.err.println("Introdujiste algo que no es numero donde deberia ir uno :(");
            }
            else if (e instanceof NoSuchElementException){
                System.err.println("Una estacion introducida no existe en el mapa :(");
            }
            return;
        }
    }
}