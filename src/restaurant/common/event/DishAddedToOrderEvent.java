package restaurant.common.event;

import java.time.LocalDateTime;

public class DishAddedToOrderEvent extends Event {
    private final String orderId;
    private final String dishName;
    private final int quantity;

    public DishAddedToOrderEvent(String orderId, String dishName, int quantity, LocalDateTime timestamp) {
        super();
        this.orderId = orderId;
        this.dishName = dishName;
        this.quantity = quantity;
        // timestamp можно извлечь через getTimestamp()
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDishName() {
        return dishName;
    }

    public int getQuantity() {
        return quantity;
    }
}
