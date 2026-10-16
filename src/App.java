import CLI.CLI;
import Order.OrderService;

public class App {
    public static void main(String[] args) {
        OrderService service = new OrderService();
        CLI cli = CLI.getInstance();
        cli.run(service);
    }
}
