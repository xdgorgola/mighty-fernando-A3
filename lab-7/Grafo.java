import java.util.LinkedList;
import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;

/* Autores:
 * 		Pedro Rodriguez 15-11264
 * 		Mariangela Rizzo 17-10538
 */

/**
 * Representacion de un gafo como lista de adyacencia
 */
public class Grafo {

	private LinkedList<ALNode> grafo;
	private LinkedList<Integer[]> arcos;
	private HashSet<Integer> verticesID;
	private HashSet<String> verticesNames;

	/**
	 * Obtiene la representacion del grafo como lista de adyacencias.
	 * 
	 * @return Representacion del grafo como lista de adyacencia
	 */
	public LinkedList<ALNode> obtenerGrafo() {
		return grafo;
	}

	/**
	 * Obtiene los arcos del grafo.
	 * 
	 * @return Arcos del grafo
	 */
	public LinkedList<Integer[]> obtenerArcos() {
		return arcos;
	}

	/**
	 * Obtiene las ID de los vertices del grafo.
	 * 
	 * @return ID de los vertices del grafo
	 */
	public HashSet<Integer> obtenerVerticesID() {
		return verticesID;
	}

	/**
	 * Obtiene las ID de los vertices del grafo.
	 * 
	 * @return ID de los vertices del grafo
	 */
	public HashSet<String> obtenerVerticesNomb() {
		return verticesNames;
	}

	/**
	 * Obtiene un nodo del grafo basado en su ID.
	 * 
	 * @param id ID del nodo a buscar
	 * @return El vertice asociado a la ID si esta en el grafo/null en otro caso
	 */
	public ALNode obtenerNodo(int id) {
		for (ALNode alnode : grafo) {
			if (alnode.obtenerID() == id) {
				return alnode;
			}
		}
		return null;
	}

	/**
	 * Obtiene un nodo del grafo basado en su Nombre.
	 * 
	 * @param name Nombre del nodo a buscar
	 * @return El vertice asociado al nombre si esta en el grafo/null en otro caso
	 */
	public ALNode obtenerNodo(String name) {
		for (ALNode alnode : grafo) {
			if (alnode.obtenerNombre().equals(name)) {
				return alnode;
			}
		}
		return null;
	}

	/**
	 * Agrega un vertice al grafo.
	 * 
	 * @param id   ID del vertice a agregar
	 * @param name Nombre del vertice a agregar
	 */
	public void agregarVertice(int id, String name) {
		if (verticesID.contains(id) || verticesNames.contains(name)) {
			return;
		}

		ALNode nuevo = new ALNode(id, name);
		grafo.add(nuevo);
		verticesID.add(id);
		verticesNames.add(name);
	}

	/**
	 * Agrega una arista al grafo.
	 * 
	 * @param vi Vertice 1 de la arista
	 * @param vf Vertice 2 de la arista
	 */
	public void agregarArco(int vi, int vf) {
		for (ALNode alnode : grafo) {
			if (alnode.obtenerID() == (vi)) {
				alnode.agregarSucesor(vf);
			} else if (alnode.obtenerID() == (vf)) {
				alnode.agregarPredecesor(vi);
			}
		}
		arcos.add(new Integer[] { vi, vf });
	}

	public Grafo() {
		this.grafo = new LinkedList<ALNode>();
		this.arcos = new LinkedList<Integer[]>();
		this.verticesID = new HashSet<Integer>();
		this.verticesNames = new HashSet<String>();
	}
}

// Lista de adyacencia
class ALNode {
	private int id;
	private String name;
	private int order;
	private boolean visited;
	private LinkedList<Integer> sucesores;
	private LinkedList<Integer> predecesores;

	/**
	 * Obtiene la ID del vertice.
	 * 
	 * @return ID del vertice
	 */
	public int obtenerID() {
		return this.id;
	}

	/**
	 * Obtiene el nombre del vertice.
	 * 
	 * @return Nombre del vertice
	 */
	public String obtenerNombre() {
		return name;
	}

	/**
	 * Obtiene el orden del vertice.
	 * 
	 * @return Orden del vertice
	 */
	public int obtenerOrden() {
		return order;
	}

	/**
	 * Obtiene si el vertice fue visitado o no.
	 * 
	 * @return true si visitado, false en otro caso.
	 */
	public boolean estaVisitado() {
		return visited;
	}

	/**
	 * Marca el vertice como
	 */
	public void marcarVisitado() {
		visited = true;
	}

	/**
	 * Asigna un valor ordinal al vertice.
	 * 
	 * @param order el valor que se asigna.
	 */
	public void asignarOrden(int order) {
		this.order = order;
	}

	/**
	 * Obtiene los sucesores del vertice
	 * 
	 * @return Vertices sucesores
	 */
	public LinkedList<Integer> obtenerSucesores() {
		return sucesores;
	}

	/**
	 * Obtiene los predecesores del vertice
	 * 
	 * @return Vertices predecesores
	 */
	public LinkedList<Integer> obtenerPredecesores() {
		return predecesores;
	}

	/**
	 * Agrega un vertice predecesor a este vertice.
	 * 
	 * @param aAgregar Vertice predecesor a agregar
	 */
	public void agregarPredecesor(int aAgregar) {
		for (Integer pred : predecesores) {
			if (pred == aAgregar) {
				return;
			}
		}
		predecesores.add(aAgregar);
	}

	/**
	 * Agrega un vertice sucesor a este vertice.
	 * 
	 * @param aAgregar Vertice sucesor a agregar
	 */
	public void agregarSucesor(int aAgregar) {
		for (Integer suce : sucesores) {
			if (suce == aAgregar) {
				return;
			}
		}
		sucesores.add(aAgregar);
	}

	public ALNode(int id, String name) {
		this.id = id;
		this.name = name;
		this.order = -1;
		this.visited = false;
		this.predecesores = new LinkedList<Integer>();
		this.sucesores = new LinkedList<Integer>();
	}
}
