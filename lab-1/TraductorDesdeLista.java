import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.ListIterator;

/**Almacena un grafo que puede crecer din&aacute;micamente para prop&oacute;sitos
 * de traduci&oacute;n a Matriz de Adyacencias. Esta clase est&aacute; pensada para ser
 * usada al leer grafos en formato Lista de Adyacencias desde un archivo.
 */
public class TraductorDesdeLista extends TraductorGrafo{
	
	/** Representacion del grafo en lista de adyacencia
	 */
	private LinkedList<LinkedList<Integer>> auxGrafo;

	/**Crea un grafo minimal*/
	TraductorDesdeLista(){
		auxGrafo = new LinkedList<LinkedList<Integer>>();
	}
	
	/**Agrega un v&eacute;rtice al grafo. Si el v&eacute;rtice ya existe, el m&eacute;todo no hace
	 * nada.
	 * 
	 * @param id El n&uacute;mero del v&eacute;rtice que se desea agregar
	 */
	public void agregarVertice(int id){
		// Primero busca si el vertice ya existe
		ListIterator<LinkedList<Integer>> iterator = auxGrafo.listIterator();
		LinkedList<Integer> act;
		while (iterator.hasNext()){
			act = iterator.next();
			// Si existe, chao cacerola
			if (act.getFirst() == id) return;
		}
		// Sino, bienvenido a la familia
		LinkedList<Integer> toAdd = new LinkedList<Integer>();
		toAdd.add(id);
		auxGrafo.add(toAdd);
	}
	
	/**{@inheritDoc}**/
	public void agregarArco(int verticeInicial, int verticeFinal){
		// Se itera primero todos los vertices
		ListIterator<LinkedList<Integer>> iterator = auxGrafo.listIterator();
		LinkedList<Integer> act;
		while (iterator.hasNext()){
			act = iterator.next();
			// Si el vertice es el inicial, entonces revisa si el arco ya existe
			if (act.getFirst() == verticeInicial){
				ListIterator<Integer> internalIterator = act.listIterator();
				Integer act2 = internalIterator.next();
				while (internalIterator.hasNext()){
					act2 = internalIterator.next();
					if (act2 == verticeFinal) return;
				}
				// Si no existe, lo agrega!
				act.add(verticeFinal);
			}
		}
	}
	
	/**{@inheritDoc}**/
	public String imprimirGrafoTraducido(){
		String traduccion = "   ";
		int nroNodos = 0;
		ListIterator<LinkedList<Integer>> iterator = auxGrafo.listIterator();	
		LinkedList<Integer> act;
		// Primero contamos la cantidad de nodos en el grafo interno
		while (iterator.hasNext()){
			act = iterator.next();
			traduccion += nroNodos + " ";
			nroNodos += 1;
		}	
		// Creamos la rayita de separacion linda
		traduccion += "\n--";
		for (int i = 0; i < nroNodos; i++){
			traduccion += "--";
		}
		traduccion += "\n";
		// Creamos la representacion del grafo como matriz
		crearMatrizInterna(nroNodos);

		for (int i = 0; i < nroNodos; i++){
			traduccion += i + " | ";
			for (int j = 0; j < nroNodos; j++){
				traduccion += grafo[i][j] + " ";
			}
			traduccion += "\n";
		}
		return traduccion;
	}

	/** Crea a partir de la representaci&oacute;n como lista enlazada del grafo interno,
	 *  la matriz de adyac&eacute;ncia que representa la lista. La matriz interna de la
	 *  clase es modificada directamente.
	 * 
	 * @param numeroNodos N&uacute;mero de v&eacute;rtices del grafo
	 */
	public void crearMatrizInterna(int numeroNodos) {
		grafo = new int[numeroNodos][numeroNodos];

		ListIterator<LinkedList<Integer>> iterator = auxGrafo.listIterator();
		LinkedList<Integer> act;
		// Para cada vertice de la lista de adyacencia, agrega en la matriz de adyacencia interna
		// sus adyacencias
		while (iterator.hasNext()){
			act = iterator.next();
			ListIterator<Integer> internalIterator = act.listIterator();
			Integer initial = internalIterator.next();
			Integer act2;
			// Agrega las adyacencias del vertice en la matriz
			while (internalIterator.hasNext()){
				act2 = internalIterator.next();
				grafo[initial][act2] = 1;
			}
		}		
	}
}