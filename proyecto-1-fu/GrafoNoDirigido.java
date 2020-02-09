import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
    public boolean loadGraph(String file) throws IOException, FileNotFoundException{
        int n = 0;
        int m = 0;
        Scanner scan = new Scanner(new File(file));
        String line = scan.nextLine();
        Grafo g;
        if (line.equals("ND")){
            g = new GrafoNoDirigido();
        }
        else{
            return false;
        }

        line = scan.nextLine();
        n = Integer.parseInt(line);
        line = scan.nextLine();
        m = Integer.parseInt(line);

        for (int i = 0; i < n; i++){
            line = scan.nextLine();
            String[] results = line.split("\\s+");
            if (results.length != 5){
                return false;
            }
            Vertice toAdd = new Vertice(Integer.parseInt(results[0]), results[1], Integer.parseInt(results[2]), 
                Integer.parseInt(results[3]), Integer.parseInt(results[4]));
            if (!g.addVertex(toAdd)){
                return false;
            }
        }
        for (int i = 0; i < m; i++){
            line = scan.nextLine();
            String[] results = line.split("\\s+");
            if (results.length != 4 || Integer.parseInt(results[2]) != 0){
                return false;
            }
            Arista toAdd = null;//new Arista(id, iVertice, fVertice, weight)
            if (!addAxis(toAdd)){
                return false;
            }
        }
        scan.close();
        return true;
    }

    @Override
    public boolean addVertex(Vertice v) {
        if (isVertex(v.getID())) {
            return false;
        } else {
            ALNode toAddd = new ALNode(v);
            nodeIDs.add(v.getID());
            graph.add(toAddd);
            return true;
        }
    }

    @Override
    public boolean addVertex(int id, String name, double x, double y, double w) {
        if (isVertex(id)) {
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
    public boolean removeVertex(int id) {
        if (!isVertex(id)) {
            return false;
        } else {
            for (ALNode alNode : graph) {
                if (alNode.getID() == id) {
                    graph.remove(alNode);
                } else {
                    LinkedList<Vertice> adjacencies = alNode.getAdjacencies();
                    for (Vertice v : adjacencies) {
                        if (v.getID() == id) {
                            adjacencies.remove(v);
                            break;
                        }
                    }
                }
            }
            nodeIDs.remove((Integer) id);
            return true;
        }
    }

    @Override
    public boolean isVertex(int id) {
        return nodeIDs.contains(id);
    }

    @Override
    public Vertice getVertex(int id) throws NoSuchElementException {
        if (!isVertex(id)) {
            throw new NoSuchElementException();
        } else {
            for (ALNode alNode : graph) {
                if (alNode.getID() == id) {
                    return alNode.getVertex();
                }
            }
        }
        return null;
    }

    @Override
    public LinkedList<Vertice> vertices() {
        LinkedList<Vertice> vertices = new LinkedList<Vertice>();
        for (ALNode vertice : graph) {
            vertices.add(vertice.getVertex());
        }
        return vertices;
    }

    public boolean addAxis(Arista a) {
        Vertice vi = a.getVertex1();
        Vertice vf = a.getVertex2();
        if (!isVertex(vi.getID()) || !isVertex(vf.getID()) || sideIDs.contains(a.getID())) {
            return false;
        } else {
            boolean status = true;
            int c = 0;
            for (ALNode alNode : graph) {
                if (alNode.getID() == vi.getID()) {
                    // pendiente con esto porque si es ciclo, puede duplicar nodos, asi que deberia
                    // de agregar un metodo interno en alNode que chequee eso
                    status = status && alNode.addVertex(vi);
                    if (c == 1) {
                        break;
                    }
                } else if (alNode.getID() == vf.getID()) {
                    status = status && alNode.addVertex(vf);
                    if (c == 1) {
                        break;
                    }
                }
            }
            return status;
        }
    }

    public boolean isAxis(String u, String v, int tipo) {
        return true;
    }

    public boolean isAxis(int id) {
        return sideIDs.contains(id);
    }

    @Override
    public LinkedList<Lado> sides() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int vertexNumber() {
        return graph.size();
    }

    @Override
    public int sideNumber() {
        return sideIDs.size();
    }

    @Override
    public int degree(int id) throws NoSuchElementException {
        if (!isVertex(id)) {
            throw new NoSuchElementException();
        } else {
            for (ALNode alNode : graph) {
                if (alNode.getID() == id) {
                    return alNode.getAdjacencies().size();
                }
            }
        }
        return 0;
    }

    @Override
    public LinkedList<Vertice> adjacents(int id) throws NoSuchElementException {
        if (!isVertex(id)) {
            throw new NoSuchElementException();
        } else {
            for (ALNode alNode : graph) {
                if (alNode.getID() == id) {
                    return alNode.getAdjacencies();
                }
            }
        }
        return null;
    }

    @Override
    public LinkedList<Lado> incidents(int id) throws NoSuchElementException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Grafo clone() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
}