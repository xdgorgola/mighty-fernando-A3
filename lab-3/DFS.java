import java.util.LinkedList;

/**
* Class that applies DFS with different traversal orders.
*/
public class DFS {
    /**
    * Graph in which is going to be used DFS
    */
    private Grafo grafo;
    /**
    * Path in preorder.
    */
    private LinkedList<Integer> pre;
    /**
    * Path in postorder.
    */
    private LinkedList<Integer> post;
    /**
    * Path in reverse postorder.
    */
    private LinkedList<Integer> reversePost;

    /** Loads a graph from a file and use DFS on it.
	 * 
	 * @param grafo Graph to apply DFS.
	 */

    public DFS(Grafo grafo) {
        this.grafo = grafo;
        pre = new LinkedList<Integer>(); // Working as a queue.
        post = new LinkedList<Integer>(); // Working as a queue.
        reversePost = new LinkedList<Integer>(); // Working as a stack.
    }



    /** Start to run DFS recursively since node with u as identifier. 
     * It does save the preords, postorder, and reverse postorder of DFS.
	 * 
	 * @param u source node.
	 */
    public void DFSVisited(int u) {
        grafo.GetNode(u).SetVisited(true);
        pre.add(u);

        for (int v : grafo.SucesorNodes(u)) {
            if (!grafo.GetNode(v).IsVisited()) {
                DFSVisited(v);
            }
        }

        post.add(u);
        reversePost.add(u);
    }

    /**It allows to get the preorder of DFS.
     * 
     * @return A Linked List of Integers working as a Queue that saves preorder of DFS.
     */
    public LinkedList<Integer> GetPre() {
        return pre;
    }

     /**It allows to get the postorder of DFS.
     * 
     * @return A Linked List of Integers working as a Queue that saves postorder of DFS.
     */
    public LinkedList<Integer> GetPost() {
        return post;
    }

     /**It allows to get the reverse postorder of DFS.
     * 
     * @return A Linked List of Integers working as a Stack that saves Reverse postorder of DFS.
     */
    public LinkedList<Integer> GetReversePost() {
        return reversePost;
    }

     /**It does clean the Linked List saving preorder of DFS, leaving it empty.
     * 
     */
    public void ClearPre() {
        pre.clear();
    }
}