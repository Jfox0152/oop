import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;

interface QueueBehaviour {
    void takeInQueue(Customer customer);
    void releaseFromQueue(Customer customer);
    Customer getNextInQueue();
}

interface MarketBehaviour {
    void acceptOrder(Order order);
    Order giveOrder(Customer customer);
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Order {
    private Customer customer;
    private String item;

    public Order(Customer customer, String item) {
        this.customer = customer;
        this.item = item;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getItem() {
        return item;
    }
}

public class Market implements QueueBehaviour, MarketBehaviour {
    private Queue<Customer> customerQueue;
    private Map<Customer, Order> orders;

    public Market() {
        this.customerQueue = new LinkedList<>();
        this.orders = new HashMap<>();
    }

    @Override
    public void takeInQueue(Customer customer) {
        customerQueue.offer(customer);
        System.out.println(customer.getName() + " вошел в очередь.");
    }

    @Override
    public void releaseFromQueue(Customer customer) {
        if (customerQueue.remove(customer)) {
            System.out.println(customer.getName() + " покинул очередь.");
        } else {
            System.out.println(customer.getName() + " не был в очереди.");
        }
    }

    @Override
    public Customer getNextInQueue() {
        return customerQueue.peek();
    }

    @Override
    public void acceptOrder(Order order) {
        orders.put(order.getCustomer(), order);
        System.out.println("Заказ принят: " + order.getItem() + " для " + order.getCustomer().getName());
    }

    @Override
    public Order giveOrder(Customer customer) {
        return orders.remove(customer);
    }

    public void update() {
        while (!customerQueue.isEmpty()) {
            Customer customer = customerQueue.poll();
            Order order = giveOrder(customer);
            if (order != null) {
                System.out.println(customer.getName() + " получил свой заказ: " + order.getItem());
            } else {
                System.out.println(customer.getName() + " не имеет заказа.");
            }
        }
    }

    public static void main(String[] args) {
        Market market = new Market();
        Customer customer1 = new Customer("John");
        Customer customer2 = new Customer("Jane");
        Customer customer3 = new Customer("Jack");
        market.takeInQueue(customer1);
        market.takeInQueue(customer2);
        market.takeInQueue(customer3);
        Order order1 = new Order(customer1, "Apple");
        Order order2 = new Order(customer2, "Banana");
        market.acceptOrder(order1);
        market.acceptOrder(order2);
        market.update();
    }
}
