package restaurant.query.repository;

import restaurant.query.model.OrderView;

import java.util.*;

public class OrderViewRepository {
    private final Map<String, OrderView> orders = new HashMap<>();

    public void save(OrderView orderView) {
        orders.put(orderView.getId(), orderView);
    }

    public OrderView findById(String id) {
        OrderView ov = orders.get(id);
        if (ov == null) {
            throw new IllegalArgumentException("Заказ не найден: " + id);
        }
        return ov;
    }

    public List<OrderView> findAll() {
        return new ArrayList<>(orders.values());
    }
}
