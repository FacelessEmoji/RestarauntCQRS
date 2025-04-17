package restaurant.query.service;

import restaurant.common.event.*;
import restaurant.query.model.OrderItemView;
import restaurant.query.model.OrderView;
import restaurant.query.repository.OrderViewRepository;

public class EventHandler implements EventBus.EventHandler {
    private final OrderViewRepository orderViewRepository;

    public EventHandler(OrderViewRepository orderViewRepository) {
        this.orderViewRepository = orderViewRepository;
    }

    @Override
    public void handle(Event event) {
        if (event instanceof OrderCreatedEvent) {
            handleOrderCreated((OrderCreatedEvent) event);
        } else if (event instanceof DishAddedToOrderEvent) {
            handleDishAdded((DishAddedToOrderEvent) event);
        } else if (event instanceof OrderUpdatedEvent) {
            handleOrderUpdated((OrderUpdatedEvent) event);
        } else if (event instanceof OrderCompletedEvent) {
            handleOrderCompleted((OrderCompletedEvent) event);
        }
    }

    private void handleOrderCreated(OrderCreatedEvent event) {
        OrderView orderView = new OrderView(
                event.getOrderId(),
                event.getCustomerName(),
                "CREATED",
                event.getCreatedAt()
        );
        orderViewRepository.save(orderView);
    }

    private void handleDishAdded(DishAddedToOrderEvent event) {
        OrderView orderView = orderViewRepository.findById(event.getOrderId());
        double price = getDishPrice(event.getDishName());
        OrderItemView item = new OrderItemView(event.getDishName(), event.getQuantity(), price);
        orderView.addItem(item);
        orderViewRepository.save(orderView);
    }

    private void handleOrderUpdated(OrderUpdatedEvent event) {
        OrderView orderView = orderViewRepository.findById(event.getOrderId());
        orderView.setStatus("UPDATED");
        orderViewRepository.save(orderView);
    }

    private void handleOrderCompleted(OrderCompletedEvent event) {
        OrderView orderView = orderViewRepository.findById(event.getOrderId());
        orderView.setStatus("COMPLETED");
        orderViewRepository.save(orderView);
    }

    private double getDishPrice(String dishName) {
        return switch (dishName.toLowerCase()) {
            case "бургер" -> 250.0;
            case "картофель фри" -> 120.0;
            case "салат" -> 150.0;
            case "напиток" -> 100.0;
            default -> 200.0; // по умолчанию
        };
    }
}
