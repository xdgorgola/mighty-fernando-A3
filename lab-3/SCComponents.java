import java.util.LinkedList;

/**
* Class that find the number of strongly connected components of a graph.
*/
public class SCComponents {
    /**
    * Number of strongly connected components.
    */
    private int numsc;
   
    /**On the given graph applies DFS and get its Reverse Postorder stack. 
    * Later down the road, it gets the reverse of the graph and applies DFS a second time, 
    * getting increasing number of strongly connected componets.
    * 
    */
    public SCComponents(Grafo grafo) {
        DFS dfs = new DFS(grafo);
        if (!grafo.Vertices().isEmpty()) {
            dfs.DFSVisited(0);
        } else {
            return;
        }
        LinkedList<Integer> order = dfs.GetReversePost();

        Grafo rgrafo = grafo.GetReverse();
        DFS dfs2 = new DFS(rgrafo);

        while (!order.isEmpty()) {
            int v = order.pollLast();

            if (!rgrafo.GetNode(v).IsVisited()) {
                dfs2.DFSVisited(v);
                numsc++;
            }
        }

    }

    /**Allows to get how many strongly connected components does the graph have.
     * 
     * @return Number of strongly connected components.
     */
    public int GetNumberSC() {
        return numsc;
    }

}