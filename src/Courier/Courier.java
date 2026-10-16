package Courier;


public class Courier{
    private final int id;
    private final String name;
    private final double costByDistance;

    public Courier(int id, String name, double costByDistance) {
        this.id = id;
        this.name = name;
        this.costByDistance = costByDistance;
    }

    public String toString() {
        return String.format("Курьер #%d: %s (%.2f руб/ед. расстояния)", id, name, costByDistance);
    }

    public int getId() { return id; }
    public String getName() { return name;}
    public double getCostByDistance() { return costByDistance;}
}
