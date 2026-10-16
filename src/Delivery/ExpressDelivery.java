package Delivery;

import Address.Address;
import Courier.Courier;

public class ExpressDelivery implements DeliveryMethod {
    private static final double BASE_COST = 500.0;
    private static final double SPEED = 40.0;
    private static final double PROCESSING_TIME = 0.5;

    @Override
    public String getName() {
        return "Экспресс-доставка";
    }

    @Override
    public boolean requiresCourier() {
        return true;
    }

    @Override
    public double calcDeliveryTime(Address source, Address destination) {
        return PROCESSING_TIME + source.distance(destination) / SPEED;
    }

    @Override
    public double calcDeliveryCost(Address source, Address destination, Courier courier) {
        if (courier == null) {
            throw new IllegalArgumentException("Для экспресс-доставки нужен курьер");
        }
        return BASE_COST + source.distance(destination) * courier.getCostByDistance() * 1.5;
    }
}