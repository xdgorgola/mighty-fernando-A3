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

    /**
     * IDs de los vertices en el grafo.
     */
    private HashSet<Integer> nodeIDs;
    /**
     * Nombres de los vertices en el grafo.
     */
    private HashSet<String> nodeNames;
    /**
     * IDs de las aristas en el grafo.
     */
    private HashSet<Integer> sideIDs;
    /**
     * Lados en el grafo.
     */
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

    /**
     * Representacion como lista de adyacencia del grafo.
     */
    private LinkedList<ALNode> graph;

    /**
     * Retorna las IDs de los vertices en el grafo.
     * 
     * @return HashSet con las IDs de los vertices
     */
    public HashSet<Integer> getVerticesIDs() {
        return nodeIDs;
    }

    /**
     * Retorna la representacion del grafo.
     * 
     * @return Grafo como lista de adyacencia
     */
    public LinkedList<ALNode> getGraph() {
        return graph;
    }

    @Override
    public boolean cargarGrafo(Grafo g, String file) throws FileNotFoundException {
        GrafoNoDirigido gnd = null;
        int n = 0;
        int m = 0;
        Scanner scan = new Scanner(new File(file));

        String line = scan.nextLine();
        if (line.equals("ND")) {
            gnd = (GrafoNoDirigido) g;

            gnd.gLados.clear();
            gnd.sideIDs.clear();
            gnd.nodeIDs.clear();
            gnd.nodeNames.clear();
            gnd.graph.clear();
        } else {
            scan.close();
            return false;
        }

        try {
            line = scan.nextLine();
            n = Integer.parseInt(line);
            System.out.println("n: " + n);
            line = scan.nextLine();
            m = Integer.parseInt(line);
            System.out.println("m: " + m);
            if (n < 0 || m < 0){
                scan.close();
                return false;
            }
        } catch (NumberFormatException e) {
            scan.close();
            return false;
        }

        for (int i = 0; i < n; i++) {
            line = scan.nextLine();
            String[] results = line.trim().split("\\s+");
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
            String[] results = line.trim().split("\\s+");
            if (results.length != 4) {
                scan.close();
                return false;
            }
            Vertice vi = gnd.obtenerVertice(g, results[0]);
            Vertice vf = gnd.obtenerVertice(g, results[1]);
            Arista toAdd = new Arista(Integer.parseInt(results[2]), vi, vf, Double.parseDouble(results[3]));
            if (!gnd.agregarArista(g, toAdd)) {
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
            while (gIterator.hasNext()) {
                ALNode actAlNode = gIterator.next();
                if (actAlNode.obtenerID() == id) {
                    gIterator.remove();
                } else {
                    ListIterator<Vertice> adjIterator = actAlNode.obtenerAdyacencias().listIterator();
                    while (adjIterator.hasNext()) {
                        Vertice actVertice = adjIterator.next();
                        if (Vertice.obtenerID(actVertice) == id) {
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

    /**
     * Chequea si un vertice con cierto nombre pertenece al grafo g.
     * 
     * @param g      Grafo a buscar vertice.
     * @param nombre Nombre de vertice a buscar en g.
     * @return true si el vertice se encuentra en el grafo/false en caso contrario.
     */
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

    /**
     * Busca un vertice con cierto nombre en el grafo g y lo retorna. Si el vertice
     * no se encuentra en el grao g, levanta una excepcion.
     * 
     * @param g      Grafo de donde obtener el vertice
     * @param nombre Nombre del vertice a buscar en el grafo
     * @return El vertice si lo encuentra en el grafo/null sino.
     * @throws NoSuchElementException Si el vertice de identificador id no se
     *                                encuentra en el grafo g.
     */
    public Vertice obtenerVertice(Grafo g, String nombre) throws NoSuchElementException {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        if (!gnd.estaVertice(g, nombre)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = gnd.graph;
            for (ALNode alNode : graph) {
                if (alNode.obtenerNombre().equals(nombre)) {
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

    /**
     * Intenta agregar una arista al grafo. Si la arista ya existe o algun vertice
     * no existe en el grafo, no hace nada.
     * 
     * @param g Grafo a agregar arista.
     * @param a Arista a agregar.
     * @return true si la arista se agrego/false en otro caso
     */
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
            return true;
        }
    }

    /**
     * Intenta agregar una arista al grafo. Si la arista ya existe o algun vertice
     * no existe en el grafo, no hace nada.
     * 
     * @param g    Grafo a agregar arista
     * @param u    Nombre del vertice 1 de la arista
     * @param v    Nombre del vertice 2 de la arista
     * @param tipo Identificador de la arista
     * @param p    Peso de la arista
     * @return true si se agrego la arista/false en caso contrario
     */
    public boolean agregarArista(Grafo g, String u, String v, int tipo, double p) {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        if (estaArista(g, u, v, tipo) || !gnd.estaVertice(g, u) || !gnd.estaVertice(g, v) || estaArista(g, tipo)) {
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
            return true;
        }
    }

    /**
     * Chequea si una arista existe en el grafo.
     * 
     * @param g    Grafo a chequear si la arista existe
     * @param u    Nombre del vertice 1 de la arista
     * @param v    Nombre del vertice 2 de la arista
     * @param tipo Identificador de la arista
     * @return true si existe la arista/false en caso contrario
     */
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

    /**
     * Chequea si una arista existe en el grafo.
     * 
     * @param g  Grafo a buscar arista
     * @param id Identificador de la arista a buscar
     * @return true si la arista esta en el grafo/false en caso contrario
     */
    public boolean estaArista(Grafo g, int id) {
        return ((GrafoNoDirigido) g).sideIDs.contains(id);
    }

    /**
     * Chequea si existe una arista en el grafo g que conecte dos vertices en especifico.
     * 
     * @param g Grafo a chequear si existe arista
     * @param id1 Vertice 1 del grafo
     * @param id2 Vertice 2 del grafo
     * @return true si existe la arista/false en otro caso
     */
    public boolean existeArista(Grafo g, int id1, int id2) {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        for (Lado lado : gnd.gLados) {
            Arista a = (Arista) lado;
            if ((Vertice.obtenerID(Arista.obtenerVertice1(a)) == id1
                    && Vertice.obtenerID(Arista.obtenerVertice2(a)) == id2)
                    || (Vertice.obtenerID(Arista.obtenerVertice2(a)) == id1
                            && Vertice.obtenerID(Arista.obtenerVertice2(a)) == id2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene una arista en el grafo g con un identificador en especifico.
     * 
     * @param g Grafo a buscar arista
     * @param id Identificador de la arista a buscar
     * @return Arista buscada
     * @throws NoSuchElementException Si no existe la arista buscada
     */
    public Arista obtenerArista(Grafo g, int id) throws NoSuchElementException {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        if (!gnd.sideIDs.contains(id)) {
            throw new NoSuchElementException();
        } else {
            for (Lado lado : gLados) {
                if (Lado.obtenerTipo(lado) == id) {
                    return (Arista) lado;
                }
            }
            return null;
        }
    }

    /**
     * En el grafo g elimina una arista con un identificador en especifico.
     * 
     * @param g Grafo a eliminar arista
     * @param id Identificador de la arista a eliminar
     * @return true si se elimino la arista/false en caso contrario
     */
    public boolean eliminarArista(Grafo g, int id) {
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        if (!gnd.estaArista(g, id)) {
            return false;
        } else {
            Arista a = gnd.obtenerArista(g, id);

            Vertice v1 = Arista.obtenerVertice1(a);
            Vertice v2 = Arista.obtenerVertice2(a);
            int id1 = Vertice.obtenerID(Arista.obtenerVertice1(a));
            int id2 = Vertice.obtenerID(Arista.obtenerVertice2(a));

            gnd.sideIDs.remove(id);
            gnd.gLados.remove(a);

            // Hay que chequear si existe una arista entre ambos nodos primero.
            if (!existeArista(g, id1, id2)) {
                System.out.println("ptoo");
                // Buscamos los nodos con esas ID y los borramos
                int c = 0;
                for (ALNode node : gnd.graph) {
                    if (node.obtenerID() == id1) {
                        node.obtenerAdyacencias().remove(v2);
                        c++;
                    } else if (node.obtenerID() == id2) {
                        node.obtenerAdyacencias().remove(v1);
                        c++;
                    }
                    if (c == 2) {
                        return true;
                    }
                }
            }
            return false;
        }
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
        String aux = "vertices del grafo: \n";
        GrafoNoDirigido gnd = (GrafoNoDirigido) g;
        for (ALNode alNode : gnd.graph) {
            aux += Vertice.toString(alNode.obtenerVertice()) + "\n";
        }
        aux += "--------------------------------------------------------------\n";
        for (Lado lado : gnd.gLados) {
            aux += ((Arista) lado).toString(lado) + "\n\n";
        }
        aux += "--------------------------------------------------------------\n";
        aux += "IDS de vertices en el grafo: \n";
        aux += gnd.nodeIDs.toString() + "\n";
        aux += "Nombres de vertices en el grafo: \n";
        aux += gnd.nodeNames.toString() + "\n";
        aux += "IDS de aristas en el grafo: \n";
        aux += gnd.sideIDs.toString() + "\n";
        return aux;
    }

    public GrafoNoDirigido() {
        nodeIDs = new HashSet<Integer>();
        nodeNames = new HashSet<String>();
        sideIDs = new HashSet<Integer>();
        gLados = new ArrayList<Lado>();
        graph = new LinkedList<ALNode>();
    }

    public static void main(String[] args) {
        GrafoNoDirigido g = new GrafoNoDirigido();

        //// g.agregarVertice(g, 0, "batman", 0, 0, 10);
        //// g.agregarVertice(g, 1, "vs", 0, 0, 10);
        //// g.agregarVertice(g, 2, "superman", 0, 0, 10);
        ////
        //// g.agregarArista(g, "batman", "vs", 0, 0);
        //// g.agregarArista(g, "batman", "vs", 1, 0);
        ////
        //// g.agregarArista(g, "superman", "vs", 0, 0);
        //// g.agregarArista(g, "superman", "vs", 1, 0);
        ////
        //// g.agregarArista(g, "superman", "vs", 2, 0);
        //// g.agregarArista(g, "superman", "vs", 3, 0);
        ////
        //// g.agregarArista(g, "batman", "superman", 4, 0);
        //// g.agregarArista(g, "batman", "superman", 5, 0);
        ////
        //// g.agregarArista(g, "batman", "batman", 6, 0);
        //// g.agregarArista(g, "batman", "batman", 7, 0);
        //// g.agregarArista(g, "batman", "batman", 8, 0);
        //// System.out.println(g.toString(g));
        //// g.eliminarArista(g, 8);
        //// g.eliminarArista(g, 7);
        //// g.eliminarArista(g, 6);
        //// g.eliminarArista(g, 5);
        //// g.eliminarArista(g, 4);
        //// g.eliminarArista(g, 4);
        //// g.eliminarArista(g, 1);
        //// System.out.println(g.toString(g));
        try {
            g.cargarGrafo(g, "/home/gorgola/Desktop/CI2693-Lab-Algos-III/mighty-fernando-A3/proyecto-1/testsuperman");
            System.out.println(g.toString(g));
        } catch (FileNotFoundException e) {
            System.out.println("algo fallo");
        }
    }
}