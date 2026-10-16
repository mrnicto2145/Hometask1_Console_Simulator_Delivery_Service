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

    OrderStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
