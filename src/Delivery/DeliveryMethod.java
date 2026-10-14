package Delivery;

import Address.Address;
import Courier.Courier;

public interface DeliveryMethod {
    public double DeliveryTime(Address source, Address destination);
    public double DeliveryCost(Address source, Address destination, Courier courier);
}
