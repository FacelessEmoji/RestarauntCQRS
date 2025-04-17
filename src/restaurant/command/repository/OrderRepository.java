package restaurant.command.repository;

import restaurant.command.model.CustomerOrder;
import java.util.HashMap;
import java.util.Map;

/**
 * Простейшая in-memory реализация репозитория заказов.
 */
public class OrderRepository {
    private final Map<String, CustomerOrder> orders = new HashMap<>();

    public void save(CustomerOrder order) {
        orders.put(order.getId(), order);
    }

    public CustomerOrder findById(String orderId) {
        CustomerOrder order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Заказ не найден: " + orderId);
        }
        return order;
    }
}
