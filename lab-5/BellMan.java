import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
public class BellMan {

    public static DijkBellResults bellManOrigin(Grafo g, int origin){
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
        return new DijkBellResults(costs, preds);
    }

    public static LinkedList<Integer> construirCamino(int origen, int objetivo, Integer[] preds){
        // se usa como stack
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


    public static void main(String[] args) throws FileNotFoundException{
        Grafo g = new Grafo();
        g.cargarGrafo(args[0]);
        DijkBellResults res = BellMan.bellManOrigin(g, Integer.parseInt(args[1]));
        DecimalFormat df = new DecimalFormat("0.0#");

        System.out.println();
        int n = g.obtenerGrafo().size();

        for (int i = 0; i < n; i++){
            String aux = "Nodo " + i + ": " + args[1];

            LinkedList<Integer> path = BellMan.construirCamino(Integer.parseInt(args[1]), i, res.pred);
            ListIterator<Integer> listIterator = path.listIterator(path.size() - 1);

            while (listIterator.hasPrevious()){
                aux += "->" + listIterator.previous();
            }
            aux += "\t\t" + (path.size() - 1) + " lados (costo " + df.format(res.costs[i]) + ")"; 
            
            System.out.println(aux);
        }
    }

}


// ESTRUCUTRA
class DijkBellResults {
    public Double[] costs;
    public Integer[] pred;

    public DijkBellResults(Double[] costs, Integer[] pred){
        this.costs = costs;
        this.pred = pred;
    }
}