import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.IllegalArgumentException;

/** Class that receives a file with a graph formated in a certain way and
 *  tries to find a Hamiltonian path in it.
 */
public class Hamilton{

    /**Loads a line that contains an edge.
	 * 
	 * @param line  Line to load.
	 * @param grafo Graph where the edge is added. It is modified directly.
	 * @throws IllegalArgumentException If the line format is not correct.
	 */
    static void loadEdge(String line, Grafo grafo)
    throws IllegalArgumentException
    {	
        String[] vertices = line.split(" ");     
        if (vertices.length > 1){
            for (int i = 1; i < vertices.length; i++){
                grafo.AddEdge(Integer.parseInt(vertices[0]), Integer.parseInt(vertices[i]));
            }
        }                        
    }

    /**Loads a graph from a file and stores it in a adjacency list
	 * 
	 * @param  fileName Filename
	 * @return               File graph representation
	 * 
	 * @throws IOException              If there\'s any error within the file reading
	 * @throws IllegalArgumentException If the file format is not correct
	 */
    static Grafo loadGraph(String fileName)
		throws IOException
	{
		Grafo salida = new Grafo();
		int vertices; 

		BufferedReader Lector = new BufferedReader(new FileReader(fileName));
		
		String linea = Lector.readLine(); 
		vertices =  Integer.parseInt(linea);
		
        for (int i = 0; i < vertices; i++){
			salida.InsertNode(i);
		}

		linea = Lector.readLine(); 

		linea = Lector.readLine(); 
		do{
			loadEdge(linea, salida);  
			linea=Lector.readLine();
		}
		while(linea != null);
		
		return salida;
    }
    

    /** Loads a graph in a file and tries to find a Hamiltonian Path within it using
	 *  an algorithm selected by the user. The avaible algorithms are BFS and DFS.
	 * 
	 * @param args Array with the filename and the algorithm to be used
	 * 
	 * @throws IOException              If there\'s any error within the file reading
	 * @throws IllegalArgumentException If the file format is not correct
	 */
    public static void main(String[] args)
		throws IOException, IllegalArgumentException
	{
		if (args.length <= 0){
			throw new IllegalArgumentException("Uso: java Hamilton <nombreArchivo>");
		}

		Grafo g = loadGraph(args[0]);

		if (args[1].equals("BFS")){
			BFS bfs = new BFS(g);
			bfs.HamiltonianBFS();
        }else if (args[1].equals("DFS")){
			DFS dfs = new DFS(g); 
			dfs.FindHamiltonianPathInitial();
		} else{
			throw new IllegalArgumentException("Uso: java Hamilton <algoritmoDeBusqueda>");
		}
	}
}