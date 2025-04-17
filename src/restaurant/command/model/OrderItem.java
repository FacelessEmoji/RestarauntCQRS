package restaurant.command.model;

/**
 * Элемент заказа – блюдо и его количество.
 */
public class OrderItem {
    private final String dishName; // геттер для dishName
    private int quantity; // геттер и сеттер для quantity

    public OrderItem(String dishName, int quantity) {
        this.dishName = dishName;
        this.quantity = quantity;
    }

    public String getDishName() {
        return dishName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return dishName + " x " + quantity;
    }
}
