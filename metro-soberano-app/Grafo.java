import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import Lados.Arco;
import Lados.Arista;
import Lados.Lado;
import Vertice.Vertice;

// AUTORES: Mariangela Rizzo 17-10538 Pedro Rodriguez 15-11264

public interface Grafo {

    /**
     * Carga un grafo desde un archivo.
     * 
     * @param archivo Archivo a cargar
     * @return true si se logro cargar el archivo/false en caso contrario
     * @throws FileNotFoundException Si el archivo no existe
     */
    public boolean cargarGrafo(String archivo) throws FileNotFoundException;

    /**
     * Intenta agregar un vertice v al grafo g. Si un vertice con el mismo
     * identificador ya se encuentra en el grafo g, no hace nada.
     * 
     * @param v Vertice a agregar.
     * @return true si se agrego el vertice/false en caso contrario
     */
    public boolean agregarVertice(Vertice v);

    /**
     * Se crea un nuevo vertice v al grafo con distintos atributos deseados y se
     * intenta agregar en el grafo g. Si un vertice con el mismo identificador se
     * encuentra en el grafo g, no hace nada.
     * 
     * @param id   ID del vertice a agregar.
     * @param name Nombre del vertice a agregar.
     * @param x    Coordenada X del vertice a agregar.
     * @param y    Coordenada Y del vertice a agregar.
     * @param w    Peso del vertice a agregar.
     * @return true si se agrego el vertice/false en caso contrario.
     */
    public boolean agregarVertice(int id, String name, double x, double y, double w);

    /**
     * Se elimina del grafo g un vertice con cierto identificador. Si no existe un
     * vertice con ese identiicador en el grafo g, no hace nada.
     * 
     * @param id ID del vertice a eliminar
     * @return Si se logro eliminar el vertice o no
     */
    public boolean eliminarVertice(int id);

    /**
     * Chequea si un vertice con cierto identificador pertenece al grafo g.
     * 
     * @param id Identificador de vertice a buscar en g.
     * @return true si el vertice se encuentra en el grafo/false en caso contrario.
     */
    public boolean estaVertice(int id);

    /**
     * Busca un vertice con cierto identificador en el grafo g y lo retorna. Si el
     * vertice no se encuentra en el grao g, levanta una excepcion.
     * 
     * @param id Identificador del vertice a buscar en el grafo
     * @return El vertice si lo encuentra en el grafo/null sino.
     * @throws NoSuchElementException Si el vertice de identificador id no se
     *                                encuentra en el grafo g.
     */
    public Vertice obtenerVertice(int id) throws NoSuchElementException;

    /**
     * Retorna los vertices de un grafo g como una lista enlazada que contiende
     * objetos de la clase <code>Vertice</code> que representan los vertices del
     * grafo g
     * 
     * @return Lista enlanzada con vertices del grafo g
     */
    public LinkedList<Vertice> vertices();

    /**
     * Retorna una lista con los lados de un grafo.
     * 
     * @return ArrayList con los lados de un grafo g, representados con el objeto
     *         <code>Lado</code>.
     */
    public ArrayList<Lado> lados();

    /**
     * Calcula el numero de vertices de un grafo.
     * 
     * @return El numero de vertices del grafo.
     */
    public int numeroVertices();

    /**
     * Calcula el numero de lados de un grafo.
     * 
     * @return El numero de lados del grafo.
     */
    public int numeroLados();

    /**
     * Calcula el grado de un vertice con cierto identificador en un grafo.
     * 
     * @param id Identificador del vertice a calcular el grado.
     * @return El grado del vertice en el grafo.
     * @throws NoSuchElementException Si el vertice a calcular el grado no se
     *                                encuentra en el grafo.
     */
    public int grado(int id) throws NoSuchElementException;

    /**
     * Busca los vertices adyacentes de un vertice con cierto identificador en un grafo
     * y los devuelve.
     * 
     * @param id Identificador del vertice a calcular adyacencias
     * @return Lista enlazada con los vertices adyacentes del vertice
     * @throws NoSuchElementException Si el vertice no se encuentra en el grafo
     */
    public LinkedList<Vertice> adyacentes(int id) throws NoSuchElementException;

    /**
     * Busca los lados incidentes de un vertice con cierto identificador en un grafo
     * y los devuelve.
     * 
     * @param id Identificador del vertice a calcular incidentes.
     * @return Lados que incident en el vertice a buscar.
     * @throws NoSuchElementException Si el vertice no se encuentra en el grafo.
     */
    public LinkedList<Lado> incidentes(int id) throws NoSuchElementException;

    /**
     * Clona el grafo.
     * 
     * @return deepcopy del grafo g
     */
    public Grafo clone();

    public String toString();
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
        return vertex.obtenerID();
    }

    /**
     * Obtiene el nombre del vertice del nodo.
     * 
     * @return Nombre del vertice del nodo
     */
    public String obtenerNombre() {
        return vertex.obtenerNombre();
    }

    /**
     * Agrega un vertice a las adyacencias del vertice del nodo.
     * 
     * @param v Vertice a agregar
     */
    public void agregarVertice(Vertice v) {
        for (Vertice vertice : adyacencias) {
            if (vertice.obtenerID() == v.obtenerID()){
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


/**
 * Clase que se usa para representar un camino/cadena en un grafo.
 * Mantiene dos listas, una de lados y vertices que se han tomado en orden.
 */
class NodePath{
    /**
     * Lados tomados en el camino (en orden)
     */
    private LinkedList<Lado> path;
    /**
     * Vertices tomados en el camino (en orden)
     */
    private LinkedList<Vertice> pathV;
    /**
     * Costo transbordos (cuantos transbordos se hacen en el camino)
     */
    private double costTrasbordos;

    /**
     * Obtiene el camino de lados.
     * 
     * @return Camino de lados
     */
    public LinkedList<Lado> getPathE(){
        return path;
    }

    /**
     * Extiende el camino con un arco.
     * 
     * @param toAdd Arco para extender
     */
    public void extendPathArco(Arco toAdd){
        path.add(toAdd);
        pathV.add(toAdd.obtenerVerticeFinal());
        calculateTrasbordosCost();
    }

    /**
     * Extiende el camino con una arista.
     * 
     * @param toAdd Arista para extender
     */
    public void extendPathArista(Arista toAdd){
        path.add(toAdd);
        if (getLastVert().obtenerID() == toAdd.obtenerVertice1().obtenerID()){
            pathV.add(toAdd.obtenerVertice2());
        }
        else{
            pathV.add(toAdd.obtenerVertice1());
        }
        calculateTrasbordosCost();
    }

    /**
     * Obtiene el ultimo lado del camino.
     * 
     * @return Ultimo lado del camino
     */
    public Lado getLastEdge(){
        return path.getLast();
    }

    /**
     * Obtiene el primer lado del camino.
     * 
     * @return Primer lado del camino
     */
    public Lado getFirstEdge(){
        return path.getFirst();
    }

    /**
     * Obtiene el ultimo vertice del camino.
     * 
     * @return Ultimo vertice del camino
     */
    public Vertice getLastVert(){
        return pathV.getLast();
    }

    /**
     * Obtiene el primer vertice del camino.
     * 
     * @return Primer vertice del camino
     */
    public Vertice getFirstVert(){
        return pathV.getFirst();
    }

    /**
     * Calcula cuantos transbordos se realizan en el camino
     */
    public void calculateTrasbordosCost(){
        //cost = getFirstVert().obtenerPeso();
        costTrasbordos = 0;
        if (path.size() != 0){
            String currentLine = getFirstEdge().obtenerLinea();
            for (int i = 0; i < path.size(); i++){
                // Si hay cambio de linea, aumenta en transbordo
                if (!path.get(i).obtenerLinea().equals(currentLine)){
                    currentLine = path.get(i).obtenerLinea();
                    costTrasbordos += 1;
                    //System.out.println(pathV.get(i).obtenerPeso());
                    //cost += pathV.get(i).obtenerPeso();
                }
            }
        }
    }

    /**
     * Obtiene el numero de transbordos realizados en el camino
     * 
     * @return Numero de transbordos en el camino
     */
    public double getCostTrasbordos(){
        return costTrasbordos;
    }

    /**
     * Chequea si un lado ya se tomo en el camino antes del ultimo
     * 
     * @param toCheck Camino a chequear
     * @return Si un lado ya se tomo en el camino antes del ultimo
     */
    public boolean isBeforeLast(Lado toCheck){
        for (Lado lado : path) {
            if (lado.obtenerID() == getLastEdge().obtenerID());
            else if (lado.obtenerID() == toCheck.obtenerID()) return true;
        }
        return false;
    }

    public String toString(){
        String aux ="";
        if (path.size() != 0){
            String currentLine = getFirstEdge().obtenerLinea();
            aux += "Toma la linea " + currentLine + " desde " + getFirstVert().obtenerID() + " " + getFirstVert().obtenerNombre() +
                " hasta ";
            for (int i = 0; i < path.size(); i++){
                // Si hay cambio de linea, aumenta en transbordo
                if (!path.get(i).obtenerLinea().equals(currentLine)){
                    currentLine = path.get(i).obtenerLinea();
                    Vertice ult = pathV.get(i);
                    aux += ult.obtenerID() + " " + ult.obtenerNombre() + "\n";
                    aux += "Toma la linea " + currentLine + " desde " + ult.obtenerID() + " " + 
                        ult.obtenerNombre() + " hasta ";
                }
            }
            aux += getLastVert().obtenerID() + " " + getLastVert().obtenerNombre();
        }
        return aux;
    }

    /**
     * Constructor basico
     */
    public NodePath(){
        path = new LinkedList<Lado>();
        pathV = new LinkedList<Vertice>();
        costTrasbordos = Double.POSITIVE_INFINITY;
    }

    /**
     * Constructor que clona otro camino.
     * 
     * @param toClone Camino a clonar
     */
    public NodePath(NodePath toClone){
        path = new LinkedList<Lado>(toClone.path);
        pathV = new LinkedList<Vertice>(toClone.pathV);
        calculateTrasbordosCost();
    } 

    /**
     * Constructor con vertice inicial.
     * 
     * @param init Vertice con el que se inicia el camino
     */
    public NodePath(Vertice init){
        path = new LinkedList<Lado>();
        pathV = new LinkedList<Vertice>();
        pathV.add(init); 
        calculateTrasbordosCost();
    }
}