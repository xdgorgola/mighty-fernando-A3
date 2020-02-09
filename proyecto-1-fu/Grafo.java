import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public interface Grafo {

    public boolean loadGraph(String file) throws IOException, FileNotFoundException;

    public boolean addVertex(Vertice v);
    public boolean addVertex(int id, String name, double x, double y, double w);
    public boolean removeVertex(int id);
    public boolean isVertex(int id);
    public Vertice getVertex(int id) throws NoSuchElementException;
    
    public LinkedList<Vertice> vertices();
    public LinkedList<Lado> sides();            
    public int vertexNumber();
    public int sideNumber();

    public int degree(int id) throws NoSuchElementException;
    public LinkedList<Vertice> adjacents(int id) throws NoSuchElementException;
    public LinkedList<Lado> incidents(int id) throws NoSuchElementException;

    public Grafo clone();
    public String toString();
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
        return vertex.getID();
    }

    public boolean addVertex(Vertice v){
        if (!adjacencies.contains(v)){
            adjacencies.add(v);
            return true;
        }
        return false;
    }
    
    public LinkedList<Vertice> getAdjacencies(){
        return adjacencies;
    }

    public ALNode(Vertice v){
        this.vertex = v;
    }
}