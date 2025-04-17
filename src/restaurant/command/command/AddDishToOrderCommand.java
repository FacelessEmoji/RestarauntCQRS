package restaurant.command.command;

import java.util.UUID;

/**
 * Команда для добавления блюда в заказ.
 */
public class AddDishToOrderCommand implements Command {
    private final String commandId;
    private final String orderId;    // идентификатор заказа
    private final String dishName;   // название блюда
    private final int quantity;      // количество блюда

    public AddDishToOrderCommand(String orderId, String dishName, int quantity) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.dishName = dishName;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }
}
