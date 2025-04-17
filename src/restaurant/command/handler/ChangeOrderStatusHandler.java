package restaurant.command.handler;

import restaurant.command.command.ChangeOrderStatusCommand;
import restaurant.command.model.CustomerOrder;
import restaurant.command.repository.OrderRepository;

/**
 * Обработчик команды изменения статуса заказа.
 */
public class ChangeOrderStatusHandler implements CommandHandler<ChangeOrderStatusCommand> {
    private final OrderRepository orderRepository;

    public ChangeOrderStatusHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(ChangeOrderStatusCommand command) {
        CustomerOrder order = orderRepository.findById(command.getOrderId());
        order.changeStatus(command.getNewStatus());
        orderRepository.save(order);
    }
}
