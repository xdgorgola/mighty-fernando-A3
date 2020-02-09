/** */
public class Vertice {
    private int id;
    private String name;
    
    private double xCord;
    private double yCord;

    private double weight;

    public static int getID(Vertice v){
        return v.id;
    }

    public static double getWeight(Vertice v){
        return v.weight;
    }

    public static String getName(Vertice v){
        return v.name;
    }

    public static double getX(Vertice v){
        return v.xCord;
    }

    public static double getY(Vertice v){
        return v.yCord;
    }

    public static String toString(Vertice v){
        return "id: " + v.id + " name: " + v.name + " xCord: " + v.xCord + 
            " yCord: " + v.yCord + " weight: " + v.weight;
    }

    public Vertice(int id, String name, double xCord, double yCord, double weight) {
        this.id = id;
        this.name = name;
        this.xCord = xCord;
        this.yCord = yCord;
        this.weight = weight;
    }
}