package Order;

import java.util.Map;
import java.util.Set;

public enum OrderStatus {
    NEW("Новый"),
    DELIVERY_METHOD_SELECTED("Способ доставки выбран"),
    COURIER_ASSIGNED("Курьер назначен"),
    READY_FOR_PICKUP("Готов к самовывозу"),
    IN_TRANSIT("В пути"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменён");

    private final String title;
    public static final Map<OrderStatus, Set<OrderStatus>> ALLOWED_TRANSITIONS = Map.of(
    OrderStatus.NEW, Set.of(OrderStatus.DELIVERY_METHOD_SELECTED, OrderStatus.CANCELLED),
    OrderStatus.DELIVERY_METHOD_SELECTED, Set.of(
        OrderStatus.COURIER_ASSIGNED,
        OrderStatus.READY_FOR_PICKUP,
        OrderStatus.CANCELLED
    ),
    OrderStatus.COURIER_ASSIGNED, Set.of(OrderStatus.IN_TRANSIT, OrderStatus.CANCELLED),
    OrderStatus.READY_FOR_PICKUP, Set.of(OrderStatus.DELIVERED, OrderStatus.CANCELLED),
    OrderStatus.IN_TRANSIT, Set.of(OrderStatus.DELIVERED, OrderStatus.CANCELLED)
    );

    OrderStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
