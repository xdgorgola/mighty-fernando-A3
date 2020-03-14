import java.util.LinkedList;
import java.util.HashSet;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.IllegalArgumentException;


/* Autores:
 * 		Pedro Rodriguez 15-11264
 * 		Mariangela Rizzo 17-10538
 */

/**
 * Carga el grafo de un archivo y aplica DFS para hallar el orden topologico.
 */
public class Orden{

    /**
	 * Carga un grafo y lo almacena como lista de adyacencia.
	 * 
	 * @param fileName Nombre del archivo.
	 * @return Representación del grafo en el archivo.
	 * 
	 * @throws IOException              So hay un error leyendo el archivo.
	 */
    public Grafo cargarGrafo(String fileName) throws IOException{
        Grafo salida = new Grafo();

        BufferedReader Lector = new BufferedReader(new FileReader(fileName));

        String linea = Lector.readLine();
        String[] slinea = linea.split(" ");
        int j = 0;

        while (linea != null){
            salida.agregarVertice(j , slinea[0].split(":")[0]);
            
            for (int i = 1; i<slinea.length; i++){
                if (!salida.obtenerVerticesNomb().contains(slinea[i])){
                    j++;
                }
                salida.agregarVertice (j , slinea[i]);
                salida.agregarArco(salida.obtenerNodo(slinea[0].split(":")[0]).obtenerID(), salida.obtenerNodo(slinea[i]).obtenerID());
            }

            linea = Lector.readLine();
            if (linea!=null){
            slinea = linea.split(" ");
            if (!salida.obtenerVerticesNomb().contains(slinea[0].split(":")[0])){
                j++;
            }}
        } 
        
        Lector.close();
        return salida;	
    }

    public Orden(){}

    public static void main(String[] args) throws IOException, IllegalArgumentException{
        if (args.length <= 0) {
			throw new IllegalArgumentException("Uso: java Orden <nombreArchivo>");
        }
        
        Orden o = new Orden(); 
        Grafo g = o.cargarGrafo(args[0]);
        DFS dfs = new DFS(g);

        dfs.DFSR();
        dfs.PrintOrder();
    }
}