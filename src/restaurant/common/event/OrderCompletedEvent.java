package restaurant.common.event;

import java.time.LocalDateTime;

public class OrderCompletedEvent extends Event {
    private final String orderId;
    private final LocalDateTime completedAt;

    public OrderCompletedEvent(String orderId, LocalDateTime completedAt) {
        super();
        this.orderId = orderId;
        this.completedAt = completedAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
