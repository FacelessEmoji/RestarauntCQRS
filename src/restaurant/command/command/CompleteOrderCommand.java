package restaurant.command.command;

import java.util.UUID;

/**
 * Команда для завершения (комплектации) заказа.
 */
public class CompleteOrderCommand implements Command {
    private final String commandId;
    private final String orderId; // идентификатор заказа

    public CompleteOrderCommand(String orderId) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getOrderId() {
        return orderId;
    }
}
