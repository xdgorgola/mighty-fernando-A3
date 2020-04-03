import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.lang.Math;
import Lados.Arista;
import Lados.Lado;
import Vertice.Vertice;

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

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
    /**
     * Lineas habilitadas
     */
    private ArrayList<String> gLineas;
    /**
     * Maxima rapidez con la que se recorren los lados
     */
    private double maxSpeed;

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

    /***
     * Retorna las lineas habilitadas en el grafo.
     * 
     * @return Las lineas habilitadas como ArrayList de string
     */
    public ArrayList<String> getLineas() {
        return gLineas;
    }

    /***
     * Retorna llos lados del grafo
     * 
     * @return Los lado como ArrayList de lados
     */
    public ArrayList<Lado> getLados() {
        return gLados;
    }

    /***
     * Retorna la mayor rapidez del grafo.
     * 
     * @return La rapidez maxima entre lados.
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public boolean cargarGrafo(String file) throws FileNotFoundException {
        int n = 0;
        int m = 0;
        Scanner scan = new Scanner(new File(file));

        String line = scan.nextLine();
        if (line.equals("n")) {
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
            // Numero de tramos/aristas
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
            Arista toAdd = new Arista(i, vi, vf, Double.parseDouble(results[3]), results[2].trim());
            if (!agregarArista(toAdd)) {
                System.err.println("Error en tramo: " + i);
                System.err.println("No pudo agregarse.");
                scan.close();
                return false;
            }
            // Calculo de la rapidez maxima
            double R = 6372.8;

            double dLon = Math.toRadians(vf.obtenerX() - vi.obtenerX());
            double dLat = Math.toRadians(vf.obtenerY() - vi.obtenerY());
            double lat1 = Math.toRadians(vi.obtenerY());
            double lat2 = Math.toRadians(vf.obtenerY());

            double a = Math.pow(Math.sin(dLat / 2), 2)
                    + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.asin(Math.sqrt(a));
            if ((R * c) / Double.parseDouble(results[3]) > this.maxSpeed) {
                this.maxSpeed = (R * c) / Double.parseDouble(results[3]);
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
            ALNode toAddd = new ALNode(v);
            nodeIDs.add(v.obtenerID());
            nodeNames.add(v.obtenerNombre());
            graph.add(toAddd);
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
            nodeNames.remove(obtenerVertice(id).obtenerNombre());
            nodeIDs.remove((Integer) id);

            ListIterator<ALNode> gIterator = graph.listIterator();
            while (gIterator.hasNext()) {
                ALNode actAlNode = gIterator.next();
                if (actAlNode.obtenerID() == id) {
                    gIterator.remove();
                } else {
                    ListIterator<Vertice> adjIterator = actAlNode.obtenerAdyacencias().listIterator();
                    while (adjIterator.hasNext()) {
                        Vertice actVertice = adjIterator.next();
                        if (actVertice.obtenerID() == id) {
                            adjIterator.remove();
                            break;
                        }
                    }
                }
            }

            ListIterator<Lado> iterator = gLados.listIterator();
            while (iterator.hasNext()) {
                Arista a = (Arista) iterator.next();
                if (a.obtenerVertice1().obtenerID() == id || a.obtenerVertice2().obtenerID() == id) {
                    sideIDs.remove(a.obtenerTipo());
                    iterator.remove();
                }
            }
            return true;
        }
    }

    @Override
    public boolean estaVertice(int id) {
        return nodeIDs.contains(id);
    }

    /**
     * Chequea si un vertice con cierto nombre pertenece al grafo
     * 
     * @param nombre Nombre de vertice a buscar en
     * @return true si el vertice se encuentra en el grafo/false en caso contrario.
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
     * no se encuentra en el grao g, levanta una excepcion.
     * 
     * @param nombre Nombre del vertice a buscar en el grafo
     * @return El vertice si lo encuentra en el grafo/null sino.
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
        LinkedList<Vertice> verts = new LinkedList<Vertice>();
        for (ALNode alNode : graph) {
            verts.add(alNode.obtenerVertice());
        }
        return verts;
    }

    /**
     * Intenta agregar una arista al grafo. Si la arista ya existe o algun vertice
     * no existe en el grafo, no hace nada.
     * 
     * @param a Arista a agregar.
     * @return true si la arista se agrego/false en otro caso
     */
    public boolean agregarArista(Arista a) {
        Vertice vi = a.obtenerVertice1();
        Vertice vf = a.obtenerVertice2();
        if (!estaVertice(vi.obtenerID()) || !estaVertice(vf.obtenerID())
                || estaArista(vi.obtenerNombre(), vf.obtenerNombre(), a.obtenerTipo())) {
            return false;
        } else {
            gLados.add(a);
            sideIDs.add(a.obtenerTipo());
            for (ALNode alNode : graph) {
                if (alNode.obtenerNombre().equals(vi.obtenerNombre())) {
                    alNode.agregarVertice(vf);
                } else if (alNode.obtenerNombre().equals(vf.obtenerNombre())) {
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
     * @param u    Nombre del vertice 1 de la arista
     * @param v    Nombre del vertice 2 de la arista
     * @param tipo Identificador de la arista
     * @param p    Peso de la arista
     * @return true si se agrego la arista/false en caso contrario
     */
    public boolean agregarArista(String u, String v, int tipo, double p, String linea) {
        if (estaArista(u, v, tipo) || !estaVertice(u) || !estaVertice(v) || estaArista(tipo)) {
            return false;
        } else {
            Vertice iVertice = obtenerVertice(u);
            Vertice fVertice = obtenerVertice(v);
            Arista toAdd = new Arista(tipo, iVertice, fVertice, p, linea);
            gLados.add(toAdd);
            sideIDs.add(tipo);

            for (ALNode alNode : graph) {
                if (alNode.obtenerNombre().equals(u)) {
                    alNode.agregarVertice(fVertice);
                } else if (alNode.obtenerNombre().equals(u)) {
                    alNode.agregarVertice(iVertice);
                }
            }
            return true;
        }
    }

    /**
     * Chequea si una arista existe en el grafo.
     * 
     * @param u    Nombre del vertice 1 de la arista
     * @param v    Nombre del vertice 2 de la arista
     * @param tipo Identificador de la arista
     * @return true si existe la arista/false en caso contrario
     */
    public boolean estaArista(String u, String v, int tipo) {
        if (!estaVertice(u) || !estaVertice(v) || !estaArista(tipo)) {
            return false;
        } else {
            for (ALNode alNode : graph) {
                Vertice ver = alNode.obtenerVertice();
                if (ver.obtenerNombre() == u) {
                    LinkedList<Vertice> adjacents = alNode.obtenerAdyacencias();
                    for (Vertice adj : adjacents) {
                        if (adj.obtenerNombre() == v) {
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
     * @param id Identificador de la arista a buscar
     * @return true si la arista esta en el grafo/false en caso contrario
     */
    public boolean estaArista(int id) {
        return sideIDs.contains(id);
    }

    /**
     * Chequea si existe una arista en el grafo g que conecte dos vertices en
     * especifico.
     * 
     * @param id1 Vertice 1 del grafo
     * @param id2 Vertice 2 del grafo
     * @return true si existe la arista/false en otro caso
     */
    public boolean existeArista(int id1, int id2) {
        for (Lado lado : gLados) {
            Arista a = (Arista) lado;
            if (((a.obtenerVertice1()).obtenerID() == id1 && (a.obtenerVertice2()).obtenerID() == id2)
                    || ((a.obtenerVertice2()).obtenerID() == id1 && (a.obtenerVertice2()).obtenerID() == id2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene una arista en el grafo g con un identificador en especifico.
     * 
     * @param id Identificador de la arista a buscar
     * @return Arista buscada
     * @throws NoSuchElementException Si no existe la arista buscada
     */
    public Arista obtenerArista(int id) throws NoSuchElementException {
        if (!sideIDs.contains(id)) {
            throw new NoSuchElementException();
        } else {
            for (Lado lado : gLados) {
                if (lado.obtenerTipo() == id) {
                    return (Arista) lado;
                }
            }
            return null;
        }
    }

    /**
     * En el grafo g elimina una arista con un identificador en especifico.
     * 
     * @param id Identificador de la arista a eliminar
     * @return true si se elimino la arista/false en caso contrario
     */
    public boolean eliminarArista(int id) {
        if (!estaArista(id)) {
            return false;
        } else {
            Arista a = obtenerArista(id);

            Vertice v1 = a.obtenerVertice1();
            Vertice v2 = a.obtenerVertice2();
            int id1 = a.obtenerVertice1().obtenerID();
            int id2 = a.obtenerVertice2().obtenerID();

            sideIDs.remove(id);
            gLados.remove(a);

            // Hay que chequear si existe una arista entre ambos nodos primero.
            if (!existeArista(id1, id2)) {
                // Buscamos los nodos con esas ID y los borramos
                int c = 0;
                for (ALNode node : graph) {
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

    @Override
    public int grado(int id) throws NoSuchElementException {
        try {
            if (!estaVertice(id)) {
                throw new NoSuchElementException();
            } else {
                int c = 0;
                Vertice v = obtenerVertice(id);
                for (Lado lado : gLados) {
                    if (lado.incide(v))
                        c++;
                }
                return c;
            }
        } catch (NoSuchElementException e) {
            System.out.println("El nodo con identificador: " + id + " no pertenece al grafo!");
            return -1;
        }
    }

    @Override
    public LinkedList<Vertice> adyacentes(int id) throws NoSuchElementException {
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

    @Override
    public LinkedList<Lado> incidentes(int id) throws NoSuchElementException {
        if (!nodeIDs.contains(id)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<Lado> incidentes = new LinkedList<Lado>();
            for (Lado lado : gLados) {
                if ((((Arista) lado).obtenerVertice1()).obtenerID() == id
                        || (((Arista) lado).obtenerVertice2()).obtenerID() == id) {
                    incidentes.add(lado);
                }
            }
            return incidentes;
        }
    }

    @Override
    public Grafo clone() {
        GrafoNoDirigido xd = new GrafoNoDirigido();
        xd.graph = new LinkedList<ALNode>();
        for (ALNode node : graph) {
            ALNode clon = new ALNode(node);
            xd.graph.add(clon);
        }
        xd.nodeIDs = new HashSet<Integer>(nodeIDs);
        xd.nodeNames = new HashSet<String>(nodeNames);
        xd.sideIDs = new HashSet<Integer>(sideIDs);
        xd.gLados = new ArrayList<Lado>();
        xd.maxSpeed = this.maxSpeed;
        for (Lado lado : gLados) {
            Lado clon = new Arista((Arista) lado);
            xd.gLados.add(clon);
        }
        return xd;
    }

    @Override
    public String toString() {
        String aux = "vertices del grafo: \n";
        for (ALNode alNode : graph) {
            aux += Vertice.toString(alNode.obtenerVertice()) + "\n";
        }
        aux += "--------------------------------------------------------------\n";
        for (Lado lado : gLados) {
            aux += ((Arista) lado).toString(lado) + "\n\n";
        }
        aux += "--------------------------------------------------------------\n";
        aux += "IDS de vertices en el grafo: \n";
        aux += nodeIDs.toString() + "\n";
        aux += "Nombres de vertices en el grafo: \n";
        aux += nodeNames.toString() + "\n";
        aux += "IDS de aristas en el grafo: \n";
        aux += sideIDs.toString() + "\n";
        return aux;
    }

    public GrafoNoDirigido() {
        nodeIDs = new HashSet<Integer>();
        nodeNames = new HashSet<String>();
        sideIDs = new HashSet<Integer>();
        gLados = new ArrayList<Lado>();
        graph = new LinkedList<ALNode>();
        maxSpeed = 0.0;
    }
}