import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import Lados.Arista;
import Lados.Lado;
import Vertice.Vertice;

/**
 * GrafoNoDirigido
 */
public class GrafoNoDirigido implements Grafo {

    private HashSet<Integer> nodeIDs;
    private HashSet<String> nodeNames;
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
        if (line.equals("ND")) {
            g = new GrafoNoDirigido();
        } else {
            scan.close();
            return false;
        }

        line = scan.nextLine();
        n = Integer.parseInt(line);
        line = scan.nextLine();
        m = Integer.parseInt(line);

        for (int i = 0; i < n; i++) {
            line = scan.nextLine();
            String[] results = line.split("\\s+");
            if (results.length != 5) {
                scan.close();
                return false;
            }
            Vertice toAdd = new Vertice(Integer.parseInt(results[0]), results[1], Integer.parseInt(results[2]),
                    Integer.parseInt(results[3]), Integer.parseInt(results[4]));
            if (!g.agregarVertice(g, toAdd)) {
                scan.close();
                return false;
            }
        }
        for (int i = 0; i < m; i++) {
            line = scan.nextLine();
            String[] results = line.split("\\s+");
            if (results.length != 4 || Integer.parseInt(results[2]) != 0) {
                scan.close();
                return false;
            }
            Arista toAdd = null;// new Arista(id, iVertice, fVertice, weight)
            if (!((GrafoNoDirigido) g).agregarArista(g, toAdd)) {
                scan.close();
                return false;
            }
        }
        scan.close();
        return true;
    }

    @Override
    public boolean agregarVertice(Grafo g, Vertice v) {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        if (gnd.estaVertice(g, Vertice.obtenerID(v)) || gnd.estaVertice(g, Vertice.obtenerNombre(v))) {
            return false;
        } else {
            ALNode toAddd = new ALNode(v);
            nodeIDs.add(Vertice.obtenerID(v));
            nodeNames.add(Vertice.obtenerNombre(v));
            graph.add(toAddd);
            return true;
        }
    }

    @Override
    public boolean agregarVertice(Grafo g, int id, String name, double x, double y, double w) {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        if (gnd.estaVertice(g, id) || gnd.estaVertice(g, name)) {
            return false;
        } else {
            Vertice v = new Vertice(id, name, x, y, w);
            ALNode toAdd = new ALNode(v);
            nodeIDs.add(id);
            nodeNames.add(name);
            graph.add(toAdd);
            return true;
        }
    }

    @Override
    public boolean eliminarVertice(Grafo g, int id) {
        if (!g.estaVertice(g, id)) {
            return false;
        } else {
            GrafoNoDirigido gnd = (GrafoNoDirigido) g;
            gnd.nodeNames.remove(Vertice.obtenerNombre(gnd.obtenerVertice(g, id)));
            gnd.nodeIDs.remove((Integer) id);
            
            ListIterator<ALNode> gIterator = gnd.graph.listIterator();
            while (gIterator.hasNext()){
                ALNode actAlNode = gIterator.next();
                if (actAlNode.obtenerID() == id){
                    gIterator.remove();
                }
                else{
                    ListIterator<Vertice> adjIterator = actAlNode.obtenerAdyacencias().listIterator();
                    while (adjIterator.hasNext()){
                        Vertice actVertice = adjIterator.next();
                        if (Vertice.obtenerID(actVertice) == id){
                            adjIterator.remove();
                            break;
                        }
                    }
                }
            }

            ListIterator<Lado> iterator = gnd.gLados.listIterator();
            while (iterator.hasNext()) {
                Arista a = (Arista) iterator.next();
                if (Vertice.obtenerID(Arista.obtenerVertice1(a)) == id
                    || Vertice.obtenerID(Arista.obtenerVertice2(a)) == id) {
                    gnd.sideIDs.remove(Arista.obtenerTipo(a));
                    iterator.remove();
                }
            }
            return true;
        }
    }

    @Override
    public boolean estaVertice(Grafo g, int id) {
        return ((GrafoNoDirigido) g).nodeIDs.contains(id);
    }

    public boolean estaVertice(Grafo g, String nombre) {
        return ((GrafoNoDirigido) g).nodeNames.contains(nombre);
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

    // nuevo comentar
    public Vertice obtenerVertice(Grafo g, String nombre) throws NoSuchElementException {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        if (!gnd.estaVertice(g, nombre)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = gnd.graph;
            for (ALNode alNode : graph) {
                if (alNode.obtenerNombre() == nombre) {
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

    public boolean agregarArista(Grafo g, Arista a) {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        Vertice vi = Arista.obtenerVertice1(a);
        Vertice vf = Arista.obtenerVertice2(a);
        if (!g.estaVertice(gnd, Vertice.obtenerID(vi)) || !gnd.estaVertice(gnd, Vertice.obtenerID(vf))
                || estaArista(gnd, Vertice.obtenerNombre(vi), Vertice.obtenerNombre(vf), Arista.obtenerTipo(a))) {
            return false;
        } else {
            gnd.gLados.add(a);
            gnd.sideIDs.add(Arista.obtenerTipo(a));
            for (ALNode alNode : gnd.graph) {
                if (alNode.obtenerNombre() == Vertice.obtenerNombre(vi)) {
                    alNode.agregarVertice(vf);
                } else if (alNode.obtenerNombre() == Vertice.obtenerNombre(vf)) {
                    alNode.agregarVertice(vi);
                }
            }
        }
        return false;
    }

    public boolean agregarArista(Grafo g, String u, String v, int tipo, double p) {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        if (estaArista(g, u, v, tipo) || !gnd.estaVertice(g, u) || !gnd.estaVertice(g, v) ||
            estaArista(g, tipo)){
            return false;
        } else {
            Vertice iVertice = gnd.obtenerVertice(g, u);
            Vertice fVertice = gnd.obtenerVertice(g, v);
            Arista toAdd = new Arista(tipo, iVertice, fVertice, p);
            gnd.gLados.add(toAdd);
            gnd.sideIDs.add(tipo);

            for (ALNode alNode : gnd.graph) {
                if (alNode.obtenerNombre() == u) {
                    alNode.agregarVertice(fVertice);
                } else if (alNode.obtenerNombre() == v) {
                    alNode.agregarVertice(iVertice);
                }
            }
        }
        return false;
    }

    public boolean estaArista(Grafo g, String u, String v, int tipo) {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        if (!gnd.estaVertice(g, u) || !gnd.estaVertice(g, v) || !gnd.estaArista(g, tipo)) {
            return false;
        } else {
            for (ALNode alNode : gnd.graph) {
                Vertice ver = alNode.obtenerVertice();
                if (Vertice.obtenerNombre(ver) == u) {
                    LinkedList<Vertice> adjacents = alNode.obtenerAdyacencias();
                    for (Vertice adj : adjacents) {
                        if (Vertice.obtenerNombre(adj) == v) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean estaArista(Grafo g, int id) {
        return ((GrafoNoDirigido) g).sideIDs.contains(id);
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
        return ((GrafoNoDirigido) g).gLados.size();
    }

    @Override
    public int grado(Grafo g, int id) throws NoSuchElementException {
        try {
            if (!estaVertice(g, id)) {
                throw new NoSuchElementException();
            } else {
                LinkedList<ALNode> graph = ((GrafoNoDirigido) g).graph;
                for (ALNode alNode : graph) {
                    if (alNode.obtenerID() == id) {
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
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        if (!gnd.nodeIDs.contains(id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<Lado> incidentes = new LinkedList<Lado>();
            for (Lado lado : gnd.gLados) {
                if (Vertice.obtenerID(Arista.obtenerVertice1((Arista) lado)) == id
                        || Vertice.obtenerID(Arista.obtenerVertice2((Arista) lado)) == id) {
                    incidentes.add(lado);
                }
            }
        }
        return null;
    }

    @Override
    public Grafo clone(Grafo g) {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        GrafoNoDirigido xd = new GrafoNoDirigido();
        xd.graph = new LinkedList<ALNode>(gnd.graph);
        xd.nodeIDs = new HashSet<Integer>(gnd.nodeIDs);
        xd.sideIDs = new HashSet<Integer>(gnd.sideIDs);
        xd.gLados = new ArrayList<Lado>(gnd.gLados);
        return xd;
    }

    @Override
    public String toString(Grafo g) {
        String aux = "Nodos del grafo: \n";
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        for (ALNode alNode : gnd.graph) {
            aux += Vertice.toString(alNode.obtenerVertice()) + "\n";
        }
        aux += "--------------------------------------------------------------\n";
        for (Lado lado : gnd.gLados) {
            aux += ((Arista) lado).toString(lado) + "\n\n";
        }
        aux += "--------------------------------------------------------------\n";
        aux += "IDS de nodos en el grafo: \n";
        aux += gnd.nodeIDs.toString() + "\n";
        aux += "Nombres de nodos en el grafo: \n";
        aux += gnd.nodeNames.toString()+ "\n";
        aux += "IDS de aristas en el grafo: \n";
        aux += gnd.sideIDs.toString() + "\n";
        return aux;
    }

    public GrafoNoDirigido() {
        nodeIDs = new HashSet<Integer>();
        nodeNames = new HashSet<String>();
        ;
        sideIDs = new HashSet<Integer>();
        ;
        gLados = new ArrayList<Lado>();
        graph = new LinkedList<ALNode>();
    }

    public static void main(String[] args) {
        GrafoNoDirigido g = new GrafoNoDirigido();

        g.agregarVertice(g, 3, "hola", 0, 0, 10);
        g.agregarVertice(g, 4, "senorita", 0, 0, 10);

        Vertice vi = new Vertice(3, "hola", 0, 0, 10);
        Vertice vf = new Vertice(4, "senorita", 0, 0, 10);
        Arista xd = new Arista(0, vi, vf, 10);

        g.agregarArista(g, xd);
        g.eliminarVertice(g, 4);

        vi = new Vertice(4, "fernando", 0, 0, 5);
        vf = new Vertice(5, "feo", 0, 0, 10);
        g.agregarVertice(g, vi);
        g.agregarVertice(g, vf);

        g.agregarArista(g, "fernando", "feo", 1, 0);
        g.agregarArista(g, "hola", "feo", 2, 4);
        g.agregarArista(g, "hola", "hola", 9, 0);
        g.eliminarVertice(g, 5);
        g.eliminarVertice(g, 3);
        g.eliminarVertice(g, 4);
        
        g.agregarVertice(g, 0, "batman", 0, 0, 10);
        g.agregarVertice(g, 1, "vs", 0, 0, 10);
        g.agregarVertice(g, 2, "superman", 0, 0, 10);

        g.agregarArista(g, "batman", "vs", 0, 0);
        g.agregarArista(g, "batman", "vs", 1, 0);

        g.agregarArista(g, "superman", "vs", 0, 0);
        g.agregarArista(g, "superman", "vs", 1, 0);

        g.agregarArista(g, "superman", "vs", 2, 0);
        g.agregarArista(g, "superman", "vs", 3, 0);

        g.agregarArista(g, "batman", "superman", 4, 0);
        g.agregarArista(g, "batman", "superman", 5, 0);

        g.agregarArista(g, "batman", "batman", 6, 0);
        g.agregarArista(g, "batman", "batman", 7, 0);
        g.agregarArista(g, "batman", "batman", 8, 0);

        System.out.println(g.toString(g));
        for (ALNode node : g.graph) {
            String wooo = "Ady nodo " + node.obtenerID() + "\n";
            for (Vertice v : node.obtenerAdyacencias()) {
                wooo += Vertice.obtenerID(v) + ", ";
            }
            wooo += "\n";
            System.out.println(wooo);
        }

        Vertice batman = g.obtenerVertice(g, "batman");
        System.out.println(Vertice.toString(batman));

        g.eliminarVertice(g, 0);
        System.out.println(g.toString(g));
        for (ALNode node : g.graph) {
            String wooo = "Ady nodo " + node.obtenerID() + "\n";
            for (Vertice v : node.obtenerAdyacencias()) {
                wooo += Vertice.obtenerID(v) + ", ";
            }
            wooo += "\n";
            System.out.println(wooo);
        }
        g.eliminarVertice(g, 2);
        System.out.println(g.toString(g));
        for (ALNode node : g.graph) {
            String wooo = "Ady nodo " + node.obtenerID() + "\n";
            for (Vertice v : node.obtenerAdyacencias()) {
                wooo += Vertice.obtenerID(v) + ", ";
            }
            wooo += "\n";
            System.out.println(wooo);
        }
    }
}