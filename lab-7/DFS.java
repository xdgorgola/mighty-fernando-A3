import java.util.LinkedList;
import java.util.HashSet;

/* Autores:
 * 		Pedro Rodriguez 15-11264
 * 		Mariangela Rizzo 17-10538
 */

/**
 * DFS con la tecnica de Bellman para grafos sin ciclos.
 */
public class DFS {
    private Grafo grafo;
    private int contador;
    private LinkedList<ALNode> ord;

    public DFS(Grafo grafo) {
        this.grafo = grafo;
        this.contador = grafo.obtenerGrafo().size();
        this.ord = new LinkedList<ALNode>();
    }

    /**
	 * Aplica DFSRecursivo a todas las componentes conexas de un grafo.
	 */
    public void DFSR() {
        for (int i = 0; i < grafo.obtenerGrafo().size(); i++) {
            if (!grafo.obtenerNodo(i).estaVisitado()) {
                DFSRecursivo(i);
            }
        }
    }

    /**
	 * Aplica la estrategia de DFS para recorrer el grafo pero, una vez que no encuentra sucesores de un nodo,
     * empieza a numerarlos tras disminuir el contador. 
	 */
    public void DFSRecursivo(int v) {
        grafo.obtenerNodo(v).marcarVisitado();
        for (int u : grafo.obtenerNodo(v).obtenerSucesores()) {
            if (!grafo.obtenerNodo(u).estaVisitado()) {
                DFSRecursivo(u);
            }
        }
        contador--;
        grafo.obtenerNodo(v).asignarOrden(contador);
        ord.add(grafo.obtenerNodo(v));
    }

    /**
	 * Obtiene los nodos almacenados desde el de mayor orden hasta el de menor orden
	 * 
	 * @return  La lista con los nodos.
	 */
    public LinkedList<ALNode> obtenerOrden() {
        return ord;
    }

    /**
	 * Imprime el nombre de los nodos, desde el de mayor orden al de menor orden. Separados por un espacio.
	 */
    public void PrintOrder() {
        String s = "";
        for (ALNode node : ord) {
            s += node.obtenerNombre() + " ";
        }
        System.out.println(s);
    }
}