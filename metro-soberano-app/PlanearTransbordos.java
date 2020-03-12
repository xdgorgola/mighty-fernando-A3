import Lados.Arco;
import Lados.Arista;
import Lados.Lado;
import Vertice.Vertice;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

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
    public static NodePath getBestRouteND(GrafoNoDirigido g, int src, int dst) {
        NodePath initial = new NodePath(g.obtenerVertice(src));
        NodePath best  = PlanearTransbordos.getBestRouteRecurND(g, src, dst, initial, null);
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
        NodePath initial = new NodePath(g.obtenerVertice(src));
        NodePath best  = PlanearTransbordos.getBestRouteRecurD(g, src, dst, initial, null);
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
        for (Lado lado : adys) {
            if (!act.isBeforeLast(lado)) finals.add(lado);
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
            // Si no fue recorrido ya ese arco y ademas el ult vertice es vertice inicial, agarralo
            if (!act.isBeforeLast(lado) && ((Arco)lado).obtenerVerticeInicial().obtenerID() == last.obtenerID())
                finals.add(lado);
            }
        return finals;
    }

    public static void main(String[] args) throws FileNotFoundException{
        GrafoNoDirigido g = new GrafoNoDirigido();
        g.cargarGrafo("Caracas.txt");
        NodePath xd = PlanearTransbordos.getBestRouteND(g, 3, 6);
        if (xd == null){
            System.out.println("Puta no llegas.");
            return;
        }
        xd.calculateTrasbordosCost();
        System.out.println("Total trasbordos!: " + xd.getCostTrasbordos());
        System.out.println(xd.toString());

        GrafoNoDirigido otro = PlanearTransbordos.generateLinesGraphND(g, "Lineas.txt");
        xd = PlanearTransbordos.getBestRouteND(otro, 3, 6);
        if (xd == null){
            System.out.println("Puta no llegas.");
            return;
        }
        xd.calculateTrasbordosCost();
        System.out.println("Total trasbordos!: " + xd.getCostTrasbordos());
        System.out.println(xd.toString());
    
        //GrafoDirigido g = new GrafoDirigido();
        ////g.cargarGrafo("Londres (copy 1).txt");
        //g.cargarGrafo("LondresD.txt");
        //NodePath xd = MejorRuta.getBestRouteD(g, 105, 101);
        //if (xd == null){
        //    System.out.println("Puta no llegas.");
        //    return;
        //}
        //xd.calculateTrasbordosCost();
        //System.out.println("Total trasbordos!: " + xd.getCostTrasbordos());
    //
        //GrafoDirigido otro = MejorRuta.generateLinesGraphD(g, "Lineas.txt");
        //xd = MejorRuta.getBestRouteD(otro, 105, 101);
        //if (xd == null){
        //    System.out.println("Puta no llegas.");
        //    return;
        //}
        //xd.calculateTrasbordosCost();
        //System.out.println("Total trasbordos!: " + xd.getCostTrasbordos());
    }
}