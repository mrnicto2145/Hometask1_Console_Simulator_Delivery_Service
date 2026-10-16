package OrderList;

public class OrderPosition {
    private final int id, cost, count;
    private final String name;

    public OrderPosition(int id, String name, int cost, int count) {
        if (count <= 0)
            throw new IllegalArgumentException("Количество должно быть положительным");
        if (cost < 0)
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.count = count;
    }

    public double getTotalCost() {
        return (cost * count) / 100.0;
    }

    public int getRawCost() {
        return cost * count;
    }

    public String toString() {
        return String.format("%s x%d = %.2f руб.", name, count, getTotalCost());
    }

    public int getId() { return id; }
}
