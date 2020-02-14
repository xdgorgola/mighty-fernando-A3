import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

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

class LLNode {
    private int id;
    private int color;
    private int immID;
    private int ord;
    private LinkedList<Integer> suceNodes;
    private LinkedList<Integer> predNodes;

    public LLNode(int id) {
        this.id = id;
        color = 0;
        immID = -1;
        ord = -1;
        suceNodes = new LinkedList<Integer>();
        predNodes = new LinkedList<Integer>();

    }

    public int GetID() {
        return id;
    }

    public int GetOrd(){
        return ord;
    }

    public void SetOrd(int i){
        ord = i;
    }
    
    public void SetColor(int color) {
        this.color = color;
    }

    public int GetColor() {
        return color;
    }

    public void SetImmediatePred(int immID) {
        this.immID = immID;
    }

    public void SetSucesor(int suce) {
        suceNodes.add(suce);
    }

    public void SetPredecesor(int pred) {
        predNodes.add(pred);
    }

    // Este atributo es al momento de hacer DFS o BFS, el que se encuentra justo
    // antes del nodo. No para agregar edge.
    public int GetImmediatePred() {
        return immID;
    }

    public LinkedList<Integer> GetSucesors() {
        return suceNodes;
    }

    public LinkedList<Integer> GetPredecesors() {
        return predNodes;
    }

    @Override
    public String toString() {
        String aux = "ID Nodo: " + id + "\nColor: " + color + "\n";
        return aux;
    }
}

class NodePath {
    private LinkedList<Integer> path;

    public void AddNext(int toAdd) {
        path.add(toAdd);
    }

    public Integer GetLast() {
        return path.peekLast();
    }

    public Integer GetPrevLast() {
        ListIterator<Integer> iterator = path.listIterator(path.size());
        iterator.previous();
        return iterator.previous();
    }

    public LinkedList<Integer> GetPath() {
        return path;
    }

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
