/** */
public class Vertice {
    private int id;
    private String name;
    
    private double xCord;
    private double yCord;

    private double weight;

    public int getID(){
        return id;
    }

    public double getWeight(){
        return weight;
    }

    public String getName(){
        return name;
    }

    public double getX(){
        return xCord;
    }

    public double getY(){
        return yCord;
    }

    public String toString(){
        return "id: " + id + " name: " + name + " xCord: " + xCord + 
            " yCord: " + yCord + " weight: " + weight;
    }

    public Vertice(int id, String name, double xCord, double yCord, double weight) {
        this.id = id;
        this.name = name;
        this.xCord = xCord;
        this.yCord = yCord;
        this.weight = weight;
    }
}