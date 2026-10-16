package OrderList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderList {
    List<OrderPosition> positions = new ArrayList<>();

    public void addPosition(OrderPosition position) {
        positions.add(position);
    }

    public boolean removePosition(int positionId) {
        return positions.removeIf(position -> position.getId() == positionId);
    }

    public List<OrderPosition> getPositions() {
        return Collections.unmodifiableList(positions);
    }

    public double getTotalCost() {
        int sum = 0;
        for (OrderPosition position : positions)
            sum += position.getRawCost();
        return sum / 100.0;
    }

    public boolean isEmpty() {
        return positions.isEmpty();
    }

    public int size() {
        return positions.size();
    }

    public String toString() {
        if (positions.isEmpty()) {
            return "  (позиции отсутствуют)";
        }

        StringBuilder result = new StringBuilder();
        for (OrderPosition position : positions) {
            result.append("  ").append(position).append("\n");
        }
        return result.toString();
    }
}
