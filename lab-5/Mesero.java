import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.ListIterator;

public class Mesero {

    public static void main(String[] args) throws FileNotFoundException {
        Grafo g = new Grafo();
        g.cargarGrafo(args[0]);
        BellResults res = BellMan.bellManOrigin(g, Integer.parseInt(args[1]));
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