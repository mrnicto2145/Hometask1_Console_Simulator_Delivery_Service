package Delivery;

import Address.Address;
import Courier.Courier;

public interface DeliveryMethod {

    public double calcDeliveryTime(Address source, Address destination);

    public double calcDeliveryCost(Address source, Address destination, Courier courier);

    public String getName();
    public boolean requiresCourier();
}
