package Delivery;

import Address.Address;
import Courier.Courier;

public class PickupDelivery implements DeliveryMethod {
    public String getName() {
        return "Самовывоз";
    }

    public boolean requiresCourier() {
        return false;
    }

    public double calcDeliveryTime(Address source, Address destination) {
        return 0.0;
    }

    public double calcDeliveryCost(Address source, Address destination, Courier courier) {
        return 0.0;
    }
}
