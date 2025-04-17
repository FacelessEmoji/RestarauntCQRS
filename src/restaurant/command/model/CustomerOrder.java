package restaurant.command.model;

import restaurant.common.event.EventBus;
import restaurant.common.event.OrderCreatedEvent;
import restaurant.common.event.DishAddedToOrderEvent;
import restaurant.common.event.OrderUpdatedEvent;
import restaurant.common.event.OrderCompletedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Агрегат "Заказ клиента". Содержит список блюд, статус и информацию о клиенте.
 */
public class CustomerOrder {
    private final String id; // геттер для id
    private final String customerName; // геттер для customerName
    private final List<OrderItem> items; // геттер для items
    private OrderStatus status; // геттер для status
    private final LocalDateTime createdAt; // геттер для createdAt

    public CustomerOrder(String customerName) {
        this.id = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();

        // Публикация события создания заказа
        EventBus.getInstance().publish(new OrderCreatedEvent(this.id, this.customerName, this.createdAt));
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void addDish(String dishName, int quantity) {
        items.add(new OrderItem(dishName, quantity));
        // Публикация события добавления блюда в заказ
        EventBus.getInstance().publish(new DishAddedToOrderEvent(this.id, dishName, quantity, LocalDateTime.now()));
    }

    public void updateDishQuantity(String dishName, int newQuantity) {
        boolean found = false;
        for (OrderItem item : items) {
            if (item.getDishName().equals(dishName)) {
                item.setQuantity(newQuantity);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Блюдо " + dishName + " не найдено в заказе");
        }
        // Публикация события обновления заказа
        EventBus.getInstance().publish(new OrderUpdatedEvent(this.id, LocalDateTime.now()));
    }

    public void changeStatus(OrderStatus newStatus) {
        this.status = newStatus;
        // Публикация события обновления заказа
        EventBus.getInstance().publish(new OrderUpdatedEvent(this.id, LocalDateTime.now()));
    }

    public void completeOrder() {
        this.status = OrderStatus.COMPLETED;
        // Публикация события завершения заказа
        EventBus.getInstance().publish(new OrderCompletedEvent(this.id, LocalDateTime.now()));
    }
}
