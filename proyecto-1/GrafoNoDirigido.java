import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * GrafoNoDirigido
 */
public class GrafoNoDirigido implements Grafo {

    private HashSet<Integer> nodeIDs;
    private HashSet<Integer> sideIDs;

    // Usamos HashSet porque el get del hashset es O(1) si se mantiene un buen load
    // factor
    // (de eso se encarga java) y el remove igualmente es O(1), tiempo bastante
    // aceptable para
    // chequear si existe un nodo en vez de tener que iterar en toda la lista para
    // ver primero
    // si el nodo existe. En caso de que exista, fino, pero si no existe, iteraste
    // toda una lista
    // para nada :(.

    private LinkedList<ALNode> graph;

    public HashSet<Integer> getVerticesIDs() {
        return nodeIDs;
    }

    public LinkedList<ALNode> getGraph() {
        return graph;
    }

    @Override
    public boolean loadGraph(Grafo g, String file) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addVertex(Grafo g, Vertice v) {
        if (g.isVertex(g, Vertice.getID(v))) {
            return false;
        } else {
            ALNode toAddd = new ALNode(v);
            nodeIDs.add(Vertice.getID(v));
            graph.add(toAddd);
            return true;
        }
    }

    @Override
    public boolean addVertex(Grafo g, int id, String name, double x, double y, double w) {
        if (g.isVertex(g, id)) {
            return false;
        } else {
            Vertice v = new Vertice(id, name, x, y, w);
            ALNode toAdd = new ALNode(v);
            nodeIDs.add(id);
            graph.add(toAdd);
            return true;
        }
    }

    @Override
    public boolean removeVertex(Grafo g, int id) {
        if (!g.isVertex(g, id)) {
            return false;
        } else {
            GrafoNoDirigido gND = (GrafoNoDirigido) g;
            for (ALNode alNode : graph) {
                if (alNode.getID() == id) {
                    graph.remove(alNode);
                } else {
                    LinkedList<Vertice> adjacencies = alNode.getAdjacencies();
                    for (Vertice v : adjacencies) {
                        if (Vertice.getID(v) == id) {
                            adjacencies.remove(v);
                            break;
                        }
                    }
                }
            }
            gND.nodeIDs.remove((Integer) id);
            return true;
        }
    }

    @Override
    public boolean isVertex(Grafo g, int id) {
        return ((GrafoNoDirigido) g).nodeIDs.contains(id);
    }

    @Override
    public Vertice getVertex(Grafo g, int id) throws NoSuchElementException {
        if (!g.isVertex(g, id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = ((GrafoNoDirigido) g).graph;
            for (ALNode alNode : graph) {
                if (alNode.getID() == id) {
                    return alNode.getVertex();
                }
            }
        }
        return null;
    }

    @Override
    public LinkedList<Vertice> vertices(Grafo g) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean addAxis(Grafo g, Arista a){
        Vertice vi = Arista.getVertex1(a);
        Vertice vf = Arista.getVertex2(a);
        if (!g.isVertex(g, Vertice.getID(vi)) || !g.isVertex(g, Vertice.getID(vf)) ||
        ((GrafoNoDirigido)g).sideIDs.contains(Lado.getID(a))) {
            return false;
        }
        return false;
    }

    public boolean isAxis(Grafo g, String u, String v, int tipo){
        return true;
    }

    public boolean isAxis(Grafo g, int id){
        return ((GrafoNoDirigido)g).sideIDs.contains(id);
    }

    @Override
    public LinkedList<Lado> sides(Grafo g) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int vertexNumber(Grafo g) {
        return ((GrafoNoDirigido) g).graph.size();
    }

    @Override
    public int sideNumber(Grafo g) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int degree(Grafo g, int id) throws NoSuchElementException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public LinkedList<Vertice> adjacents(Grafo g, int id) throws NoSuchElementException {
        if (!g.isVertex(g, id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = ((GrafoNoDirigido) g).graph;
            for (ALNode alNode : graph) {
                if (alNode.getID() == id) {
                    return alNode.getAdjacencies();
                }
            }
        }
        return null;
    }

    @Override
    public LinkedList<Lado> incidents(Grafo g, int id) throws NoSuchElementException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Grafo clone(Grafo g) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString(Grafo g) {
        // TODO Auto-generated method stub
        return null;
    }
}