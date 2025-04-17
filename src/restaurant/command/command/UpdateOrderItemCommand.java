package restaurant.command.command;

import java.util.UUID;

/**
 * Команда для изменения количества (или состава) блюда в заказе.
 */
public class UpdateOrderItemCommand implements Command {
    private final String commandId;
    private final String orderId;     // идентификатор заказа
    private final String dishName;    // название блюда, которое требуется обновить
    private final int newQuantity;    // новое количество блюда

    public UpdateOrderItemCommand(String orderId, String dishName, int newQuantity) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.dishName = dishName;
        this.newQuantity = newQuantity;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDishName() {
        return dishName;
    }

    public int getNewQuantity() {
        return newQuantity;
    }
}
