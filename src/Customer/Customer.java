package Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Address.Address;

public class Customer {
    private final int id;
    private final String name;
    private final Address homeAddress;
    private final List<Integer> cards;

    public Customer(int id, String name, Address homeAddress) {
        this.id = id;
        this.name = name;
        this.homeAddress = homeAddress;
        this.cards = new ArrayList<>();
    }

    public void addCard(int card) {
        cards.add(card);
    }

    public List<Integer> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public String toString() {
        return String.format("Клиент #%d: %s, дом. адрес: %s", id, name, homeAddress);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Address getHomeAddress() { return homeAddress; }
}
