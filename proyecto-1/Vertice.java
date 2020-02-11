/** */
public class Vertice {
    private int id;
    private String nombre;
    
    private double xCord;
    private double yCord;

    private double peso;

    public static int obtenerID(Vertice v){
        return v.id;
    }

    public static double obtenerPeso(Vertice v){
        return v.peso;
    }

    public static String obtenerNombre(Vertice v){
        return v.nombre;
    }

    public static double obtenerX(Vertice v){
        return v.xCord;
    }

    public static double obtenerY(Vertice v){
        return v.yCord;
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
}