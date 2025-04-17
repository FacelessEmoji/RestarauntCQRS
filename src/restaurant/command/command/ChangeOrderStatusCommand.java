package restaurant.command.command;

import restaurant.command.model.OrderStatus;
import java.util.UUID;

/**
 * Команда для изменения статуса заказа.
 */
public class ChangeOrderStatusCommand implements Command {
    private final String commandId;
    private final String orderId;       // идентификатор заказа
    private final OrderStatus newStatus; // новый статус заказа

    public ChangeOrderStatusCommand(String orderId, OrderStatus newStatus) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.newStatus = newStatus;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderStatus getNewStatus() {
        return newStatus;
    }
}
