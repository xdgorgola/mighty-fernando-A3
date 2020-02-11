import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    private ArrayList<Lado> gLados;

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
    public boolean cargarGrafo(Grafo g, String file) throws FileNotFoundException {
        int n = 0;
        int m = 0;
        Scanner scan = new Scanner(new File(file));
        String line = scan.nextLine();
        if (line.equals("ND")){
            g = new GrafoNoDirigido();
        }
        else{
            scan.close();
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
                scan.close();
                return false;
            }
            Vertice toAdd = new Vertice(Integer.parseInt(results[0]), results[1], Integer.parseInt(results[2]), 
                Integer.parseInt(results[3]), Integer.parseInt(results[4]));
            if (!g.agregarVertice(g, toAdd)){
                scan.close();
                return false;
            }
        }
        for (int i = 0; i < m; i++){
            line = scan.nextLine();
            String[] results = line.split("\\s+");
            if (results.length != 4 || Integer.parseInt(results[2]) != 0){
                scan.close();
                return false;
            }
            Arista toAdd = null;//new Arista(id, iVertice, fVertice, weight)
            if (!((GrafoNoDirigido) g).agregarArista(g, toAdd)){
                scan.close();
                return false;
            }
        }
        scan.close();
        return true;
    }

    @Override
    public boolean agregarVertice(Grafo g, Vertice v) {
        if (g.estaVertice(g, Vertice.obtenerID(v))) {
            return false;
        } else {
            ALNode toAddd = new ALNode(v);
            nodeIDs.add(Vertice.obtenerID(v));
            graph.add(toAddd);
            return true;
        }
    }

    @Override
    public boolean agregarVertice(Grafo g, int id, String name, double x, double y, double w) {
        if (g.estaVertice(g, id)) {
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
    public boolean eliminarVertice(Grafo g, int id) {
        if (!g.estaVertice(g, id)) {
            return false;
        } else {
            GrafoNoDirigido gND = (GrafoNoDirigido) g;
            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    graph.remove(alNode);
                } else {
                    LinkedList<Vertice> adjacencies = alNode.obtenerAdyacencias();
                    for (Vertice v : adjacencies) {
                        if (Vertice.obtenerID(v) == id) {
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
    public boolean estaVertice(Grafo g, int id) {
        return ((GrafoNoDirigido) g).nodeIDs.contains(id);
    }

    ////// COMENTAR ESTE ES NUEVO
    public boolean estaVertice(Grafo g, String nombre){
        LinkedList<ALNode> nodos = ((GrafoNoDirigido)g).graph;
        for (ALNode alNode : nodos) {
            if (Vertice.obtenerNombre(alNode.obtenerVertice()) == nombre){
                return true;
            }
        }
        return false;
    }

    @Override
    public Vertice obtenerVertice(Grafo g, int id) throws NoSuchElementException {
        if (!g.estaVertice(g, id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = ((GrafoNoDirigido) g).graph;
            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    return alNode.obtenerVertice();
                }
            }
        }
        return null;
    }

    @Override
    public LinkedList<Vertice> vertices(Grafo g) {
        LinkedList<Vertice> verts = new LinkedList<Vertice>();
        for (ALNode alNode : ((GrafoNoDirigido) g).graph) {
            verts.add(alNode.obtenerVertice());
        }
        return verts;
    }

    public boolean agregarArista(Grafo g, Arista a){
        Vertice vi = Arista.obtenerVertice1(a);
        Vertice vf = Arista.obtenerVertice2(a);
        if (!g.estaVertice(g, Vertice.obtenerID(vi)) || !g.estaVertice(g, Vertice.obtenerID(vf)) ||
        ((GrafoNoDirigido)g).sideIDs.contains(Lado.obtenerID(a))) {
            return false;
        }
        return false;
    }

    public boolean agregarArista(Grafo g, int u, int v, int tipo, double p){
        if (estaArista(g, u, v, tipo)){
            return false;
        }
        if (!((GrafoNoDirigido)g).estaVertice(g, u) || !((GrafoNoDirigido)g).estaVertice(g, v)){
            return false;
        }
        else{

        }
        return false;
    }

    public boolean estaArista(Grafo g, int u, int v, int tipo){
        for (ALNode alNode : ((GrafoNoDirigido)g).graph) {
            Vertice ver = alNode.obtenerVertice();
            if (Vertice.obtenerID(ver) == u){
                LinkedList<Vertice> adjacents = alNode.obtenerAdyacencias();
                for (Vertice adj : adjacents) {
                    if (Vertice.obtenerID(adj) == v){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean estaArista(Grafo g, int id){
        return ((GrafoNoDirigido)g).sideIDs.contains(id);
    }

    @Override
    public ArrayList<Lado> lados(Grafo g) {
        return ((GrafoNoDirigido) g).gLados;
    }

    @Override
    public int numeroVertices(Grafo g) {
        return ((GrafoNoDirigido) g).graph.size();
    }

    @Override
    public int numeroLados(Grafo g) {
        return ((GrafoNoDirigido)g).gLados.size();
    }

    @Override
    public int grado(Grafo g, int id) throws NoSuchElementException {
        try {
            if (!estaVertice(g, id)){
                throw new NoSuchElementException();
            }
            else{
                for (ALNode alNode : graph) {
                    if (alNode.obtenerID() == id){
                        return alNode.obtenerAdyacencias().size();
                    }
                }
                return 0;
            }
        } catch (NoSuchElementException e) {
            System.out.println("El nodo con identificador: " + id + " no pertenece al grafo!");
            return -1;
        }
    }

    @Override
    public LinkedList<Vertice> adyacentes(Grafo g, int id) throws NoSuchElementException {
        if (!g.estaVertice(g, id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = ((GrafoNoDirigido) g).graph;
            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    return alNode.obtenerAdyacencias();
                }
            }
        }
        return null;
    }

    @Override
    public LinkedList<Lado> incidentes(Grafo g, int id) throws NoSuchElementException {
        if (!((GrafoNoDirigido)g).nodeIDs.contains(id)){
            throw new NoSuchElementException();
        }
        else{

        }
        return null;
    }

    @Override
    public Grafo clone(Grafo g) {
        GrafoNoDirigido xd = new GrafoNoDirigido();
        xd.graph = new LinkedList<ALNode>(((GrafoNoDirigido)g).graph);
        xd.nodeIDs = new HashSet<Integer>(((GrafoNoDirigido)g).nodeIDs);
        xd.sideIDs = new HashSet<Integer>(((GrafoNoDirigido)g).sideIDs);
        xd.gLados = new ArrayList<Lado>(((GrafoNoDirigido)g).gLados);
        return xd;
    }

    @Override
    public String toString(Grafo g) {
        // TODO Auto-generated method stub
        return null;
    }
}