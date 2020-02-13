import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import Lados.Lado;
import Vertice.Vertice;

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
     * @param id
     * @return
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
     * objetos de la clase <code>Vertice</vertice> que representan los vertices del
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

    public Grafo clone(Grafo g);

    public String toString(Grafo g);
}

/**
 * AllNode
 */
class ALNode {

    private Vertice vertex;
    private LinkedList<Vertice> adyacencias;
    private LinkedList<Vertice> predecesores;

    public Vertice obtenerVertice() {
        return vertex;
    }

    public int obtenerID() {
        return Vertice.obtenerID(vertex);
    }

    public String obtenerNombre() {
        return Vertice.obtenerNombre(vertex);
    }

    public void agregarVertice(Vertice v) {
        for (Vertice vertice : adyacencias) {
            if (Vertice.obtenerID(vertice) == Vertice.obtenerID(v)){
                return;
            }
        }
        adyacencias.add(v);
    }

    public void agregarPredecesor(Vertice v) {
        predecesores.add(v);
    }

    public LinkedList<Vertice> obtenerAdyacencias() {
        return adyacencias;
    }

    public LinkedList<Vertice> obtenerPredecesores() {
        return predecesores;
    }

    public ALNode(Vertice v) {
        adyacencias = new LinkedList<Vertice>();
        predecesores = new LinkedList<Vertice>();
        this.vertex = v;
    }
}