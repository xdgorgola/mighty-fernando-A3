import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Lados.Arco;
import Lados.Lado;
import Vertice.Vertice;

/**
 * GrafoNoDirigido
 */
public class GrafoDirigido implements Grafo {

    private HashSet<Integer> nodeIDs;
    private HashSet<String> nodeNames;
    private HashSet<Integer> sideIDs;
    private ArrayList<Lado> gLados;

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
        if (line.equals("D")) {
            g = new GrafoDirigido();
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
            Arco toAdd = null;// new Arista(id, iVertice, fVertice, weight)
            if (!((GrafoDirigido) g).agregarArco(g, toAdd)) {
                scan.close();
                return false;
            }
        }
        scan.close();
        return true;
    }

    @Override
    public boolean agregarVertice(Grafo g, Vertice v) {
        GrafoDirigido gd = (GrafoDirigido) g;
        if (gd.estaVertice(g, Vertice.obtenerID(v)) || gd.estaVertice(g, Vertice.obtenerNombre(v))) {
            return false;
        } else {
            ALNode toAdd = new ALNode(v);
            nodeIDs.add(Vertice.obtenerID(v));
            nodeNames.add(Vertice.obtenerNombre(v));
            graph.add(toAdd);
            return true;
        }
    }

    @Override
    public boolean agregarVertice(Grafo g, int id, String name, double x, double y, double w) {
        GrafoDirigido gd = (GrafoDirigido) g;
        if (gd.estaVertice(g, id) || gd.estaVertice(g, name)) {
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
            GrafoDirigido gd = (GrafoDirigido) g;
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
                    LinkedList<Vertice> predecesors = alNode.obtenerPredecesores();
                    for (Vertice v : predecesors) {
                        if (Vertice.obtenerID(v) == id) {
                            predecesors.remove(v);
                            break;
                        }
                    }
                }
            }
            gd.nodeIDs.remove((Integer) id);
            return true;
        }
    }

    @Override
    public boolean estaVertice(Grafo g, int id) {
        return ((GrafoDirigido) g).nodeIDs.contains(id);
    }

    public boolean estaVertice(Grafo g, String nombre) {
        return ((GrafoDirigido) g).nodeNames.contains(nombre);
    }

    @Override
    public Vertice obtenerVertice(Grafo g, int id) throws NoSuchElementException {
        if (!g.estaVertice(g, id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = ((GrafoDirigido) g).graph;
            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    return alNode.obtenerVertice();
                }
            }
        }
        return null;
    }

    public Vertice obtenerVertice(Grafo g, String nombre) throws NoSuchElementException {
        GrafoDirigido gd = (GrafoDirigido) g;
        if (!gd.estaVertice(g, nombre)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = gd.graph;
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
        for (ALNode alNode : ((GrafoDirigido) g).graph) {
            verts.add(alNode.obtenerVertice());
        }
        return verts;
    }

    public boolean agregarArco(Grafo g, Arco a) {
        GrafoDirigido gd = (GrafoDirigido) g;
        Vertice vi = Arco.obtenerVerticeInicial(a);
        Vertice vf = Arco.obtenerVerticeFinal(a);
        if (!gd.estaVertice(gd, Vertice.obtenerID(vi)) || !gd.estaVertice(gd, Vertice.obtenerID(vf))
                || gd.sideIDs.contains(Lado.obtenerID(a))) {
            return false;
        }else{
            gd.sideIDs.add(Lado.obtenerID(a));
            gd.gLados.add(a);
            for (ALNode alnode : gd.graph){
                if (alnode.obtenerID()== Vertice.obtenerID(vi)) {
                    alnode.agregarPredecesor(vf);
                }else if (alnode.obtenerID()== Vertice.obtenerID(vf)){
                    alnode.agregarVertice(vi);
                }
            }
        }
        return false;
    }

    public boolean agregarArco(Grafo g, String u, String v, int tipo, double p) {
        GrafoDirigido gd = new GrafoDirigido();
        if (estaArco(g, u, v, tipo)) {
            return false;
        }
        if (!gd.estaVertice(g, u) || !gd.estaVertice(g, v)) {
            return false;
        } else {
            Vertice iVertice = gd.obtenerVertice(g, u);
            Vertice fVertice = gd.obtenerVertice(g, v);
            Arco toAdd = new Arco(tipo, iVertice, fVertice, p);
            gd.gLados.add(toAdd);
            gd.sideIDs.add(tipo);

            for (ALNode alNode : gd.graph) {
                if (alNode.obtenerNombre() == u){
                    alNode.agregarVertice(fVertice);
                }
                else if (alNode.obtenerNombre() == v){
                    alNode.agregarPredecesor(iVertice);
                }
            }
        }
        return false;
    }

    public boolean estaArco(Grafo g, String u, String v, int tipo) {
        GrafoDirigido gd = new GrafoDirigido();
        if (!gd.estaVertice(g, u) || !gd.estaVertice(g, v) || !gd.estaArco(g, tipo)) {
            return false;
        } else {
            for (ALNode alNode : gd.graph) {
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

    public boolean estaArco(Grafo g, int id) {
        return ((GrafoDirigido) g).sideIDs.contains(id);
    }

    @Override
    public ArrayList<Lado> lados(Grafo g) {
        return ((GrafoDirigido) g).gLados;
    }

    @Override
    public int numeroVertices(Grafo g) {
        return ((GrafoDirigido) g).graph.size();
    }

    @Override
    public int numeroLados(Grafo g) {
        return ((GrafoDirigido) g).gLados.size();
    }

    public int gradoInterno(Grafo g, int id) throws NoSuchElementException {
        try {
            if (!estaVertice(g, id)) {
                throw new NoSuchElementException();
            } else {
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

    public int gradoExterno(Grafo g, int id) throws NoSuchElementException {
        try {
            if (!estaVertice(g, id)) {
                throw new NoSuchElementException();
            } else {
                for (ALNode alNode : graph) {
                    if (alNode.obtenerID() == id) {
                        return alNode.obtenerPredecesores().size();
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
    public int grado(Grafo g, int id) {
        try {
            if (!estaVertice(g, id)) {
                throw new NoSuchElementException();
            } else {
                return (gradoInterno(g, id) + gradoExterno(g, id));
            }
        } catch (NoSuchElementException e) {
            System.out.println("El nodo con identificador: " + id + " no pertenece al grafo!");
            return -1;
        }
    }

    public LinkedList<Vertice> sucesores(Grafo g, int id) throws NoSuchElementException {
        if (!g.estaVertice(g, id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = ((GrafoDirigido) g).graph;
            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    return alNode.obtenerAdyacencias();
                }
            }
        }
        return null;
    }

    public LinkedList<Vertice> predecesores(Grafo g, int id) throws NoSuchElementException {
        if (!g.estaVertice(g, id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = ((GrafoDirigido) g).graph;
            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    return alNode.obtenerPredecesores();
                }
            }
        }
        return null;
    }

    @Override
    public LinkedList<Vertice> adyacentes(Grafo g, int id) throws NoSuchElementException {
        if (!g.estaVertice(g, id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = ((GrafoDirigido) g).graph;
            LinkedList<Vertice> s= new LinkedList<Vertice>();
            LinkedList<Vertice> p= new LinkedList<Vertice>();
            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    s= alNode.obtenerAdyacencias();
                    p= alNode.obtenerPredecesores();
                }
            }
            LinkedList<Vertice> a = new LinkedList<Vertice>();
            a.addAll(s);
            a.addAll(p);
            return a;
        }
    }

    @Override
    public LinkedList<Lado> incidentes(Grafo g, int id) throws NoSuchElementException {
        GrafoDirigido gd = (GrafoDirigido) g;
        if (!gd.nodeIDs.contains(id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<Lado> incidentes = new LinkedList<Lado>();
            for (Lado lado : gd.gLados){
                if (Vertice.obtenerID(Arco.obtenerVerticeInicial((Arco) lado)) == id
                || Vertice.obtenerID(Arco.obtenerVerticeFinal((Arco) lado)) == id) {
                incidentes.add(lado);
                 }
            }
        return incidentes;
        }
    }

    @Override
    public Grafo clone(Grafo g) {
        GrafoDirigido gd = (GrafoDirigido) g;
        GrafoDirigido c = new GrafoDirigido();
        c.graph = new LinkedList<ALNode>(gd.graph);
        c.nodeIDs = new HashSet<Integer>(gd.nodeIDs);
        c.sideIDs = new HashSet<Integer>(gd.sideIDs);
        c.gLados = new ArrayList<Lado>(gd.gLados);
        return c;
    }

    @Override
    public String toString(Grafo g) {
        StringBuilder aux = new StringBuilder();
        aux.append("Nodos del grafo: \n");
        GrafoDirigido gd = (GrafoDirigido) g;
        for (ALNode alNode : gd.graph) {
            aux.append(Vertice.toString(alNode.obtenerVertice()));
            aux.append("\n");
        }
        aux.append("--------------------------------------------------------------\n");
        for (Lado lado : gd.gLados) {
            aux.append(((Arco) lado).toString(lado));
            aux.append("\n");
        }
        aux.append("--------------------------------------------------------------");
        return aux.toString();
    }

    public static void main(String[] args) {
        GrafoDirigido g = new GrafoDirigido();
        g.agregarVertice(g, 0, "mano", 0, 0, 10);
        g.agregarVertice(g, 1, "que", 0, 0, 10);
        g.agregarVertice(g, 2, "chorizos", 0, 0, 10);
        g.agregarArco(g, "que", "chorizos", 1, 10);
        g.agregarArco(g, "que", "chorizos", 1, 10);
        g.agregarArco(g, "que", "chorizos", 2, 10);
        System.out.println(g.toString(g));
    }
}
