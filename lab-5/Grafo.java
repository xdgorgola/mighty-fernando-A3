import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;

/**
 * Representacion de un gafo como lista de adyacencia
 */
public class Grafo{

	private LinkedList<ALNode> grafo;
	private LinkedList<Integer[]> lados;


	/**
	 * Carga un grafo desde un archivo con un formato en especifico.
	 * 
	 * @param path Path del archivo con el grafo
	 * @throws FileNotFoundException Si el archivo no existe
	 */
	public void cargarGrafo(String path) throws FileNotFoundException{
		Scanner scan = new Scanner(new File(path));
		int nroVerts = Integer.parseInt(scan.nextLine().trim());
		for (int i = 0; i < nroVerts; i++){
			String[] toAdd = (scan.nextLine().trim()).split("\\s+");
			double[] pos = new double[]{Double.parseDouble(toAdd[0]), Double.parseDouble(toAdd[1])};
			agregarVertice(i, pos);
		}
		scan.nextLine();
		int nroArist = Integer.parseInt(scan.nextLine().trim());
		for (int i = 0; i < nroArist; i++){
			String texto = scan.nextLine().trim();
			String[] linea = texto.split("\\s+");
			agregarArista(Integer.parseInt(linea[0]), Integer.parseInt(linea[1]));
		}
	}


	/**
	 * Obtiene la representacion del grafo como lista de adyacencias.
	 * 
	 * @return Representacion del grafo como lista de adyacencia
	 */
	public LinkedList<ALNode> obtenerGrafo(){
		return grafo;
	}


	/**
	 * Obtiene los lados del grafo.
	 * 
	 * @return Lados del grafo
	 */
	public LinkedList<Integer[]> obtenerLados(){
		return lados;
	}


	/**
	 * Obtiene un nodo del grafo basado en su ID.
	 * 
	 * @param id ID del nodo a buscar
	 * @return El vertice asociado a la ID si esta en el grafo/null en otro caso
	 */
	public ALNode obtenerNodo(int id){
		for (ALNode alnode : grafo){
			if (alnode.obtenerID() == id){
				return alnode;
			}
		}
		return null;
	}


	/**
	 * Calcula el costo de una arista del grafo.
	 * <b>COSTO: Distancia euclidiana entre dos vertices.</b>
	 * <b>IMPORTANTE: No toma en cuenta si la arista pertenece o no al grafo.</b>
	 * 
	 * @param v1 Vertice 1 del arista
	 * @param v2 Vertice 2 del arista
	 * @return Costo de la arista
	 */
	public double obtenerCostoLado(int v1, int v2){
		ALNode n1 = obtenerNodo(v1);
		ALNode n2 = obtenerNodo(v2);
		double[] dir = new double[2];
		dir[0] = n2.obtenerPosicion()[0] - n1.obtenerPosicion()[0];
		dir[1] = n2.obtenerPosicion()[1] - n1.obtenerPosicion()[1];
		return Math.sqrt(dir[0] * dir [0] + dir [1] * dir[1]);		
	}


	/**
	 * Agrega un vertice al grafo.
	 * 
	 * @param aAgregar ID del vertice a agregar
	 * @param pos Posicion del vertice a agregar
	 */
	public void agregarVertice(int aAgregar, double[] pos){
		ALNode nuevo = new ALNode(aAgregar, pos);
		grafo.add(nuevo);
	}


	/**
	 * Agrega una arista al grafo.
	 * 
	 * @param vi Vertice 1 de la arista
	 * @param vf Vertice 2 de la arista
	 */
	public void agregarArista(int vi, int vf){
		for (ALNode alnode : grafo){
			if (alnode.obtenerID() == (vi)){
				alnode.agregarAdyacencia(vf);
			}
			else if (alnode.obtenerID() == (vf)){
				alnode.agregarAdyacencia(vi);
			}
		}
		lados.add(new Integer[]{vi, vf});
		lados.add(new Integer[]{vf, vi});
	}


	public Grafo(){
		this.grafo = new LinkedList<ALNode>();
		this.lados = new LinkedList<Integer[]>();
	}
}

// Lista de adyacencia
class ALNode{
	private int id;
	private double[] pos;
	private LinkedList<Integer> adyacencias;


	/**
	 * Obtiene la ID del vertice.
	 * 
	 * @return ID del vertice
	 */
	public int obtenerID(){
		return this.id;
	}


	/**
	 * Obtiene la posicion del vertice.
	 * 
	 * @return Posicion del vertice
	 */
	public double[] obtenerPosicion(){
		return pos;
	}


	/**
	 * Obtiene las adyacencias del vertice
	 * 
	 * @return Vertices adyacentes
	 */
	public LinkedList<Integer> obtenerAdyacencias(){
		return adyacencias;
	}


	/**
	 * Agrega un vertice adyacente a este vertice.
	 * 
	 * @param aAgregar Vertice adyacente a agregar
	 */
	public void agregarAdyacencia(int aAgregar){
		for (Integer ady : adyacencias){
			if (ady.equals(aAgregar)){
				return;
			}
		}
		adyacencias.add(aAgregar);
	}


	public ALNode(int id, double[] pos){
		this.id = id;
		this.pos = pos;
		this.adyacencias = new LinkedList<Integer>();
	}
}
