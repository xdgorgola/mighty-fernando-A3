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

    public Grafo GetReverse() {
        Grafo rgrafo = new Grafo();
        for (int i = 0; i < this.nodes.size(); i++) {
            rgrafo.InsertNode(i);
        }
        for (Integer node : rgrafo.nodes) {
            for (Integer suc : this.SucesorNodes(node)) {
                rgrafo.AddEdge(suc, node);
            }
        }
        return rgrafo;
    }

    public Grafo() {
        nodes = new HashSet<Integer>();
        graph = new LinkedList<LLNode>();
    }
}

class LLNode {
    private int id;
    private boolean isVisited = false;
    private LinkedList<Integer> suceNodes;

    public LLNode(int id) {
        this.id = id;
        this.suceNodes = new LinkedList<Integer>();
    }

    public int GetID() {
        return id;
    }

    public void ChangeID(int newID) {
        this.id = newID;
    }

    public LinkedList<Integer> GetSucesors() {
        return suceNodes;
    }

    public void SetSucesor(int suce) {
        suceNodes.add(suce);
    }

    public boolean IsVisited() {
        return isVisited;
    }

    public void SetVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

}
