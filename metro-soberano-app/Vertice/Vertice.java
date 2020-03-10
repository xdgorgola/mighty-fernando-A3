package Vertice;

/** */
public class Vertice {
    private int id;
    private String nombre;
    private String linea;
    
    private double xCord;
    private double yCord;

    private double peso;

    public int obtenerID(){
        return this.id;
    }

    public double obtenerPeso(){
        return this.peso;
    }

    public String obtenerNombre(){
        return this.nombre;
    }

    public double obtenerX(){
        return this.xCord;
    }

    public double obtenerY(){
        return this.yCord;
    }

    public static String toString(Vertice v){
        return "id: " + v.id + " name: " + v.nombre + " xCord: " + v.xCord + 
            " yCord: " + v.yCord + " weight: " + v.peso;
    }

    public Vertice(int id, String nombre, double xCord, double yCord, double peso) {
        this.id = id;
        this.nombre = nombre;
        this.xCord = xCord;
        this.yCord = yCord;
        this.peso = peso;
    }

    public Vertice(Vertice v){
        this.id = v.obtenerID();
        this.peso = v.obtenerPeso();
        this.nombre = v.obtenerNombre();
        this.xCord = v.obtenerX();
        this.yCord = v.obtenerY();
    }
}