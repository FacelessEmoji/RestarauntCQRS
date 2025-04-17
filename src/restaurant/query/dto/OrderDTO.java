package restaurant.query.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private final String id;           // геттер для id
    private final String customerName; // геттер для customerName
    private final String status;       // геттер для status
    private final LocalDateTime createdAt; // геттер для createdAt
    private final List<String> items;  // список блюд (например, "Бургер x2")

    public OrderDTO(String id, String customerName, String status, LocalDateTime createdAt, List<String> items) {
        this.id = id;
        this.customerName = customerName;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<String> getItems() {
        return items;
    }
}
