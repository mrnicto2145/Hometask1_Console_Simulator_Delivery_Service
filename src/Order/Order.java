package Order;

import java.util.Map;
import java.util.Set;

import Address.Address;
import Courier.Courier;
import Customer.Customer;
import Delivery.DeliveryMethod;
import OrderList.OrderList;
import OrderList.OrderPosition;

public class Order {
    private final int id;
    private final Customer customer;
    private final OrderList orderList;
    private final Address deliveryAddress;
    private DeliveryMethod deliveryMethod;
    private Courier courier;
    private OrderStatus status;

    public Order(int id, Customer customer, Address deliveryAddress) {
        this.id = id;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.orderList = new OrderList();
        this.status = OrderStatus.NEW;
    }

    public void addPosition(OrderPosition position) throws IllegalStateException{
        if (status != OrderStatus.NEW)
            throw new IllegalStateException("Позиции можно добавлять только до выбора способа доставки");
        orderList.addPosition(position);
    }

    public void selectDeliveryMethod(DeliveryMethod method) throws IllegalStateException{
        if (status != OrderStatus.NEW && status != OrderStatus.DELIVERY_METHOD_SELECTED)
            throw new IllegalStateException("Способ доставки нельзя изменить на текущем статусе: " + status);

        if (orderList.isEmpty())
            throw new IllegalStateException("Нельзя выбрать доставку для заказа без позиций");

        this.deliveryMethod = method;
        this.courier = null;

        if (method.requiresCourier()) {
            this.status = OrderStatus.DELIVERY_METHOD_SELECTED;
        } else {
            this.status = OrderStatus.READY_FOR_PICKUP;
        }
    }

    public void assignCourier(Courier courier) throws IllegalStateException{
        requireDeliveryMethod();
        if (!deliveryMethod.requiresCourier()) {
            throw new IllegalStateException("Для самовывоза курьер не назначается");
        }
        if (status != OrderStatus.DELIVERY_METHOD_SELECTED) {
            throw new IllegalStateException("Курьера можно назначить только после выбора способа доставки");
        }

        this.courier = courier;
        this.status = OrderStatus.COURIER_ASSIGNED;
    }

    public void changeStatus(OrderStatus newStatus) throws IllegalStateException{
        if (newStatus == status) {
            return;
        }

        Set<OrderStatus> allowed = OrderStatus.ALLOWED_TRANSITIONS.getOrDefault(status, Set.of());
        if (!allowed.contains(newStatus)) {
            throw new IllegalStateException("Недопустимый переход: " + status + " -> " + newStatus);
        }

        switch (newStatus) {
            case DELIVERY_METHOD_SELECTED -> requireDeliveryMethod();
            case COURIER_ASSIGNED -> requireCourier();
            case READY_FOR_PICKUP -> requirePickup();
        }

        this.status = newStatus;
    }

    public double getProductsTotal() {
        return orderList.getTotalCost();
    }

    public double getDeliveryCost(Address warehouse) {
        if (deliveryMethod == null)
            return 0.0;
        return deliveryMethod.calcDeliveryCost(warehouse, deliveryAddress, courier);
    }

    public double getDeliveryTime(Address warehouse) {
        if (deliveryMethod == null)
            return 0.0;
        return deliveryMethod.calcDeliveryTime(warehouse, deliveryAddress);
    }

    public double getTotalCost(Address warehouse) {
        return getProductsTotal() + getDeliveryCost(warehouse);
    }

    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public OrderList getOrderList() { return orderList; }
    public Address getDeliveryAddress() { return deliveryAddress; }
    public DeliveryMethod getDeliveryMethod() { return deliveryMethod; }
    public Courier getCourier() { return courier; }
    public OrderStatus getStatus() { return status; }

    public String getShortInfo() {
        return String.format(
                "Заказ #%d | клиент: %s | статус: %s | сумма товаров: %.2f руб.",
                id,
                customer.getName(),
                status,
                getProductsTotal()
        );
    }

    private void requireDeliveryMethod() {
        if (deliveryMethod == null) {
            throw new IllegalStateException("Сначала выберите способ доставки");
        }
    }

    private void requireCourier() {
        if (courier == null) {
            throw new IllegalStateException("Сначала назначьте курьера");
        }
    }

    private void requirePickup() {
        if (deliveryMethod == null || deliveryMethod.requiresCourier()) {
            throw new IllegalStateException("Готовность к самовывозу доступна только для самовывоза");
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Заказ #").append(id).append("\n");
        result.append("  Клиент: ").append(customer).append("\n");
        result.append("  Адрес доставки: ").append(deliveryAddress).append("\n");
        result.append("  Позиции:\n").append(orderList);
        result.append("  Стоимость товаров: ")
                .append(String.format("%.2f", getProductsTotal()))
                .append(" руб.\n");

        if (deliveryMethod != null) {
            result.append("  Способ доставки: ").append(deliveryMethod.getName()).append("\n");
            result.append("  Курьер: ")
                    .append(courier == null ? "не назначен" : courier)
                    .append("\n");
        } else {
            result.append("  Способ доставки: не выбран\n");
        }

        result.append("  Статус: ").append(status).append("\n");
        return result.toString();
    }
}
