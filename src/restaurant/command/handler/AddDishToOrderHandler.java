package restaurant.command.handler;

import restaurant.command.command.AddDishToOrderCommand;
import restaurant.command.model.CustomerOrder;
import restaurant.command.repository.OrderRepository;

/**
 * Обработчик команды добавления блюда в заказ.
 */
public class AddDishToOrderHandler implements CommandHandler<AddDishToOrderCommand> {
    private final OrderRepository orderRepository;

    public AddDishToOrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(AddDishToOrderCommand command) {
        CustomerOrder order = orderRepository.findById(command.getOrderId());
        order.addDish(command.getDishName(), command.getQuantity());
        orderRepository.save(order);
    }
}
