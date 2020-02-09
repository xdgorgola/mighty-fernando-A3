import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * A class that contains a graph represented as a Adjacencies Linked List
 */
public class Grafo {

    /**
     * Graph representation as Adjacencies Linked List
     */
    private LinkedList<LLNode> graph;
    /**
     * Nodes in the graph
     */
    private HashSet<Integer> nodes;

    /**
     * Gets all the nodes/vertices of the graph
     * 
     * @return Nodes/Vertices of the graph
     */
    public HashSet<Integer> Vertices() {
        return nodes;
    }

    /**
     * Gets a node object from the <code>graph</code> that is associated to an
     * integer id. Returns nulls if the node isn't in the graph.
     * 
     * @param nodeID Node id to look for
     * @return Node associated to the node id
     */
    public LLNode GetNode(int nodeID) {
        if (!nodes.contains(nodeID)) {
            return null;
        } else {
            ListIterator<LLNode> iterator = graph.listIterator();
            while (iterator.hasNext()) {
                LLNode act = iterator.next();
                if (act.GetID() == nodeID) {
                    return act;
                }
            }
            return null;
        }
    }

    /** Checks if an Edge already exist in the graph
     * @param from Initial node of the edge
     * @param to Final node of the edge
     * @return If the edge is already in the graph
     */
    public boolean IsEdgeInGraph(int from, int to){
        LLNode fromNode = GetNode(from);
        return fromNode.GetAdjacents().contains(to);
    }

    /**
     * Inserts a node in the graph with the choosen ID. If the node is already in
     * the graph, it does nothing.
     * 
     * @param nodeID Node ID of the node to add.
     */
    public void InsertNode(int nodeID) {
        if (nodes.contains(nodeID)) {
            return;
        } else {
            LLNode lnode = new LLNode(nodeID);
            nodes.add(nodeID);
            graph.add(lnode);
        }
    }

    /**
     * Adds an edge between the node <code>from</code> and the node <code>to</code>.
     * The edge goes from both nodes (undirected graph).
     * 
     * @param from First node of the edge.
     * @param to   Second node of the edge.
     */
    public void AddEdge(int from, int to) {
        if (!nodes.contains(from) || !nodes.contains(to)) {
            System.err.println("Nodes not in graph!");
        } else {
            if (IsEdgeInGraph(from, to)){
                System.err.println("Edge already in graph! Exiting program");
                System.exit(0);
            }
            LLNode initial = this.GetNode(from);
            initial.GetAdjacents().add(to);

            initial = this.GetNode(to);
            initial.GetAdjacents().add(from);
        }
    }

    /**
     * Gets the adjancent nodes of a node from a graph. If the node with the
     * associated ID isn't in the graph, it returns null. If the node doesn't got
     * any adjacent nodes, it returns a empty linked list.
     * 
     * @param nodeID NodeID of the node to look for it adjacent nodes
     * @return Adjacent nodes of a node in the graph
     */
    public LinkedList<Integer> AdjacentNodes(int nodeID) {
        if (!nodes.contains(nodeID)) {
            return null;
        } else {
            LLNode toGet = this.GetNode(nodeID);
            return toGet.GetAdjacents();
        }
    }

    public Grafo() {
        nodes = new HashSet<Integer>();
        graph = new LinkedList<LLNode>();
    }
}

/** Linked List Node representation for a adjacency list graph representation.
 */
class LLNode {
    /** Node id
     */
    private int id;
    /** Indicates if the node is already visited
     */
    private boolean isVisited = false;
    /** Node adjacent nodes list
     */
    private LinkedList<Integer> adjNodes;

    /** Get the node ID
     * @return Node id
     */
    public int GetID() {
        return id;
    }

    /** Gets the node adjacent nodes as a Integer Linked List
     * @return Node adjacent nodes
     */
    public LinkedList<Integer> GetAdjacents() {
        return adjNodes;
    }

    /** Checks if the node is already visited
     * @return If the node is already visited
     */
    public boolean IsVisited() {
        return isVisited;
    }

    /** Sets visited status of the node
     * @param isVisited Value to set isVisited to
     */
    public void SetVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public LLNode(int id) {
        this.id = id;
        adjNodes = new LinkedList<Integer>();
    }
}

/** Class used to represent a path in a graph using integers.
 */
class GraphPath{
    /** Path in a graph.
     */
    private LinkedList<Integer> path;

    /** Gets the path.
     * @return The path.
     */
    public LinkedList<Integer> GetPath(){
        return path;
    }

    /** Extends the path with a node.
     * @param toAdd Node to expand the path with.
     */
    public void AddToPath(Integer toAdd){
        path.add(toAdd);
    }

    /** Checks if a node is already in the path.
     * @param node Node to check if it\'s already in the path.
     * @return If the node is already in the path.
     */
    public boolean NodeInPath(Integer node){
        return path.contains(node);
    }

    /** Clones the path.
     * @return A deepcopy of the path.
     */
    public LinkedList<Integer> ClonePath(){
        LinkedList<Integer> clone = new LinkedList<Integer>();
        for (Integer node : path) {
            clone.add(node);
        }
        return clone;
    }

    /** Creates a string with a pretty representation of the path.
     * @return A pretty string with the path
     */
    public String PrettyString(){
        String prettyBaby = "";
        ListIterator<Integer> iterator = path.listIterator();
        while (iterator.hasNext()){
            Integer act = iterator.next();
            prettyBaby += act;
            if (iterator.hasNext()){
                prettyBaby += "-";
            }
        }
        return prettyBaby;
    }

    public GraphPath(){
        path = new LinkedList<Integer>();
    }

    public GraphPath(LinkedList<Integer> path){
        this.path = path;
    }
}
