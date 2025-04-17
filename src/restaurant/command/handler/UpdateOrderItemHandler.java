package restaurant.command.handler;

import restaurant.command.command.UpdateOrderItemCommand;
import restaurant.command.model.CustomerOrder;
import restaurant.command.repository.OrderRepository;

/**
 * Обработчик команды изменения количества блюда в заказе.
 */
public class UpdateOrderItemHandler implements CommandHandler<UpdateOrderItemCommand> {
    private final OrderRepository orderRepository;

    public UpdateOrderItemHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(UpdateOrderItemCommand command) {
        CustomerOrder order = orderRepository.findById(command.getOrderId());
        order.updateDishQuantity(command.getDishName(), command.getNewQuantity());
        orderRepository.save(order);
    }
}
