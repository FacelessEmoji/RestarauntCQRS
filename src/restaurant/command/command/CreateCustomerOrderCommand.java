package restaurant.command.command;

import java.util.UUID;

/**
 * Команда на создание нового заказа клиента.
 */
public class CreateCustomerOrderCommand implements Command {
    private final String commandId;
    private final String customerName; // имя клиента

    public CreateCustomerOrderCommand(String customerName) {
        this.commandId = UUID.randomUUID().toString();
        this.customerName = customerName;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getCustomerName() {
        return customerName;
    }
}

