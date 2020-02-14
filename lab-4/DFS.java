import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Class that applies DFS with different traversal orders.
 */
public class DFS {
    /**
     * Graph in which is going to be used DFS
     */
    private Grafo grafo;
    /**
     * Path in postorder.
     */
    private LinkedList<Integer> path;
    private int ord;

    /**
     * Loads a graph from a file and use DFS on it.
     * 
     * @param grafo Graph to apply DFS.
     */

    public DFS(Grafo grafo) {
        this.grafo = grafo;
        ord = 0;
        path = new LinkedList<Integer>();
    }

    public void DFSForest() {
        if (grafo.GetNode(0) != null) {
            grafo.GetNode(0).SetImmediatePred(0);
            for (int vertex : grafo.Vertices()) {
                if (grafo.GetNode(vertex).GetColor() == 0 && grafo.GetNode(vertex).GetImmediatePred() != -1) {
                    DFSVisited(vertex);
                }
            }
        }
    }

    /**
     * Start to run DFS recursively since node with u as identifier. It does save
     * the preords, postorder, and reverse postorder of DFS.
     * 
     * @param u source node.
     */
    public void DFSVisited(int u) {
        LLNode node = grafo.GetNode(u);
        node.SetOrd(ord);
        ord++;
        node.SetColor(1);
        path.add(u);
        for (int v : node.GetSucesors()) {
            LLNode suce = grafo.GetNode(v);
            if (suce.GetColor() == 0) {
                suce.SetImmediatePred(u);
                DFSVisited(v);
            }
        }
        node.SetColor(2);
    }

    public LinkedList<Integer> GetPath() {
        return path;
    }

    public void Arb() {
        System.out.println("0-0 (raiz)");
        String tab = "  ";
        String c="";
        for (int vertex : path) {
            LLNode node = grafo.GetNode(vertex);
            for (int suce : node.GetSucesors()) {
                if (path.contains(suce) && grafo.GetNode(suce).GetImmediatePred() == vertex) {
                    String s = tab + grafo.GetNode(suce).GetImmediatePred() + "-" + suce + " (arco de camino)";
                    tab += "    ";
                    System.out.println(s);
                } else if (path.contains(suce) && grafo.GetNode(suce).GetImmediatePred() != vertex) {
                    c = vertex + "-" + suce;
                    String s= "";
                    if (vertex != 0) {
                        s += tab + c + " (arco de subida)";
                    } else {
                    }
                    System.out.println(s);
                }
            }
        }
    }


    public static void main(String[] args) {
        Grafo g = new Grafo();

        for (int i = 0; i < 8; i++) {
            g.InsertNode(i);
        }

        g.AddEdge(0, 2);
        g.AddEdge(0, 5);
        g.AddEdge(0, 7);

        g.AddEdge(2, 6);

        g.AddEdge(3, 4);

        g.AddEdge(4, 5);
        g.AddEdge(4, 7);

        g.AddEdge(5, 3);

        g.AddEdge(6, 2);
        g.AddEdge(6, 4);

        g.AddEdge(7, 1);

        DFS dfs = new DFS(g);
        dfs.DFSForest();

        dfs.Arb();
    }
}