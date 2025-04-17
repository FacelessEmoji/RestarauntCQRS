package restaurant.api.facade;

import restaurant.command.command.*;
import restaurant.command.handler.CommandBus;
import restaurant.query.dto.OrderDTO;
import restaurant.query.dto.OrderStatisticsDTO;
import restaurant.query.service.OrderQueryService;

import java.util.List;

public class RestaurantFacade {
    private final CommandBus commandBus;
    private final OrderQueryService queryService;

    public RestaurantFacade(CommandBus commandBus, OrderQueryService queryService) {
        this.commandBus = commandBus;
        this.queryService = queryService;
    }

    // Командная сторона (запись)
    public void createOrder(String customerName) {
        commandBus.dispatch(new CreateCustomerOrderCommand(customerName));
    }

    public void addDish(String orderId, String dishName, int quantity) {
        commandBus.dispatch(new AddDishToOrderCommand(orderId, dishName, quantity));
    }

    public void updateDish(String orderId, String dishName, int newQuantity) {
        commandBus.dispatch(new UpdateOrderItemCommand(orderId, dishName, newQuantity));
    }

    public void changeOrderStatus(String orderId, restaurant.command.model.OrderStatus newStatus) {
        commandBus.dispatch(new ChangeOrderStatusCommand(orderId, newStatus));
    }

    public void completeOrder(String orderId) {
        commandBus.dispatch(new CompleteOrderCommand(orderId));
    }

    // Запросная сторона (чтение)
    public OrderDTO getOrder(String orderId) {
        return queryService.getOrderById(orderId);
    }

    public List<OrderDTO> getAllOrders() {
        return queryService.getAllOrders();
    }

    public OrderStatisticsDTO getOrderStatistics(String orderId) {
        return queryService.getOrderStatistics(orderId);
    }
}
