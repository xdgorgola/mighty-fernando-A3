import java.util.LinkedList;
import java.util.NoSuchElementException;

public interface Grafo {

    public boolean loadGraph(Grafo g, String file);

    public boolean addVertex(Grafo g, Vertice v);
    public boolean addVertex(Grafo g, int id, String name, double x, double y, double w);
    public boolean removeVertex(Grafo g, int id);
    public boolean isVertex(Grafo g, int id);
    public Vertice getVertex(Grafo g, int id) throws NoSuchElementException;
    
    public LinkedList<Vertice> vertices(Grafo g);
    public LinkedList<Lado> sides(Grafo g);            
    public int vertexNumber(Grafo g);
    public int sideNumber(Grafo g);

    public int degree(Grafo g, int id) throws NoSuchElementException;
    public LinkedList<Vertice> adjacents(Grafo g, int id) throws NoSuchElementException;
    public LinkedList<Lado> incidents(Grafo g, int id) throws NoSuchElementException;

    public Grafo clone(Grafo g);
    public String toString(Grafo g);
}

/**
 * AllNode
 */
class ALNode {

    private Vertice vertex;
    private LinkedList<Vertice> adjacencies;

    public Vertice getVertex(){
        return vertex;
    }

    public int getID(){
        return vertex.getID(vertex);
    }

    public void addVertex(Vertice v){
        adjacencies.add(v);
    }
    
    public LinkedList<Vertice> getAdjacencies(){
        return adjacencies;
    }

    public ALNode(Vertice v){
        this.vertex = v;
    }
}