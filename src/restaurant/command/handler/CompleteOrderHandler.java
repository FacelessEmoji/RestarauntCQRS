package restaurant.command.handler;

import restaurant.command.command.CompleteOrderCommand;
import restaurant.command.model.CustomerOrder;
import restaurant.command.repository.OrderRepository;

/**
 * Обработчик команды завершения заказа.
 */
public class CompleteOrderHandler implements CommandHandler<CompleteOrderCommand> {
    private final OrderRepository orderRepository;

    public CompleteOrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(CompleteOrderCommand command) {
        CustomerOrder order = orderRepository.findById(command.getOrderId());
        order.completeOrder();
        orderRepository.save(order);
    }
}
