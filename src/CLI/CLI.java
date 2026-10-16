package CLI;

import Address.Address;
import Courier.Courier;
import Customer.Customer;
import Delivery.DeliveryMethod;
import Order.Order;
import Order.OrderService;
import Order.OrderStatus;
import OrderList.OrderPosition;

import java.util.Collection;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner = new Scanner(System.in);

    private CLI() {}

    private static class CliHolder {
        private static final CLI INSTANCE = new CLI();
    }

    public static CLI getInstance() {
        return CliHolder.INSTANCE;
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void run(OrderService service) {
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readInt("Выберите пункт: ");

            try {
                clear();
                switch (choice) {
                    case 1:
                        createOrder(service);
                        break;
                    case 2:
                        showOrders(service);
                        break;
                    case 3:
                        selectDelivery(service);
                        break;
                    case 4:
                        changeStatus(service);
                        break;
                    case 5:
                        running = false;
                        System.out.println("Работа программы завершена.");
                        break;
                    default:
                        System.out.println("Нет такого пункта меню.");
                        break;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nНажмите Enter для продолжения...");
                scanner.nextLine();
                clear();
            }
        }
    }

    private void printMainMenu() {
        clear();
        System.out.println("\n===== Служба доставки =====");
        System.out.println("1. Создать новый заказ");
        System.out.println("2. Показать список заказов");
        System.out.println("3. Выбрать способ доставки и назначить курьера");
        System.out.println("4. Изменить статус заказа");
        System.out.println("5. Выйти");
    }

    private void createOrder(OrderService service) {
        System.out.println("\n--- Новый заказ ---");

        String customerName = readNonEmptyString("Имя клиента: ");
        Address homeAddress = readAddress("Домашний адрес клиента");
        Customer customer = service.createCustomer(customerName, homeAddress);

        Address deliveryAddress = readAddress("Адрес доставки");
        Order order = service.createOrder(customer, deliveryAddress);

        int positionsCount = readInt("Сколько позиций добавить? ");
        for (int i = 0; i < positionsCount; i++) {
            System.out.println("Позиция " + (i + 1));

            String name = readNonEmptyString("Название товара: ");
            double cost = readDouble("Цена за единицу: ");
            int count = readInt("Количество: ");

            OrderPosition position = service.createPosition(name, cost, count);
            order.addPosition(position);
        }

        System.out.println("Заказ #" + order.getId() + " создан.");
    }

    private void showOrders(OrderService service) {
        System.out.println("\n--- Список заказов ---");

        Collection<Order> orders = service.getOrders();
        if (orders.isEmpty()) {
            System.out.println("Заказов пока нет.");
            return;
        }

        for (Order order : orders) {
            System.out.println(order.getShortInfo());
        }

        int orderId = readInt("Введите номер заказа для подробностей (0 - назад): ");
        if (orderId != 0) {
            System.out.println(service.findOrder(orderId));
        }
    }

    private void selectDelivery(OrderService service) {
        int orderId = readInt("Номер заказа: ");
        Order order = service.findOrder(orderId);

        System.out.println("Способы доставки:");
        System.out.println("1. Стандартная доставка курьером");
        System.out.println("2. Экспресс-доставка");
        System.out.println("3. Самовывоз");

        int methodOption = readInt("Выберите способ: ");
        service.selectDeliveryMethod(orderId, methodOption);

        if (methodOption == 1 || methodOption == 2)
            assignCourier(service, orderId);

        DeliveryMethod method = order.getDeliveryMethod();
        double cost = order.getDeliveryCost(service.getWarehouse());
        double time = order.getDeliveryTime(service.getWarehouse());

        System.out.printf("Выбран способ: %s%n", method.getName());
        System.out.printf("Стоимость доставки: %.2f руб.%n", cost);
        System.out.printf("Ориентировочный срок: %.2f ч.%n", time);
    }

    private void assignCourier(OrderService service, int orderId) {
        System.out.println("Доступные курьеры:");
        for (Courier courier : service.getCouriers())
            System.out.println(courier);

        int courierId = readInt("Введите ID курьера: ");
        service.assignCourier(orderId, courierId);
        System.out.println("Курьер назначен.");
    }

    private void changeStatus(OrderService service) {
        int orderId = readInt("Номер заказа: ");
        Order order = service.findOrder(orderId);

        System.out.println("Текущий статус: " + order.getStatus());
        System.out.println("Доступные статусы:");

        OrderStatus[] statuses = OrderStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println(i + " - " + statuses[i]);
        }

        int statusIndex = readInt("Выберите новый статус: ");
        if (statusIndex < 0 || statusIndex >= statuses.length) {
            throw new IllegalArgumentException("Неверный номер статуса");
        }

        service.changeStatus(orderId, statuses[statusIndex]);
        System.out.println("Статус изменён. Текущая информация:");
        System.out.println(order);
    }

    private Address readAddress(String title) {
        System.out.println(title + ":");
        double x = readDouble("  X: ");
        double y = readDouble("  Y: ");
        double regionFactor = readDouble("  Коэффициент региона: ");
        return new Address(x, y, regionFactor);
    }

    private String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Значение не может быть пустым.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Введите число.");
            }
        }
    }
}
