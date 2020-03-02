import java.util.Arrays;
import java.util.LinkedList;

/* Autores:
 * 		Pedro Rodriguez 15-11264
 * 		Mariangela Rizzo 17-10538
 */

public class BellMan {

    /**
     * Algoritmo de BellMan aplicado a un grafo g desde un origen arbitrario
     * 
     * @param g Grafo a aplicar BellMan
     * @param origin Nodo a conseguir los caminos mas cortos
     * @return Estructura con los resultados del algoritmo
     */
    public static BellResults bellManOrigin(Grafo g, int origin){
        int n = g.obtenerGrafo().size();

        LinkedList<Integer[]> lados = g.obtenerLados();
        Double[] costs = new Double[n]; 
        Integer[] preds = new Integer[n];
        Arrays.setAll(costs, p -> Double.POSITIVE_INFINITY);

        int i = 0; 
        boolean change = true;

        costs[origin] = 0.0;
        preds[origin] = origin;

        while (i <= n && change){
            change = false;
            for (Integer[] lado : lados) {
                if (costs[lado[1]] > costs[lado[0]]  + g.obtenerCostoLado(lado[0], lado[1])){
                    costs[lado[1]] = costs[lado[0]]  + g.obtenerCostoLado(lado[0], lado[1]);
                    preds[lado[1]] = lado[0];
                    change = true;
                }
            }
            i += 1;
        }
        for (Integer[] lado : lados) {
            if (costs[lado[1]] > costs[lado[0]]  + g.obtenerCostoLado(lado[0], lado[1])){
                System.err.println("Hay un ciclo con lado negativo!");
                return null;
            }
        }
        return new BellResults(costs, preds);
    }


    /**
     * Construye un camino de costo minimo entre un vertice y otro
     * partiendo de un array de predecesores generado por BellMan.
     * <b>IMPORTANTE: Bellman debe haber sido ejecutado desde el vertice origen.</b>
     * 
     * @param origen Origen del camino
     * @param objetivo Objetivo del camino
     * @param preds Arreglo de predecesores
     * @return Camino de costo minimo partiendo de origen a objetivo
     */
    public static LinkedList<Integer> construirCamino(int origen, int objetivo, Integer[] preds){
        LinkedList<Integer> camino = new LinkedList<Integer>();
        int act = objetivo;
        camino.add(objetivo);
        while (act != origen){
            camino.add(preds[act]);
            if (preds[act] == null) {
                System.err.println("Hay una mesa no alcanzable uwu");
                return camino;
            }
            else act = preds[act];
        }
        return camino;
    }
}

/**
 * Estructura que contiene los resultados del BellMan
 */
class BellResults {
    public Double[] costs;
    public Integer[] pred;

    public BellResults(Double[] costs, Integer[] pred){
        this.costs = costs;
        this.pred = pred;
    }
}