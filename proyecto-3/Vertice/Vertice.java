package Vertice;

/**
 * Vertice de un grafo
 */
public class Vertice {

    private int id;

    private String nombre;

    private double xCord;
    private double yCord;

    private double peso;

    /**
     * Obtiene la ID del vertice.
     * 
     * @return retorna la ID del vertice
     */
    public int obtenerID() {
        return this.id;
    }

    /**
     * Obtiene el peso del vertice.
     * 
     * @return retorna el peso del vertice
     */
    public double obtenerPeso() {
        return this.peso;
    }

    /**
     * Obtiene el nombre del vertice.
     * 
     * @return retorna el nombre del vertice
     */
    public String obtenerNombre() {
        return this.nombre;
    }

    /**
     * Obtiene la posicion X del vertice.
     * 
     * @return retorna la posicion X del vertice
     */
    public double obtenerX() {
        return this.xCord;
    }

    /**
     * Obtiene la posicion Y del vertice.
     * 
     * @return retorna la posicion Y del vertice
     */
    public double obtenerY() {
        return this.yCord;
    }

    public static String toString(Vertice v) {
        return "id: " + v.id + " name: " + v.nombre + " xCord: " + v.xCord + " yCord: " + v.yCord + " weight: "
                + v.peso;
    }

    public Vertice(int id, String nombre, double xCord, double yCord, double peso) {
        this.id = id;
        this.nombre = nombre;
        this.xCord = xCord;
        this.yCord = yCord;
        this.peso = peso;
    }

    public Vertice(Vertice v) {
        this.id = v.obtenerID();
        this.peso = v.obtenerPeso();
        this.nombre = v.obtenerNombre();
        this.xCord = v.obtenerX();
        this.yCord = v.obtenerY();
    }
}