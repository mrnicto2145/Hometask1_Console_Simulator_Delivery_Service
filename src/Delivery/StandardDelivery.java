package Delivery;

import Address.Address;
import Courier.Courier;

public class StandardDelivery implements DeliveryMethod {
    private static final double BASE_COST = 200.0;
    private static final double SPEED = 20.0;
    private static final double PROCESSING_TIME = 2.0;

    public String getName() {
        return "Стандартная доставка курьером";
    }

    public boolean requiresCourier() {
        return true;
    }

    public double calcDeliveryTime(Address source, Address destination) {
        return PROCESSING_TIME + source.distance(destination) / SPEED;
    }

    public double calcDeliveryCost(Address source, Address destination, Courier courier) {
        if (courier == null) {
            throw new IllegalArgumentException("Для стандартной доставки нужен курьер");
        }
        return BASE_COST + source.distance(destination) * courier.getCostByDistance();
    }
}