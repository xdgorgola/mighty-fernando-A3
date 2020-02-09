import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Class that tries to find a Hamiltonian path in a graph. If the Path exists,
 * it shows the path, otherwise, it displays a message telling the user that
 * there isn't a Hamiltonian path in the graph. The algorithm used is BFS.
 */
public class BFS {

    /**
     * Graph to look for a Hamiltonian Path.
     */
    private Grafo graph;

    /**
     * Checks if a <code>GraphPath</code> is a Hamiltonian path for the
     * <code>BFS</code> object instance. It compares the size of the path and the
     * number of vertices in the graph.
     * 
     * @param path Path to check
     * @return If the path is Hamiltonian or not
     */
    public boolean IsHamiltonianPath(GraphPath path) {
        return path.GetPath().size() == graph.Vertices().size();
    }

    /**
     * 
     */
    public void HamiltonianBFS() {
        HashSet<Integer> verts = graph.Vertices();
        ArrayList<GraphPath> newPaths = new ArrayList<GraphPath>();
        ArrayList<GraphPath> levelPaths = new ArrayList<GraphPath>();

        Integer depth = 0;

        for (Integer gNode : verts) {
            depth = 0;

            newPaths.clear();
            levelPaths.clear();

            System.out.println("Path from " + gNode + ":");

            GraphPath iniPath = new GraphPath();

            iniPath.AddToPath(gNode);

            levelPaths.add(iniPath);

            while (levelPaths.size() > 0) {
                for (GraphPath lvlPath : levelPaths) {
                    System.out.println(GetSpaceString(depth) + lvlPath.PrettyString());
                    if (IsHamiltonianPath(lvlPath)) {
                        System.out.println(
                                "I found a Hamiltonian Path using all my power! Here it is:" + lvlPath.PrettyString());
                        System.out.println("Lenght of the path: " + lvlPath.GetPath().size());
                        return;
                    } else {
                        HashSet<Integer> aun = MightyBNAU(lvlPath);
                        for (Integer lanu : aun) {
                            GraphPath nextTry = new GraphPath(lvlPath.ClonePath());
                            nextTry.AddToPath(lanu);
                            newPaths.add(nextTry);
                        }
                    }
                }
                depth += 1;
                levelPaths = new ArrayList<GraphPath>(newPaths);
                newPaths.clear();
            }
        }
        System.out.println("MISSION FAILED! I WASN\'T ABLE TO FIND A HAMILTONIAN PATH SIR!");
        System.out.println("AWAITING ORDERS!");
    }

    /**
     * Gets all the unvisited adjacent nodes of the last node of a path.
     * 
     * @param path Path to get the unvisited adjacent nodes from the last node.
     * @return All the univisited adjacent nodes of the last node of the path as a
     *         <code>HashSet(Integer)</code>
     */
    public HashSet<Integer> MightyBNAU(GraphPath path) {
        // Adjacent unvisited nodes
        HashSet<Integer> adUnNodes = new HashSet<Integer>();
        // Adjacent nodes of the last node of the path
        LinkedList<Integer> adjNodes = graph.AdjacentNodes(path.GetPath().getLast());
        for (Integer node : adjNodes) {
            if (!path.NodeInPath(node)) {
                adUnNodes.add(node);
            }
        }
        return adUnNodes;
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

    /**
     * Creates a Hamiltonian Path calculator for <code>grafo</code> graph
     * 
     * @param grafo Graph to look for a Hamiltonian Path
     */
    public BFS(Grafo grafo) {
        this.graph = grafo;
    }
}