import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;


public class Grafo{
	private LinkedList<ALNode> grafo;
	private LinkedList<Integer[]> lados;

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


	public LinkedList<ALNode> obtenerGrafo(){
		return grafo;
	}


	public LinkedList<Integer> obtenerVertices(){
		LinkedList<Integer> jeje = new LinkedList<Integer>();
		for (ALNode v : grafo){
			jeje.add(v.obtenerID());
		}
		return jeje;
	}

	public LinkedList<Integer[]> obtenerLados(){
		return lados;
	}


	public ALNode obtenerNodo(int id){
		for (ALNode alnode : grafo){
			if (alnode.obtenerID() == id){
				return alnode;
			}
		}
		return null;
	}


// IMPORTANTE: ESTO ES MAGNITUD AL CUADRADO, NO CALCULA LA RAIZ ASI QUE SI QUIERES
// COMPARAR CON UNA VALOR, TIENES QUE ELEVAR EL VALOR AL CUADRADO. SOLO COMPARA CUAL ES
// MAYOR MAS NO EL VERDADERO VALOR
// mag = sqrt(x*x + y*y)^2 asi que  mag^2 = x*x + y*y
	public double obtenerCostoLadoSQR(int v1, int v2){
		ALNode n1 = obtenerNodo(v1);
		ALNode n2 = obtenerNodo(v2);
		double[] dir = new double[2];
		dir[0] = n2.obtenerPosicion()[0] - n1.obtenerPosicion()[0];
		dir[1] = n2.obtenerPosicion()[1] - n1.obtenerPosicion()[1];
		return dir[0] * dir [0] + dir [1] * dir[1];
	}

	public double obtenerCostoLado(int v1, int v2){
		ALNode n1 = obtenerNodo(v1);
		ALNode n2 = obtenerNodo(v2);
		double[] dir = new double[2];
		dir[0] = n2.obtenerPosicion()[0] - n1.obtenerPosicion()[0];
		dir[1] = n2.obtenerPosicion()[1] - n1.obtenerPosicion()[1];
		return Math.sqrt(dir[0] * dir [0] + dir [1] * dir[1]);		
	}

	public void agregarVertice(int aAgregar, double[] pos){
		ALNode nuevo = new ALNode(aAgregar, pos);
		grafo.add(nuevo);
	}


	public void eliminarVertice(int aEliminar){
		ListIterator<ALNode> iterator = grafo.listIterator();
		while (iterator.hasNext()){
			ALNode act = iterator.next();
			if (act.obtenerID() == aEliminar){
				iterator.remove();
			}
			else if (act.obtenerAdyacencias().contains(aEliminar)){
				act.obtenerAdyacencias().remove(aEliminar);
			}
		}
	}


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


	public Grafo clonar(){
		Grafo clon = new Grafo();
		LinkedList<ALNode> nuevoGrafo = new LinkedList<ALNode>();
		for (ALNode node : this.grafo){
			nuevoGrafo.add(new ALNode(node));
		}
		clon.grafo = nuevoGrafo;
		return clon;
	}


	public Grafo(){
		this.grafo = new LinkedList<ALNode>();
		this.lados = new LinkedList<Integer[]>();
	}


	public static void main(String[] args) throws FileNotFoundException{
		Grafo g = new Grafo();
		g.cargarGrafo("EjemploC.txt");
		for (double f : (g.obtenerNodo(3).obtenerPosicion())){
			System.out.println(f);
		}
		System.out.println(g.obtenerCostoLadoSQR(0, 1));
	}
}

// Lista de lados
class ALNode{
	private int id;
	private double[] pos;
	private LinkedList<Integer> adyacencias;


	public int obtenerID(){
		return this.id;
	}

	public double[] obtenerPosicion(){
		return pos;
	}


	public LinkedList<Integer> obtenerAdyacencias(){
		return adyacencias;
	}


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

	public ALNode(ALNode toClone){
		this.id = toClone.id;
		this.pos = new double[2];
		System.arraycopy(toClone.pos, 0, this.pos, 0, 2);
		this.adyacencias = new LinkedList<Integer>(toClone.adyacencias);
	}
}
