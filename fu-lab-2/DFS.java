import java.util.LinkedList;

/**
 * Class that tries to find a Hamiltonian path in a graph. If the Path exists,
 * it shows the path, otherwise, it displays a message telling the user that
 * there isn't a Hamiltonian path in the graph. The algorithm used is DFS.
 */
public class DFS {

    /**
     * Graph to look for a Hamiltonian Path.
     */
    private Grafo graph;
    /**
     * Path built in real-time to look for the Hamiltonian Path.
     */
    private LinkedList<Integer> path;
    /**
     * Depth of the path/recursion in real-time.
     */
    private int depth = 0;

    /**
     * Checks if the path found is a Hamiltonian Path. It compares the size of the
     * path and the number of vertices in the graph.
     * 
     * @return If the path is Hamiltonian or not.
     */
    public boolean IsHamiltonianPath() {
        return path.size() == graph.Vertices().size();
    }

    /**
     * Initial call of the recursive method to find a Hamiltonian Path in a graph.
     */
    public void FindHamiltonianPathInitial() {

        path = new LinkedList<Integer>();
        depth = 0;

        // Iterates trough all the vertices in the graph and tries to find a path from
        // them.
        for (Integer gNode : graph.Vertices()) {
            System.out.println("Path from " + gNode + ":");

            LLNode initial = graph.GetNode(gNode);

            initial.SetVisited(true);
            path.add(initial.GetID());

            // Checks if the graph is a single node!
            if (IsHamiltonianPath()) {
                System.out.println("Hamiltonian path found! :) Be happy!: " + path);
                System.out.println("Lenght of the path: " + path.size());
                return;
            } else {
                LinkedList<Integer> adjacents = initial.GetAdjacents();
                for (Integer adjNode : adjacents) {
                    if (!graph.GetNode(adjNode).IsVisited()) {
                        if (FindHamiltonianPath(adjNode)) {
                            System.out.println("Hamiltonian path found! :) Be happy!: " + path);
                            System.out.println("Lenght of the path: " + path.size());
                            return;
                        }
                    }
                }
            }
            // If there isn't any Hamiltonian paths with the this node, try starting with
            // another.
            initial.SetVisited(false);
            path.removeFirst();
        }
        System.out.println("I wasn\'t able to find a Hamiltonian path :( Don\'t be sad my master,");
        System.out.println("maybe try with another graph!");
        return;
    }

    public boolean FindHamiltonianPath(int node) {

        LLNode levelNode = graph.GetNode(node);

        levelNode.SetVisited(true);

        System.out.println(GetSpaceString(depth) + path.getLast() + "-" + node);

        path.add((Integer) node);
        depth += 1;

        // If the path is Hamiltonian, return true and finish!
        if (IsHamiltonianPath()) {
            return true;
        // Else, try to create a Hamiltonian Path appending the adjacent vertices of the
        // node
        } else {
            LinkedList<Integer> adjacents = levelNode.GetAdjacents();
            // For every adjacent node, try to create a Hamiltonian path!
            for (Integer adjNode : adjacents) {
                // If the adjacent node isn't in the path, try creating a new Hamiltonian path with it
                if (!graph.GetNode(adjNode).IsVisited()) {
                    if (FindHamiltonianPath(adjNode)) {
                        return true;
                    }
                // Try another node if the adjacent node was already visited.
                } else {
                    System.out.println(GetSpaceString(depth) + node + "-" + adjNode
                            + "  Already tried this path!");
                }
            }

            // If there's not any Hamiltonian Path derivated from this path, go back a node.
            levelNode.SetVisited(false);
            path.remove((Integer) node);
            depth -= 1;
            return false;
        }
    }

    /**
     * Creates a string that contains a desired number of empty spaces
     * 
     * @param spaceNum Desired empty spaces
     * @return A string with <code>spaceNum</code> empty spaces
     */
    public String GetSpaceString(int spaceNum) {
        String spaces = "";
        for (int i = 0; i < spaceNum; i++) {
            spaces += "  ";
        }
        return spaces;
    }

    /** Creates a Hamiltonian Path calculator for <code>grafo</code> graph
     * @param grafo Graph to look for a Hamiltonian Path
     */
    public DFS(Grafo grafo) {
        this.graph = grafo;
    }
}