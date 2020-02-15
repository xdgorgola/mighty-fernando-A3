import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

// AUTORES Mariangela Rizzo 17-10538
//         Pedro Rodriguez  15-11264

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
     * Gets the graph representation as a Linked List.
     * 
     * @return Graph as a Linked List
     */
    public LinkedList<LLNode> GetGraph(){
        return graph;
    }

    /**
     * Gets a node object from the <code>graph<\code> that is associated to an
     * integer id. Returns nulls if the node isn't in the graph.
     * 
     * @param nodeID Node id to look for
     * @return Node associated to the node id
     */
    public LLNode GetNode(int nodeID) {
        if (!nodes.contains(nodeID))
            return null;
        else {
            ListIterator<LLNode> iterator = graph.listIterator();
            while (iterator.hasNext()) {
                LLNode act = iterator.next();
                if (act.GetID() == nodeID)
                    return act;
            }
            return null;
        }
    }

    /**
     * Inserts a node in the graph with the choosen ID. If the node is already in
     * the graph, it does nothing.
     * 
     * @param nodeID Node ID of the node to add.
     */
    public void InsertNode(int nodeID) {
        if (nodes.contains(nodeID))
            return;
        else {
            LLNode lnode = new LLNode(nodeID);
            nodes.add(nodeID);
            graph.add(lnode);
        }
    }

    /**
     * Adds an edge between the node <code>from<\code> and the node <code>to<\code>.
     * The edge goes from both nodes (undirected graph).
     * 
     * @param from First node of the edge. * @param to Second node of the edge.
     */
    public void AddEdge(int from, int to) {
        if (!nodes.contains(from) || !nodes.contains(to))
            return;
        else {
            this.GetNode(from).SetSucesor(to);
            this.GetNode(to).SetPredecesor(from);
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
    public LinkedList<Integer> SucesorNodes(int nodeID) {
        if (!nodes.contains(nodeID)) {
            return null;
        } else {
            LLNode toGet = this.GetNode(nodeID);
            return toGet.GetSucesors();
        }
    }

    /**
     * Gets the predecessor nodes of a node in the graph.
     * 
     * @param nodeID NodeID of the node to look for it predecessor nodes
     * @return Predecessors nodes of the node
     */
    public LinkedList<Integer> PredecesorNodes(int nodeID) {
        if (!nodes.contains(nodeID)) {
            return null;
        } else {
            LLNode toGet = this.GetNode(nodeID);
            return toGet.GetPredecesors();
        }
    }

    public Grafo() {
        nodes = new HashSet<Integer>();
        graph = new LinkedList<LLNode>();
    }

    public static void main(String[] args) {
        Grafo g = new Grafo();

        for (int i = 0; i < 8; i++) {
            g.InsertNode(i);
        }

        g.AddEdge(0, 1);
        g.AddEdge(0, 2);

        g.AddEdge(1, 2);

        g.AddEdge(2, 3);

        g.AddEdge(4, 2);

        g.AddEdge(5, 6);

        g.AddEdge(7, 6);

        System.out.println(g.GetNode(2).GetPredecesors());
        System.out.println(g.GetNode(2).GetSucesors());
    }
}

/**
 * Node of the linked list used to represent the graph. It contains all the info of
 * a vertice of the graph. That info is:
 * <lu>
 * <li>ID<\li>
 * <li>Color<\li>
 * <li>Inmediate predecessor ID<\li>
 * <li>Appearance order in BFS/DFS algorithm<\li>
 * <li>Sucessor nodes<\li>
 * <li>Predecessor nodes<\li>
 * <\lu>
 */
class LLNode {
    /** 
     * Node ID 
     */
    private int id;
    /** 
     * Node color 
     */
    private int color;
    /** 
     * Node predecessor inmediate ID 
     */
    private int immID;
    /** 
     * Node appearance order in BFS/DFS algorithm 
     */
    private int ord;
    /** 
     * Sucessor nodes of the node 
     */
    private LinkedList<Integer> suceNodes;
    /** 
     * Predecessor nodes of the node 
     */
    private LinkedList<Integer> predNodes;

    public LLNode(int id) {
        this.id = id;
        color = 0;
        immID = -1;
        ord = -1;
        suceNodes = new LinkedList<Integer>();
        predNodes = new LinkedList<Integer>();

    }

    /**
     * Gets the node ID.
     * 
     * @return The node ID
     */
    public int GetID() {
        return id;
    }

    /**
     * Gets the node appearance order in BFS/DFS algorithm.
     * @return Appearance order in BFS/DFS algorithm
     */
    public int GetOrd(){
        return ord;
    }

    public void SetOrd(int i){
        ord = i;
    }

    /**
     * Sets the color of the node.
     * 
     * @param color Color to set the node.
     */
    public void SetColor(int color) {
        this.color = color;
    }

    /**
     * Gets the color of the node.
     * 
     * @return The node color
     */
    public int GetColor() {
        return color;
    }

    /**
     * Sets the inmediate predecessor node ID of the node.
     * 
     * @param immID The inmediate predecessor node ID of the node
     */
    public void SetImmediatePred(int immID) {
        this.immID = immID;
    }

    /**
     * Adds a sucessor to the sucessor nodes.
     * 
     * @param suce Sucessor node ID to add
     */
    public void SetSucesor(int suce) {
        suceNodes.add(suce);
    }

    /**
     * Adds a predecessor to the predecessor nodes.
     * 
     * @param pred Predecessor node ID to add
     */
    public void SetPredecesor(int pred) {
        predNodes.add(pred);
    }

    /**
     * Gets the inmediate predecessor node ID.
     * 
     * @return Inmediate predecessor node ID of the node
     */
    public int GetImmediatePred() {
        return immID;
    }

    /**
     * Gets the sucessors of the node.
     * 
     * @return Sucessors of the node
     */
    public LinkedList<Integer> GetSucesors() {
        return suceNodes;
    }

    /**
     * Gets the predecessors of the node.
     * 
     * @return Predecessors of the node
     */
    public LinkedList<Integer> GetPredecesors() {
        return predNodes;
    }

    @Override
    public String toString() {
        String aux = "ID Nodo: " + id + "\nColor: " + color + "\n";
        return aux;
    }
}

/**
 * A Linked List that represents a path in a graph.
 * It contains the path nodes IDs ordered by appearance order of the nodes in the path.
 */
class NodePath {
    /**
     * Path
     */
    private LinkedList<Integer> path;

    /**
     * Expands the path with a node.
     * 
     * @param toAdd ID of the node to expand the path with
     */
    public void AddNext(int toAdd) {
        path.add(toAdd);
    }

    /**
     * Gets the last node of the path.
     * 
     * @return ID of the last node of the path
     */
    public Integer GetLast() {
        return path.peekLast();
    }

    /**
     * Gets the node previous to the last node of the path.
     * @return THe node previous to the last node of the path
     */
    public Integer GetPrevLast() {
        ListIterator<Integer> iterator = path.listIterator(path.size());
        iterator.previous();
        return iterator.previous();
    }

    /**
     * Gets the path.
     * 
     * @return Linked List used to represent the path
     */
    public LinkedList<Integer> GetPath() {
        return path;
    }

    /**
     * Clones the path.
     * 
     * @return Cloned node path (deepcopy)
     */
    public NodePath Clone() {
        NodePath clone = new NodePath();
        clone.path = new LinkedList<Integer>(this.path);
        return clone;
    }

    public NodePath() {
        this.path = new LinkedList<Integer>();
    }

    public NodePath(int i) {
        this.path = new LinkedList<Integer>();
        this.path.add(i);
    }
}
