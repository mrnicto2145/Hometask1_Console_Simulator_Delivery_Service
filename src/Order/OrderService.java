package Order;

import Address.Address;
import Courier.Courier;
import Customer.Customer;
import Delivery.DeliveryMethod;
import Delivery.ExpressDelivery;
import Delivery.PickupDelivery;
import Delivery.StandardDelivery;
import OrderList.OrderPosition;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderService {
    private final Map<Integer, Order> orders = new LinkedHashMap<>();
    private final Map<Integer, Customer> customers = new LinkedHashMap<>();
    private final Map<Integer, Courier> couriers = new LinkedHashMap<>();
    private final Address warehouse;

    private int nextOrderId = 1;
    private int nextCustomerId = 1;
    private int nextPositionId = 1;

    public OrderService() {
        this.warehouse = new Address(0, 0, 0);

        couriers.put(1, new Courier(1, "Иван Петров", 15.0));
        couriers.put(2, new Courier(2, "Сергей Смирнов", 12.0));
        couriers.put(3, new Courier(3, "Анна Иванова", 18.0));
    }

    public Customer createCustomer(String name, Address homeAddress) {
        Customer customer = new Customer(nextCustomerId++, name, homeAddress);
        customers.put(customer.getId(), customer);
        return customer;
    }

    public Order createOrder(Customer customer, Address deliveryAddress) {
        Order order = new Order(nextOrderId++, customer, deliveryAddress);
        orders.put(order.getId(), order);
        return order;
    }

    public OrderPosition createPosition(String name, double cost, int count) {
        return new OrderPosition(nextPositionId++, name, (int)(cost*100), count);
    }

    public Collection<Order> getOrders() {
        return Collections.unmodifiableCollection(orders.values());
    }

    public Order findOrder(int id) {
        Order order = orders.get(id);
        if (order == null) {
            throw new IllegalArgumentException("Заказ #" + id + " не найден");
        }
        return order;
    }

    public Collection<Courier> getCouriers() {
        return Collections.unmodifiableCollection(couriers.values());
    }

    public Courier findCourier(int id) {
        Courier courier = couriers.get(id);
        if (courier == null) {
            throw new IllegalArgumentException("Курьер #" + id + " не найден");
        }
        return courier;
    }

    public Address getWarehouse() {
        return warehouse;
    }

    public DeliveryMethod createDeliveryMethod(int option) {
        if (option == 1) {
            return new StandardDelivery();
        }
        if (option == 2) {
            return new ExpressDelivery();
        }
        if (option == 3) {
            return new PickupDelivery();
        }
        throw new IllegalArgumentException("Неверный способ доставки: " + option);
    }

    public void selectDeliveryMethod(int orderId, int methodOption) {
        Order order = findOrder(orderId);
        DeliveryMethod method = createDeliveryMethod(methodOption);
        order.selectDeliveryMethod(method);
    }

    public void assignCourier(int orderId, int courierId) {
        Order order = findOrder(orderId);
        Courier courier = findCourier(courierId);
        order.assignCourier(courier);
    }

    public void changeStatus(int orderId, OrderStatus status) {
        findOrder(orderId).changeStatus(status);
    }
}
