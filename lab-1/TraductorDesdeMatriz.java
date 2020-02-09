/**Almacena un grafo que puede crecer din&aacute;micamente para prop&oacute;sitos
 * de traduci&oacute;n a Matriz de Adyacencias. Esta clase est&aacute; pensada para ser
 * usada al leer grafos en formato Lista de Adyacencias desde un archivo.
 */
public class TraductorDesdeMatriz extends TraductorGrafo{
	
	//ToDo: Debe colocar aqu&iacute; estructuras de java.util.collections apropiadas
	private int[][] AdjacencyMatrix;
	/**Crea un grafo con el n&uacute;mero de v&eacute;rtices dado
	 * 
	 * @param vertices El n&uacute;mero de v&eacute;rtices del grafo
	 */
	TraductorDesdeMatriz(int vertices){
		AdjacencyMatrix = new int[vertices][vertices];
	}
	
	/**{@inheritDoc}**/
	public void agregarArco(int verticeInicial, int verticeFinal){
		AdjacencyMatrix[verticeInicial][verticeFinal] = 1;
	}
	
	/**{@inheritDoc}**/
	public String imprimirGrafoTraducido(){
		StringBuilder s= new StringBuilder();
        for (int i = 0; i<AdjacencyMatrix.length; i++){
            s.append(i + ": ");
            for (int j = 0; j<AdjacencyMatrix.length; j++){
                if (AdjacencyMatrix[i][j] == 1){
                    s.append(j + " ");
                }
                
            }
            s.append("\n");
        } 
        return s.toString();
	}
}