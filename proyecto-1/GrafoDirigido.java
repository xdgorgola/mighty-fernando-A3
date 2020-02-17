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
        int n = 0;
        int m = 0;
        Scanner scan = new Scanner(new File(file));
        String line = scan.nextLine();
        GrafoDirigido gd = (GrafoDirigido) g;
        if (line.equals("D")) {

            gd.gLados.clear();
            gd.sideIDs.clear();
            gd.nodeIDs.clear();
            gd.nodeNames.clear();
            gd.graph.clear();
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
            if (!gd.agregarVertice(g, toAdd)) {
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
            Vertice vi = gd.obtenerVertice(g, results[0]);
            Vertice vf = gd.obtenerVertice(g, results[1]);
            Arco toAdd = new Arco(Integer.parseInt(results[2]), vi, vf, Double.parseDouble(results[3]));
            if (!gd.agregarArco(g, toAdd)) {
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

    /**
     * Revisa si un vertice con cierto nombre pertenece al grafo g.
     * 
     * @param g Grafo en el que se quiere ubicar el vertice.
     * @param nombre Nombre de vertice a buscar en el grafo.
     * @return true si el vertice esta en el grafo; false en de no ser asi.
     */
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
    /**
     * Busca un vertice con cierto nombre en el grafo g y lo retorna. Si el vertice
     * no se encuentra en el grafo g, levanta una excepcion.
     * 
     * @param g Grafo de donde obtener el vertice.
     * @param nombre Nombre del vertice a buscar en el grafo.
     * @return El vertice si lo encuentra en el grafo/null si no lo hace.
     * @throws NoSuchElementException Si el vertice de identificador id no se
     *                                encuentra en el grafo g.
     */
    public Vertice obtenerVertice(Grafo g, String nombre) throws NoSuchElementException {
        GrafoDirigido gd = (GrafoDirigido) g;
        if (!gd.estaVertice(g, nombre)) {
            throw new NoSuchElementException();
        } else {
            LinkedList<ALNode> graph = gd.graph;
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
        LinkedList<Vertice> vertices = new LinkedList<Vertice>();
        for (ALNode alNode : ((GrafoDirigido) g).graph) {
            vertices.add(alNode.obtenerVertice());
        }
        return vertices;
    }
    /**
     * Intenta agregar un arco al grafo y retorna true si lo logra. Si el arco ya existe o algun vertice
     * no existe en el grafo, retorna falso
     * 
     * @param g Grafo a agregar arco.
     * @param a Arco a agregar.
     * @return true si la arco se agrego; false en otro caso
     */
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
                if (alnode.obtenerID()== Vertice.obtenerID(vf) && !alnode.obtenerPredecesores().contains(vf)) {
                    alnode.agregarPredecesor(vi);
                }else if (alnode.obtenerID()== Vertice.obtenerID(vi) && !alnode.obtenerAdyacencias().contains(vf)){
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
     * @param g Grafo a agregar arista.
     * @param u    Nombre del vertice inicial del arco.
     * @param v    Nombre del vertice final del arco.
     * @param tipo Identificador del arco.
     * @param p    Peso del arco.
     * @return true si la arista se agrego/false en otro caso
     */
    public boolean agregarArco(Grafo g, String u, String v, int tipo, double p) {
        GrafoDirigido gd = (GrafoDirigido) g;
        if (estaArco(g, u, v, tipo) || !gd.estaVertice(g, u) || !gd.estaVertice(g, v) || estaArco(g, tipo) ) {
            return false;
        } else {
            Vertice vi = gd.obtenerVertice(g, u);
            Vertice vf = gd.obtenerVertice(g, v);
            Arco toAdd = new Arco(tipo, vi, vf, p);
            gd.gLados.add(toAdd);
            gd.sideIDs.add(tipo);

            for (ALNode alNode : gd.graph) {
                if (alNode.obtenerNombre().equals(u)){
                    alNode.agregarVertice(vf);
                }
                else if (alNode.obtenerNombre().equals(v)){
                    alNode.agregarPredecesor(vi);
                }
            }
        return true;
        }
    }
    /**
     * Chequea si un arco existe en el grafo.
     * 
     * @param g    Grafo a chequear si el arco existe
     * @param u    Nombre del vertice inicial del arco.
     * @param v    Nombre del vertice final del arco.
     * @param tipo Identificador del arco.
     * @return true si existe el arco/false en caso contrario
     */
    public boolean estaArco(Grafo g, String u, String v, int tipo) {
        GrafoDirigido gd = (GrafoDirigido) g;
        if (!gd.estaVertice(g, u) || !gd.estaVertice(g, v) || !gd.estaArco(g, tipo)) {
            return false;
        } else {
            for (ALNode alNode : gd.graph) {
                Vertice ver = alNode.obtenerVertice();
                if (Vertice.obtenerNombre(ver).equals(u)) {
                    LinkedList<Vertice> sucesors = alNode.obtenerAdyacencias();
                    for (Vertice suce : sucesors) {
                        if (Vertice.obtenerNombre(suce).equals(v)) {
                            return true;
                        }
                    }
                } else if (Vertice.obtenerNombre(ver).equals(v)) {
                    LinkedList<Vertice> predecesors = alNode.obtenerPredecesores();
                    for (Vertice pred : predecesors) {
                        if (Vertice.obtenerNombre(pred).equals(v)) {
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
    public boolean estaArco(Grafo g, int tipo) {
        return ((GrafoDirigido) g).sideIDs.contains(tipo);
    }
    /**
     * Intenta eliminar un arco en el grafo g.
     * 
     * @param g    Grafo en el que se intenta eliminar el arco.
     * @param u    Nombre del vertice inicial del arco.
     * @param v    Nombre del vertice final del arco.
     * @param tipo Identificador del arco.
     * @return true si se elimina el arco/false en caso contrario
     */
    public boolean eliminarArco(Grafo g, String u, String v, int tipo){
        GrafoDirigido gd = (GrafoDirigido) g;
        if (!gd.estaArco(g, u, v, tipo)) {
            return false;
        } else {
            Arco arco = gd.obtenerArco(g, tipo);
            gd.sideIDs.remove(tipo);
            gd.gLados.remove(arco);

            Vertice idu = gd.obtenerVertice(g, u);
            Vertice idv = gd.obtenerVertice(g, v);

            for (ALNode node : gd.graph){

                if (node.obtenerPredecesores().contains(idu)){
                    node.obtenerPredecesores().remove(idu);
                }

                if (node.obtenerAdyacencias().contains(idv)){
                    node.obtenerAdyacencias().remove(idv);
                }
            }
            return true;
        }
    }

    /**
     * Obtiene un arco en el grafo g con un identificador en especifico.
     * 
     * @param g Grafo a buscar arco.
     * @param tipo Identificador del arco a buscar
     * @return Arco buscado
     * @throws NoSuchElementException Si no existe el arco buscado
     */
    public Arco obtenerArco(Grafo g , int tipo){
        if (!(((GrafoDirigido) g).estaArco(g, tipo))) {
            throw new NoSuchElementException();
        } else {
            ArrayList<Lado> glados = ((GrafoDirigido) g).gLados;
            for (Lado lado : glados) {
                if (Lado.obtenerTipo(lado) == tipo) {
                    return (Arco) lado;
                }
            }
        }
        return null;
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

    /**
     * 
     * Devuelve el grado interno de un nodo en especifico.
     * 
     * @param g Grafo que contiene al nodo.
     * @param id identificador del nodo.
     * @return grado interno del nodo con identificador id.
     * @throws NoSuchElementException si el nodo no esta en el grafo especificado.
     */
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

    /**
     * 
     * Devuelve el grado externo de un nodo en especifico.
     * 
     * @param g Grafo que contiene al nodo.
     * @param id identificador del nodo.
     * @return grado externo del nodo con identificador id.
     * @throws NoSuchElementException si el nodo no esta en el grafo especificado.
     */
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

    /**
     * 
     * Devuelve todos los sucesores de un nodo en especifico.
     * 
     * @param g Grafo que contiene al nodo.
     * @param id identificador del nodo.
     * @return lista con los nodos sucesores.
     * @throws NoSuchElementException si el nodo no esta en el grafo especificado.
     */
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
    /**
     * 
     * Devuelve todos los predecesores de un nodo en especifico.
     * 
     * @param g Grafo que contiene al nodo.
     * @param id identificador del nodo.
     * @return lista con los nodos predecesores.
     * @throws NoSuchElementException si el nodo no esta en el grafo especificado.
     */
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

    public GrafoDirigido(){
        nodeIDs = new HashSet<Integer>();
        nodeNames = new HashSet<String>();
        sideIDs = new HashSet<Integer>();
        gLados = new ArrayList<Lado>();
        graph = new LinkedList<ALNode>();
    }

}
