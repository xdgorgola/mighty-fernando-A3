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

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

/**
 * GrafoDirigido
 */
public class GrafoDirigido implements Grafo {

    /**
     * IDs de los vertices en el grafo.
     */
    private HashSet<Integer> nodeIDs;
    /**
     * Nombres de los vertices en el grafo.
     */
    private HashSet<String> nodeNames;
    /**
     * IDs de los arcos en el grafo.
     */
    private HashSet<Integer> sideIDs;
    /**
     * Arcos en el grafo.
     */
    private ArrayList<Lado> gLados;
    /**
     * Representacion como lista de nodos del grafo.
     */
    private LinkedList<ALNode> graph;
    /**
     * Lineas habilitadas
     */
    private ArrayList<String> gLineas;

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

    /***
     * Retorna las lineas habilitadas en el grafo.
     * 
     * @return Las lineas habilitadas como ArrayList de string
     */
    public ArrayList<String> getLineas() {
        return gLineas;
    }

    @Override
    public boolean cargarGrafo(String file) throws FileNotFoundException {
        int n = 0;
        int m = 0;
        Scanner scan = new Scanner(new File(file));

        String line = scan.nextLine();
        if (line.equals("d")) {
            gLados.clear();
            sideIDs.clear();
            nodeIDs.clear();
            nodeNames.clear();
            graph.clear();
        } else {
            scan.close();
            return false;
        }

        try {
            line = scan.nextLine().trim();
            // Numero de paradas/vertices
            n = Integer.parseInt(line);
            System.out.println("n: " + n);
            line = scan.nextLine().trim();
            // Numero de tramos/arcos
            m = Integer.parseInt(line);
            System.out.println("m: " + m);
            if (n < 0 || m < 0) {
                scan.close();
                return false;
            }
        } catch (NumberFormatException e) {
            System.err.println("Formato incorrecto, no hay un numero en nro de paradas/tramos.");
            scan.close();
            return false;
        }

        for (int i = 0; i < n; i++) {
            line = scan.nextLine();
            String[] results = line.trim().split("\\s+");
            if (results.length != 5) {
                System.err.println("Error en parada: " + i);
                System.err.println("Definicion de parada incompleta o con datos extra.");
                scan.close();
                return false;
            }
            Vertice toAdd = new Vertice(Integer.parseInt(results[0]), results[1].trim(), Double.parseDouble(results[2]),
            Double.parseDouble(results[3]), Double.parseDouble(results[4]));
            if (!agregarVertice(toAdd)) {
                System.err.println("Error en parada: " + i);
                System.err.println("No pudo agregarse.");
                scan.close();
                return false;
            }
        }
        for (int i = 0; i < m; i++) {
            line = scan.nextLine();
            String[] results = line.trim().split("\\s+");
            if (results.length != 4) {
                System.err.println("Error en tramo: " + i);
                System.err.println("Definicion de parada incompleta o con datos extra.");
                scan.close();
                return false;
            }
            Vertice vi = obtenerVertice(Integer.parseInt(results[0]));
            Vertice vf = obtenerVertice(Integer.parseInt(results[1]));
            Arco toAdd = new Arco(i, vi, vf, Double.parseDouble(results[3]), results[2].trim());
            if (!agregarArco(toAdd)) {
                System.err.println("Error en tramo: " + i);
                System.err.println("No pudo agregarse.");
                scan.close();
                return false;
            }
        }
        scan.close();
        return true;
    }

    @Override
    public boolean agregarVertice(Vertice v) {

        if (estaVertice(v.obtenerID()) || estaVertice(v.obtenerNombre())) {
            return false;
        } else {
            ALNode toAdd = new ALNode(v);
            nodeIDs.add(v.obtenerID());
            nodeNames.add(v.obtenerNombre());
            graph.add(toAdd);
            return true;
        }
    }

    @Override
    public boolean agregarVertice(int id, String name, double x, double y, double w) {

        if (estaVertice(id) || estaVertice(name)) {
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
    public boolean eliminarVertice(int id) {
        if (!estaVertice(id)) {
            return false;
        } else {

            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    graph.remove(alNode);
                } else {
                    LinkedList<Vertice> adjacencies = alNode.obtenerAdyacencias();
                    for (Vertice v : adjacencies) {
                        if (v.obtenerID() == id) {
                            adjacencies.remove(v);
                            break;
                        }
                    }
                    LinkedList<Vertice> predecesors = alNode.obtenerPredecesores();
                    for (Vertice v : predecesors) {
                        if (v.obtenerID() == id) {
                            predecesors.remove(v);
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
    public boolean estaVertice(int id) {
        return nodeIDs.contains(id);
    }

    /**
     * Revisa si un vertice con cierto nombre pertenece al grafo
     * 
     * @param nombre Nombre de vertice a buscar en el grafo.
     * @return true si el vertice esta en el grafo; false en de no ser asi.
     */
    public boolean estaVertice(String nombre) {
        return nodeNames.contains(nombre);
    }

    @Override
    public Vertice obtenerVertice(int id) throws NoSuchElementException {
        if (!estaVertice(id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = this.graph;
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
     * no se encuentra en el grafo levanta una excepcion.
     * 
     * @param nombre Nombre del vertice a buscar en el grafo.
     * @return El vertice si lo encuentra en el grafo/null si no lo hace.
     * @throws NoSuchElementException Si el vertice de identificador id no se
     *                                encuentra en el grafo
     */
    public Vertice obtenerVertice(String nombre) throws NoSuchElementException {

        if (!estaVertice(nombre)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = this.graph;
            for (ALNode alNode : graph) {
                if (alNode.obtenerNombre().equals(nombre)) {
                    return alNode.obtenerVertice();
                }
            }
        }
        return null;
    }

    @Override
    public LinkedList<Vertice> vertices() {
        LinkedList<Vertice> vertices = new LinkedList<Vertice>();
        for (ALNode alNode : graph) {
            vertices.add(alNode.obtenerVertice());
        }
        return vertices;
    }

    /**
     * Intenta agregar un arco al grafo y retorna true si lo logra. Si el arco ya
     * existe o algun vertice no existe en el grafo, retorna falso
     * 
     * @param a Arco a agregar.
     * @return true si la arco se agrego; false en otro caso
     */
    public boolean agregarArco(Arco a) {

        Vertice vi = a.obtenerVerticeInicial();
        Vertice vf = a.obtenerVerticeFinal();
        if (!estaVertice(vi.obtenerID()) || !estaVertice(vf.obtenerID()) || sideIDs.contains(a.obtenerID())) {
            return false;
        } else {
            sideIDs.add(a.obtenerID());
            gLados.add(a);
            for (ALNode alnode : graph) {
                if (alnode.obtenerID() == vf.obtenerID() && !alnode.obtenerPredecesores().contains(vf)) {
                    alnode.agregarPredecesor(vi);
                } else if (alnode.obtenerID() == vi.obtenerID() && !alnode.obtenerAdyacencias().contains(vf)) {
                    alnode.agregarVertice(vf);
                }
            }
        }
        return true;
    }

    /**
     * Intenta agregar una arista al grafo. Si la arista ya existe o algun vertice
     * no existe en el grafo, no hace nada.
     * 
     * @param u    Nombre del vertice inicial del arco.
     * @param v    Nombre del vertice final del arco.
     * @param tipo Identificador del arco.
     * @param p    Peso del arco.
     * @return true si la arista se agrego/false en otro caso
     */
    public boolean agregarArco(String u, String v, int tipo, double p, String linea) {

        if (estaArco(u, v, tipo) || !estaVertice(u) || !estaVertice(v) || estaArco(tipo)) {
            return false;
        } else {
            Vertice vi = obtenerVertice(u);
            Vertice vf = obtenerVertice(v);
            Arco toAdd = new Arco(tipo, vi, vf, p, linea);
            gLados.add(toAdd);
            sideIDs.add(tipo);

            for (ALNode alNode : graph) {
                if (alNode.obtenerNombre().equals(u)) {
                    alNode.agregarVertice(vf);
                } else if (alNode.obtenerNombre().equals(v)) {
                    alNode.agregarPredecesor(vi);
                }
            }
            return true;
        }
    }

    /**
     * Chequea si un arco existe en el grafo.
     * 
     * @param u    Nombre del vertice inicial del arco.
     * @param v    Nombre del vertice final del arco.
     * @param tipo Identificador del arco.
     * @return true si existe el arco/false en caso contrario
     */
    public boolean estaArco(String u, String v, int tipo) {

        if (!estaVertice(u) || !estaVertice(v) || !estaArco(tipo)) {
            return false;
        } else {
            for (ALNode alNode : graph) {
                Vertice ver = alNode.obtenerVertice();
                if (ver.obtenerNombre().equals(u)) {
                    LinkedList<Vertice> sucesors = alNode.obtenerAdyacencias();
                    for (Vertice suce : sucesors) {
                        if (suce.obtenerNombre().equals(v)) {
                            return true;
                        }
                    }
                } else if (ver.obtenerNombre().equals(v)) {
                    LinkedList<Vertice> predecesors = alNode.obtenerPredecesores();
                    for (Vertice pred : predecesors) {
                        if (pred.obtenerNombre().equals(v)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Chequea si un arco existe en el grafo.
     * 
     * @param tipo Identificador del arco.
     * @return true si existe el arclo/false en caso contrario
     */
    public boolean estaArco(int tipo) {
        return sideIDs.contains(tipo);
    }

    /**
     * Intenta eliminar un arco en el grafo
     * 
     * @param u    Nombre del vertice inicial del arco.
     * @param v    Nombre del vertice final del arco.
     * @param tipo Identificador del arco.
     * @return true si se elimina el arco/false en caso contrario
     */
    public boolean eliminarArco(String u, String v, int tipo) {

        if (!estaArco(u, v, tipo)) {
            return false;
        } else {
            Arco arco = obtenerArco(tipo);
            sideIDs.remove(tipo);
            gLados.remove(arco);

            Vertice idu = obtenerVertice(u);
            Vertice idv = obtenerVertice(v);

            for (ALNode node : graph) {

                if (node.obtenerPredecesores().contains(idu)) {
                    node.obtenerPredecesores().remove(idu);
                }

                if (node.obtenerAdyacencias().contains(idv)) {
                    node.obtenerAdyacencias().remove(idv);
                }
            }
            return true;
        }
    }

    /**
     * Obtiene un arco en el grafo g con un identificador en especifico.
     * 
     * @param tipo Identificador del arco a buscar
     * @return Arco buscado
     * @throws NoSuchElementException Si no existe el arco buscado
     */
    public Arco obtenerArco(int tipo) {
        if (!(estaArco(tipo))) {
            throw new NoSuchElementException();
        } else {
            ArrayList<Lado> glados = gLados;
            for (Lado lado : glados) {
                if (lado.obtenerTipo() == tipo) {
                    return (Arco) lado;
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<Lado> lados() {
        return gLados;
    }

    @Override
    public int numeroVertices() {
        return graph.size();
    }

    @Override
    public int numeroLados() {
        return gLados.size();
    }

    /**
     * 
     * Devuelve el grado interno de un nodo en especifico.
     * 
     * @param id identificador del nodo.
     * @return grado interno del nodo con identificador id.
     * @throws NoSuchElementException si el nodo no esta en el grafo especificado.
     */
    public int gradoInterno(int id) throws NoSuchElementException {
        try {
            if (!estaVertice(id)) {
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

    /**
     * 
     * Devuelve el grado externo de un nodo en especifico.
     * 
     * @param id identificador del nodo.
     * @return grado externo del nodo con identificador id.
     * @throws NoSuchElementException si el nodo no esta en el grafo especificado.
     */
    public int gradoExterno(int id) throws NoSuchElementException {
        try {
            if (!estaVertice(id)) {
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
    public int grado(int id) {
        try {
            if (!estaVertice(id)) {
                throw new NoSuchElementException();
            } else {
                return (gradoInterno(id) + gradoExterno(id));
            }
        } catch (NoSuchElementException e) {
            System.out.println("El nodo con identificador: " + id + " no pertenece al grafo!");
            return -1;
        }
    }

    /**
     * 
     * Devuelve todos los sucesores de un nodo en especifico.
     * 
     * @param id identificador del nodo.
     * @return lista con los nodos sucesores.
     * @throws NoSuchElementException si el nodo no esta en el grafo especificado.
     */
    public LinkedList<Vertice> sucesores(int id) throws NoSuchElementException {
        if (!estaVertice(id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = this.graph;
            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    return alNode.obtenerAdyacencias();
                }
            }
        }
        return null;
    }

    /**
     * 
     * Devuelve todos los predecesores de un nodo en especifico.
     * 
     * @param id identificador del nodo.
     * @return lista con los nodos predecesores.
     * @throws NoSuchElementException si el nodo no esta en el grafo especificado.
     */
    public LinkedList<Vertice> predecesores(int id) throws NoSuchElementException {
        if (!estaVertice(id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = this.graph;
            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    return alNode.obtenerPredecesores();
                }
            }
        }
        return null;
    }

    @Override
    public LinkedList<Vertice> adyacentes(int id) throws NoSuchElementException {
        if (!estaVertice(id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = this.graph;
            LinkedList<Vertice> s = new LinkedList<Vertice>();
            LinkedList<Vertice> p = new LinkedList<Vertice>();
            for (ALNode alNode : graph) {
                if (alNode.obtenerID() == id) {
                    s = alNode.obtenerAdyacencias();
                    p = alNode.obtenerPredecesores();
                }
            }
            LinkedList<Vertice> a = new LinkedList<Vertice>();
            a.addAll(s);
            a.addAll(p);
            return a;
        }
    }

    @Override
    public LinkedList<Lado> incidentes(int id) throws NoSuchElementException {

        if (!nodeIDs.contains(id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<Lado> incidentes = new LinkedList<Lado>();
            for (Lado lado : gLados) {
                if ((((Arco) lado).obtenerVerticeInicial()).obtenerID() == id
                        || (((Arco) lado).obtenerVerticeFinal()).obtenerID() == id) {
                    incidentes.add(lado);
                }
            }
            return incidentes;
        }
    }

    @Override
    public Grafo clone() {

        GrafoDirigido c = new GrafoDirigido();
        c.graph = new LinkedList<ALNode>();
        for (ALNode node : graph) {
            ALNode clon = new ALNode(node);
            c.graph.add(clon);
        }
        c.nodeIDs = new HashSet<Integer>(nodeIDs);
        c.nodeNames = new HashSet<String>(nodeNames);
        c.sideIDs = new HashSet<Integer>(sideIDs);
        c.gLados = new ArrayList<Lado>();
        for (Lado lado : gLados) {
            Lado clon = new Arco((Arco)lado);
            c.gLados.add(clon);
        }
        return c;
    }

    @Override
    public String toString() {
        StringBuilder aux = new StringBuilder();
        aux.append("Nodos del grafo: \n");

        for (ALNode alNode : graph) {
            aux.append(Vertice.toString(alNode.obtenerVertice()));
            aux.append("\n");
        }
        aux.append("--------------------------------------------------------------\n");
        for (Lado lado : gLados) {
            aux.append(((Arco) lado).toString(lado));
            aux.append("\n");
        }
        aux.append("--------------------------------------------------------------");
        return aux.toString();
    }

    public GrafoDirigido() {
        nodeIDs = new HashSet<Integer>();
        nodeNames = new HashSet<String>();
        sideIDs = new HashSet<Integer>();
        gLados = new ArrayList<Lado>();
        graph = new LinkedList<ALNode>();
    }

}
