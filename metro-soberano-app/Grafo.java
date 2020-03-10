import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import Lados.Lado;
import Vertice.Vertice;

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

public interface Grafo {

    /**
     * Carga un grafo desde un archivo.
     * 
     * @param g       Grafo donde se almacenara la informacion
     * @param archivo Archivo a cargar
     * @return true si se logro cargar el archivo/false en caso contrario
     * @throws FileNotFoundException Si el archivo no existe
     */
    public boolean cargarGrafo(Grafo g, String archivo) throws FileNotFoundException;

    /**
     * Intenta agregar un vertice v al grafo g. Si un vertice con el mismo
     * identificador ya se encuentra en el grafo g, no hace nada.
     * 
     * @param g Grafo a agregar vertice.
     * @param v Vertice a agregar.
     * @return true si se agrego el vertice/false en caso contrario
     */
    public boolean agregarVertice(Grafo g, Vertice v);

    /**
     * Se crea un nuevo vertice v al grafo con distintos atributos deseados y se
     * intenta agregar en el grafo g. Si un vertice con el mismo identificador se
     * encuentra en el grafo g, no hace nada.
     * 
     * @param g    Grafo a agregar vertice.
     * @param id   ID del vertice a agregar.
     * @param name Nombre del vertice a agregar.
     * @param x    Coordenada X del vertice a agregar.
     * @param y    Coordenada Y del vertice a agregar.
     * @param w    Peso del vertice a agregar.
     * @return true si se agrego el vertice/false en caso contrario.
     */
    public boolean agregarVertice(Grafo g, int id, String name, double x, double y, double w);

    /**
     * Se elimina del grafo g un vertice con cierto identificador. Si no existe un
     * vertice con ese identiicador en el grafo g, no hace nada.
     * 
     * @param g  Grafo a eliminar vertice.
     * @param id ID del vertice a eliminar
     * @return Si se logro eliminar el vertice o no
     */
    public boolean eliminarVertice(Grafo g, int id);

    /**
     * Chequea si un vertice con cierto identificador pertenece al grafo g.
     * 
     * @param g  Grafo a buscar vertice.
     * @param id Identificador de vertice a buscar en g.
     * @return true si el vertice se encuentra en el grafo/false en caso contrario.
     */
    public boolean estaVertice(Grafo g, int id);

    /**
     * Busca un vertice con cierto identificador en el grafo g y lo retorna. Si el
     * vertice no se encuentra en el grao g, levanta una excepcion.
     * 
     * @param g  Grafo de donde obtener el vertice
     * @param id Identificador del vertice a buscar en el grafo
     * @return El vertice si lo encuentra en el grafo/null sino.
     * @throws NoSuchElementException Si el vertice de identificador id no se
     *                                encuentra en el grafo g.
     */
    public Vertice obtenerVertice(Grafo g, int id) throws NoSuchElementException;

    /**
     * Retorna los vertices de un grafo g como una lista enlazada que contiende
     * objetos de la clase <code>Vertice</code> que representan los vertices del
     * grafo g
     * 
     * @param g Grafo a retornar sus vertices
     * @return Lista enlanzada con vertices del grafo g
     */
    public LinkedList<Vertice> vertices(Grafo g);

    /**
     * Retorna una lista con los lados de un grafo.
     * 
     * @param g Grado a retornar lados.
     * @return ArrayList con los lados de un grafo g, representados con el objeto
     *         <code>Lado</code>.
     */
    public ArrayList<Lado> lados(Grafo g);

    /**
     * Calcula el numero de vertices de un grafo.
     * 
     * @param g Grafo a calcular el numero de vertices.
     * @return El numero de vertices del grafo.
     */
    public int numeroVertices(Grafo g);

    /**
     * Calcula el numero de lados de un grafo.
     * 
     * @param g Grafo a calcular el numero de lados.
     * @return El numero de lados del grafo.
     */
    public int numeroLados(Grafo g);

    /**
     * Calcula el grado de un vertice con cierto identificador en un grafo.
     * 
     * @param g  Grafo donde calcular el grado del vertice.
     * @param id Identificador del vertice a calcular el grado.
     * @return El grado del vertice en el grafo.
     * @throws NoSuchElementException Si el vertice a calcular el grado no se
     *                                encuentra en el grafo.
     */
    public int grado(Grafo g, int id) throws NoSuchElementException;

    /**
     * Busca los vertices adyacentes de un vertice con cierto identificador en un grafo
     * y los devuelve.
     * 
     * @param g Grafo a buscar adyacentes
     * @param id Identificador del vertice a calcular adyacencias
     * @return Lista enlazada con los vertices adyacentes del vertice
     * @throws NoSuchElementException Si el vertice no se encuentra en el grafo
     */
    public LinkedList<Vertice> adyacentes(Grafo g, int id) throws NoSuchElementException;

    /**
     * Busca los lados incidentes de un vertice con cierto identificador en un grafo
     * y los devuelve.
     * 
     * @param g  Grafo a buscar incidentes.
     * @param id Identificador del vertice a calcular incidentes.
     * @return Lados que incident en el vertice a buscar.
     * @throws NoSuchElementException Si el vertice no se encuentra en el grafo.
     */
    public LinkedList<Lado> incidentes(Grafo g, int id) throws NoSuchElementException;

    /**
     * Clona el grafo.
     * 
     * @param g Grafo a clonar
     * @return deepcopy del grafo g
     */
    public Grafo clone(Grafo g);

    public String toString(Grafo g);
}

/**
 * Nodo usado en la representacion de lista de adyacencia del grafo. Contiene toda la informacion de un
 * vertice en el grafo.
 */
class ALNode {

    /**
     * Vertice valor del vertice.
     */
    private Vertice vertex;
    /**
     * Adyancencias del vertice.
     */
    private LinkedList<Vertice> adyacencias;
    /**
     * Predecesores del vertice.
     */
    private LinkedList<Vertice> predecesores;

    /**
     * Obtiene el vertice del nodo.
     * 
     * @return Vertice asociado al nodo
     */
    public Vertice obtenerVertice() {
        return vertex;
    }

    /**
     * Obtiene la ID del vertice del nodo.
     * 
     * @return ID del vertice del nodo
     */
    public int obtenerID() {
        return Vertice.obtenerID(vertex);
    }

    /**
     * Obtiene el nombre del vertice del nodo.
     * 
     * @return Nombre del vertice del nodo
     */
    public String obtenerNombre() {
        return Vertice.obtenerNombre(vertex);
    }

    /**
     * Agrega un vertice a las adyacencias del vertice del nodo.
     * 
     * @param v Vertice a agregar
     */
    public void agregarVertice(Vertice v) {
        for (Vertice vertice : adyacencias) {
            if (Vertice.obtenerID(vertice) == Vertice.obtenerID(v)){
                return;
            }
        }
        adyacencias.add(v);
    }

    /**
     * Agrega un predecesor al vertice del nodo.
     * 
     * @param v Vertice a agregar
     */
    public void agregarPredecesor(Vertice v) {
        predecesores.add(v);
    }

    /**
     * Obtiene las adyacencias del vertice en el nodo.
     * 
     * @return Adyacencias del vertice en el nodo
     */
    public LinkedList<Vertice> obtenerAdyacencias() {
        return adyacencias;
    }

    /**
     * Obtiene los predecesores del vertice en el nodo.
     * 
     * @return Predecesores del vertice en el nodo
     */
    public LinkedList<Vertice> obtenerPredecesores() {
        return predecesores;
    }

    public ALNode(Vertice v) {
        this.adyacencias = new LinkedList<Vertice>();
        this.predecesores = new LinkedList<Vertice>();
        this.vertex = v;
    }

    public ALNode(ALNode alNode){
        this.vertex = new Vertice(alNode.obtenerVertice());
        this.adyacencias = new LinkedList<Vertice>();
        for (Vertice ady : alNode.adyacencias) {
            this.adyacencias.add(new Vertice(ady));
        }
        this.predecesores = new LinkedList<Vertice>();
        for (Vertice pred : alNode.predecesores) {
            this.predecesores.add(new Vertice(pred));
        }
    }
}