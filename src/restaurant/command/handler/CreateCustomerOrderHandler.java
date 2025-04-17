package restaurant.command.handler;

import restaurant.command.command.CreateCustomerOrderCommand;
import restaurant.command.model.CustomerOrder;
import restaurant.command.repository.OrderRepository;

/**
 * Обработчик команды создания нового заказа клиента.
 */
public class CreateCustomerOrderHandler implements CommandHandler<CreateCustomerOrderCommand> {
    private final OrderRepository orderRepository;

    public CreateCustomerOrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(CreateCustomerOrderCommand command) {
        // Создаём новый заказ с указанным именем клиента
        CustomerOrder order = new CustomerOrder(command.getCustomerName());
        orderRepository.save(order);
    }
}
