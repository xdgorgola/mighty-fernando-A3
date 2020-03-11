import Lados.Arista;
import Lados.Lado;
import Vertice.Vertice;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class MejorRuta {


    private static NodePath getBestRouteND(GrafoNoDirigido g, int src, int dst) {
        NodePath initial = new NodePath(g.obtenerVertice(src));
        NodePath best  = MejorRuta.getBestRouteRecurND(g, src, dst, initial, null);
        // Si sigue siendo null es que nunca llego jejejejje
        // se usa null para representar el mejor camino no encontrado aun
        return best;
    }

    private static NodePath getBestRouteRecurND(GrafoNoDirigido g, int src, int dst, NodePath act, NodePath best) {
        if (MejorRuta.isSolution(act, src, dst)){
            if (MejorRuta.isWorth(act, best)) return act;
            return best;
        }
        Vertice ult = act.getLastVert();
        for (Lado suc : MejorRuta.getValidEdgesND(g, act, ult)) {
            NodePath testSol = new NodePath(act);
            testSol.extendPathArista((Arista)suc);
            if (isWorth(testSol, best)){
                best = MejorRuta.getBestRouteRecurND(g, src, dst, testSol, best);
            }
        }
        return best;
    }

    private static boolean isSolution(NodePath toTest, int src, int dst){
        if (toTest.getPathE().size() == 0) return false;
        Vertice init = toTest.getFirstVert();
        Vertice fin = toTest.getLastVert();
        return init.obtenerID() == src && fin.obtenerID() == dst;
    }

    private static boolean isWorth(NodePath toTest, NodePath best){
        if (best == null) return true;
        return toTest.getCost() < best.getCost();
    }


    private static LinkedList<Lado> getValidEdgesND(GrafoNoDirigido g, NodePath act, Vertice last){
        int lastID = last.obtenerID();
        LinkedList<Lado> adys = g.incidentes(lastID);
        LinkedList<Lado> finals = new LinkedList<Lado>();
        if (act.getPathE().size() > 0) adys.remove(act.getLastEdge());
        for (Lado lado : adys) {
            if (!act.isBeforeLast(lado)) finals.add(lado);
        }
        return finals;
    }

    public static void main(String[] args) throws FileNotFoundException{
        GrafoNoDirigido g = new GrafoNoDirigido();
        g.cargarGrafo("Londres (copy 1).txt");
        MejorRuta.getBestRouteND(g, 105, 103);
    }
}